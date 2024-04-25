package tn.PiFx.controllers;
//--Imports--//
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
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
import java.util.ResourceBundle;
//--Imports End--//

public class AdminUserController implements Initializable  {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField adresseTF;
    @FXML
    public TextField cinTF;
    @FXML
    public TextField emailTF;
    @FXML
    public TextField mdpTF;
    @FXML
    public TextField nomTF;
    @FXML
    public TextField numtelTF;
    @FXML
    public TextField prenomTF;
    @FXML
    public TextField professionTF;
    @FXML
    public ComboBox<?> roleCOMBOBOX;
    @FXML
    private TextField uinfolabel;
    @FXML
    public GridPane userContainer;


    private Connection conx;
    private final ServiceUtilisateurs UserS = new ServiceUtilisateurs();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        load();
    }

    public void load() {
        int column = 0;
        int row = 1;
        try {
            userContainer.getChildren().clear();
            for (User user : UserS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardViewUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardviewUserController cardController = fxmlLoader.getController();
                cardController.setData(user);
                userBox.setUserData(user.getId());
                cardController.setAdminUserController(this);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void TriEmail(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        try {
            for (User user : UserS.TriparEmail()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardviewUserController cardC = fxmlLoader.getController();
                cardC.setData(user);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void TriNom(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        try {
            for (User user : UserS.TriparNom()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                Object cardC = fxmlLoader.getController();
               // cardC.setData(user);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void populateEditForm(User user) {
        if (user == null) {
            showAlert("Error", "User data is not available.", Alert.AlertType.ERROR);
            return;
        }
        cinTF.setText(String.valueOf(user.getCin()));
        nomTF.setText(user.getNom());
        prenomTF.setText(user.getPrenom());
        emailTF.setText(user.getEmail());
        adresseTF.setText(user.getAdresse());
        numtelTF.setText(String.valueOf(user.getNum_tel()));
        professionTF.setText(user.getProfession());
        mdpTF.setText(user.getPassword());
    }

    private boolean emailExists(String email) throws SQLException{
        conx = DataBase.getInstance().getConx();
        String query = "SELECT * FROM `user` WHERE email=?";
        PreparedStatement statement = conx.prepareStatement(query);
        statement.setString(1,email);
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
        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com|outlook\\.(com|tn|fr)|yahoo\\.(com|tn|fr))$")){
            if (numtelTF.getText().matches("\\d{8}")) {
                if (!emailExists(EMAIL)) {
                    UserS.Add(new User(CIN,NOM,PRENOM,EMAIL,ADRESSE,NUMTEL,MDP,ROLE,PROFESSION));
                    uinfolabel.setText("Ajout Effectué");
                    String subject = "Account confirmed !";
                    String body = String.format("Bonjour%s,\n\nVos informations ont bien été enregistrés.\n\nCordialement,", PRENOM);
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
        final String senderEmail = "slim.bentanfous@esprit.tn";
        final String senderPassword = "Salamlam2002!";

        String host = "smtp-mail.outlook.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.stattls.enable","true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Confirmation email sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    void refreshUserInterface() {
        load();
    }





    private boolean validateUserData(User user) {
        return false;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
