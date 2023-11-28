package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class World {
    private TETile[][] board;

    private Random random;
    private int scale = 5; //comparable to holesize

    private boolean[][] bboard;
    private boolean[][] bboardCopy;
    private Set<Room> roomSet;
    private Set<Room> adjRooms;
    private Map<Room, Set<Room>> roomHash;
    private int avatarX;
    private int avatarY;
    private String allinput;
    private boolean seedInputPhase;
    private int flowers;


    public World(int width, int height, long SEED) throws IOException {
        random = new Random(SEED);
        board = new TETile[width][height];
        bboard = new boolean[width][height];
        roomSet = new HashSet<>();
        roomHash = new HashMap<>();
        avatarX = randomNum(width);
        avatarY = randomNum(height);
        allinput = new String();
        seedInputPhase = true;
        allinput = "N" + SEED + "S";
        flowers = 0;
        bboardCopy = new boolean[width][height];



        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(allinput);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

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
    }


    private void worldMoves(int width, int height, int scale) {    // for duplicating rooms across the board
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
        roomSet.add(newRoom);

        for (int i = x; i < x + roomWidth; i++) {
            for (int j = y; j < y + roomHeight; j++) {
                if (i >= 0 && j >= 0 && i < width && j < height) {
                    bboard[i][j] = true;
                    board[i][j] = Tileset.FLOWER;
                }
            }
        }
        board[centersW][centersH] = Tileset.MOUNTAIN;
    }

    public void closestRooms(int width, int height) {
        for (Room room : roomSet) {
            adjRooms = new TreeSet<>(Comparator.comparingDouble(r -> dist(room, r))); // Use a TreeSet with a custom comparator
            roomHash.put(room, adjRooms);
            while (roomHash.get(room).size() < 3) {
                Room adjRoom = adjRoom(room, width, height);
                adjRooms.add(adjRoom);
                roomHash.put(room, adjRooms);
            }
        }
    }


    public Double dist(Room room, Room r) {
        int x = Math.abs(room.getCenterX() - r.getCenterX());
        int y = Math.abs(room.getCenterY() - r.getCenterY());
        return (double) (x + y);
    }

    public Room adjRoom(Room room, int width, int height) {
        Room adjRoom = room;
        Double dist = width + .5;
        for (Room r : roomSet) {
            if (room == r || roomHash.get(room).contains(r)) {
                continue;
            }

            Double actDis = dist(room, r); //distance between room and r
            if (roomHash.get(room).contains(r)) {
                break;
            }
            if (actDis < dist) {
                adjRoom = r;
                dist = actDis;
            }
        }
        return adjRoom;
    }


//    private void buildHallways(int width, int height) {
//        for (Map.Entry<Room, Set<Room>> entry : roomHash.entrySet()) {
//            Room room1 = entry.getKey();
//            int startX = room1.getCenterX();
//            int startY = room1.getCenterY();
//
//            for (Room eachAdjRoom : entry.getValue()) {
//
//                if (board[startX][startY] == Tileset.MOUNTAIN || board[eachAdjRoom.getCenterX()][eachAdjRoom.getCenterY()] == Tileset.MOUNTAIN)
//                    connectCenters(startX, startY, eachAdjRoom.getCenterX(), eachAdjRoom.getCenterY());
//
//            }
//        }
//    }


    private void buildSortedHallways(int width, int height) {
        for (Map.Entry<Room, Set<Room>> entry : roomHash.entrySet()) {
            Room room1 = entry.getKey();
            int startX = room1.getCenterX();
            int startY = room1.getCenterY();

            // Use TreeSet to have a sorted collection of adjacent rooms
            TreeSet<Room> sortedAdjRooms = new TreeSet<>(Comparator.comparingDouble(r -> dist(room1, r)));

            sortedAdjRooms.addAll(entry.getValue());

            for (Room eachAdjRoom : sortedAdjRooms) {
                if (board[startX][startY] == Tileset.MOUNTAIN || board[eachAdjRoom.getCenterX()][eachAdjRoom.getCenterY()] == Tileset.MOUNTAIN) {
                    connectCenters(startX, startY, eachAdjRoom.getCenterX(), eachAdjRoom.getCenterY());
                }
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
                    if (bboard[i - 1][j + 1] && board[i][j] == Tileset.WATER) {//left top
                        board[i][j] = Tileset.TREE;
                    }
                }
            }
        }
    }


    public TETile[][] getTiles() {
        TETile[][] boardcopy = new TETile[board.length][board[0].length];
        displayScore(board.length, board[0].length);
        for (int i = 0; i < board.length; i++) {
            boardcopy[i] = Arrays.copyOf(board[i], board[0].length);
        }
        boardcopy[avatarX][avatarY] = Tileset.AVATAR;
        return boardcopy;
    }


    public void handle(char key) {
        char lower = Character.toLowerCase(key);

        if (seedInputPhase) {


            if (Character.isLowerCase(lower)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
                    writer.write(lower);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

        if (lower == 'w' || lower == 's' || lower == 'a' || lower == 'd') {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
                writer.write(lower);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (lower == 'c') {
            if (bboardCopy[avatarX][avatarY]){
                flowers++;
            }
            board[avatarX][avatarY] = Tileset.GRASS;
            bboardCopy[avatarX][avatarY] = false;
        }
    }





    public void displayScore(int width, int height) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont();

        int flower = flowers;

        double X = width -5;
        double Y = height -2;

        StdDraw.text(X, Y, "Score: " + flower);

        StdDraw.show();
    }

}




