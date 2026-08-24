/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.medicarehospitalsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class WardManagerTest {
    private Patient patient;
    private Inpatient inpatient;
    private WardManager manager;

    @BeforeEach
    public void setUp() {

        manager = new WardManager();

        patient = new Patient(
                "P001",
                "John",
                "Doe",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        inpatient = new Inpatient(
                "P002",
                "Jane",
                "Smith",
                45,
                "Female",
                "Pneumonia", PatientCategory.INPATIENT,
                "Ward-A",
                "None"
        );
    }

    // Test registering a patient
    @Test
    public void testRegisterPatientSuccess() {

        boolean result = manager.registerPatient(patient);
       

        assertTrue(result);
        assertEquals(1, manager.getPatientList().size());
    }

    // Test duplicate patient ID
    @Test
    public void testRegisterDuplicatePatient() {

        manager.registerPatient(patient);

        Patient duplicate = new Patient(
                "P001",
                "Mary",
                "Jones",
                25,
                "Female",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        boolean result = manager.registerPatient(duplicate);

        assertFalse(result);
        assertEquals(1, manager.getPatientList().size());
    }

    // Test searching for a patient
    @Test
    public void testSearchPatientSuccess() {

        manager.registerPatient(patient);

        Patient result = manager.searchPatient("P001");

        assertNotNull(result);
        assertEquals("P001", result.getPatientId());
        assertEquals("John", result.getFirstName());
    }

    // Test searching for a patient that does not exist
    @Test
    public void testSearchPatientNotFound() {

        Patient result = manager.searchPatient("P999");

        assertNull(result);
    }

    // Test updating patient details
    @Test
    public void testUpdatePatientSuccess() {

        manager.registerPatient(patient);

        boolean result = manager.updatePatient(
                "P001",
                "Johnny",
                "Doe",
                31,
                "Cold"
        );

        assertTrue(result);

        Patient updated = manager.searchPatient("P001");

        assertEquals("Johnny", updated.getFirstName());
        assertEquals("Doe", updated.getLastName());
        assertEquals(31, updated.getAge());
        assertEquals("Cold", updated.getMedicalCondition());
    }

    // Test updating a patient that does not exist
    @Test
    public void testUpdatePatientNotFound() {

        boolean result = manager.updatePatient(
                "P999",
                "Test",
                "Patient",
                20,
                "Test"
        );

        assertFalse(result);
    }

    // Test deleting a patient
    @Test
    public void testDeletePatientSuccess() {

        manager.registerPatient(patient);

        boolean result = manager.deletePatient("P001");

        assertTrue(result);
        assertNull(manager.searchPatient("P001"));
        assertEquals(0, manager.getPatientList().size());
    }

    // Test deleting a patient that does not exist
    @Test
    public void testDeletePatientNotFound() {

        boolean result = manager.deletePatient("P999");

        assertFalse(result);
    }

    // Test allocating a bed
    @Test
    public void testAllocateBedSuccess() {

        manager.registerPatient(inpatient);

        boolean result = manager.allocateBed(
                inpatient,
                "B01"
        );

        assertTrue(result);
        assertEquals("B01", inpatient.getBedNumber());
    }

    // Test allocating a second patient to the same bed
    @Test
    public void testAllocateAlreadyOccupiedBed() {

        manager.registerPatient(inpatient);

        boolean firstAllocation =
                manager.allocateBed(inpatient, "B01");

        assertTrue(firstAllocation);

        Inpatient secondInpatient = new Inpatient(
                "P003",
                "Bob",
                "White",
                50,
                "Male",
                "Observation", PatientCategory.INPATIENT,
                "Ward-A",
                "None"
        );

        manager.registerPatient(secondInpatient);

        boolean secondAllocation =
                manager.allocateBed(
                        secondInpatient,
                        "B01"
                );

        assertFalse(secondAllocation);
        assertEquals("None", secondInpatient.getBedNumber());
    }

    // Test that an inpatient cannot receive a second bed
    @Test
    public void testPatientAlreadyHasBed() {

        manager.registerPatient(inpatient);

        manager.allocateBed(inpatient, "B01");

        boolean result =
                manager.allocateBed(inpatient, "B02");

        assertFalse(result);
        assertEquals("B01", inpatient.getBedNumber());
    }

    // Test invalid bed number
    @Test
    public void testInvalidBedNumber() {

        manager.registerPatient(inpatient);

        boolean result =
                manager.allocateBed(inpatient, "B99");

        assertFalse(result);
        assertEquals("None", inpatient.getBedNumber());
    }

    // Test releasing a bed
    @Test
    public void testReleaseBedSuccess() {

        manager.registerPatient(inpatient);

        manager.allocateBed(inpatient, "B01");

        boolean result =
                manager.releaseBed("B01");

        assertTrue(result);
        assertEquals("None", inpatient.getBedNumber());
    }

    // Test releasing a bed that is not occupied
    @Test
    public void testReleaseUnoccupiedBed() {

        manager.registerPatient(inpatient);

        boolean result =
                manager.releaseBed("B01");

        assertFalse(result);
    }

    // Test sorting patients by ID
    @Test
    public void testSortPatientsById() {

        Patient patient1 = new Patient(
                "P003",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Mary",
                "Jones",
                25,
                "Female",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        Patient patient3 = new Patient(
                "P002",
                "Peter",
                "Brown",
                40,
                "Male",
                "Fever",
                PatientCategory.OUTPATIENT
        );

        manager.registerPatient(patient1);
        manager.registerPatient(patient2);
        manager.registerPatient(patient3);

        manager.sortPatientsById();

        assertEquals(
                "P001",
                manager.getPatientList().get(0).getPatientId()
        );

        assertEquals(
                "P002",
                manager.getPatientList().get(1).getPatientId()
        );

        assertEquals(
                "P003",
                manager.getPatientList().get(2).getPatientId()
        );
    }
    
}
