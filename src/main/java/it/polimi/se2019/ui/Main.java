package it.polimi.se2019.ui;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Adrenaline");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
