package com.example.servlet;

import com.example.DAO.DAOPatient;
import com.example.entity.DentistryPatient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/edit-patients-list")
public class PatientsListEditorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PatientAdd: #doGet()");
        DAOPatient daoPatient = new DAOPatient();
        long id = Long.parseLong(request.getParameter("patient_id"));
        System.out.println("id = " + id);
        DentistryPatient patient = new DentistryPatient();
        if(id == 0L) {
            patient.setPatient_id(id);
            request.setAttribute("patient", patient);
        }
        else
            request.setAttribute("patient", daoPatient.findById(id));
        String path = "/patientsListEditor.jsp";
        getServletContext().getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOPatient daoPatient = new DAOPatient();
        System.out.println("PatientAdd: doPost()");
        System.out.println("id = " + request.getParameter("patient_id"));
        long id = Long.parseLong(request.getParameter("patient_id"));
        String surname = new String(request.getParameter("surname").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String name = new String(request.getParameter("name").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String midname = new String(request.getParameter("midname").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        LocalDate birthday = LocalDate.parse(LocalDate.parse(request.getParameter("birthday")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String gender = request.getParameter("gender");
        String comment = request.getParameter("state");
        DentistryPatient patient = new DentistryPatient(surname, name, midname, birthday, gender, comment);

        String error = null;
        DentistryPatient current = daoPatient.findID(patient);
        if(current != null && id == 0L){
            id = current.getPatient_id();
            System.out.println("err: " + id);
            error = "Patient already exist in the list. You can edit current";
            patient.setPatient_id(id);
        }
        else {
            if(id == 0L) {
                if(!daoPatient.insert(patient))
                    error = "Failed. Something went wrong";
            }
            else{
                if(!daoPatient.update(id, patient))
                    error = "Failed. Something went wrong";
            }
        }

        if(error != null){
            request.setAttribute("errorMessage", error);
            request.setAttribute("patient", patient);
            getServletContext().getRequestDispatcher("/patientsListEditor.jsp").forward(request, response);
        }
        else{
            response.sendRedirect("patients_servlet");
        }
    }
}
