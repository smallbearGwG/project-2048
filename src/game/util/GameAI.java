package game.util;

import game.data.GameData;

import java.util.HashMap;

public class GameAI {

    private static final HashMap<Integer, int[][]> weights = new HashMap<>();

    static {
        weights.put(4, new int[][]{{2, 1, 0, 0}, {4, 2, 1, 0}, {8, 4, 2, 1}, {16, 8, 4, 2}});

        weights.put(5, new int[][]{{2, 1, 0, 0, 0}, {4, 2, 1, 0, 0}, {8, 4, 2, 1, 0}, {16, 8, 4, 2, 1}, {32, 16, 8, 4, 2}});

        weights.put(6, new int[][]{{2, 1, 0, 0, 0, 0}, {4, 2, 1, 0, 0, 0}, {8, 4, 2, 1, 0, 0}, {16, 8, 4, 2, 1, 0}, {32, 16, 8, 4, 2, 1}, {64, 32, 16, 8, 4, 2}});
    }

    public static int findBestMove(GameData gameData) {
        int bestMove = 0;
        int maxScore = Integer.MIN_VALUE;

        for (int code : new int[]{0, 1, 2, 3}) {
            GameData clonedData = gameData.clone();
            boolean moved = switch (code) {
                case 0 -> GameLogic.moveUp(clonedData);
                case 1 -> GameLogic.moveDown(clonedData);
                case 2 -> GameLogic.moveLeft(clonedData);
                case 3 -> GameLogic.moveRight(clonedData);
                default -> false;
            };
            if (moved) {
                int score = evaluateGameData(clonedData);
                if (score > maxScore) {
                    maxScore = score;
                    bestMove = code;
                }
            }
        }
        return bestMove;
    }

    private static int evaluateGameData(GameData gameData) {
        int score = 0;
        for (int i = 0; i < gameData.size; i++) {
            for (int j = 0; j < gameData.size; j++) {
                score += gameData.board[i][j] * weights.get(gameData.size)[i][j];
            }
        }
        return score;
    }
}
