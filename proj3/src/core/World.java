package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;


public class World {
    private static TETile[][] board;
    private static final long SEED = 10000;
    private static final Random random = new Random(SEED);
    private int scale = 5; //comparable to holesize

    private TETile[][] halls;
    private static boolean[][] bboard;

    private Set<Room> roomSet;

//    private HashMap<Integer, Integer> roomDimensions;




    public World(int width, int height) {
        board = new TETile[width][height];
        bboard = new boolean[width][height];
        roomSet = new HashSet<>();
        for (Room room : roomSet) {
            System.out.println("Room coordinates: (" + room.getX() + ", " + room.getY() + ")");
            System.out.println("Room dimensions: " + room.getWidth() + " x " + room.getHeight());
            System.out.println("Room center: " + room.getCenterX() + " x " + room.getCenterY());
            System.out.println();
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.WATER;
            }
        }
        worldMoves(width, height, scale);
        addBorders(width, height);
    }


    private void worldMoves(int width, int height, int scale) {    // for duplicating rooms across the board
        for (int x = 3; x < width-3; x += 3 * scale) {   // iterates every other 5x5
            for (int y = 3; y < height-3; y += 3 *scale) {   /*height/size*/
                int roomx = randomNum(scale);
                int roomy = randomNum(scale);
                buildRectangularRoom(x+roomx,y+roomy, width, height); /*x+roomx, y+roomy*/
            }
        }
    }

    private int randomNum (int bound) {
        return random.nextInt(bound);
    }

    private void buildRectangularRoom(int x, int y, int width, int height) {
        int roomWidth = randomNum((width / scale) - 3 + 1) + 3;
        int roomHeight = randomNum((height / scale) - 3 + 1) + 3;

        int centersW = x + roomWidth / 2;
        int centersH = y + roomHeight / 2;

        buildhallways(centersW, centersH, width, height);

        Room newRoom = new Room(x, y, roomWidth, roomHeight, centersW, centersH);
        roomSet.add(newRoom);

        for (int i = x; i <= x + roomWidth; i++) {
            for (int j = y; j <= y + roomHeight; j++) {
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    bboard[i][j] = true;
                    board[i][j] = Tileset.FLOWER;
                }
            }
        }
        board[centersW][centersH] = Tileset.SAND;
    }

    private void buildhallways(int centerW, int centerH, int width, int height) {
        if (centerW > 0 && centerW + (3 * scale) < width-3) { //debug later  check x   + (width / scale)
            int endx = centerW + (3 * scale);
            for (int i = centerW; i <= endx; i++) {   //i <= centerW + (3 * scale)
                    bboard[i][centerH] = true;
                    board[i][centerH] = Tileset.FLOWER;
                    }
            if (!bboard[endx+1][centerH]){
                addConnector(endx+1, centerH, width, height);
            }
        }
        if (centerH > 0 && centerH + (3 * scale) < height-3) { //debug later  check y   + (height / scale)
            int endy = centerH + (3 * scale);

            for (int j = centerH; j <= endy; j++) {   //j <= centerH + (3 * scale)
                    bboard[centerW][j] = true;
                    board[centerW][j] = Tileset.FLOWER;
                    }
            if (!bboard[centerW][endy+1]){
                addConnector(centerW,endy+1, width, height);
            }
        }
    }

    private void addConnector(int lastx, int lasty, int width, int height) {
        if (bboard[lastx][lasty-1]) {  //bottom   lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 &&

            if (lastx <= width / 2) { //&& lasty
                for (int i = lastx; bboard[i][lasty]; i++) {
                    bboard[i][lasty] = true;
                    board[i][lasty] = Tileset.FLOWER;
                }
            } else {
                for (int i = lastx; bboard[i][lasty]; i--) {
                    bboard[i][lasty] = true;
                    board[i][lasty] = Tileset.FLOWER;
                }
            }
        }
         else if (bboard[lastx][lasty+1]){  //top   lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 &&

             if (lastx < width / 2) {
                 for (int i = lastx; bboard[i][lasty]; i++) {
                     bboard[i][lasty] = true;
                     board[i][lasty] = Tileset.FLOWER;
                 }
             } else {
                 for (int i = lastx; bboard[i][lasty]; i--) {
                     bboard[i][lasty] = true;
                     board[i][lasty] = Tileset.FLOWER;
                 }
             }
         }
        else if (bboard[lastx+1][lasty]) {  //right   lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 &&

            if (lasty < height/2) {
                for (int j = lasty; bboard[lastx][j]; j++) {
                    bboard[lastx][j] = true;
                    board[lastx][j] = Tileset.FLOWER;
                }
            } else {
                for (int j = lasty; bboard[lastx][j]; j--) {
                    bboard[lastx][j] = true;
                    board[lastx][j] = Tileset.FLOWER;
                }
            }
        }
        else if (bboard[lastx-1][lasty]) {   //left   lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 &&

            if (lasty < height/2) {
                for (int j = lasty; bboard[lastx][j]; j++) {
                    bboard[lastx][j] = true;
                    board[lastx][j] = Tileset.FLOWER;
                }
            } else {
                for (int j = lasty; bboard[lastx][j]; j--) {
                    bboard[lastx][j] = true;
                    board[lastx][j] = Tileset.FLOWER;
                }
            }
        }
    }

//
//    private boolean isConnected(){
//        return false;
//    }

    private void addBorders(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                    if (bboard[i][j]) {
                        if (i - 1 >= 0 && !bboard[i - 1][j]) {  //left
                            board[i - 1][j] = Tileset.TREE;
                        }
                        if (i + 1 < width && !bboard[i + 1][j]) { //right
                            board[i + 1][j] = Tileset.TREE;
                        }
                        if (j - 1 >= 0 && !bboard[i][j - 1]) { //down
                            board[i][j - 1] = Tileset.TREE;
                        }
                        if (j + 1 < height && !bboard[i][j + 1]) { //up
                            board[i][j + 1] = Tileset.TREE;
                        }
                    }
                }
            }
        addCorners(width, height);
        }

    private void addCorners(int width, int height) {
        for (int i = 0; i < width-1; i++) {
            for (int j = 0; j < height-1; j++) {
                if (((i-1) >= 0) && (j-1)>=0 && ((i+1) <= width) && ((j+1) <= height)) {
                    if (bboard[i+1][j+1] && board[i][j] == Tileset.WATER) { //left bottom
                        board[i][j] = Tileset.TREE;
                    }
                    if (bboard[i+1][j-1] && board[i][j] == Tileset.WATER) { //right bottom
                        board[i][j] = Tileset.TREE;
                    }
                    if (bboard[i-1][j-1] && board[i][j] == Tileset.WATER) { //right top
                        board[i][j] = Tileset.TREE;
                    }
                    if (bboard[i-1][j+1]  && board[i][j] == Tileset.WATER) {//left top
                        board[i][j] = Tileset.TREE;
                    }
                }
            }
        }
    }


    public TETile[][] getTiles() {
        return board;
    }




    // build your own world!


//    helper method just for rooms
    // helper method just for hallways

    //generate all rooms and hallways connection


}
