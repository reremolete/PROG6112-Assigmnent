/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicarehospitalsystem;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Student
 */
public class WardManager {
    //Creating an array list
    private ArrayList<Patient> patientList = new ArrayList<>();
   
    //Creating a 2D array of the bed management
    private String[][] bedLayout = {{"B01", "B02", "B03", "B04", "B05"},
    {"B06", "B07", "B08", "B09", "B10"},
    {"B11", "B12", "B13", "B14", "B15"},
    {"B16", "B17", "B18", "B19", "B20"}};
   
    public WardManager(){
        //using nested loop to fill all 20 beds
        //using length field
        int count = 1;
       
        for(int r = 0; r < bedLayout.length; r++){
            for (int c = 0; c < bedLayout[r].length; c++){
                if (count < 10){
                    bedLayout[r][c] = "B0" + count;
                }else{
                    bedLayout[r][c] = "B" + count;
                }
                count++;
            }
        }
    }
    //Patient management
    //Registering the new patientm using ID verification process
    public boolean registerPatient(Patient patient){
        if (searchPatient(patient.getPatientId()) != null){
            //Id a duplicate ID is found then
            return false;
        }
        patientList.add(patient);
        return true;
    }

    // Search for a patient using their patient ID
    public Patient searchPatient(String patientId){
        for (int i = 0; i < patientList.size(); i++){
            if (patientList.get(i).getPatientId().equalsIgnoreCase(patientId)){
                return patientList.get(i);
            }
        }
        return null;
    }

    // Update an existing patients details
    public boolean updatePatient(String id, String newFirstName, String newLastName, int newAge, String newCondition){
        Patient p = searchPatient(id);
        if (p != null) {
            p.setFirstName(newFirstName);
            p.setLastName(newLastName);
            p.setAge(newAge);
            p.setMedicalCondition(newCondition);
            return true;
        }
        //If the existing patients Id is incorrect then
        return false;
    }

    // A method deleting Patients Record
    public boolean deletePatient(String patientId){
        //search if if the patient is on the hospitals list
        Patient p = searchPatient(patientId);
        if (p != null){
            // If the inpatient has a bed assigned to them the relsease their assigned bed back to the ward
            if (p instanceof Inpatient){
                Inpatient inp = (Inpatient) p;
                if (!inp.getBedNumber().equals("None")){
                    releaseBed(inp.getBedNumber());
                }
            }
            //remove the patient from the list
            patientList.remove(p);
            return true;
        }
        return false;
    }

    //Bed Management
    // Display Ward Layout using nested loops
    public void displayWardLayout(){
        System.out.println(" Ward Bed Layout (4x5)");
        for (int r = 0; r < bedLayout.length; r++){
            for (int c = 0; c < bedLayout[r].length; c++){
                System.out.print("[" + bedLayout[r][c] + "]\t");
            }
            System.out.println();
        }
    }

    //A method to allocate Bed (Validation prevents allocated/occupied beds)
    public boolean allocateBed(Inpatient patient, String bedNumber){
        if (!patient.getBedNumber().equals("None")){
            // Already has a bed then
            return false;
        }
        //checks if the bed matches the bed number and that it is not occuppied
        for (int r = 0; r < bedLayout.length; r++){
            for (int c = 0; c < bedLayout[r].length; c++){
                //Gives the patient a bed and the allocation becomes a success
                if (bedLayout[r][c].equalsIgnoreCase(bedNumber) && !bedLayout[r][c].equals("OCCUPIED")){
                    bedLayout[r][c] = "OCCUPIED";
                    patient.setBedNumber(bedNumber);
                    return true;
                }
            }
        }
        // Bed not available or invalid
        return false;
    }

    // Releases the bed so that another patient can make use of the bed
    public boolean releaseBed(String bedNumber){
        for (int r = 0; r < bedLayout.length; r++){
            for (int c = 0; c < bedLayout[r].length; c++){
                // If we match bed position, reset label
                int bedIndex = (r * 5) + (c + 1);
                String defaultCode = (bedIndex < 10) ? "B0" + bedIndex : "B" + bedIndex;
               
                // Find patient tied to this bed to releases them
                for (Patient p : patientList){
                    if (p instanceof Inpatient){
                        Inpatient inp = (Inpatient) p;
                        if (inp.getBedNumber().equalsIgnoreCase(bedNumber)){
                            //changes the bed occupancy to none
                            inp.setBedNumber("None");
                            bedLayout[r][c] = defaultCode;
                            return true;
                        }
                    }
                }
            }
        }
        //if the bed Number was not assigned to any patient then
        return false;
    }

    //Reports
    public void sortPatientsById(){
        Collections.sort(patientList, new Comparator<Patient>(){
            @Override
            public int compare(Patient p1, Patient p2){
                return p1.getPatientId().compareToIgnoreCase(p2.getPatientId());
            }
        });
    }

    // Generate Ward Occupancy and Patient Summary Report
    public void generateReport(){
        int occupiedCount = 0;
        for (int r = 0; r < bedLayout.length; r++){
            for (int c = 0; c < bedLayout[r].length; c++){
                if (bedLayout[r][c].equals("OCCUPIED")){
                    occupiedCount++;
                }
            }
        }
        int totalBeds = 20;
        int availableBeds = totalBeds - occupiedCount;
        double occupancyPercentage = ((double) occupiedCount / totalBeds) * 100;

        System.out.println("===WARD OCCUPANCY REPORT===");
        System.out.println("Total Registered Patients : " + patientList.size());
        System.out.println("Total Occupied Beds       : " + occupiedCount);
        System.out.println("Total Available Beds      : " + availableBeds);
        System.out.printf("Ward Occupancy Percentage : %.2f%%n", occupancyPercentage);
    }

    public ArrayList<Patient> getPatientList(){return patientList;}


}
