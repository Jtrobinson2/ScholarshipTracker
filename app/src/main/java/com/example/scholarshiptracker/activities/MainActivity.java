package com.example.scholarshiptracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.adapters.ScholarshipAdapter;
import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/*
 *   TODO: make the detail activity look good
 *    TODO: Add actions to all fab options
 *    TODO: find colors for all fab options
 *     TODO: decrease fab opening and closing animation
 *     TODO: get the proper text sizes use them throughout your app
 *      TODO: Fix cardview layout make them the same size, and look better
 *       TODO: Create splashscreen
 *        TODO: Test app
 *         TODO: make spacing even on editor and add activities
 *         TODO: fix announcement date info button placement
 *         TODO: Create app icoon
 *           TODO: make free and paid variants
 *            TODO: Add list item addition, and deletion animations
 *             TODO: Translate APP
 *          TODO: add premium feature to remove ads, and export database to google sheets
 *    TODO: Add a  material scrollbar to they can jump to the top of the list
 *     TODO: add a navigation drawer housing the rate button, go-premium button, about us button, and settings button
 *       TODO: currently database is cleared when version is updgraded change this for future updates to the app
 *
 * */


public class MainActivity extends AppCompatActivity {
    private ScholarshipViewModel scholarshipViewModel;
    private ScholarshipAdapter adapter;
    private RecyclerView scholarshipRecyclerView;
    private static final int REQUEST_CODE_EDIT = 1;
    private Parcelable savedRecyclerViewState;
    private static final int REQUEST_CODE_ITEM_VIEW = 2;
    private static final int REQUEST_CODE_ADD = 3;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpViewModel();
        FloatingActionButton addScholarshipbutton = findViewById(R.id.expandable_fab_base);

        addScholarshipbutton.setOnClickListener(view -> {


        });


    }


    private void setUpRecyclerView() {
        scholarshipRecyclerView = findViewById(R.id.scholarships_recycler_view);

//        Sending an object using serializable  is slower than parceable but not noticeable here
        adapter = new ScholarshipAdapter(new ScholarshipAdapter.ScholarshipDiff(), new ScholarshipAdapter.onClickInterface() {
            @Override
            public void onEditClicked(Scholarship scholarship, int position) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("Scholar", scholarship);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT);


            }


            @Override
            public void onDeleteClicked(Scholarship scholarship, int position) {
                showDeleteScholarshipDialog(scholarship);
            }

            @Override
            public void onItemViewClick(Scholarship scholarship, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Scholar", scholarship);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_ITEM_VIEW);

            }
        });

        layoutManager = new LinearLayoutManager(this);
        scholarshipRecyclerView.setAdapter(adapter);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        scholarshipRecyclerView.setLayoutManager(layoutManager);


    }


    private void setUpViewModel() {
        scholarshipViewModel = ViewModelProviders.of(this).get(ScholarshipViewModel.class);
        scholarshipViewModel.getAllScholarships().observe(this, scholarships -> {
            setUpRecyclerView();
            adapter.submitList(scholarships);
//            Restore the scroll position when the new data has come into the recyclerview
            layoutManager.onRestoreInstanceState(savedRecyclerViewState);
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
                            AddScholarshipActivity.showDeletionSnackbar(this);
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
                for (int i = 0; i < 10; i++) {
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
                    AddScholarshipActivity.showDeletionSnackbar(this);
                    dialog.cancel();
                });

        builder.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int position = data.getIntExtra("updatedPos", 0);
            if (requestCode == REQUEST_CODE_EDIT) {
                AddScholarshipActivity.showUpdatedSuccessSnackbar(MainActivity.this);
            }
//            When you come back from the add scholarship activity scroll to the top of the list
            else if (requestCode == REQUEST_CODE_ADD) {
                AddScholarshipActivity.showAdditionSuccessSnackbar(MainActivity.this);
                new Handler().postDelayed(() -> scholarshipRecyclerView.scrollToPosition(adapter.getItemCount() - 1), 75);
            }
        }


    }


    //    saving the scroll position so when the user return it doesn't just jump to the bottom of the list
    @Override
    protected void onPause() {
        super.onPause();
        savedRecyclerViewState = layoutManager.onSaveInstanceState();
    }

    public void addScholarshipMoney(View view) {
    }

    public void orderByDate(View view) {
    }

    public void orderByAmount(View view) {
    }

    public void orderAlphabetically(View view) {
    }

    public void addScholarship(View view) {
        Intent intent = new Intent(MainActivity.this, AddScholarshipActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }
}



