package com.example.service;

import com.example.entity.DentistryVisit;
import com.example.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VisitService implements IVisitService{

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<DentistryVisit> getAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public DentistryVisit getVisitById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public DentistryVisit getVisitByVisitCode(String code) {
        return visitRepository.findByVisitCode(code);
    }

    @Override
    public List<DentistryVisit> getVisitsByPatient_id(Long id){
        return visitRepository.findDentistryVisitsByPatient_Id(id);
    }

    @Override
    public DentistryVisit saveVisit(DentistryVisit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public DentistryVisit updateVisit(Long id, DentistryVisit visit) {
        visit.setVisit_id(id);
        return visitRepository.save(visit);
    }

    @Override
    public void deleteVisitById(Long id) {
        visitRepository.deleteById(id);
    }

    public boolean availableTime(DentistryVisit visit, List<DentistryVisit> visitsList){
        return visitsList.stream().noneMatch(v -> Objects.equals(visit.getDoctorCode(), v.getDoctorCode()) && visit.getTimeVisit().equals(v.getTimeVisit()));
    }

    public Object[] doctorCodeList(){
        List<DentistryVisit> visits = getAllVisits();
        return visits.stream().map(DentistryVisit::getDoctorCode).distinct().toArray();
    }
}
