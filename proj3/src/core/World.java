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

    private TETile[][] halls;

    public World(int width, int height) {
        rwidth = 5;           /*randomNum(width/5);*/
        rheight = 4;            /*randomNum(height/5);*/
        board = new TETile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.WATER;
            }
        }
        rectangularRoom(rwidth, rheight);
    }



    private int randomNum (int bound) {
        return random.nextInt(bound);
    }

    private void rectangularRoom(int width, int height) {
        TETile[][] rooms = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rooms[x][y] = Tileset.GRASS;
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
