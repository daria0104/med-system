package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
public class DentistryPatient {

    public DentistryPatient(String surname, String name, String midName, LocalDate birthday, String gender, String stateComments) {
        this.id = 0L;
        this.surname = surname;
        this.name = name;
        this.midName = midName;
        this.birthday = birthday;
        this.gender = "male".equals(gender);
        this.stateComments = stateComments;
    }

    public DentistryPatient(String surname, String name, String midName, String birthday, String gender, String stateComments) {
        this.id = 0L;
        this.surname = surname;
        this.name = name;
        this.midName = midName;
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.gender = "male".equals(gender);
        this.stateComments = stateComments;
    }

    public DentistryPatient(String surname) {
        this.surname = surname;
        this.name = "Not entered";
        this.midName = "Not entered";
        this.birthday = LocalDate.parse("01.01.2000", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.gender = true;
        this.stateComments = "Consultation";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name="surname", nullable = false)
    String surname;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="mid_name", nullable = false)
    String midName;

    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

    public String getStringBirthday() {
        return birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Column(name = "gender", nullable = false)
    boolean gender;

    public String getStringGender() {
        return gender ? "male" : "female";
    }


    @Column(name="comments", nullable = false)
    String stateComments = "Consultation";



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DentistryPatient patient = (DentistryPatient) o;
        return gender == patient.gender && Objects.equals(surname, patient.surname) && Objects.equals(name, patient.name) && Objects.equals(midName, patient.midName) && Objects.equals(birthday, patient.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, midName, birthday, gender, stateComments);
    }
}
