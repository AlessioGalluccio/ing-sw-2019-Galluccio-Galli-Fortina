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
    @FXML
    public Button login;
    @FXML
    public ImageView enemy1card1;
    @FXML
    public ImageView enemy1card2;
    @FXML
    public ImageView enemy1card3;
    @FXML
    public ImageView enemy1First;
    @FXML
    public ImageView enemy1Actions;
    @FXML
    public ImageView enemy2card1;
    @FXML
    public ImageView enemy2card2;
    @FXML
    public ImageView enemy2card3;
    @FXML
    public ImageView enemy2First;
    @FXML
    public ImageView enemy2Actions;
    @FXML
    public ImageView enemy3card1;
    @FXML
    public ImageView enemy3card2;
    @FXML
    public ImageView enemy3card3;
    @FXML
    public ImageView enemy3First;
    @FXML
    public ImageView enemy3Actions;
    @FXML
    public ImageView enemy4card1;
    @FXML
    public ImageView enemy4card2;
    @FXML
    public ImageView enemy4card3;
    @FXML
    public ImageView enemy4First;
    @FXML
    public ImageView enemy4Actions;
    @FXML
    public Button cell02;
    @FXML
    public Button cell01;
    @FXML
    public Button cell00;
    @FXML
    public Button cell10;
    @FXML
    public Button cell11;
    @FXML
    public Button cell12;
    @FXML
    public Button cell22;
    @FXML
    public Button cell21;
    @FXML
    public Button cell20;
    @FXML
    public Button cell30;
    @FXML
    public Button cell31;
    @FXML
    public Button cell32;
    @FXML
    public ImageView ammoCell02;
    @FXML
    public ImageView ammoCell00;
    @FXML
    public ImageView ammoCell10;
    @FXML
    public ImageView ammoCell11;
    @FXML
    public ImageView ammoCell12;
    @FXML
    public ImageView ammoCell21;
    @FXML
    public ImageView ammoCell20;
    @FXML
    public ImageView ammoCell31;
    @FXML
    public ImageView ammoCell32;
    public ImageView imEnemyCharacter1;
    public ImageView imEnemyCharacter2;
    public ImageView imEnemyCharacter3;
    public ImageView imEnemyCharacter4;


    //ogni label o textfield ecc che vado a creare nel file fxml lo devo riportare come attributo nel controller
    //con lo stesso come dato in fx:id e in onAction metto il metodo

    @FXML
    private Label status;

    @FXML
    private TextField username;

    public void login(ActionEvent event) throws Exception{
        if(username.getText().equals("user")){
            status.setText("Login Success");

            // get a handle to the stage
            Stage stage = (Stage) login.getScene().getWindow();
            // do what you have to do
            stage.close();

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
            // get a handle to the stage
            Stage stage = (Stage) rbmap1.getScene().getWindow();
            // do what you have to do
            stage.close();
        }


        if(rbmap2.isSelected()){

            choosenMap = 2;
            System.out.println(choosenMap);
            // get a handle to the stage
            Stage stage = (Stage) rbmap2.getScene().getWindow();
            // do what you have to do
            stage.close();


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
            cell32.setDisable(true);
            cell00.setDisable(false);
            ammoCell32.setDisable(true);

        }
        if (choosenMap == 1) {
            mappa.setImage(new Image("mappa.jpg"));
            cell00.setDisable(true);
            cell32.setDisable(false);
            ammoCell00.setDisable(true);

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

        //set map's cells
        cell00.setStyle("-fx-background-color: transparent;");
        cell00.setGraphic(ammoCell00);
        cell01.setStyle("-fx-background-color: transparent;");
        cell01.setDisable(false);
        cell02.setStyle("-fx-background-color: transparent;");
        cell02.setGraphic(ammoCell02);
        cell02.setDisable(false);
        cell10.setStyle("-fx-background-color: transparent;");
        cell10.setGraphic(ammoCell10);
        cell10.setDisable(false);
        cell11.setStyle("-fx-background-color: transparent;");
        cell11.setGraphic(ammoCell11);
        cell11.setDisable(false);
        cell12.setStyle("-fx-background-color: transparent;");
        cell12.setGraphic(ammoCell12);
        cell12.setDisable(false);
        cell20.setStyle("-fx-background-color: transparent;");
        cell20.setGraphic(ammoCell20);
        cell20.setDisable(false);
        cell21.setStyle("-fx-background-color: transparent;");
        cell21.setGraphic(ammoCell21);
        cell21.setDisable(false);
        cell22.setStyle("-fx-background-color: transparent;");
        cell22.setDisable(false);
        cell30.setStyle("-fx-background-color: transparent;");
        cell30.setDisable(false);
        cell31.setStyle("-fx-background-color: transparent;");
        cell31.setGraphic(ammoCell31);
        cell31.setDisable(false);
        cell32.setStyle("-fx-background-color: transparent;");
        cell32.setGraphic(ammoCell32);


        //set enemycharacter
        //TODO cambiare dinamicamnete i personaggi
        enemyCharacter1.setGraphic(imEnemyCharacter1);
        enemyCharacter1.setStyle("-fx-background-color: transparent;");

        enemyCharacter2.setGraphic(imEnemyCharacter2);
        enemyCharacter2.setStyle("-fx-background-color: transparent;");

        enemyCharacter3.setGraphic(imEnemyCharacter3);
        enemyCharacter3.setStyle("-fx-background-color: transparent;");

        enemyCharacter4.setGraphic(imEnemyCharacter4);
        enemyCharacter4.setStyle("-fx-background-color: transparent;");






        //TODO togliere queste immagini servite solo a impostare i bottoni nella giusta posizione
        possibleActions.setVisible(true);
        yourCharacter.setVisible(true);
        possibleActions.setImage(new Image("characters/actionsBlue.jpg"));
        yourCharacter.setImage(new Image("characters/characterBlue.jpg"));




    }
}
