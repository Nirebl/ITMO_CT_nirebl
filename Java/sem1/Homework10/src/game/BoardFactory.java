package game;

import static java.lang.Integer.min;

class BoardFactory {
    public static Board CreateBoard(Config cfg) {
        String board = cfg.getBoard();
        switch (board) {
            case "Rectangle":
                return CreateRectangleBoard(cfg);
            case "Diamond":
                return CreateDiamodBoard(cfg);
            default:
                throw new AssertionError("Unknown board " + board);
        }
    }

    private static Board CreateRectangleBoard(Config cfg) {
        int n = cfg.getN();
        int m = cfg.getM();
        int k = cfg.getK();

        if (k < 3) {
            throw new AssertionError("k is smaller than 3");
        }

        if (k > min(n, m)) {
            throw new AssertionError("k is more than n or m");
        }

        if (n * m > 2000 || n * m == 0) {
            throw new AssertionError("m or n is incorrect");
        }

        return new RectangleBoard(n, m, k);
    }

    private static Board CreateDiamodBoard(Config cfg) {
        int n = cfg.getN();
        int m = cfg.getM();
        int k = cfg.getK();

        if (k < 3) {
            throw new AssertionError("k is smaller than 3");
        }

        if (k > min(n, m)) {
            throw new AssertionError("k is more than n or m");
        }

        if (n * m > 2000 || n * m == 0) {
            throw new AssertionError("m or n is incorrect");
        }

        return new DiamondBoard(n, m, k);
    }

}
