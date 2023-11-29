package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static HUD mousehud;
    private static World w;


    public static void main(String[] args) throws IOException {
        displayMain();
        int width = 50;
        int height = 50;
        StringBuilder seed = new StringBuilder();
        w = null;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

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
                        w = new World(width, height, Long.parseLong(seed.toString()));
                        boolean[][] bboard = w.getbboard();
                        mousehud = new HUD(width, height, bboard);
                        done = true;
                    }
                } else if (lower == 'l') {
                    // get the input from file,
                    // do world.handleInput(input)
                    BufferedReader br
                            = new BufferedReader(new FileReader("output.txt"));
                    w = World.handleStringInput(br.readLine());
                    boolean[][] bboard = w.getbboard();
                    mousehud = new HUD(width, height, bboard);
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
            w = new World(width, height, 5);
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
                    w.save();
                    break;
                }
                w.handle(key);
            }
            ter.renderFrame(w.getTiles());
            displayScore(width, height);
            displayHUD();
            StdDraw.show();
        }
    }
    private static void displayMain() {
        Screen screen = new Screen();
        screen.displayMain();
    }

    public static void displayScore(int width, int height) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont();

        int flower = w.getflowers();

        double X = width - 5;
        double Y = height - 2;

        StdDraw.text(X, Y, "Score: " + flower);

    }

    public static void displayHUD() {
        mousehud.updateMousePosition();
        String hoverInfo = mousehud.getMouseHoverObject();
        mousehud.displayMouseHUD(hoverInfo);
    }



}
