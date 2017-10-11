package projects.knightstour;

import java.util.*;

public class Main {
    public static int bLen = 8;

    private int[][] board;
    private Square[][][] availMoves;
    private int startRow, startCol;
    private int numVisits;
    private int totalVisitsNeeded;
    private SquareComparator sqComp;

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
        sqComp = new SquareComparator();
    }

    public void tour() {
        place(startRow, startCol);
        int numSpaces = (int)Math.log10(totalVisitsNeeded) + 1;
        for (int[] row : board) {
            for (int visit : row) {
                int nDigits = (int)Math.log10(visit) + 1;
                System.out.print(visit);
                for (int i = 0; i < numSpaces - nDigits + 1; i++) System.out.print(" ");

            }
            System.out.println();
        }
    }

    private boolean place(int row, int col) {
        board[row][col] = ++numVisits;
        if (numVisits >= totalVisitsNeeded) return true;
        Square[] curMoves = availMoves[row][col];
        for (Square mov : curMoves) {
            mov.futureMoves = 0;
            for (Square mov2 : availMoves[mov.row][mov.col]) {
                if (board[mov2.row][mov2.col] <= 0) mov.futureMoves++;
            }
        }
        Arrays.sort(curMoves, sqComp);
        for (Square mov : curMoves) {
            if (board[mov.row][mov.col] <= 0 && (mov.futureMoves > 0 || numVisits == totalVisitsNeeded - 1) && place(mov.row, mov.col)) return true;
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
        long start, end;
        start = System.currentTimeMillis();
        m.tour();
        end = System.currentTimeMillis();
        System.out.println("-----------------------");
        System.out.println("Solution found in " + (end - start) + " ms");
    }
}

class Square {
    int row, col, futureMoves;
    public Square(int r, int c) {row = r;col = c;}
}

class SquareComparator implements Comparator<Square> {

    @Override
    public int compare(Square o1, Square o2) {
        if (o1.futureMoves > o2.futureMoves) return 1;
        if (o1.futureMoves < o2.futureMoves) return -1;
        return 0;
    }
}