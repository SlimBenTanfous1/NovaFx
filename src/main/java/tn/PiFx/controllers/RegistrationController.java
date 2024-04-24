package tn.PiFx.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.stage.Window;
import tn.PiFx.entities.User;
import tn.PiFx.services.ServiceUtilisateurs;
import tn.PiFx.utils.DataBase;
import tn.PiFx.utils.SessionManager;

public class RegistrationController {

    @FXML
    private TextField AdresseInscTf;

    @FXML
    private TextField ConfirmMdpInscTf;

    @FXML
    private TextField EmailInscTf;

    @FXML
    private TextField MdpInsTf;

    @FXML
    private TextField NomInscTf;

    @FXML
    private TextField NumTelInscTf;

    @FXML
    private TextField PrenomInscTf;

    private Connection cnx;
    private final ServiceUtilisateurs UserS = new ServiceUtilisateurs();
    public static final String ACCOUNT_SID = "AC19cce70cb19c2d9ece20819b3722d89f";
    public static final String AUTH_TOKEN = "b933ef29635257e886667b46e5c41b30";
    public static final String TWILIO_PHONE_NUMBER = "+441571597017";


    private void sendVerificationCode(String toPhoneNumber, String code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String fullPhoneNumber = "+216" + toPhoneNumber;
        Message.creator(
                new PhoneNumber(fullPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                "Your verification code is: " + code
        ).create();
    }

    @FXML
    void ConfirmerInscButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waves - Inscription");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }


