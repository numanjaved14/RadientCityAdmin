package com.test.radientcityadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnUserAdd, btnUserSearch, btnServices, announce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        activitySwitch();

    }

    private void initialize() {
        btnUserAdd = findViewById(R.id.add_user);
        btnUserSearch = findViewById(R.id.users);
        btnServices = findViewById(R.id.services);
        announce = findViewById(R.id.announcements);
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
                Intent intent = new Intent(MainActivity.this, SearchUser.class);
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
    }
}