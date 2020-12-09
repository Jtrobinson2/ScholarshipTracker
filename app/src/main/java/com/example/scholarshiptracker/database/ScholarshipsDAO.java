package com.example.scholarshiptracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScholarshipsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addScholarship(Scholarship scholarship);

    @Query("SELECT * FROM scholarship_table ORDER BY scholarshipID ASC")
    public LiveData<List<Scholarship>> readAllScholarships();

    @Delete
    public void deleteScholarship(Scholarship scholarship);

    @Query("DELETE FROM scholarship_table")
    public void deleteAllScholarships();


    @Update
    public void updateScholarship(Scholarship scholarship);


}
