package com.example.scholarshiptracker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Scholarship.class}, version = 1, exportSchema = false)
public abstract class ScholarshipsDatabase extends RoomDatabase {
    public abstract ScholarshipsDAO scholarshipsDAO();

    //  volatile guarantees visibility of changes to variables across threads.
    private static volatile ScholarshipsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Synchronized keyword makes sure that only one thread can access a variable or function at a time.
    public static ScholarshipsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScholarshipsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScholarshipsDatabase.class, "scholarship_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}


