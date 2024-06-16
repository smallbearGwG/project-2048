package game.view;

import game.GameWindow;
import game.component.FancyButton;
import game.data.GameData;
import game.util.DialogUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Diy extends BasicView {
    public int size;
    public GameData gameData;
    public FancyButton[][] buttons;

    public Diy(GameWindow game) {
        super(game);
    }

    @Override
    public void viewLayoutSetting() {
        int[] result = DialogUtil.chooseNewGameSizeAndTarget();
        this.size = result[0];
        gameData = new GameData(size, 2048);
        buttons = new FancyButton[size][size];
        this.drawGameByGame(gameData);
    }

    public void drawGameByGame(GameData gameData) {
        this.removeAll();
        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setBounds(50, 50, 600, 600);
        gamePanel.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new FancyButton(gameData.board[i][j] + "");
                buttons[i][j].setFont(new Font("", Font.BOLD, 30));
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(e -> {
                    gameData.board[finalI][finalJ] = changeNumber(gameData.board[finalI][finalJ]);
                    buttons[finalI][finalJ].setText(gameData.board[finalI][finalJ] + "");
                });
                gamePanel.add(buttons[i][j]);
            }
        }
        this.add(gamePanel);

        JButton start = new FancyButton("开始游戏");
        start.setBounds(700, 300, 250, 60);
        start.setFocusable(false);
        start.addActionListener(e -> {
            Map<String, Supplier<?>> optionFunctions = new HashMap<>();
            optionFunctions.put("quit", () -> {
                this.gameWindow.switchToPanel(Choose.class);
                return null;
            });
            this.gameWindow.gameManager.loadGameAndSwitchToGameView(this.gameData, optionFunctions);
        });
        this.add(start);
    }

    public JPanel replaceTopBar() {
        JPanel topContainer = new JPanel();
        topContainer.setOpaque(false);
        JLabel userInfo = new JLabel("用户:" + (gameWindow.gameManager.currentUser == null ? "light" : gameWindow.gameManager.currentUser.name));

        JButton exit = new FancyButton("返回");
        exit.setFocusable(false);
        //返回方法
        exit.addActionListener(e -> {
            this.gameWindow.switchToPanel(Choose.class);
        });

        JButton toggleMusicButton = new FancyButton(this.gameWindow.gameManager.bgmPlay ? "关闭音乐" : "开启音乐");
        toggleMusicButton.setFocusable(false);
        //播放声音按钮
        toggleMusicButton.addActionListener(e -> {
            if (this.gameWindow.gameManager.bgmPlay) {
                this.gameWindow.gameManager.bgmManager.stop();
                this.gameWindow.gameManager.bgmPlay = false;
                toggleMusicButton.setText("开启音乐");
            } else {
                this.gameWindow.gameManager.bgmManager.play();
                this.gameWindow.gameManager.bgmPlay = true;
                toggleMusicButton.setText("关闭音乐");
            }
        });

        topContainer.add(userInfo);
        topContainer.add(toggleMusicButton);
        topContainer.add(exit);

        return topContainer;
    }

    public int changeNumber(int number) {
        if (number == 0) return 2;
        if (number >= 512) return 0;
        return number * 2;
    }
}
