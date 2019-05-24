package it.polimi.se2019.ui;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Controller {
    @FXML
    public Label mylabel;
    @FXML
    public ImageView mappa;
    @FXML
    public Button RedWeapon;
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
            /*
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Map1.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Mappa1");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
            */
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("chooseMap.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Choose Map");
            primaryStage.setScene(new Scene(root, 300, 300));
            primaryStage.show();



        }
        else
            status.setText("Login Fail");
    }

    public void chooseMap(ActionEvent event) throws Exception{
        //mylabel.setText("ciao");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Map1.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Mappa1");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        //mappa.setImage(new Image("mappa.jpg"));


        /*

        if(rbmap2.isSelected()){
            mappa.setImage(new Image("mappa2.jpg"));
        }

        */




    }


}
