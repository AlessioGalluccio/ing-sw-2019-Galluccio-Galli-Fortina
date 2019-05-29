package it.polimi.se2019.ui;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;



public class Controller {
    @FXML
    public Label mylabel;
    @FXML
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
    @FXML
    public Button showMap;

    private static int choosenMap;
    @FXML
    public Button weaponDeck;
    @FXML
    public Button powerupDeck;
    @FXML
    public ImageView imweaponDeck;
    @FXML
    public ImageView impowerupDeck;
    @FXML
    public ImageView imPowerupCard1;
    @FXML
    public ImageView imPowerupCard2;
    @FXML
    public ImageView imPowerupCard3;
    @FXML
    public ImageView imWeaponCard1;
    @FXML
    public ImageView imWeaponCard2;
    @FXML
    public ImageView imWeaponCard3;
    @FXML
    public Button bPowerupCard1;
    @FXML
    public Button bPowerupCard2;
    @FXML
    public Button bPowerupCard3;
    @FXML
    public Button bWeaponCard1;
    @FXML
    public Button bWeaponCard2;
    @FXML
    public Button bWeaponCard3;
    @FXML
    public ImageView possibleActions;
    @FXML
    public ImageView yourCharacter;
    @FXML
    public ImageView imFirstPlayer;
    @FXML
    public Button enemyCharacter4;
    @FXML
    public Button enemyCharacter3;
    @FXML
    public Button enemyCharacter2;
    @FXML
    public Button enemyCharacter1;
    @FXML
    public Button bDiscardPowerup1;
    @FXML
    public Button bDiscardPowerup2;
    @FXML
    public Button bDiscardPowerup3;
    @FXML
    public Button bReloadWeapon1;
    @FXML
    public Button bReloadWeapon2;
    @FXML
    public Button bReloadWeapon3;
    @FXML
    public Button bDiscardWeapon1;
    @FXML
    public Button bDiscardWeapon2;
    @FXML
    public Button bDiscardWeapon3;


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

        //set the buttons stile and images for powerup deck and weapon deck
        impowerupDeck.setDisable(false);
        impowerupDeck.setImage(new Image("AD_powerups_IT_02.jpg"));
        imweaponDeck.setDisable(false);
        imweaponDeck.setImage(new Image("AD_weapons_IT_02.jpg"));
        weaponDeck.setDisable(false);
        weaponDeck.setGraphic(imweaponDeck);
        weaponDeck.setStyle("-fx-background-color: transparent;");
        powerupDeck.setGraphic(impowerupDeck);
        powerupDeck.setDisable(false);
        powerupDeck.setStyle("-fx-background-color: transparent;");

        //set the buttons stile and images for player's cards
        bPowerupCard1.setGraphic(imPowerupCard1);
        bPowerupCard1.setStyle("-fx-background-color: transparent;");
        bPowerupCard2.setGraphic(imWeaponCard2);
        bPowerupCard2.setStyle("-fx-background-color: transparent;");
        imPowerupCard3.setImage(new Image("emptyPowerup.jpg"));
        bPowerupCard3.setGraphic(imPowerupCard3);
        bPowerupCard3.setStyle("-fx-background-color: transparent;");
        imWeaponCard1.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard1.setGraphic(imWeaponCard1);
        bWeaponCard1.setStyle("-fx-background-color: transparent;");
        imWeaponCard2.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard2.setGraphic(imWeaponCard2);
        bWeaponCard2.setStyle("-fx-background-color: transparent;");
        imWeaponCard3.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard3.setGraphic(imWeaponCard3);
        bWeaponCard3.setStyle("-fx-background-color: transparent;");

        //add background


        /*
        // new Image(url)
        Image image = new Image("background.png");
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(800, 800, true, true, true, false);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);
        */


        //TODO togliere queste immagini servite solo a impostare i bottoni nella giusta posizione
        possibleActions.setVisible(true);
        yourCharacter.setVisible(true);
        possibleActions.setImage(new Image("characters/actionsBlue.jpg"));
        yourCharacter.setImage(new Image("characters/characterBlue.jpg"));




    }
}
