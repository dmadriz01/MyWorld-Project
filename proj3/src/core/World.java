package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;


public class World {
    private static TETile[][] board;
    private static final long SEED = 1;
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

//        roomDimensions.put(roomWidth, roomHeight);
        int centersW = x + roomWidth / 2;
        int centersH = y + roomHeight / 2;
        buildhallways(centersW,centersH, width, height);


//        new int[] {centersW, centersH};


        for (int i = x; i <= x + roomWidth; i++) {
            for (int j = y; j <= y + roomHeight; j++) {
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    bboard[i][j] = true;
                    board[i][j] = Tileset.FLOWER;
                }
            }
        }
        board[centersW][centersH] = Tileset.SAND;




//        for (int j= 0; j< roomMap.size() ; j+=2) {
//            int end = roomMap.size() <= j+2 ? roomMap.size() : j+3;
//            if(roomMap.size() - j == 4) end = end +1;
//            if(j != 9) result.add(mentah.subList(j, end));
//        }



//       if (centersW >= 0 && centersH >= 0 && centersW < width && centersH < height) {
////            roomMap[centersW][centersH];
//        }
////        System.out.print(roomMap);
//        if (x >= 2 && y >=2 && x < width - 2 && y < height - 2) {
//            board[centersW][centersH] = Tileset.SAND;
//        }


//        for(Integer key: roomMap.keySet()){
//            for(Integer value: roomMap.get(key)) {
//                new LinkedList<>();
//            }
//        }
//
//
//        for (int key :roomMap.keySet()) {
//            int val1 = roomMap.get(key);
//
//            }
//        }
//

//    int val2 = roomMap.get();
//            if (roomMap.get(i) != null && roomMap.get(i + 1) != null) {
//                    buildhallways(i, val1, i + 1, val2);

//        for (int x1 = 0; x1 <= roomMap.size(); x1++){
////            roomMap.get();
        }
//            for the x&y corner index


    private void buildhallways(int centerW, int centerH, int width, int height) {
        if (centerW > 0 && centerW+(3*scale) < width) { //debug later  check x   + (width / scale)
            for (int i = centerW; i <= centerW+(3*scale); i++) {
                if (bboard[centerW + (3 * scale)][centerH]) {
                    bboard[i][centerH] = true;
                    board[i][centerH] = Tileset.FLOWER;
                }
            }
        }
        if (centerH  > 0 && centerH+(3*scale) < height) { //debug later  check y   + (height / scale)
            for (int j = centerH; j <= centerH+(3*scale); j++) {
                bboard[centerW][j] = true;
                board[centerW][j] = Tileset.FLOWER;
            }
        }
    }
//

//
//        }
//        }
//        for (int x =)

//        find midpoint
//        connect midpoint to the rooms (to create L shape)
//        helpoer method to build hallway from anchor point
//        make sure anchor point is not in room



//
//    private int midpoint(int center1, int center2){
//        int sum = center1+center2;
//        return (int) Math.round((double) sum /2);
//    }



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
