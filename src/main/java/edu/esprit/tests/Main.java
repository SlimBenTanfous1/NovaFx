package edu.esprit.tests;

import edu.esprit.entities.AvisRestau;
import edu.esprit.service.ServiceDevis;
import edu.esprit.utils.DataSource;
import javafx.event.ActionEvent;

import java.util.List;



public class Main {
    public static void main(String[] args) {
        try {
            DataSource.getInstance();
            ServiceDevis serviceAvis = new ServiceDevis();

            // Creating a sample AvisRestau

            AvisRestau avis = new AvisRestau();
            avis.setStatus("good");
            avis.setIdR(30);
            avis.setId(13);


            // Adding the AvisRestau using the ajouter method
            // serviceAvis.ajouter(avis);



            //serviceAvis.supprimer(15);

            System.out.println("Modifying AvisRestau...");
            serviceAvis.modifier(avis);
            System.out.println("AvisRestau Modified!");


            // Retrieving all AvisRestau
            List<AvisRestau> allAvis = serviceAvis.getAll();

            // Displaying the details of each AvisRestau
            for (AvisRestau a : allAvis) {
                System.out.println("ID Restaurant: " + a.getIdR() +
                        ", ID Avis: " + a.getId() +
                        ", Commentaire: " + a.getResponse());
            }
        } catch (Exception e) {
            // Handle or log the exception
            e.printStackTrace();
        }

    }
}



