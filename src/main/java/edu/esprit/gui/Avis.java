package edu.esprit.gui;


import edu.esprit.entities.AvisRestau;
import edu.esprit.entities.Devis;
import edu.esprit.service.ServiceDevis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class Avis implements Initializable {


    @FXML
    private Button ajouter;


    @FXML
    private TextField sasie;

    @FXML
    private  TextField sasie1;

    @FXML
    private VBox comentbox;

    @FXML
    private Label nbdislike;

    @FXML
    private Label nblike;



    private ServiceDevis sa = new ServiceDevis();
    private List<AvisRestau> la;
    private Devis r =new Devis() ;

    private AvisRestau avis=new  AvisRestau() ;



    public String bad_words(String badWord) {

        List<String> badListW = Arrays.asList("fuck", "bitch","motherfucker","zebi","zabour","9ahba","khahba");
        String badNew = "";
        List<String> newList = new ArrayList<>();
        for (String str : badListW) {
            if (badWord.contains(str)) {
                badNew += "" + str;
                if (str.length() >= 1) {
                    StringBuilder result = new StringBuilder();
                    result.append(str.charAt(0));
                    for (int i = 0; i < str.length() - 2; ++i) {
                        result.append("*");
                    }
                    result.append(str.charAt(str.length() - 1));
                    str = result.toString();
                    if (!str.isEmpty()) {
                        System.out.println("ATTENTION !! Vous avez écrit un gros mot  : " + result + " .C'est un avertissement ! Priére d'avoir un peu de respect ! Votre description sera envoyée comme suit :");
                        System.out.println(badWord.replace(badNew, "") + " ");
                    }
                }
                Notifications.create()
                        .darkStyle()
                        .title("ATTENTION !! Vous avez écrit un gros mot ")
                        .hideAfter(Duration.seconds(10))
                        .show();
            }
        }
        return (badWord.replace(badNew, "") + " ");
    }





    @FXML
    void ajouterAvisAction(ActionEvent event) {
        // Retrieve values from text fields
        String status = sasie.getText();
        String response=sasie1.getText();

        status = bad_words(status);

        // Create a Restaurant object
       AvisRestau avis = new AvisRestau();
       avis.setStatus(status);
       avis.setResponse(response);
        avis.setIdR(r.getId());

       avis.setIdR(34);


        // Call the ajouter method of ServiceRestau to add the restaurant to the database
        ServiceDevis service = new ServiceDevis();
        service.ajouter(avis);
        // Clear fields and reset selected image
        show();
        sasie.clear();
        sasie1.clear();
    }

    @FXML
    private void clickLike(MouseEvent mouseEvent) {

        // Create a Restaurant object
        AvisRestau avis = new AvisRestau();

        //avis.setIdR(r.getIdR());
        avis.setIdR(34);
        avis.setId(30);
        avis.setStatus("a7sen restau");

        // Call the ajouter method of ServiceRestau to add the restaurant to the database
        ServiceDevis service = new ServiceDevis();
        service.modifier(avis);


        // Clear fields and reset selected image
        show();

    }















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
    }


    public void show(){
       comentbox.getChildren().clear();
        la = sa.getAll();


        for (int i = 0; i < la.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/unAvis.fxml"));

            try {
                AnchorPane anchorPane = fxmlLoader.load();
                UnAvis item = fxmlLoader.getController();
                item.setData(la.get(i));


                comentbox.getChildren().add(anchorPane);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


