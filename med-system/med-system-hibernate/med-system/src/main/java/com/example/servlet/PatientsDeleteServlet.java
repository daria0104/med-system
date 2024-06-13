package com.example.servlet;

import com.example.DAO.DAOPatient;
import com.example.DAO.DAOVisit;
import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/delete-patient")
public class PatientsDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PatientDelete: #doGet()");
        DAOPatient daoPatient = new DAOPatient();
        DAOVisit daoVisit = new DAOVisit();
        long id = Long.parseLong(request.getParameter("patient_id"));
        String path = "";
        String error = null;
        List<DentistryVisit> visits = null;
        DentistryPatient patient = daoPatient.findById(id);
        if(patient == null) {
            error = "Failed. Cannot find patient.";
        }
        else{
            if(!daoPatient.delete(id)){
                error = "Failed. " + patient.getName() + " " + patient.getSurname() + " still has visits to attend. If you want to delete patient, please, cancel all appointments.";
            }
        }

        if(error != null){
            request.setAttribute("visits", visits);
            request.setAttribute("errorMessage", error);
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
        else{
            response.sendRedirect("patients_servlet");
        }
    }
}
