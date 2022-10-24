package Proj;


import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SaveScriptTest extends Application{



    String[] args;
    Game game;
    Level level;
    SaveScript saveScript;



    //Kjøres før hver test
    @BeforeEach
	public void setup() {
        try {
            Application.launch(args);
        } catch (Exception e) {
            
        }
        saveScript = new SaveScript();
        game = new Game();
        game.addTower(10, 20, 0);
        game.addTower(30, 40, 1);
        game.addTower(50, 60, 2);
        game.setTowersAtWaveStart(game.getTowers());
        game.setWaveStartHP(1);
        game.setWave(4);
        game.setWaveStartMoney(140);
	}


    @Test
	@DisplayName("saveTest")
	public void saveTest() throws IOException {
        saveScript.save("testSave", game);
        Path actualFile = null;
        Path testFile = null;
        try {
            actualFile = new File(SaveScript.getFilePath("actualSave")).toPath();
            testFile = new File(SaveScript.getFilePath("testSave")).toPath();
        } catch (Exception e) {
            fail("Couldnt load file.");
        }

        
        
        

        Assertions.assertEquals(-1, Files.mismatch(actualFile, testFile), "Checks if contents of files are the same.");
	}


    @Test
	@DisplayName("saveTest")
	public void loadTest() throws FileNotFoundException {
        Assertions.assertThrows(FileNotFoundException.class, () -> {saveScript.load("wrongFile");}, "Wrong filename leads to FileNotFoundException.");
        Game loadedGame;
        loadedGame = saveScript.load("actualSave");
        Assertions.assertEquals(loadedGame.getWave(), 4);
        Assertions.assertEquals(loadedGame.getMoney(), 140);
        Assertions.assertEquals(loadedGame.getHP(), 1);
        Assertions.assertEquals(loadedGame.getTowers().size(), 3);
        
	}


	//Trængs for å mekke enemies og sånn weird graphics error.
	@Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tower Defence");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("LevelSelect.fxml"))));
        stage.show();
    }

}