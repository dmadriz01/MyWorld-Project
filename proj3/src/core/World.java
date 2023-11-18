package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;


public class World {
    private static TETile[][] board;
    private static final long SEED = 11000;
    private static final Random random = new Random(SEED);
    private int scale = 5; //comparable to holesize

    private TETile[][] halls;
    private static boolean[][] bboard;

    private ArrayList roomMap;

//    private HashMap<Integer, Integer> roomDimensions;




    public World(int width, int height) {
        board = new TETile[width][height];
        bboard = new boolean[width][height];
        roomMap = new ArrayList<>();
//                new int[width][height];  //make 50 the number of rooms
//        roomDimensions = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.WATER;
            }
        }
        worldMoves(width, height, scale);
        addBorders(width, height);

        //starts at 5
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
        if (centerW > 0 && centerW + (3 * scale) < width) { //debug later  check x   + (width / scale)
            int endx = centerW + (3 * scale);
            for (int i = centerW; i <= endx; i++) {   //i <= centerW + (3 * scale)
                    bboard[i][centerH] = true;
                    board[i][centerH] = Tileset.FLOWER;
                    if (!bboard[endx][centerH]){
                        addConnector(endx, centerH, width, height);
                    }
            }
//            addConnector(endx, centerH, width, height);
        }
        if (centerH > 0 && centerH + (3 * scale) < height) { //debug later  check y   + (height / scale)
            int endy = centerH + (3 * scale);
            for (int j = centerH; j <= endy; j++) {   //j <= centerH + (3 * scale)
                    bboard[centerW][j] = true;
                    board[centerW][j] = Tileset.FLOWER;
                    if (!bboard[centerW][endy]){
                        addConnector(centerW,endy, width, height);
                    }
            }
//            addConnector(centerW,endy, width, height);
        }
    }

    private void addConnector(int lastx, int lasty, int width, int height) {
        if (lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 && bboard[lastx][lasty-1]) {  //bottom

            if (lastx <= height / 2) {
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
         else if (lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 && bboard[lastx][lasty+1]){  //top

             if (lastx < height / 2) {
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
        else if (lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 && bboard[lastx+1][lasty]) {  //right

            if (lasty < width/2) {
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
        else if (lastx >= 0 && lasty >= 0 && lastx < width-1 && lasty < height-1 && bboard[lastx-1][lasty]) {   //left

            if (lasty < width/2) {
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
        }


    public TETile[][] getTiles() {
        return board;
    }




    // build your own world!


//    helper method just for rooms
    // helper method just for hallways

    //generate all rooms and hallways connection


}
