package com.test.radientcityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class AddAnnouncement extends AppCompatActivity {

    EditText announeTitle, announceDescription, announceStatus, announceDate;
    Button slctImg, submitAnnoune;
    String title, description, status, date;
    FirebaseDatabase dataBase;
    DatabaseReference firebaseRef;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);

        initialize();
        buttonClick();
    }

    private void buttonClick() {
        slctImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        submitAnnoune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = announeTitle.getText().toString().trim();
                description = announceDescription.getText().toString().trim();
                status = announceStatus.getText().toString().trim();
                date = announceDate.getText().toString().trim();
                addAnnouncement();

            }
        });
    }

    private void addAnnouncement() {
        progressDialog.show();
        Random rand = new Random();
        int upperbound = 15000;
        int int_random = rand.nextInt(upperbound);
        String announcementId = "announcement-" + int_random + "-Radient";
        firebaseRef = FirebaseDatabase.getInstance().getReference("Announcements").child(announcementId);
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("firebaseId", announcementId);
        hashmap.put("title", title);
        hashmap.put("description", description);
        hashmap.put("status", status);
        hashmap.put("date", date);
        Log.i("PYC_LOG", "Sending Params " + hashmap);
        firebaseRef.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(AddAnnouncement.this, "Announcement Added Successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddAnnouncement.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initialize() {
        announeTitle = findViewById(R.id.announce_title);
        announceDescription = findViewById(R.id.announce_desc);
        slctImg = findViewById(R.id.select_image);
        submitAnnoune = findViewById(R.id.submit_announce);
        announceDate = findViewById(R.id.announce_date);
        announceStatus = findViewById(R.id.announce_status);
        dataBase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(AddAnnouncement.this);
        progressDialog.setTitle("Adding announcement");
        progressDialog.setMessage("loading . . . . ");
    }
}