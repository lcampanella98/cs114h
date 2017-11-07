package projects.knightstour;

import java.util.*;

public class Main {
    public static int bLen = 8;

    private Square[][] board;
    private Square[][][] allMoves;
    private int startRow, startCol;
    private int moveNumber;
    private int totalMoves;
    private SquareComparator sqComp;

    public Main(int startRow, int startCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        board = new Square[bLen][bLen];
        for (int i = 0; i < bLen; i++) {
            for (int j = 0; j < bLen; j++) board[i][j] = new Square(i, j);
        }
        moveNumber = 0;
        totalMoves = bLen * bLen;
        allMoves = new Square[bLen][bLen][];
        for (int i = 0; i < allMoves.length; i++) {
            for (int j = 0; j < allMoves[i].length; j++) {
                allMoves[i][j] = getAvailableMoves(board[i][j]);
                for (int k = 0; k < allMoves[i][j].length; k++) allMoves[i][j][k].accessibility++;
            }
        }
        sqComp = new SquareComparator();
    }

    public void tour(boolean print) {
        place(startRow, startCol);
        if (!print) return;
        int numSpaces = (int)Math.log10(totalMoves) + 1;
        for (Square[] row : board) {
            for (Square sq : row) {
                int nDigits = (int)Math.log10(sq.move) + 1;
                System.out.print(sq.move);
                for (int i = 0; i < numSpaces - nDigits + 1; i++) System.out.print(" ");

            }
            System.out.println();
        }
    }

    private boolean place(int row, int col) {
        board[row][col].move = ++moveNumber;
        if (moveNumber >= totalMoves) return true;
        Square[] availMoves = allMoves[row][col];
        for (Square mov : availMoves) mov.accessibility--;
        Arrays.sort(availMoves, sqComp);
        for (Square mov : availMoves) {
            if (board[mov.row][mov.col].move <= 0 && (mov.accessibility > 0 || moveNumber == totalMoves - 1) && place(mov.row, mov.col)) return true;
        }
        for (Square mov : availMoves) mov.accessibility++;
        board[row][col].move = 0;
        --moveNumber;
        return false;
    }

    private Square[] getAvailableMoves(Square cur) {
        int r = cur.row, c = cur.col;
        List<Square> moves = new ArrayList<>(8);
        if (r + 2 < bLen) {
            if (c + 1 < bLen) moves.add(board[r + 2][c + 1]);
            if (c - 1 >= 0) moves.add(board[r + 2][c - 1]);
        }
        if (r - 2 >= 0) {
            if (c + 1 < bLen) moves.add(board[r - 2][c + 1]);
            if (c - 1 >= 0) moves.add(board[r - 2][c - 1]);
        }
        if (c + 2 < bLen) {
            if (r + 1 < bLen) moves.add(board[r + 1][c + 2]);
            if (r - 1 >= 0) moves.add(board[r - 1][c + 2]);
        }
        if (c - 2 >= 0) {
            if (r + 1 < bLen) moves.add(board[r + 1][c - 2]);
            if (r - 1 >= 0) moves.add(board[r - 1][c - 2]);
        }
        return moves.toArray(new Square[0]);
    }

    public static void main(String[] args) {
        long start, end;
//        start = System.currentTimeMillis();
//        int l = 24;
//        bLen = l;
//        for (int i = 0; i < l; i++) {
//            for (int j = 0; j < l; j++) {
//                Main m = new Main(i, j);
//                System.out.println(i + "," + j);
//                m.tour(false);
//            }
//        }
//        System.out.println("-----------------------");
//        end = System.currentTimeMillis();
//        System.out.println("Solutions found in " + (end - start) + " ms");
//        if (true) return;
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Enter an even-number chess board: ");
        int size = Integer.parseInt(scan.nextLine());
        System.out.println("Enter initial row of the knight between 1 and " + size + " or leave blank to use random row and column:");
        int row, col;
        try {
            row = Integer.parseInt(scan.nextLine()) - 1;
            System.out.println("Enter initial column of the knight between 1 and " + size + " or leave blank to use random row and column:");
            col = Integer.parseInt(scan.nextLine()) - 1;
        }catch (Exception e) {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
            System.out.println("Starting at " + (row+1) + "," + (col+1));
        }
        System.out.println("Searching...");
        System.out.println("-----------------------");
        bLen = size;
        Main m = new Main(row, col);
        start = System.currentTimeMillis();
        m.tour(true);
        end = System.currentTimeMillis();
        System.out.println("-----------------------");
        System.out.println("Solution found in " + (end - start) + " ms");
    }
}

class Square {
    int row, col;
    int accessibility;
    int move;
    public Square(int r, int c) {
        row = r;
        col = c;
        accessibility = 0;
    }
}

class SquareComparator implements Comparator<Square> {

    @Override
    public int compare(Square o1, Square o2) {
        if (o1.accessibility > o2.accessibility) return 1;
        if (o1.accessibility < o2.accessibility) return -1;
        return 0;
    }
}