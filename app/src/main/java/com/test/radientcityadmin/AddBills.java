package com.test.radientcityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.radientcityadmin.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AddBills extends AppCompatActivity {
    private EditText bill_title, bill_desc, bill_amount, bill_status, billIssue_date, billDue_date;
    private Button submit_announce;
    FirebaseDatabase dataBase;
    DatabaseReference firebaseRef;
    ProgressDialog progressDialog;
    private Spinner users;
    List<User> activeUsers = new ArrayList<User>();
    String UUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bills);
        mainMethods();

    }

    private void mainMethods() {
        initViews();
        buttonClick();
    }

    private void buttonClick() {
        submit_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBills(bill_title.getText().toString().trim(), bill_desc.getText().toString().trim(),
                        bill_amount.getText().toString().trim(), bill_status.getText().toString().trim(),
                        billIssue_date.getText().toString().trim(), billDue_date.getText().toString().trim()
                );
            }
        });

    }

    private void initViews() {
        bill_title = findViewById(R.id.bill_title);
        bill_desc = findViewById(R.id.bill_desc);
        bill_amount = findViewById(R.id.bill_amount);
        bill_status = findViewById(R.id.bill_status);
        billIssue_date = findViewById(R.id.billIssue_date);
        billDue_date = findViewById(R.id.billDue_date);
        submit_announce = findViewById(R.id.submit_announce);
        users = findViewById(R.id.users);
        dataBase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(AddBills.this);
        progressDialog.setTitle("Adding Bill against this user");
        progressDialog.setMessage("loading . . . . ");
        fetchAllRegisteredUsers();
        users.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UUID = activeUsers.get(i).getFirebaseId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void fetchAllRegisteredUsers() {
        activeUsers.clear();
        List<String> list = new ArrayList<>();
        list.add("Select User");
        DatabaseReference firebaseRef = dataBase.getReference("Users");
        firebaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                  if (dataSnapshot.getValue() != null) {
                                                      for (DataSnapshot shots : dataSnapshot.getChildren()) {
                                                          User user = shots.getValue(User.class);
                                                          assert user != null;
                                                          String userType = user.getType();
                                                          if (userType.equalsIgnoreCase("user")) {
                                                              String id = user.getFirebaseId();
                                                              String name = user.getUserName();
                                                              User model = new User(id, name);
                                                              activeUsers.add(model);
                                                              list.add(name);
                                                          }
                                                      }
                                                      ArrayAdapter<String> adp1 = new ArrayAdapter<String>(AddBills.this,
                                                              android.R.layout.simple_list_item_1, list);
                                                      users.setAdapter(adp1);

                                                  } else {
                                                      Toast.makeText(AddBills.this, "No User Found",
                                                              Toast.LENGTH_SHORT).show();
                                                  }
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {
                                                  Toast.makeText(AddBills.this, error.getMessage(),
                                                          Toast.LENGTH_SHORT).show();
                                              }
                                          }
        );
    }

    private void addBills(String bill_title, String bill_desc, String bill_amount, String bill_status,
                          String billIssue_date, String billDue_date) {
        progressDialog.show();
        Random rand = new Random();
        int upperbound = 15000;
        int int_random = rand.nextInt(upperbound);
        String announcementId = "bill-" + bill_title + "-" + int_random + "-Radient";
        firebaseRef = FirebaseDatabase.getInstance().getReference("Bills").child(UUID).child(announcementId);
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("firebaseId", announcementId);
        hashmap.put("candidate", UUID);
        hashmap.put("title", bill_title);
        hashmap.put("description", bill_desc);
        hashmap.put("amount", bill_amount);
        hashmap.put("status", bill_status);
        hashmap.put("IssueDate", billIssue_date);
        hashmap.put("DueDate", billDue_date);
        Log.i("PYC_LOG", "Sending Params " + hashmap);
        firebaseRef.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(AddBills.this, "Announcement Added Successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddBills.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}