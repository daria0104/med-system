package com.example.service;

import com.example.entity.DentistryVisit;

import java.util.List;

public interface IVisitService {
    List<DentistryVisit> getAllVisits();
    DentistryVisit getVisitById(Long id);

    DentistryVisit getVisitByVisitCode(String code);
    DentistryVisit saveVisit(DentistryVisit visit);
    DentistryVisit updateVisit(Long id, DentistryVisit visit);

    List<DentistryVisit> getVisitsByPatient_id (Long id);
    void deleteVisitById(Long id);

}
