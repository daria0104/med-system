package com.example.controller;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import com.example.service.VisitService;
import org.springframework.ui.Model;
import com.example.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PatientController {
    private final PatientService patientService;
    private final VisitService visitService;

    public PatientController(PatientService patientService, VisitService visitService) {
        this.patientService = patientService;
        this.visitService = visitService;
    }

    @GetMapping("/")
    public String showStartPage(){
//        System.out.println(session.getId());
//        System.out.println("MaxInactiveInterval: " + session.getMaxInactiveInterval());
        return "startPage";
    }

    @GetMapping("/patients")
    public String showPatientsList(Model model){
        model.addAttribute("patients", patientService.getAllPatients());
        return "/patient/patientsList";
    }

    @GetMapping("/patient/new")
    public String editPatientsForm(Model model){
        DentistryPatient patient = new DentistryPatient("", "", "", LocalDate.now(), "", "");
//        DentistryPatient patient = new DentistryPatient("Borisenko", "Борис", "Borisovich", "17.07.2000", "male", "Consultation");
        model.addAttribute("patient", patient);
        model.addAttribute("errorMessage", null);
        return "/patient/editPatientPage";
    }

    @PostMapping("/patient/save/{id}")
    public String addPatient(@PathVariable Long id,
                             @ModelAttribute("patient") DentistryPatient patient,
                             Model model){
        System.out.println("/patient/save/" + patient.getSurname());

        DentistryPatient current = patientService.getPatientID(patient);
        String error = null;
        if(current != null && patient.getId() == 0L){
            id = current.getId();
            System.out.println("err: " + id);
            error = "Patient already in the list. You can edit current";
            patient.setId(id);
        }
        else {
            if(id == 0L) {
                if(patientService.savePatient(patient) == null)
                    error = "Failed. Something went wrong";
            }
            else{
                if(patientService.updatePatient(id, patient) == null)
                    error = "Failed. Something went wrong";
            }
        }

        if(error != null){
            model.addAttribute("errorMessage", error);
            model.addAttribute("patient", patient);
            return "/patient/editPatientPage";
        }
        else{
            return "redirect:/patients";
        }
    }

    @GetMapping("/patient/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model){
        System.out.println("/patient/edit/" + id);
        DentistryPatient patient = patientService.getPatientById(id);
        System.out.println(patient.getBirthday());
        model.addAttribute("patient", patient);
        model.addAttribute("errorMessage", null);
        return "/patient/editPatientPage";
    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable Long id, Model model){
        System.out.println("/patient/delete/" + id);
        String error = null;
        List<DentistryVisit> visits = visitService.getVisitsByPatient_id(id);
        DentistryPatient patient = patientService.getPatientById(id);
        if(patient == null) {
            error = "Failed. Cannot find patient.";
        }
        else{
            if(visitService.getVisitsByPatient_id(id) != null && !visitService.getVisitsByPatient_id(id).isEmpty()) {
                System.out.println(visitService.getVisitsByPatient_id(id));
                error = "Failed. " + patient.getName() + " " + patient.getSurname() + " still has visits to attend. If you want to delete patient, please, cancel all appointments.";
            }
            else
                patientService.deletePatientById(id);
        }

        if(error != null){
            model.addAttribute("visits", visits);
            model.addAttribute("errorMessage", error);
            return "errorPage";
        }
        else{
            System.out.println("error == null");
            return "redirect:/patients";
        }
    }
}
