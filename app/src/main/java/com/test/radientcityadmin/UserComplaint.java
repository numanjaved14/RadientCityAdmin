package com.test.radientcityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.radientcityadmin.Adapters.ComplaintAdapter;
import com.test.radientcityadmin.Models.ComplaintDataModel;

import java.util.ArrayList;
import java.util.List;

public class UserComplaint extends AppCompatActivity {
    private RecyclerView recycler_complaint;
    private ComplaintAdapter recyclerViewAdapter;
    private List<ComplaintDataModel> list = new ArrayList<ComplaintDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        setRecycler();
        fetchAllComplaint();
    }

    private void setRecycler() {
        recycler_complaint = findViewById(R.id.recycler_complaint);
        recycler_complaint.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new ComplaintAdapter(this);
        recycler_complaint.setAdapter(recyclerViewAdapter);
    }

    private void fetchAllComplaint() {
        list.clear();

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference("Complaint");
        firebaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                  if (dataSnapshot.getValue() != null) {
                                                      for (DataSnapshot shots : dataSnapshot.getChildren()) {
                                                          ComplaintDataModel complaintdatamodel = shots.getValue(ComplaintDataModel.class);
                                                          assert complaintdatamodel != null;


                                                          String cat = complaintdatamodel.getCategary();
                                                          String desc = complaintdatamodel.getDescription();

                                                          ComplaintDataModel modelnew = new ComplaintDataModel();
                                                          modelnew.setCategary(cat);
                                                          modelnew.setDescription(desc);
                                                          list.add(modelnew);

                                                      }
                                                      if (list!=null && list.size()!=0){
                                                          recyclerViewAdapter.setList(list);
                                                      }

                                                  } else {
                                                      Toast.makeText(UserComplaint.this, "No Announcement Found",
                                                              Toast.LENGTH_SHORT).show();
                                                  }
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {
                                                  Toast.makeText(UserComplaint.this, error.getMessage(),
                                                          Toast.LENGTH_SHORT).show();
                                              }
                                          }
        );
    }
}