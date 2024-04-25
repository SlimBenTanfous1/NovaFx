package edu.esprit.gui;

import edu.esprit.entities.AvisRestau;
import edu.esprit.entities.Devis;
import edu.esprit.service.ServiceDevis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class BackAvis implements Initializable {

    @FXML
    private TableColumn<AvisRestau,String> commentR;

    @FXML
    private TableColumn<AvisRestau, String> nomR;
   @FXML
   private TableColumn<AvisRestau, String> responseR;

    @FXML
    private Button supprimer;

    @FXML
    private TableView<AvisRestau> tabAvis;

    int selectedId;
    @FXML
    TextField TFcommentR;

    ServiceDevis sa=new ServiceDevis();








    private void loadTableData() {
        ObservableList<AvisRestau> data = FXCollections.observableArrayList(sa.getAll());

        tabAvis.setItems(data);
    }

    public void updateTable_r() {
        List<AvisRestau> response = sa.getAll();
        tabAvis.getItems().setAll(response);
    }




    // Assurez-vous que cette ligne est bien présente dans votre classe

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomR.setCellValueFactory(new PropertyValueFactory<>("id"));
        commentR.setCellValueFactory(new PropertyValueFactory<AvisRestau, String>("response"));
        responseR.setCellValueFactory(new PropertyValueFactory<AvisRestau, String>("status"));


        loadTableData();

        tabAvis.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AvisRestau selectedAvis = tabAvis.getSelectionModel().getSelectedItem();
                selectedId = selectedAvis.getId();
                String selectedStatus = selectedAvis.getStatus();



            }
        });

        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sa.supprimer(selectedId);
                updateTable_r();
            }


        });
    }

}