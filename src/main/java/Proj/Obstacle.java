package Proj;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Obstacle {
    
    private String type;
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final int radius;
    private Color color;
    private boolean invisible;
    private Image png;

    // Rectangle
    public Obstacle(String type, int x, int y, int width, int height, Color color, Boolean invisible) {
        setType(type);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.invisible = invisible;
        this.radius = 0;
    }
    // Circle
    public Obstacle(String type, int x, int y, int radius, Color color) {
        setType(type);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.height = 0;
        this.width = 0;
    }
    // Circle image
    public Obstacle(String type, int x, int y, int radius, String pngString) {
        setType(type);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.png = new Image(pngString);
        this.height = 0;
        this.width = 0;

    }
    // Rectangle image
    public Obstacle(String type, int x, int y, int width, int height, String pngString) {
        setType(type);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.png = new Image(pngString);
        this.radius = 0;
    }

    public String getType() {
        return type;
    }

    private void setType (String type) {
        if (type != "circle" && type != "rectangle" && type != "circleImage" && type != "rectangleImage") {
            throw new IllegalArgumentException("not valid obstacle type");
        }
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 0 || x>900) {
            throw new IllegalArgumentException("X must be bigger than 0 or smaller than 900 to be on screen.");
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 0 || y>900) {
            throw new IllegalArgumentException("Y must be bigger than 0 or smaller than 900 to be on screen.");
        }
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }
    
    public boolean isInvisible() {
        return invisible;
    }

    public Image getPng() {
        return png;
    }

}
