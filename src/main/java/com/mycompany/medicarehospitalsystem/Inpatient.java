/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicarehospitalsystem;

/**
 *
 * @author Student
 */
public class Inpatient extends Patient{
    //the subclass extends the class which is inheritance
    private String wardNumber;
    private String bedNumber;
   
    //adding a constructor using super
    public Inpatient(String patientId, String firstName, String lastName, int age, String gender, String medicalCondition, PatientCategory INPATIENT, String wardNumber, String bedNumber){
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }
    //setters and getters

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }
    //method that displays the displayDetails method using override
    @Override
    public void displayDetails(){
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }
}
