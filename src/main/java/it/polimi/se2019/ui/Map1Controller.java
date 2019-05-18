package it.polimi.se2019.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Map1Controller {

    public ImageView imageview;

    public void showMap(ActionEvent actionEvent) {

        Image image = new Image("@../../../../../../../images/mapone.jpg");
        imageview.setImage(image);
    }

   /* public Label selectWeapon;
    public void selectWeapon(ActionEvent actionEvent) {
        selectWeapon.setText("Arma Selezionata");
    }
    */

}
