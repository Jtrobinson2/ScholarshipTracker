package com.example.scholarshiptracker.test;

import com.example.scholarshiptracker.database.Scholarship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Test class for scholarhsip pojo
 */
public class ScholarshipTest {
    //Below are string constants to be used to construct valid scholarships
    private static final double VALID_AMOUNT = 10.00;
    private static final String VALID_NAME = "Name";
    private static final String VALID_DATE_APPLIED = "03/18/2002";
    private static final String VALID_APPLICATION_DEADLINE = "03/18/2002";
    private static final String VALID_ANNOUNCEMENT_DATE = "03/18/2002";
    private static final String VALID_CONTACT_INFO = "jmoney@gmail.com";
    private static final String VALID_OTHER_NOTES = "jmoney@gmail.com";
    private static final int VALID_SCHOLARSHIP_ID = 1;
    //Scholarship to be re-used in tests
    private Scholarship scholarship;

    //Setup method for making a fresh scholarship with the minimal data needed in the smallest constructor
    @BeforeEach
    void setUp() {
        scholarship = null;
        scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE);
    }

    /*
    This method passively tests many getter and setter methods in the pojo
     */
    @Test
    void testScholarshipConstructor() {
        //Test creating a scholarship with null name
        Exception e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(null, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE), "Scholarship with null name was allowed.");
        assertEquals("Scholarship needs a name.", e.getMessage());

        //Test creating a scholarship with empty name
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship("", VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE), "Scholarship with empty name was allowed.");
        assertEquals("Scholarship needs a name.", e.getMessage());

        //Test creating a scholarship with 0 amount
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(VALID_NAME, 0, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE), "Scholarship with 0 amount was allowed.");
        assertEquals("Scholarship needs an amount.", e.getMessage());

        //Test creating a scholarship with negative amount
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(VALID_NAME, -1, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE), "Scholarship with negative amount was allowed.");
        assertEquals("Scholarship needs an amount.", e.getMessage());

        //Test creating a scholarship with emtpy date applied
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, "", VALID_APPLICATION_DEADLINE), "Scholarship with empty date was allowed.");
        assertEquals("Date applied is required.", e.getMessage());

        //Test creating a scholarship with null date applied
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, null, VALID_APPLICATION_DEADLINE), "Scholarship with null date was allowed.");
        assertEquals("Date applied is required.", e.getMessage());


        //Test creating a scholarship with empty application deadline
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, ""), "Scholarship with empty date was allowed.");
        assertEquals("Deadline is required.", e.getMessage());

        //Test creating a scholarship with null application deadline
        e = assertThrows(IllegalArgumentException.class, () -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, null), "Scholarship with null date was allowed.");
        assertEquals("Deadline is required.", e.getMessage());

        //Test adding valid scholarship with minimum criteria has the leftover fields set to N/A
        assertDoesNotThrow(() -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE));

        //Check fields
        assertEquals(VALID_NAME, scholarship.getScholarshipName());

        assertEquals(VALID_AMOUNT, scholarship.getAmount());

        assertEquals(VALID_DATE_APPLIED, scholarship.getDateApplied());

        assertEquals(VALID_APPLICATION_DEADLINE, scholarship.getApplicationDeadline());

        //Check the fields that should be N/A
        assertEquals("N/A", scholarship.getExpectedResponseDate());

        assertEquals("N/A", scholarship.getContactInfo());

        assertEquals("N/A", scholarship.getOtherNotes());

        //Test adding valid scholarships with just contact info added
        assertDoesNotThrow(() -> assertDoesNotThrow(() -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE, "", VALID_CONTACT_INFO, "")));

        //Check the fields that should be N/A
        assertEquals("N/A", scholarship.getExpectedResponseDate());

        assertEquals(VALID_CONTACT_INFO, scholarship.getContactInfo());

        assertEquals("N/A", scholarship.getOtherNotes());

        //Test adding valid scholarship with just annoncement date added
        assertDoesNotThrow(() -> assertDoesNotThrow(() -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE, VALID_ANNOUNCEMENT_DATE, "", "")));

        //Check the fields that should be N/A
        assertEquals(VALID_ANNOUNCEMENT_DATE, scholarship.getExpectedResponseDate());

        assertEquals("N/A", scholarship.getContactInfo());

        assertEquals("N/A", scholarship.getOtherNotes());

        //Test adding valid scholarship with just other notes added
        assertDoesNotThrow(() -> assertDoesNotThrow(() -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE, "", "", VALID_OTHER_NOTES)));

        //Check the fields that should be N/A
        assertEquals("N/A", scholarship.getExpectedResponseDate());

        assertEquals("N/A", scholarship.getContactInfo());

        assertEquals(VALID_OTHER_NOTES, scholarship.getOtherNotes());


        //Test adding valid scholarship with scholarship ID specified during constrcution
        assertDoesNotThrow(() -> scholarship = new Scholarship(VALID_SCHOLARSHIP_ID, VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE, VALID_ANNOUNCEMENT_DATE, VALID_CONTACT_INFO, VALID_OTHER_NOTES));

        //Check fields
        assertEquals(VALID_SCHOLARSHIP_ID, scholarship.getScholarshipID());

        assertEquals(VALID_NAME, scholarship.getScholarshipName());

        assertEquals(VALID_AMOUNT, scholarship.getAmount());

        assertEquals(VALID_DATE_APPLIED, scholarship.getDateApplied());

        assertEquals(VALID_APPLICATION_DEADLINE, scholarship.getApplicationDeadline());

        assertEquals(VALID_ANNOUNCEMENT_DATE, scholarship.getExpectedResponseDate());

        assertEquals(VALID_CONTACT_INFO, scholarship.getContactInfo());

        assertEquals(VALID_OTHER_NOTES, scholarship.getOtherNotes());

        //Test adding valid scholarship (no scholarship ID), with all possible attributes
        assertDoesNotThrow(() -> scholarship = new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE, VALID_ANNOUNCEMENT_DATE, VALID_CONTACT_INFO, VALID_OTHER_NOTES));

        //Check fields
        assertEquals(VALID_NAME, scholarship.getScholarshipName());

        assertEquals(VALID_AMOUNT, scholarship.getAmount());

        assertEquals(VALID_DATE_APPLIED, scholarship.getDateApplied());

        assertEquals(VALID_APPLICATION_DEADLINE, scholarship.getApplicationDeadline());

        assertEquals(VALID_ANNOUNCEMENT_DATE, scholarship.getExpectedResponseDate());

        assertEquals(VALID_CONTACT_INFO, scholarship.getContactInfo());

        assertEquals(VALID_OTHER_NOTES, scholarship.getOtherNotes());
    }


    @Test
    void testCompareTo() {
        //Test a scholarship ahead of the default
        assertTrue(scholarship.compareTo(new Scholarship("Opportunity", VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE)) < 0);

        //Test a scholarship before the default
        assertTrue(scholarship.compareTo(new Scholarship("Apple", VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE)) > 0);

        //Test a scholarship equal to the default
        assertTrue(scholarship.compareTo(new Scholarship(VALID_NAME, VALID_AMOUNT, VALID_DATE_APPLIED, VALID_APPLICATION_DEADLINE)) == 0);
    }
}