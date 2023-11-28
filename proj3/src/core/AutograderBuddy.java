package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.IOException;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) throws IOException {

        int WIDTH = 50;
        int HEIGHT = 50;
        long seed;

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        String lower = input.toLowerCase();
        String[] seedString = lower.split("[ns]");

        seed = Long.parseLong(seedString[1]);

        World w = new World(WIDTH, HEIGHT, seed);
        return w.getTiles();
        }





//        boolean done = false;
//        boolean seedInputScreen = false;
//
//        while (!done) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char key = StdDraw.nextKeyTyped();
//                char lower = Character.toLowerCase(key);
//                try {
//                    if (lower == 'n' && !seedInputScreen) {
//                        seedInputScreen = true;
//                    } else if (seedInputScreen) {
//                        if (Character.isDigit(lower)) {
//                            seed.append(lower);
//                        } else if (lower == 's' && !seed.isEmpty()) {
//                            w = new World(WIDTH, HEIGHT, Long.parseLong(seed.toString()));
//                            done = true;
//                        }
//                    } else if (lower == 'l') {
//                        done = true;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace(); // Handle the exception appropriately
//                }
//
//        if (w == null) {
//            w = new World(WIDTH, HEIGHT, 5);
//        }
//
//        boolean quit = false;
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char key = StdDraw.nextKeyTyped();
//                char lower = Character.toLowerCase(key);
//                if (key == ':') {
//                    quit = true;
//                }
//                if (quit && lower == 'q') {
//                    Screen.displayGameEnded();
//                    break;
//                }
//                w.handle(key);
//            }
//            return w.getTiles();
//        }
//    }

//        long seed;
//        int WIDTH = 50;
//        int HEIGHT = 50;
//
//
//        String numberOnly = input.replaceAll("[^0-9]", "");
//        seed = Long.parseLong(numberOnly);
//
//        try {
//            World w = new World(WIDTH, HEIGHT, seed);
//            return w.getTiles();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }


//        throw new RuntimeException("Please fill out AutograderBuddy!");




    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
