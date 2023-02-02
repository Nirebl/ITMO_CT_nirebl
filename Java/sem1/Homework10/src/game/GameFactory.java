package game;

import java.util.ArrayList;

public class GameFactory {
    public static Game CreateGame(Config cfg) {
        String game = cfg.getGame();
        switch (game) {
            case "TwoPlayer":
                return CreateTwoPlayerGame(cfg);
            case "Tournament":
                return CreateTournamentGame(cfg);
            default:
                throw new AssertionError("Unknown game " + game);
        }
    }

    private static Game CreateTwoPlayerGame(Config cfg) {
        Board board = BoardFactory.CreateBoard(cfg);

        Player player1 = PlayerFactory.CreatePlayer(cfg.getPlayers().get(0), cfg);
        Player player2 = PlayerFactory.CreatePlayer(cfg.getPlayers().get(1), cfg);

        return new TwoPlayerGame(board, player1, player2);
    }

    private static Game CreateTournamentGame(Config cfg) {
        ArrayList<Player> players = new ArrayList<>();
        for (String strPl : cfg.getPlayers()) {
            players.add(PlayerFactory.CreatePlayer(strPl, cfg));
        }

        return new TournamentGame(cfg, players);
    }

}
