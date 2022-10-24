package Proj;

import java.util.List;

public class spreadAttack extends Attack{

    public spreadAttack(int damage, int range, double speed) {
        super(damage, range, speed);
    }

    @Override
    public void attackEnemies(List<Enemy> enemies, int x, int y, GameController gameController, Tower tower) {
        boolean attack = false;
        if (tower.getCooldown() <= 0) {
            for (Enemy enemy : enemies) {
                if (Math.sqrt(Math.pow(x-enemy.getX(), 2)+Math.pow(y-enemy.getY(), 2)) < this.getRange()) {
                    this.damageEnemy(enemy);
                    attack = true;
                    tower.setCooldown(60);
                }
            }
            if (attack) {
            gameController.addAnimation(gameController.createCircle(x, y, range));
            }
        } else {
            tower.setCooldown(tower.getCooldown() - speed);;
        }
        
    }
    
}
