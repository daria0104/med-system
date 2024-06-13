package com.example.service;

import com.example.entity.DentistryPatient;
import com.example.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService{

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<DentistryPatient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public DentistryPatient getPatientBySurname(String surname) {
        return patientRepository.findBySurname(surname);
    }

    // check if such patient exist in DB
    public DentistryPatient getPatientID(DentistryPatient patient){
        return patientRepository.findBySurnameAndNameAndMidNameAndBirthdayAndGender(patient.getSurname(), patient.getName(), patient.getMidName(), patient.getBirthday(), patient.isGender());
    }

    @Override
    public DentistryPatient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public DentistryPatient savePatient(DentistryPatient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public DentistryPatient updatePatient(Long id, DentistryPatient patient) {
        patient.setId(id);
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }
}
