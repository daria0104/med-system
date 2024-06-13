package com.example.servlet;

import com.example.DAO.DAOVisit;
import com.example.entity.DentistryVisit;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "VisitsServlet", value = "/visits_servlet")
public class VisitsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("#doGet()");
        List<DentistryVisit> visits = new DAOVisit().showAll();
        request.setAttribute("visits", visits);
        String path = "/views/visits.jsp";
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
