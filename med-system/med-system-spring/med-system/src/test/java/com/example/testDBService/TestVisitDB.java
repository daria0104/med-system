package com.example.testDBService;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import com.example.service.PatientService;
import com.example.service.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class TestVisitDB {

    @Autowired
    private VisitService visitService;
    @Autowired
    private PatientService patientService;


    @Test
    void testGetAllVisits() {
        System.out.println("Visits");
        List<DentistryVisit> visits = visitService.getAllVisits();
        visits.forEach(System.out::println);
    }

    @Test
    void testFindByID(){
//        assertEquals("1698313", visitService.getVisitById(52L).getVisitCode());
        assertNull(visitService.getVisitById(8L));
    }

    @Test
    void testFindByVisitCode(){
        assertEquals("1698313", visitService.getVisitByVisitCode("1698313").getVisitCode());
        assertNull(visitService.getVisitByVisitCode("234455"));
    }

//    @Test
//    void testFindByPatient_id(){
//        assertEquals(1L, visitService.getVisitByPatient_id(1L).getVisit_id());
//        assertNull(visitService.getVisitByPatient_id(5L));
//    }

    @Test
    void testFindVisitsByPatient_id(){
        System.out.println("\nVisits for patient with id: " + 2L);
        System.out.println(visitService.getVisitsByPatient_id(2L) + "\n");
    }

    @Test
    void testInsertVisit() {
        System.out.println("Inserting visit to DB.");
        DentistryPatient patient = new DentistryPatient("Sidorchuk", "Maria", "Cergiivna", LocalDate.parse("17.07.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "female",
                "Patient has wisdom teeth pain in the left lower jaw. After dental x-rays doctor will able to make a decision.");
        DentistryPatient temp = patientService.getPatientID(patient) == null ? patient : patientService.getPatientID(patient);
        DentistryVisit visit = new DentistryVisit(temp, "0349", "Consultation",
                LocalDateTime.parse("12.12.2022 18:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                500.0, 500.0);
        if(visitService.availableTime(visit, visitService.getAllVisits())) {//if(daoVisit.findByKey(visit) == null) {
            if (visitService.saveVisit(visit)!= null) {
                System.out.println("Visit successfully inserted to DB.");
                List<DentistryVisit> visitList = visitService.getAllVisits();
                visitList.forEach(System.out::println);
            } else {
                System.err.println("Error. Visit not inserted to DB.");
            }
        }else{
            System.err.println("Visit to insert already getPatientID in DB.");
        }
    }

    @Test
    void testUpdateVisit() {
        System.out.println("Updating visit in DB.");
        DentistryVisit visit = visitService.getVisitByVisitCode("1698313");
        if (visit != null) {
            DentistryPatient patient = new DentistryPatient("Sidorchuk", "Maria", "Cergiivna", LocalDate.parse("17.07.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "female",
                    "Patient has wisdom teeth pain in the left lower jaw. After dental x-rays doctor will able to make a decision.");
            DentistryVisit updated = new DentistryVisit(patient, "0349", "Dental x-rays", LocalDateTime.parse("30.10.2023 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), 400.0, 400.0);
            List<DentistryVisit> visits = visitService.getAllVisits();
            if (visitService.availableTime(updated, visits)) {
                long id = visit.getVisit_id();
                if (visitService.updateVisit(id, updated) != null) {
                    System.out.println("Visit successfully updated in DB.");
                    List<DentistryVisit> visitList = visitService.getAllVisits();
                    visitList.forEach(System.out::println);
                } else {
                    System.err.println("Error. Visit not updated to DB.");
                }
            } else {
                System.err.println("Visit already getPatientID in DB.");
            }
        } else {
            System.err.println("Error. Visit to update not found.");
        }
    }

    @Test
    void testDeleteVisit() {
        System.out.println("Deleting visit to DB.");
        DentistryVisit visit = visitService.getVisitByVisitCode("1704103");
        if (visit != null) {
            long id = visit.getVisit_id();
            visitService.deleteVisitById(id);
            assertNull(visitService.getVisitByVisitCode("1704103"));
            System.out.println("Visit successfully deleted from DB.");
            List<DentistryVisit> patientList = visitService.getAllVisits();
            patientList.forEach(System.out::println);
        } else {
            System.err.println("Visit to delete not found in DB.");
        }
    }

}
