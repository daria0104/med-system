package com.example.report;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class HtmlReportGenerator extends ReportGenerator {
    @Override
    protected String generateHeader(DentistryPatient patient) {
        return "<html><head><title>Report</title>"
                + "<link rel=\"stylesheet\" href=\"/style.css\">"
                + "<style>table, td, th { border: 1px solid; text-align: left;}\n"
                + "table { border-collapse: collapse; }</style>"
                + "</head><body>"
                + "<div>"
                + "<p>Creation time: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "</p>"
                + "<h3>Report</h3>"
                + "<p>" + patient.getSurname() + " " + patient.getName() + " (" + patient.getStringGender() + ")<p>"
                + "<p>Birth date: " + patient.getStringBirthday() + "</p>";
    }

    @Override
    protected String generateData(List<DentistryVisit> visits) {
        StringBuilder data = new StringBuilder();
        for (DentistryVisit visit : visits) {
            data.append("<tr>")
                    .append("<td>").append(visit.getVisitCode()).append("</td>")
                    .append("<td>").append(visit.getDoctorCode()).append("</td>")
                    .append("<td>").append(visit.getStringTimeVisit()).append("</td>")
                    .append("<td>").append(visit.getService()).append("</td>")
                    .append("<td>").append(visit.getCost()).append("</td>")
                    .append("<td>").append(visit.getPayment()).append("</td>")
                    .append("</tr>");
        }
        return "<table cellpadding=\"7\" style=\"border-collapse: collapse; border: 1px solid;\">" +
                "<tr>" +
                "<th>Visit code</th>" +
                "<th>Doctor code</th>" +
                "<th>Visit time</th>" +
                "<th>Service</th>" +
                "<th>Price</th>" +
                "<th>Payment</th>" +
                "</tr>" + data + "</table>";
    }

    @Override
    protected String generateFooter() {
        return "<div>Daria Maier KN-221G</div></body></html>";
    }
}
