package it.polimi.se2019.ui;




import it.polimi.se2019.MyThread;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.Points;
import it.polimi.se2019.view.clientView.ClientMapView;
import it.polimi.se2019.view.clientView.ClientView;
import javafx.application.Platform;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import java.util.List;


public class Controller {


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
    public ImageView imPlayer1Cell23;
    public ImageView imPlayer2Cell23;
    public ImageView imPlayer3Cell23;
    public ImageView imPlayer4Cell23;
    public ImageView imPlayer5Cell23;




    public Button fireButton;
    public Button endTurnButton;
    public Label yourPointsLabel;
    public AnchorPane myContainer;
    public TabPane container1;
    public TabPane container2;
    public AnchorPane enemy1back;
    ClientView clientView;
    ClientMapView mapView;

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


    private int suddenDeath = 2;
    private int skull = 0;

    @FXML
    public Label labelProva;

    String transparent = "-fx-background-color: transparent;";


    //ogni label o textfield ecc che vado a creare nel file fxml lo devo riportare come attributo nel controller
    //con lo stesso come dato in fx:id e in onAction metto il metodo


    public void skullCheck(ActionEvent event){

        skull = Integer.parseInt(skulls.getText());
        if(skull<1 ||skull >8){
            errorSkulls.setVisible(true);
        }
        else
            errorSkulls.setVisible(false);


        System.out.println(skull);


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
        //TODO creo sempre clientView??
        clientView = new ClientView();
        if(suddenDeathYes.isSelected()){
            suddenDeath=1;
            System.out.println(suddenDeath);
        }
        if(suddenDeathNo.isSelected()){
            suddenDeath=0;
            System.out.println(suddenDeath);
        }

        if(((choosenMap==1)||(choosenMap==2)||(choosenMap==3)||(choosenMap==4))&&((skull>=1)&&(skull<=8))&&((suddenDeath==0) || (suddenDeath==1))){
            boolean sd;
            if(suddenDeath==0) {
                sd=false;
            }
            else {
                sd = true;
            }
            System.out.println("recap");
            System.out.println(choosenMap);
            System.out.println(skull);
            System.out.println(suddenDeath);
            clientView.createSettingMessage(choosenMap,skull,sd);

            Stage stage = (Stage) rbmap2.getScene().getWindow();
            stage.close();
            controllerLogin.open("WaitingRoom","LEAN BACK AND CHILL", 520, 400);

        }



    }



    public void showMap(ActionEvent event) throws InterruptedException{




        setPlayerOnMap();

        setDiscard();



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
        possibleActions.setVisible(true);
        yourCharacter.setVisible(true);
        possibleActions.setImage(new Image("characters/actionsBlue.jpg"));
        yourCharacter.setImage(new Image("characters/characterBlue.jpg"));

        labelProva.setText("BENVENUTO");


        myThread = new MyThread(this);
        myThread.start();



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
        imWeaponCard4.setImage(new Image("emptyWeapon.jpg"));
        bWeaponCard4.setGraphic(imWeaponCard4);
        bWeaponCard4.setStyle(transparent);


    }

    /**
     * set trash's image on power up discard button
     */
    public void setDiscard(){
        bDiscardPowerup1.setGraphic(imTrashP1);
        bDiscardPowerup1.setStyle(transparent);
        bDiscardPowerup2.setGraphic(imTrashP2);
        bDiscardPowerup2.setStyle(transparent);
        bDiscardPowerup3.setGraphic(imTrashP3);
        bDiscardPowerup3.setStyle(transparent);
        bDiscardPowerup4.setGraphic(imTrashP4);
        bDiscardPowerup4.setStyle(transparent);

    }

    public void setPlayerOnMap(){



    }


    /**
     * open Add Firemode's window when the player clicks on add firemode button
     * @param event
     * @throws Exception
     */
    public void addFiremode(ActionEvent event) throws Exception {

        controllerLogin.open("addFiremode.fxml","ADD FIREMODE",350,400);

    }

