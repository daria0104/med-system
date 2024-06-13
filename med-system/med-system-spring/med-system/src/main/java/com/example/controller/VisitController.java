package com.example.controller;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import com.example.service.PatientService;
import com.example.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VisitController {
    private final PatientService patientService;
    private final VisitService visitService;

    public VisitController(PatientService patientService, VisitService visitService) {
        this.patientService = patientService;
        this.visitService = visitService;
    }

    @GetMapping("/patient/{id}/visits")
    public String infoEmployeeForm(@PathVariable Long id, Model model) {
        DentistryPatient patient = patientService.getPatientById(id);
        System.out.println(patient);
        List<DentistryVisit> visits = visitService.getVisitsByPatient_id(id);
        String message = " visits";
        if (visits.isEmpty())
            message = " does not have any appointments.";

        model.addAttribute("patient", patient);
        model.addAttribute("visits", visits);
        model.addAttribute("doctorCodeList", visitService.doctorCodeList());
        model.addAttribute("message", message);
        return "/visit/visitsList";
    }

    @GetMapping("/patient/{patient_id}/visit/new")
    public String editVisitsForm(@PathVariable Long patient_id, Model model){
        DentistryPatient patient = patientService.getPatientById(patient_id);
//        DentistryPatient temp = patientService.getPatientID(patient) == null ? patient : patientService.getPatientID(patient);
        DentistryVisit visit = new DentistryVisit(patient, "", "", LocalDateTime.now(),  0.0, 0.0);
        model.addAttribute("visit", visit);
        model.addAttribute("errorMessage", null);
        model.addAttribute("patient", patient);
        return "/visit/editVisitPage";
    }

    @PostMapping("/patient/{patient_id}/visit/save/{visit_id}")
    public String saveVisit(@PathVariable Long patient_id, @PathVariable Long visit_id, @ModelAttribute("visit") DentistryVisit visit, Model model){

        DentistryPatient patient = patientService.getPatientById(patient_id);
        String errorMessage = null;
        if(visit_id == 0L){
            if(visitService.availableTime(visit, visitService.getAllVisits())) {
                visit.setPatient(patient);
                visit.setVisitCode(visit.generateVisitCode());
                if (visitService.saveVisit(visit) == null)
                    errorMessage = "Something went wrong during creating visit";
            }
            else
                errorMessage = "Doctor is not available at this time. Please, choose another date.";
        }
        else{
            if(!visitService.getVisitById(visit_id).getTimeVisit().equals(visit.getTimeVisit())) {
                errorMessage = "You cannot change the appointment date";
                visit.setTimeVisit(visitService.getVisitById(visit_id).getTimeVisit());
            }
            else {
                visit.setPatient(patient);
                visit.setVisitCode(visit.generateVisitCode());
                if (visitService.updateVisit(visit_id, visit) == null)
                    errorMessage = "Something went wrong during changing visit";
            }
        }

        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("visit", visit);
            model.addAttribute("patient", patient);
            return "/visit/editVisitPage";
        }
        else
            return "redirect:/patient/" + patient_id + "/visits";
    }

    @GetMapping("/patient/{patient_id}/visit/edit/{visit_id}")
    public String updateVisit(@PathVariable Long visit_id, @PathVariable Long patient_id, Model model){
        System.out.println("/patient/" + patient_id + "/visit/edit/" + visit_id);

        System.out.println(visitService.doctorCodeList());

        DentistryPatient patient = patientService.getPatientById(patient_id);
        DentistryVisit visit = visitService.getVisitById(visit_id);
        model.addAttribute("patient", patient);
        model.addAttribute("visit", visit);
        model.addAttribute("errorMessage", null);
        return "/visit/editVisitPage";
    }

    @GetMapping("patient/{patient_id}/visit/delete/{visit_id}")
    public String deleteVisit(@PathVariable Long patient_id, @PathVariable Long visit_id, Model model){

        DentistryPatient patient = patientService.getPatientById(patient_id);
        DentistryVisit visit = visitService.getVisitById(visit_id);
        System.out.println("/patient/" + patient_id + "/delete/visit/" + visit_id);
        String error = null;
        if(patient != null) {
            if (visit == null)
                error = "Failed. Cannot find visit.";

            else {
                visitService.deleteVisitById(visit_id);
                if (visitService.getVisitByVisitCode(visit.getVisitCode()) != null)
                    error = "Failed. " + visit.getVisitCode() + " was not deleted.";
            }
        }

        if(error != null){
            model.addAttribute("errorMessage", error);
            return "errorPage";
        }
        else{
            return "redirect:/patient/" + patient_id + "/visits";
        }
    }
}
