package Proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SaveScript implements ISaveScript{

    public void save(String filename, Game game) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(getFilePath(filename)))) {
			writer.println(game.getWave());
			writer.println(game.getWaveStartHP());
			writer.println(game.getWaveStartMoney());
            writer.println(game.getTowersAtWaveStart().size());
			for (Tower tower : game.getTowersAtWaveStart()) {
                int index = 0;
                for (int i = 0; i < game.getTowerOptions().length; i++) {
                    if (tower.getPng().equals(game.getTowerOptions()[i].getPng())) {
                        index = i;
                    }
                }
                writer.println(index + " " + tower.getX() + " " + tower.getY());
            }
		}
    }



    public Game load(String filename) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
			int wave = scanner.nextInt();
			int HP = scanner.nextInt();
            int money = scanner.nextInt();
            int numberTowers = scanner.nextInt();
			Game game = new Game();
            game.setHP(HP);
            game.setMoney(money);
            game.setWave(wave);

            game.setWaveStartHP(HP);
            game.setWaveStartMoney(money);



			for (int i = 0; i < numberTowers; i++) {
                int index = scanner.nextInt();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                game.addTower(x, y, index);
            }
            
            for (Tower tower : game.getTowers()) {
                game.getTowersAtWaveStart().add(tower);
            }

			return game;
		}
	}


    public static String getFilePath(String filename) {
        try {
            return SaveScript.class.getResource("saves/").getFile() + filename + ".txt";
        } catch (Exception e) {
            File file = new File(SaveScript.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Proj/" + "saves/");
            try {
                file.mkdirs();
                return SaveScript.class.getResource("saves/").getFile() + filename + ".txt";
            } catch (Exception e2) {
                throw e2;
            }
        }
		
	}
    
}
