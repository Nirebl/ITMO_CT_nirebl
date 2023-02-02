package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentGame extends Game {
    private final Config cfg;
    private final List<Player> players;

    public TournamentGame(Config cfg, List<Player> players) {
        this.cfg = cfg;
        this.players = players;
    }


    private TwoPlayerResult playPair(Player player1, Player player2) {
        TwoPlayerGame game = new TwoPlayerGame(BoardFactory.CreateBoard(cfg), player1, player2);
        return (TwoPlayerResult) game.play(false);
    }


    @Override
    public GameResult play(boolean log) {
        final Map<String, Integer> results = new HashMap<>();

        int countPlayers = players.size();
        for (int i = 0; i < countPlayers; i++) {
            for (int j = i; j < countPlayers; j++) {
                if (i == j)
                    continue;

                Player pl1 = players.get(i);
                Player pl2 = players.get(j);

                int point1 = 0;
                int point2 = 0;

                TwoPlayerResult gRes = playPair(pl1, pl2);
                if (gRes.hasWinner()) {
                    if (gRes.getNoPlayer() == 1)
                        point1 += 3;
                    else
                        point2 += 3;
                } else {
                    point1++;
                    point2++;
                }

                gRes = playPair(pl2, pl1);
                if (gRes.hasWinner()) {
                    if (gRes.getNoPlayer() == 1)
                        point2 += 3;
                    else
                        point1 += 3;
                } else {
                    point1++;
                    point2++;
                }

                results.put(i + ":" + j, point1);
                results.put(j + ":" + i, point2);
            }
        }

        return new TournamentResult(countPlayers, results);
    }

}
