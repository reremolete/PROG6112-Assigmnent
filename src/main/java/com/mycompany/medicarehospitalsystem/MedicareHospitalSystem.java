/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.medicarehospitalsystem;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class MedicareHospitalSystem {

    public static void main(String[] args) {
        //Instantiate this to manage the patients records
        WardManager manager = new WardManager();
        Scanner sc = new Scanner(System.in);
        //controls the main menu in the main class
        boolean running = true;
       

        while (running) {
            //Loop that allows the user to interact with the menu options
            System.out.println("===MEDICARE HOSPITAL PATIENT ADMISSION SYSTEM===");
            System.out.println("1. Register New Patient");
            System.out.println("2. Search Patient Record");
            System.out.println("3. Update Patient Details");
            System.out.println("4. Delete Patient Record");
            System.out.println("5. Display All Patients (Sorted by ID)");
            System.out.println("6. Display 2D Ward Bed Layout");
            System.out.println("7. Allocate Bed to Inpatient");
            System.out.println("8. Release Bed from Inpatient");
            System.out.println("9. Generate Ward Occupancy Report");
            System.out.println("10. Exit Application");
            System.out.print("Enter choice (1-10): ");

            int choice = sc.nextInt();
            sc.nextLine();
           
            //what responds to the user depending on the menu option they chose
            switch (choice) {
                //Registeringthe new patient menu option
                case 1:
                    System.out.print("Enter Patient ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter First Name: ");
                    String fn = sc.nextLine();
                    System.out.print("Enter Last Name: ");
                    String ln = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Gender: ");
                    String gen = sc.nextLine();
                    System.out.print("Enter Medical Condition: ");
                    String cond = sc.nextLine();
                   
                    //Prompts the user to select a particular category from the menu options
                    System.out.println("Select Category (1. INPATIENT, 2. OUTPATIENT, 3. EMERGENCY): ");
                    int catChoice = sc.nextInt();
                    sc.nextLine();

                    //If the user selects option 1 from then in registers them as an inpatient
                    if (catChoice == 1) {
                        Inpatient inp = new Inpatient(id, fn, ln, age, gen, cond, PatientCategory.INPATIENT, "Ward-A", "None");
                        if (manager.registerPatient(inp)) {
                            System.out.println("Inpatient registered successfully.");
                        } else {
                            System.out.println("Error: Duplicate Patient ID detected!");
                        }
                        //If not it registers them as the other 2 options
                    } else {
                        PatientCategory cat = (catChoice == 2) ? PatientCategory.OUTPATIENT : PatientCategory.EMERGENCY;
                        Patient p = new Patient(id, fn, ln, age, gen, cond, cat);
                        if (manager.registerPatient(p)) {
                            System.out.println("Patient registered successfully.");
                        } else {
                            System.out.println("Error: Duplicate Patient ID detected!");
                        }
                    }
                    break;
               
                //Searches if the patient has been on record in the hospital
                case 2:
                    System.out.print("Enter Patient ID to search: ");
                    Patient pSearch = manager.searchPatient(sc.nextLine());
                    if (pSearch != null) {
                        System.out.println("Patient Found: ");
                        pSearch.displayDetails();
                    } else {
                        System.out.println("Patient not found!");
                    }
                    break;
                   
                //This updates the patients records    
                case 3:
                    System.out.print("Enter Patient ID to update: ");
                    String Id = sc.nextLine();
                    System.out.print("Enter New First Name: ");
                    String Fn = sc.nextLine();
                    System.out.print("Enter New Last Name: ");
                    String Ln = sc.nextLine();
                    System.out.print("Enter New Age: ");
                    int Age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Condition: ");
                    String Cond = sc.nextLine();

                    if (manager.updatePatient(Id, Fn, Ln, Age, Cond)) {
                        System.out.println("Patient details updated!");
                    } else {
                        System.out.println("Patient record not found.");
                    }
                    break;

                //Deletes the patients details to free the bed that was registeredunder them
                case 4:
                    System.out.print("Enter Patient ID to delete: ");
                    if (manager.deletePatient(sc.nextLine())) {
                        System.out.println("Patient record removed.");
                    } else {
                        System.out.println("Patient record not found.");
                    }
                    break;

                //Displays all the registered patients records
                case 5:
                    manager.sortPatientsById();
                    System.out.println("\n All Registered Patients (Sorted by ID) ");
                    for (Patient p : manager.getPatientList()) {
                        p.displayDetails();
                    }
                    break;

                //Displays the representation of the ward beds layout
                case 6:
                    manager.displayWardLayout();
                    break;

                //This one Allocates a bed to an inpatient
                case 7:
                    System.out.print("Enter Inpatient ID for Allocation: ");
                    Patient pAlloc = manager.searchPatient(sc.nextLine());
                    if (pAlloc instanceof Inpatient) {
                        manager.displayWardLayout();
                        System.out.print("Enter Bed Number to Allocate (e.g., B01): ");
                        String bNum = sc.nextLine();
                        if (manager.allocateBed((Inpatient) pAlloc, bNum)) {
                            System.out.println("Bed allocated successfully.");
                        } else {
                            System.out.println("Allocation failed. Bed occupied or invalid bed number.");
                        }
                    } else {
                        System.out.println("Only Inpatients can be allocated a bed!");
                    }
                    break;

                    //releases a bed from an assigned inpatient waiting to be discharged
                case 8:
                    System.out.print("Enter Bed Number to release (e.g., B01): ");
                    String rNum = sc.nextLine();
                    if (manager.releaseBed(rNum)) {
                        System.out.println("Bed released successfully.");
                    } else {
                        System.out.println("Failed to release bed. Bed was not occupied.");
                    }
                    break;

                    //Occupancy report
                case 9:
                    manager.generateReport();
                    break;

                 //Exit loop
                case 10:
                    running = false;
                    System.out.println("Exiting Medicare Admission System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please enter a number from 1 to 10.");
            }
        }
    }
}
