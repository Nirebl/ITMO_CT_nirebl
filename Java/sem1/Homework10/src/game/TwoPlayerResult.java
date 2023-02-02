package game;

public class TwoPlayerResult extends GameResult {
    private final boolean hasWinner;
    private final int noPlayer;

    public TwoPlayerResult(boolean hasWinner, int noPlayer) {
        this.hasWinner = hasWinner;
        this.noPlayer = noPlayer;
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public int getNoPlayer() {
        return noPlayer;
    }
}
