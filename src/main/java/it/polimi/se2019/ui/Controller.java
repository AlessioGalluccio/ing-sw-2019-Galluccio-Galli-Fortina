package it.polimi.se2019.ui;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    //ogni label o textfield ecc che vado a creare nel file fxml lo devo riportare come attributo nel controller
    //con lo stesso come dato in fx:id e in onAction metto il metodo

    @FXML
    private Label status;

    @FXML
    private TextField username;

    public void login(ActionEvent event) throws Exception{
        if(username.getText().equals("user")){
            status.setText("Login Success");

            //apriamo così una seconda casella dopo il login
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Map1.fxml"));
            primaryStage.setTitle("Mappa1");
            primaryStage.setScene(new Scene(root, 700, 400));
            primaryStage.show();
        }
        else
            status.setText("Login Fail");
    }
}
