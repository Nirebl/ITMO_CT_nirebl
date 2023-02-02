package game;

import java.util.HashMap;
import java.util.Map;

public class TournamentResult extends GameResult {
    private final int countPlayers;
    private final Map<String, Integer> results;

    public TournamentResult(int countPlayers, Map<String, Integer> results) {
        this.countPlayers = countPlayers;
        this.results = results;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public int getPoints(int player1, int player2) {
        return results.get(player1 + ":" + player2);
    }

}
