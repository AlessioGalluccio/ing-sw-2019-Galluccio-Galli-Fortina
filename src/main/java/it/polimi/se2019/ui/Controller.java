package it.polimi.se2019.ui;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;



public class Controller {
    @FXML
    public Label mylabel;

    public ImageView mappa;
    @FXML
    public Button RedWeapon;
    @FXML
    public ToggleGroup mappaGroup;
    @FXML
    public RadioButton rbmap1;
    @FXML
    public RadioButton rbmap2;
    @FXML
    public RadioButton rbmap3;
    @FXML
    public RadioButton rbmap4;
    public Button showMap;

    public static int choosenMap;
    public Button weaponDeck;
    public Button powerupDeck;
    public ImageView imweaponDeck;
    public ImageView impowerupDeck;
    public ImageView imPowerupCard1;
    public ImageView imPowerupCard2;
    public ImageView imPowerupCard3;
    public ImageView imWeaponCard1;
    public ImageView imWeaponCard2;
    public ImageView imWeaponCard3;


    //ogni label o textfield ecc che vado a creare nel file fxml lo devo riportare come attributo nel controller
    //con lo stesso come dato in fx:id e in onAction metto il metodo

    @FXML
    private Label status;

    @FXML
    private TextField username;

    public void login(ActionEvent event) throws Exception{
        if(username.getText().equals("user")){
            status.setText("Login Success");

            //apriamo così una seconda casella dopo il login
            /*
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Map1.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Mappa1");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
            */
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("chooseMap.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Choose Map");
            primaryStage.setScene(new Scene(root, 300, 300));
            primaryStage.show();



        }
        else
            status.setText("Login Fail");
    }

    public void chooseMap(ActionEvent event) throws Exception{


        //mappa.setImage(new Image("mappa.jpg"));

        if(rbmap1.isSelected()){

            choosenMap = 1;
            System.out.println(choosenMap);
        }


        if(rbmap2.isSelected()){

            choosenMap = 2;
            System.out.println(choosenMap);


        }

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Map1.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Map1");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();




    }


    public void showMap(ActionEvent event) {
        System.out.println(choosenMap);
        if (choosenMap == 2) {
            mappa.setImage(new Image("mappa2.jpg"));
        }
        if (choosenMap == 1) {
            mappa.setImage(new Image("mappa.jpg"));

        }
        showMap.setDisable(true);
        showMap.setVisible(false);
        impowerupDeck.setDisable(false);
        impowerupDeck.setImage(new Image("AD_powerups_IT_02.png"));
        imweaponDeck.setDisable(false);
        imweaponDeck.setImage(new Image("AD_weapons_IT_02.png"));
        weaponDeck.setDisable(false);
        powerupDeck.setDisable(false);
    }
}
