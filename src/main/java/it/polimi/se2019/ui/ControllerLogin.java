package it.polimi.se2019.ui;

import it.polimi.se2019.model.handler.Identificator;
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
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControllerLogin implements UiInterface {

    private FXMLLoader fxmlLoader;
    @FXML
    public Label wrongIp;
    @FXML
    public Button bShowWinner;
    @FXML
    public Label rank1;
    @FXML
    public Label rank2;
    @FXML
    public Label rank3;
    @FXML
    public Label rank4;
    @FXML
    public Label rank5;
    @FXML
    public ImageView imWinner;
    @FXML
    public Label labelMatchId;


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

    @FXML
    public Button bshowMyMatchID;

    private boolean firstAgain = false;

    static List<Character> characters;

    static List<Player> playersRanking;
    static int skullNumber=0;


    /**
     * send setting when login button is clicked
     * @param event after been clicked
     */
    public void loginButton(ActionEvent event) {

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
     * @param success true if the login has success
     * @param isFirst true if the player is the first
     */
    @Override
    public void login(boolean success, boolean isFirst) {

        Platform.runLater(() ->  {

                if (success && isFirst && firstAgain) {
                    try {
                        fxmlLoader = open("WaitingRoom.fxml", "LEAN BACK AND CHILL", 520, 400);
                        controller = fxmlLoader.getController();
                    } catch (Exception e) {
                        Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");
                    }
                }
                else if (success) {

                    status.setText("LOGIN SUCCESSFUL!");
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();

                    if (isFirst) {
                        try {

                            fxmlLoader = open("chooseMap.fxml", " CHOOSE MAP", 450, 530);
                            controller = fxmlLoader.getController();
                            firstAgain = true;

                        } catch (Exception e) {
                            Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");
                        }
                    } else {
                        try {
                            fxmlLoader = open("WaitingRoom.fxml", "LEAN BACK AND CHILL", 520, 400);
                            controller = fxmlLoader.getController();
                        } catch (Exception e) {
                            Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");
                        }
                    }
                } else {
                    status.setText("LOGIN FAILED");

                }


        });

    }

    /**
     * open file FXML in a window
     * @param fileName string name of the file fxml
     * @param windowName string name of the window to open
     * @param width of the window
     * @param height of the window
     * @throws Exception when opening the fxml file
     */
    public static FXMLLoader open(String fileName, String windowName, int width, int height) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerLogin.class.getClassLoader().getResource(fileName));
        Parent root = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle(windowName);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
        if(fileName.equalsIgnoreCase("WaitingRoom.fxml")
                || fileName.equalsIgnoreCase("chooseMap.fxml") || fileName.equalsIgnoreCase("Map1.fxml")) {
            primaryStage.setOnCloseRequest(event -> clientView.shutdownServer());
        }

        if(!fileName.equalsIgnoreCase("Map1.fxml"))
            primaryStage.setResizable(false);

        return  fxmlLoader;
    }




    /**
     * open map window
     */
    @Override
    public void startGame() {


        Platform.runLater(() ->  {

        try {

            fxmlLoader = open("Map1.fxml", "ADRENALINE", 1366, 768);
            Stage stage = (Stage) controller.getCloseWaiting().getScene().getWindow();
            stage.close();
            controller = fxmlLoader.getController();
        }
        catch (Exception e) {
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");
        }
            });

        }


    /**
     * when the player is disconnect
      * @param matchID show the matchId in gui to riconnection
     */
    @Override
    public void disconnect(int matchID) {
        Platform.runLater(() -> {
            try {
                open("disconnected.fxml", "DISCONNECT", 405, 243);
                FXMLLoader fxmlLoader = new FXMLLoader(ControllerLogin.class.getClassLoader().getResource("disconnected.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("DISCONNECT");
                primaryStage.setScene(new Scene(root, 405, 243));


                primaryStage.show();
            } catch (Exception e) {
                Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");

            }
        });
    }

    /**
     * show to the player their match id
     * @param event after been clicked
     */
    public void showMyMatchID(ActionEvent event){
        labelMatchId.setText("Match Id : " + clientView.getMatchId());
    }

    /**
     * set the skullboard
     * @param skullBoard pass the skullboard to ControllerLogin
     */
    @Override
    public void setSkullBoard(SkullBoardView skullBoard) {
        skullBoardView = skullBoard;
        //Controller.setSkullBoard((ClientSkullBoardView) skullBoardView);
    }

    /**
     * set mapView
     * @param mapViev to initialize mapview in ControllerLogin
     */
    @Override
    public void setMapView(MapView mapViev) {
        mapView = (ClientMapView) mapViev;
    }

    /**
     * set enemyviews
     * @param enemyView to initialize enemyviews in ControllerLogin
     */
    @Override
    public void setEnemyView(EnemyView enemyView) {
        if(enemyView1==null) enemyView1 = (ClientEnemyView) enemyView;
        else if(enemyView2==null) enemyView2 = (ClientEnemyView) enemyView;
        else if(enemyView3==null) enemyView3 = (ClientEnemyView) enemyView;
        else if(enemyView4==null) enemyView4 = (ClientEnemyView) enemyView;
    }

    /**
     * print string from controller
     * @param message string from controller to show on gui
     */
    @Override
    public void printFromController(String message) {
        Platform.runLater(() -> {
            if (message.equals(Identificator.YOU_DEAD)) {
                try {
                    ControllerLogin.open("yourDead.fxml", "YOU DIED", 350, 400);
                } catch (Exception e) {

                }
            }

        });
        controller.printf(message);
    }

    /**
     * update cell
     * @param cell to update
     */
    @Override
    public void updateCell(Cell cell) {
        controller.updatePlayersPosition(cell);
        controller.updateWeaponMap();
        updateAmmoCardMap();
    }

    /**
     * update the player
     */
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
    private void updatePlayerCharacter(){
        try {
            controller.updatePlayerCharacter(clientView.getPlayerCopy().getCharacter().getId());
        }catch (NullPointerException e){
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");

        }
    }

    /**
     * update Player Powerup's Images
     */
    private void updatePlayerPowerup() {
        controller.updatePlayerPowerup();
    }

    /**
     * update enemy
     * @param enemyView for updating gui
     */
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
        if(skullNumber == 0){
            skullNumber = ControllerLogin.skullBoardView.getNumSkullCopy();
        }
        updateSkullMap(skullNumber);
        //updateSetting(mapView.getMapCopy().getID(), skullNumber);
    }

    /**
     * print player's ranking
     * @param players list of player to show ranking
     */
    @Override
    public void printRanking(List<Player> players) throws Exception {
        Platform.runLater(() -> {
            try {
                open("playersRanking.fxml", "RANKING", 600, 600);
                playersRanking = players;
            }
            catch (Exception e){

            }
        });
    }

    /**
     * tell the player whose turn it is
     * @param nickname string of the person name
     * @param yourTurn if true print message that says is the player turn
     */
    @Override
    public void turn(String nickname, boolean yourTurn) {
        if(!yourTurn) {
            controller.printf(" It's " + nickname + " turn");
        }
        else{
            controller.printf("It's your turn!!");
        }
    }

    /**
     * set possible character to choose
     * @param character list of possible character
     */
    @Override
    public void chooseCharacter(List<Character> character) {
        characters= character;

    }

    /**
     * update player's points on gui calling method on ui controller
     */
    private void updatePlayerPoints(){
        Player player = clientView.getPlayerCopy();
        controller.updatePoints(player.getNumPoints());
    }

    /**
     * update number of skulls on gui map
     * @param skullNumber number of skull on map
     */
    private void updateSkullMap(int skullNumber){
        String skullNumberString = String.valueOf(skullNumber);
        controller.updateSkullBoard(skullNumberString);
    }

    /**
     * update initial setting of map number and skulls
     * @param choosenMap int of map choosen
     * @param skull int of skull number
     */
    public void updateSetting(int choosenMap, int skull){
        this.updateSkullMap(skull);


    }

    /**
     * update weapons images on map
     */
    private void updateWeaponMap(){
        controller.updateWeaponMap();
    }

    /**
     * update player' weapons images
     */
    private void updateWeaponPlayer(){
        controller.updateWeaponPlayer();
    }

    /**
     * update player ammo
     */
    private void updatePlayerAmmo(){
        try {
            int red = clientView.getPlayerCopy().getAmmo().getRedAmmo();
            int blue = clientView.getPlayerCopy().getAmmo().getBlueAmmo();
            int yellow = clientView.getPlayerCopy().getAmmo().getYellowAmmo();
            controller.updatePlayerAmmo(red, blue, yellow);
        }
        catch (NullPointerException e){
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.FINE, "do nothing");

        }

    }



    /**
     * update ammoCard on Map
     */
    private void updateAmmoCardMap(){
        controller.updateAmmoCardMap();
    }


    /**
     * update player damage on gui
     */
    private void updatePlayerDamage(){
        controller.updatePlayerDamage();
    }

    /**
     * set image franzy when the player is in frenzy mode
     */
    private void setPlayerFrenzy(){
        if(clientView.getPlayerCopy().isFrenzyDeath()){
           controller.frenzyPlayer();
        }
    }

    /**
     * update player's marks on gui
     */
    private void updatePlayerMark(){
        controller.updatePlayerMarks();
    }

    /**
     * update player skull on map
     */
    private void updatePlayerSkull(){
        controller.updatePlayerSkull();
    }


    /**
     * show how win the game on gui
     * @param event after been clicked
     */
    public void showWinner(ActionEvent event) {
        bShowWinner.setDisable(true);
        bShowWinner.setVisible(false);
        rank3.setDisable(false);
        imWinner.setVisible(true);
        ArrayList<Label> labelRanking = new ArrayList<>();
        labelRanking.add(rank1);
        labelRanking.add(rank2);
        labelRanking.add(rank3);
        labelRanking.add(rank4);
        labelRanking.add(rank5);
        labelRanking.get(0).setText("WINNER : " + playersRanking.get(0).getNickname() + " Points : " + playersRanking.get(0).getNumPoints());
        labelRanking.get(1).setText("SECOND : " + playersRanking.get(1).getNickname() + " Points : " + playersRanking.get(1).getNumPoints());
        for(int i=2; i<playersRanking.size(); i++){
            labelRanking.get(i).setText(" " +playersRanking.get(i).getNickname()+ " Points : " + playersRanking.get(i).getNumPoints());
        }

    }
}