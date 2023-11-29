//package core;
//
//import edu.princeton.cs.algs4.StdDraw;
//public class Screen {
//    private static final int WIDTH = 50;
//    private static final int HEIGHT = 50;
//    private static final double TEXT_SCALE = 0.03; // Adjust this scale factor
//
//    private int flowerCount;
//
//    public void displayMain() {
//        StdDraw.clear(StdDraw.BLACK);
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//
//        // Draw title
//        StdDraw.setPenColor(StdDraw.WHITE);
//        double titleX = WIDTH / 2;
//        double titleY = HEIGHT * 0.8;
//        StdDraw.text(titleX, titleY, "My Game");
//
//        // Draw menu options
//        double menuX = WIDTH / 2;
//        double menuY1 = HEIGHT * 0.6;
//        double menuY2 = HEIGHT * 0.5;
//        double menuY3 = HEIGHT * 0.4;
//
//        StdDraw.text(menuX, menuY1, "N - New World");
//        StdDraw.text(menuX, menuY2, "L - Load World");
//        StdDraw.text(menuX, menuY3, "Q - Quit");
//
//        StdDraw.show();
//    }
//
//    public static void displaySeedInput(String seedInput) {
//        StdDraw.clear(StdDraw.BLACK);
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//
//
//        StdDraw.setPenColor(StdDraw.WHITE);
//        double X = WIDTH / 2;
//        double Y = HEIGHT * 0.8;
//        StdDraw.text(X, Y, "Enter Seed");
//
//        // Draw seed input
//
//        StdDraw.text(X, Y-5,"Seed: " + seedInput);
//        StdDraw.text(X, Y-10, "Press S to Start");
//        StdDraw.show();
//    }
//
//
//    public static void displayGameEnded() {
//        StdDraw.clear(StdDraw.BLACK);
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//
//        StdDraw.setPenColor(StdDraw.WHITE);
//        double inX = WIDTH / 2;
//        double inY = HEIGHT * 0.8;
//        StdDraw.text(inX, inY, "Game Ended");
//
//        StdDraw.show();
//    }
//
//}