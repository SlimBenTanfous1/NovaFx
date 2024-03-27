package tn.PiFx.controllers;

import org.mindrot.jbcrypt.BCrypt;
import tn.PiFx.utils.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public boolean authenticateUser(String username, String password) {
        Connection con = DataBase.getInstance().getConx();
        String query = "SELECT password FROM users WHERE nom = ?"; // Assuming 'nom' is the username field

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("mdp");
                return BCrypt.checkpw(password, storedHash);
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Login Error: " + e.getMessage());
            return false;
        }
    }
}

