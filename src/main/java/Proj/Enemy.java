package Proj;

import javafx.scene.image.Image;

public class Enemy {
    private int HP;
    private final int speed;
    private final int size;
    private int x;
    private int y;
    private final Image png;
    private int dist = 0;
    private int step = -1;
    private int totalDist;

    public Enemy(int HP, int speed, int size, int x, int y, String pngString) {
        this.HP = HP;
        this.speed = speed;
        this.size = size;
        this.x = x;
        this.y = y;
        this.totalDist = 0;
        this.png = new Image(pngString);
    }

    public Enemy(int HP, int speed, int size, int x, int y, Image png) {
        this.HP = HP;
        this.speed = speed;
        this.size = size;
        this.x = x;
        this.y = y;
        this.totalDist = 0;
        this.png = png;
    }

    public int getTotalDist() {
        return totalDist;
    }

    public void setTotalDist(int totalDist) {
        this.totalDist = totalDist;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public int getSize() {
        return size;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getPng() {
        return png;
    }

}
