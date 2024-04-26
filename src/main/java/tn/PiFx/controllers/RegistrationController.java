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
import java.util.Optional;
import java.util.Random;
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
    private TextField CinInsTF;

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

    @FXML
    private Label reginfo;

    private Connection cnx;
    private final ServiceUtilisateurs UserS = new ServiceUtilisateurs();

    // Api SMS Slim
    public static final String ACCOUNT_SID = "AC19cce70cb19c2d9ece20819b3722d89f";
    public static final String AUTH_TOKEN = "b933ef29635257e886667b46e5c41b30";
    public static final String TWILIO_PHONE_NUMBER = "+441571597017";
    public String verificationCode;

    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
    private void sendVerificationCode(String toPhoneNumber, String code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String fullPhoneNumber = "+216" + toPhoneNumber;
        Message.creator(
                new PhoneNumber(fullPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                "Your verification code is: " + code
        ).create();
    }

    //Fin Api//





    @FXML
    void SeConnecterButtonInsc(ActionEvent event) {

    }

    @FXML
    public void ConfirmerInscButton(javafx.event.ActionEvent actionEvent) {
        int CIN = Integer.parseInt(CinInsTF.getText());
        String NOM = NomInscTf.getText();
        String PRENOM = PrenomInscTf.getText();
        String EMAIL = EmailInscTf.getText();
        String ADRESSE = AdresseInscTf.getText();
        String MDP = MdpInsTf.getText();
        //String CONFIRMMDP = ConfirmMdpInscTf.getText();
        int NUMTEL = Integer.parseInt(NumTelInscTf.getText());
        try {
            if (!UserS.isValidEmail(EmailInscTf.getText())) {
                reginfo.setText("Email est invalide");
            } else if (!(UserS.isValidPhoneNumber(Integer.parseInt(NumTelInscTf.getText())))) {
                reginfo.setText("N° Telephone est invalide");
            } else if (UserS.checkUserExists(EMAIL)) {
                reginfo.setText("Email déjà existe");
            } else {
                this.verificationCode = generateVerificationCode();
                sendVerificationCode(String.valueOf(NUMTEL), this.verificationCode);
                boolean isCodeVerified = false;
                while (!isCodeVerified) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Verification Code");
                    dialog.setHeaderText("Entrez le code de vérification envoyé à votre téléphone:");
                    dialog.setContentText("Code:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String inputCode = result.get();
                        if (inputCode.equals(this.verificationCode)) {
                            isCodeVerified = true;
                            UserS.Add(new User(0,CIN, NOM, PRENOM, EMAIL, ADRESSE, NUMTEL, MDP, "User"));
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                                Parent root = loader.load();
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setTitle("Nova");
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("Code est incorrect");
                            errorAlert.setContentText("Le code de vérification que vous avez entré est incorrect. Veuillez réessayer.");
                            errorAlert.showAndWait();
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Nova");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SeConnecterButtonInsc(javafx.event.ActionEvent actionEvent) {
    }
}


