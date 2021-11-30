package com.example.scholarshiptracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.adapters.ScholarshipAdapter;
import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;
import com.nambimobile.widgets.efab.ExpandableFab;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.mateware.snacky.Snacky;

/*
 *   TODO: make the detail activity look good
 *     TODO: get the proper text sizes use them throughout your app
 *       TODO: figure out how you want the list items to look
 *       TODO: Create splashscreen
 *        TODO: Test app
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
    private static final int REQUEST_CODE_EDIT = 1;
    private static final int REQUEST_CODE_ITEM_VIEW = 2;
    private static final int REQUEST_CODE_ADD = 3;
    private ScholarshipViewModel scholarshipViewModel;
    private ScholarshipAdapter adapter;
    private RecyclerView scholarshipRecyclerView;
    private Parcelable savedRecyclerViewState;
    private LinearLayoutManager layoutManager;
    private ExpandableFab expandableFab;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;


    /**
     * Boolean that determines how the recyclerview was sorted used for returning to the previous sorted order when leaving and returning the mainactivity
     */
    private boolean isSortedAlphabetically;


    /**
     * Boolean that determines how the recyclerview was sorted used for returning to the previous sorted order when leaving and returning the mainactivity
     */
    private boolean isSortedByAmount;


    /**
     * Boolean that determines how the recyclerview was sorted used for returning to the previous sorted order when leaving and returning the mainactivity
     */
    private boolean isSortedByDateApplied;

    /*
     * Helper method to get the dpi so that I can alter the expandableFAB button margins
     * */
    private static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableFab = findViewById(R.id.expandable_fab_base);
        adjustExpandableFabMargins(getDensityName(this));
        setUpViewModel();
        setUpNavigationDrawer();



    }

    /**
     * Helper method to setup the navigation drawer in the main activity
     */
    private void setUpNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    /**
     * Overrode this method to synchronize the navigation drawer icon
     * @param savedInstanceState of activity
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
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

            case (android.R.id.home):
                drawerLayout.openDrawer(GravityCompat.START);
                return true;


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
        double totalEarned = 0.00;
        for (int i = 0; i < scholarshipViewModel.getAllScholarships().getValue().size(); i++) {
            totalEarned += scholarshipViewModel.getAllScholarships().getValue().get(i).getAmount();
        }
        Snacky.builder().setActivity(this).setText("Total Amount: " + totalEarned).setBackgroundColor(this.getResources().getColor(R.color.colorPrimary)).setTextColor(this.getResources().getColor(R.color.colorAccent)).build().show();

    }

    public void orderByDate(View view) {

        //Checking how the list was sorted before this method was called to update the flags appropriately
        if (isSortedAlphabetically || isSortedByAmount) {
            isSortedAlphabetically = false;
            isSortedByAmount = false;
            isSortedByDateApplied = true;
        }
        List<String> sortedList;
        List<Long> epochList;

        //Get the dates of the scholarships and convert them to mili since epoch
        sortedList = new ArrayList<String>();

        //loop through all the scholarships and get the dates
        for (int i = 0; i < scholarshipViewModel.getAllScholarships().getValue().size(); i++) {
            sortedList.add(scholarshipViewModel.getAllScholarships().getValue().get(i).getDateApplied());
        }

        epochList = new ArrayList<Long>();
        //Add all the dates to an epoch list
        for (String date : sortedList) {
            try {
                epochList.add(convertDateStringToEpoch(date));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Scholarship has invalid date string");
            }
        }

        //Now sort the list of dates
        Collections.sort(epochList, Long::compareTo);

        //convert the sorted list of epoch dates back into there date strings
        //Add the date strings to the date string list (now in sorted order)
        for (int i = 0; i < epochList.size(); i++) {
            sortedList.set(i, convertEpochToDateString(epochList.get(i)));
        }

        //Now for every date string find the scholarship associated with it and add it to a list
        // Setup the new array to replace the unsorted tasks array
        List<Scholarship> sorted = new ArrayList<Scholarship>();

        for (int i = 0; i < sortedList.size(); i++) {
            for (int j = 0; j < scholarshipViewModel.getAllScholarships().getValue().size(); j++) {
                if (scholarshipViewModel.getAllScholarships().getValue().get(j).getDateApplied().equals(sortedList.get(i))) {
                    sorted.add(scholarshipViewModel.getAllScholarships().getValue().get(j));
                }
            }
        }

        // Replace the old list of scholarship with the new sorted one
        for (int i = 0; i < scholarshipViewModel.getAllScholarships().getValue().size(); i++) {
            scholarshipViewModel.getAllScholarships().getValue().set(i, sorted.get(i));
        }

        //No need to reverse since we wan't to order by most recent first
        adapter.notifyDataSetChanged();


    }

    /**
     * Orders scholarships in a list by descending order
     *
     * @param view
     */
    public void orderByAmount(View view) {
        //Checking how the list was sorted before this method was called to update the flags appropriately
        if (isSortedAlphabetically || isSortedByDateApplied) {
            isSortedAlphabetically = false;
            isSortedByDateApplied = false;
            isSortedByAmount = true;

        }
        //Creating a new comparator and comparing on amount this only works in Android N and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Comparator<Scholarship> compareByAmount = Comparator.comparing(Scholarship::getAmount);

            //get all the scholarships avaliable
            List<Scholarship> sortedList = scholarshipViewModel.getAllScholarships().getValue();

            //Sort them by amound
            Collections.sort(sortedList, compareByAmount);

            //replace the unsorted list with the new sorted list
            for (int i = 0; i < scholarshipViewModel.getAllScholarships().getValue().size(); i++) {
                scholarshipViewModel.getAllScholarships().getValue().set(i, sortedList.get(i));
            }


            //notify the adapter of the changes
            adapter.notifyDataSetChanged();


        }

        // If the device doesn't have android N we'll have to make a comparator the old fashioned way returns a positive int if this has more than another scholarship
        Comparator<Scholarship> compareByAmount = (thisOne, thatOne) -> {
            if (thisOne.getAmount() > thatOne.getAmount()) {
                return 1;
            } else if (thisOne.getAmount() < thatOne.getAmount()) {
                return -1;
            } else if (thisOne.getAmount() == thatOne.getAmount()) {
                return 0;
            }
            return 0;
        };


        //get all the scholarships avaliable
        List<Scholarship> sortedList = scholarshipViewModel.getAllScholarships().getValue();

        //Sort them by amound
        Collections.sort(sortedList, compareByAmount);

        //replace the unsorted list with the new sorted list
        for (int i = 0; i < scholarshipViewModel.getAllScholarships().getValue().size(); i++) {
            scholarshipViewModel.getAllScholarships().getValue().set(i, sortedList.get(i));
        }

        //notify the adapter of the changes no need to reverse the list, ints when naturally sorted are in ascending order which would be descending in the recyclerview
        adapter.notifyDataSetChanged();


    }

    public void orderAlphabetically(View view) {
        //Checking how the list was sorted before this method was called to update the flags appropriately
        if (isSortedByAmount || isSortedByDateApplied) {
            isSortedByAmount = false;
            isSortedByDateApplied = false;
            isSortedAlphabetically = true;

        }
        List<Scholarship> sortedList = scholarshipViewModel.getAllScholarships().getValue();

        Collections.sort(sortedList, Scholarship::compareTo);

        for (int i = 0; i < scholarshipViewModel.getAllScholarships().getValue().size(); i++) {

            //set it to the reverse since the recyclerview is inverted
            scholarshipViewModel.getAllScholarships().getValue().set(i, sortedList.get(i));


        }

        Collections.reverse(scholarshipViewModel.getAllScholarships().getValue());
        adapter.notifyDataSetChanged();
    }

    public void addScholarship(View view) {
        Intent intent = new Intent(MainActivity.this, AddScholarshipActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    /*
     * Helper method to adjust expandable fab margins based on pixel density since the library
     * uses Px and not DP!
     * */
    private void adjustExpandableFabMargins(String deviceDPI) {
        switch (deviceDPI) {
            case ("xxxhdpi"):
                expandableFab.setFirstFabOptionMarginPx(200);
                expandableFab.setSuccessiveFabOptionMarginPx(200);
                break;
            case ("xxhdpi"):
                expandableFab.setSuccessiveFabOptionMarginPx(150);
                expandableFab.setFirstFabOptionMarginPx(150);
                break;
            case ("xhdpi"):
                expandableFab.setFirstFabOptionMarginPx(100);
                expandableFab.setSuccessiveFabOptionMarginPx(100);
                break;
            case ("hdpi"):
                expandableFab.setFirstFabOptionMarginPx(75);
                expandableFab.setSuccessiveFabOptionMarginPx(75);
                break;
            case ("mdpi"):
                expandableFab.setFirstFabOptionMarginPx(50);
                expandableFab.setSuccessiveFabOptionMarginPx(50);
                break;
            case ("ldpi"):
                expandableFab.setFirstFabOptionMarginPx(37);
                expandableFab.setSuccessiveFabOptionMarginPx(37);
                break;
            default:
                expandableFab.setFirstFabOptionMarginPx(80);
                expandableFab.setSuccessiveFabOptionMarginPx(75);
        }
    }

    /**
     * Helper method to convert scholarships mm/dd/yyyy date string to miliseconds epoch for comparison purposes
     *
     * @param date mm/dd/yyyy
     * @return miliseconds since epoch of date string
     * @throws ParseException for invalid date strings
     */
    private Long convertDateStringToEpoch(String date) throws ParseException {

        return new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();


    }


    /**
     * Helper method to convert epoch date to string date
     *
     * @param date in milliseconds since epoch you want to convert
     * @return mm/dd/yyyy representation of date long
     */
    private String convertEpochToDateString(Long date) {
        //Create data in millis
        Date epochDate = new Date(date);
        // use correct format ('S' for milliseconds)
        SimpleDateFormat formatter = new SimpleDateFormat("MM/d/yyyy");
        // format date
        String formatted = formatter.format(date);

        return formatted;


    }

    /**
     * Overriden to handle navigation drawer being closed on back pressed
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}


