package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        displayMain();
        int WIDTH = 50;
        int HEIGHT = 50;

        // build your own world!
        //running world

        World w = null;

        boolean done = false;
        while (!done){
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                char lower = Character.toLowerCase(key);
                if (lower == 'n'){
                    String seed = "";
                    while (true){
                        if (StdDraw.hasNextKeyTyped()) {
                            char lowerkey = Character.toLowerCase(StdDraw.nextKeyTyped());
                            if (lowerkey =='s'){
                                break;
                            }
                            seed += lowerkey;
                        }
                    }

                    w = new World(WIDTH, HEIGHT, Integer.parseInt(seed));

                    done = true;
                }
                else if (lower == 'l'){


                    done = true;
                }
            }
        }
        if (w == null){
            w = new World(WIDTH, HEIGHT, 5);
        }


        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        boolean quit = false;

        while(true) {

            if (StdDraw.hasNextKeyTyped()) {

                char key = StdDraw.nextKeyTyped();
                char lower = Character.toLowerCase(key);
                if (key == ':'){
                    quit = true;
                }
                if (quit && lower == 'q'){
                    break;
                }
                w.handle(key);

            }

            ter.renderFrame(w.getTiles());
        }
        

    }

    private static void displayMain() {
        Screen screen = new Screen();
        screen.displayMain();
    }

}
