package com.example.scholarshiptracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.adapters.ScholarshipAdapter;
import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/*
 * TODO: have scholarships added to the list most recent on top
 *  TODO: after editing a scholarship have the list stay at the position of the edited scholarship
 *   TODO: make the detail activity look good
 *    TODO: Choose a color scheme for the app
 *     TODO: get the proper text sizes use them throughout your app
 *      TODO: make the button icons on the list items have a transparent backround
 *     TODO: polish the edit texts and add an info button next to the announcement date field and other notes field
 *    TODO: Add a scrollbar to they can jump to the top of the list
 *
 * */
public class MainActivity extends AppCompatActivity {
    private ScholarshipViewModel scholarshipViewModel;
    private ScholarshipAdapter adapter;
    private ScholarshipAdapter.onClickInterface onClickInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();
        setUpViewModel();
        FloatingActionButton addScholarshipbutton = findViewById(R.id.add_scholarship_button);

        addScholarshipbutton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddScholarshipActivity.class);
            startActivity(intent);

        });


    }

    private void setUpRecyclerView() {
        RecyclerView scholarshipRecyclerView = findViewById(R.id.scholarships_recycler_view);

//        Sending an object using serializable  is slower than parceable but not noticeable here
        adapter = new ScholarshipAdapter(new ScholarshipAdapter.ScholarshipDiff(), new ScholarshipAdapter.onClickInterface() {
            @Override
            public void onEditClicked(Scholarship scholarship) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("Scholar", scholarship);
                startActivity(intent);

            }

            @Override
            public void onDeleteClicked(Scholarship scholarship) {
                showDeleteScholarshipDialog(scholarship);
            }

            @Override
            public void onItemViewClick(Scholarship scholarship) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Scholar", scholarship);
                startActivity(intent);

            }
        });
        scholarshipRecyclerView.setAdapter(adapter);
        scholarshipRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

    }


    private void setUpViewModel() {
        scholarshipViewModel = ViewModelProviders.of(this).get(ScholarshipViewModel.class);
        scholarshipViewModel.getAllScholarships().observe(this, new Observer<List<Scholarship>>() {
            @Override
            public void onChanged(List<Scholarship> scholarships) {
                adapter.submitList(scholarships);
            }
        });

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
            case (R.id.add_fake_scholarships):
                for (int i = 0; i < 100; i++) {
                    Scholarship scholarship = new Scholarship("Test", 12, "03/2/3223", "03/19/2002");
                    scholarshipViewModel.insertScholarship(scholarship);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showDeleteScholarshipDialog(Scholarship scholarship) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this scholarship?");
        builder.setTitle("Delete Entry");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_baseline_delete_24);

        builder.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    scholarshipViewModel.deleteScholarship(scholarship);
                    Toast.makeText(MainActivity.this, "Scholarship Deleted", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                });

        builder.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}