package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;


public class World {
    private static TETile[][] board;
    private static final long SEED = 2873123;
    private static final Random random = new Random(SEED);
    private int scale = 5; //comparable to holesize

    private TETile[][] halls;
    private static boolean[][] bboard;

    private HashMap<Integer, Integer> roomMap;




    public World(int width, int height) {
        board = new TETile[width][height];
        bboard = new boolean[width][height];
        roomMap = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.WATER;
            }
        }
        worldMoves(width, height, scale);
        addBorders(width, height);
    }


    private void worldMoves(int width, int height, int scale) {    // for duplicating rooms across the board
        for (int x = 0; x < width; x += 2 * scale) {   // iterates every other 5x5
            for (int y = 0; y < height; y += 2 * scale) {   /*height/size*/
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
        int roomWidth = randomNum((width / scale)-3+1)+3;
        int roomHeight = randomNum((height / scale)-3+1)+3;

        for (int i = x; i <= x+roomWidth; i++){
            for(int j=y; j <= y+roomHeight; j++){
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    bboard[i][j] = true;
                    board[i][j] = Tileset.FLOWER;
                }
            }
        }
        roomMap.put(x,y);
        board[x][y] = Tileset.SAND;
    }

    private void buildhallways(){

//        find midpoint
//        connect midpoint to the rooms (to create L shape)
//        helpoer method to build hallway from anchor point
//        make sure anchor point is not in room
        


    }

    private boolean isConnected(){
        return false;
    }

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
