package Proj;

import java.util.List;

import javafx.scene.image.Image;

public class Tower {
    private int x;
    private int y;
    private final Attack attack;
    private final int size;
    private final int cost;
    private final Image png;
    private double cooldown = 0;

    public Tower(final Attack attack, final int size, final int cost, final String pngString) {
        this.attack = attack;
        this.size = size;
        this.cost = cost;
        this.png = new Image(pngString);
    }

    public Tower(final Attack attack, final int size, final int cost, final Image png) {
        this.attack = attack;
        this.size = size;
        this.cost = cost;
        this.png = png;
    }

    public void attackEnemies(final List<Enemy> enemies, final GameController gameController) {
        attack.attackEnemies(enemies, this.x, this.y, gameController, this);
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(final double cooldown) {
        this.cooldown = cooldown;
    }

    public Image getPng() {
        return png;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) throws IllegalArgumentException {
        if (x < 0 || x > 900) {
            throw new IllegalArgumentException("X must be bigger than 0 or smaller than 900 to be on screen.");
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) throws IllegalArgumentException {
        if (y < 0 || y > 900) {
            throw new IllegalArgumentException("Y must be bigger than 0 or smaller than 900 to be on screen.");
        }
        this.y = y;
    }

    public Attack getAttack() {
        return attack;
    }

    public int getSize() {
        return size;
    }

    public int getCost() {
        return cost;
    }

}
