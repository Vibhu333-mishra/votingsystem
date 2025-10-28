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

@WebServlet("/vote")
public class voteservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession(false);
        if(session==null || session.getAttribute("voterId")==null)
        {
            resp.getWriter().println("go to login page");
            return;
        }
        int voterId=(int) session.getAttribute("voterId");
        boolean hasVoted=(boolean) session.getAttribute("hasVoted");
        if(hasVoted) {
            resp.getWriter().println("You have already voted!");
            return;
        }
        int partyId=Integer.parseInt(req.getParameter("party"));
        try(Connection con=DBUtil.getConnection())
        {
        PreparedStatement ps= con.prepareStatement("INSERT INTO votes(voter_id,candidate_id) values(?,?)");
        ps.setInt(1,voterId);
        ps.setInt(2,partyId);
        ps.executeUpdate();
        PreparedStatement ps2= con.prepareStatement("UPDATE voters SET has_voted=true where id=?");
        ps2.setInt(1,voterId);
        ps2.executeUpdate();
            session.setAttribute("hasVoted", true);
            resp.getWriter().println("Vote submitted successfully!");
            resp.getWriter().println("<br><a href='results'>See Results</a>");


    }catch(Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error submitting vote!");
        }
}}
