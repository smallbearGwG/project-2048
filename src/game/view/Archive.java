package game.view;

import game.GameWindow;
import game.component.FancyButton;
import game.data.GameData;
import game.data.UserData;
import game.util.DialogUtil;
import game.util.GameLogic;
import game.util.ImageUtil;
import game.util.TimeUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Archive extends BasicView {
    public UserData userData;

    public Archive(GameWindow game) {
        super(game);
    }

    @Override
    public void viewLayoutSetting() {
        this.userData = this.gameWindow.gameManager.currentUser;
        drawSaveBySave(this.userData);
    }

    public void drawSaveBySave(UserData userData) {
        this.removeAll();

        JPanel panel = new JPanel();
        panel.setBounds(60, 100, 800, 500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        for (int i = 0; i < 8; i++) {
            JPanel saveSlot = new JPanel();
            saveSlot.setOpaque(false);
            saveSlot.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // 设置间距为10像素
            int lambdaI = i;

            GameData save = userData.gameData[i];
            JLabel numberLabel = new JLabel("ID " + (i + 1) + ":");
            saveSlot.add(numberLabel);
            if (save != null) {
                //游戏时间
                JLabel timeLabel = new JLabel("time:" + TimeUtil.formatTime(save.showTime()));
                saveSlot.add(timeLabel);

                JLabel sizeLabel = new JLabel("size:" + save.size + "*" + save.size);
                saveSlot.add(sizeLabel);

                //step
                JLabel stepsLabel = new JLabel("steps:" + save.steps);
                saveSlot.add(stepsLabel);

                //分数
                JLabel scoreLabel = new JLabel("score:" + save.score);
                saveSlot.add(scoreLabel);

                //
                JButton loadButton = new FancyButton("加载");
                loadButton.addActionListener(e -> {
//                    JOptionPane.showMessageDialog(null, "加载存档");
                    GameData needLoadData = userData.gameData[lambdaI];
                    Map<String, Supplier<?>> optionFunctions = new HashMap<>();
                    optionFunctions.put("quit", () -> {
                        this.gameWindow.gameManager.saveGame();
                        this.gameWindow.switchToPanel(Archive.class);
                        return null;
                    });
                    this.gameWindow.gameManager.loadGameAndSwitchToGameView(needLoadData, optionFunctions);
                });
                saveSlot.add(loadButton);

                //
                JButton deleteButton = new FancyButton("删除");
                deleteButton.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(null, "确认删除存档 " + lambdaI + " 吗？", "删除存档", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        userData.gameData[lambdaI] = null;
                        this.gameWindow.gameManager.archiveManager.serialize(userData);
                        //删除完了后要刷新
                        drawSaveBySave(userData);
                    }
                });
                saveSlot.add(deleteButton);
            } else {
                // 存档时间
                JButton newGame = new FancyButton("开始新游戏");
                newGame.addActionListener(e -> {
                    int gameChoose[] = DialogUtil.chooseNewGameSizeAndTarget(true);
                    GameData gameData = new GameData(gameChoose[0], gameChoose[1]);
                    GameLogic.initializeGame(gameData);
                    Map<String, Supplier<?>> optionFunctions = new HashMap<>();
                    optionFunctions.put("quit", () -> {
                        //这里需要保存游戏
                        userData.gameData[lambdaI] = gameData;
                        this.gameWindow.gameManager.saveGame();
                        this.gameWindow.switchToPanel(Archive.class);
                        return null;
                    });

                    this.gameWindow.gameManager.loadGameAndSwitchToGameView(gameData, optionFunctions);
                });
                saveSlot.add(newGame);
            }

            // 添加到容器中
            panel.add(saveSlot);
        }
        this.add(panel);

        JButton backButton = new JButton(ImageUtil.createScaledIcon("resource/image/storeMode/back.jpg", 130, 80));
        backButton.setBounds(10, 10, 130, 80);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            this.gameWindow.switchToPanel(Choose.class);
        });
        this.add(backButton);

        // 更新界面
        this.revalidate();
        this.repaint();
    }
}
