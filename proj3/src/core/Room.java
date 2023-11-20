package core;

public class Room {
    private int x;        // bottom left corner x
    private int y;        // bottom left cornet y
    private int width;    // width of the room
    private int height;   // height of the room
    private int centerX; //center x
    private int centerY; //center y

    // Constructor
    public Room(int x, int y, int width, int height, int centerX, int centerY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    // Getters and setters for the attributes
    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY(){
        return centerY;
    }
}
