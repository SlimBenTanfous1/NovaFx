package edu.esprit.gui;

import edu.esprit.entities.AvisRestau;
import edu.esprit.service.ServiceDevis;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;
import edu.esprit.gui.Avis;
public class Chart implements Initializable {

    @FXML
    private PieChart pieChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Récupérer la liste de devis (à remplacer par la vraie liste)
        ServiceDevis serviceDevis = new ServiceDevis();
        List<AvisRestau> listeDevis = serviceDevis.getAll();


        // Créer une instance de la classe Chart
        Chart chart = new Chart();

        // Calculer les statistiques à partir de la liste de devis
        int[] statistiques = chart.calculerStatistiques(listeDevis);

        // Créer les données pour le PieChart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Traité", statistiques[0]),
                        new PieChart.Data("Non traité", statistiques[1])
                );

        // Liaison des noms et valeurs des données pour l'affichage dans le PieChart
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " : ", data.pieValueProperty()
                        )
                )
        );

        // Ajouter les données au PieChart
        pieChart.getData().addAll(pieChartData);
    }

    private int[] calculerStatistiques(List<AvisRestau> listeDevis) {
        // Initialiser les compteurs pour les devis traités et non traités
        int traiteCount = 0;
        int nonTraiteCount = 0;

        // Parcourir la liste de devis
        for (AvisRestau devis : listeDevis) {
            // Vérifier le statut de chaque devis et incrémenter les compteurs en conséquence
            if (devis.getStatus().equalsIgnoreCase("101")) {
                traiteCount++;
            } else if (devis.getStatus().equalsIgnoreCase("100")) {
                nonTraiteCount++;
            }
        }

        // Retourner les résultats sous forme de tableau
        return new int[]{traiteCount, nonTraiteCount};
    }

}