package game;

import java.util.Scanner;

public class PlayerFactory {

    public static Player CreatePlayer(String player, Config cfg) {
        switch(player){
            case "Human":
                return new HumanPlayer(new Scanner(System.in));
            case "Random":
                return new RandomPlayer(cfg.getN(), cfg.getM());
            case "Sequential":
                return new SequentialPlayer(cfg.getN(), cfg.getM());
            default:
                throw new AssertionError("Unknown player " + player);
        }
    }

}
