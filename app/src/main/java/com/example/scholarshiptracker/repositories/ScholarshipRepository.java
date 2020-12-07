package com.example.scholarshiptracker.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.database.ScholarshipsDAO;
import com.example.scholarshiptracker.database.ScholarshipsDatabase;

import java.util.List;

public class ScholarshipRepository {
    private ScholarshipsDAO scholarshipDAO;
    private LiveData<List<Scholarship>> scholarships;
    private ScholarshipsDatabase database;

    public ScholarshipRepository(Application application) {
        database = ScholarshipsDatabase.getDatabase(application);
        scholarshipDAO = database.scholarshipsDAO();
        scholarships = scholarshipDAO.readAllScholarships();
    }

    public LiveData<List<Scholarship>> getAllScholarships() {
        return scholarships;
    }

    public void insert(Scholarship scholarship) {
        ScholarshipsDatabase.databaseWriteExecutor.execute(() -> {
            scholarshipDAO.addScholarship(scholarship);
        });

    }

    public void deleteAllScholarships() {

        ScholarshipsDatabase.databaseWriteExecutor.execute(() -> {
            scholarshipDAO.deleteAllScholarships();
        });
    }

    public void deleteScholarshp(Scholarship scholarship) {
        ScholarshipsDatabase.databaseWriteExecutor.execute(() -> scholarshipDAO.deleteScholarship(scholarship));

    }

}
