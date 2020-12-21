package com.example.scholarshiptracker.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
//Implements serializable so I can pass objects between activities
@Entity(tableName = "scholarship_table")
public class Scholarship implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int scholarshipID;

    @ColumnInfo(name = "name")
    @NonNull
    private String scholarshipName;

    @ColumnInfo(name = "amount")
    @NonNull
    private double amount;

    @ColumnInfo(name = "date_applied")
    @NonNull
    private String dateApplied;

    @ColumnInfo(name = "application_deadline")
    @NonNull
    private String applicationDeadline;

    @ColumnInfo(name = "expected_response")
    private String expectedResponseDate;

    @ColumnInfo(name = "contact_info")
    private String contactInfo;

    @ColumnInfo(name = "other_notes")
    private String otherNotes;


    @Ignore
    public Scholarship(@NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline) {
        this.scholarshipName = scholarshipName;
        this.amount = amount;
        this.dateApplied = dateApplied;
        this.applicationDeadline = applicationDeadline;
    }

    @Ignore
    public Scholarship(@NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline, String expectedResponseDate) {
        this.scholarshipName = scholarshipName;
        this.amount = amount;
        this.dateApplied = dateApplied;
        this.applicationDeadline = applicationDeadline;
        this.expectedResponseDate = expectedResponseDate;
    }

    public Scholarship(@NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline, String expectedResponseDate, String contactInfo) {
        this.scholarshipName = scholarshipName;
        this.amount = amount;
        this.dateApplied = dateApplied;
        this.applicationDeadline = applicationDeadline;
        this.expectedResponseDate = expectedResponseDate;
        this.contactInfo = contactInfo;
    }

//    This constructor has the ID for update queries
    @Ignore
    public Scholarship(int scholarshipID, @NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline, String expectedResponseDate, String contactInfo, String otherNotes) {
        this.scholarshipID = scholarshipID;
        this.scholarshipName = scholarshipName;
        this.amount = amount;
        this.dateApplied = dateApplied;
        this.applicationDeadline = applicationDeadline;
        this.expectedResponseDate = expectedResponseDate;
        this.contactInfo = contactInfo;
        this.otherNotes = otherNotes;
    }

    @Ignore
    public Scholarship(@NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline, String expectedResponseDate, String contactInfo, String otherNotes) {
        this.scholarshipName = scholarshipName;
        this.amount = amount;
        this.dateApplied = dateApplied;
        this.applicationDeadline = applicationDeadline;
        this.expectedResponseDate = expectedResponseDate;
        this.contactInfo = contactInfo;
        this.otherNotes = otherNotes;
    }

    public void setApplicationDeadline(@NonNull String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public void setScholarshipName(@NonNull String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateApplied(@NonNull String dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void setExpectedResponseDate(String expectedResponseDate) {
        this.expectedResponseDate = expectedResponseDate;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public int getScholarshipID() {
        return scholarshipID;
    }

    @NonNull
    public String getScholarshipName() {
        return scholarshipName;
    }

    public double getAmount() {
        return amount;
    }

    @NonNull
    public String getDateApplied() {
        return dateApplied;
    }

    @NonNull
    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public String getExpectedResponseDate() {
        return expectedResponseDate;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getOtherNotes() {
        return otherNotes;
    }
}
