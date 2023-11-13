package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class Main {

    public static void main(String[] args) {

        int WIDTH = 50;
        int HEIGHT = 50;

        // build your own world!
        //running world

        World w = new World(WIDTH, HEIGHT);

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(w.getTiles());

    }
}
