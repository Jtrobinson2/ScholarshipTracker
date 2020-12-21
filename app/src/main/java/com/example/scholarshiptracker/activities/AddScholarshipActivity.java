package com.example.scholarshiptracker.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.textfield.TextInputLayout;

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

    //    Alt-J to select multiple layouts
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout amountTextInputLayout;
    private TextInputLayout dateAppliedTextInputLayout;
    private TextInputLayout deadlineTextInputLayout;
    private TextInputLayout announcementTextInputLayout;
    private TextInputLayout contactInfoTextInputLayout;
    private TextInputLayout otherNotesTextInputLayout;


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

        nameTextInputLayout = findViewById(R.id.name_input_layout);
        dateAppliedTextInputLayout = findViewById(R.id.date_applied_input_layout);
        otherNotesTextInputLayout = findViewById(R.id.other_notes_input_layout);
        deadlineTextInputLayout = findViewById(R.id.deadline_input_layout);
        announcementTextInputLayout = findViewById(R.id.announcement_input_layout);
        contactInfoTextInputLayout = findViewById(R.id.contact_input_layout);
        amountTextInputLayout = findViewById(R.id.amount_input_layout);


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
        double amount = 0.00;
        String contactInfo = "";
        String otherNotes = "";


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

        if ((!validateScholarshipName() | !validateScholarshipAmount() | !validateDateApplied() | !validateDeadlineEntered())) {
            Toast.makeText(this, "All required fields not entered correctly", Toast.LENGTH_LONG).show();
        } else {
            dateApplied = dateAppliedEditText.getText().toString();
            deadline = deadlineEditText.getText().toString();
            amount = Double.parseDouble(amountEditText.getText().toString());
            scholarshipName = nameEditText.getText().toString();

            Scholarship scholarship = new Scholarship(scholarshipName, amount, dateApplied, deadline, announcmentDate, contactInfo, otherNotes);
            viewModel.insertScholarship(scholarship);
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
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
    private void showUnsavedChangedDialog(@NonNull Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to exit?");
        builder.setTitle("Unsaved Changes");
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


    /*
     * On back pressed returns the recyclerview to the list item you were looking at
     * therefore I want the home button to do the same
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        showUnsavedChangedDialog(this);
    }

    //  Helper method to determine if a number is positive or an number in the first place
    public static boolean isPositiveNumber(String input) {
        try {
            double integer = Double.parseDouble(input);
            if (integer <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            return false;
        }


    }

    private boolean validateScholarshipName() {
        if (nameEditText.getText().toString().isEmpty()) {
            nameTextInputLayout.setError("Scholarship needs a name");
            return false;
        } else {
            nameTextInputLayout.setError(null);
            nameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateScholarshipAmount() {
        if (amountEditText.getText().toString().isEmpty()) {
            amountTextInputLayout.setError("Scholarship needs an amount");
            return false;
        } else if (!isPositiveNumber(amountEditText.getText().toString())) {
            amountTextInputLayout.setError("Lets not go into debt here");
            return false;
        } else {
            amountTextInputLayout.setError(null);
            amountTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDateApplied() {
        if (dateAppliedEditText.getText().toString().isEmpty()) {
            dateAppliedTextInputLayout.setError("Date applied is required");
            return false;
        } else {
            dateAppliedTextInputLayout.setError(null);
            dateAppliedTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDeadlineEntered() {
        if (deadlineEditText.getText().toString().isEmpty()) {
            deadlineTextInputLayout.setError("Deadline is required");
            return false;
        } else {
            deadlineTextInputLayout.setError(null);
            deadlineTextInputLayout.setErrorEnabled(false);
            return true;

        }
    }
}
