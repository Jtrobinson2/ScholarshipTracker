package com.example.scholarshiptracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scholarshiptracker.database.Scholarship;
import com.example.scholarshiptracker.repositories.ScholarshipRepository;

import java.util.List;


public class ScholarshipViewModel extends AndroidViewModel {

    private ScholarshipRepository repository;

    private LiveData<List<Scholarship>> scholarships;

    public ScholarshipViewModel(Application application) {
        super(application);
        repository = new ScholarshipRepository(this.getApplication());
        scholarships = repository.getAllScholarships();
    }

    public void insertScholarship(Scholarship scholarship) {
        repository.insert(scholarship);
    }

    public LiveData<List<Scholarship>> getAllScholarships() {
        return scholarships;
    }
}
