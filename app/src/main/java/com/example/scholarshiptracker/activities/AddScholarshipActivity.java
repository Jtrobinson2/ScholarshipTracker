package com.example.scholarshiptracker.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.viewmodels.ScholarshipViewModel;
import com.google.android.material.snackbar.Snackbar;

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
    private DatePickerDialog.OnDateSetListener announcementListner;

    private String dateApplied;
    private String deadline;
    private String announcement;


    private static final int DATE_PICKER_APPLIED = 0;
    private static final int DATE_PICKER_DEADLINE = 1;
    private static final int DATE_PICKER_ANNOUNCE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scholarship);

        nameEditText = findViewById(R.id.scholarship_name_edit_text);
        amountEditText = findViewById(R.id.amount_edit_text);
        dateAppliedEditText = findViewById(R.id.date_applied_edit_text);
        deadlineEditText = findViewById(R.id.application_deadline_edit_text);
        announcementEditText = findViewById(R.id.announcement_edit_text);
        contactInfoEditText = findViewById(R.id.contact_info_edit_text);
        otherNotesEditText = findViewById(R.id.other_notes_edit_text);
        submitButton = findViewById(R.id.submit_button);

        dateAppliedEditText.setOnClickListener(view -> {
            showDatePickerDialog(DATE_PICKER_APPLIED);
            Toast.makeText(this, "Date applied clicked", Toast.LENGTH_SHORT).show();
        });

        dateAppliedListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            String chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
            dateAppliedEditText.setText(chosenDate);
        };


        announcementEditText.setOnClickListener(view -> {
            showDatePickerDialog(DATE_PICKER_ANNOUNCE);
            Toast.makeText(this, "announcement clicked", Toast.LENGTH_SHORT).show();
        });

        announcementListner = (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {
            String chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
            announcementEditText.setText(chosenDate);
        };

        deadlineEditText.setOnClickListener(view -> {
            showDatePickerDialog(DATE_PICKER_DEADLINE);
            Toast.makeText(this, "deadline clicked", Toast.LENGTH_SHORT).show();
        });

        deadlineListener = (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {
            String chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
            deadlineEditText.setText(chosenDate);
        };


    }



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
                        announcementListner,
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
            default:
                Toast.makeText(this, "Error with Datepicker", Toast.LENGTH_SHORT).show();;
        }


    }

//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//        chosenDate = monthOfYear + 1 + "/" + dayOfMonth + "/" + year;
//
//    }


}
