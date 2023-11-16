package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;



public class World {
    //just the rooms and hallways
    private static TETile[][] board;
    private static final long SEED = 2873123;
    private static final Random random = new Random(SEED);
    private int width;
    private int height;
    private int[][] roomMoves;
    private int size = 5;
    private int scale = 5; //comparable to holesize

    private TETile[][] halls;
    private static boolean[][] bboard;

//    protected class Coordinate{
//        int x;
//        int y;
//        public Coordinate(int x, int y){
//            this.x = x;
//            this.y = y;
//        }
//    }

    private Collection<Integer>[] xbuckets;

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
//        rwidth = randomNum(size);
//        rheight = randomNum(size);
        worldMoves(width, height, scale);
        addBorders(width, height);
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                System.out.print(bboard[i][j] + " ");
//            }
//            System.out.println(); // Move to the next line for the next row
//        }
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

//        board[x][y] = Tileset.FLOWER;

//        roomMap.put(roomWidth,roomHeight);

        for (int i = x; i <= x+roomWidth; i++){
            for(int j=y; j <= y+roomHeight; j++){
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    bboard[i][j] = true;
                    board[i][j] = Tileset.FLOWER;
                }
            }
        }
    }

    private void buildhallways(){

//        find midpoint
//        connect midpoint to the rooms (to create L shape)
//        helpoer method to build hallway from anchor point
//        make sure anchor point is not in room
        


    }

    private void fillroom (int width, int height){


    }

    private void addBorders(int width, int height) {
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                if (bboard[i][j]) {
                    if (!bboard[i - 1][j] && i - 1 > 0) {  //left
                        board[i - 1][j] = Tileset.TREE;
                    }
                    if (!bboard[i + 1][j] && i + 1 < width) { //right
                        board[i + 1][j] = Tileset.TREE;
                    }
                    if (!bboard[i][j - 1] && j - 1 > 0) { //down
                        board[i][j - 1] = Tileset.TREE;
                    }
                    if (!bboard[i][j + 1] && j + 1 < height) { //up
                        board[i][j + 1] = Tileset.TREE;
                    }
                    }
                }
            }
        }






//        roomMoves = new int[][]{
//                {randomNum(width/size),randomNum(height/size)}    /*,{2,randomNum(2)},{3,randomNum(4)},{4,randomNum(1)*/
//        };
//        for (int i=0; i< roomMoves.length;i++){
//
//        }
//
//        for (int i = 0; i < roomMoves.length; i++) {
//            int x1 = x;
//            int y1 = y;
//
//            x += roomMoves[i][0] * roomWidth;
//            y += roomMoves[i][1] * roomHeight;
//
//
//            fill(x, y, roomWidth, roomHeight, width, height);
//            addborders(x,y, roomWidth, roomHeight);
//
//            x = x1;
//            y = y1;


//        int roomWidth = 1;
//        int roomHeight = 1;
//        roomMoves = new int[][]{
//                {randomNum(2),randomNum(3)}    /*,{2,randomNum(2)},{3,randomNum(4)},{4,randomNum(1)*/
//        };
//
//
//        for (int i = 0; i < roomMoves.length; i++) {
//            int x1 = x;
//            int y1 = y;
//
//            x += roomMoves[i][0] * roomWidth;
//            y += roomMoves[i][1] * roomHeight;
//
//
//            fill(x, y, roomWidth, roomHeight, width, height);
//            addborders(x,y, roomWidth, roomHeight);
//
//            x = x1;
//            y = y1;


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

//        }
//    }
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

    private void addborders(int x, int y, int width, int height){
        for (int i = x; i<=width; i++){
//            for (int j=y; j<=height; j++){
            board[i][y] = Tileset.FLOOR;

        }
//        board[x][y] = Tileset.FLOOR;
    }


    public TETile[][] getTiles() {
        return board;
    }




    // build your own world!


//    helper method just for rooms
    // helper method just for hallways

    //generate all rooms and hallways connection


}
