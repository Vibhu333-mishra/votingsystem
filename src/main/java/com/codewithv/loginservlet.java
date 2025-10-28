package com.codewithv;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class loginservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, name, has_voted FROM voters WHERE email=? AND password=?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("voterId", rs.getInt("id"));
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("hasVoted", rs.getBoolean("has_voted"));
                resp.sendRedirect("vote.html");
            } else{
                resp.getWriter().println("Invalid login!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error logging in!");
        }
    }
}