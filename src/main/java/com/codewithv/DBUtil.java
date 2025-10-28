package com.codewithv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/votingsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public static boolean registervoter(String name, String email, String password) {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT into voters(name,email,password) values(?,?,?)"))
                {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            return true;
        }
        catch(Exception e){
            System.out.println("Error in registervoter: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}