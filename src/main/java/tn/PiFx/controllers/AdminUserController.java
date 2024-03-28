package tn.PiFx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.sql.Connection;
import tn.PiFx.services.ServiceUtilisateurs;

import tn.PiFx.entities.User;
import tn.PiFx.utils.DataBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserController {
    @FXML
    private TextField adresseTF;

    @FXML
    private TextField cinTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField mdpTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField numtelTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private ComboBox<?> roleCOMBOBOX;
    @FXML
    private TextField uinfolabel;

    private Connection conx;
    private final ServiceUtilisateurs UserS = new ServiceUtilisateurs();


    private boolean emailExists(String email) throws SQLException {
        conx = DataBase.getInstance().getConx();
        String query = "SELECT * FROM `user` WHERE email=?";
        PreparedStatement statement = conx.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    @FXML
    void AjouterButton(ActionEvent event) throws SQLException{

        int CIN = Integer.parseInt(cinTF.getText());
        String NOM = nomTF.getText();
        String PRENOM = prenomTF.getText();
        String EMAIL = emailTF.getText();
        String ADRESSE = adresseTF.getText();
        String MDP = mdpTF.getText();
        int NUMTEL = Integer.parseInt(numtelTF.getText());
        String ROLE = (String) roleCOMBOBOX.getValue();
        //String IMAGE = pdptf.getText();
        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com)$")) {
            if (numtelTF.getText().matches("\\d{8}")) {
                if (!emailExists(EMAIL)) {
                    UserS.Add(new User(CIN,NOM,PRENOM,EMAIL,ADRESSE,NUMTEL,MDP,ROLE));
                    uinfolabel.setText("Ajout Effectué");
                } else {
                    uinfolabel.setText("Email existe déja");
                }
            } else {
                uinfolabel.setText("N° Télèphone est invalide");
            }
        } else {
            uinfolabel.setText("Email est invalide");
        }


    }

    @FXML
    void ModifierButton(ActionEvent event)  {

    }





}
