package Proj;

import java.util.List;

public abstract class Attack {

    protected final int damage;
    protected final int range;
    protected final double speed;  // Attacks per second.


    public Attack(int damage, int range, double speed) {
        this.damage = damage;
        this.range = range;
        this.speed = speed;
    }

    protected void damageEnemy(Enemy enemy) {
        enemy.setHP(enemy.getHP()- this.damage);
    }

    abstract public void attackEnemies(List<Enemy> enemies, int x, int y, GameController gameController, Tower tower);

    protected int getDamage() {
        return damage;
    }

    protected int getRange() {
        return range;
    }

    protected double getSpeed() {
        return speed;
    }
    

    
    
}
