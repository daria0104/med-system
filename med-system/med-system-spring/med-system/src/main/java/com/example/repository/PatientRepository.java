package com.example.repository;

import com.example.entity.DentistryPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<DentistryPatient, Long> {
    DentistryPatient findBySurname(String name);
    DentistryPatient findBySurnameAndNameAndMidNameAndBirthdayAndGender(String surname, String name, String midName, LocalDate birthday, Boolean gender);
}