    /**
     * send FireModeMessage the the player choose the weapon's firemode on gui
     * @param event
     */
    public void sendFiremode(ActionEvent event) {
        if(firemode1.isSelected()) {
            clientView.createFireModeMessage(1);
            Stage stage = (Stage) firemode1.getScene().getWindow();
            stage.close();
        }
        if(firemode2.isSelected()){
            clientView.createFireModeMessage(2);
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
            clientView.createOptionalMessage(1);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }
        if(firemodeOp2.isSelected()){
            clientView.createOptionalMessage(2);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }
        if(firemodeOp2.isSelected()){
            clientView.createOptionalMessage(3);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }

    }


    /**
     * send weapon message with ClientView's method to the server, when the player clicks on a player's weaponcard button
     * @param event
     */
    public void selectWeapon(ActionEvent event) {
        Player player = clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if(selectWeaponButton1 == source){
                    clientView.createWeaponMessage(weapons.get(0));

            }

            if(selectWeaponButton2 == source){

                    clientView.createWeaponMessage(weapons.get(1));

            }

            if(selectWeaponButton3 == source){
                  clientView.createWeaponMessage(weapons.get(2));


            }

            if(selectWeaponButton4 == source){
                clientView.createWeaponMessage(weapons.get(3));

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
        clientView.createFireMessage();
    }

    /**
     * send PassTurn message with ClientView's method to the server, when the player clicks End Turn's button
     * @param event
     */
    public void endTurn(ActionEvent event) {
        clientView.createPassTurnMessage();

    }

    public void printSomething(ActionEvent event) {
        System.out.println("FUNZIONA");

    }

    /**
     * update label on gui that show player's points
     * @param points
     */
    public void updatePoints(int points){
        String string = Integer.toString(points);
        yourPointsLabel.setText(string);
    }

    /**
     * send ActionMessage with ClientView's method to the server when the player clicks on a action button
     * @param event
     */
    public void sendActionMessage (ActionEvent event){
        Object source = event.getSource();

        if(moveButton == source){
            clientView.createActionMessage(1);
        }

        if(grabButton == source){
            clientView.createActionMessage(2);
        }

        if(grabButton == source){
            clientView.createActionMessage(3);
        }
    }

    /**
     * send CellMessage with ClientView's method to the server when the player clicks on a cell button
     * @param event
     */
    public void sendCellMessage(ActionEvent event){

        Object source = event.getSource();

        if(cell00 == source){
            clientView.createCellMessage(0,0);
        }

        if(cell01 == source){
            clientView.createCellMessage(0,1);
        }

        if(cell02 == source){
            clientView.createCellMessage(0,2);
        }

        if(cell10 == source){
            clientView.createCellMessage(1,0);
        }

        if(cell11 == source){
            clientView.createCellMessage(1,1);
        }

        if(cell12 == source){
            clientView.createCellMessage(1,2);
        }

        if(cell20 == source){
            clientView.createCellMessage(2,0);
        }

        if(cell21 == source){
            clientView.createCellMessage(2,1);
        }

        if(cell22 == source){
            clientView.createCellMessage(2,2);
        }

        if(cell30 == source){
            clientView.createCellMessage(3,0);
        }

        if(cell31 == source){
            clientView.createCellMessage(3,1);
        }

        if(cell32 == source){
            clientView.createCellMessage(3,2);
        }
    }

    /**
     * send a WeaponMessage to the server when the player clicks on one of the map weapons button to collect the card
     * @param event
     */
    public void sendMapWeapon(ActionEvent event){
        Object source = event.getSource();
        //TODO Prendere array list di spawncell

        if(redWeapon1 == source){

        }


    }


    /**
     * send reload message when the palyer select a weapon to be reload
     * @param event
     */
    public void reloadWeapon(ActionEvent event) {
        Player player = clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if(bReloadWeapon1 == source){
                clientView.createReloadMessage(weapons.get(0));

            }

            if(bReloadWeapon2 == source){

                clientView.createReloadMessage(weapons.get(1));

            }

            if(bReloadWeapon3 == source){
                clientView.createReloadMessage(weapons.get(2));


            }

            if(selectWeaponButton4 == source){
                clientView.createWeaponMessage(weapons.get(3));

            }
        }
        catch (NullPointerException e){
            //TODO

        }




    }
}


