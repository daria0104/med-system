package com.example.servlet;

import com.example.DAO.DAOPatient;
import com.example.DAO.IDAO;
import com.example.DAO.PatientProxy;
import com.example.entity.DentistryPatient;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(name = "PatientsServlet", value = "/patients_servlet")
public class PatientsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(PatientsServlet.class.getName() + "#doGet()");
        PatientProxy proxy = new PatientProxy();
        List<DentistryPatient> patients = proxy.showAll();
        System.out.println("proxy: " + proxy.isXMLDataSource());
        request.setAttribute("patients", patients);
        request.setAttribute("dataSource", proxy.isXMLDataSource());
        String path = "/patients.jsp";
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
