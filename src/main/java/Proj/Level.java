package Proj;

import javafx.scene.paint.Color;

public class Level {
    private final Enemy[] enemyOptions = {
            new Enemy(60, 2, 70, -50, 285, "Proj/greengoblin.png"), // goblin
            new Enemy(80, 3 , 70, -50, 285, "Proj/skeletor.png"), // skeleton
            new Enemy(200, 1 , 140, -50, 285, "Proj/troll.png"), // skeleton
    };

    private final Enemy[][] waves = { // a list of waves which are lists of enemies
            { newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), },
            { newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), },
            { newEnemy(1), null, newEnemy(1), null, newEnemy(1), null, newEnemy(1), null, newEnemy(1), null, newEnemy(1), null, newEnemy(1), },
            { newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(1), },
            { newEnemy(0), newEnemy(1), newEnemy(0), newEnemy(1), newEnemy(0), newEnemy(1), newEnemy(0), newEnemy(1), newEnemy(0), newEnemy(1), newEnemy(0), newEnemy(1), newEnemy(0), newEnemy(1), },
            { newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), },
            { newEnemy(0), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), },
            { newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(0), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), },
            { newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), },
             { newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), },
            { newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(2), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), newEnemy(1), }
        };

    private final double[] waveSpeeds = { // list of speeds for spawning for each wave
            1,
            2,
            1,
            2,
            1.5,
            1,
            1,
            1.5,
            2,
            2,
            3,
    };

    private final Obstacle[] obstacles = {
            // Invisible rectangles on path
            new Obstacle("rectangle", 0, 235, 295, 90, Color.BLUE, true),
            new Obstacle("rectangle", 212, 50, 83, 270, Color.BLUE, true),
            new Obstacle("rectangle", 212, 50, 297, 90, Color.BLUE, true),
            new Obstacle("rectangle", 426, 50, 83, 420, Color.BLUE, true),
            new Obstacle("rectangle", 187, 387, 322, 90, Color.BLUE, true),
            new Obstacle("rectangle", 187, 387, 83, 213, Color.BLUE, true),
            new Obstacle("rectangle", 187, 503, 640, 100, Color.BLUE, true),
            new Obstacle("rectangle", 744, 303, 83, 300, Color.BLUE, true),
            new Obstacle("rectangle", 531, 303, 296, 90, Color.BLUE, true),
            new Obstacle("rectangle", 531, 162, 83, 208, Color.BLUE, true),
            new Obstacle("rectangle", 531, 162, 321, 90, Color.BLUE, true),
            new Obstacle("rectangle", 769, 0, 83, 230, Color.BLUE, true),
            // Visible ones
            new Obstacle("circleImage", 170, 100, 50, "proj/bush.png"),
            new Obstacle("circleImage", 640, 70, 50, "proj/bush.png"),
            new Obstacle("circleImage", 60, 400, 50, "proj/rock.png"),
            // Screen behind labels
            new Obstacle("rectangle", 0, 0, 120, 100, Color.BEIGE, false),

    };


    protected Enemy newEnemy(int i) throws IllegalArgumentException{ // returns a new enemy of index in enemyOptions
        if (i<0 || i>=this.enemyOptions.length) {
            throw new IllegalArgumentException("Index not valid.");
        }
        return new Enemy(enemyOptions[i].getHP(), enemyOptions[i].getSpeed(), enemyOptions[i].getSize(),
                enemyOptions[i].getX(), enemyOptions[i].getY(), enemyOptions[i].getPng());
    }

    public Enemy[][] getWaves() {
        return waves;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    @Override
    public String toString() {
        return "Level [waves=" + waves + "]";
    }

    public double[] getWaveSpeeds() {
        return waveSpeeds;
    }

    

}
