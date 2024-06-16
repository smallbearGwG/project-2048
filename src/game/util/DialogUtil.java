package game.util;

import javax.swing.*;

public class DialogUtil {

    public static int[] chooseNewGameSizeAndTarget() {
        return chooseNewGameSizeAndTarget(false);
    }

    public static int[] chooseNewGameSizeAndTarget(boolean flag) {
        // Options for game board size
        String[] sizeOptions = {"6x6", "5x5", "4x4"};
        int sizeChoice = JOptionPane.showOptionDialog(null, "选择游戏板大小:", "游戏设置", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, sizeOptions, sizeOptions[0]);
        int size = switch (sizeChoice) {
            case 0 -> 6;
            case 1 -> 5;
            case 2 -> 4;
            default -> 6;
        };
        int target = 2048;
        if (flag) {
            String[] targetOptions = {"2048", "1024", "512", "256", "128"};
            int targetChoice = JOptionPane.showOptionDialog(null, "选择目标数字:", "游戏设置", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, targetOptions, targetOptions[0]);
            target = switch (targetChoice) {
                case 0 -> 2048;
                case 1 -> 1024;
                case 2 -> 512;
                case 3 -> 256;
                case 4 -> 128;
                default -> 2048;
            };
        }

        return new int[]{size, target};
    }
}
