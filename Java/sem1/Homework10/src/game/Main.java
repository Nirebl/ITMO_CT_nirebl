package game;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        try {
            Config cfg = Config.ReadConfiguration(args[0]);

            Game game = GameFactory.CreateGame(cfg);
            final GameResult result = game.play(true);

            if (result instanceof TwoPlayerResult) {
                ShowTwoPlayerResult((TwoPlayerResult) result);
            } else if (result instanceof TournamentResult) {
                ShowTournamentResult((TournamentResult) result);
            } else
                throw new AssertionError("Unknown result " + result);

        } catch (StringIndexOutOfBoundsException | IOException | NoSuchElementException e) {
            System.out.println("Config Error : " + e.getMessage());
        }
    }

    private static void ShowTwoPlayerResult(TwoPlayerResult result) {
        if (result.hasWinner())
            System.out.println(result.getNoPlayer() + " player won");
        else
            System.out.println("Draw");
    }

    private static void ShowTournamentResult(TournamentResult result) {
        int countPlayers = result.getCountPlayers();

        final StringBuilder sb = new StringBuilder("  ");
        for (int i = 1; i <= countPlayers; i++) {
            sb.append(i);
            sb.append(" ");
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < countPlayers; r++) {
            sb.append(r + 1);
            sb.append(" ");
            for (int c = 0; c < countPlayers; c++) {
                if (r == c) {
                    sb.append("X");
                } else
                    sb.append(result.getPoints(r, c));
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());

        System.out.println(sb.toString());
    }
}
