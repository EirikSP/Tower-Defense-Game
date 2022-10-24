package Proj;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelTest extends Application{



    String[] args;
    Level level;

    //Kjøres før hver test
    @BeforeEach
	public void setup() {
        try {
            Application.launch(args);
        } catch (Exception e) {
            
        }
        level = new Level();
	}


    @Test
	@DisplayName("newEnemyTest")
	public void newEnemyTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {level.newEnemy(-1);}, "Checks if correct exception is thrown.");
        Enemy firstEnemy = level.newEnemy(1);
        Assertions.assertEquals(3, firstEnemy.getSpeed());
        Assertions.assertEquals(80, firstEnemy.getHP());
        Enemy secondEnemy = level.newEnemy(2);
        Assertions.assertEquals(1, secondEnemy.getSpeed());
        Assertions.assertEquals(200, secondEnemy.getHP());
        Enemy thirdEnemy = level.newEnemy(0);
        Assertions.assertEquals(2, thirdEnemy.getSpeed());
        Assertions.assertEquals(60, thirdEnemy.getHP());
	}


	//Trængs for å mekke enemies og sånn weird graphics error.
	@Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("LevelSelect.fxml"))));
        stage.show();
    }

}