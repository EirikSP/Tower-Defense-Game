package Proj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("StartScreen.fxml"))));
        stage.show();
    }

}
