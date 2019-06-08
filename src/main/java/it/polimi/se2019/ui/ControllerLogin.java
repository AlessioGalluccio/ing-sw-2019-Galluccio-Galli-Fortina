package it.polimi.se2019.ui;

import it.polimi.se2019.network.configureMessage.LoginMessage;
import it.polimi.se2019.view.UiInterface;
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
    public void login(boolean success, boolean isFirst) throws Exception{

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
        primaryStage.setTitle("windowName");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();

    }

    @Override
    public void selectedMap(int choosenMap){

    }
}