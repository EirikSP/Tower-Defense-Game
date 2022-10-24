package Proj;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TowerTest extends Application{



    String[] args;

    //Kjøres før hver test
    @BeforeEach
	public void setup() {
        try {
            Application.launch(args);
        } catch (Exception e) {
            
        }
	}


    @Test
	@DisplayName("Constructor and Set Test")
	public void constructorSetTest() {
		Tower tower1 = new Tower(new SingleAttack(2, 3, 3), 100, 20, "Proj/samurai.png");
        Tower tower2 = new Tower(new SingleAttack(2, 3, 3), 100, 20, new Image("Proj/samurai.png"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {tower1.setY(-3);}, "Checks if setY/X throws correct exceptions.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {tower1.setY(940);}, "Checks if setY/X throws correct exceptions.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {tower2.setX(-1);}, "Checks if setY/X throws correct exceptions.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {tower2.setX(4221);}, "Checks if setY/X throws correct exceptions.");
	}


	//Trængs for å mekke enemies og sånn weird graphics error.
	@Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("LevelSelect.fxml"))));
        stage.show();
    }

}