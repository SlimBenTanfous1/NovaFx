package tn.PiFx.controllers;

import tn.PiFx.entities.User;
import tn.PiFx.utils.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {
    public boolean signUpUser(User user) {
        Connection con = DataBase.getInstance().getConx();
        String query = "INSERT INTO users (cin, num_tel, nom, prenom, adresse, mdp, profession, roles) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, user.getCin());
            pst.setInt(2, user.getNum_tel());
            pst.setString(3, user.getNom());
            pst.setString(4, user.getPrenom());
            pst.setString(5, user.getAdresse());
            pst.setString(6, user.getMdp()); // Consider hashing the password here
            pst.setString(7, user.getProfession());
            pst.setString(8, user.getRoles());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SignUp Error: " + e.getMessage());
            return false;
        }
    }
}
