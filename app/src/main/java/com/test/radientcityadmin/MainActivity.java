package com.test.radientcityadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnUserAdd, btnUserSearch, btnServices, announce, addBill;
    ImageButton ib_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        activitySwitch();

        ib_logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void initialize() {
        btnUserAdd = findViewById(R.id.add_user);
        btnUserSearch = findViewById(R.id.complaints);
        btnServices = findViewById(R.id.services);
        announce = findViewById(R.id.announcements);
        addBill = findViewById(R.id.bills);
        ib_logOut = findViewById(R.id.logout);
    }

    private void activitySwitch() {
        btnUserAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUser.class);
                startActivity(intent);
            }
        });
        btnUserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserComplaint.class);
                startActivity(intent);
            }
        });
        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ServiceRequests.class);
                startActivity(intent);
            }
        });
        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAnnouncement.class);
                startActivity(intent);
            }
        });
        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBills.class);
                startActivity(intent);
            }
        });
    }
}