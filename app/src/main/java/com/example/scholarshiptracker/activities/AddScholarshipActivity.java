package com.example.scholarshiptracker.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import de.mateware.snacky.Snacky;
import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class AddScholarshipActivity extends AppCompatActivity {

    //    Constants used to identify different dialogs and onDateListeners
    private static final int DATE_PICKER_APPLIED = 0;
    private static final int DATE_PICKER_DEADLINE = 1;
    private static final int DATE_PICKER_ANNOUNCE = 2;
    private ScholarshipViewModel viewModel;
    private EditText nameEditText;
    private CurrencyEditText amountEditText;
    private EditText dateAppliedEditText;
    private EditText deadlineEditText;
    private EditText announcementEditText;
    private EditText contactInfoEditText;
    private EditText otherNotesEditText;
    private Button submitButton;
    private ImageButton infoButton;
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

    public static void showAnnounceInfoDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Announcement date is the day you expect the scholarship to announce the winners");
        builder.setTitle("Announcement Date");
        builder.setCancelable(true);


        builder.setPositiveButton(
                "Ok",
                (dialog, id) -> {
                    dialog.dismiss();

                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showErrorSnackbar(Activity activity, String errorMessage) {
        Snacky.builder()
                .setActivity(activity)
                .setText(errorMessage)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    public static void showUpdatedSuccessSnackbar(Activity activity) {
        Snacky.builder()
                .setActivity(activity)
                .setText("Update Successful")
                .setIcon(R.drawable.ic_baseline_check_white_24)
                .setDuration(Snacky.LENGTH_SHORT)
                .success()
                .show();

    }

    public static void showAdditionSuccessSnackbar(Activity activity) {
        Snacky.builder()
                .setActivity(activity)
                .setText("Scholarship Added")
                .setIcon(R.drawable.ic_baseline_attach_money_white24)
                .setDuration(Snacky.LENGTH_SHORT)
                .success()
                .show();

    }

    public static void showDeletionSnackbar(Activity activity) {
        Snacky.builder()
                .setActivity(activity)
                .setText("Deleted Successfully")
                .setIcon(R.drawable.ic_baseline_delete_white_24)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scholarship);

//        Initializing ViewModel
        viewModel = ViewModelProviders.of(this).get(ScholarshipViewModel.class);

        nameEditText = findViewById(R.id.scholarship_name_edit_text);


        amountEditText = findViewById(R.id.amount_edit_text);
//        This symbol is only for USA users only haven't figured out how to change it dynamically
        amountEditText.setCurrency(CurrencySymbols.USA);
        amountEditText.setSpacing(false);
        amountEditText.setDecimals(true);


        dateAppliedEditText = findViewById(R.id.date_applied_edit_text);
        deadlineEditText = findViewById(R.id.application_deadline_edit_text);
        announcementEditText = findViewById(R.id.announcement_edit_text);
        contactInfoEditText = findViewById(R.id.contact_info_edit_text);
        otherNotesEditText = findViewById(R.id.other_notes_edit_text);

        infoButton = findViewById(R.id.info_button);
        submitButton = findViewById(R.id.submit_button);

        nameTextInputLayout = findViewById(R.id.name_input_layout);
        dateAppliedTextInputLayout = findViewById(R.id.date_applied_input_layout);
        otherNotesTextInputLayout = findViewById(R.id.other_notes_input_layout);
        deadlineTextInputLayout = findViewById(R.id.deadline_input_layout);
        announcementTextInputLayout = findViewById(R.id.announcement_input_layout);
        contactInfoTextInputLayout = findViewById(R.id.contact_input_layout);
        amountTextInputLayout = findViewById(R.id.amount_input_layout);


        infoButton.setOnClickListener(view -> {
            showAnnounceInfoDialog(this);
        });

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
        Scholarship scholarship = null;


        if (!validateScholarshipName() | !validateScholarshipAmount() | !validateDateApplied() | !validateDeadlineEntered()) {
            showErrorSnackbar(this, "Error");
        } else {
            dateApplied = dateAppliedEditText.getText().toString();
            deadline = deadlineEditText.getText().toString();
            amount = amountEditText.getCleanDoubleValue();
            scholarshipName = nameEditText.getText().toString();
            announcmentDate = announcementEditText.getText().toString();
            contactInfo = contactInfoEditText.getText().toString();
            otherNotes = otherNotesEditText.getText().toString();

            //Data should be validated up until this point but invalid data could still throw an exception
            try {
                 scholarship = new Scholarship(scholarshipName, amount, dateApplied, deadline, announcmentDate, contactInfo, otherNotes);
            } catch(IllegalArgumentException e) {
                showErrorSnackbar(this, e.getMessage());
            }

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

    //Data validation method for edit texts
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

    //Data validation method for edit texts//Data validation method for edit texts
    private boolean validateScholarshipAmount() {
        if (amountEditText.getText().toString().isEmpty()) {
            amountTextInputLayout.setError("Scholarship needs an amount");
            return false;
        } else if (!isPositiveNumber(String.valueOf(amountEditText.getCleanDoubleValue()))) {
            amountTextInputLayout.setError("Lets not go into debt here");
            return false;
        } else {
            amountTextInputLayout.setError(null);
            amountTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    //Data validation method for edit texts
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

    //Data validation method for edit texts
    private boolean validateDeadlineEntered() {
        if (deadlineEditText.getText().toString().isEmpty()) {
            deadlineTextInputLayout.setError("Deadline is required.");
            return false;
        } else {
            deadlineTextInputLayout.setError(null);
            deadlineTextInputLayout.setErrorEnabled(false);
            return true;

        }
    }
}
