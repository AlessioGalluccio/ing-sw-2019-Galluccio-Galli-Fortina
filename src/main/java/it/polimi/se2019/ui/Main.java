package it.polimi.se2019.ui;



import javafx.application.Application;

import javafx.stage.Stage;



public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        ControllerLogin.open("login.fxml", "Adrenaline",300,300);

    }




    public static void main(String[] args) {
        launch(args);
    }
}
