package tn.PiFx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import tn.PiFx.entities.User;
import tn.PiFx.services.ServiceUtilisateurs;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CardviewUserController implements Initializable {
    private final ServiceUtilisateurs UserS = new ServiceUtilisateurs();

    @FXML
    private TextField idModifTF;
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
    public Button ModifyButton;

    @FXML

    private TextField ProfessionModifTf;

    @FXML
    private ComboBox<String> RolesModifCB;
    @FXML
    private Pane Card;
    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};

    int uid,unumtel;
    String unom, uprenom, uemail, umdp, urole;
    private AdminUserController adminUserController;
    private User currentUser;
    ObservableList<String> RoleList = FXCollections.observableArrayList("User", "Admin");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RolesModifCB.setItems(RoleList);

    }

    public void setData(User user){

        this.currentUser = user;
        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");
        idModifTF.setText(String.valueOf(user.getId()));
        CinModifTF.setText(String.valueOf(user.getCin()));
        NomModifTf.setText(user.getNom());
        PrenomModifTf.setText(user.getPrenom());
        EmailModifTf.setText(user.getEmail());
        AdresseModifTF.setText(user.getAdresse());
        NumTelModifTf.setText(String.valueOf(user.getNum_tel()));
        ProfessionModifTf.setText(user.getProfession());
        MdpModifTf.setText(user.getPassword());
        RolesModifCB.setValue(user.getRoles());
        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));

    }



    @FXML
    void SupprimerButtonUser(ActionEvent event) {
        try {
            // Assuming you have a TextField or similar widget to get the user's ID for deletion
            int userId = Integer.parseInt(idModifTF.getText());  // Replace 'idModifTF' with your actual TextField's ID

            if (userId > 0) {
                User userToDelete = new User();
                userToDelete.setId(userId);  // Set the ID on a User object, assuming User class has this method

                // Call the delete method
                boolean isDeleted = UserS.Delete(userToDelete);
                if (isDeleted) {
                    showAlert("Success", "User successfully deleted.", Alert.AlertType.INFORMATION);
                    // Optional: Update UI to reflect the removal
                } else {
                    showAlert("Error", "Failed to delete the user. No user found with ID: " + userId, Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Error", "Invalid user ID.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid user ID format. Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while deleting the user: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    @FXML
    void ModifierButtonUser(ActionEvent event) throws SQLException {
        System.out.println("test0");

        try {

            int id = Integer.parseInt(idModifTF.getText());
            int cin = Integer.parseInt(CinModifTF.getText());
            String nom = NomModifTf.getText();
            String prenom = PrenomModifTf.getText();
            String email = EmailModifTf.getText();
            String adresse = AdresseModifTF.getText();
            String mdp = MdpModifTf.getText();
            int numtel = Integer.parseInt(NumTelModifTf.getText());
            String role = RolesModifCB.getValue() != null ? RolesModifCB.getValue().toString() : "";
            String profession = ProfessionModifTf.getText();


            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || mdp.isEmpty() || role.isEmpty() || profession.isEmpty()) {
                showAlert("Validation Error", "Please fill in all the fields.", Alert.AlertType.ERROR);
            }

            else {
                UserS.Update(new User(id,cin,nom, prenom, adresse, email, numtel, mdp, role, profession));

                System.out.println("test1");
                adminUserController.populateEditForm(this.currentUser);
            }

        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter valid numbers for CIN and Telephone.", Alert.AlertType.ERROR);
        }


    }

    public void setAdminUserController(AdminUserController controller) {
        this.adminUserController = controller;
    }
    


    private void updateUserInDatabase(User user) {
        boolean updateSuccess = UserS.Update(user);
        if (updateSuccess) {
            adminUserController.refreshUserInterface();
        } else {
            showAlert("Update Error", "Failed to update user information.", Alert.AlertType.ERROR);
        }    }

    private void showAlert(String validationError, String s, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setTitle(validationError);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }


    public Button getModifyButton() {
        return ModifyButton;
    }
}



