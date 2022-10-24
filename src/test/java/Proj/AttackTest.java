package Proj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AttackTest extends Application{
    
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
	@DisplayName("Test Single Attack")
	public void testSingleAttack() {
		game.addTower(1, 250, 0); // Knight
        game.getTowers().get(0).attackEnemies(game.getEnemies(), gameController);
        assertEquals(30, game.getEnemies().get(0).getHP());
        assertEquals(60, game.getEnemies().get(1).getHP());
	}

    @Test
	@DisplayName("Test Spread Attack")
	public void testSpreadAttack() {
		game.addTower(1, 250, 1); // Samurai
        game.getEnemies().get(2).setX(500); // Place far away
        game.getTowers().get(0).attackEnemies(game.getEnemies(), gameController);
        assertEquals(50, game.getEnemies().get(0).getHP());
        assertEquals(50, game.getEnemies().get(1).getHP()); // both should be damaged 

        assertEquals(60, game.getEnemies().get(2).getHP()); // Should not be damaged
	}

    @Test
	@DisplayName("Test Sniper Attack")
	public void testSniperAttack() {
		game.addTower(800, 500, 2); // Sniper far away
        game.getTowers().get(0).attackEnemies(game.getEnemies(), gameController);
        assertEquals(-10, game.getEnemies().get(0).getHP());
        assertEquals(60, game.getEnemies().get(1).getHP()); // should not be damaged 
	}


    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("LevelSelect.fxml"))));
        stage.show();
    }
}
