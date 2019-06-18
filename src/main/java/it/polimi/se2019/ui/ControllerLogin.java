package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.messages.LoginMessage;
import it.polimi.se2019.network.rmi.RMIClient;
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

import java.util.List;


public class ControllerLogin implements UiInterface {

    private static FXMLLoader fxmlLoader;
    private Controller controller;
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




    public void loginButton(ActionEvent event) throws Exception, InterruptedException {
        if(clientView==null) {
            clientView = new ClientView();
            clientView.setUi(this);

            RMIClient rmi = new RMIClient(clientView, "localhost");
            rmi.connect();

            // SocketClient socket = new SocketClient(9001, "localhost", clientView);
            //socket.connect();
            clientView.setUp(rmi);
            //Controller.setClientView(clientView);
        }
        if (matchID.getText().equals("")) {
            //loginMessage = new LoginMessage());
            clientView.createLoginMessage(username.getText(),-1);

        } else {
            int matchId = Integer.parseInt(matchID.getText());
            //loginMessage = new LoginMessage(username.getText(), matchId);
            clientView.createLoginMessage(username.getText(),matchId);
        }


    }





    @Override
    public void login(boolean success, boolean isFirst) {

        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                if(success) {
                    System.out.println(8);
                    status.setText("LOGIN SUCCESSFUL!");
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();
                    System.out.println(8);
                    if(isFirst) {
                        try {
                            System.out.println(8);
                            fxmlLoader = open("chooseMap.fxml", " CHOOSE MAP", 470, 400);
                            controller = fxmlLoader.getController();
                            System.out.println(8);
                        } catch (Exception e) {
                        }
                    }

                    else{
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
    static FXMLLoader open(String fileName, String windowName, int width, int height) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLogin.class.getClassLoader().getResource(fileName));
        Parent root = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle(windowName);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();

        return  fxmlLoader;
    }




    public void selectedMap(int choosMap){
        Controller.updateMap(choosMap);
    }

    /**
     * open map window
     */
    @Override
    public void startGame() {
        selectedMap(mapView.getMapCopy().getID());
        
       /* if(enemyView1!=null) {
            setEnemyNickname(enemyView1.getNickname());
        }*/
       //TODO ETC...
        Platform.runLater(() -> {
        try {
            fxmlLoader = open("Map1.fxml", "ADRENALINE", 700, 700);
            controller = fxmlLoader.getController();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            });
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

    }

    @Override
    public void updateCell(Cell cell) {

    }

    @Override
    public void updatePlayer() {

    }

    @Override
    public void updateEnemy(ClientEnemyView enemyView) {

    }

    @Override
    public void updateSkullBoard() {

    }

    @Override
    public void printRanking(List<Player> players) {

    }

    @Override
    public void turn(String nickname, boolean yourTurn) {

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

    //TODO mettere tutti i metodi di aggiornamento NON DEVE ESSERE USATO PER L'INIZIO DEL GIOCO!!
    public void updateMap(){

        updateWeaponMap();
        updateWeaponPlayer();
        updatePlayerAmmo();
    }
}