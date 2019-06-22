package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.messages.LoginMessage;
import it.polimi.se2019.network.rmi.RMIClient;
import it.polimi.se2019.network.socket.SocketClient;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ControllerLogin implements UiInterface {

    private static FXMLLoader fxmlLoader;
    public Label wrongIp;


    private Controller controller;
    static ClientView clientView;
    static SkullBoardView skullBoardView;
    static ClientEnemyView enemyView1;
    static ClientEnemyView enemyView2;
    static ClientEnemyView enemyView3;
    static ClientEnemyView enemyView4;
    static ClientMapView mapView;



    @FXML
    public RadioButton rbRmi;
    @FXML
    public RadioButton rbSocket;
    @FXML
    public TextField ipAddress;
    @FXML
    private TextField username;

    @FXML
    private Label status;

    @FXML
    public TextField matchID;

    @FXML
    public Button login;

    private boolean firstAgain = false;

    static List<Character> characters;


    /**
     * send setting when login button is clicked
     * @param event
     * @throws Exception
     * @throws InterruptedException
     */
    public void loginButton(ActionEvent event) throws Exception, InterruptedException {
        //if(!ipAddress.getText().equals("")) {
            if (clientView == null) {
                clientView = new ClientView();
            }
            clientView.setUi(this);
        if(!ipAddress.getText().equals("")) {
            try {

                    if (rbRmi.isSelected()) {
                        RMIClient rmi = new RMIClient(clientView, ipAddress.getText());
                        rmi.connect();
                        clientView.setUp(rmi);
                    } else {
                        SocketClient socket = new SocketClient(9001, ipAddress.getText(), clientView);
                        socket.connect();
                        clientView.setUp(socket);
                    }

            }
            catch(UnknownHostException | RemoteException ex){
                wrongIp.setVisible(true);
            }

            if (matchID.getText().equals("")) {
                clientView.createLoginMessage(username.getText(), -1);

            } else {
                int matchId = Integer.parseInt(matchID.getText());
                clientView.createLoginMessage(username.getText(), matchId);
            }

       }

    }


    /**
     * close login window and one choosemap window if the player is the first, else open waiting room
     * @param success
     * @param isFirst
     */
    @Override
    public void login(boolean success, boolean isFirst) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (success && isFirst && firstAgain) {
                    try {
                        fxmlLoader = open("WaitingRoom.fxml", "LEAN BACK AND CHILL", 520, 400);
                        controller = fxmlLoader.getController();
                    } catch (Exception e) {
                    }
                }
                else if (success) {
                    System.out.println(8);
                    status.setText("LOGIN SUCCESSFUL!");
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();
                    System.out.println(8);
                    if (isFirst) {
                        try {
                            System.out.println(8);
                            fxmlLoader = open("chooseMap.fxml", " CHOOSE MAP", 450, 530);
                            controller = fxmlLoader.getController();
                            firstAgain = true;
                            System.out.println(8);
                        } catch (Exception e) {
                        }
                    } else {
                        try {
                            fxmlLoader = open("WaitingRoom.fxml", "LEAN BACK AND CHILL", 520, 400);
                            controller = fxmlLoader.getController();
                        } catch (Exception e) {
                        }
                    }
                } else {
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
    static FXMLLoader open(String fileName, String windowName, int width, int height) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLogin.class.getClassLoader().getResource(fileName));
        Parent root = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle(windowName);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            ControllerLogin.clientView.shutdownServer();
        });
        if(!fileName.equalsIgnoreCase("Map1.fxml"))
            primaryStage.setResizable(false);

        return  fxmlLoader;
    }


    /**
     * pass to the controller map which map is choosen
     * @param choosMap
     */
    public void selectedMap(int choosMap){
        Controller.updateMap(choosMap);
    }

    /**
     * open map window
     */
    @Override
    public void startGame() {


        Platform.runLater(new Runnable() {
            @Override
            public void run(){

        try {
            selectedMap(mapView.getMapCopy().getID());
            fxmlLoader = open("Map1.fxml", "ADRENALINE", 1366, 768);

            closeWaitingRoom(controller);
            controller = fxmlLoader.getController();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            }});

        }

    @Override
    public void disconnect(int matchID) {
        //TODO
    }

    @Override
    public void setSkullBoard(SkullBoardView skullBoard) {
        skullBoardView = skullBoard;
        //Controller.setSkullBoard((ClientSkullBoardView) skullBoardView);
    }

    @Override
    public void setMapView(MapView mapViev) {
        mapView = (ClientMapView) mapViev;
    }

    @Override
    public void setEnemyView(EnemyView enemyView) {
        if(enemyView1==null) enemyView1 = (ClientEnemyView) enemyView;
        else if(enemyView2==null) enemyView2 = (ClientEnemyView) enemyView;
        else if(enemyView3==null) enemyView3 = (ClientEnemyView) enemyView;
        else if(enemyView4==null) enemyView4 = (ClientEnemyView) enemyView;
    }

    @Override
    public void printFromController(String message) {
            controller.printf(message);
    }

    @Override
    public void updateCell(Cell cell) {
        controller.updatePlayersPosition(cell);
        controller.updateWeaponMap();
        updateAmmoCardMap();
    }

    @Override
    public void updatePlayer() {
        updateWeaponPlayer();
        updatePlayerPowerup();
        updatePlayerPoints();
        updatePlayerAmmo();
        setPlayerFrenzy();
        updatePlayerDamage();
        updatePlayerSkull();
        updatePlayerMark();
        updatePlayerCharacter();


    }

    /**
     * update Player Character's Images
     */
    public void updatePlayerCharacter(){
        try {
            controller.updatePlayerCharacter(clientView.getPlayerCopy().getCharacter().getId());
        }catch (NullPointerException e){

        }
    }

    /**
     * update Player Powerup's Images
     */
    private void updatePlayerPowerup() {
        controller.updatePlayerPowerup();
    }

    @Override
    public void updateEnemy(ClientEnemyView enemyView) {
        controller.updateEnemyCharacter(enemyView);
        controller.updateEnemyWeapon(enemyView);
        controller.frenzyEnemy(enemyView);
        controller.updateEnemyDamage(enemyView);
        controller.updateEnemyMarks(enemyView);
        controller.updateEnemySkull(enemyView);
    }

    @Override
    public void updateSkullBoard() {
        //TODO
    }

    @Override
    public void printRanking(List<Player> players) {
        //TODO
    }

    @Override
    public void turn(String nickname, boolean yourTurn) {
        //TODO
    }

    /**
     * set possible character to choose
     * @param character
     */
    @Override
    public void chooseCharacter(List<Character> character) {
        characters= character;

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
     * update ammoCard on Map
     */
    public void updateAmmoCardMap(){
        controller.updateAmmoCardMap();
    }


    /**
     * update player damage on gui
     */
    public void updatePlayerDamage(){
        controller.updatePlayerDamage();
    }

    /**
     * set image franzy when the player is in frenzy mode
     */
    public void setPlayerFrenzy(){
        if(clientView.getPlayerCopy().isFrenzyDeath()){
            controller.frenzyPlayer();
        }
    }

    /**
     * update player's marks on gui
     */
    public void updatePlayerMark(){
        controller.updatePlayerMarks();
    }

    /**
     * update player skull on map
     */
    public void updatePlayerSkull(){
        controller.updatePlayerSkull();
    }
    /**
     * update game display
     */


    public void updateMap(){
        updateAmmoCardMap();
        updateWeaponMap();
        updateWeaponPlayer();
        updatePlayerAmmo();
    }

    static void closeWaitingRoom(Controller controller){
        Stage stage = (Stage) controller.getCloseWaiting().getScene().getWindow();
        stage.close();
    }
}