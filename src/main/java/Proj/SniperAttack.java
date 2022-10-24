package Proj;

import java.util.List;

import javafx.scene.paint.Color;

public class SniperAttack extends Attack{

    public SniperAttack(int damage, int range, double speed) {
        super(damage, range, speed);
    }

    @Override
    public void attackEnemies(List<Enemy> enemies, int x, int y, GameController gameController, Tower tower) {
        if (tower.getCooldown() <= 0) {
            try {
                this.damageEnemy(enemies.get(0));
                tower.setCooldown(60);
                gameController.addAnimation(gameController.createLine(x, y, enemies.get(0).getX(),enemies.get(0).getY(), 10, Color.WHITE));
            } catch (Exception e) { // catches error when sniper wants to attack but there are no enemies currently
                tower.setCooldown(tower.getCooldown() - speed);
            }
        } else {
            tower.setCooldown(tower.getCooldown() - speed);
        }
        
    }
    
}
