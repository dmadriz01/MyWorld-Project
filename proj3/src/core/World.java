package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class World {
    //just the rooms and hallways
    private static TETile[][] board;
    private static final long SEED = 2873123;
    private static final Random random = new Random(SEED);
    private int rwidth;
    private int rheight;
    private int[][] roomMoves;
    private int size = 5;

    private TETile[][] halls;

    public World(int width, int height) {
        board = new TETile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.WATER;
            }
        }
        rwidth = randomNum(size);
        rheight = randomNum(size);
        worldMoves(width, height);
    }


    private void worldMoves(int width, int height) {    // for duplicating rooms across the board
        for (int x = 0; x < width; x += width/size) {   /* width/size*/
            for (int y = 0; y < height; y += height/size ) {   /*height/size*/
                rectangularRoom(x, y, width, height);
            }
        }
    }

    private int randomNum (int bound) {
        return random.nextInt(bound);
    }

    private void rectangularRoom(int x, int y, int width, int height) {
        int roomWidth = 5;
        int roomHeight = 6;
        roomMoves = new int[][]{
                {0,randomNum(3)}    /*,{2,randomNum(2)},{3,randomNum(4)},{4,randomNum(1)*/
        };

        for (int i = 0; i < roomMoves.length; i++) {
            int x1 = x;
            int y1 = y;

            x += roomMoves[i][0] * roomWidth;
            y += roomMoves[i][1] * roomHeight;

            fill(x, y, roomWidth, roomHeight, width, height);

            x = x1;
            y = y1;


//        int rwid = 5; /*randomNum(width / size);*/
//        int rhei =6; /* randomNum(height / size);*/
//
//        int sizeOfRooms = 5;
//
//        int roomWidth = 3; /*rwid + sizeOfRooms;*/
//        int roomHeight = 4; /*rhei + sizeOfRooms;*/
//
//        roomMoves = new int[][]{{roomWidth, roomHeight}    /*, {rwid, rhei}*/
//        };
//
//        for (int i = 0; i < roomMoves.length; i++) {
//            int x1 = x;
//            int y1 = y;
//
//            x += roomMoves[i][0];
//            y += roomMoves[i][1];
//
//            fill(x, y, width, height);
//
//            x = x1;
//            y = y1;

        }
    }
    private void fill (int x, int y, int roomWidth, int roomHeight, int width, int height){
        for (int i = x; i<= x+roomWidth+1; i++)
                for (int j = y; j<= y+roomHeight+1; j++) {
                    if (i >= 0 && j >= 0 && i < width && j < height) {
                        board[i][j] = Tileset.FLOWER;
                    }
                }
        }





    private void hallways(){

    }

    private void addborders(){

    }


    public TETile[][] getTiles() {
        return board;
    }




    // build your own world!


//    helper method just for rooms
    // helper method just for hallways

    //generate all rooms and hallways connection


}
