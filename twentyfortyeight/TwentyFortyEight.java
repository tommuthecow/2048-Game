package org.cis1200.twentyfortyeight;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TwentyFortyEight {
    Square[][] board;
    //LinkedList<Square[][]> boardList = new LinkedList<>();
    private final int rows;
    private final int columns;
    private int score = 0;
    private int highScore = 0;

    /**
     * Constructor sets up game state.
     */
    public TwentyFortyEight(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        board = new Square[rows][columns];
    }


    /**
     * Retrieves the score
     *
     * @return int that represents the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves the highest score
     *
     * @return int that represents the highest score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Retrieves the board with all the Square's values
     *
     * @return 2D int array representing the values on the board
     */
    public int[][] getValueBoard() {
        int[][] vBoard = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    vBoard[i][j] = 0;
                } else {
                    vBoard[i][j] = board[i][j].getValue();
                }
            }
        }
        return vBoard;
    }

    /**
     * Sets squares on a specific coordinate
     *
     * @param row: row of coordinate
     *             col: column of coordinate
     *             square: square that is set in coordinate
     */
    public void setSquare(int row, int col, Square square) {
        board[row][col] = square;
    }

    /**
     * Sets squares on a specific coordinate
     *
     * @return LinkedList of all null coordinates with row and column
     */
    public Set<int[]> getEmpty() {
        Set<int[]> emptySet = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    int[] empty = {i, j};
                    emptySet.add(empty);
                }
            }
        }
        return emptySet;
    }

    /**
     * Adds a square to an empty coordinate (either 2 or 4 with 10% chance being 4)
     */
    public void addSquare() {
        Random rand = new Random();
        int value = (rand.nextDouble() < 0.1) ? 4 : 2; // 10% chance of 4, 90% chance of 2
        Set<int[]> emptySet = getEmpty();
        if (emptySet.isEmpty()) {
            return; // no empty cells, can't add a square
        }
        int index = rand.nextInt(emptySet.size());
        int i = 0;
        for (int[] emptyCell : emptySet) {
            if (i == index) {
                setSquare(emptyCell[0], emptyCell[1], new Square(value));
                return;
            }
            i++;
        }
    }

    /**
     * resets the status of Separate of the entire board
     */
    private void resetSeparate() {
        for (Square[] squares : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (squares[j] != null) {
                    squares[j].setSeparate(true);
                }
            }
        }
    }

    /*public boolean undo() {
        try {
            this.boardList.removeLast();
            board = this.boardList.getLast();
            return true;
        } catch (Exception e){
            System.out.println("can't undo");
            return false;
        }
    } */

    public boolean moveLeft() {
        int[][] first = getValueBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                moveLeftHelper(i, j);
            }
        }
        resetSeparate();
        int[][] second = getValueBoard();
        return !Arrays.deepEquals(first, second);
    }

    private void moveLeftHelper(int row, int column) {
        Square square = board[row][column];
        if (square == null) {
            return;
        }
        if (column == 0) {
            return;
        } else if (board[row][column - 1] == null) {
            board[row][column - 1] = square;
            board[row][column] = null;
            moveLeftHelper(row, column - 1);
        } else {
            if (square.getValue() == board[row][column - 1].getValue()
                    && board[row][column].getSeparate()
                    && board[row][column - 1].getSeparate()) {
                Square newSquare = square.merge(board[row][column - 1]);
                score += newSquare.getValue();
                board[row][column - 1] = newSquare;
                board[row][column] = null;
            } else {
                return;
            }
        }
    }

    public boolean moveRight() {
        int[][] first = getValueBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = board[0].length - 1; j >= 0; j--) {
                moveRightHelper(i, j);
            }
        }
        resetSeparate();
        int[][] second = getValueBoard();
        return !Arrays.deepEquals(first, second);
    }

    private void moveRightHelper(int row, int column) {
        Square square = board[row][column];
        if (square == null) {
            return;
        }

        if (column == board[0].length - 1) {
            return;
        } else if (board[row][column + 1] == null) {
            board[row][column + 1] = square;
            board[row][column] = null;
            moveRightHelper(row, column + 1);
        } else {
            if (square.getValue() == board[row][column + 1].getValue()
                    && board[row][column].getSeparate()
                    && board[row][column + 1].getSeparate()) {
                Square newSquare = square.merge(board[row][column + 1]);
                score += newSquare.getValue();
                board[row][column + 1] = newSquare;
                board[row][column] = null;
            } else {
                return;
            }
        }

    }

    public boolean moveUp() {
        int[][] first = getValueBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                moveUpHelper(i, j);
            }
        }
        resetSeparate();
        int[][] second = getValueBoard();
        return !Arrays.deepEquals(first, second);
    }

    private void moveUpHelper(int row, int column) {
        Square square = board[row][column];
        if (square == null) {
            return;
        }

        if (row == 0) {
            return;
        } else if (board[row - 1][column] == null) {
            board[row - 1][column] = square;
            board[row][column] = null;
            moveUpHelper(row - 1, column);
        } else {
            if (square.getValue() == board[row - 1][column].getValue()
                    && board[row][column].getSeparate()
                    && board[row - 1][column].getSeparate()) {
                Square newSquare = square.merge(board[row - 1][column]);
                score += newSquare.getValue();
                board[row - 1][column] = newSquare;
                board[row][column] = null;
            } else {
                return;
            }
        }
    }

    public boolean moveDown() {
        int[][] first = getValueBoard();

        for (int i = (board.length - 1); i >= 0; i--) {
            for (int j = 0; j < board[0].length; j++) {
                moveDownHelper(i, j);
            }
        }
        resetSeparate();
        int[][] second = getValueBoard();
        return !Arrays.deepEquals(first, second);
    }

    private void moveDownHelper(int row, int column) {
        Square square = board[row][column];
        if (square == null) {
            return;
        }

        if (row == board.length - 1) {
            return;
        } else if (board[row + 1][column] == null) {
            board[row + 1][column] = square;
            board[row][column] = null;
            moveDownHelper(row + 1, column);
        } else {
            if (square.getValue() == board[row + 1][column].getValue()
                    && board[row][column].getSeparate()
                    && board[row + 1][column].getSeparate()) {
                Square newSquare = square.merge(board[row + 1][column]);
                score += newSquare.getValue();
                board[row + 1][column] = newSquare;
                board[row][column] = null;
            } else {
                return;
            }
        }
    }

    public boolean checkWin() {
        int[][] valueBoard = getValueBoard();
        for (int i = 0; i < valueBoard.length; i++) {
            for (int j = 0; j < valueBoard[0].length; j++) {
                if (valueBoard[i][j] == 2048) {
                    highScore = 2048;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLoss() {
        if (checkWin()) {
            return false;
        }

        int tempScore = this.score;
        Square[][] tempBoard = new Square[board.length][];
        for (int i = 0; i < board.length; i++) {
            tempBoard[i] = board[i].clone();
        }

        boolean winPossible = moveUp() || moveDown() || moveRight() || moveLeft();

        board = tempBoard;
        score = tempScore;

        return !winPossible;
    }


    public void load(String path) {
        try {
            if (path == null) {
                throw new IllegalArgumentException("path is null");
            } else {
                try {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    for (int i = 0; i < board.length; i++) {
                        String l = br.readLine();
                        String[] values = l.split(",");
                        for (int j = 0; j < board[0].length; j++) {
                            board[i][j] = new Square(Integer.parseInt(values[j]));
                        }
                    }
                    score = Integer.parseInt(br.readLine());
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException("invalid path");
                } catch (IOException e) {
                    System.out.println("IO exception");
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public void reset(String path) {
        board = new Square[rows][columns];
        score = 0;
        Double random = Math.random();
        if (random > 0.5) {
            addSquare();
        } else {
            addSquare();
            addSquare();
        }
        writeTo(path);

        File file;
        try {
            file = Paths.get("score.txt").toFile();
        } catch (InvalidPathException e) {
            file = new File("score.txt");
        }

        try {
            FileReader fr = new FileReader("score.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            int highestScore = Integer.parseInt(line);
            highScore = highestScore;
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public void writeTo(String path) {
        File file;
        try {
            file = Paths.get(path).toFile();
        } catch (InvalidPathException e) {
            file = new File(path);
        }

        try {
            FileWriter fw = new FileWriter(path, false);
            BufferedWriter bw = new BufferedWriter(fw);
            int[][] intBoard = getValueBoard();

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    bw.write(intBoard[i][j] + "");
                    bw.write(",");
                }
                bw.newLine();
            }

            bw.write(score + "");
            bw.close();

        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }

    public void writeHighScoreTo(String path) {
        File file;
        try {
            file = Paths.get(path).toFile();
        } catch (InvalidPathException e) {
            file = new File(path);
        }

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            int highestScore = Integer.parseInt(br.readLine());
            if (highestScore > highScore) {
                highScore = highestScore;
            }
            FileWriter fw = new FileWriter(path, false);
            BufferedWriter bw = new BufferedWriter(fw);
            if (score > highestScore) {
                highScore = score;
                bw.write(score + "");
                bw.newLine();
                bw.close();
            } else {
                bw.write(highestScore + "");
                bw.newLine();
                bw.close();
            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        }

    }
}
