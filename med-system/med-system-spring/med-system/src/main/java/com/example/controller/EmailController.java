package com.example.controller;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import com.example.entity.EmailDetails;
import com.example.report.HtmlReportGenerator;
import com.example.report.ReportGenerator;
import com.example.report.ReportGeneratorFactory;
import com.example.report.TxtReportGenerator;
import com.example.service.PatientService;
import com.example.service.VisitService;
import com.example.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
public class EmailController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private VisitService visitService;

    public EmailController(EmailService emailService, VisitService visitService) {
        this.emailService = emailService;
        this.visitService = visitService;
    }

    @GetMapping("/patient/{patient_id}/visits/report")
    public ResponseEntity<?> getReport(@PathVariable Long patient_id, @RequestParam String format)
    {
        String content = attachmentContent(patient_id, format);//report.generateReport(patient, visitList);
        System.out.println("entered to report");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "visits_report-" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "." + format);


        System.out.println("\nresponse entity: " + ResponseEntity.accepted());
        return ResponseEntity.ok().headers(headers).body(content);
    }

    @PostMapping("/patient/{patient_id}/visits/sendMail")
    public ResponseEntity<String> sendMailWithAttachment(@PathVariable Long patient_id,
                                         @RequestParam String format,
                                         @RequestParam String recipient, Model model)
    {
        System.out.println("entered to sender");
        String content = attachmentContent(patient_id, format);
        DentistryPatient patient = patientService.getPatientById(patient_id);

        EmailDetails details = new EmailDetails();
        details.setRecipient(recipient);
        details.setSubject("Visits report");
        details.setMessageBody("Good day,\nyou received report with visits list. " +
                "If you have some problems with opening report, please, write.\n" +
                "Have a nice day,\nDentistry Clinic \"Smile\"");
        details.setAttachment(Base64.getEncoder().encodeToString(content.getBytes()));
        details.setAttachmentName(patient.getName() + "_" + patient.getSurname() + " visits report." + format);
        emailService.sendMailWithAttachment(details);
        model.addAttribute("", "disableInputForSeconds(30)");
        return ResponseEntity.ok("Email successfully sent");
//        return "redirect:/patient/" + patient_id + "/visits";
    }

    private String attachmentContent(Long patient_id, String format){
        DentistryPatient patient = patientService.getPatientById(patient_id);
        List<DentistryVisit> visitList = visitService.getVisitsByPatient_id(patient_id);
        ReportGenerator report = ReportGeneratorFactory.getReportGenerator(format);

        return report.generateReport(patient, visitList);
    }

    private boolean isAdmin(Principal principal) {
        return principal != null && principal instanceof Authentication
                && ((Authentication) principal).getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }
}
