package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);
        if (state == null)
            newGame(request, view);
        else
            view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);

        String cell = request.getParameterMap().keySet().stream()
                .filter(x -> x.matches("cell_\\d{2}"))
                .findFirst()
                .orElse(null);

        if (cell == null || cell.isEmpty()) {
            return;
        }

        state.nextState(cell.charAt(5) - '0', cell.charAt(6) - '0');

        view.put("state", state);
        throw new RedirectException("/ticTacToe");
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State(4);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private State getState(HttpServletRequest request) {
        return (State) request.getSession().getAttribute("state");
    }

    public static class State {
        private final int size;
        private int emptyCellCount;
        private Phase phase;
        private boolean crossesMove;
        private final Cell[][] cells;

        State(int size) {
            this.size = size;
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
            this.cells = new Cell[size][size];
            this.emptyCellCount = size * size;
        }

        public int getSize() {
            return size;
        }

        public Phase getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public void nextState(int row, int column) {
            if (phase != Phase.RUNNING || !isFreeCell(row, column)) {
                return;
            }

            cells[row][column] = crossesMove ? Cell.X : Cell.O;
            emptyCellCount--;

            checkForEndGame(row, column);
            crossesMove = !crossesMove;
        }

        private void checkForEndGame(int row, int column) {
             if (checkWin(row, column)) {
                phase = crossesMove ? Phase.WON_X : Phase.WON_O;
            } else if (emptyCellCount == 0) {
                phase = Phase.DRAW;
            }
        }

        private boolean checkWin(int row, int column) {
            if (countCells(row, column, -1, 0) +
                    countCells(row, column, 1, 0) - 1 >= size)
                return true;
            if (countCells(row, column, 0, -1) +
                    countCells(row, column, 0, 1) - 1 >= size)
                return true;
            if (countCells(row, column, -1, -1) +
                    countCells(row, column, 1, 1) - 1 >= size)
                return true;
            if (countCells(row, column, 1, -1) +
                    countCells(row, column, -1, 1) - 1 >= size)
                return true;

            return false;
        }

        private int countCells(int row, int column, int deltaRow, int deltaColumn) {
            return isValid(row, column) && cells[row][column] == (crossesMove ? Cell.X : Cell.O) ?
                    countCells(row + deltaRow, column + deltaColumn, deltaRow, deltaColumn) + 1
                    : 0;
        }

        private boolean isValid(int row, int column) {
            return 0 <= row && row < size && 0 <= column && column < size;
        }

        private boolean isFreeCell(int row, int column) {
            return isValid(row, column) && cells[row][column] == null;
        }
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }

    public enum Cell {
        X, O
    }
}
