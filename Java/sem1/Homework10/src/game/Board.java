package game;

public interface Board {
    Position getPosition();

    MoveResult makeMove(Move move);
}
