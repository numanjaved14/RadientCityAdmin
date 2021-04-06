package com.test.radientcityadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.radientcityadmin.Adapters.RecyclerViewAdapter;
import com.test.radientcityadmin.Models.Datamodel_announce;

import java.util.ArrayList;
import java.util.List;

public class Announcements extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton btnAddAnnounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        initialize();
        getData();

        btnAddAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Announcements.this, AddAnnouncement.class);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        recyclerView = findViewById(R.id.recyclerview_home);
        btnAddAnnounce = findViewById(R.id.add_announce_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void getData() {
        List<Datamodel_announce> list = new ArrayList<Datamodel_announce>();

        list.add(new Datamodel_announce("name", "desc", R.drawable.ic_launcher_background));
        list.add(new Datamodel_announce("name", "desc", R.drawable.ic_launcher_background));
        list.add(new Datamodel_announce("name", "desc", R.drawable.ic_launcher_background));
        list.add(new Datamodel_announce("name", "desc", R.drawable.ic_launcher_background));
        list.add(new Datamodel_announce("name", "desc", R.drawable.ic_launcher_background));
        recyclerViewAdapter.setList(list);

    }
}