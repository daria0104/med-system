package com.example.report;

import org.springframework.stereotype.Component;
import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TxtReportGenerator extends ReportGenerator {
    @Override
    protected String generateHeader(DentistryPatient patient) {
        return "Creation date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n\n" +
                patient.getName() + " " + patient.getSurname() + " (" + patient.getStringGender() + ")\n" +
                "Birth date: " + patient.getStringBirthday() + "\n\n";
    }

    @Override
    protected String generateData(List<DentistryVisit> visits) {
        StringBuilder data = new StringBuilder();
        for (DentistryVisit visit : visits) {
            data.append(visit.getVisitCode()).append("\t\t")
                    .append(visit.getDoctorCode()).append("\t\t")
                    .append(visit.getStringTimeVisit()).append("\t")
                    .append(visit.getCost()).append("\t")
                    .append(visit.getPayment()).append("\t\t")
                    .append(visit.getService()).append("\n");
        }
        return "Visit code\tDoctor code\tVisit time\t\tPrice\tPayment\t\tService\n" + data;
    }

    @Override
    protected String generateFooter() {
        return "\nDaria Maier KN-221G";
    }
}
