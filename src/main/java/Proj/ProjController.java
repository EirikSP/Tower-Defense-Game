package Proj;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ProjController{

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private void runGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException{
        this.runGame(event);
    }



    @FXML
    private void handleButtonClick1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LevelSelect.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }



}