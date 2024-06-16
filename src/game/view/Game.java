package game.view;

import game.GameWindow;
import game.component.FancyButton;
import game.component.GridComponent;
import game.data.GameData;
import game.util.GameAI;
import game.util.GameLogic;
import game.util.TimeUtil;

import javax.swing.*;
import java.awt.*;

public class Game extends BasicView {
    public JLabel stepInfo;
    public JLabel scopeInfo;
    public JLabel timerLabel;
    private Timer timer;

    public Game(GameWindow game) {
        super(game);
    }

    @Override
    public void viewLayoutSetting() {
        GameData gameData = this.gameWindow.gameManager.currentGame;
        drawGameByGame(gameData);

        //计时器启动
        timer = new Timer(1000, e -> {
            if (GameLogic.isGameOver(gameData) <= 0) {
                gameData.time++;
                timerLabel.setText("时间: " + TimeUtil.formatTime(gameData.showTime()));
            } else {
                if (GameLogic.isGameOver(gameData) == 1) {
                    JOptionPane.showMessageDialog(null, "你输了");
                }
                if (GameLogic.isGameOver(gameData) == 2) {
                    JOptionPane.showMessageDialog(null, "倒计时结束 你输了");
                }
                timer.stop();
            }
        });
        if (GameLogic.isGameOver(gameData) <= 0) timer.start();
    }

    public JPanel replaceTopBar() {
        JPanel topContainer = new JPanel();
        topContainer.setOpaque(false);
        JLabel userInfo = new JLabel("游戏用户:" + (gameWindow.gameManager.currentUser == null ? "light" : gameWindow.gameManager.currentUser.name));

        JButton exit = new FancyButton("返回");
        exit.setFocusable(false);
        //返回方法
        exit.addActionListener(e -> {
            //定时器停止
            timer.stop();
            this.optionFunctions.get("quit").get();
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

    public void drawGameByGame(GameData gameData) {
        // 清除旧的游戏面板和信息面板
        this.removeAll();

        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setBounds(50, 50, 600, 600);
        gamePanel.setLayout(new GridLayout(gameData.size, gameData.size));

        for (int i = 0; i < gameData.size; i++) {
            for (int j = 0; j < gameData.size; j++) {
                int number = gameData.board[i][j];
                GridComponent button = new GridComponent(number);
                gamePanel.add(button);
            }
        }
        this.add(gamePanel);

        stepInfo = new JLabel("Score: " + gameData.score);
        stepInfo.setFont(new Font("", Font.BOLD, 20));
        stepInfo.setForeground(new Color(0, 0, 0));

        scopeInfo = new JLabel("Step: " + gameData.steps);
        scopeInfo.setFont(new Font("", Font.BOLD, 20));
        scopeInfo.setForeground(new Color(255, 0, 0));

        timerLabel = new JLabel("时间: " + TimeUtil.formatTime(gameData.showTime()));
        timerLabel.setFont(new Font("", Font.BOLD, 20));
        timerLabel.setForeground(new Color(151, 137, 0));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setBounds(700, 50, 200, 250);
        infoPanel.setLayout(new GridLayout(5, 1));

        infoPanel.add(stepInfo);
        infoPanel.add(scopeInfo);
        infoPanel.add(timerLabel);
        if (!gameData.countdownMode) {
            JButton prevButton = new FancyButton("上一步");
            prevButton.setFocusable(false);
            prevButton.addActionListener(e -> {
                this.gameWindow.gameManager.doPrev();
            });
            infoPanel.add(prevButton);

            JButton aiButton = new FancyButton("AI行动");
            aiButton.addActionListener(e -> {
                int code = GameAI.findBestMove(gameData);
                switch (code) {
                    case 0 -> this.gameWindow.gameManager.doGame(0);
                    case 1 -> this.gameWindow.gameManager.doGame(1);
                    case 2 -> this.gameWindow.gameManager.doGame(2);
                    case 3 -> this.gameWindow.gameManager.doGame(3);
                }
            });
            aiButton.setFocusable(false);
            infoPanel.add(aiButton);
        }
        this.add(infoPanel);

        //上下左右按钮
        JButton upButton = new FancyButton("↑");
        JButton downButton = new FancyButton("↓");
        JButton leftButton = new FancyButton("←");
        JButton rightButton = new FancyButton("→");

        upButton.setBounds(800, 480, 60, 60);
        upButton.addActionListener(e -> this.gameWindow.gameManager.doGame(0));
        upButton.setFocusable(false);
        this.add(upButton);

        downButton.setBounds(800, 540, 60, 60);
        downButton.addActionListener(e -> this.gameWindow.gameManager.doGame(1));
        downButton.setFocusable(false);
        this.add(downButton);

        leftButton.setBounds(740, 540, 60, 60);
        leftButton.addActionListener(e -> this.gameWindow.gameManager.doGame(2));
        leftButton.setFocusable(false);
        this.add(leftButton);

        rightButton.setBounds(860, 540, 60, 60);
        rightButton.addActionListener(e -> this.gameWindow.gameManager.doGame(3));
        rightButton.setFocusable(false);
        this.add(rightButton);

        // 刷新界面
        this.revalidate();
        this.repaint();
    }
}
