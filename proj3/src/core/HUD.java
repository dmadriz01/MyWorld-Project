package core;

import edu.princeton.cs.algs4.StdDraw;

public class HUD {
    private int mouseX;
    private int mouseY;
    private int width;
    private int height;
    private boolean[][] bboard;
    private boolean isGameOver;
    private boolean stopFlickering;
    private String previnfo;

    public HUD(boolean[][] bboard) {
        // Set initial mouse coordinates
        this.mouseX = 0;
        this.mouseY = 0;
        this.width = width;
        this. height = height;
        this.bboard = bboard;
        isGameOver = false;
        previnfo = "";
    }

    public void updateMousePosition() {
        // Get the current mouse coordinates
        this.mouseX = (int) StdDraw.mouseX();
        this.mouseY = (int) StdDraw.mouseY();
    }

    public void displayMouseHUD(String info) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.WHITE);

        double X = 5;
        double Y = height - 2;

//        String info = getMouseHoverObject();
//

        StdDraw.text(X, Y, "Tile: " + info);
        StdDraw.show();

//        if (!previnfo.equals(info)){
//            StdDraw.text(X, Y, "Tile: " + info);
//            StdDraw.show();
//            previnfo = info;
//        }

//        StdDraw.text(X, Y, "Tile: " + info);
//        if (this.info == info){
//
//        }


    }

    public String getMouseHoverObject() {
        if (mouseX >= 0 && mouseY >= 0 && mouseX < width && mouseY < height) {
            if (isValidCoordinate(mouseX, mouseY) && bboard[mouseX][mouseY]
                    || isValidCoordinate(mouseX + 1,mouseY) && bboard[mouseX + 1][mouseY]
                    || isValidCoordinate(mouseX - 1, mouseY) && bboard[mouseX - 1][mouseY]
                    || isValidCoordinate(mouseX, mouseY - 1) &&  bboard[mouseX][mouseY - 1]
                    || isValidCoordinate(mouseX, mouseY + 1) && bboard[mouseX][mouseY + 1]) {
                previnfo = "Field";
                return previnfo;
            } else {
                previnfo = "Water";
                return previnfo;
            }
        }
        return "Null";
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isStopFlickering() {
        return stopFlickering;
    }

    public void changeStopFlickering() {
        if (stopFlickering) {
            stopFlickering = false;
        }
        else {
            stopFlickering = true;
        }
    }

    // Your game loop
//    public void runGame() {
//
//        while (!isGameOver) {
//            updateMousePosition();
//            String hoverInfo = getMouseHoverObject();
//            displayMouseHUD(hoverInfo);
//
//
//        }
//    }
}