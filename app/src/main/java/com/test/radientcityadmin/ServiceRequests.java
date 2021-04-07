package com.test.radientcityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.radientcityadmin.Adapters.ComplaintAdapter;
import com.test.radientcityadmin.Adapters.ServiceAdapter;
import com.test.radientcityadmin.Models.ComplaintDataModel;
import com.test.radientcityadmin.Models.ServiceDataModel;
import com.test.radientcityadmin.Models.User;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequests extends AppCompatActivity {
    private RecyclerView recycler_service;
    private ServiceAdapter recyclerViewAdapter;
    private List<ServiceDataModel> list = new ArrayList<ServiceDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requests);
        getService();
        setRecycler();
    }

    private void getService() {
        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                  if (dataSnapshot.getValue() != null) {
                                                      for (DataSnapshot shots : dataSnapshot.getChildren()) {
                                                          User user = shots.getValue(User.class);
                                                          assert user != null;
                                                          String id = user.getFirebaseId();
                                                          fetchAllServices(id);
                                                      }

                                                  } else {
                                                      Toast.makeText(ServiceRequests.this, "No User Found",
                                                              Toast.LENGTH_SHORT).show();
                                                  }
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {
                                                  Toast.makeText(ServiceRequests.this, error.getMessage(),
                                                          Toast.LENGTH_SHORT).show();
                                              }
                                          }
        );
    }

    private void setRecycler() {
        recycler_service = findViewById(R.id.recycler_service);
        recycler_service.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new ServiceAdapter(this);
        recycler_service.setAdapter(recyclerViewAdapter);
    }

    private void fetchAllServices(String childId) {
        list.clear();

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference("Service").child(childId);
        firebaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                  if (dataSnapshot.getValue() != null) {
                                                      for (DataSnapshot shots : dataSnapshot.getChildren()) {
                                                          ServiceDataModel serviceDataModel = shots.getValue(ServiceDataModel.class);
                                                          assert serviceDataModel != null;
                                                          String cat = serviceDataModel.getCategary();
                                                          String desc = serviceDataModel.getDescription();
                                                          String date = serviceDataModel.getServiceDate();
                                                          String time = serviceDataModel.getServiceTime();
                                                          String parentId = serviceDataModel.getParentId();
                                                          String serviceId = serviceDataModel.getFirebaseId();
                                                          String status = serviceDataModel.getServiceStatus();

                                                          ServiceDataModel modelnew = new ServiceDataModel();
                                                          modelnew.setCategary(cat);
                                                          modelnew.setDescription(desc);
                                                          modelnew.setServiceDate(date);
                                                          modelnew.setServiceTime(time);
                                                          modelnew.setParentId(parentId);
                                                          modelnew.setFirebaseId(serviceId);
                                                          modelnew.setServiceStatus(status);
                                                          list.add(modelnew);

                                                      }
                                                      if (list!=null && list.size()!=0){
                                                          recyclerViewAdapter.setList(list);
                                                      }

                                                  }
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {
                                                  Toast.makeText(ServiceRequests.this, error.getMessage(),
                                                          Toast.LENGTH_SHORT).show();
                                              }
                                          }
        );
    }
}