package com.example.scholarshiptracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.adapters.ScholarshipAdapter;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;

public class MainActivity extends AppCompatActivity {
    private ScholarshipViewModel scholarshipViewModel;
    private ScholarshipAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();
        setUpViewModel();
        Button button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddScholarshipActivity.class);
            startActivity(intent);

        });


    }

    private void setUpRecyclerView() {
        RecyclerView scholarshipRecyclerView = findViewById(R.id.scholarships_recycler_view);
        adapter = new ScholarshipAdapter(new ScholarshipAdapter.ScholarshipDiff());
        scholarshipRecyclerView.setAdapter(adapter);
        scholarshipRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setUpViewModel() {
        scholarshipViewModel = ViewModelProviders.of(this).get(ScholarshipViewModel.class);
        scholarshipViewModel.getAllScholarships().observe(this, scholarships -> adapter.submitList(scholarships));

    }


}