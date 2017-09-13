package projects.knightstour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static int bLen = 8;

    private int[][] board;
    private Square[][][] availMoves;
    private int startRow, startCol;
    private int numVisits;
    private int totalVisitsNeeded;

    public Main(int startRow, int startCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        board = new int[bLen][bLen];
        numVisits = 0;
        totalVisitsNeeded = bLen * bLen;
        availMoves = new Square[bLen][bLen][];
        for (int i = 0; i < availMoves.length; i++) {
            for (int j = 0; j < availMoves[i].length; j++) {
                availMoves[i][j] = getAvailableMoves(new Square(i, j));
            }
        }
    }

    public void tour() {
        place(startRow, startCol);
        for (int[] row : board) {
            for (int visit : row) {
                System.out.print(visit + (visit< 10 ? "  " : " "));
            }
            System.out.println();
        }
    }

    private boolean place(int row, int col) {
        board[row][col] = ++numVisits;
        if (numVisits >= totalVisitsNeeded) return true;
        for (Square mov : availMoves[row][col]) {
            if (board[mov.row][mov.col] <= 0 && place(mov.row, mov.col)) return true;
        }
        board[row][col] = 0;
        --numVisits;
        return false;
    }

    private Square[] getAvailableMoves(Square cur) {
        int r = cur.row, c = cur.col;
        List<Square> moves = new ArrayList<>(8);
        if (r + 2 < bLen) {
            if (c + 1 < bLen) moves.add(new Square(r + 2, c + 1));
            if (c - 1 >= 0) moves.add(new Square(r + 2, c - 1));
        }
        if (r - 2 >= 0) {
            if (c + 1 < bLen) moves.add(new Square(r - 2, c + 1));
            if (c - 1 >= 0) moves.add(new Square(r - 2, c - 1));
        }
        if (c + 2 < bLen) {
            if (r + 1 < bLen) moves.add(new Square(r + 1, c + 2));
            if (r - 1 >= 0) moves.add(new Square(r - 1, c + 2));
        }
        if (c - 2 >= 0) {
            if (r + 1 < bLen) moves.add(new Square(r + 1, c - 2));
            if (r - 1 >= 0) moves.add(new Square(r - 1, c - 2));
        }
        return moves.toArray(new Square[0]);
    }

    public static void main(String[] args) {
        int row = 3, col = 4;
        Main m = new Main(row, col);
        long start, end;
        start = System.currentTimeMillis();
        m.tour();
        end = System.currentTimeMillis();
        System.out.println("\n-----------------------");
        System.out.println("executed in " + (end - start) + "ms");
    }
}

class Square {
    int row, col;
    public Square(int r, int c) {row = r;col = c;}
}
