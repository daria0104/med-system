package com.example.service;

import com.example.entity.DentistryPatient;

import java.util.List;

public interface IPatientService {
    List<DentistryPatient> getAllPatients();
    DentistryPatient getPatientBySurname(String name);
    DentistryPatient getPatientById(Long id);
    DentistryPatient savePatient(DentistryPatient patient);
    DentistryPatient updatePatient(Long id, DentistryPatient patient);
    void deletePatientById(Long id);

}
