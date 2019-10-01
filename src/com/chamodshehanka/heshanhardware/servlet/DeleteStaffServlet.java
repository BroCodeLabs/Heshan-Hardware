package com.chamodshehanka.heshanhardware.servlet;

import com.chamodshehanka.heshanhardware.controller.StaffController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chamodshehanka on 5/18/2019
 * @project HeshanHardware
 **/
@WebServlet(name = "DeleteStaffServlet", urlPatterns = "/DeleteStaff")
public class DeleteStaffServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffID = request.getParameter("staffID");
        boolean isDeleted = StaffController.removeStaff(staffID);
        if (isDeleted){
            request.setAttribute("message", "done");
            request.getRequestDispatcher("/manage-staff.jsp").forward(request, response);
        }else {
            request.setAttribute("message", "error");
            request.getRequestDispatcher("/manage-staff.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
