package Proj;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.awt.MouseInfo;

public class Game{
    
    private int money = 100;
    private int HP = 10;
    private int wave = 0; // Wave starts on 0 in the code
    private int enemyCount = 0;
    private boolean waveCheck = true;
    private List<Tower> towers = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private int waveStartMoney;
    private int waveStartHP;
    private List<Tower> towersAtWaveStart = new ArrayList<>();
    private boolean wonGame;
    private boolean lostGame;

    // Drag n drop variables
    private boolean dragging = false;
    private boolean placable;
    private Tower dragQueen;
    private int dragX;
    private int dragY;
    private Color dragCircleColor;

    

   

    private Tower[] towerOptions = {
                                    new Tower(new SingleAttack(30, 140, 1), 100, 100,  "Proj/gigachad.png"),
                                    new Tower(new spreadAttack(10, 100, 1), 110, 150,  "Proj/samurai.png"),
                                    new Tower(new SniperAttack(70, 1200, 0.5), 110, 250,  "Proj/archer.png"),
                                };


    private int[] path = {300, -180, 215, 335, -240, 115, 555, -205, -200, -140, 235, -310};

    public Game() {
        this.waveStartMoney = money;
        this.waveStartHP = HP;
    }

    public void addTower(int x, int y, int index){
            Tower newTower = new Tower(towerOptions[index].getAttack(), towerOptions[index].getSize(), towerOptions[index].getCost(), towerOptions[index].getPng());
            newTower.setX(x);
            newTower.setY(y);
            towers.add(newTower);
    }

    public void addTower(int x, int y, Tower tower){
        Tower newTower = new Tower(tower.getAttack(), tower.getSize(), tower.getCost(), tower.getPng());
        newTower.setX(x);
        newTower.setY(y);
        towers.add(newTower);
    }

    public void addEnemy(Enemy enemy){
        enemies.add(new Enemy(enemy.getHP(), enemy.getSpeed(), enemy.getSize(), enemy.getX(), enemy.getY(), enemy.getPng()));
    }

    
    public void newWave(Level level) {
        if (this.getEnemies().size() == 0 && this.waveCheck && this.enemyCount == level.getWaves()[this.wave].length) {
            this.enemyCount = 0;
            this.wave += 1;
            this.waveStartMoney = this.money;
            this.towersAtWaveStart = this.towers;
            this.waveStartHP = this.HP;
            this.waveCheck = false;
        }
    }



