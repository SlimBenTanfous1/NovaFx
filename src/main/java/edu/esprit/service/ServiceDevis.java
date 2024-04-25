package edu.esprit.service;

import edu.esprit.entities.AvisRestau;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDevis implements IServiceDevis {

    Connection cnx = DataSource.getInstance().getCnx();
    private static final int STATIC_USER_ID = 2; // Utilisateur statique, vous devez remplacer cela par la gestion de l'utilisateur connecté


    @Override
    public void ajouter(Object o) {
        if (o instanceof AvisRestau) {
            AvisRestau avis = (AvisRestau) o;
            String req = "INSERT INTO response_devis(id,status,response) VALUES (?, ?,?)";
            try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
                //ps.setInt(1, STATIC_USER_ID);
                 ps.setInt(1, avis.getIdR());
                ps.setString(2, avis.getStatus());
                ps.setString(3, avis.getResponse());
                ps.executeUpdate();

                // Récupérer la clé primaire générée si nécessaire
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idAvis = generatedKeys.getInt(1);
                    avis.setId(idAvis);
                }

                System.out.println("Avis added!");
            } catch (SQLException e) {
                // Handle or log the exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void modifier(Object o) {
        if (o instanceof AvisRestau) {
            AvisRestau avis = (AvisRestau) o;
            String req = "UPDATE response_devis SET  Response  = ?,status = ? WHERE id = ? ";
            //AND idR = ?
            try (PreparedStatement ps = cnx.prepareStatement(req)) {

                ps.setInt(1, avis.getId());
                ps.setString(2, avis.getStatus());
                ps.setString(3, avis.getResponse());

             //   ps.setInt(3, avis.getIdR());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Avis with ID " + avis.getId() + " updated successfully!");
                } else {
                    System.out.println("No record found with ID " + avis.getId() + ". Update failed.");
                }
            } catch (SQLException e) {
                // Gérer ou journaliser l'exception
                e.printStackTrace();
            }
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM response_devis WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {

                System.out.println("restaurant with ID " + id + " deleted successfully!");
            } else {
                System.out.println("No record found with ID " + id + ". Deletion failed.");
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

    }

   /* @Override
    public  List<AvisRestau> getAll() {
        List<AvisRestau> avisList = new ArrayList<>();
        String req = "SELECT * FROM avisrestau";
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {
            while (res.next()) {
                int idR = res.getInt("idR");
                int idA = res.getInt("idA");

                String commentaire = res.getString("commentaire");

                AvisRestau avis = new AvisRestau(idR, idA, commentaire);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
        return avisList;
    }*/

    @Override
    public Object getOneById(int id) {
        return null;
    }


    public List<AvisRestau> getAll() {
        List<AvisRestau> avisList = new ArrayList<>();
        String req = "SELECT id,response,status FROM response_devis";
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {
            while (res.next()) {
                int id = res.getInt("id");
                String response = res.getString("response");
                String status = res.getString("status");

                AvisRestau avis = new AvisRestau(id,response,status);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            // Gérer ou enregistrer l'exception
            e.printStackTrace();
        }
        return avisList;
    }





}
