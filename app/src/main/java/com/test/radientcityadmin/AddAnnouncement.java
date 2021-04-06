package com.test.radientcityadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAnnouncement extends AppCompatActivity {

    EditText announeTitle, announceDescription;
    Button slctImg, submitAnnoune;

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

            }
        });
    }


    private void initialize() {
        announeTitle = findViewById(R.id.announce_title);
        announceDescription = findViewById(R.id.announce_desc);
        slctImg = findViewById(R.id.select_image);
        submitAnnoune = findViewById(R.id.submit_announce);
    }
}