    public void spawnEnemies(int frame, int FPS, Level level) throws IllegalArgumentException{
        if (FPS <= 0) {
            throw new IllegalArgumentException("FPS must have a positive value.");
        }
        try {
            if ((frame*level.getWaveSpeeds()[wave])%(FPS) == 0 && this.enemyCount < level.getWaves()[this.wave].length){ // adds enemy to enemies every 2 seconds
                if (!(level.getWaves()[this.wave][enemyCount] == null)) { 
                    addEnemy(level.getWaves()[this.wave][enemyCount]);
                }
                this.enemyCount += 1;
                this.waveCheck = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            this.wonGame = true;
        }
        
    }



    public void moveEnemies() {

        List<Enemy> enemiesToRemove = new ArrayList<>();

        for (Enemy enemy : this.enemies) {  // loops through enemies on the board
        
            if (Math.abs(enemy.getDist()) - enemy.getSpeed() <= 0) { // changes enemy's step to the next one

                enemy.setStep(enemy.getStep() + 1);
                if (enemy.getStep() >= path.length) {
                    enemiesToRemove.add(enemy);
                    setHP(this.HP-1);
                    if (this.HP < 1) {
                        lostGame = true;
                    }
                    continue;
                }
                enemy.setDist(path[enemy.getStep()]);
                
            }
            
            // moves enemy onwards in the path 
            if (Math.abs(enemy.getDist()) - enemy.getSpeed() > 0) {
                if (enemy.getStep()%2==0) { // if even: move on x-axis
                    enemy.setX(enemy.getX() + enemy.getSpeed()*enemy.getDist()/Math.abs(enemy.getDist()));
                } else {  // if odd: move on x-axis
                    enemy.setY(enemy.getY() + enemy.getSpeed()*enemy.getDist()/Math.abs(enemy.getDist()));
                }
                enemy.setDist(enemy.getDist() - enemy.getSpeed()*enemy.getDist()/Math.abs(enemy.getDist()));  // updates distance after moving on path
                enemy.setTotalDist(enemy.getTotalDist() + enemy.getSpeed());
            }
        
        }

        this.enemies.removeAll(enemiesToRemove);
    }




    public void sortEnemies() {
        List<Enemy> temp = this.getEnemies();
        temp.sort((a, b) -> b.getTotalDist() - a.getTotalDist());
        this.setEnemies(temp);
    }





    public void removeEnemies() {
        List<Enemy> removerList = new ArrayList<Enemy>();
        for (Enemy enemy : enemies) {
            if (enemy.getHP() <= 0) {
                removerList.add(enemy);
            }
        }
        enemies.removeAll(removerList);
    }

    public void addMoney(int frame, int FPS){
        if (FPS <= 0) {
            throw new IllegalArgumentException("FPS must have a positive value.");
        }
        if (frame%(FPS) == 0) {
            money += 5;
        }
    }

    public void dragCheckPrint(Obstacle[] obstacles, Pane towerPane) {
        // Checks whether tower placement is allowed and signalizes accordingly.
        java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
        Scene scene = towerPane.getScene();
        Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
        Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());

        dragX = (int) Math.round(p.getX() - windowCoord.getX() - sceneCoord.getX());
        dragY = (int) Math.round(p.getY() - windowCoord.getY() - sceneCoord.getY());

        // DragQueen hitboxing

        dragCircleColor = Color.GREY;
        placable = true;
        // Check if dragQueen is outside the pane, only necessary to make circle red because only clicking on pane activated placeTower
        if (dragX < 0 || dragX > 900 || dragY < 0 || dragY > 600) {
            dragCircleColor= Color.RED;
        }
        // Check if dragQueen is is on top of another tower
        for (Tower tower : towers) {
            if (Math.sqrt(Math.pow(tower.getX()-dragX, 2)+Math.pow(tower.getY()-dragY, 2)) < tower.getSize()/4 + dragQueen.getSize()/4) {
                dragCircleColor = Color.RED;
                placable = false;
                break;
            }
        }

        // TODO Check if dragQueen is on top of obstacle
        // Obstacle hitbox
        for (Obstacle obstacle: obstacles) {
            if (obstacle.getType() == "circle" || obstacle.getType() == "circleImage") {
                if (Math.sqrt(Math.pow(obstacle.getX()-dragX, 2)+Math.pow(obstacle.getY()-dragY, 2)) < obstacle.getRadius() + dragQueen.getSize()/2 - 30) {
                    dragCircleColor = Color.RED;
                    placable = false;
                    break;
                }
            }
            else if (obstacle.getType() == "rectangle" || obstacle.getType() == "rectangleImage") {
                if (dragX > obstacle.getX() && dragX < obstacle.getX()+obstacle.getWidth() && dragY > obstacle.getY() && dragY < obstacle.getY()+obstacle.getHeight()) {
                    dragCircleColor = Color.RED;
                    placable = false;
                    break;
                }
            }
        }
    
    }

    
    

    public boolean isWonGame() {
        return wonGame;
    }

    public boolean isLostGame() {
        return lostGame;
    }


    public int getWaveStartMoney() {
        return waveStartMoney;
    }

    public int getWaveStartHP() {
        return waveStartHP;
    }

    public List<Tower> getTowersAtWaveStart() {
        return towersAtWaveStart;
    }

    public void setTowersAtWaveStart(List<Tower> towerList) {
        this.towersAtWaveStart = towerList;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public boolean getWaveCheck() {
        return this.waveCheck;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money <= 0) {
            throw new IllegalArgumentException("Money must have a positive value or be zero.");
        }
        this.money = money;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Tower[] getTowerOptions() {
        return towerOptions;
    }

    public void setWaveStartMoney(int waveStartMoney) {
        this.waveStartMoney = waveStartMoney;
    }

    public void setWaveStartHP(int waveStartHP) {
        this.waveStartHP = waveStartHP;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isPlacable() {
        return placable;
    }

    public Tower getDragQueen() {
        return dragQueen;
    }

    public void setDragQueen(Tower dragQueen) {
        this.dragQueen = dragQueen;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public Color getDragCircleColor() {
        return dragCircleColor;
    }

    
    
}
