package game;

import java.io.IOException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());

        Move move = null;

        int ask = 0;
        while (ask++ <= 1) {
            move = new Move(in.nextInt() - 1, in.nextInt() - 1, position.getTurn());

            if (position.isValid(move)) {
                return move;
            }

            System.out.println("Wrong move, please write correct");
        }
        return move;
    }
}
