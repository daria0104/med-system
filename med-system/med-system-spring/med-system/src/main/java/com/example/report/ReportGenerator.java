package com.example.report;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;

import java.util.List;

public abstract class ReportGenerator {
    public final String generateReport(DentistryPatient patient, List<DentistryVisit> visits) {
        StringBuilder report = new StringBuilder();

        report.append(generateHeader(patient));
        report.append(generateData(visits));
//        report.append(generateFooter(train));

        return report.toString();
    }

    protected abstract String generateHeader(DentistryPatient patient);

    protected abstract String generateData(List<DentistryVisit> visits);

    protected abstract String generateFooter();
}
