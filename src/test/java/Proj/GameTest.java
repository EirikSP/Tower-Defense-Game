package Proj;


import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameTest extends Application {

    String[] args;
    Game game;
    Level level;


    




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
        }
	}


    @Test
	@DisplayName("Tests spawnEnemies() and newWave()")
	public void spawnTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {game.spawnEnemies(1, 0, level);}, "Checks if FPS=0 throws correct exception.");
		
        Assertions.assertEquals(4, game.getEnemies().size(), "Checks if correct number of enemies have been spawned.");
        game.setEnemies(new ArrayList<Enemy>());
        game.newWave(level);
        Assertions.assertFalse(game.getWaveCheck(), "Checks if game has regognized the wave has passed.");
        Assertions.assertEquals(1, game.getWave(), "Checks if wave is now 1 instead of 0.");
	}


    @Test
	@DisplayName("removeEnemiesTest")
	public void removeEnemiesTest() {
        Assertions.assertEquals(4, game.getEnemies().size(), "Checks if correct number of enemies have been spawned.");
        game.getEnemies().get(0).setHP(0);
        game.removeEnemies();
        Assertions.assertEquals(3, game.getEnemies().size(), "Checks if correct number of enemies have been removed.");
        game.getEnemies().get(2).setHP(0);
        game.removeEnemies();
        Assertions.assertEquals(2, game.getEnemies().size(), "Checks if correct number of enemies have been removed.");
        game.getEnemies().get(1).setHP(0);
        game.removeEnemies();
        Assertions.assertEquals(1, game.getEnemies().size(), "Checks if correct number of enemies have been removed.");
	}


    @Test
	@DisplayName("moveEnemiesTest")
	public void moveEnemiesTest() {
        game.getEnemies().get(0).setStep(11);
        game.getEnemies().get(2).setStep(11);
        game.moveEnemies();
        Assertions.assertEquals(2, game.getEnemies().size());
        game.getEnemies().get(1).setDist(0);
        game.getEnemies().get(1).setStep(8);
        game.moveEnemies();
        Assertions.assertEquals(2, game.getEnemies().size());
        game.getEnemies().get(0).setDist(0);
        game.getEnemies().get(0).setStep(11);
        game.moveEnemies();
        Assertions.assertEquals(1, game.getEnemies().size());
	}


    @Test
	@DisplayName("addTowerTest")
	public void addTowerTest() {
        game.addTower(10, 15, 1);
        game.addTower(50, 30, 2);
        game.addTower(80, 100, 0);
        Assertions.assertEquals(3, game.getTowers().size());
        Assertions.assertEquals(50, game.getTowers().get(1).getX());
        Assertions.assertEquals(100, game.getTowers().get(2).getY());
        Assertions.assertEquals(10, game.getTowers().get(0).getX());
        game.addTower(300, 500, game.getTowers().get(1));
        Assertions.assertEquals(4, game.getTowers().size());
        Assertions.assertEquals(500, game.getTowers().get(3).getY());
	}



    @Test
	@DisplayName("sortEnemiesTest")
	public void sortEnemiesTest() {
        Enemy secondEnemy = game.getEnemies().get(1);
        game.getEnemies().get(1).setTotalDist(500);
        game.sortEnemies();
        Assertions.assertEquals(secondEnemy.getSpeed(), game.getEnemies().get(0).getSpeed());
        game.getEnemies().get(2).setTotalDist(600);
        game.getEnemies().get(3).setTotalDist(700);
        game.sortEnemies();
        Assertions.assertEquals(secondEnemy.getSpeed(), game.getEnemies().get(2).getSpeed());
	}

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("LevelSelect.fxml"))));
        stage.show();
    }

}