package game;

public class TwoPlayerGame extends Game{
    private final Board board;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public GameResult play(boolean log) {
        while (true) {
            final GameResult result1 = makeMove(player1, 1, log);
            if (result1 != null)  {
                return result1;
            }
            final GameResult result2 = makeMove(player2, 2, log);
            if (result2 != null)  {
                return result2;
            }
        }
    }

    private GameResult makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final MoveResult result = board.makeMove(move);
        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return new TwoPlayerResult(true, no);
            case LOOSE:
                return new TwoPlayerResult(true, 3 -no);
            case DRAW:
                return new TwoPlayerResult(false, -1);
            case UNKNOWN:
                return null;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
