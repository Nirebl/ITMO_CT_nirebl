package game;

import java.util.Arrays;
import java.util.Map;

public abstract class NMBoardBase implements Board {
    protected static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );

    protected final int n, m;
    protected final int k;
    protected final Position position;
    protected final Cell[][] field;
    protected Cell turn;
    private int availableCells;


    public NMBoardBase(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.field = new Cell[n][m];
        availableCells = n * m;

        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }

        turn = Cell.X;
        position = new NMBoardBasePosition(this);
    }


    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public MoveResult makeMove(Move move) {
        if (!position.isValid(move)) {
            return MoveResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        availableCells--;
        if (checkWin(move)) {
            return MoveResult.WIN;
        }

        if (checkDraw()) {
            return MoveResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;

        return MoveResult.UNKNOWN;
    }

    //protected abstract boolean checkDraw();
    private boolean checkDraw() {
        return availableCells == 0;
    }

    protected abstract boolean checkWin(Move move);

    private class NMBoardBasePosition implements Position {
        private final NMBoardBase board;

        public NMBoardBasePosition(NMBoardBase board) {
            this.board = board;
        }

        @Override
        public boolean isValid(Move move) {
            return 0 <= move.getRow() && move.getRow() < board.n
                    && 0 <= move.getCol() && move.getCol() < board.m
                    && board.field[move.getRow()][move.getCol()] == Cell.E
                    && board.turn == move.getValue();
        }

        @Override
        public Cell getCell(int row, int column) {
            return board.field[row][column];
        }

        @Override
        public Cell getTurn() {
            return board.turn;
        }

        @Override
        public String toString() {
            return board.toString();
        }
    }
}
