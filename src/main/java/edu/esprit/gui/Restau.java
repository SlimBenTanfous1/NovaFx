package edu.esprit.gui;



import edu.esprit.entities.Devis;
import edu.esprit.service.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;
import javafx.scene.input.MouseEvent;


public class Restau {



    @FXML
    private ImageView img;

    @FXML
    private Label nom;
    private Devis r ;
    private MyListener myListener ;
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(r);
    }

    /*private Restaurant r ;
    private MyListener myListener ;*/


@FXML
    /*public void setData(Restaurant r,MyListener myListener) {
        this.r = r;
    nom.setText(r.getNomR());
    this.myListener = myListener;
    InputStream imageStream = getClass().getResourceAsStream(r.getImageR());
    if (imageStream != null) {
        Image image = new Image(imageStream);
        img.setImage(image);
    } else {
        System.out.println("Image non trouvée : " + r.getImageR());
        // Vous pouvez définir une image par défaut ici si vous le souhaitez
        // imageRestau.setImage(imageParDefaut);
    }
    }*/



public void setData(Devis r, MyListener myListener) {
    this.r = r;
    nom.setText(r.getName());

    this.myListener = myListener;



}


}



