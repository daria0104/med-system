package com.example.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Arrays.stream;

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
    public DentistryVisit(DentistryPatient patient, String doctorCode, String service, String timeVisit, double cost, double payment) {
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id", referencedColumnName = "patient_id", nullable = false)
    DentistryPatient patient;

    @Column(name = "doctor_code", nullable = false)
    String doctorCode;

    @Column(name ="service", nullable = false)
    String service;

    @Column(name="time_visit", nullable = false)
    LocalDateTime timeVisit;

    @Column(name = "cost", nullable = false)
    double cost;

    @Column(name = "payment", nullable = false)
    @Check(constraints = "(cost * 0.3) <= payment AND payment <= cost")
    double payment;

    public String generateVisitCode(){
        long timestamp = Timestamp.valueOf(timeVisit).getTime();
//        long timestamp = System.currentTimeMillis();
        int randomValue = ThreadLocalRandom.current().nextInt(0, 1000);
        int uniqueInt = (int) (timestamp % 1000000) * 1000 + randomValue;
//        StringBuilder s = new StringBuilder(String.valueOf(uniqueInt)).reverse();
        StringBuilder s = new StringBuilder(String.valueOf(timestamp));
        s.setLength(7);
        return s.toString();
    }
}
