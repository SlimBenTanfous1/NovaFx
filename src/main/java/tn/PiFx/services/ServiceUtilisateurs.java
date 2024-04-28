package tn.PiFx.services;

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
    public boolean Add(User user) {
        String qry = "INSERT INTO `user`(`nom`, `prenom`, `adresse`, `email`, `num_tel`, `profession`, `password`, `cin`,`role`) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean isAdded = false;

        try (PreparedStatement stm = conx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, user.getNom());
            stm.setString(2, user.getPrenom());
            stm.setString(3, user.getAdresse());
            stm.setString(4, user.getEmail());
            stm.setInt(5, user.getNum_tel());
            stm.setString(6, user.getProfession());
            stm.setString(7, user.getPassword());
            stm.setInt(8, user.getCin());
            stm.setString(9,user.getRoles());

            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                        isAdded = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isAdded;
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
                User user = new User(
                        rs.getInt("id"),
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getInt("num_tel"),
                        rs.getString("password"),
                        rs.getString("profession"),
                        rs.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users;
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
    public boolean Update(User user) {

        System.out.println("Attempting to update user with ID: " + user.getId());  // Debug statement

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE user SET nom=?, prenom=?, adresse=?, email=?, num_tel=?, profession=?, cin=?, role=? WHERE id=?";

            connection = DataBase.getInstance().getConx();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            System.out.println("User Details: " + user.toString());  // Make sure 'User' class has a proper 'toString' method

            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getAdresse());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getNum_tel());
            preparedStatement.setString(6, user.getProfession());
            preparedStatement.setInt(7, user.getCin());
            preparedStatement.setString(8, user.getRoles());
            preparedStatement.setInt(9, user.getId());


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            } else {
                System.out.println("Update failed - No rows affected. Check if the ID exists: " + user.getId());
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return false;
        }






    @Override
    public boolean Delete(User user) {
        String query = "DELETE FROM user WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DataBase.getInstance().getConx();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User deleted successfully.");
                return true;
            } else {
                System.out.println("No user found with ID: " + user.getId());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQL error occurred during the delete operation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void DeleteByID(int id) {

    }
    public boolean isValidEmail(String email) {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com|outlook\\.(com|tn|fr)|yahoo\\.(com|tn|fr))$";
        return email.matches(emailRegex);
    }
    public boolean isValidPhoneNumber(int numTel) {
        String numTelStr = String.valueOf(numTel);
        return numTelStr.length() == 8;
    }
    public boolean checkUserExists(String email) {
        String req = "SELECT count(1) FROM `user` WHERE `email`=?";
        boolean exists = false;
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                exists = res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user exists: " + e.getMessage());
        }
        return exists;
    }


}
