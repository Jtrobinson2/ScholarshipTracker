package com.example.scholarshiptracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scholarshiptracker.R;
import com.example.scholarshiptracker.database.Scholarship;

public class DetailActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView amountTextView;
    private TextView dateAppliedTextView;
    private TextView deadlineTextView;
    private TextView announcementTextView;
    private TextView contactInfoTextView;
    private TextView otherNotesTextView;
    private Scholarship recievedScholarship;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTextView = findViewById(R.id.name_text_view);
        amountTextView = findViewById(R.id.amount_text_view);
        dateAppliedTextView = findViewById(R.id.date_applied_text_view);
        deadlineTextView = findViewById(R.id.application_deadline_text_view);
        announcementTextView = findViewById(R.id.announcement_text_view);
        contactInfoTextView = findViewById(R.id.contact_info_text_view);
        otherNotesTextView = findViewById(R.id.other_notes_text_view);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            recievedScholarship = (Scholarship) intent.getSerializableExtra("Scholar");
            nameTextView.setText(recievedScholarship.getScholarshipName());
            amountTextView.setText(String.valueOf(recievedScholarship.getAmount()));
            dateAppliedTextView.setText(recievedScholarship.getDateApplied());
            deadlineTextView.setText(recievedScholarship.getApplicationDeadline());
            announcementTextView.setText(recievedScholarship.getExpectedResponseDate());
            contactInfoTextView.setText(recievedScholarship.getExpectedResponseDate());
            otherNotesTextView.setText(recievedScholarship.getOtherNotes());
        }

    }
}