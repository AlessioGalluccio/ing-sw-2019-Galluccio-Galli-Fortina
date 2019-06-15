package it.polimi.se2019.ui;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.configureMessage.LoginMessage;
import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.clientView.ClientMapView;
import it.polimi.se2019.view.clientView.ClientSkullBoardView;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class ControllerLogin implements UiInterface {

    static Controller controller;
    static ClientView clientView;
    static SkullBoardView skullBoardView;
    static ClientEnemyView enemyView1;
    static ClientEnemyView enemyView2;
    static ClientEnemyView enemyView3;
    static ClientEnemyView enemyView4;
    static ClientMapView mapView;

    @FXML
    private TextField username;

    @FXML
    private Label status;

    @FXML
    public TextField matchID;

    @FXML
    public Button login;

    public LoginMessage loginMessage;


    public void loginButton(ActionEvent event) throws Exception, InterruptedException {
        if (matchID.getText().equals("")) {
            loginMessage = new LoginMessage(username.getText());


        } else {
            int matchId = Integer.parseInt(matchID.getText());
            loginMessage = new LoginMessage(username.getText(), matchId);

        }


    }





    @Override
    public void login(boolean success, boolean isFirst) {

        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                if(success==true) {
                    status.setText("LOGIN SUCCESSFUL!");
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();

                    if( isFirst== true) {
                        try {
                            open("chooseMap.fxml", " CHOOSE MAP", 470, 400);
                        } catch (Exception e) {
                        }
                    }

                    if (isFirst == false){
                        try {
                            open("WaitingRoom.fxml", "LEAN BACK AND CHILL", 520, 400);
                        } catch (Exception e) {
                        }
                    }
                }
                else{
                    status.setText("LOGIN FAILED");
                }


            }
        });

    }

    /**
     * open file FXML in a window
     * @param fileName
     * @param windowName
     * @param width
     * @param height
     * @throws Exception
     */
    public void open(String fileName, String windowName, int width, int height) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fileName));
        Stage primaryStage = new Stage();
        primaryStage.setTitle(windowName);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();

    }

    @Override
    public void selectedMap(int choosMap){
        controller.updateMap(choosMap);
    }

    /**
     * open map window
     */
    @Override
    public void startGame() {
        try {
            open("Map1.fxml", "ADRENALINE", 700, 700);
        }
        catch (Exception e) {
        }

    }

    @Override
    public void disconnect(int matchID) {

    }

    @Override
    public void setSkullBoard(SkullBoardView skullBoard) {
        skullBoardView = skullBoard;
        Controller.setSkullBoard((ClientSkullBoardView) skullBoardView);
    }

    @Override
    public void setMapView(MapView mapView) {

    }

    @Override
    public void setEnemyView(EnemyView enemyView) {

    }

    /**
     * update player's points on gui calling method on ui controller
     */
    public void updatePlayerPoints(){
        Player player = clientView.getPlayerCopy();
        controller.updatePoints(player.getNumPoints());
    }

    /**
     * update number of skulls on gui map
     * @param skullNumber
     */
    public void updateSkullMap(int skullNumber){
        String skullNumberString = String.valueOf(skullNumber);
        controller.updateSkullBoard(skullNumberString);
    }

    /**
     * update initial setting of map number and skulls
     * @param choosenMap
     * @param skull
     */
    public void updateSetting(int choosenMap, int skull){
        this.updateSkullMap(skull);
        this.selectedMap(choosenMap);
    }

    /**
     * update weapons images on map
     */
    public void updateWeaponMap(){
        controller.updateWeaponMap();
    }

    /**
     * update player' weapons images
     */
    public void updateWeaponPlayer(){
        controller.updateWeaponPlayer();
    }

    /**
     * update player ammo
     */
    public void updatePlayerAmmo(){
        try {
            int red = clientView.getPlayerCopy().getAmmo().getRedAmmo();
            int blue = clientView.getPlayerCopy().getAmmo().getBlueAmmo();
            int yellow = clientView.getPlayerCopy().getAmmo().getYellowAmmo();
            controller.updatePlayerAmmo(red, blue, yellow);
        }
        catch (NullPointerException e){

        }

    }

    /**
     * update game display
     */
    public void updateMap(){

        //TODO mettere tutti i metodi di aggiornamento
        updateWeaponMap();
        updateWeaponPlayer();
        updatePlayerAmmo();
    }
}