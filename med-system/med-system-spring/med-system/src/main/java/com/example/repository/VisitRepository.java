package com.example.repository;

import com.example.entity.DentistryVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<DentistryVisit, Long> {
    List<DentistryVisit> findDentistryVisitsByPatient_Id(Long id);
    DentistryVisit findByVisitCode(String code);
}
