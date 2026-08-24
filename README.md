# PROG6112-Assignment-1
## Medicare Hospital Patient Admission System

A Java-based object-oriented terminal application for managing hospital patient registrations, ward bed allocations, occupancy reporting, and record maintenance.

---

Features
 
Patient Management: Register new patients, search records by Patient ID, update details, and remove records safely.
Inheritance & Polymorphism: Supports general `Patient` records alongside detailed Inpatient records (with assigned ward/bed numbers).
2D Ward Grid Allocation: Interactive 4x5 array representing 20 ward beds (B01 to B20). Tracks available vs. occupied beds in real time.
Bed Management: Prevents double-allocation of beds and automatically frees up assigned beds upon patient deletion or manual release.
Sorting & Analytics Sorts patient lists alphabetically by ID and generates occupancy reports including total count, available count, and usage percentages.
Automated Testing: Includes a suite of JUnit 5-unit tests covering core manager logic and edge cases.

---

## Built With

language: Java 8+
IDE: Apache NetBeans
Testing: JUnit 5
Data Structures: 1D Array List, 2D Array (String [] []), Enum

---

## Project Structure

src/com/mycompany/medicarehospitalsystem/
├── PatientCategory.java         Enum for patient classification (INPATIENT, OUTPATIENT, EMERGENCY)
├── Patient.java                 Base class encapsulating core patient attributes
├── Inpatient.java               Subclass inheriting from patient, adding ward and bed details
├── WardManager.java             Business logic for patient list, 2D bed array, sorting, and reporting
├── Main.java                    Console menu interface and user input handler
└── WardManagerUnitTest.java     JUnit 5 test suite for unit testing core logic


## Getting Started

### Prerequisites

* [JDK 8 or higher](https://www.oracle.com/java/technologies/downloads/)
* [Apache NetBeans IDE](https://netbeans.apache.org/) (or any modern Java IDE like IntelliJ IDEA / Eclipse)

### Installation & Execution

1. Clone the repository:
git clone https://github.com/your-username/MedicareHospitalSystem.git

```


2. Open in NetBeans:
* Launch NetBeans.
* Select File Open Project.
* Navigate to the cloned folder and open MedicareHospitalSystem.


3. Run the Application:
 Right-click Main.java Run File


4. Run Unit Tests:
Right-click the project node in NetBeans .



The test suite (WardManagerUnitTest.java) verifies:

Successful patient registration & prevention of duplicate Patient IDs.
Record retrieval via search and attribute update logic.
Valid bed allocation and prevention of allocating occupied beds.
Releasing beds and updating patient status.
Deleting patient records and releasing associated resources.
