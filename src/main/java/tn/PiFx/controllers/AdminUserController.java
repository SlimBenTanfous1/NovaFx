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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
    private TextField professionTF;

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
        String PROFESSION = professionTF.getText();

        //String IMAGE = pdptf.getText();
        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com|outlook\\.(com|tn|fr)|yahoo\\.(com|tn|fr))$")){
            if (numtelTF.getText().matches("\\d{8}")) {
                if (!emailExists(EMAIL)) {
                    UserS.Add(new User(CIN,NOM,PRENOM,EMAIL,ADRESSE,NUMTEL,MDP,ROLE,PROFESSION));
                    uinfolabel.setText("Ajout Effectué");
                    String subject = "Confirmation of Information Update";
                    String body = String.format("Hello %s,\n\nYour information has been successfully updated.\n\nBest regards,", PRENOM);
                    sendEmailConfirmation(EMAIL, subject, body);
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
    private void sendEmailConfirmation(String recipient, String subject, String body){
        final String senderEmail = "novassurance@outlook.com";
        final String senderPassword = "changer@!0";

        String host = "smtp-mail.outlook.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    });
        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(senderEmail));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Confirmation email sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    /*
    @FXML
    void ModifierButton(ActionEvent event)  {
        int CIN = Integer.parseInt(.getText());
        String NOM = nomtf.getText();
        String PRENOM = prenomtf.getText();
        String EMAIL = emailtf.getText();
        String MDP = mdptf.getText();
        int NUMTEL = Integer.parseInt(numteltf.getText());
        String ROLE = (String) rolecb.getValue();
        String IMAGE = pdptf.getText();
        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com|outlook\\.(com|tn|fr)|yahoo\\.(com|tn|fr))$")) {
            if (numteltf.getText().matches("\\d{8}")) {
                UserS.Update(new Utilisateur(ID, NOM, PRENOM, EMAIL, MDP, NUMTEL, ROLE, IMAGE));
                uinfolabel.setText("Modificatiion à été effectué avec sucées!");
            } else {
                uinfolabel.setText("N° Telephone est invalide");
            }
        } else {
            uinfolabel.setText("Email est invalide");
        }

    }*/





}
