package com.example.scholarshiptracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.delete_scholarships_menu_item: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete all entries?");
                builder.setTitle("Delete All Scholarships");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.ic_baseline_delete_24);

                builder.setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            scholarshipViewModel.deleteAllScholarships();
                            Toast.makeText(MainActivity.this, "Scholarships Deleted", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        });

                builder.setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel());

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}