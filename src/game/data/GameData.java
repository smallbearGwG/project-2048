package game.data;

import java.io.Serializable;

public class GameData implements Serializable, Cloneable {
    public int size;
    public int[][] board;
    public int steps;
    public int score;
    public long time;
    public long endTime;
    public int targetNumber;
    public boolean countdownMode;
    public boolean win;
    public boolean props;

    public GameData(int size, int targetNumber) {
        this(size, targetNumber, 5, false);
    }

    public GameData(int size, int targetNumber, int endTime, boolean countdownMode) {
        this(size, targetNumber, endTime, countdownMode, false);
    }

    public GameData(int size, int targetNumber, int endTime, boolean countdownMode, boolean props) {
        board = new int[size][size];
        this.size = size;
        this.endTime = endTime;
        this.targetNumber = targetNumber;
        this.countdownMode = countdownMode;
        this.props = props;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public long showTime() {
        if (countdownMode) {
            return (endTime - time > 0) ? endTime - time : 0;
        } else {
            return time;
        }
    }

    @Override
    public GameData clone() {
        try {
            GameData cloned = (GameData) super.clone();
            cloned.board = new int[size][size];
            for (int i = 0; i < size; i++) {
                System.arraycopy(this.board[i], 0, cloned.board[i], 0, size);
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

}
