package game;

public final class DiamondBoard extends NMBoardBase {

    public DiamondBoard(int n, int m, int k) {
        super(n, m, k);
    }

    @Override
    protected boolean checkWin(Move move) {
        if (goLeft(move) + goRight(move) + 1 >= k) {
            return true;
        }
        if (goLeftDown(move) + goRightUp(move) + 1 >= k) {
            return true;
        }
        if (goLeftUp(move) + goRightDown(move) + 1 >= k) {
            return true;
        }

        return false;
    }

    private int goLeft(Move move) {
        int sum = 0;

        for (int i = move.getCol() - 1; i >= 0 && field[move.getRow()][i] == move.getValue() && sum < k; --i) {
            sum++;
        }
        return sum;
    }

    private int goLeftUp(Move move) {
        int sum = 0;

        int i = move.getRow() - 1;
        int j = move.getCol()/* - 1;*/;
        while (i >= 0 && j >= 0) {
            if (field[i][j] == move.getValue() && sum < k) {
                sum++;
            } else {
                return sum;
            }
            i--;
        }
        return sum;
    }

    private int goLeftDown(Move move) {
        int sum = 0;

        int i = move.getRow() + 1;
        int j = move.getCol() - 1;
        while (i < n && j >= 0) {
            if (field[i][j] == move.getValue() && sum < k) {
                sum++;
            } else {
                return sum;
            }
            i++;
            j--;
        }
        return sum;
    }

    private int goRight(Move move) {
        int sum = 0;

        for (int i = move.getCol() + 1; i < m && field[move.getRow()][i] == move.getValue() && sum < k; ++i) {
            sum++;
        }
        return sum;
    }

    private int goRightDown(Move move) {
        int sum = 0;

        int i = move.getRow() + 1;
        int j = move.getCol() /*+ 1*/;
        while (i < n && j < m) {
            if (field[i][j] == move.getValue() && sum < k) {
                sum++;
            } else {
                return sum;
            }
            i++;
        }
        return sum;
    }

    private int goRightUp(Move move) {
        int sum = 0;

        int i = move.getRow() - 1;
        int j = move.getCol() + 1;
        while (i >= 0 && j < m) {
            if (field[i][j] == move.getValue() && sum < k) {
                sum++;
            } else {
                return sum;
            }
            i--;
            j++;
        }
        return sum;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 1; i <= m; i++) {
            sb.append(i);
            sb.append(" ");
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < n; r++) {
            sb.append(r + 1);
            for (int k = r; k > 0; k--)
                sb.append(" ");
            for (Cell cell : field[r]) {
                sb.append(CELL_TO_STRING.get(cell));
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }

}
