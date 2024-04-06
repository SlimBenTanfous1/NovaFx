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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
            userContainer.getChildren().clear(); // Clear existing content if refreshing

            for (User user : UserS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardViewUser.fxml"));

                Pane userBox = fxmlLoader.load();
                CardviewUserController cardController = fxmlLoader.getController();

                // Setting user data to the card's controller
                cardController.setData(user);

                // Storing the user ID in the userBox for retrieval later
                userBox.setUserData(user.getId());

                // Set the action for the 'Modify' button
               /* Button modifyButton = cardController.getModifyButton();
                modifyButton.setOnAction(event -> {
                    // When a modify button is clicked, fetch the user ID from the card
                    Pane card = (Pane) ((Node) event.getSource()).getParent();
                    int userId = (int) card.getUserData();

                });*/

                // Position the card on the grid
                if (column == 3) { // Assuming you want 3 cards per row
                    column = 0;
                    row++;
                }

                userContainer.add(userBox, column++, row); // Add the card to the grid
                GridPane.setMargin(userBox, new Insets(10)); // Set margin for styling
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /*@Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
    }*/
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



    @FXML
    void ModifierButton(ActionEvent event){

    }

    private int getCurrentUserId(ActionEvent event) {

        Button clickedButton = (Button) event.getSource(); // Cast to Button, the source of the event
        Pane userCard = (Pane) clickedButton.getParent(); // The card is the parent of the clicked button
        Integer userId = (Integer) userCard.getUserData(); // Retrieve the stored user ID
        if (userId == null) {
            throw new IllegalStateException("No user is currently selected.");
        }
        return userId;

    }

    private void refreshUserInterface() {
        userContainer.getChildren().clear();

        // Call the load method to refresh the user display
        load();
        // If you have a TableView or ListView, you would refresh it like this:
        // tableView.refresh(); or listView.setItems(...);

        // If you are using pagination or some other form of dynamic data loading,
        // you may need to reset or update those components as well.
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

    private User getUserFromForm() {
        try {
            int cin = Integer.parseInt(cinTF.getText());
            String nom = nomTF.getText();
            String prenom = prenomTF.getText();
            String email = emailTF.getText();
            String adresse = adresseTF.getText();
            String mdp = mdpTF.getText();
            int numtel = Integer.parseInt(numtelTF.getText());
            String role = roleCOMBOBOX.getValue() != null ? roleCOMBOBOX.getValue().toString() : "";
            String profession = professionTF.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || mdp.isEmpty() || role.isEmpty() || profession.isEmpty()) {
                showAlert("Validation Error", "Please fill in all the fields.", Alert.AlertType.ERROR);
                return null;
            }

            return new User(cin, nom, prenom, email, adresse, numtel, mdp, role, profession);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter valid numbers for CIN and Telephone.", Alert.AlertType.ERROR);
            return null;
        }
    }


}
