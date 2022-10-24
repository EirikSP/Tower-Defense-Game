package Proj;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ObstacleTest extends Application{
    
    String[] args;
    Game game;
    Level level;
    GameController gameController;

    //Kjøres før hver test
    @BeforeEach
	public void setup() {
        try {
            Application.launch(args);
        } catch (Exception e) {
        }

        game = new Game();
        level = new Level();
        for (int i = 0; i < level.getWaves()[0].length; i++) {
            game.spawnEnemies(1, 1, level);
            game.newWave(level);
            gameController = new GameController();
        }
	}

    @Test
	@DisplayName("Test constructor")
	public void testConstructor() {
		Obstacle CorrectObstacle1 = new Obstacle("circle", 1, 1, 20, Color.RED);
        Obstacle CorrectObstacle2 = new Obstacle("rectangle", 1, 1, 20, 30, Color.RED, false);
        Obstacle CorrectObstacle3 = new Obstacle("circleImage", 1, 1, 20, "Proj/bush.png");
        Obstacle CorrectObstacle4 = new Obstacle("rectangleImage", 1, 1, 20, 60, "Proj/bush.png");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Obstacle incorrectObstacle = new Obstacle("invalidType", 1, 1, 20, 60, "Proj/bush.png");

        }, "invalid type");
	}


    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("LevelSelect.fxml"))));
        stage.show();
    }
}

