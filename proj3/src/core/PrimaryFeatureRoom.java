package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class PrimaryFeatureRoom {
    private TETile[][] board;
    public int width;
    public int height;
    private static final Random RANDOM = new Random(2873123L);

    public PrimaryFeatureRoom(){

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = buildroom();
            }
        }

}

    private TETile buildroom(){
        int tileNum = RANDOM.nextInt(3);
        TETile var10000 = switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.FLOWER;
            default -> Tileset.NOTHING;
        };

        return var10000;
    }

}
