package it.polimi.se2019.ui;




import it.polimi.se2019.MyThread;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellAmmo;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.Points;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.clientView.ClientMapView;
import it.polimi.se2019.view.clientView.ClientSkullBoardView;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.remoteView.SkullBoardView;
import javafx.application.Platform;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    //imageview of players' posizion on map
    public ImageView imPlayer1Cell00;
    public ImageView imPlayer2Cell00;
    public ImageView imPlayer3Cell00;
    public ImageView imPlayer4Cell00;
    public ImageView imPlayer5Cell00;
    public ImageView imPlayer1Cell01;
    public ImageView imPlayer2Cell01;
    public ImageView imPlayer3Cell01;
    public ImageView imPlayer4Cell01;
    public ImageView imPlayer5Cell01;
    public ImageView imPlayer1Cell02;
    public ImageView imPlayer2Cell02;
    public ImageView imPlayer3Cell02;
    public ImageView imPlayer4Cell02;
    public ImageView imPlayer5Cell02;
    public ImageView imPlayer1Cell03;
    public ImageView imPlayer2Cell03;
    public ImageView imPlayer3Cell03;
    public ImageView imPlayer4Cell03;
    public ImageView imPlayer5Cell03;
    public ImageView imPlayer1Cell10;
    public ImageView imPlayer2Cell10;
    public ImageView imPlayer3Cell10;
    public ImageView imPlayer4Cell10;
    public ImageView imPlayer5Cell10;
    public ImageView imPlayer1Cell11;
    public ImageView imPlayer2Cell11;
    public ImageView imPlayer3Cell11;
    public ImageView imPlayer4Cell11;
    public ImageView imPlayer5Cell11;
    public ImageView imPlayer1Cell12;
    public ImageView imPlayer2Cell12;
    public ImageView imPlayer3Cell12;
    public ImageView imPlayer4Cell12;
    public ImageView imPlayer5Cell12;
    public ImageView imPlayer1Cell13;
    public ImageView imPlayer2Cell13;
    public ImageView imPlayer3Cell13;
    public ImageView imPlayer4Cell13;
    public ImageView imPlayer5Cell13;
    public ImageView imPlayer1Cell20;
    public ImageView imPlayer2Cell20;
    public ImageView imPlayer3Cell20;
    public ImageView imPlayer4Cell20;
    public ImageView imPlayer5Cell20;
    public ImageView imPlayer1Cell21;
    public ImageView imPlayer2Cell21;
    public ImageView imPlayer3Cell21;
    public ImageView imPlayer4Cell21;
    public ImageView imPlayer5Cell21;
    public ImageView imPlayer1Cell22;
    public ImageView imPlayer2Cell22;
    public ImageView imPlayer3Cell22;
    public ImageView imPlayer4Cell22;
    public ImageView imPlayer5Cell22;
    public ImageView imPlayer1Cell32;
    public ImageView imPlayer2Cell32;
    public ImageView imPlayer3Cell32;
    public ImageView imPlayer4Cell32;
    public ImageView imPlayer5Cell32;
    public Tab tabEnemy1;
    public Tab tabEnemy2;
    public Tab tabEnemy3;
    public Tab tabEnemy4;
    public RadioButton rbSprog;
    public RadioButton rbViolet;
    public RadioButton rbStruct;
    public RadioButton rbDozer;
    public RadioButton rbBanshee;


    private ArrayList<ImageView> skull = new ArrayList<>();
    public Button fireButton;
    public Button endTurnButton;
    public Label yourPointsLabel;
    public AnchorPane myContainer;
    public TabPane container1;
    public TabPane container2;
    public AnchorPane enemy1back;



    ControllerLogin controllerLogin;

    @FXML
    public Button moveButton;
    @FXML
    public Button grabButton;
    @FXML
    public Button shootButton;
    public Button addFire1;
    public Button addFire2;
    public Button addFire3;
    public RadioButton firemode1;
    public RadioButton firemode2;
    public RadioButton firemodeOp1;
    public RadioButton firemodeOp2;
    public RadioButton firemodeOp3;
    public ToggleGroup AddFiremode;
    public TextField skulls;
    public Label errorSkulls;
    public RadioButton suddenDeathYes;
    public RadioButton suddenDeathNo;
    public Button sendButton;


    @FXML
    MyThread myThread;

    @FXML
    public Label mylabel;
    @FXML
    public ImageView mappa;
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

    public static int choosenMap =0;
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
    public ImageView imPowerupCard4;
    @FXML
    public ImageView imWeaponCard1;
    @FXML
    public ImageView imWeaponCard2;
    @FXML
    public ImageView imWeaponCard3;
    @FXML
    public ImageView imWeaponCard4;
    @FXML
    public Button bPowerupCard1;
    @FXML
    public Button bPowerupCard2;
    @FXML
    public Button bPowerupCard3;
    @FXML
    public Button bPowerupCard4;
    @FXML
    public Button bWeaponCard1;
    @FXML
    public Button bWeaponCard2;
    @FXML
    public Button bWeaponCard3;
    @FXML
    public Button bWeaponCard4;
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
    public Button bDiscardPowerup4;
    @FXML
    public Button bReloadWeapon1;
    @FXML
    public Button bReloadWeapon2;
    @FXML
    public Button bReloadWeapon3;
    @FXML
    public ImageView imReload1;
    @FXML
    public ImageView imReload2;
    @FXML
    public ImageView imReload3;
    @FXML
    public Button bDiscardWeapon1;
    @FXML
    public Button bDiscardWeapon2;
    @FXML
    public Button bDiscardWeapon3;

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
    @FXML
    public ImageView imEnemyCharacter1;
    @FXML
    public ImageView imEnemyCharacter2;
    @FXML
    public ImageView imEnemyCharacter3;
    @FXML
    public ImageView imEnemyCharacter4;
    @FXML
    public ImageView mappa1prev;
    @FXML
    public ImageView mappa2prev;
    @FXML
    public ImageView mappa3prev;
    @FXML
    public ImageView mappa4prev;
    @FXML
    public ImageView imRedWeapon1;
    @FXML
    public ImageView imRedWeapon2;
    @FXML
    public ImageView imRedWeapon3;
    @FXML
    public ImageView imBlueWeapon1;
    @FXML
    public ImageView imBlueWeapon2;
    @FXML
    public ImageView imBlueWeapon3;
    @FXML
    public ImageView imYellowWeapon1;
    @FXML
    public ImageView imYellowWeapon2;
    @FXML
    public ImageView imYellowWeapon3;
    @FXML
    public Button redWeapon2;
    @FXML
    public Button redWeapon3;
    @FXML
    public Button yellowWeapon1;
    @FXML
    public Button yellowWeapon2;
    @FXML
    public Button yellowWeapon3;
    @FXML
    public Button blueWeapon1;
    @FXML
    public Button blueWeapon2;
    @FXML
    public Button blueWeapon3;
    @FXML
    public Button redWeapon1;
    public ImageView enemy1Damage0;
    public ImageView enemy1Damage1;
    public ImageView enemy1Damage2;
    public ImageView enemy1Damage3;
    public ImageView enemy1Damage4;
    public ImageView enemy1Damage5;
    public ImageView enemy1Damage6;
    public ImageView enemy1Damage7;
    public ImageView enemy1Damage8;
    public ImageView enemy1Damage9;
    public ImageView enemy1Damage10;
    public ImageView enemy1Damage11;
    public ImageView enemy2Damage0;
    public ImageView enemy2Damage1;
    public ImageView enemy2Damage2;
    public ImageView enemy2Damage3;
    public ImageView enemy2Damage4;
    public ImageView enemy2Damage5;

    @FXML
    public Button selectWeaponButton1;
    @FXML
    public Button selectWeaponButton2;
    @FXML
    public Button selectWeaponButton3;
    @FXML
    public Button selectWeaponButton4;

    @FXML
    public ImageView imTrashP1;
    @FXML
    public ImageView imTrashP2;
    @FXML
    public ImageView imTrashP3;
    @FXML
    public ImageView imTrashP4;
    @FXML
    public ImageView imTrashW1;
    @FXML
    public ImageView imTrashW2;
    @FXML
    public ImageView imTrashW3;
    @FXML
    public ImageView imTrashW4;

    @FXML
    public ImageView imSkull1;
    @FXML
    public ImageView imSkull2;
    @FXML
    public ImageView imSkull3;
    @FXML
    public ImageView imSkull4;
    @FXML
    public ImageView imSkull5;
    @FXML
    public ImageView imSkull6;
    @FXML
    public ImageView imSkull7;
    @FXML
    public ImageView imSkull8;

    @FXML
    public ImageView bammo1;
    @FXML
    public ImageView bammo2;
    @FXML
    public ImageView bammo3;
    @FXML
    public ImageView rammo1;
    @FXML
    public ImageView rammo2;
    @FXML
    public ImageView rammo3;
    @FXML
    public ImageView yammo1;
    @FXML
    public ImageView yammo2;
    @FXML
    public ImageView yammo3;





    private int suddenDeath = 2;
    private int init_skull = 0;

    @FXML
    public Label labelProva;

    String transparent = "-fx-background-color: transparent;";

    //public static void setClientView(ClientView clientViev) {
        //clientView=clientViev;
    //}


    //ogni label o textfield ecc che vado a creare nel file fxml lo devo riportare come attributo nel controller
    //con lo stesso come dato in fx:id e in onAction metto il metodo


    public void skullCheck(ActionEvent event){

        init_skull = Integer.parseInt(skulls.getText());
        if(init_skull <5 ||init_skull  >8){
            errorSkulls.setVisible(true);
        }
        else
            errorSkulls.setVisible(false);

        System.out.println("Init skulls:" + init_skull );


    }


    public void chooseMap(ActionEvent event){

        if(rbmap1.isSelected()){
            choosenMap = 1;
            System.out.println(choosenMap);
        }

        if(rbmap2.isSelected()){
            choosenMap = 2;
            System.out.println(choosenMap);
        }

        if(rbmap3.isSelected()){
            choosenMap = 3;
            System.out.println(choosenMap);
        }

        if(rbmap4.isSelected()){
            choosenMap = 4;
            System.out.println(choosenMap);
        }

    }


    public void sendSetting(ActionEvent event) throws Exception{


        //mappa.setImage(new Image("mappa.jpg"));

        if(suddenDeathYes.isSelected()){
            suddenDeath=1;
            System.out.println("Yes sudden death");
        }
        if(suddenDeathNo.isSelected()){
            suddenDeath=0;
            System.out.println("No sudden death");
        }

        if(((choosenMap==1)||(choosenMap==2)||(choosenMap==3)||(choosenMap==4))&&((init_skull >=5)&&(init_skull <=8))&&((suddenDeath==0) || (suddenDeath==1))){
            boolean sd;
            if(suddenDeath==0) {
                sd=false;
            }
            else {
                sd = true;
            }
            System.out.println("recap");
            System.out.println(choosenMap);
            System.out.println("Skulls: " + init_skull );
            System.out.println(suddenDeath);
            ControllerLogin.clientView.createSettingMessage(choosenMap,init_skull ,sd);

            Stage stage = (Stage) rbmap2.getScene().getWindow();
            stage.close();
            ControllerLogin.open("WaitingRoom.fxml","LEAN BACK AND CHILL", 520, 400);

        }



    }



    public void showMap(ActionEvent event) throws InterruptedException{

        //set ammo card on map
        updateAmmoCardMap();
        // set enemy nickname on window
        setEnemyNickname();

        // set weapon card on map
        updateWeaponMap();
        //set skull on map
        String skullnum = Integer.toString(ControllerLogin.skullBoardView.getNumSkullCopy());
        updateSkullBoard(skullnum);

        setPlayerOnMap();

        setDiscardReload();



        System.out.println(choosenMap);

        if (choosenMap == 2) {
            mappa.setImage(new Image("mappa2.jpg"));
            cell32.setDisable(true);
            cell00.setDisable(false);
            ammoCell32.setDisable(true);
            ammoCell32.setVisible(false);

        }

        if (choosenMap == 1) {
            mappa.setImage(new Image("mappa.jpg"));
            cell00.setDisable(true);
            cell32.setDisable(false);
            ammoCell00.setDisable(true);
            ammoCell00.setVisible(false);

        }

        if(choosenMap == 3){
            mappa.setImage(new Image("mappa3.jpg"));

        }

        if(choosenMap == 4){
            mappa.setImage(new Image("mappa4.jpg"));
            cell32.setDisable(true);
            ammoCell32.setDisable(true);
            ammoCell32.setVisible(false);
            cell00.setDisable(true);
            ammoCell00.setDisable(true);
            ammoCell00.setVisible(false);

        }

        showMap.setDisable(true);
        showMap.setVisible(false);

        //set the buttons stile and images for powerup deck and weapon deck
        setDeck();

        //set the buttons stile and images for player's cards
        setPlayerCards();

        //add background

        BackgroundImage myBI= new BackgroundImage(new Image("background.png",32,32,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        //then you set to your node
        myContainer.setBackground(new Background(myBI));


        //set map's cells
        setCellsMap();

        //set enemycharacter

        setEnemyCharacter();

        //set Weapons

        setWeaponMap();

        //set palyer's actions buttons

        setAction();



        //TODO togliere queste immagini servite solo a impostare i bottoni nella giusta posizione


        labelProva.setText("BENVENUTO");


        myThread = new MyThread(this);
        myThread.start();



    }





    /**
     * set actions' buttons for the player
     */
    public void setAction(){

        moveButton.setStyle(transparent);
        grabButton.setStyle(transparent);
        shootButton.setStyle(transparent);


    }


    /**
     * set weapon cards buttons on the map
     */
    public void setWeaponMap(){

        redWeapon1.setGraphic(imRedWeapon1);
        redWeapon1.setStyle(transparent);

        redWeapon2.setGraphic(imRedWeapon2);
        redWeapon2.setStyle(transparent);

        redWeapon3.setGraphic(imRedWeapon3);
        redWeapon3.setStyle(transparent);

        blueWeapon1.setGraphic(imBlueWeapon1);
        blueWeapon1.setStyle(transparent);

        blueWeapon2.setGraphic(imBlueWeapon2);
        blueWeapon2.setStyle(transparent);

        blueWeapon3.setGraphic(imBlueWeapon3);
        blueWeapon3.setStyle(transparent);

        yellowWeapon1.setGraphic(imYellowWeapon1);
        yellowWeapon1.setStyle(transparent);

        yellowWeapon2.setGraphic(imYellowWeapon2);
        yellowWeapon2.setStyle(transparent);

        yellowWeapon3.setGraphic(imYellowWeapon3);
        yellowWeapon3.setStyle(transparent);


    }

    /**
     * set map's cells
     */
    public void setCellsMap(){

        cell00.setStyle(transparent);
        cell00.setGraphic(ammoCell00);
        cell01.setStyle(transparent);
        cell01.setDisable(false);
        cell02.setStyle(transparent);
        cell02.setGraphic(ammoCell02);
        cell02.setDisable(false);
        cell10.setStyle(transparent);
        cell10.setGraphic(ammoCell10);
        cell10.setDisable(false);
        cell11.setStyle(transparent);
        cell11.setGraphic(ammoCell11);
        cell11.setDisable(false);
        cell12.setStyle(transparent);
        cell12.setGraphic(ammoCell12);
        cell12.setDisable(false);
        cell20.setStyle(transparent);
        cell20.setGraphic(ammoCell20);
        cell20.setDisable(false);
        cell21.setStyle(transparent);
        cell21.setGraphic(ammoCell21);
        cell21.setDisable(false);
        cell22.setStyle(transparent);
        cell22.setDisable(false);
        cell30.setStyle(transparent);
        cell30.setDisable(false);
        cell31.setStyle(transparent);
        cell31.setGraphic(ammoCell31);
        cell31.setDisable(false);
        cell32.setStyle(transparent);
        cell32.setGraphic(ammoCell32);


        moveButton.setStyle(transparent);
        shootButton.setStyle(transparent);
        grabButton.setStyle(transparent);

    }

    /**
     * set enemys' characters buttons
     */
    public void setEnemyCharacter(){

        enemyCharacter1.setGraphic(imEnemyCharacter1);
        enemyCharacter1.setStyle(transparent);

        enemyCharacter2.setGraphic(imEnemyCharacter2);
        enemyCharacter2.setStyle(transparent);

        enemyCharacter3.setGraphic(imEnemyCharacter3);
        enemyCharacter3.setStyle(transparent);

        enemyCharacter4.setGraphic(imEnemyCharacter4);
        enemyCharacter4.setStyle(transparent);
    }

    /**
     * set the buttons stile and images for powerup deck and weapon deck
     */
    public void setDeck(){

        impowerupDeck.setDisable(false);
        impowerupDeck.setImage(new Image("AD_powerups_IT_02.jpg"));
        imweaponDeck.setDisable(false);
        imweaponDeck.setImage(new Image("AD_weapons_IT_02.jpg"));
        weaponDeck.setDisable(false);
        weaponDeck.setGraphic(imweaponDeck);
        weaponDeck.setStyle(transparent);
        powerupDeck.setGraphic(impowerupDeck);
        powerupDeck.setDisable(false);
        powerupDeck.setStyle(transparent);
    }

    /**
     * set the buttons stile and images for player's cards
     */
    public void setPlayerCards(){
        bPowerupCard1.setGraphic(imPowerupCard1);
        bPowerupCard1.setStyle(transparent);
        bPowerupCard2.setGraphic(imWeaponCard2);
        bPowerupCard2.setStyle(transparent);
        imPowerupCard3.setImage(new Image("emptyPowerup.jpg"));
        bPowerupCard3.setGraphic(imPowerupCard3);
        bPowerupCard3.setStyle(transparent);
        imWeaponCard1.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard1.setGraphic(imWeaponCard1);
        bWeaponCard1.setStyle(transparent);
        imWeaponCard2.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard2.setGraphic(imWeaponCard2);
        bWeaponCard2.setStyle(transparent);
        imWeaponCard3.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard3.setGraphic(imWeaponCard3);
        bWeaponCard3.setStyle(transparent);
        imPowerupCard4.setImage(new Image("emptyPowerup.jpg"));
        bPowerupCard4.setGraphic(imPowerupCard4);
        bPowerupCard4.setStyle(transparent);



    }

    /**
     * set trash's image on power up discard button and reload image on reload button
     */
    public void setDiscardReload(){
        bDiscardPowerup1.setGraphic(imTrashP1);
        bDiscardPowerup1.setStyle(transparent);
        bDiscardPowerup2.setGraphic(imTrashP2);
        bDiscardPowerup2.setStyle(transparent);
        bDiscardPowerup3.setGraphic(imTrashP3);
        bDiscardPowerup3.setStyle(transparent);
        bDiscardPowerup4.setGraphic(imTrashP4);
        bDiscardPowerup4.setStyle(transparent);

        bDiscardWeapon1.setGraphic(imTrashW1);
        bDiscardWeapon1.setStyle(transparent);
        bDiscardWeapon2.setGraphic(imTrashW2);
        bDiscardWeapon2.setStyle(transparent);
        bDiscardWeapon3.setGraphic(imTrashW3);
        bDiscardWeapon3.setStyle(transparent);

        bReloadWeapon1.setGraphic(imReload1);
        bReloadWeapon1.setStyle(transparent);
        bReloadWeapon2.setGraphic(imReload2);
        bReloadWeapon2.setStyle(transparent);
        bReloadWeapon3.setGraphic(imReload3);
        bReloadWeapon3.setStyle(transparent);

    }

    public void setPlayerOnMap(){



    }


    /**
     * open Add Firemode's window when the player clicks on add firemode button
     * @param event
     * @throws Exception
     */
    public void addFiremode(ActionEvent event) throws Exception {

        ControllerLogin.open("addFiremode.fxml","ADD FIREMODE",350,400);

    }

    /**
     * send FireModeMessage the the player choose the weapon's firemode on gui
     * @param event
     */
    public void sendFiremode(ActionEvent event) {
        if(firemode1.isSelected()) {
            ControllerLogin.clientView.createFireModeMessage(1);
            Stage stage = (Stage) firemode1.getScene().getWindow();
            stage.close();
        }
        if(firemode2.isSelected()){
            ControllerLogin.clientView.createFireModeMessage(2);
            Stage stage = (Stage) firemode1.getScene().getWindow();
            stage.close();
        }

    }


    /**
     * send OptionalMessage the the player choose the weapon's optional firemode on gui
     * @param event
     */
    public void sendOptional(ActionEvent event) {

        if(firemodeOp1.isSelected()){
            ControllerLogin.clientView.createOptionalMessage(1);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }
        if(firemodeOp2.isSelected()){
            ControllerLogin.clientView.createOptionalMessage(2);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }
        if(firemodeOp2.isSelected()){
            ControllerLogin.clientView.createOptionalMessage(3);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }

    }


    /**
     * send weapon message with ClientView's method to the server, when the player clicks on a player's weaponcard button
     * @param event
     */
    public void selectWeapon(ActionEvent event) {
        Player player = ControllerLogin.clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if(selectWeaponButton1 == source){
                ControllerLogin.clientView.createWeaponMessage(weapons.get(0));

            }

            if(selectWeaponButton2 == source){

                ControllerLogin.clientView.createWeaponMessage(weapons.get(1));

            }

            if(selectWeaponButton3 == source){
                ControllerLogin.clientView.createWeaponMessage(weapons.get(2));


            }

        }
        catch (NullPointerException e){
            //TODO

        }


    }

    /**
     * send Fire message with ClientView's method to the server, when the player clicks on FIRE button
     * @param event
     */
    public void fire(ActionEvent event) {

        ControllerLogin.clientView.createFireMessage();
    }

    /**
     * send PassTurn message with ClientView's method to the server, when the player clicks End Turn's button
     * @param event
     */
    public void endTurn(ActionEvent event) {
        ControllerLogin.clientView.createPassTurnMessage();

    }

    public void printSomething(ActionEvent event) {
        System.out.println("FUNZIONA");

    }


    /**
     * send ActionMessage with ClientView's method to the server when the player clicks on a action button
     * @param event
     */
    public void sendActionMessage (ActionEvent event){
        Object source = event.getSource();

        if(moveButton == source){
            ControllerLogin.clientView.createActionMessage(1);
        }

        if(grabButton == source){
            ControllerLogin.clientView.createActionMessage(2);
        }

        if(grabButton == source){
            ControllerLogin.clientView.createActionMessage(3);
        }
    }

    /**
     * send CellMessage with ClientView's method to the server when the player clicks on a cell button
     * @param event
     */
    public void sendCellMessage(ActionEvent event){

        Object source = event.getSource();

        if(cell00 == source){
            ControllerLogin.clientView.createCellMessage(0,0);
        }

        if(cell01 == source){
            ControllerLogin.clientView.createCellMessage(0,1);
        }

        if(cell02 == source){
            ControllerLogin.clientView.createCellMessage(0,2);
        }

        if(cell10 == source){
            ControllerLogin.clientView.createCellMessage(1,0);
        }

        if(cell11 == source){
            ControllerLogin.clientView.createCellMessage(1,1);
        }

        if(cell12 == source){
            ControllerLogin.clientView.createCellMessage(1,2);
        }

        if(cell20 == source){
            ControllerLogin.clientView.createCellMessage(2,0);
        }

        if(cell21 == source){
            ControllerLogin.clientView.createCellMessage(2,1);
        }

        if(cell22 == source){
            ControllerLogin.clientView.createCellMessage(2,2);
        }

        if(cell30 == source){
            ControllerLogin.clientView.createCellMessage(3,0);
        }

        if(cell31 == source){
            ControllerLogin.clientView.createCellMessage(3,1);
        }

        if(cell32 == source){
            ControllerLogin.clientView.createCellMessage(3,2);
        }
    }

    /**
     * send a WeaponMessage to the server when the player clicks on one of the map weapons button to collect the card
     * @param event
     */
    public void sendMapWeapon(ActionEvent event){
        Object source = event.getSource();
        List<CellSpawn> cellSpawns = ControllerLogin.mapView.getCellSpawn();
        List<WeaponCard> redWeaponCards =  cellSpawns.get(0).getWeapon();
        List<WeaponCard> bluWeaponCards =  cellSpawns.get(1).getWeapon();
        List<WeaponCard> yellowWeaponCards =  cellSpawns.get(2).getWeapon();


        if(redWeapon1 == source){
            ControllerLogin.clientView.createWeaponMessage(redWeaponCards.get(0));
        }
        if(redWeapon2 == source){
            ControllerLogin.clientView.createWeaponMessage(redWeaponCards.get(1));
        }
        if(redWeapon3 == source){
            ControllerLogin.clientView.createWeaponMessage(redWeaponCards.get(2));
        }
        if(blueWeapon1 == source){
            ControllerLogin.clientView.createWeaponMessage(bluWeaponCards.get(0));
        }
        if(blueWeapon2 == source){
            ControllerLogin.clientView.createWeaponMessage(bluWeaponCards.get(1));
        }
        if(blueWeapon3 == source){
            ControllerLogin.clientView.createWeaponMessage(bluWeaponCards.get(2));
        }
        if(yellowWeapon1 == source){
            ControllerLogin.clientView.createWeaponMessage(yellowWeaponCards.get(0));
        }
        if(yellowWeapon2 == source){
            ControllerLogin.clientView.createWeaponMessage(yellowWeaponCards.get(1));
        }
        if(yellowWeapon3 == source){
            ControllerLogin.clientView.createWeaponMessage(yellowWeaponCards.get(2));
        }


    }


    /**
     * send reload message when the player selects a weapon to be reload
     * @param event
     */
    public void reloadWeapon(ActionEvent event) {
        Player player = ControllerLogin.clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if(bReloadWeapon1 == source){
                ControllerLogin.clientView.createReloadMessage(weapons.get(0));

            }

            if(bReloadWeapon2 == source){

                ControllerLogin.clientView.createReloadMessage(weapons.get(1));

            }

            if(bReloadWeapon3 == source){
                ControllerLogin.clientView.createReloadMessage(weapons.get(2));


            }

        }
        catch (NullPointerException e){
            //TODO

        }




    }


    /**
     * send a player message when the player selects a target to shoot
     * @param event
     */
    public void selectEnemy(ActionEvent event) {
        Object source = event.getSource();
        if(enemyCharacter1 == source){
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView1.getID());
        }
        if(enemyCharacter2 == source){
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView2.getID());
        }
        if(enemyCharacter3 == source){
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView3.getID());
        }
        if(enemyCharacter4 == source){
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView4.getID());
        }

    }

    /**
     * Send a discard weapon message when the player selects a weapon discard button
     * @param event
     */
    public void discardWeapon(ActionEvent event) {
        Player player = ControllerLogin.clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if (bDiscardWeapon1 == source) {
                ControllerLogin.clientView.createDiscardWeaponMessage(weapons.get(0));

            }

            if (bDiscardWeapon2 == source) {

                ControllerLogin.clientView.createDiscardWeaponMessage(weapons.get(1));

            }

            if (bDiscardWeapon3 == source) {
                ControllerLogin.clientView.createDiscardWeaponMessage(weapons.get(2));


            }

        } catch (NullPointerException e) {
            //TODO


        }
    }

    public void discardPowerup(ActionEvent event) {
            Player player = ControllerLogin.clientView.getPlayerCopy();
        try {
            List<PowerupCard> powerupCards = player.getPowerupCardList();

            Object source = event.getSource();

            if (bDiscardPowerup1 == source) {
                ControllerLogin.clientView.createDiscardPowerupMessage(powerupCards.get(0));

            }

            if (bDiscardPowerup2 == source) {

                ControllerLogin.clientView.createDiscardPowerupMessage(powerupCards.get(1));

            }

            if (bDiscardPowerup3 == source) {

                ControllerLogin.clientView.createDiscardPowerupMessage(powerupCards.get(2));

            }
            if (bDiscardPowerup3 == source) {

                ControllerLogin.clientView.createDiscardPowerupMessage(powerupCards.get(3));

            }

        } catch (NullPointerException e) {
            //TODO


        }
    }




    /**
     * update label on gui that show player's points
     * @param points
     */
    public void updatePoints(int points){
        String string = Integer.toString(points);
        yourPointsLabel.setText(string);
    }



    static void updateMap(int chosenMap){
        choosenMap= chosenMap;
    }


    /**
     * update skulls' imageviews on map
     * @param skullNumber
     */
    public void updateSkullBoard(String skullNumber){

        skull.add(imSkull1);
        skull.add(imSkull2);
        skull.add(imSkull3);
        skull.add(imSkull4);
        skull.add(imSkull5);
        skull.add(imSkull6);
        skull.add(imSkull7);
        skull.add(imSkull8);

        //TODO mettere segnalino giocatore
        Platform.runLater(() -> {
            try {

                int teschi = Integer.parseInt(skullNumber);
                teschi--;
                for (int counter = 0; counter < skull.size(); counter++) {
                    skull.get(counter).setVisible(false);
                }
                while (teschi >= 0) {
                    skull.get(teschi).setVisible(true);
                    teschi--;
                }
            }
            catch (Exception e){

            }

        });


    }


    //TODO FINIRE METODO

    /**
     * update weaponcards image on map
     */
    public void updateWeaponMap(){

        Platform.runLater(() -> {

            ArrayList<ImageView> redWeapons = new ArrayList<>();
            ArrayList<ImageView> blueWeapons = new ArrayList<>();
            ArrayList<ImageView> yellowWeapons = new ArrayList<>();

            redWeapons.add(imRedWeapon1);
            redWeapons.add(imRedWeapon2);
            redWeapons.add(imRedWeapon3);

            blueWeapons.add(imBlueWeapon1);
            blueWeapons.add(imBlueWeapon2);
            blueWeapons.add(imBlueWeapon3);

            yellowWeapons.add(imYellowWeapon1);
            yellowWeapons.add(imYellowWeapon2);
            yellowWeapons.add(imYellowWeapon3);

            List<CellSpawn> cellSpawn = ControllerLogin.mapView.getCellSpawn();
            List<WeaponCard> weaponCardsRed = cellSpawn.get(0).getWeapon();
            List<WeaponCard> weaponCardsBlue = cellSpawn.get(1).getWeapon();
            List<WeaponCard> weaponCardsYellow = cellSpawn.get(2).getWeapon();

            for (int counter = 0; counter < weaponCardsRed.size(); counter++) {
                redWeapons.get(counter).setImage(setWeapon(weaponCardsRed.get(counter).getID()));
            }

            for (int counter = 0; counter < weaponCardsBlue.size(); counter++) {
                blueWeapons.get(counter).setImage(setWeapon(weaponCardsBlue.get(counter).getID()));
            }

            for (int counter = 0; counter < weaponCardsYellow.size(); counter++) {
                yellowWeapons.get(counter).setImage(setWeapon(weaponCardsYellow.get(counter).getID()));
            }

        });

    }

    /**
     * set each weapon id with the image
     * @param weaponID
     * @return
     */
    public Image setWeapon(int weaponID){
        Image image = new Image("cards/AD_weapons_IT_0225.png");
        switch (weaponID){
            case 13: image = new Image("cards/AD_weapons_IT_022.png");
                break;
            case 21: image = new Image("cards/AD_weapons_IT_023.png");
                break;
            case 10: image = new Image("cards/AD_weapons_IT_024.png");
                break;
            case 9: image = new Image("cards/AD_weapons_IT_025.png");
                break;
            case 11: image = new Image("cards/AD_weapons_IT_026.png");
                break;
            case 8: image = new Image("cards/AD_weapons_IT_027.png");
                break;
            case 12: image = new Image("cards/AD_weapons_IT_028.png");
                break;
            case 18: image = new Image("cards/AD_weapons_IT_029.png");
                break;
            case 4: image = new Image("cards/AD_weapons_IT_0210.png");
                break;
            case 20: image = new Image("cards/AD_weapons_IT_0211.png");
                break;
            case 19: image = new Image("cards/AD_weapons_IT_0212.png");
                break;
            case 7: image = new Image("cards/AD_weapons_IT_0213.png");
                break;
            case 3: image = new Image("cards/AD_weapons_IT_0214.png");
                break;
            case 6: image = new Image("cards/AD_weapons_IT_0215.png");
                break;
            case 16: image = new Image("cards/AD_weapons_IT_0216.png");
                break;
            case 2: image = new Image("cards/AD_weapons_IT_0217.png");
                break;
            case 5: image = new Image("cards/AD_weapons_IT_0218.png");
                break;
            case 17: image = new Image("cards/AD_weapons_IT_0219.png");
                break;
            case 15: image = new Image("cards/AD_weapons_IT_0220.png");
                break;
            case 14: image = new Image("cards/AD_weapons_IT_0221.png");
                break;
            case 1: image = new Image("cards/AD_weapons_IT_0222.png");
                break;

        }
        return image;
    }

    /**
     * set AmmoCard on map
     */
    public void updateAmmoCardMap(){
        ArrayList<CellAmmo> cellAmmos = new ArrayList<>();
        ArrayList<ImageView> imAmmoCell = new ArrayList<>();


        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(0,2));
        imAmmoCell.add(ammoCell02);
        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(1,0));
        imAmmoCell.add(ammoCell10);
        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(1,1));
        imAmmoCell.add(ammoCell11);
        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(1,2));
        imAmmoCell.add(ammoCell12);
        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(2,0));
        imAmmoCell.add(ammoCell20);
        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(2,1));
        imAmmoCell.add(ammoCell21);
        cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(3,1));
        imAmmoCell.add(ammoCell31);


        if(choosenMap == 1){
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(3,2));
            imAmmoCell.add(ammoCell32);
        }

        if(choosenMap == 2){
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(0,0));
            imAmmoCell.add(ammoCell00);
        }

        if(choosenMap == 3){
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(0,0));
            imAmmoCell.add(ammoCell00);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(3,2));
        }




        for (int i=0; i<cellAmmos.size(); i++){
            imAmmoCell.get(i).setImage(setAmmoCard(cellAmmos.get(i).getCardID()));
            System.out.println(cellAmmos.get(i).getCardID());
        }




    }

    /**
     * set each ammoCard id with the image
     * @param ammoID
     * @return
     */
    public Image setAmmoCard(List<Integer> ammoID){
        int ammoCardId = ammoID.get(0);
        Image image = new Image("ammo/AD_ammo_04.png");
        switch (ammoCardId){
            case 0: image = new Image("ammo/AD_ammo_0421.png");
                break;
            case 1: image = new Image("ammo/AD_ammo_0429.png");
                break;
            case 2: image = new Image("ammo/AD_ammo_0431.png");
                break;
            case 4: image = new Image("ammo/AD_ammo_0432.png");
                break;
            case 5: image = new Image("ammo/AD_ammo_0428.png");
                break;
            case 8: image = new Image("ammo/AD_ammo_0427.png");
                break;
            case 13: image = new Image("ammo/AD_ammo_0417.png");
                break;
            case 14: image = new Image("ammo/AD_ammo_0416.png");
                break;
            case 15: image = new Image("ammo/AD_ammo_0415.png");
                break;
            case 17: image = new Image("ammo/AD_ammo_0414.png");
                break;
            case 18: image = new Image("ammo/AD_ammo_0413.png");
                break;
            case 19: image = new Image("ammo/AD_ammo_0418.png");
                break;

        }
        return image;

    }
    /**
     * update player's weapon images
     */
    public void updateWeaponPlayer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    List<WeaponCard> weaponCards = ControllerLogin.clientView.getPlayerCopy().getWeaponCardList();
                    imWeaponCard1.setImage(setWeapon(weaponCards.get(0).getID()));
                    imWeaponCard2.setImage(setWeapon(weaponCards.get(1).getID()));
                    imWeaponCard3.setImage(setWeapon(weaponCards.get(2).getID()));
                }
                catch (Exception e){

                }


            }
        });

    }


    /**
     * update Player's Red Ammo
     * @param red
     */
    public void updatePlayerRedAmmo(String red){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ImageView> redAmmo = new ArrayList<>();
                    redAmmo.add(rammo1);
                    redAmmo.add(rammo2);
                    redAmmo.add(rammo3);
                    int redNumber = Integer.parseInt(red);
                    redNumber--;
                    for (int counter = 0; counter < redAmmo.size(); counter++) {
                        redAmmo.get(counter).setVisible(false);
                    }
                    while (redNumber >= 0) {
                        redAmmo.get(redNumber).setVisible(true);
                        redNumber--;
                    }

                }
                catch (Exception e){

                }
            }
        });

    }

    /**
     * update Player's Blue Ammo
     * @param blu
     */
    public void updatePlayerBlueAmmo(String blu){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ImageView> blueAmmo = new ArrayList();
                    blueAmmo.add(bammo1);
                    blueAmmo.add(bammo2);
                    blueAmmo.add(bammo3);
                    int blueNumber = Integer.parseInt(blu);
                    blueNumber--;
                    for (int counter = 0; counter < blueAmmo.size(); counter++) {
                        blueAmmo.get(counter).setVisible(false);
                    }
                    while (blueNumber >= 0) {
                        blueAmmo.get(blueNumber).setVisible(true);
                        blueNumber--;
                    }

                }
                catch (Exception e){

                }
            }
        });

    }


    /**
     * update Player's Yellow Ammo
     * @param yellow
     */
    public void updatePlayerYellowAmmo(String yellow){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ImageView> yellowAmmo = new ArrayList();
                    yellowAmmo.add(bammo1);
                    yellowAmmo.add(bammo2);
                    yellowAmmo.add(bammo3);
                    int yellowNumber = Integer.parseInt(yellow);
                    yellowNumber--;
                    for (int counter = 0; counter < yellowAmmo.size(); counter++) {
                        yellowAmmo.get(counter).setVisible(false);
                    }
                    while (yellowNumber >= 0) {
                        yellowAmmo.get(yellowNumber).setVisible(true);
                        yellowNumber--;
                    }
                }
                catch (Exception e){

                }

            }
        });

    }


    /**
     * update all Player's ammo
     * @param red
     * @param blue
     * @param yellow
     */
    public void updatePlayerAmmo(int red, int blue, int yellow){
        String redAmmo = String.valueOf(red);
        String blueAmmo = String.valueOf(blue);
        String yellowAmmo = String.valueOf(yellow);

        updatePlayerRedAmmo(redAmmo);
        updatePlayerBlueAmmo(blueAmmo);
        updatePlayerYellowAmmo(yellowAmmo);
    }



    public void printf(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelProva.setText(string);
            }
        });

    }

    /**
     * change enemys' nickname on map's window
     */
    public void setEnemyNickname(){
        if(ControllerLogin.enemyView1!=null) {
            tabEnemy1.setText(ControllerLogin.enemyView1.getNickname());
        }
        else{
            tabEnemy1.setDisable(true);
            tabEnemy1.setStyle(transparent);
        }
        if(ControllerLogin.enemyView2!=null) {
            tabEnemy2.setText(ControllerLogin.enemyView2.getNickname());

        }
        else{
            tabEnemy2.setDisable(true);
            tabEnemy2.setStyle(transparent);
        }
        if(ControllerLogin.enemyView3!=null) {
            tabEnemy3.setText(ControllerLogin.enemyView3.getNickname());
        }
        else{
            tabEnemy3.setDisable(true);
            tabEnemy3.setStyle(transparent);
        }
        if(ControllerLogin.enemyView4!=null) {
            tabEnemy4.setText(ControllerLogin.enemyView4.getNickname());
        }
        else{
            tabEnemy4.setDisable(true);
            tabEnemy4.setStyle(transparent);
        }
    }



    public void chooseYourCharacter() throws Exception{
        ControllerLogin.open("chooseCharacter.fxml", "CHOOSE YOUR CHARACTER", 500, 500);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //TODO IN BASE A QUELLO CHE MI VIENE PASSATO DISATTIVO I PERSONAGGI
            }
        });


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void chooseCharacter(ActionEvent event) {

        possibleActions.setVisible(true);
        yourCharacter.setVisible(true);
        ArrayList<Image> images = new ArrayList<>();

        if(rbSprog.isSelected()){
            ControllerLogin.clientView.createCharacterMessage(1);
            images = setCharacter(1);

        }
        if(rbViolet.isSelected()){
            ControllerLogin.clientView.createCharacterMessage(2);
            images = setCharacter(2);
        }
        if(rbStruct.isSelected()){
            ControllerLogin.clientView.createCharacterMessage(3);
            images = setCharacter(3);
        }
        if(rbDozer.isSelected()){
            ControllerLogin.clientView.createCharacterMessage(4);
            images = setCharacter(4);
        }
        if(rbBanshee.isSelected()){
            ControllerLogin.clientView.createCharacterMessage(5);
            images = setCharacter(5);
        }

        yourCharacter.setImage(images.get(0));
        possibleActions.setImage(images.get(1));

    }

    /**
     * update Enemy Character Image
     */
    public void updateEnemyCharacter(ClientEnemyView enemyView) {
        ArrayList<Image> images = new ArrayList<>();
        if (enemyView == ControllerLogin.enemyView1) {
            images = setCharacter(ControllerLogin.enemyView1.getCharacter().getId());
            imEnemyCharacter1.setImage(images.get(0));
            enemy1Actions.setImage(images.get(1));
        }
        if (enemyView == ControllerLogin.enemyView2) {
            images = setCharacter(ControllerLogin.enemyView2.getCharacter().getId());
            imEnemyCharacter2.setImage(images.get(0));
            enemy2Actions.setImage(images.get(1));
        }
        if (enemyView == ControllerLogin.enemyView3) {
            images = setCharacter(ControllerLogin.enemyView3.getCharacter().getId());
            imEnemyCharacter3.setImage(images.get(0));
            enemy3Actions.setImage(images.get(1));
        }
        if (enemyView == ControllerLogin.enemyView4) {
            images = setCharacter(ControllerLogin.enemyView4.getCharacter().getId());
            imEnemyCharacter4.setImage(images.get(0));
            enemy4Actions.setImage(images.get(1));
        }

    }

    /**
     * set each Character id with the image
     */
    public ArrayList<Image> setCharacter(int characterID){
        Image image1 = new Image("characters/characterBlue.jpg");
        Image image2= new Image("characters/actionsBlue.jpg");
        switch (characterID) {
            case 1:
                image1 = new Image("characters/characterGreen.jpg");
                image2 = new Image("actionsGreen.jpg");
                break;
            case 2:
                image1 = new Image("characters/characterViolet.jpg");
                image2 = new Image("characters/actionsViolet.jpg");
                break;
            case 3:
                image1 = new Image("characters/characterYellow.jpg");
                image2 = new Image("characters/actionsYellow.jpg");
                break;
            case 4:
                image1 = new Image("characters/characterGrey.jpg");
                image2 = new Image("characters/actionsGrey.jpg");
                break;
            case 5:
                image1 = new Image("characters/characterBlue.jpg");
                image2= new Image("characters/actionsBlue.jpg");
                break;
        }
        ArrayList<Image> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);

        return images;

    }

}


