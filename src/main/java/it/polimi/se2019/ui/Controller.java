package it.polimi.se2019.ui;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellAmmo;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.clientView.*;
import javafx.application.Platform;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable {

    boolean yourDead = false;

    static int selectTargetingScope;

    //character button
    @FXML
    public Button bselectCharacter;
    @FXML
    public RadioButton rbBlueAmmo;
    @FXML
    public RadioButton rbRedAmmo;
    @FXML
    public RadioButton rbYellowAmmo;

    //button to convert targeting scope in ammo
    @FXML
    public Button bconvertAmmo1;
    @FXML
    public Button bconvertAmmo2;
    @FXML
    public Button bconvertAmmo3;
    @FXML
    public Button bconvertAmmo4;

    //true when all the skull are taken
    static boolean isFrenzyTime = false;


    //button to close wainting room
    @FXML
    public Button closeWaiting;

    // match ID
    @FXML
    public Label labelMatchID;

    //imageview of players' posizion on map
    @FXML
    public ImageView imPlayer1Cell00;
    @FXML
    public ImageView imPlayer2Cell00;
    @FXML
    public ImageView imPlayer3Cell00;
    @FXML
    public ImageView imPlayer4Cell00;
    @FXML
    public ImageView imPlayer5Cell00;
    @FXML
    public ImageView imPlayer1Cell01;
    @FXML
    public ImageView imPlayer2Cell01;
    @FXML
    public ImageView imPlayer3Cell01;
    @FXML
    public ImageView imPlayer4Cell01;
    @FXML
    public ImageView imPlayer5Cell01;
    @FXML
    public ImageView imPlayer1Cell02;
    @FXML
    public ImageView imPlayer2Cell02;
    @FXML
    public ImageView imPlayer3Cell02;
    @FXML
    public ImageView imPlayer4Cell02;
    @FXML
    public ImageView imPlayer5Cell02;
    @FXML
    public ImageView imPlayer1Cell10;
    @FXML
    public ImageView imPlayer2Cell10;
    @FXML
    public ImageView imPlayer3Cell10;
    @FXML
    public ImageView imPlayer4Cell10;
    @FXML
    public ImageView imPlayer5Cell10;
    @FXML
    public ImageView imPlayer1Cell11;
    @FXML
    public ImageView imPlayer2Cell11;
    @FXML
    public ImageView imPlayer3Cell11;
    @FXML
    public ImageView imPlayer4Cell11;
    @FXML
    public ImageView imPlayer5Cell11;
    @FXML
    public ImageView imPlayer1Cell12;
    @FXML
    public ImageView imPlayer2Cell12;
    @FXML
    public ImageView imPlayer3Cell12;
     @FXML
    public ImageView imPlayer4Cell12;
     @FXML
    public ImageView imPlayer5Cell12;
     @FXML
    public ImageView imPlayer1Cell20;
     @FXML
    public ImageView imPlayer2Cell20;
     @FXML
    public ImageView imPlayer3Cell20;
     @FXML
    public ImageView imPlayer4Cell20;
     @FXML
    public ImageView imPlayer5Cell20;
     @FXML
    public ImageView imPlayer1Cell21;
     @FXML
    public ImageView imPlayer2Cell21;
     @FXML
    public ImageView imPlayer3Cell21;
     @FXML
    public ImageView imPlayer4Cell21;
     @FXML
    public ImageView imPlayer5Cell21;
     @FXML
    public ImageView imPlayer1Cell22;
     @FXML
    public ImageView imPlayer2Cell22;
     @FXML
    public ImageView imPlayer3Cell22;
     @FXML
    public ImageView imPlayer4Cell22;
     @FXML
    public ImageView imPlayer5Cell22;
     @FXML
    public ImageView imPlayer1Cell30;
     @FXML
    public ImageView imPlayer2Cell30;
     @FXML
    public ImageView imPlayer3Cell30;
     @FXML
    public ImageView imPlayer4Cell30;
     @FXML
    public ImageView imPlayer5Cell30;
     @FXML
    public ImageView imPlayer1Cell31;
     @FXML
    public ImageView imPlayer2Cell31;
     @FXML
    public ImageView imPlayer3Cell31;
     @FXML
    public ImageView imPlayer4Cell31;
     @FXML
    public ImageView imPlayer5Cell31;
     @FXML
    public ImageView imPlayer1Cell32;
     @FXML
    public ImageView imPlayer2Cell32;
     @FXML
    public ImageView imPlayer3Cell32;
     @FXML
    public ImageView imPlayer4Cell32;
     @FXML
    public ImageView imPlayer5Cell32;
     @FXML
    public Tab tabEnemy1;
     @FXML
    public Tab tabEnemy2;
     @FXML
    public Tab tabEnemy3;
     @FXML
    public Tab tabEnemy4;
     @FXML
    public RadioButton rbSprog;
     @FXML
    public RadioButton rbViolet;
     @FXML
    public RadioButton rbStruct;
     @FXML
    public RadioButton rbDozer;
     @FXML
    public RadioButton rbBanshee;
     @FXML
    public Button dbDropweapon;
     @FXML
    public Button bFrenzyShoot1;
     @FXML
    public Button bFrenzyMove1;
     @FXML
    public Button bFrenzyGrab1;
     @FXML
    public Button bFrenzyShoot2;
     @FXML
    public Button bFrenzyGrab2;
     @FXML
    public Button fireButton;
     @FXML
    public Button endTurnButton;
     @FXML
    public Label yourPointsLabel;
     @FXML
    public AnchorPane myContainer;
     @FXML
    public TabPane container1;
     @FXML
    public TabPane container2;
     @FXML
    public AnchorPane enemy1back;
     @FXML


    ControllerLogin controllerLogin;

    @FXML
    public Button moveButton;
    @FXML
    public Button grabButton;
    @FXML
    public Button shootButton;
    @FXML
    public Button addFire1;
    @FXML
    public Button addFire2;
    @FXML
    public Button addFire3;
    @FXML
    public RadioButton firemode1;
    @FXML
    public RadioButton firemode2;
    @FXML
    public RadioButton firemodeOp1;
    @FXML
    public RadioButton firemodeOp2;
    @FXML
    public ToggleGroup AddFiremode;
    @FXML
    public TextField skulls;
    @FXML
    public Label errorSkulls;
    @FXML
    public RadioButton suddenDeathYes;
    @FXML
    public RadioButton suddenDeathNo;
    @FXML
    public Button sendButton;


    //players damage
    @FXML
    public ImageView playerDamage0;
    @FXML
    public ImageView playerDamage1;
    @FXML
    public ImageView playerDamage2;
    @FXML
    public ImageView playerDamage3;
    @FXML
    public ImageView playerDamage4;
    @FXML
    public ImageView playerDamage5;
    @FXML
    public ImageView playerDamage6;
    @FXML
    public ImageView playerDamage7;
    @FXML
    public ImageView playerDamage8;
    @FXML
    public ImageView playerDamage9;
    @FXML
    public ImageView playerDamage10;
    @FXML
    public ImageView playerDamage11;

    //enemy 1 damage

    @FXML
    public ImageView enemy1Damage0;
    @FXML
    public ImageView enemy1Damage1;
    @FXML
    public ImageView enemy1Damage2;
    @FXML
    public ImageView enemy1Damage3;
    @FXML
    public ImageView enemy1Damage4;
    @FXML
    public ImageView enemy1Damage5;
    @FXML
    public ImageView enemy1Damage6;
    @FXML
    public ImageView enemy1Damage7;
    @FXML
    public ImageView enemy1Damage8;
    @FXML
    public ImageView enemy1Damage9;
    @FXML
    public ImageView enemy1Damage10;
    @FXML
    public ImageView enemy1Damage11;

    //enemy 2 damage

    @FXML
    public ImageView enemy2Damage0;
    @FXML
    public ImageView enemy2Damage1;
    @FXML
    public ImageView enemy2Damage2;
    @FXML
    public ImageView enemy2Damage3;
    @FXML
    public ImageView enemy2Damage4;
    @FXML
    public ImageView enemy2Damage5;
    @FXML
    public ImageView enemy2Damage6;
    @FXML
    public ImageView enemy2Damage7;
    @FXML
    public ImageView enemy2Damage8;
    @FXML
    public ImageView enemy2Damage9;
    @FXML
    public ImageView enemy2Damage10;
    @FXML
    public ImageView enemy2Damage11;


    //enemy 3 damage

    @FXML
    public ImageView enemy3Damage0;
    @FXML
    public ImageView enemy3Damage1;
    @FXML
    public ImageView enemy3Damage2;
    @FXML
    public ImageView enemy3Damage3;
    @FXML
    public ImageView enemy3Damage4;
    @FXML
    public ImageView enemy3Damage5;
    @FXML
    public ImageView enemy3Damage6;
    @FXML
    public ImageView enemy3Damage7;
    @FXML
    public ImageView enemy3Damage8;
    @FXML
    public ImageView enemy3Damage9;
    @FXML
    public ImageView enemy3Damage10;
    @FXML
    public ImageView enemy3Damage11;

    //enemy 4 damage

    @FXML
    public ImageView enemy4Damage0;
    @FXML
    public ImageView enemy4Damage1;
    @FXML
    public ImageView enemy4Damage2;
    @FXML
    public ImageView enemy4Damage3;
    @FXML
    public ImageView enemy4Damage4;
    @FXML
    public ImageView enemy4Damage5;
    @FXML
    public ImageView enemy4Damage6;
    @FXML
    public ImageView enemy4Damage7;
    @FXML
    public ImageView enemy4Damage8;
    @FXML
    public ImageView enemy4Damage9;
    @FXML
    public ImageView enemy4Damage10;
    @FXML
    public ImageView enemy4Damage11;



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

    static int choosenMap = 0;

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


    @FXML
    public Button selectWeaponButton1;
    @FXML
    public Button selectWeaponButton2;
    @FXML
    public Button selectWeaponButton3;
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

    // player marks
    @FXML
    public ImageView mark1;
    @FXML
    public ImageView mark2;
    @FXML
    public ImageView mark3;
    @FXML
    public ImageView mark4;
    @FXML
    public ImageView mark5;
    @FXML
    public ImageView mark6;
    @FXML
    public ImageView mark7;
    @FXML
    public ImageView mark8;
    @FXML
    public ImageView mark9;
    @FXML
    public ImageView mark10;
    @FXML
    public ImageView mark11;
    @FXML
    public ImageView mark12;

    //enemy 1 marks
    @FXML
    public ImageView enemy1mark1;
    @FXML
    public ImageView enemy1mark2;
    @FXML
    public ImageView enemy1mark3;
    @FXML
    public ImageView enemy1mark4;
    @FXML
    public ImageView enemy1mark5;
    @FXML
    public ImageView enemy1mark6;
    @FXML
    public ImageView enemy1mark7;
    @FXML
    public ImageView enemy1mark8;
    @FXML
    public ImageView enemy1mark9;
    @FXML
    public ImageView enemy1mark10;
    @FXML
    public ImageView enemy1mark11;
    @FXML
    public ImageView enemy1mark12;

    //enemy 1 skull
    @FXML
    public ImageView enemy1Skull1;
    @FXML
    public ImageView enemy1Skull2;
    @FXML
    public ImageView enemy1Skull3;
    @FXML
    public ImageView enemy1Skull4;
    @FXML
    public ImageView enemy1Skull5;
    @FXML
    public ImageView enemy1Skull6;



    //enemy 2 marks
    @FXML
    public ImageView enemy2mark1;
    @FXML
    public ImageView enemy2mark2;
    @FXML
    public ImageView enemy2mark3;
    @FXML
    public ImageView enemy2mark4;
    @FXML
    public ImageView enemy2mark5;
    @FXML
    public ImageView enemy2mark6;
    @FXML
    public ImageView enemy2mark7;
    @FXML
    public ImageView enemy2mark8;
    @FXML
    public ImageView enemy2mark9;
    @FXML
    public ImageView enemy2mark10;
    @FXML
    public ImageView enemy2mark11;
    @FXML
    public ImageView enemy2mark12;

    //enemy 2 skull
    @FXML
    public ImageView enemy2Skull1;
    @FXML
    public ImageView enemy2Skull2;
    @FXML
    public ImageView enemy2Skull3;
    @FXML
    public ImageView enemy2Skull4;
    @FXML
    public ImageView enemy2Skull5;
    @FXML
    public ImageView enemy2Skull6;


    //enemy 3 marks
    @FXML
    public ImageView enemy3mark1;
    @FXML
    public ImageView enemy3mark2;
    @FXML
    public ImageView enemy3mark3;
    @FXML
    public ImageView enemy3mark4;
    @FXML
    public ImageView enemy3mark5;
    @FXML
    public ImageView enemy3mark6;
    @FXML
    public ImageView enemy3mark7;
    @FXML
    public ImageView enemy3mark8;
    @FXML
    public ImageView enemy3mark9;
    @FXML
    public ImageView enemy3mark10;
    @FXML
    public ImageView enemy3mark11;
    @FXML
    public ImageView enemy3mark12;


    //enemy 3 skull
    @FXML
    public ImageView enemy3Skull1;
    @FXML
    public ImageView enemy3Skull2;
    @FXML
    public ImageView enemy3Skull3;
    @FXML
    public ImageView enemy3Skull4;
    @FXML
    public ImageView enemy3Skull5;
    @FXML
    public ImageView enemy3Skull6;



    //enemy 4 marks
    @FXML
    public ImageView enemy4mark1;
    @FXML
    public ImageView enemy4mark2;
    @FXML
    public ImageView enemy4mark3;
    @FXML
    public ImageView enemy4mark4;
    @FXML
    public ImageView enemy4mark5;
    @FXML
    public ImageView enemy4mark6;
    @FXML
    public ImageView enemy4mark7;
    @FXML
    public ImageView enemy4mark8;
    @FXML
    public ImageView enemy4mark9;
    @FXML
    public ImageView enemy4mark10;
    @FXML
    public ImageView enemy4mark11;
    @FXML
    public ImageView enemy4mark12;


    //enemy 4 skull
    @FXML
    public ImageView enemy4Skull1;
    @FXML
    public ImageView enemy4Skull2;
    @FXML
    public ImageView enemy4Skull3;
    @FXML
    public ImageView enemy4Skull4;
    @FXML
    public ImageView enemy4Skull5;
    @FXML
    public ImageView enemy4Skull6;

    //player skulls
    @FXML
    public ImageView playerSkull1;
    @FXML
    public ImageView playerSkull2;
    @FXML
    public ImageView playerSkull3;
    @FXML
    public ImageView playerSkull4;
    @FXML
    public ImageView playerSkull5;
    @FXML
    public ImageView playerSkull6;

    private int suddenDeath = 2;
    private int initSkull = 0;

    @FXML
    public Label labelProva;

    private String transparent = "-fx-background-color: transparent;";


    /**
     * take skulls' number from the user on gui
     * @param event after been clicked
     */
    public void skullCheck(ActionEvent event) {

        initSkull = Integer.parseInt(skulls.getText());
        if (initSkull < 1 || initSkull > 8) {
            errorSkulls.setVisible(true);
        } else
            errorSkulls.setVisible(false);



    }


    /**
     * let the user select the game's map on gui
     * @param event after been clicked
     */
    public void chooseMap(ActionEvent event) {

        if (rbmap1.isSelected()) {
            choosenMap = 1;

        }

        if (rbmap2.isSelected()) {
            choosenMap = 2;

        }

        if (rbmap3.isSelected()) {
            choosenMap = 3;

        }

        if (rbmap4.isSelected()) {
            choosenMap = 4;

        }

    }


    /**
     * send settings to the server from gui
     * @param event after been clicked
     */
    public void sendSetting(ActionEvent event) {



        if (suddenDeathYes.isSelected()) {
            suddenDeath = 1;

        }
        if (suddenDeathNo.isSelected()) {
            suddenDeath = 0;

        }

        if (((choosenMap == 1) || (choosenMap == 2) || (choosenMap == 3) || (choosenMap == 4)) && ((initSkull >= 1) && (initSkull <= 8)) && ((suddenDeath == 0) || (suddenDeath == 1))) {
            boolean sd;
            if (suddenDeath == 0) {
                sd = false;
            } else {
                sd = true;
            }

            ControllerLogin.clientView.createSettingMessage(choosenMap, initSkull, sd);

            Stage stage = (Stage) rbmap2.getScene().getWindow();
            stage.close();

            ControllerLogin.clientView.handleLogin(true, true);

        }


    }


    /**
     * show all the elements on gui to play the game
     * @param event after been clicked
     */
    public void startGame(ActionEvent event)  {


        //update players position after disconnection
        Cell[][] cells = ControllerLogin.mapView.getCells();
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                updatePlayersPosition(cells[i][j]);
            }
        }


        //disable character button if the player has been disconnected before and set character images
        if(ControllerLogin.clientView.getPlayerCopy().getCharacter()!=null) {
            bselectCharacter.setVisible(false);
            bselectCharacter.setDisable(true);
            yourCharacter.setVisible(true);
            possibleActions.setVisible(true);
            if(ControllerLogin.clientView.getPlayerCopy().isFrenzyDeath()){
                frenzyPlayer();
            }
            else{

                ArrayList<Image> images= setCharacter(ControllerLogin.clientView.getPlayerCopy().getCharacter().getId(),isFrenzyTime);
                yourCharacter.setImage(images.get(0));
                possibleActions.setImage(images.get(1));

            }

        }

        else{
            bselectCharacter.setVisible(true);
            bselectCharacter.setDisable(false);
        }


        //set Player ammo
        updatePlayerAmmo(ControllerLogin.clientView.getPlayerCopy().getAmmo().getRedAmmo(), ControllerLogin.clientView.getPlayerCopy().getAmmo().getBlueAmmo(),
                ControllerLogin.clientView.getPlayerCopy().getAmmo().getYellowAmmo());

        //set skull on map
        String skullnum = Integer.toString(ControllerLogin.skullBoardView.getOriginalSkull());
        updateSkullBoard(skullnum);

        updatePlayerSkull();
        updatePlayerMarks();
        updatePoints(ControllerLogin.clientView.getPlayerCopy().getNumPoints());
        updatePlayerDamage();


        setDiscardReload();

        possibleActions.setVisible(true);
        yourCharacter.setVisible(true);


        if (ControllerLogin.mapView.getMapCopy().getID() == 2) {
            mappa.setImage(new Image("mappa2.jpg"));
            cell32.setDisable(true);
            cell00.setDisable(false);
            ammoCell32.setDisable(true);
            ammoCell32.setVisible(false);

        }

        if (ControllerLogin.mapView.getMapCopy().getID() == 1) {
            mappa.setImage(new Image("mappa.jpg"));
            cell00.setDisable(true);
            cell32.setDisable(false);
            ammoCell00.setDisable(true);
            ammoCell00.setVisible(false);

        }

        if (ControllerLogin.mapView.getMapCopy().getID() == 3) {
            mappa.setImage(new Image("mappa3.jpg"));

        }

        if (ControllerLogin.mapView.getMapCopy().getID() == 4) {
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

        //update palyer's powerup
        updatePlayerPowerup();

        //add background

        BackgroundImage myBI = new BackgroundImage(new Image("background.png", 32, 32, false, true),
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

        //show matchID
        showMatchID(Integer.toString(ControllerLogin.clientView.getMatchId()));

        //set ammo card on map
        updateAmmoCardMap();
        // set enemy nickname on window
        setEnemyNickname();

        // set weapon card on map
        updateWeaponMap();
        //update player's weapon
        updateWeaponPlayer();


        //update enemy
        if(ControllerLogin.enemyView1!=null){
            updateEnemyCharacter(ControllerLogin.enemyView1);
            updateEnemyWeapon(ControllerLogin.enemyView1);
            frenzyEnemy(ControllerLogin.enemyView1);
            updateEnemyDamage(ControllerLogin.enemyView1);
            updateEnemyMarks(ControllerLogin.enemyView1);
            updateEnemySkull(ControllerLogin.enemyView1);
        }

        if(ControllerLogin.enemyView2!=null){
            updateEnemyCharacter(ControllerLogin.enemyView2);
            updateEnemyWeapon(ControllerLogin.enemyView2);
            frenzyEnemy(ControllerLogin.enemyView2);
            updateEnemyDamage(ControllerLogin.enemyView2);
            updateEnemyMarks(ControllerLogin.enemyView2);
            updateEnemySkull(ControllerLogin.enemyView2);
        }

        if(ControllerLogin.enemyView3!=null){
            updateEnemyCharacter(ControllerLogin.enemyView3);
            updateEnemyWeapon(ControllerLogin.enemyView3);
            frenzyEnemy(ControllerLogin.enemyView3);
            updateEnemyDamage(ControllerLogin.enemyView3);
            updateEnemyMarks(ControllerLogin.enemyView3);
            updateEnemySkull(ControllerLogin.enemyView3);
        }

        if(ControllerLogin.enemyView4!=null){
            updateEnemyCharacter(ControllerLogin.enemyView4);
            updateEnemyWeapon(ControllerLogin.enemyView4);
            frenzyEnemy(ControllerLogin.enemyView4);
            updateEnemyDamage(ControllerLogin.enemyView4);
            updateEnemyMarks(ControllerLogin.enemyView4);
            updateEnemySkull(ControllerLogin.enemyView4);
        }




    }


    /**
     * set actions' buttons for the player
     */
    private void setAction() {

        moveButton.setStyle(transparent);
        grabButton.setStyle(transparent);
        shootButton.setStyle(transparent);


    }


    /**
     * set weapon cards buttons on the map
     */
    private void setWeaponMap() {

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
    private void setCellsMap() {

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
    private void setEnemyCharacter() {

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
    private void setDeck() {

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
    private void setPlayerCards() {
        bPowerupCard1.setGraphic(imPowerupCard1);
        bPowerupCard1.setStyle(transparent);
        bPowerupCard2.setGraphic(imPowerupCard2);
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
      private void setDiscardReload() {
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



    /**
     * open Add Firemode's window when the player clicks on add firemode button
     *
     * @param event after been clicked
     * @throws Exception for opening fxml file
     */
    public void selectFiremode(ActionEvent event) throws Exception {

        ControllerLogin.open("addFiremode.fxml", "ADD FIREMODE", 350, 400);

    }

    /**
     * send FireModeMessage the the player choose the weapon's firemode on gui
     *
     * @param event after been clicked
     */
    public void sendFiremode(ActionEvent event) {
        if (firemode1.isSelected()) {
            ControllerLogin.clientView.createFireModeMessage(1);
            Stage stage = (Stage) firemode1.getScene().getWindow();
            stage.close();
        }
        if (firemode2.isSelected()) {
            ControllerLogin.clientView.createFireModeMessage(2);
            Stage stage = (Stage) firemode1.getScene().getWindow();
            stage.close();
        }

    }


    /**
     * send OptionalMessage the the player choose the weapon's optional firemode on gui
     *
     * @param event after been clicked
     */
    public void sendOptional(ActionEvent event) {

        if (firemodeOp1.isSelected()) {
            ControllerLogin.clientView.createOptionalMessage(1);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }
        if (firemodeOp2.isSelected()) {
            ControllerLogin.clientView.createOptionalMessage(2);
            Stage stage = (Stage) firemodeOp1.getScene().getWindow();
            stage.close();
        }

    }


    /**
     * send weapon message with ClientView's method to the server, when the player clicks on a player's weaponcard button
     *
     * @param event after been clicked
     */
    public void selectWeapon(ActionEvent event) {
        Player player = ControllerLogin.clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if (selectWeaponButton1 == source) {
                ControllerLogin.clientView.createWeaponMessage(weapons.get(0));

            }

            if (selectWeaponButton2 == source) {

                ControllerLogin.clientView.createWeaponMessage(weapons.get(1));

            }

            if (selectWeaponButton3 == source) {
                ControllerLogin.clientView.createWeaponMessage(weapons.get(2));


            }

        } catch (NullPointerException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

        }


    }

    /**
     * send Fire message with ClientView's method to the server, when the player clicks on FIRE button
     *
     * @param event after been clicked
     */
    public void fire(ActionEvent event) {

        ControllerLogin.clientView.createFireMessage();
    }

    /**
     * send PassTurn message with ClientView's method to the server, when the player clicks End Turn's button
     *
     * @param event after been clicked
     */
    public void endTurn(ActionEvent event) {
        ControllerLogin.clientView.createPassTurnMessage();

    }


    /**
     * send ActionMessage with ClientView's method to the server when the player clicks on a action button
     *
     * @param event after been clicked
     */
    public void sendActionMessage(ActionEvent event) {
        Object source = event.getSource();

        if (moveButton == source || bFrenzyMove1 == source) {
            ControllerLogin.clientView.createActionMessage(1);
        }

        if (grabButton == source || bFrenzyGrab1 == source || bFrenzyGrab2 == source) {
            ControllerLogin.clientView.createActionMessage(2);
        }

        if (shootButton == source || bFrenzyShoot1 == source || bFrenzyShoot2 == source) {
            ControllerLogin.clientView.createActionMessage(3);
        }
    }

    /**
     * send CellMessage with ClientView's method to the server when the player clicks on a cell button
     *
     * @param event after been clicked
     */
    public void sendCellMessage(ActionEvent event) {

        Object source = event.getSource();

        if (cell00 == source) {
            ControllerLogin.clientView.createCellMessage(0, 0);
        }

        if (cell01 == source) {
            ControllerLogin.clientView.createCellMessage(0, 1);
        }

        if (cell02 == source) {
            ControllerLogin.clientView.createCellMessage(0, 2);
        }

        if (cell10 == source) {
            ControllerLogin.clientView.createCellMessage(1, 0);
        }

        if (cell11 == source) {
            ControllerLogin.clientView.createCellMessage(1, 1);
        }

        if (cell12 == source) {
            ControllerLogin.clientView.createCellMessage(1, 2);
        }

        if (cell20 == source) {
            ControllerLogin.clientView.createCellMessage(2, 0);
        }

        if (cell21 == source) {
            ControllerLogin.clientView.createCellMessage(2, 1);
        }

        if (cell22 == source) {
            ControllerLogin.clientView.createCellMessage(2, 2);
        }

        if (cell30 == source) {
            ControllerLogin.clientView.createCellMessage(3, 0);
        }

        if (cell31 == source) {
            ControllerLogin.clientView.createCellMessage(3, 1);
        }

        if (cell32 == source) {
            ControllerLogin.clientView.createCellMessage(3, 2);
        }
    }

    /**
     * send a WeaponMessage to the server when the player clicks on one of the map weapons button to collect the card
     *
     * @param event after been clicked
     */
    public void sendMapWeapon(ActionEvent event) {
        Object source = event.getSource();
        List<CellSpawn> cellSpawns = ControllerLogin.mapView.getCellSpawn();
        List<WeaponCard> redWeaponCards = cellSpawns.get(0).getWeapon();
        List<WeaponCard> bluWeaponCards = cellSpawns.get(1).getWeapon();
        List<WeaponCard> yellowWeaponCards = cellSpawns.get(2).getWeapon();


        if (redWeapon1 == source) {
            ControllerLogin.clientView.createWeaponMessage(redWeaponCards.get(0));
        }
        if (redWeapon2 == source) {
            ControllerLogin.clientView.createWeaponMessage(redWeaponCards.get(1));
        }
        if (redWeapon3 == source) {
            ControllerLogin.clientView.createWeaponMessage(redWeaponCards.get(2));
        }
        if (blueWeapon1 == source) {
            ControllerLogin.clientView.createWeaponMessage(bluWeaponCards.get(0));
        }
        if (blueWeapon2 == source) {
            ControllerLogin.clientView.createWeaponMessage(bluWeaponCards.get(1));
        }
        if (blueWeapon3 == source) {
            ControllerLogin.clientView.createWeaponMessage(bluWeaponCards.get(2));
        }
        if (yellowWeapon1 == source) {
            ControllerLogin.clientView.createWeaponMessage(yellowWeaponCards.get(0));
        }
        if (yellowWeapon2 == source) {
            ControllerLogin.clientView.createWeaponMessage(yellowWeaponCards.get(1));
        }
        if (yellowWeapon3 == source) {
            ControllerLogin.clientView.createWeaponMessage(yellowWeaponCards.get(2));
        }


    }


    /**
     * send reload message when the player selects a weapon to be reload
     *
     * @param event after been clicked
     */
    public void reloadWeapon(ActionEvent event) {
        Player player = ControllerLogin.clientView.getPlayerCopy();
        try {
            List<WeaponCard> weapons = player.getWeaponCardList();

            Object source = event.getSource();

            if (bReloadWeapon1 == source) {
                ControllerLogin.clientView.createReloadMessage(weapons.get(0));

            }

            if (bReloadWeapon2 == source) {

                ControllerLogin.clientView.createReloadMessage(weapons.get(1));

            }

            if (bReloadWeapon3 == source) {
                ControllerLogin.clientView.createReloadMessage(weapons.get(2));


            }

        } catch (NullPointerException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

        }


    }


    /**
     * send a player message when the player selects a target to shoot
     *
     * @param event after been clicked
     */
    public void selectEnemy(ActionEvent event) {
        Object source = event.getSource();
        if (enemyCharacter1 == source) {
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView1.getID());
        }
        if (enemyCharacter2 == source) {
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView2.getID());
        }
        if (enemyCharacter3 == source) {
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView3.getID());
        }
        if (enemyCharacter4 == source) {
            ControllerLogin.clientView.createPlayerMessage(ControllerLogin.enemyView4.getID());
        }

    }

    /**
     * Send a discard weapon message when the player selects a weapon discard button
     *
     * @param event after been clicked
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
            Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

        }
    }


    /**
     * send message to the server to discard the poerup selected on gui
     * @param event after been clicked
     */
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
            if (bDiscardPowerup4 == source) {

                ControllerLogin.clientView.createDiscardPowerupMessage(powerupCards.get(3));

            }

        } catch (NullPointerException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");


        }
    }


    /**
     * update label on gui that show player's points
     *
     * @param points player's points
     */
    void updatePoints(int points) {
        Platform.runLater(() ->  {
                try {
                    String string = Integer.toString(points);
                    yourPointsLabel.setText(string);
                }
                catch (NullPointerException e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }
            });


    }




    /**
     * update skulls' imageviews on map
     *
     * @param skullNumber number of skull on map
     */
    public void updateSkullBoard(String skullNumber) {

        Platform.runLater(() -> {
            try {
                ArrayList<ImageView> skull = new ArrayList<>();
                skull.add(imSkull8);
                skull.add(imSkull7);
                skull.add(imSkull6);
                skull.add(imSkull5);
                skull.add(imSkull4);
                skull.add(imSkull3);
                skull.add(imSkull2);
                skull.add(imSkull1);

                int teschi = Integer.parseInt(skullNumber);
                if (ControllerLogin.skullBoardView.getNumSkullCopy() == 0){
                    isFrenzyTime=true;
                }
                //teschi--;
                for(int counter = 0; counter < skull.size(); counter++) {
                    skull.get(counter).setVisible(false);
                }
                /*
                while (teschi >= 0) {
                    skull.get(teschi).setVisible(true);
                    teschi--;
                }
                */
                for(int i=8-teschi; i<skull.size(); i++){

                    skull.get(i).setImage(new Image("Icon/skull.png"));
                    skull.get(i).setVisible(true);

                }

                int j=0;
                for ( int i=8-teschi; i<(8- teschi + ControllerLogin.skullBoardView.getDeathCopy().size()); i++ ){
                    skull.get(i).setImage(setDamageImages(ControllerLogin.skullBoardView.getDeathCopy().get(j).getWhoKilled().getCharacter().getId(), ControllerLogin.skullBoardView.getDeathCopy().get(j).getPoints()));


                }
            } catch (Exception e) {
                Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
            }

        });

    }



    /**
     * update weaponcards image on map
     */
     void updateWeaponMap() {
        Platform.runLater(() -> {
            try {

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
                    redWeapons.get(counter).setVisible(false);
                    if(weaponCardsRed.get(counter)!=null) {
                        redWeapons.get(counter).setVisible(true);
                        redWeapons.get(counter).setImage(setWeapon(weaponCardsRed.get(counter).getID()));
                    }
                }

                for (int counter = 0; counter < weaponCardsBlue.size(); counter++) {
                    blueWeapons.get(counter).setVisible(false);
                    if(weaponCardsBlue.get(counter)!=null) {
                        blueWeapons.get(counter).setVisible(true);
                        blueWeapons.get(counter).setImage(setWeapon(weaponCardsBlue.get(counter).getID()));
                    }
                }

                for (int counter = 0; counter < weaponCardsYellow.size(); counter++) {
                    yellowWeapons.get(counter).setVisible(false);
                    if(weaponCardsYellow.get(counter)!=null) {
                        yellowWeapons.get(counter).setVisible(true);
                        yellowWeapons.get(counter).setImage(setWeapon(weaponCardsYellow.get(counter).getID()));
                    }
                }
            }
            catch (Exception e){
                Logger.getLogger(Controller.class.getName()).log(Level.INFO, "Do nothing");
            }
        });

    }

    /**
     * set each weapon id with the image
     *
     * @param weaponID select weapon image by the id
     * @return image of weapon
     */
    private Image setWeapon(int weaponID) {
        Image image = new Image("cards/AD_weapons_IT_0225.png");
        switch (weaponID) {
            case 13:
                image = new Image("cards/AD_weapons_IT_022.png");
                break;
            case 21:
                image = new Image("cards/AD_weapons_IT_023.png");
                break;
            case 10:
                image = new Image("cards/AD_weapons_IT_024.png");
                break;
            case 9:
                image = new Image("cards/AD_weapons_IT_025.png");
                break;
            case 11:
                image = new Image("cards/AD_weapons_IT_026.png");
                break;
            case 8:
                image = new Image("cards/AD_weapons_IT_027.png");
                break;
            case 12:
                image = new Image("cards/AD_weapons_IT_028.png");
                break;
            case 18:
                image = new Image("cards/AD_weapons_IT_029.png");
                break;
            case 4:
                image = new Image("cards/AD_weapons_IT_0210.png");
                break;
            case 20:
                image = new Image("cards/AD_weapons_IT_0211.png");
                break;
            case 19:
                image = new Image("cards/AD_weapons_IT_0212.png");
                break;
            case 7:
                image = new Image("cards/AD_weapons_IT_0213.png");
                break;
            case 3:
                image = new Image("cards/AD_weapons_IT_0214.png");
                break;
            case 6:
                image = new Image("cards/AD_weapons_IT_0215.png");
                break;
            case 16:
                image = new Image("cards/AD_weapons_IT_0216.png");
                break;
            case 2:
                image = new Image("cards/AD_weapons_IT_0217.png");
                break;
            case 5:
                image = new Image("cards/AD_weapons_IT_0218.png");
                break;
            case 17:
                image = new Image("cards/AD_weapons_IT_0219.png");
                break;
            case 15:
                image = new Image("cards/AD_weapons_IT_0220.png");
                break;
            case 14:
                image = new Image("cards/AD_weapons_IT_0221.png");
                break;
            case 1:
                image = new Image("cards/AD_weapons_IT_0222.png");
                break;
            default: break;
        }
        return image;
    }

    /**
     * set AmmoCard on map
     */
    void updateAmmoCardMap() {

        Platform.runLater(() ->  {
            try{
            ArrayList<CellAmmo> cellAmmos = new ArrayList<>();
            ArrayList<ImageView> imAmmoCell = new ArrayList<>();


            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(0, 2));
            imAmmoCell.add(ammoCell02);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(1, 0));
            imAmmoCell.add(ammoCell10);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(1, 1));
            imAmmoCell.add(ammoCell11);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(1, 2));
            imAmmoCell.add(ammoCell12);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(2, 0));
            imAmmoCell.add(ammoCell20);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(2, 1));
            imAmmoCell.add(ammoCell21);
            cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(3, 1));
            imAmmoCell.add(ammoCell31);


            if (ControllerLogin.mapView.getMapCopy().getID() == 1) {
                cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(3, 2));
                imAmmoCell.add(ammoCell32);
            }

            if (ControllerLogin.mapView.getMapCopy().getID() == 2) {
                cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(0, 0));
                imAmmoCell.add(ammoCell00);
            }

            if (ControllerLogin.mapView.getMapCopy().getID() == 3) {
                cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(0, 0));
                imAmmoCell.add(ammoCell00);
                cellAmmos.add((CellAmmo) ControllerLogin.mapView.getCell(3, 2));
                imAmmoCell.add(ammoCell32);
            }


            for (int i = 0; i < cellAmmos.size(); i++) {
                imAmmoCell.get(i).setVisible(false);
                    if(cellAmmos.get(i).getAmmo()!=null) {
                        imAmmoCell.get(i).setVisible(true);
                        imAmmoCell.get(i).setImage(setAmmoCard(cellAmmos.get(i).getAmmo().getIDtype()));
                    }
                }
            }
        catch (NullPointerException e){
            Logger.getLogger(Controller.class.getName()).log(Level.INFO, "do nothing");
        }

        });

    }

    /**
     * set each ammoCard id with the image
     *
     * @param ammoID select the right ammo images by the id
     * @return ammo card image
     */
    private Image setAmmoCard(int ammoID) {

        Image image = new Image("ammo/AD_ammo_04.png");
        switch (ammoID) {
            case 0:
                image = new Image("ammo/AD_ammo_0421.png");
                break;
            case 1:
                image = new Image("ammo/AD_ammo_0429.png");
                break;
            case 2:
                image = new Image("ammo/AD_ammo_0431.png");
                break;
            case 4:
                image = new Image("ammo/AD_ammo_0432.png");
                break;
            case 5:
                image = new Image("ammo/AD_ammo_0428.png");
                break;
            case 8:
                image = new Image("ammo/AD_ammo_0427.png");
                break;
            case 13:
                image = new Image("ammo/AD_ammo_0417.png");
                break;
            case 14:
                image = new Image("ammo/AD_ammo_0416.png");
                break;
            case 15:
                image = new Image("ammo/AD_ammo_0415.png");
                break;
            case 17:
                image = new Image("ammo/AD_ammo_0414.png");
                break;
            case 18:
                image = new Image("ammo/AD_ammo_0413.png");
                break;
            case 19:
                image = new Image("ammo/AD_ammo_0418.png");
                break;
            default: break;
        }
        return image;

    }

    /**
     * update player's weapon images
     */
    void updateWeaponPlayer() {
        Platform.runLater(() ->  {
                try {

                    ArrayList<Button> selectWeapon = new ArrayList<>();
                    selectWeapon.add(selectWeaponButton1);
                    selectWeapon.add(selectWeaponButton2);
                    selectWeapon.add(selectWeaponButton3);

                    ArrayList<Button> reloadWeapon = new ArrayList<>();
                    reloadWeapon.add(bReloadWeapon1);
                    reloadWeapon.add(bReloadWeapon2);
                    reloadWeapon.add(bReloadWeapon3);

                    ArrayList<Button> discardWeapon = new ArrayList<>();
                    discardWeapon.add(bDiscardWeapon1);
                    discardWeapon.add(bDiscardWeapon2);
                    discardWeapon.add(bDiscardWeapon3);

                    ArrayList<Button> firemode = new ArrayList<>();
                    firemode.add(addFire1);
                    firemode.add(addFire2);
                    firemode.add(addFire3);



                    if(ControllerLogin.clientView.getPlayerCopy().getWeaponCardList()!=null) {
                        List<WeaponCard> weaponCards = ControllerLogin.clientView.getPlayerCopy().getWeaponCardList();
                        ArrayList<ImageView> imWeapon = new ArrayList<>();
                        imWeapon.add(imWeaponCard1);
                        imWeapon.add(imWeaponCard2);
                        imWeapon.add(imWeaponCard3);
                        for (int i = 0; i < weaponCards.size(); i++) {
                            imWeapon.get(i).setImage(setWeapon(weaponCards.get(i).getID()));
                            if (!weaponCards.get(i).isReloaded()) {
                                selectWeapon.get(i).setDisable(true);
                                firemode.get(i).setDisable(true);
                                reloadWeapon.get(i).setDisable(false);
                            } else {
                                selectWeapon.get(i).setDisable(false);
                                firemode.get(i).setDisable(false);
                                reloadWeapon.get(i).setDisable(true);

                            }

                            discardWeapon.get(i).setDisable(false);

                        }
                        for (int i = weaponCards.size(); i < 3; i++) {
                            imWeapon.get(i).setImage(new Image("emptyWeapon.jpg"));
                            selectWeapon.get(i).setDisable(true);
                            reloadWeapon.get(i).setDisable(true);
                            discardWeapon.get(i).setDisable(true);
                            firemode.get(i).setDisable(true);
                        }
                    }
                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

                }



        });

    }


    /**
     * update Player's Red Ammo
     *
     * @param red number of red ammo
     */
    private void updatePlayerRedAmmo(String red) {

        Platform.runLater(() ->  {
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

                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });

    }

    /**
     * update Player's Blue Ammo
     *
     * @param blu number of blu ammo
     */
    private void updatePlayerBlueAmmo(String blu) {

        Platform.runLater(() ->  {
                try {
                    ArrayList<ImageView> blueAmmo = new ArrayList<>();
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

                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });

    }


    /**
     * update Player's Yellow Ammo
     *
     * @param yellow number of yellow ammo
     */
    private void updatePlayerYellowAmmo(String yellow) {

        Platform.runLater(() ->  {
                try {
                    ArrayList<ImageView> yellowAmmo = new ArrayList();
                    yellowAmmo.add(yammo1);
                    yellowAmmo.add(yammo2);
                    yellowAmmo.add(yammo3);
                    int yellowNumber = Integer.parseInt(yellow);
                    yellowNumber--;
                    for (int counter = 0; counter < yellowAmmo.size(); counter++) {
                        yellowAmmo.get(counter).setVisible(false);
                    }
                    while (yellowNumber >= 0) {
                        yellowAmmo.get(yellowNumber).setVisible(true);
                        yellowNumber--;
                    }
                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }


        });

    }


    /**
     * update all Player's ammo
     *
     * @param red red ammo number
     * @param blue blue ammo number
     * @param yellow yellow ammo number
     */
     void updatePlayerAmmo(int red, int blue, int yellow) {
        Platform.runLater(() ->  {
                String redAmmo = String.valueOf(red);
                String blueAmmo = String.valueOf(blue);
                String yellowAmmo = String.valueOf(yellow);

                updatePlayerRedAmmo(redAmmo);
                updatePlayerBlueAmmo(blueAmmo);
                updatePlayerYellowAmmo(yellowAmmo);

        });

    }


    /**
     * print string from controller
     * @param string string from controller
     */
     void printf(String string) {
        Platform.runLater(() ->  {
                try {
                    labelProva.setText(string);
                }catch (NullPointerException e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });

    }

    /**
     * get close button for closing waiting room
     * @return closeWaiting button
     */
     Button getCloseWaiting() {

        return closeWaiting;

    }

    /**
     * change enemys' nickname on map's window
     */
    private void setEnemyNickname() {
        if (ControllerLogin.enemyView1 != null) {
            tabEnemy1.setText(ControllerLogin.enemyView1.getNickname());
        } else {
            tabEnemy1.setDisable(true);
            tabEnemy1.setStyle(transparent);
        }
        if (ControllerLogin.enemyView2 != null) {
            tabEnemy2.setText(ControllerLogin.enemyView2.getNickname());

        } else {
            tabEnemy2.setDisable(true);
            tabEnemy2.setStyle(transparent);
        }
        if (ControllerLogin.enemyView3 != null) {
            tabEnemy3.setText(ControllerLogin.enemyView3.getNickname());
        } else {
            tabEnemy3.setDisable(true);
            tabEnemy3.setStyle(transparent);
        }
        if (ControllerLogin.enemyView4 != null) {
            tabEnemy4.setText(ControllerLogin.enemyView4.getNickname());
        } else {
            tabEnemy4.setDisable(true);
            tabEnemy4.setStyle(transparent);
        }
    }


    /**
     * show possible character
     * @param event after been clicked

     */
    public void chooseYourCharacter(ActionEvent event) {

        Platform.runLater(() ->  {

                List<Integer> characterID = new LinkedList<>();
                for(int i=0; i<ControllerLogin.characters.size(); i++){
                    characterID.add(ControllerLogin.characters.get(i).getId());
                }

                rbSprog.setDisable(true);
                rbViolet.setDisable(true);
                rbDozer.setDisable(true);
                rbStruct.setDisable(true);
                rbBanshee.setDisable(true);
                    for (int i = 0; i < characterID.size(); i++) {
                        setPossibleCharacter(characterID.get(i));
                    }


        });


    }

    /**
     * set possible radio button character clickable
     * @param i int character id
     */
    private void setPossibleCharacter(int i){
        Platform.runLater(() ->  {
                switch (i){
                    case 1: rbSprog.setDisable(false);
                            break;
                    case 2: rbViolet.setDisable(false);
                            break;
                    case 3: rbStruct.setDisable(false);
                            break;
                    case 4: rbDozer.setDisable(false);
                            break;
                    case 5: rbBanshee.setDisable(false);
                }

        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /**
     * set ImageView of player character based on the character choosen
     *
     * @param event after been clicked
     */
    public void chooseCharacter(ActionEvent event) {
        Platform.runLater(() ->  {

                try {
                    if (rbSprog.isSelected()) {

                        ControllerLogin.clientView.createCharacterMessage(1);


                    }
                    if (rbViolet.isSelected()) {

                        ControllerLogin.clientView.createCharacterMessage(2);


                    }
                    if (rbStruct.isSelected()) {

                        ControllerLogin.clientView.createCharacterMessage(3);


                    }
                    if (rbDozer.isSelected()) {

                        ControllerLogin.clientView.createCharacterMessage(4);

                    }
                    if (rbBanshee.isSelected()) {

                        ControllerLogin.clientView.createCharacterMessage(5);

                    }



                    Stage stage = (Stage) rbBanshee.getScene().getWindow();
                    stage.close();

                } catch (NotCharacterException e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });

    }


    /**
     * open chooseCharacter window
     * @param event after been clicked
     * @throws Exception for opening fxml fil
     */
    public void selectCharacter(ActionEvent event) throws Exception{
        if (ControllerLogin.clientView.getPossibleCharacter() != null) {
            ControllerLogin.open("chooseCharacter.fxml", "CHOOSE YOUR CHARACTER", 650,400);




            Platform.runLater(() ->  {
                    try {

                            bselectCharacter.setVisible(false);
                            bselectCharacter.setDisable(true);
                    }
                    catch (Exception e){
                        Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                    }

            });



        }
        else
            labelProva.setText("please wait your turn");


    }
    /**
     *
     * set player character images on map
     */
     void updatePlayerCharacter(int i){
        Platform.runLater(() ->  {
                try {
                    ArrayList<Image> image = setCharacter(i, isFrenzyTime);
                    possibleActions.setVisible(true);
                    yourCharacter.setVisible(true);
                    yourCharacter.setImage(image.get(0));
                    possibleActions.setImage(image.get(1));
                    if(isFrenzyTime){
                        frenzyPlayer();
                    }

                } catch (NullPointerException e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });
    }


    /**
     * update Enemy Character Image
     * @param enemyView by ControllerLogin
     */
     void updateEnemyCharacter(ClientEnemyView enemyView) {

        Platform.runLater(() ->  {
                try {
                    ArrayList<Image> images = new ArrayList<>();
                    if (ControllerLogin.enemyView1.getCharacter() != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView1.getNickname())) {
                            images = setCharacter(ControllerLogin.enemyView1.getCharacter().getId(),isFrenzyTime);
                            imEnemyCharacter1.setImage(images.get(0));
                            enemy1Actions.setImage(images.get(1));
                        }
                    }
                    if (ControllerLogin.enemyView2.getCharacter() != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView2.getNickname())) {
                            images = setCharacter(ControllerLogin.enemyView2.getCharacter().getId(),isFrenzyTime);
                            imEnemyCharacter2.setImage(images.get(0));
                            enemy2Actions.setImage(images.get(1));
                        }
                    }
                    if (ControllerLogin.enemyView3.getCharacter() != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView3.getNickname())) {
                            images = setCharacter(ControllerLogin.enemyView3.getCharacter().getId(),isFrenzyTime);
                            imEnemyCharacter3.setImage(images.get(0));
                            enemy3Actions.setImage(images.get(1));
                        }
                    }
                    if (ControllerLogin.enemyView4.getCharacter() != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView4.getNickname())) {
                            images = setCharacter(ControllerLogin.enemyView4.getCharacter().getId(),isFrenzyTime);
                            imEnemyCharacter4.setImage(images.get(0));
                            enemy4Actions.setImage(images.get(1));
                        }
                    }

                }catch (NullPointerException e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });




    }

    /**
     * set each Character id with the image
     */
    private ArrayList<Image> setCharacter(int characterID, Boolean frenzy) {
        Image image1 = new Image("characters/characterBlue.jpg");
        Image image2 = new Image("characters/actionsBlue.jpg");
        switch (characterID) {
            case 1:
                image1 = new Image("characters/characterGreen.jpg");
                if(frenzy){
                    image2  = new Image("frenzyCharacters/frenzyActionGreen.png");
                }
                else {
                    image2 = new Image("characters/actionsGreen.jpg");
                }
                break;
            case 2:
                image1 = new Image("characters/characterViolet.jpg");
                if(frenzy){
                    image2  = new Image("frenzyCharacters/frenzyActionsViolet.png");
                }
                else {
                    image2 = new Image("characters/actionsViolet.jpg");
                }
                break;
            case 3:
                image1 = new Image("characters/characterYellow.jpg");
                if(frenzy){
                    image2  = new Image("frenzyCharacters/frenzyActionsYellow.png");
                }
                else {
                    image2 = new Image("characters/actionsYellow.jpg");
                }
                break;
            case 4:
                image1 = new Image("characters/characterGrey.jpg");
                if(frenzy){
                    image2  = new Image("frenzyCharacters/frenzyActionsGrey.png");
                }
                else {
                    image2 = new Image("characters/actionsGrey.jpg");
                }
                break;
            case 5:
                image1 = new Image("characters/characterBlue.jpg");
                if(frenzy){
                    image2  = new Image("frenzyCharacters/frenzyActionsBlue.png");
                }
                else {
                    image2 = new Image("characters/actionsBlue.jpg");
                }
                break;
            default: break;
        }
        ArrayList<Image> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);

        return images;

    }


    /**
     * update enemy's weapons
     * @param enemyView by ControllerLogin
     */
     void updateEnemyWeapon(ClientEnemyView enemyView) {
        Platform.runLater(() ->  {
                try {
                    if (ControllerLogin.enemyView1 != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView1.getNickname())) {
                            setEnemyWeapon(enemy1card1, enemy1card2, enemy1card3, ControllerLogin.enemyView1);
                        }
                    }
                    if (ControllerLogin.enemyView2 != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView2.getNickname())) {
                            setEnemyWeapon(enemy2card1, enemy2card2, enemy2card3, ControllerLogin.enemyView2);
                        }
                    }
                    if (ControllerLogin.enemyView3 != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView3.getNickname())) {
                            setEnemyWeapon(enemy3card1, enemy3card2, enemy3card3, ControllerLogin.enemyView3);
                        }
                    }
                    if (ControllerLogin.enemyView4 != null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView4.getNickname())) {
                            setEnemyWeapon(enemy4card1, enemy4card2, enemy4card3, ControllerLogin.enemyView4);
                        }
                    }
                }catch (NullPointerException e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });

    }

    /**
     * set enemy's weapon
     *
     * @param im1 imageview to add
     * @param im2 imageview to add
     * @param im3 imageview to add
     * @param enemyView by ControllerLogin
     */
    private void setEnemyWeapon(ImageView im1, ImageView im2, ImageView im3, ClientEnemyView enemyView) {
        Platform.runLater(() ->  {
        try {
            ArrayList<WeaponCard> weaponCards = enemyView.getUnloadedWeapon();
            int loadedWeapon = enemyView.getLoadedWeapon();
            int emptyWeapon = 3 - (weaponCards.size() + loadedWeapon);
            ArrayList<ImageView> enemy1card = new ArrayList<>();
            enemy1card.add(im1);
            enemy1card.add(im2);
            enemy1card.add(im3);


            for (int i = 0; i < emptyWeapon; i++) {
                enemy1card.get(i).setImage(new Image("emptyWeapon.jpg"));
            }
            int j=0;
            for (int i = emptyWeapon; i < (emptyWeapon + weaponCards.size()); i++) {

                enemy1card.get(i).setImage(setWeapon(weaponCards.get(j).getIDtype()));
                j++;
            }

            for (int k = (emptyWeapon + weaponCards.size()); k < 3; k++) {
                enemy1card.get(k).setImage(new Image("cards/AD_weapons_IT_0225.png"));
            }


        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
        }

        });

    }



    /**
     * update player's powerup
     */
     void updatePlayerPowerup() {
        Platform.runLater(() ->  {
                try {


                    ArrayList<Button> discardWeapon = new ArrayList<>();
                    discardWeapon.add(bDiscardPowerup1);
                    discardWeapon.add(bDiscardPowerup2);
                    discardWeapon.add(bDiscardPowerup3);
                    discardWeapon.add(bDiscardPowerup4);

                    ArrayList<Button> usePowerup = new ArrayList<>();
                    usePowerup.add(bPowerupCard1);
                    usePowerup.add(bPowerupCard2);
                    usePowerup.add(bPowerupCard3);
                    usePowerup.add(bPowerupCard4);


                    List<PowerupCard> powerupCards = ControllerLogin.clientView.getPlayerCopy().getPowerupCardList();
                    ArrayList<ImageView> imPower = new ArrayList<>();
                    imPower.add(imPowerupCard1);
                    imPower.add(imPowerupCard2);
                    imPower.add(imPowerupCard3);
                    imPower.add(imPowerupCard4);
                    for (int i = 0; i < powerupCards.size(); i++) {
                        imPower.get(i).setImage(setPowerup(powerupCards.get(i).getIDtype()));
                        discardWeapon.get(i).setDisable(false);
                        usePowerup.get(i).setDisable(false);
                    }
                    for (int i = powerupCards.size(); i < 4; i++) {
                        imPower.get(i).setImage(new Image("emptyPowerup.jpg"));
                        discardWeapon.get(i).setDisable(true);
                        usePowerup.get(i).setDisable(true);
                    }
                } catch (NullPointerException e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });

    }

    /**
     * set each powerup id with an image
     * @param powerupID get the powerup images by the id
     * @return poerup images
     */
    private Image setPowerup(int powerupID) {

        Image image = new Image("AD_powerups_IT_02.jpg");
        switch (powerupID) {
            case 32:
                image = new Image("cards/AD_powerups_IT_022.png");
                break;
            case 30:
                image = new Image("cards/AD_powerups_IT_023.png");
                break;
            case 31:
                image = new Image("cards/AD_powerups_IT_024.png");
                break;
            case 2:
                image = new Image("cards/AD_powerups_IT_025.png");
                break;
            case 0:
                image = new Image("cards/AD_powerups_IT_026.png");
                break;
            case 1:
                image = new Image("cards/AD_powerups_IT_027.png");
                break;
            case 12:
                image = new Image("cards/AD_powerups_IT_028.png");
                break;
            case 10:
                image = new Image("cards/AD_powerups_IT_029.png");
                break;
            case 11:
                image = new Image("cards/AD_powerups_IT_0210.png");
                break;
            case 22:
                image = new Image("cards/AD_powerups_IT_0211.png");
                break;
            case 20:
                image = new Image("cards/AD_powerups_IT_0212.png");
                break;
            case 21:
                image = new Image("cards/AD_powerups_IT_0213.png");
                break;
        }

        return image;

    }

    /**
     * the player decide to not shoot, the weapon won't be discarded
     * @param event after been clicked
     */
    public void dropWeapon(ActionEvent event) {

        if(ControllerLogin.clientView.getPlayerCopy().getCharacter()==null) {
            bselectCharacter.setVisible(true);
            bselectCharacter.setDisable(false);
        }
        printf("Please select a character");

        ControllerLogin.clientView.createNopeMessage();
    }


    /**
     * create a powerup message to send to the server
     * @param event after been clicked
     */
    public void usePowerup(ActionEvent event){

        Object source = event.getSource();
        List<PowerupCard> powerupCards = ControllerLogin.clientView.getPlayerCopy().getPowerupCardList();

        try {
            if (bPowerupCard1 == source) {
                createPowerupMessage(powerupCards.get(0));
                if(powerupCards.get(0).getIDtype()== 0 || powerupCards.get(0).getIDtype()== 1 || powerupCards.get(0).getIDtype()== 2){
                    selectTargetingScope=0;
                    bconvertAmmo1.setVisible(true);
                    bconvertAmmo1.setDisable(false);


                }
            }
            if (bPowerupCard2 == source) {
                createPowerupMessage(powerupCards.get(1));
                if(powerupCards.get(1).getIDtype()== 0 || powerupCards.get(1).getIDtype()== 1 || powerupCards.get(1).getIDtype()== 2){
                    selectTargetingScope=1;
                    bconvertAmmo2.setVisible(true);
                    bconvertAmmo2.setDisable(false);

                }
            }
            if (bPowerupCard3 == source) {
                createPowerupMessage(powerupCards.get(2));
                if(powerupCards.get(2).getIDtype()== 0 || powerupCards.get(2).getIDtype()== 1 || powerupCards.get(2).getIDtype()== 2){
                    selectTargetingScope=2;
                    bconvertAmmo3.setDisable(false);
                    bconvertAmmo3.setVisible(true);

                }
            }
            if (bPowerupCard4 == source) {
                createPowerupMessage(powerupCards.get(3));
                if(powerupCards.get(3).getIDtype()== 0 || powerupCards.get(3).getIDtype()== 1 || powerupCards.get(3).getIDtype()== 2){
                    selectTargetingScope=3;
                    bconvertAmmo4.setDisable(false);
                    bconvertAmmo4.setVisible(true);
                }
            }
        }catch (Exception e){
            Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
        }
    }


    /**
     * connect each powerup id with the powerup message and create a powerupmessage to send to the server
     * @param usedPowerup poerup choosen by the player to be used
     */
    private void createPowerupMessage(PowerupCard usedPowerup){
        switch (usedPowerup.getIDtype()){
            case 10: {
                ControllerLogin.clientView.createNewtonMessage((NewtonCard)usedPowerup);
                break;
            }
            case 11: {
                ControllerLogin.clientView.createNewtonMessage((NewtonCard)usedPowerup);
                break;
            }
            case 12: {
                ControllerLogin.clientView.createNewtonMessage((NewtonCard)usedPowerup);
                break;
            }
            case 20: {
                ControllerLogin.clientView.createTeleporterMessage((TeleporterCard) usedPowerup);
                break;
            }
            case 21: {
                ControllerLogin.clientView.createTeleporterMessage((TeleporterCard) usedPowerup);
                break;
            }
            case 22: {
                ControllerLogin.clientView.createTeleporterMessage((TeleporterCard) usedPowerup);
                break;
            }
            case 30: {
                ControllerLogin.clientView.createTagbackGranadeMessage((TagbackGrenadeCard) usedPowerup);
                break;
            }
            case 31: {
                ControllerLogin.clientView.createTagbackGranadeMessage((TagbackGrenadeCard) usedPowerup);
                break;
            }
            case 32: {
                ControllerLogin.clientView.createTagbackGranadeMessage((TagbackGrenadeCard) usedPowerup);
                break;
            }
            default: {
                break;
            }

        }
    }


    /**
     * change player's images with frenzy's
     */
     void frenzyPlayer() {
         Platform.runLater(() -> {
             try {
                 if (ControllerLogin.clientView.getPlayerCopy().isFrenzyDeath()) {
                     ArrayList<Image> images = setFrenzyCharacter(ControllerLogin.clientView.getPlayerCopy().getCharacter().getId());
                     yourCharacter.setImage(images.get(0));
                     possibleActions.setImage(images.get(1));
                 }
                 moveButton.setDisable(true);
                 moveButton.setVisible(false);
                 grabButton.setDisable(true);
                 grabButton.setVisible(false);
                 shootButton.setDisable(true);
                 shootButton.setVisible(false);

                 if (ControllerLogin.clientView.getPlayerCopy().isFirstGroupFrenzy()) {
                     bFrenzyGrab1.setVisible(true);
                     bFrenzyGrab1.setDisable(false);
                     bFrenzyGrab1.setStyle(transparent);
                     bFrenzyMove1.setVisible(true);
                     bFrenzyMove1.setDisable(false);
                     bFrenzyMove1.setStyle(transparent);
                     bFrenzyShoot1.setVisible(true);
                     bFrenzyShoot1.setStyle(transparent);
                     bFrenzyShoot1.setDisable(false);
                 } else {
                     bFrenzyGrab2.setVisible(true);
                     bFrenzyGrab2.setDisable(false);
                     bFrenzyGrab2.setStyle(transparent);
                     bFrenzyShoot2.setVisible(true);
                     bFrenzyShoot2.setStyle(transparent);
                     bFrenzyShoot2.setDisable(false);
                 }
             } catch (Exception e) {
                 Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
             }
         });

    }

    /**
     * connect each character id with the frenzy image
     * @param characterID take the right image base on the character id
     * @return an arraylist of character and action image
     */
    private  ArrayList<Image> setFrenzyCharacter(int characterID){
        Image image1 = new Image("characters/characterBlue.jpg");
        Image image2 = new Image("characters/actionsBlue.jpg");
        switch (characterID) {
            case 1:
                image1 = new Image("frenzyCharacters/frenzyCharacterGreen.png");
                image2 = new Image("frenzyCharacters/frenzyActionGreen.png");
                break;
            case 2:
                image1 = new Image("frenzyCharacters/frenzyCharacterViolet.png");
                image2 = new Image("frenzyCharacters/frenzyActionsViolet.png");
                break;
            case 3:
                image1 = new Image("frenzyCharacters/frenzyCharacterYellow.png");
                image2 = new Image("frenzyCharacters/frenzyActionsYellow.png");
                break;
            case 4:
                image1 = new Image("frenzyCharacters/frenzyCharacterGrey.png");
                image2 = new Image("frenzyCharacters/frenzyActionsGrey.png");
                break;
            case 5:
                image1 = new Image("frenzyCharacters/frenzyCharacterBlue.png");
                image2 = new Image("frenzyCharacters/frenzyActionsBlue.png");
                break;
            default: break;
        }
        ArrayList<Image> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);

        return images;



    }

    /**
     * update enemy if they are in frenzy mode
     */
    void frenzyEnemy(ClientEnemyView enemyView){
        Platform.runLater(() ->{
            try {
                if (enemyView.isFrenzyDeath()) {
                    ArrayList<Image> images = setFrenzyCharacter(enemyView.getCharacter().getId());
                    if (enemyView.getNickname().equals(ControllerLogin.enemyView1.getNickname())) {
                        imEnemyCharacter1.setImage(images.get(0));
                        enemy1Actions.setImage(images.get(1));
                    }
                    if (enemyView.getNickname().equals(ControllerLogin.enemyView2.getNickname())) {
                        imEnemyCharacter2.setImage(images.get(0));
                        enemy2Actions.setImage(images.get(1));
                    }
                    if (enemyView.getNickname().equals(ControllerLogin.enemyView3.getNickname())) {
                        imEnemyCharacter3.setImage(images.get(0));
                        enemy3Actions.setImage(images.get(1));
                    }
                    if (enemyView.getNickname().equals(ControllerLogin.enemyView4.getNickname())) {
                        imEnemyCharacter4.setImage(images.get(0));
                        enemy4Actions.setImage(images.get(1));
                    }

                }
            }
            catch(NullPointerException e){
                Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
            }

        });

    }


    /**
     * update player's damage on gui map window
     */
     void updatePlayerDamage(){
        Platform.runLater(() ->  {
                try {
                    List<Player> players = ControllerLogin.clientView.getPlayerCopy().getDamage();
                    ArrayList<ImageView> imDamage = new ArrayList<>();

                    setDamage(imDamage, playerDamage0, playerDamage1, playerDamage2, playerDamage3, playerDamage4, playerDamage5,
                            playerDamage6, playerDamage7, playerDamage8, playerDamage9, playerDamage10, playerDamage11);

                    for(int i=0; i<imDamage.size(); i++){
                        imDamage.get(i).setVisible(false);
                    }
                    //danni a zero


                    for(int i=0; i<players.size(); i++){
                        imDamage.get(i).setVisible(true);
                        imDamage.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(), 1));



                                }




                }
                catch (Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");
                }

        });
    }

    /**
     * update enemy's damage on gui map window
     * @param enemyView by controllerLogin
     */
     void updateEnemyDamage(ClientEnemyView enemyView){
        Platform.runLater(() ->  {
                try {
                    List<Player> players= enemyView.getDamage();
                    ArrayList<ImageView> imDamage = new ArrayList<>();
                    if(ControllerLogin.enemyView1!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView1.getNickname())){
                            setDamage(imDamage, enemy1Damage0,enemy1Damage1, enemy1Damage2, enemy1Damage3, enemy1Damage4,enemy1Damage5,
                                    enemy1Damage6,enemy1Damage7, enemy1Damage8, enemy1Damage9, enemy1Damage10,enemy1Damage11);
                            for(int i=0; i<imDamage.size(); i++){
                                imDamage.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imDamage.get(i).setVisible(true);
                                imDamage.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }
                    if(ControllerLogin.enemyView2!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView2.getNickname())){
                            setDamage(imDamage, enemy2Damage0,enemy2Damage1, enemy2Damage2, enemy2Damage3, enemy2Damage4,enemy2Damage5,
                                    enemy2Damage6,enemy2Damage7, enemy2Damage8, enemy2Damage9, enemy2Damage10,enemy2Damage11);
                            for(int i=0; i<imDamage.size(); i++){
                                imDamage.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imDamage.get(i).setVisible(true);
                                imDamage.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }
                    if(ControllerLogin.enemyView3!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView3.getNickname())){
                            setDamage(imDamage, enemy3Damage0,enemy3Damage1, enemy3Damage2, enemy3Damage3, enemy3Damage4,enemy3Damage5,
                                    enemy3Damage6,enemy3Damage7, enemy3Damage8, enemy3Damage9, enemy3Damage10,enemy3Damage11);
                            for(int i=0; i<imDamage.size(); i++){
                                imDamage.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imDamage.get(i).setVisible(true);
                                imDamage.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }
                    if(ControllerLogin.enemyView4!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView4.getNickname())){
                            setDamage(imDamage, enemy4Damage0,enemy4Damage1, enemy4Damage2, enemy4Damage3, enemy4Damage4,enemy4Damage5,
                                    enemy4Damage6,enemy4Damage7, enemy4Damage8, enemy4Damage9, enemy4Damage10,enemy4Damage11);
                            for(int i=0; i<imDamage.size(); i++){
                                imDamage.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imDamage.get(i).setVisible(true);
                                imDamage.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }


                }
                catch (Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

                }

        });
    }

    /**
     * add imageview to an arraylist
     * @param imDamage ararylist to insert imageviews
     * @param im1 imageview to add
     * @param im2 imageview to add
     * @param im3 imageview to add
     * @param im4 imageview to add
     * @param im5 imageview to add
     * @param im6 imageview to add
     * @param im7 imageview to add
     * @param im8 imageview to add
     * @param im9 imageview to add
     * @param im10 imageview to add
     * @param im11 imageview to add
     */
    private void setDamage(ArrayList<ImageView> imDamage, ImageView im1,ImageView im2,ImageView im3,ImageView im4,
                          ImageView im5,ImageView im6,ImageView im7,ImageView im8,ImageView im9,ImageView im10,ImageView im11, ImageView im12 ){
        imDamage.add(im1);
        imDamage.add(im2);
        imDamage.add(im3);
        imDamage.add(im4);
        imDamage.add(im5);
        imDamage.add(im6);
        imDamage.add(im7);
        imDamage.add(im8);
        imDamage.add(im9);
        imDamage.add(im10);
        imDamage.add(im11);
        imDamage.add(im12);

    }

    /**
     * connect each drop image with the character
     * @param characterID character id to set images
     * @return image of character damage
     */
    private Image setDamageImages(int characterID, int doubleDrop) {
        Image image = new Image("Icon/BlueDrop.png");
        if (doubleDrop==1) {
            switch (characterID) {
                case 1:
                    image = new Image("Icon/GreenDrop.png");

                    break;
                case 2:
                    image = new Image("Icon/PurpleDrop.png");

                    break;
                case 3:
                    image = new Image("Icon/YellowDrop.png");

                    break;
                case 4:
                    image = new Image("Icon/GreyDrop.png");

                    break;
                case 5:
                    image = new Image("Icon/BlueDrop.png");

                    break;
                default:
                    break;
            }

        } else {
            switch (characterID) {
                case 1:
                    image = new Image("Icon/GreenDouble.png");

                    break;
                case 2:
                    image = new Image("Icon/PurpleDouble.png");

                    break;
                case 3:
                    image = new Image("Icon/YellowDouble.png");

                    break;
                case 4:
                    image = new Image("Icon/GreyDouble.png");

                    break;
                case 5:
                    image = new Image("Icon/BlueDouble.png");

                    break;
                default:
                    break;

            }

        }
        return image;
    }


    /**
     * update player's marks on gui
     */
    void updatePlayerMarks(){
        Platform.runLater(() ->  {
                try {
                    List<Player> marks = ControllerLogin.clientView.getPlayerCopy().getMark().getMarkReceived();
                    ArrayList<ImageView> imMarks = new ArrayList<>();
                    setDamage(imMarks, mark1, mark2, mark3, mark4, mark5, mark6, mark7, mark8, mark9, mark10, mark11, mark12);

                    for(int i=0; i<imMarks.size(); i++){
                        imMarks.get(i).setVisible(false);
                    }
                    for(int i=0; i<marks.size(); i++){
                        imMarks.get(i).setVisible(true);
                        imMarks.get(i).setImage(setDamageImages(marks.get(i).getCharacter().getId(), 1));
                    }
                }
                catch (Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

                }

        });
    }

    /**
     * update enemy marks on gui
     * @param enemyView by ControllerLogin
     */
     void updateEnemyMarks(ClientEnemyView enemyView){

        Platform.runLater(() ->  {
                try {
                    List<Player> players= enemyView.getMark();
                    ArrayList<ImageView> imMarks = new ArrayList<>();
                    if(ControllerLogin.enemyView1!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView1.getNickname())){
                            setDamage(imMarks,enemy1mark1, enemy1mark2, enemy1mark3, enemy1mark4,enemy1mark5,
                                    enemy1mark6,enemy1mark7, enemy1mark8, enemy1mark9, enemy1mark10,enemy1mark11, enemy1mark12);
                            for(int i=0; i<imMarks.size(); i++){
                                imMarks.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imMarks.get(i).setVisible(true);
                                imMarks.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(), 1));
                            }
                        }
                    }
                    if(ControllerLogin.enemyView2!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView2.getNickname())){
                            setDamage(imMarks,enemy2mark1, enemy2mark2, enemy2mark3, enemy2mark4,enemy2mark5,
                                    enemy2mark6,enemy2mark7, enemy2mark8, enemy2mark9, enemy2mark10,enemy2mark11, enemy2mark12);
                            for(int i=0; i<imMarks.size(); i++){
                                imMarks.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imMarks.get(i).setVisible(true);
                                imMarks.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }
                    if(ControllerLogin.enemyView3!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView3.getNickname())){
                            setDamage(imMarks,enemy3mark1, enemy3mark2, enemy3mark3, enemy3mark4,enemy3mark5,
                                    enemy3mark6,enemy3mark7, enemy3mark8, enemy3mark9, enemy3mark10,enemy3mark11, enemy3mark12);
                            for(int i=0; i<imMarks.size(); i++){
                                imMarks.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imMarks.get(i).setVisible(true);
                                imMarks.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }
                    if(ControllerLogin.enemyView4!=null){
                        if(enemyView.getNickname().equals(ControllerLogin.enemyView4.getNickname())){
                            setDamage(imMarks,enemy4mark1, enemy4mark2, enemy4mark3, enemy4mark4,enemy4mark5,
                                    enemy4mark6,enemy4mark7, enemy4mark8, enemy4mark9, enemy4mark10,enemy4mark11, enemy4mark12);
                            for(int i=0; i<imMarks.size(); i++){
                                imMarks.get(i).setVisible(false);
                            }
                            for(int i=0; i<players.size(); i++){
                                imMarks.get(i).setVisible(true);
                                imMarks.get(i).setImage(setDamageImages(players.get(i).getCharacter().getId(),1));
                            }
                        }
                    }


                }
                catch (Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

                }

        });


    }


    /**
     * update player's skull on gui map window
     */
     void updatePlayerSkull(){
        Platform.runLater(() ->  {
                try {
                    int skullNumber = ControllerLogin.clientView.getPlayerCopy().getSkull();
                    ArrayList<ImageView> imSkulls = new ArrayList<>();
                    imSkulls.add(playerSkull1);
                    imSkulls.add(playerSkull2);
                    imSkulls.add(playerSkull3);
                    imSkulls.add(playerSkull4);
                    imSkulls.add(playerSkull5);
                    imSkulls.add(playerSkull6);
                     if(!ControllerLogin.clientView.getPlayerCopy().isFrenzyDeath()){
                         for(int i=0; i<skullNumber ; i++){
                             imSkulls.get(i).setImage(new Image("Icon/skull.png"));
                         }
                     }
                     else{
                         for(int i=0; i<imSkulls.size() ; i++){
                             imSkulls.get(i).setVisible(false);
                         }

                     }
                }
                catch (Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

                }

        });

    }


    /**
     * update enemy skulls
     * @param enemyView by ControllerLogin
     */
     void updateEnemySkull(ClientEnemyView enemyView){
        Platform.runLater(() ->  {
                try {
                    int enemySkull= enemyView.getSkull();
                    ArrayList <ImageView> imSkulls = new ArrayList<>();
                    if(ControllerLogin.enemyView1!=null) {
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView1.getNickname())) {
                            setSkull(imSkulls, enemy1Skull1, enemy1Skull2, enemy1Skull3, enemy1Skull4, enemy1Skull5, enemy1Skull6);
                            if (!ControllerLogin.enemyView1.isFrenzyDeath()) {
                                for (int i = 0; i < enemySkull; i++) {

                                    imSkulls.get(i).setImage(new Image("Icon/skull.png"));
                                }
                            } else {
                                for (int i = 0; i <imSkulls.size(); i++) {
                                    imSkulls.get(i).setVisible(false);
                                }
                            }
                        }
                    }
                    if(ControllerLogin.enemyView2!=null){
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView2.getNickname())) {
                            setSkull(imSkulls, enemy2Skull1, enemy2Skull2, enemy2Skull3, enemy2Skull4, enemy2Skull5, enemy2Skull6);
                            if (!ControllerLogin.enemyView2.isFrenzyDeath()) {
                                for (int i = 0; i < enemySkull; i++) {
                                    imSkulls.get(i).setImage(new Image("Icon/skull.png"));
                                }
                            } else {
                                for (int i = 0; i < enemySkull; i++) {
                                    imSkulls.get(i).setVisible(false);
                                }
                            }
                        }

                    }
                    if(ControllerLogin.enemyView3!=null){
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView3.getNickname())) {
                            setSkull(imSkulls, enemy3Skull1, enemy3Skull2, enemy3Skull3, enemy3Skull4, enemy3Skull5, enemy3Skull6);
                            if (!ControllerLogin.enemyView3.isFrenzyDeath()) {
                                for (int i = 0; i < enemySkull; i++) {
                                    imSkulls.get(i).setImage(new Image("Icon/skull.png"));
                                }
                            } else {
                                for (int i = 0; i < enemySkull; i++) {
                                    imSkulls.get(i).setVisible(false);
                                }
                            }
                        }

                    }
                    if(ControllerLogin.enemyView4!=null){
                        if (enemyView.getNickname().equals(ControllerLogin.enemyView4.getNickname())) {
                            setSkull(imSkulls, enemy4Skull1, enemy4Skull2, enemy4Skull3, enemy4Skull4, enemy4Skull5, enemy4Skull6);
                            if (!ControllerLogin.enemyView4.isFrenzyDeath()) {
                                for (int i = 0; i < enemySkull; i++) {
                                    imSkulls.get(i).setImage(new Image("Icon/skull.png"));
                                }
                            } else {
                                for (int i = 0; i < enemySkull; i++) {
                                    imSkulls.get(i).setVisible(false);
                                }
                            }
                        }

                    }


                }
                catch (Exception e){
                    Logger.getLogger(Controller.class.getName()).log(Level.FINE, "do nothing");

                }

        });


    }

    /**
     * set skull images in an arraylist
     * @param imSkulls Arraylist to insert imageviews
     * @param im1 imageview to add
     * @param im2 imageview to add
     * @param im3 imageview to add
     * @param im4 imageview to add
     * @param im5 imageview to add
     * @param im6 imageview to add
     * @return arraylist of imageview of skull
     */
    private ArrayList<ImageView> setSkull(ArrayList <ImageView> imSkulls, ImageView im1,ImageView im2,ImageView im3,ImageView im4,
                                         ImageView im5,ImageView im6){
        imSkulls.add(im1);
        imSkulls.add(im2);
        imSkulls.add(im3);
        imSkulls.add(im4);
        imSkulls.add(im5);
        imSkulls.add(im6);
        return imSkulls;
    }


    /**
     * update players t on gui's cell
     * @param cell Cell to update
     */
     void updatePlayersPosition(Cell cell){

        Platform.runLater(() ->  {
                try {

                    List<Player> players = cell.getPlayerHere();
                    ArrayList<ImageView> imPlayerPosition = setCellPlayersPosition(cell.getCoordinateX(), cell.getCoordinateY());
                    for (ImageView imageView : imPlayerPosition) {
                        imageView.setVisible(false);
                    }
                    for (int i = 0; i < players.size(); i++) {
                        imPlayerPosition.get(i).setImage(setPositionPlayerImages(players.get(i).getCharacter().getId()));
                        imPlayerPosition.get(i).setVisible(true);
                    }
                } catch (Exception e) {
                    Logger.getLogger(Controller.class.getName()).log(Level.INFO, "do nothing");


                }

        });

    }

    /**
     * add ImageView position in an arraylist
     * @param imPlayerPosition arraylist to insert imageviews
     * @param im1 imageview to add
     * @param im2 imageview to add
     * @param im3 imageview to add
     * @param im4 imageview to add
     * @param im5 imageview to add
     * @return  arraylist of imageviews
     */
    public ArrayList<ImageView> setCellImageViewPosition(ArrayList<ImageView> imPlayerPosition, ImageView im1, ImageView im2, ImageView im3, ImageView im4,ImageView im5 ){
        imPlayerPosition.add(im1);
        imPlayerPosition.add(im2);
        imPlayerPosition.add(im3);
        imPlayerPosition.add(im4);
        imPlayerPosition.add(im5);
        return imPlayerPosition;
    }


    /**
     * set cell coordinates with player's position imageviews
     * @param x coordinate x of cell
     * @param y coordinate y of cell
     * @return arraylist of images of player position in cell
     */
    private ArrayList<ImageView>  setCellPlayersPosition(int x, int y){
        ArrayList<ImageView> images = new ArrayList<>();
        if(x==0 && y==0)
            images= setCellImageViewPosition(images, imPlayer1Cell00, imPlayer2Cell00,imPlayer3Cell00,imPlayer4Cell00,imPlayer5Cell00);
        if(x==0 && y==1)
            images= setCellImageViewPosition(images, imPlayer1Cell01, imPlayer2Cell01,imPlayer3Cell01,imPlayer4Cell01,imPlayer5Cell01);
        if(x==0 && y==2)
            images= setCellImageViewPosition(images, imPlayer1Cell02, imPlayer2Cell02,imPlayer3Cell02,imPlayer4Cell02,imPlayer5Cell02);
        if(x==1 && y==0)
            images= setCellImageViewPosition(images, imPlayer1Cell10, imPlayer2Cell10,imPlayer3Cell10,imPlayer4Cell10,imPlayer5Cell10);
        if(x==1 && y==1)
            images= setCellImageViewPosition(images, imPlayer1Cell11, imPlayer2Cell11,imPlayer3Cell11,imPlayer4Cell11,imPlayer5Cell11);
        if(x==1 && y==2)
            images= setCellImageViewPosition(images, imPlayer1Cell12, imPlayer2Cell12,imPlayer3Cell12,imPlayer4Cell12,imPlayer5Cell12);
        if(x==2 && y==0)
            images= setCellImageViewPosition(images, imPlayer1Cell20, imPlayer2Cell20,imPlayer3Cell20,imPlayer4Cell20,imPlayer5Cell20);
        if(x==2 && y==1)
            images= setCellImageViewPosition(images, imPlayer1Cell21, imPlayer2Cell21,imPlayer3Cell21,imPlayer4Cell21,imPlayer5Cell21);
        if(x==2 && y==2)
            images= setCellImageViewPosition(images, imPlayer1Cell22, imPlayer2Cell22,imPlayer3Cell22,imPlayer4Cell22,imPlayer5Cell22);
        if(x==3 && y==0)
            images= setCellImageViewPosition(images, imPlayer1Cell30, imPlayer2Cell30,imPlayer3Cell30,imPlayer4Cell30,imPlayer5Cell30);
        if(x==3 && y==1)
            images= setCellImageViewPosition(images, imPlayer1Cell31, imPlayer2Cell31,imPlayer3Cell31,imPlayer4Cell31,imPlayer5Cell31);
        if(x==3 && y==2)
            images= setCellImageViewPosition(images, imPlayer1Cell32, imPlayer2Cell32,imPlayer3Cell32,imPlayer4Cell32,imPlayer5Cell32);


            return images;


    }


    /**
     * set each position's image with the character color
     * @param characterID character id to select the right image
     * @return icon character image
     */
    private Image setPositionPlayerImages(int characterID){
        Image image = new Image("Icon/Blu.png");

        switch (characterID){
            case 1:
                image = new Image("Icon/Verde.png");

                break;
            case 2:
                image = new Image("Icon/Viola.png");

                break;
            case 3:
                image = new Image("Icon/Giallo.png");

                break;
            case 4:
                image = new Image("Icon/Grigio.png");

                break;
            case 5:
                image = new Image("Icon/Blu.png");

                break;
            default: break;
        }
        return image;
    }


    /**
     * print on gui match id
     * @param matchId string of match id
     */
    private void showMatchID(String matchId){
        labelMatchID.setText("Match ID :" + matchId);
    }

    /**
     * send Targeting scope message
     * @param event after been clicked
     */
    public void chooseTargetingAmmo(ActionEvent event) {

        List<PowerupCard> powerupCards = ControllerLogin.clientView.getPlayerCopy().getPowerupCardList();
        if (rbRedAmmo.isSelected()) {
            ControllerLogin.clientView.createTargetingScopeMessage((TargetingScopeCard) powerupCards.get(selectTargetingScope), ColorRYB.RED);

            Stage stage = (Stage) rbRedAmmo.getScene().getWindow();
            stage.close();
        }

        if (rbYellowAmmo.isSelected()) {
            ControllerLogin.clientView.createTargetingScopeMessage((TargetingScopeCard) powerupCards.get(selectTargetingScope), ColorRYB.YELLOW);

            Stage stage = (Stage) rbRedAmmo.getScene().getWindow();
            stage.close();
        }

        if (rbBlueAmmo.isSelected()) {
            ControllerLogin.clientView.createTargetingScopeMessage((TargetingScopeCard) powerupCards.get(selectTargetingScope), ColorRYB.BLUE);

            Stage stage = (Stage) rbRedAmmo.getScene().getWindow();
            stage.close();

        }


    }


    /**
     * when a targeting scope is select open window to choose the ammo to convert
     * @param event after been clicked
     * @throws Exception for opening fxml file
     */
    public void convertAmmo(ActionEvent event) throws Exception{
       ControllerLogin.open("targetingScopeColor.fxml", "CHOOSE YOUR AMMO",223,346 );
        bconvertAmmo1.setDisable(true);
        bconvertAmmo2.setDisable(true);
        bconvertAmmo3.setDisable(true);
        bconvertAmmo4.setDisable(true);
        bconvertAmmo1.setVisible(false);
        bconvertAmmo2.setVisible(false);
        bconvertAmmo3.setVisible(false);
        bconvertAmmo4.setVisible(false);

    }

}