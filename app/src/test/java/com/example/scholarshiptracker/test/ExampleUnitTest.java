package com.example.scholarshiptracker.test;

import com.example.scholarshiptracker.database.Scholarship;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String VALID_SCHOLARSHIP_NAME = "Name";


    

    @Test
    public void valid_Scholarship_Construction() {

        //Testing default minimum scholarship constructor
        Scholarship scholarship = new Scholarship(VALID_SCHOLARSHIP_NAME, 100, "03/18/2002", "09/09/2023" );
        assertEquals(VALID_SCHOLARSHIP_NAME, scholarship.getScholarshipName());
        assertEquals(100, scholarship.getAmount());
        assertEquals("03/18/2002", scholarship.getDateApplied());
        assertEquals("09/09/2023", scholarship.getApplicationDeadline());
    }
}