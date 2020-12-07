package com.example.scholarshiptracker.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;

import java.util.Calendar;

public class AddScholarshipActivity extends AppCompatActivity {

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

    //    Constants used to identify different dialogs and onDateListeners
    private static final int DATE_PICKER_APPLIED = 0;
    private static final int DATE_PICKER_DEADLINE = 1;
    private static final int DATE_PICKER_ANNOUNCE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scholarship);

//        Initializing ViewModel
        viewModel = ViewModelProviders.of(this).get(ScholarshipViewModel.class);

        nameEditText = findViewById(R.id.scholarship_name_edit_text);
        amountEditText = findViewById(R.id.amount_edit_text);
        dateAppliedEditText = findViewById(R.id.date_applied_edit_text);
        deadlineEditText = findViewById(R.id.application_deadline_edit_text);
        announcementEditText = findViewById(R.id.announcement_edit_text);
        contactInfoEditText = findViewById(R.id.contact_info_edit_text);
        otherNotesEditText = findViewById(R.id.other_notes_edit_text);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(view -> {
            addScholarship();
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

    private void addScholarship() {
        String scholarshipName = "";
        String dateApplied = "";
        String deadline = "";
        String announcmentDate = "";
        int amount = 0;
        String contactInfo = "";
        String otherNotes = "";
        boolean nameEntered = false;
        boolean amountEntered = false;
        boolean applied = false;
        boolean deadlineEntered = false;

//        Checking if all required fields are entered and that the data is valid before insertion
        if (nameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Scholarship needs a name", Toast.LENGTH_SHORT).show();
        } else {
            scholarshipName = nameEditText.getText().toString();
            nameEntered = true;
        }
        if (amountEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Scholarship needs an amount", Toast.LENGTH_SHORT).show();
        } else {
            amount = Integer.parseInt(amountEditText.getText().toString());
            amountEntered = true;
        }
        if (dateAppliedEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Date applied is required", Toast.LENGTH_SHORT).show();
        } else {
            dateApplied = dateAppliedEditText.getText().toString();
            applied = true;
        }
        if (deadlineEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Deadline is required", Toast.LENGTH_SHORT).show();
        } else {
            deadline = deadlineEditText.getText().toString();
            deadlineEntered = true;
        }
        if (announcementEditText.getText().toString().isEmpty()) {
            announcmentDate = "N/A";
        } else {
            announcmentDate = announcementEditText.getText().toString();
        }
        if (contactInfoEditText.getText().toString().isEmpty()) {
            contactInfo = "N/A";
        } else {
            contactInfo = contactInfoEditText.getText().toString();
        }
        if (otherNotesEditText.getText().toString().isEmpty()) {
            otherNotes = "N/A";
        } else {
            otherNotes = otherNotesEditText.getText().toString();
        }

        if (nameEntered == false || amountEntered == false || applied == false || deadlineEntered == false) {
            Toast.makeText(this, "All required fields not entered", Toast.LENGTH_LONG).show();
            Log.d("TAG", "nameEntered= " + nameEntered);
            Log.d("TAG", "amountEntered= " + amountEntered);
            Log.d("TAG", "applied= " + applied);
            Log.d("TAG", "deadlineEntered= " + deadlineEntered);

        } else {
            Scholarship scholarship = new Scholarship(scholarshipName, amount, dateApplied, deadline, announcmentDate, contactInfo, otherNotes);
            viewModel.insertScholarship(scholarship);
            finish();
        }

    }


    //Helper method to hide the keyboard when clicking on the date picker edit text
    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }


}
