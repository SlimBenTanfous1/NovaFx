package tn.PiFx.controllers;

import tn.PiFx.utils.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public boolean authenticateUser(String username, String password) {
        Connection con = DataBase.getInstance().getConx();
        String query = "SELECT * FROM users WHERE nom = ? AND mdp = ?"; // Assuming 'nom' is the username

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password); // In a real app, compare hashed passwords

            ResultSet rs = pst.executeQuery();
            return rs.next(); // True if there is a record
        } catch (SQLException e) {
            System.err.println("Login Error: " + e.getMessage());
            return false;
        }
    }
}
