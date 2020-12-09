package com.example.scholarshiptracker.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity {

    private ScholarshipViewModel viewModel;
    private EditText nameEditText;
    private EditText amountEditText;
    private EditText dateAppliedEditText;
    private EditText deadlineEditText;
    private EditText announcementEditText;
    private EditText contactInfoEditText;
    private EditText otherNotesEditText;
    private Button submitButton;

    private DatePickerDialog.OnDateSetListener dateAppliedListener;
    private DatePickerDialog.OnDateSetListener deadlineListener;
    private DatePickerDialog.OnDateSetListener announcementListener;
    private Scholarship recievedScholarship;

    //    Constants used to identify different dialogs and onDateListeners
    private static final int DATE_PICKER_APPLIED = 0;
    private static final int DATE_PICKER_DEADLINE = 1;
    private static final int DATE_PICKER_ANNOUNCE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        nameEditText = findViewById(R.id.scholarship_name_edit_text);
        amountEditText = findViewById(R.id.amount_edit_text);
        dateAppliedEditText = findViewById(R.id.date_applied_edit_text);
        deadlineEditText = findViewById(R.id.application_deadline_edit_text);
        announcementEditText = findViewById(R.id.announcement_edit_text);
        contactInfoEditText = findViewById(R.id.contact_info_edit_text);
        otherNotesEditText = findViewById(R.id.other_notes_edit_text);
        submitButton = findViewById(R.id.update_button);

//        Initializing ViewModel
        viewModel = ViewModelProviders.of(this).get(ScholarshipViewModel.class);

//        Getting scholarshipID passed from MainActivity
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            recievedScholarship = (Scholarship) intent.getSerializableExtra("Scholar");
            nameEditText.setText(recievedScholarship.getScholarshipName());
            amountEditText.setText(String.valueOf(recievedScholarship.getAmount()));
            deadlineEditText.setText(recievedScholarship.getApplicationDeadline());
            dateAppliedEditText.setText(recievedScholarship.getDateApplied());
            announcementEditText.setText(recievedScholarship.getExpectedResponseDate());
            contactInfoEditText.setText(recievedScholarship.getContactInfo());
            otherNotesEditText.setText(recievedScholarship.getOtherNotes());
            Toast.makeText(this, String.valueOf(recievedScholarship.getScholarshipID()), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Scholarship Failed to Load", Toast.LENGTH_SHORT).show();
        }


        submitButton.setOnClickListener(view -> {
            updateScholarship();
        });

        /*
         * When an edit text is clicked a datepicker dialog shows up, the date is then saved as a
         * string then shown in the edit text
         * */
        dateAppliedEditText.setOnClickListener(view -> {
//            hideKeyboard(this);
            hideSoftKeyboard(dateAppliedEditText);
            showDatePickerDialog(DATE_PICKER_APPLIED);
        });

        /*
         * On date listeners triggered after the user chooses a date
         * the date is saved as a string
         * */
        dateAppliedListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            String chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
            dateAppliedEditText.setText(chosenDate);
        };


        announcementEditText.setOnClickListener(view -> {
//            hideKeyboard(this);
            hideSoftKeyboard(dateAppliedEditText);
            showDatePickerDialog(DATE_PICKER_ANNOUNCE);
        });

        announcementListener = (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {
            String chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
            announcementEditText.setText(chosenDate);
        };

        deadlineEditText.setOnClickListener(view -> {
//            hideKeyboard(this);
            hideSoftKeyboard(dateAppliedEditText);
            showDatePickerDialog(DATE_PICKER_DEADLINE);
        });

        deadlineListener = (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {
            String chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
            deadlineEditText.setText(chosenDate);
        };


    }

    /*
     * This was necessary to have different onDateListers for the different dialogs
     * Otherwise one OnDatelistener could only update one editText's string
     * */
    private void showDatePickerDialog(int datePickerID) {
        switch (datePickerID) {
            case DATE_PICKER_APPLIED:
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        this,
                        dateAppliedListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case DATE_PICKER_ANNOUNCE:
                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        announcementListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case DATE_PICKER_DEADLINE:
                DatePickerDialog picker = new DatePickerDialog(
                        this,
                        deadlineListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                picker.show();
                break;

        }

    }

    private void updateScholarship() {
        String scholarshipName = nameEditText.getText().toString();
        String dateApplied = dateAppliedEditText.getText().toString();
        String deadline = deadlineEditText.getText().toString();
        String announcmentDate = announcementEditText.getText().toString();
        int amount = 0;
        String contactInfo = contactInfoEditText.getText().toString();
        String otherNotes = otherNotesEditText.getText().toString();

        boolean nameEntered = true;
        boolean amountEntered = true;
        boolean applied = true;
        boolean deadlineEntered = true;


//        Checking if all required fields are entered and that the data is valid before insertion
        if (scholarshipName.isEmpty()) {
            Toast.makeText(this, "Scholarship needs a name", Toast.LENGTH_SHORT).show();
            nameEntered = false;
        } else {
            nameEntered = true;
        }
        if (amountEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Scholarship needs an amount", Toast.LENGTH_SHORT).show();
            amountEntered = false;
        } else {
            amount = Integer.parseInt(amountEditText.getText().toString());
            amountEntered = true;
        }

        if (announcmentDate.isEmpty()) {
            announcmentDate = "N/A";
        }
        if (contactInfo.isEmpty()) {
            contactInfo = "N/A";
        }
        if (otherNotes.isEmpty()) {
            otherNotes = "N/A";
        }

        if (nameEntered == false || amountEntered == false || applied == false || deadlineEntered == false) {
            Toast.makeText(this, "All required fields not entered", Toast.LENGTH_LONG).show();
            Log.d("TAG", "nameEntered= " + nameEntered);
            Log.d("TAG", "amountEntered= " + amountEntered);
            Log.d("TAG", "applied= " + applied);
            Log.d("TAG", "deadlineEntered= " + deadlineEntered);
        } else {
            recievedScholarship.setAmount(amount);
            recievedScholarship.setScholarshipName(scholarshipName);
            recievedScholarship.setApplicationDeadline(deadline);
            recievedScholarship.setDateApplied(dateApplied);
            recievedScholarship.setContactInfo(contactInfo);
            recievedScholarship.setExpectedResponseDate(announcmentDate);
            recievedScholarship.setOtherNotes(otherNotes);

            viewModel.updateScholarship(recievedScholarship);
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    //Helper method to hide the keyboard when clicking on the date picker edit text
    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    //    Helper method to show a dialog when the user is about to leave without
//    finishing editing
    private void showUnsavedEditsDialog(@NonNull Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to exit?");
        builder.setTitle("Unsaved Edits");
        builder.setCancelable(false);


        builder.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    dialog.cancel();
                    finish();
                });

        builder.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        showUnsavedEditsDialog(this);
    }
}
