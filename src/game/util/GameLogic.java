package game.util;

import game.data.GameData;

import java.util.Random;

public class GameLogic {
    private static Random random = new Random();

    public static void initializeGame(GameData gameData) {
        addRandomTile(gameData);
        addRandomTile(gameData);
    }

    public static boolean moveLeft(GameData gameData) {
        boolean moved = false;
        for (int i = 0; i < gameData.size; i++) {
            int[] row = gameData.board[i];
            moved |= slideAndMerge(row, gameData);
        }
        if (moved) {
            addRandomTile(gameData);
            return true;
        }
        return false;
    }

    public static boolean moveRight(GameData gameData) {
        boolean moved = false;
        for (int i = 0; i < gameData.size; i++) {
            int[] row = reverseArray(gameData.board[i]);
            moved |= slideAndMerge(row, gameData);
            gameData.board[i] = reverseArray(row);
        }
        if (moved) {
            addRandomTile(gameData);
            return true;
        }
        return false;
    }

    public static boolean moveUp(GameData gameData) {
        boolean moved = false;
        for (int j = 0; j < gameData.size; j++) {
            int[] column = getColumn(gameData, j);
            moved |= slideAndMerge(column, gameData);
            setColumn(gameData, j, column);
        }
        if (moved) {
            addRandomTile(gameData);
            return true;
        }
        return false;
    }

    public static boolean moveDown(GameData gameData) {
        boolean moved = false;
        for (int j = 0; j < gameData.size; j++) {
            int[] column = reverseArray(getColumn(gameData, j));
            moved |= slideAndMerge(column, gameData);
            setColumn(gameData, j, reverseArray(column));
        }
        if (moved) {
            addRandomTile(gameData);
            return true;
        }
        return false;
    }

    private static boolean slideAndMerge(int[] line, GameData gameData) {
        boolean moved = false;
        int insertPos = 0;
        for (int i = 0; i < line.length; i++) {
            if (line[i] != 0) {
                if (insertPos != i) {
                    line[insertPos] = line[i];
                    line[i] = 0;
                    moved = true;
                }
                insertPos++;
            }
        }

        for (int i = 0; i < line.length - 1; i++) {
            if (line[i] != 0 && line[i] == line[i + 1]) {
                line[i] *= 2;
                gameData.score += line[i] / 2;
                for (int j = i + 1; j < line.length - 1; j++) {
                    line[j] = line[j + 1];
                }
                line[line.length - 1] = 0;
                moved = true;
            }
        }

        return moved;
    }

    private static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }

    private static int[] getColumn(GameData gameData, int index) {
        int[] column = new int[gameData.size];
        for (int i = 0; i < gameData.size; i++) {
            column[i] = gameData.board[i][index];
        }
        return column;
    }

    private static void setColumn(GameData gameData, int index, int[] column) {
        for (int i = 0; i < gameData.size; i++) {
            gameData.board[i][index] = column[i];
        }
    }

    private static void addRandomTile(GameData gameData) {
        int emptyCount = 0;
        for (int i = 0; i < gameData.size; i++) {
            for (int j = 0; j < gameData.size; j++) {
                if (gameData.board[i][j] == 0) {
                    emptyCount++;
                }
            }
        }

        if (emptyCount == 0) {
            return;
        }

        int randomTileIndex = random.nextInt(emptyCount);
        int value = random.nextInt(10) == 0 ? 4 : 2;
        int emptyPos = 0;

        for (int i = 0; i < gameData.size; i++) {
            for (int j = 0; j < gameData.size; j++) {
                if (gameData.board[i][j] == 0) {
                    if (emptyPos == randomTileIndex) {
                        gameData.board[i][j] = value;
                        return;
                    }
                    emptyPos++;
                }
            }
        }
    }

    public static int isGameOver(GameData gameData) {
        //倒计时结束
        if (gameData.countdownMode && gameData.endTime - gameData.time <= 0) {
            return 2;
        }
        //赢了的情况
        if (isGameWon(gameData)) {
            return 3;
        }
        for (int i = 0; i < gameData.size; i++) {
            for (int j = 0; j < gameData.size; j++) {
                if (gameData.board[i][j] == 0) {
                    return 0;
                }
                if (i < gameData.size - 1 && gameData.board[i][j] == gameData.board[i + 1][j]) {
                    return 0;
                }
                if (j < gameData.size - 1 && gameData.board[i][j] == gameData.board[i][j + 1]) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public static boolean isGameWon(GameData gameData) {
        for (int i = 0; i < gameData.size; i++) {
            for (int j = 0; j < gameData.size; j++) {
                if (gameData.board[i][j] == gameData.targetNumber) {
                    gameData.win = true;
                    return true;
                }
            }
        }
        return false;
    }
}
