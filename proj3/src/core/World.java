package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.io.*;
import java.util.*;


public class World {
    private TETile[][] board;
    private TETile[][] boardcopy;
    private Random random;
    private int scale = 5; //comparable to holesize

    private boolean[][] bboard;
    private boolean[][] bboardCopy;
    private List<Room>  roomList;
    private Map<Room, Set<Room>> roomHash;
    private int avatarX;
    private int avatarY;
    private String allinput;
    private boolean seedInputPhase;
    private int flowers;
    private List<Room> adjRooms;
    private int treasureX;
    private int treasureY;


    public World(int width, int height, long seed) {
        random = new Random(seed);
        board = new TETile[width][height];
        bboard = new boolean[width][height];
        roomList = new ArrayList<>();
        roomHash = new HashMap<>();
        avatarX = randomNum(width);
        avatarY = randomNum(height);
        allinput = new String();
        seedInputPhase = true;
        allinput = "N" + seed + "S";
        flowers = 0;
        bboardCopy = new boolean[width][height];
        treasureX = randomNum(width);
        treasureY = randomNum(height);

        for (Room room : roomList) {
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
        worldMoves(width, height);
        closestRooms(width, height);
        buildSortedHallways(width, height);
        addBorders(width, height);

        for (int i = 0; i < width; i++) {
            System.arraycopy(bboard[i], 0, bboardCopy[i], 0, height);
        }

        while (!bboard[avatarX][avatarY]) {
            avatarX = randomNum(width);
            avatarY = randomNum(height);
        }
        while (!bboard[treasureX][treasureY]) {
            treasureX = randomNum(width);
            treasureY = randomNum(height);
        }

    }



    private void worldMoves(int width, int height) {    // for duplicating rooms across the board
        for (int x = 3; x < width - 3; x += 3 * scale) {   // iterates every other 5x5
            for (int y = 3; y < height - 3; y += 3 * scale) {   /*height/size*/
                int roomx = randomNum(scale);
                int roomy = randomNum(scale);
                buildRectangularRoom(x + roomx, y + roomy, width, height); /*x+roomx, y+roomy*/
            }
        }
    }

    private int randomNum(int bound) {
        return random.nextInt(bound);
    }

    private void buildRectangularRoom(int x, int y, int width, int height) { // helper
        int roomWidth = randomNum((width / scale) - 3 + 1) + 3;
        int roomHeight = randomNum((height / scale) - 3 + 1) + 3;

        int centersW = x + roomWidth / 2;
        int centersH = y + roomHeight / 2;

        Room newRoom = new Room(x, y, roomWidth, roomHeight, centersW, centersH);
        roomList.add(newRoom);

        for (int i = x; i < x + roomWidth; i++) {
            for (int j = y; j < y + roomHeight; j++) {
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    bboard[i][j] = true;
                    board[i][j] = Tileset.FLOWER;
                }
            }
        }
    }

    public void closestRooms(int width, int height) {
        for (Room room : roomList) {
            adjRooms = new ArrayList<>(); // Use a TreeSet with a custom comparator
            while (adjRooms.size() < 1) {
                adjRoom(room, width);
            }
        }
    }

    public void adjRoom(Room room, int width) {
        Double dist = width + .5;
        for (Room r : roomList) {
            if (room == r) {
                continue;
            }
            Double actDis = dist(room, r); //distance between room and r
            if (adjRooms.contains(r)) {
                break;
            }
            if (actDis < dist) {
                adjRooms.add(r);
            }
        }
    }

    public Double dist(Room room, Room r) {
        int x = Math.abs(room.getCenterX() - r.getCenterX());
        int y = Math.abs(room.getCenterY() - r.getCenterY());
        return (double) (x + y);
    }


    private void buildSortedHallways(int width, int height) {
        for (Room room: roomList) {
            int startX = room.getCenterX();
            int startY = room.getCenterY();

            for (Room adjroom: adjRooms) {
                connectCenters(startX, startY, adjroom.getCenterX(), adjroom.getCenterY());
            }
        }
    }

