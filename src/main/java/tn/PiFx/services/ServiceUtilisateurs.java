package tn.PiFx.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.PiFx.Interfaces.IUtilisateur;
import tn.PiFx.entities.User;
import tn.PiFx.utils.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceUtilisateurs implements IUtilisateur<User> {
    private Connection conx;
    public ServiceUtilisateurs(){conx = DataBase.getInstance().getConx();}


    @Override
    public void Add(User user) {
        String qry = "INSERT INTO `user`(`nom`, `prenom`, `adresse`, `email`, `num_tel`, `password`, `cin`, `role`,`profession`) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stm = conx.prepareStatement(qry);
            stm.setString(1, user.getNom());
            stm.setString(2, user.getPrenom());
            stm.setString(3, user.getAdresse());
            stm.setString(4, user.getEmail());
            stm.setInt(5, user.getNum_tel());
            stm.setString(6, user.getPassword());
            stm.setInt(7, user.getCin());
            stm.setString(8, user.getRoles());
            stm.setString(9,user.getProfession());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public List<User> afficher() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user"; // Adjust the table name if it's different in your DB
        try (Statement stmt = conx.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Create a new User object from each record in the ResultSet
                User user = new User(
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getInt("num_tel"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("profession")
                );
                users.add(user); // Add the user to the list
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            // Handle exception - maybe log it and return an empty list or rethrow as a custom exception
        }
        return users; // Return the list of users (which may be empty if there are no users or an error occurred)
    }


    @Override
    public List<User> TriparNom() {
        return null;
    }

    @Override
    public List<User> TriparEmail() {
        return null;
    }

    @Override
    public List<User> Rechreche(String recherche) {
        return null;
    }

    @Override
    public void Update(User user) {
        try {
            String qry = "UPDATE `user` SET `nom`=?,`prenom`=?,`adresse`=?,`email`=?,`num_tel`=?,`profession`=?,`password`=?,`cin`=?,`role`=? WHERE 1";
            PreparedStatement stm = conx.prepareStatement(qry);
            stm.setString(1, user.getNom());
            stm.setString(2, user.getPrenom());
            stm.setString(3, user.getAdresse());
            stm.setString(4, user.getEmail());
            stm.setInt(5, user.getNum_tel());
            stm.setInt(6, user.getCin());
            stm.setString(7, user.getRoles());
            stm.setString(8,user.getProfession());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Delete(User user) {

    }

    @Override
    public void DeleteByID(int id) {

    }

    public User findById(int userId) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement statement = conx.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int cin = resultSet.getInt("cin");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String adresse = resultSet.getString("adresse");
                int numtel = resultSet.getInt("num_tel");
                String mdp = resultSet.getString("password");
                String role = resultSet.getString("role");
                String profession = resultSet.getString("profession");

                user = new User(cin, nom, prenom, email, adresse, numtel, mdp, role, profession);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle the exception as needed
        }
        return user;
    }
}
