package com.example.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name="visit")
@Data
@NoArgsConstructor
public class DentistryVisit {

    public DentistryVisit(String visitCode) {
        this.visitCode = visitCode;
        this.patient = null;
        this.doctorCode = "1111";
        this.service = "service";
        this.timeVisit = LocalDateTime.parse("10.10.2000 10:10", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        this.cost = 0.0;
        this.payment = 0.0;
    }

    public DentistryVisit(DentistryPatient patient, String doctorCode, String service, LocalDateTime timeVisit, double cost, double payment) {
        this.visit_id = 0L;
        this.patient = patient;
        this.doctorCode = doctorCode;
        this.service = service;
        this.timeVisit = timeVisit;
        this.cost = cost;
        this.payment = payment;
        this.visitCode = generateVisitCode();
    }

    public DentistryVisit(DentistryPatient patient, String doctorCode, String service, String timeVisit, double cost, double payment) {
//        this.visit_id = 0L;
        this.patient = patient;
        this.doctorCode = doctorCode;
        this.service = service;
        this.timeVisit = LocalDateTime.parse(timeVisit, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        this.cost = cost;
        this.payment = payment;
        this.visitCode = generateVisitCode();
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long visit_id;

    @Column(name="visit_code", unique = true, length = 7)
    String visitCode;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id", referencedColumnName = "id", nullable = false, updatable = false)
    DentistryPatient patient;

    @Column(name = "doctor_code", nullable = false)
    String doctorCode;

    @Column(name ="service", nullable = false)
    String service;

    @Column(name="time_visit", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime timeVisit;

    public String getStringTimeVisit() {
        return timeVisit.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Column(name = "cost", nullable = false)
    double cost;

    @Column(name = "payment", nullable = false)
//    @Check(constraints = "(cost * 0.3) <= payment AND payment <= cost")
    double payment;

    public String generateVisitCode(){
//        long timestamp = System.currentTimeMillis();
//        int randomValue = ThreadLocalRandom.current().nextInt(0, 1000);
//        int uniqueInt = (int) (timestamp % 1000000) * 1000 + randomValue;
//        StringBuilder s = new StringBuilder(String.valueOf(uniqueInt)).reverse();

        long timestamp = Timestamp.valueOf(timeVisit).getTime();
        StringBuilder s = new StringBuilder(String.valueOf(timestamp));
        s.setLength(3);
        return s + doctorCode;

//        UUID uuid = UUID.randomUUID();
//        long numericValue = Math.abs(uuid.getLeastSignificantBits() % 10000000);
//        return String.format("%07d", numericValue);
    }

    public static void main(String[] args) {
        DentistryPatient patient = new DentistryPatient("Sidorchuk", "Maria", "Cergiivna", LocalDate.parse("17.07.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "female",
                "Patient has wisdom teeth pain in the left lower jaw. After dental x-rays doctor will able to make a decision.");
//        DentistryPatient temp = patientService.getPatientID(patient) == null ? patient : patientService.getPatientID(patient);
        DentistryVisit visit = new DentistryVisit(patient, "1349", "Consultation",
                LocalDateTime.parse("12.12.2023 19:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                500.0, 500.0);
        System.out.println(visit.getVisitCode());

    }
}
