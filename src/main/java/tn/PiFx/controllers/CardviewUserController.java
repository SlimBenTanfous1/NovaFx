package tn.PiFx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import tn.PiFx.entities.User;
import tn.PiFx.services.ServiceUtilisateurs;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;


import java.sql.SQLException;


public class CardviewUserController {
    private final ServiceUtilisateurs UserS = new ServiceUtilisateurs();

    @FXML
    private TextField CinModifTF;
    @FXML
    private TextField AdresseModifTF;

    @FXML
    private TextField EmailModifTf;

    @FXML
    private TextField MdpModifTf;

    @FXML
    private TextField NomModifTf;

    @FXML
    private TextField NumTelModifTf;

    @FXML
    private TextField PrenomModifTf;

    @FXML
    private TextField ProfessionModifTf;

    @FXML
    private ComboBox<?> RolesModifCB;
    @FXML
    private Pane Card;
    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};

    int uid,unumtel;
    String unom, uprenom, uemail, umdp, urole;
    public void setData(User user){
        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");

        NomModifTf.setText(user.getNom());
        PrenomModifTf.setText(user.getPrenom());
        EmailModifTf.setText(user.getEmail());
        AdresseModifTF.setText(user.getAdresse());
        NumTelModifTf.setText(String.valueOf(user.getNum_tel()));
        ProfessionModifTf.setText(user.getProfession());
        MdpModifTf.setText(user.getPassword());
        //Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));



    }


    @FXML
    void SupprimerButtonUser(ActionEvent event) {

    }

    public void ModifierButtonUser(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            AdminUserController AUC = loader.getController();
            AUC.cinTF.setText(String.valueOf(uid));
            AUC.numtelTF.setText(String.valueOf(unumtel));
            AUC.nomTF.setText(unom);
            AUC.prenomTF.setText(uprenom);
            AUC.emailTF.setText(uemail);
            AUC.mdpTF.setText(umdp);
            //AUC.roleCOMBOBOX.setValue(urole);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



