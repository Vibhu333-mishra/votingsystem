package com.codewithv;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name=req.getParameter("name");
        String email=req.getParameter("email");
        String password=req.getParameter("password");

        boolean success = DBUtil.registervoter(name, email, password);
        {
            if(success)
            {
                resp.sendRedirect(req.getContextPath() + "/login.html");


            }else {
                resp.getWriter().println("Error registering user!");
            }
        }
    PrintWriter out=resp.getWriter();
        out.println("login.html");
    }
}
