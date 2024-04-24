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
        String qry = "INSERT INTO `user`(`nom`, `prenom`, `adresse`, `email`, `num_tel`, `password`, `cin`, `role`,`profession`) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean isAdded = false;

        try (PreparedStatement stm = conx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, user.getNom());
            stm.setString(2, user.getPrenom());
            stm.setString(3, user.getAdresse());
            stm.setString(4, user.getEmail());
            stm.setInt(5, user.getNum_tel());
            stm.setString(6, user.getPassword());
            stm.setInt(7, user.getCin());
            stm.setString(8,user.getRoles());
            stm.setString(9, user.getProfession());


            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve the auto-generated id
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));  // Assuming 'id' is the first column
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
    public boolean Update(User user) {
        System.out.println("Attempting to update user with ID: " + user.getId());  // Debug statement

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE user SET nom=?, prenom=?, adresse=?, email=?, num_tel=?, profession=?, password=?, cin=?, role=? WHERE id=?";

            connection = DataBase.getInstance().getConx();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getAdresse());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getNum_tel());
            preparedStatement.setString(6, user.getProfession());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setInt(8, user.getCin());
            preparedStatement.setString(9, user.getRoles());
            preparedStatement.setInt(10, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            } else {
                System.out.println("Update failed - No rows affected. Check if the ID exists: " + user.getId());
                return false;
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean Delete(User user) {
        String query = "DELETE FROM user WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a connection from your database helper class
            connection = DataBase.getInstance().getConx();

            // Prepare the SQL statement using the connection
            preparedStatement = connection.prepareStatement(query);

            // Set the id parameter in the prepared statement
            preparedStatement.setInt(1, user.getId());

            // Execute the delete statement
            int affectedRows = preparedStatement.executeUpdate();

            // Check how many rows were affected
            if (affectedRows > 0) {
                System.out.println("User deleted successfully.");
                return true; // Return true if at least one row was deleted
            } else {
                System.out.println("No user found with ID: " + user.getId());
                return false; // Return false if no rows were affected
            }
        } catch (SQLException e) {
            System.err.println("SQL error occurred during the delete operation: " + e.getMessage());
            return false;
        } finally {
            // Clean up JDBC objects
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing JDBC resources: " + e.getMessage());
            }
        }


    }

    @Override
    public void DeleteByID(int id) {

    }


}
