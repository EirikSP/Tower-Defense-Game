package Proj;

import java.util.List;

import javafx.scene.paint.Color;

public class SingleAttack extends Attack{

    public SingleAttack(int damage, int range, double speed) {
        super(damage, range, speed);
    }

    @Override
    public void attackEnemies(List<Enemy> enemies, int x, int y, GameController gameController, Tower tower) {
        if (tower.getCooldown() <= 0) {
            for (Enemy enemy : enemies) {
                if (Math.sqrt(Math.pow(x-enemy.getX(), 2)+Math.pow(y-enemy.getY(), 2)) < this.getRange()) {
                    this.damageEnemy(enemy);
                    tower.setCooldown(60);
                    gameController.addAnimation(gameController.createLine(x, y, enemy.getX(),enemy.getY(), 10, Color.ORANGE));
                    break;
                }
            }
        } else {
            tower.setCooldown(tower.getCooldown() - speed);;
        }
    }
    
}
