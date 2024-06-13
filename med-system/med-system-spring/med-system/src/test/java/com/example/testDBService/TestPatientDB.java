package com.example.testDBService;

import com.example.entity.DentistryPatient;
import com.example.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestPatientDB {

    @Autowired
    private PatientService patientService;

    @Test
    void testGetAllPatients() {
        System.out.println("Patients");
        List<DentistryPatient> patientList = patientService.getAllPatients();
        patientList.forEach(System.out::println);
    }

    @Test
    void testFindByID(){
//        assertEquals("Daniluk", patientService.getPatientById(902L).getSurname());
        assertNull(patientService.getPatientById(990L));
    }

    @Test
    void testFindBySurname(){
        assertEquals("Petrov", patientService.getPatientBySurname("Petrov").getSurname());
        assertNull(patientService.getPatientBySurname("Borisenko"));
    }

    @Test
    void testExist(){
        assertEquals("Petrov", patientService.getPatientID(patientService.getPatientBySurname("Petrov")).getSurname());
    }

    @Test
    void testInsertPatient() {
        System.out.println("Inserting patient to DB.");
        DentistryPatient patient = new DentistryPatient("Petrov", "Petro", "Petrovich", LocalDate.parse("17.07.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "male", "Consultation");
        if (patientService.getPatientBySurname(patient.getSurname()) == null) {
            if (patientService.savePatient(patient) != null) {
                System.out.println("Patient successfully inserted to DB.");
                List<DentistryPatient> patientList = patientService.getAllPatients();
                patientList.forEach(System.out::println);
            } else {
                System.err.println("Error. Patient not inserted to DB.");
            }
        } else {
            System.err.println("Patient to insert already getPatientID in DB.");
        }
    }

    @Test
    void testUpdatePatient() {
        System.out.println("Updating patient in DB.");
        DentistryPatient patient = patientService.getPatientBySurname("Petrov");
        if (patient != null) {
            long id = patient.getId();
            DentistryPatient updated = new DentistryPatient("Petrov", "Ivan", "Petrovich", LocalDate.parse("17.07.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "male", "Consultation");
            if (patientService.getPatientBySurname(updated.getSurname()) == null) {
                if (patientService.updatePatient(id, patient) != null) {
                    System.out.println("Patient successfully updated in DB.");
                    List<DentistryPatient> patientList = patientService.getAllPatients();
                    patientList.forEach(System.out::println);
                } else
                    System.err.println("Error. Patient not updated to DB.");
            } else
                System.err.println("Patient already getPatientID in DB.");

        } else
            System.err.println("Error. Patient to update not found.");
    }

    @Test
    void testDeletePatient() {
        System.out.println("Deleting patient to DB.");
        DentistryPatient patient = patientService.getPatientBySurname("Sidorchuk");
        if (patient != null) {
            long id = patient.getId();
            patientService.deletePatientById(id);
            assertNull(patientService.getPatientBySurname("Petrov"));
            System.out.println("Patient successfully deleted from DB.");
            List<DentistryPatient> patientList = patientService.getAllPatients();
            patientList.forEach(System.out::println);
        } else {
            System.err.println("Patient to delete not found in DB.");
        }
    }

}
