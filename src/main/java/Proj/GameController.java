package Proj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class GameController  extends JPanel implements Runnable{
    
    @FXML
    Pane towerPane;    

    @FXML
    Pane obstaclePane;

    @FXML
    Pane enemyPane;

    @FXML
    Label HPLabel;

    @FXML
    Label moneyLabel;

    @FXML
    Label waveLabel;

    @FXML
    Button lostButton;

    @FXML
    Button wonButton;

    Thread gameThread;

    Game game;

    // Game variables
    private final int FPS = 60;
    private int frame = 0;

    private Level level = new Level();

    //SaveScript
    private SaveScript saveScript = new SaveScript();



    //List of current "animation" images.
    private List<Shape> animationList = new ArrayList<Shape>();


    //Runs on start of game.
    @FXML
    public void initialize() throws IOException{
        //Prints Background
        printImage(0, 0, 600, new Image("Proj/CutMap.png"), obstaclePane);

        towerPane.setOnMouseClicked(e-> handlePlaceTower());
        
        //Initializes game
        game = new Game();
        
        // Print obstacles
        printAllObstacles();

        //Starts the thread that the game runs on.
        startGameThread();
    }




    Runnable drawFrame = new Runnable() {

        @Override
        public void run() {
            enemyPane.getChildren().clear();

            // Prints dragging image/tower
            
            if (game.isDragging()) {
                game.dragCheckPrint(level.getObstacles(), towerPane);
                printCircle(game.getDragX(), game.getDragY(), game.getDragQueen().getAttack().getRange(), game.getDragCircleColor(), enemyPane, true);
                printCircle(game.getDragX(), game.getDragY(), game.getDragQueen().getSize()/2, Color.GREY, enemyPane, true);
                printImage(game.getDragX() - game.getDragQueen().getSize()/2, game.getDragY() - game.getDragQueen().getSize()/2, game.getDragQueen().getSize(), game.getDragQueen().getPng(), enemyPane); // borrows enemyPane so it is deleted every frame
            }
            // Print enemies
            game.getEnemies().stream().forEach(enemy -> printImage(enemy.getX() - enemy.getSize()/2, enemy.getY()-enemy.getSize()/2, enemy.getSize(), enemy.getPng(), enemyPane));

            animationList.stream().forEach(animation -> enemyPane.getChildren().add(animation));


            updateLabels(); // UI must be updated in an application thread
            
        }
    };


    // Prints towers
    Runnable drawTowers = new Runnable() {
        @Override
        public void run() {
            game.getTowers().stream().forEach(tower -> printImage(tower.getX() - tower.getSize()/2, tower.getY() - tower.getSize()/2, tower.getSize(), tower.getPng(), towerPane));
        }
    };
    

    
    

    private void draw() {
        Platform.runLater(drawFrame);
    }

    public void startGameThread() throws IOException{
        gameThread = new Thread(this);
        gameThread.start();
    }


    //Prints initial state and then creates a loop that runs 60 times per second, according to the FPS. Source: https://www.youtube.com/watch?v=VpH33Uw-_0E
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        Platform.runLater(drawTowers);

        
        
        while (gameThread != null) {
            
            update();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime/1000000);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    

    public void addAnimation(Shape animation) {
        animationList.add(animation);
    }



    private void decayAnimation() {
        List<Shape> animationsToRemove = new ArrayList<Shape>();
        for (Shape animation : animationList) {
            if (animation.getOpacity() <= 0) {
                animationsToRemove.add(animation);
            } else {
                animation.setOpacity(animation.getOpacity() - 0.04);
            }
        }
        animationList.removeAll(animationsToRemove);
    }




    public void update(){
        
        if (game.isWonGame() || game.isLostGame()) {
            return;
        }

        //Spawns in new enemies every second until wave is empty.
        game.spawnEnemies(frame, FPS, level);

        // Check if game is won and restart button appears
        if (game.isWonGame()) {
            wonButton.setLayoutX(375);  // Display won-button
        }
        


        //Moves enemies along the track and removes enemies that reaches the end.
        game.moveEnemies();
        
        //Checks if game is lost.
        if (game.isLostGame()) {
            lostButton.setLayoutX(375.0);
        }
        
        // Add money every second
        game.addMoney(frame, FPS);


        // Towers attacking enemies
        for (Tower tower : game.getTowers()) {
            tower.attackEnemies(game.getEnemies(), this);
            game.removeEnemies();
        }
        

        //Sorts the enemy list so that the towers correctly targets the first enemy on the track.
        game.sortEnemies();

        frame += 1;
        
        //Checks whether or not to start new wave.
        game.newWave(this.level);

        decayAnimation();

        draw();  // Errors dissapeared by putting draw in the bottom
    }
    
    @FXML
    private void updateLabels(){
        HPLabel.setText("HP: " + game.getHP());
        moneyLabel.setText("Money: "  + game.getMoney());
        waveLabel.setText("Wave: "  + (game.getWave()+1));
    }


    private void printImage(int x, int y, int height, Image png, Pane cane){
        ImageView pngView = new ImageView(png);
        pngView.setX(x); 
        pngView.setY(y);
        pngView.setFitHeight(height); 
        pngView.setPreserveRatio(true); 
        pngView.setClip(new Rectangle(1200, 600)); // hides overflow
        cane.getChildren().add(pngView);
    }


    private void printCircle (int x, int y, int radius, Color color, Pane pane, Boolean transparent) {
        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        circle.setClip(new Rectangle(1200, 600)); // hides overflow
        if (transparent) {
            circle.opacityProperty().set(0.5);
        }
        pane.getChildren().add(circle);
    }

    private void printRect (int x, int y, int width, int height, Color color, Pane pane, Boolean invisible) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setClip(new Rectangle(1200, 600)); // hides overflow
        if (invisible) {
            rect.opacityProperty().set(0);
        }
        pane.getChildren().add(rect);
    }


    private void printAllObstacles() {
        for (Obstacle obstacle : level.getObstacles()) {
            if (obstacle.getType() == "circle") {
                printCircle(obstacle.getX(), obstacle.getY(), obstacle.getRadius(), obstacle.getColor(), obstaclePane, false);
            } else if (obstacle.getType() == "rectangle") {
                Boolean invisible = false;
                if (obstacle.isInvisible()) {invisible = true;}
                printRect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight(), obstacle.getColor(), obstaclePane, invisible);
            } else if (obstacle.getType() == "circleImage") {
                printImage(obstacle.getX() - obstacle.getRadius(), obstacle.getY() - obstacle.getRadius(), obstacle.getRadius()*2, obstacle.getPng(), obstaclePane);
            } else if (obstacle.getType() == "rectangleImage") {
                printImage(obstacle.getX(), obstacle.getY(), obstacle.getHeight(), obstacle.getPng(), obstaclePane);
            }
        }
    }


    public Shape createLine(int x1, int y1, int x2, int y2, int width, Color color) {
        Line line =  new Line(x1, y1, x2, y2);
        line.setStrokeWidth(width);
        line.setStroke(color);
        return line;
    }

    public Shape createCircle(int x, int y, int radius) {
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.RED);
        circle.setOpacity(0.7);
        return circle;
    }




    @FXML
    private void handleTowerN(ActionEvent event, int n) throws IOException, IllegalStateException{
        // Remove dragging tower if already dragging, so you don't have to place a tower
        if (game.isDragging() && game.getDragQueen().getPng().equals(game.getTowerOptions()[n].getPng())) { // Also checks if DragQueen() is the same tower as button clicked by checking the image
            game.setDragging(false);
            return;
        }
        
        game.setDragQueen(new Tower(game.getTowerOptions()[n].getAttack(), game.getTowerOptions()[n].getSize(), game.getTowerOptions()[n].getCost(), game.getTowerOptions()[n].getPng()));
        
        if (game.getMoney() < game.getDragQueen().getCost()) {
            game.setDragging(false);
            return;
        }
        game.setDragging(true);
    }
    

    @FXML
    private void handleTower1(ActionEvent event) throws IOException{
        handleTowerN(event, 0);
    }


    @FXML
    private void handleTower2(ActionEvent event) throws IOException{
        handleTowerN(event, 1);
    }

    @FXML
    private void handleTower3(ActionEvent event) throws IOException{
        handleTowerN(event, 2);
    }



    @FXML
    private void handlePlaceTower() {
        if (!game.isDragging()) {return;}
        if (!game.isPlacable()) {return;}

        game.setDragging(false);
        game.addTower(game.getDragX(), game.getDragY(), game.getDragQueen());

        towerPane.getChildren().clear();
        Platform.runLater(drawTowers);

        game.setMoney(game.getMoney()-game.getDragQueen().getCost());

    }

    @FXML
    private void saveGame(ActionEvent event) throws IOException{
        saveScript.save("onlySave", this.game);

        lostButton.setLayoutX(10000); // Place lost- and won-button off-screen
        wonButton.setLayoutX(10000);
    }

    @FXML
    private void loadGame(ActionEvent event) throws IOException{
        game = saveScript.load("onlySave");
        towerPane.getChildren().clear();
        Platform.runLater(drawTowers);

        lostButton.setLayoutX(10000); // Place lost- and won-button off-screen
        wonButton.setLayoutX(10000);
    }

    @FXML
    private void handleRestart() throws IOException {
        towerPane.getChildren().clear();
        
        game = new Game();

        lostButton.setLayoutX(10000); // Place lost- and won-button off-screen
        wonButton.setLayoutX(10000);
    }




    public int getFrame() {
        return frame;
    }

    public Level getLevel() {
        return level;
    }
    
}