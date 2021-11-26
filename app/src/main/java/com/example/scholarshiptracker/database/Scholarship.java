package com.example.scholarshiptracker.database;

import android.text.TextPaint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Implements serializable so I can pass objects between activities
@Entity(tableName = "scholarship_table")
public class Scholarship implements Serializable, Comparable  {

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


    /**
     * Constructor that takes bare minimum attributes for scholarship
     * @param scholarshipName of scholarship
     * @param amount of scholarship
     * @param dateApplied of scholarship
     * @param applicationDeadline of scholarship
     *
     * @throws IllegalArgumentException for invalid paramters
     */
    @Ignore
    public Scholarship(@NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline) {
        this(scholarshipName, amount, dateApplied, applicationDeadline, null, null, null);
    }

    /**
     * Constructor that takes all possible fields (besides a scholarship ID).
     * @param scholarshipName of scholarship
     * @param amount of scholarship
     * @param dateApplied of scholarship
     * @param applicationDeadline of scholarship
     *
     * @throws IllegalArgumentException for invalid parameters
     */
    public Scholarship(@NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline, String expectedResponseDate, String contactInfo, String otherNotes) {
        setScholarshipName(scholarshipName);
        setAmount(amount);
        setDateApplied(dateApplied);
        setApplicationDeadline(applicationDeadline);
        setExpectedResponseDate(expectedResponseDate);
        setContactInfo(contactInfo);
        setOtherNotes(otherNotes);
    }



    //    This constructor has the ID for update queries
    @Ignore
    public Scholarship(int scholarshipID, @NonNull String scholarshipName, double amount, @NonNull String dateApplied, @NonNull String applicationDeadline, String expectedResponseDate, String contactInfo, String otherNotes) {
        this.scholarshipID  = scholarshipID;
        setScholarshipName(scholarshipName);
        setAmount(amount);
        setDateApplied(dateApplied);
        setApplicationDeadline(applicationDeadline);
        setExpectedResponseDate(expectedResponseDate);
        setContactInfo(contactInfo);
        setOtherNotes(otherNotes);
    }


    /**
     * Setter for application deadline
     * @param applicationDeadline you want to set
     * @throws IllegalArgumentException for invalid application deadline
     */
    public void setApplicationDeadline(@NonNull String applicationDeadline) {

        if(applicationDeadline == null || applicationDeadline.isEmpty()) {
            throw new IllegalArgumentException("Deadline is required.");
        }

        this.applicationDeadline = applicationDeadline;
    }

    /**
     * Setter for schlarship name
     * @param scholarshipName you want to set
     * @throws IllegalArgumentException for invalid scholarship name
     */
    public void setScholarshipName(@NonNull String scholarshipName) {
        if(scholarshipName == null || scholarshipName.isEmpty()) {
            throw new IllegalArgumentException("Scholarship needs a name.");
        }

        this.scholarshipName = scholarshipName;
    }

    /**
     * Setter for scholarship amount
     * @param amount you want the scholarship to have
     * @throws IllegalArgumentException for negative or zero amounts
     */
    public void setAmount(double amount) {
        if(amount == 0 || amount < 0 ) {
            throw new IllegalArgumentException("Scholarship needs an amount.");
        }
        this.amount = amount;
    }

    /**
     * Setter for date applied
     * @param dateApplied of scholarship
     * @throws IllegalArgumentException for invalid dates
     */
    public void setDateApplied(@NonNull String dateApplied) {
        if(dateApplied == null || dateApplied.isEmpty()) {
            throw new IllegalArgumentException("Date applied is required.");
        }
        this.dateApplied = dateApplied;
    }

    /**
     * Setter for expected response if response is null or empty response is set to N/A
     * @param expectedResponseDate you want to set
     */
    public void setExpectedResponseDate(String expectedResponseDate) {
        if(expectedResponseDate == null || expectedResponseDate.isEmpty()) {
            this.expectedResponseDate = "N/A";
        } else {
            this.expectedResponseDate = expectedResponseDate;
        }

    }

    /**
     * Setter for contact info if info is null or empty info is set to N/A
     * @param contactInfo you want to set
     */
    public void setContactInfo(String contactInfo) {
        if(contactInfo == null || contactInfo.isEmpty()) {
            this.contactInfo = "N/A";
        } else {
            this.contactInfo = contactInfo;
        }


    }

    /**
     * Setter for scholarships notes if notes is null or empty notes is set to N/A
     * @param otherNotes you want to set
     */
    public void setOtherNotes(String otherNotes) {
        if(otherNotes == null || otherNotes.isEmpty()) {
            this.otherNotes = "N/A";
        } else {
            this.otherNotes = otherNotes;
        }
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


    /**
     * Returns a positive int if the scholarhsip is alphabetically
     * @param o object to be compared on alphebetical basis
     * @return int > 0 for scholarships greater than this.name, returns 0 if object is not scholarship object or scholarships are alphabetically the same
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof Scholarship) {
            Scholarship scholarship = (Scholarship) o;
            if(this.getScholarshipName().compareTo(scholarship.getScholarshipName()) < 0) {
                return -1;
            } else if (this.getScholarshipName().compareTo(scholarship.getScholarshipName()) == 0) {
                return 0;

            } else {
                return 1;
            }

        }
        return 0;
    }
}