    private void connectCenters(int startX, int startY, int endX, int endY) {
        int currentX = startX;
        int currentY = startY;

        while (currentX != endX || currentY != endY) {
            if (currentX < endX) {
                currentX++;
            } else if (currentX > endX) {
                currentX--;
            } else if (currentY < endY) {
                currentY++;
            } else if (currentY > endY) {
                currentY--;
            }
            bboard[currentX][currentY] = true;
            board[currentX][currentY] = Tileset.FLOWER;
        }
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
        addCorners(width, height);
    }

    private void addCorners(int width, int height) {
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (((i - 1) >= 0) && (j - 1) >= 0 && ((i + 1) <= width) && ((j + 1) <= height)) {
                    if (bboard[i + 1][j + 1] && board[i][j] == Tileset.WATER) { //left bottom
                        board[i][j] = Tileset.TREE;
                    }
                    if (bboard[i + 1][j - 1] && board[i][j] == Tileset.WATER) { //right bottom
                        board[i][j] = Tileset.TREE;
                    }
                    if (bboard[i - 1][j - 1] && board[i][j] == Tileset.WATER) { //right top
                        board[i][j] = Tileset.TREE;
                    }
                    if (bboard[i - 1][j + 1] && board[i][j] == Tileset.WATER) { //left top
                        board[i][j] = Tileset.TREE;
                    }
                }
            }
        }
    }


    public TETile[][] getTiles() {
        boardcopy = new TETile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            boardcopy[i] = Arrays.copyOf(board[i], board[0].length);
        }
        boardcopy[avatarX][avatarY] = Tileset.AVATAR;
        boardcopy[treasureX][treasureY] = Tileset.SAND;
        return boardcopy;
    }


    public void handle(char key) {
        char lower = Character.toLowerCase(key);

        if (seedInputPhase && Character.isLowerCase(lower)) {
            if (lower == 's') {
                seedInputPhase = false;
            }
        }

        if (lower == 'w' && bboard[avatarX][avatarY + 1]) {
            avatarY = avatarY + 1;
        }
        if (lower == 's' && bboard[avatarX][avatarY - 1]) {
            avatarY = avatarY - 1;
        }
        if (lower == 'a' && bboard[avatarX - 1][avatarY]) {
            avatarX = avatarX - 1;
        }
        if (lower == 'd' && bboard[avatarX + 1][avatarY]) {
            avatarX = avatarX + 1;
        }
        if (lower == 'c') {
            if (bboardCopy[avatarX][avatarY]) {
                flowers++;
            }
            board[avatarX][avatarY] = Tileset.GRASS;
            bboardCopy[avatarX][avatarY] = false;
        }


        if (lower == 'w' || lower == 's' || lower == 'a' || lower == 'd' || lower == 'c') {
            allinput += lower;
        }

    }

    public String getAllinput() {
        return allinput;
    }

    public static World handleStringInput(String input) {
        if (Character.toLowerCase(input.charAt(0)) == 'l') {
            try {
                BufferedReader br
                        = new BufferedReader(new FileReader("output.txt"));
                World w = World.handleStringInput(br.readLine());

                String lower = input.toLowerCase();

                String actualGame = lower.substring(1);

                for (int i = 0; i < actualGame.length(); i++) {
                    w.handle(actualGame.charAt(i));
                }
                return w;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            long seed;

            String lower = input.toLowerCase();
            String[] seedString = lower.split("[ns]");

            seed = Long.parseLong(seedString[1]);

            World w = new World(50, 50, seed);

            String actualGame = lower.substring(lower.indexOf('s') + 1);

            for (int i = 0; i < actualGame.length(); i++) {
                w.handle(actualGame.charAt(i));
            }
            return w;

        }
        return null;
    }

    public void save() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", false))) {

            writer.write(allinput);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void goingintoPrimary(){
        if (boardcopy[avatarX][avatarY] == boardcopy[treasureX][treasureY]){

        }
    }*/


   public int getflowers() {
        return flowers;
   }

    public boolean[][] getbboard() {
        return bboard;
    }


}