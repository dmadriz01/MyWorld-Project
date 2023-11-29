package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.io.IOException;

public class Main {
    private static HUD mousehud;
    private static World w;


    public static void main(String[] args) throws IOException {
        displayMain();
        int WIDTH = 50;
        int HEIGHT = 50;
        StringBuilder seed = new StringBuilder();
        w = null;

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        boolean done = false;
        boolean seedInputScreen = false;

        while (!done) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                char lower = Character.toLowerCase(key);
                if (lower == 'n' && !seedInputScreen) {
                    seedInputScreen = true;
                } else if (seedInputScreen) {
                    if (Character.isDigit(lower)) {
                        seed.append(lower);
                    } else if (lower == 's' && !seed.isEmpty()) {
                        w = new World(WIDTH, HEIGHT, Long.parseLong(seed.toString()));
                        boolean[][] bboard = w.getbboard();
                        mousehud = new HUD(WIDTH, HEIGHT, bboard);
                        done = true;
                    }
                } else if (lower == 'l') {
                    done = true;
                }
            }
            if (seedInputScreen) {
                Screen.displaySeedInput(seed.toString());
            } else {
                displayMain();
            }
        }

        if (w == null) {
            w = new World(WIDTH, HEIGHT, 5);
        }

        boolean quit = false;

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                char lower = Character.toLowerCase(key);
                if (key == ':') {
                    quit = true;
                }
                if (quit && lower == 'q') {
                    Screen.displayGameEnded();
                    break;
                }
                w.handle(key);
            }
            ter.renderFrame(w.getTiles());
            displayScore(w, WIDTH, HEIGHT);
            displayHUD();
            StdDraw.show();
        }
    }
    private static void displayMain() {
        Screen screen = new Screen();
        screen.displayMain();
    }

    public static void displayScore(World w, int width, int height) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont();

        int flower = w.getflowers();

        double X = width - 5;
        double Y = height - 2;

        StdDraw.text(X, Y, "Score: " + flower);

    }

    public static void displayHUD(){
        mousehud.updateMousePosition();
        String hoverInfo = mousehud.getMouseHoverObject();
        mousehud.displayMouseHUD(hoverInfo);

//        if (mousehud.isStopFlickering()) {
//
//            mousehud.changeStopFlickering();
//        } else {
//            StdDraw.show();
//        }
    }



}
