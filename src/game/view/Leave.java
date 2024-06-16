package game.view;

import game.GameWindow;
import game.component.FancyButton;
import game.data.GameData;
import game.util.GameLogic;
import game.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Leave extends BasicView {

    public Leave(GameWindow game) {
        super(game);
    }

    @Override
    public void viewLayoutSetting() {
        JButton backButton = new JButton(ImageUtil.createScaledIcon("resource/image/storeMode/back.jpg", 130, 80));
        backButton.setBounds(10, 10, 130, 80);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            this.gameWindow.switchToPanel(Choose.class);
        });
        this.add(backButton);

        JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        levelPanel.setBounds(40, 80, 200, 300);
        levelPanel.setOpaque(false);

        levelPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int userLeave = this.gameWindow.gameManager.currentUser.leave;
        JButton level1Button = createLevelButton("关卡 1");
        level1Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "leave1:2分钟内达成64");
            GameData newGame = new GameData(4, 64, 60, true);
            Map<String, Supplier<?>> optionFunctions = new HashMap<>();
            optionFunctions.put("quit", () -> {
                if (this.gameWindow.gameManager.currentGame.win) {
                    if (this.gameWindow.gameManager.currentUser.leave < 1) {
                        this.gameWindow.gameManager.currentUser.leave++;
                        this.gameWindow.gameManager.saveGame();
                    }
                }
                this.gameWindow.switchToPanel(Leave.class);
                return null;
            });
            GameLogic.initializeGame(newGame);
            this.gameWindow.gameManager.loadGameAndSwitchToGameView(newGame, optionFunctions);
        });

        JButton level2Button = createLevelButton("关卡 2");
        if (userLeave < 1) {
            level2Button.setEnabled(false);
        }
        level2Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "leave2:2分钟内达成128");
            GameData newGame = new GameData(4, 128, 120, true);
            Map<String, Supplier<?>> optionFunctions = new HashMap<>();
            optionFunctions.put("quit", () -> {
                if (this.gameWindow.gameManager.currentGame.win) {
                    if (this.gameWindow.gameManager.currentUser.leave < 2) {
                        this.gameWindow.gameManager.currentUser.leave++;
                        this.gameWindow.gameManager.saveGame();
                    }
                }
                this.gameWindow.switchToPanel(Leave.class);
                return null;
            });
            GameLogic.initializeGame(newGame);
            this.gameWindow.gameManager.loadGameAndSwitchToGameView(newGame, optionFunctions);
        });

        JButton level3Button = createLevelButton("关卡 3");
        if (userLeave < 2) {
            level3Button.setEnabled(false);
        }
        level3Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "leave3:1分钟内达成128");
            GameData newGame = new GameData(4, 128, 60, true);
            Map<String, Supplier<?>> optionFunctions = new HashMap<>();
            optionFunctions.put("quit", () -> {
                if (this.gameWindow.gameManager.currentGame.win) {
                    if (this.gameWindow.gameManager.currentUser.leave < 3) {
                        this.gameWindow.gameManager.currentUser.leave++;
                        this.gameWindow.gameManager.saveGame();
                    }
                }
                this.gameWindow.switchToPanel(Leave.class);
                return null;
            });
            GameLogic.initializeGame(newGame);
            this.gameWindow.gameManager.loadGameAndSwitchToGameView(newGame, optionFunctions);
        });

        JButton level4Button = createLevelButton("关卡 4");
        if (userLeave < 3) {
            level4Button.setEnabled(false);
        }
        level4Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "leave4:1分钟内达成128");
            GameData newGame = new GameData(4, 128, 60, true);
            Map<String, Supplier<?>> optionFunctions = new HashMap<>();
            optionFunctions.put("quit", () -> {
                if (this.gameWindow.gameManager.currentGame.win) {
                    if (this.gameWindow.gameManager.currentUser.leave < 3) {
                        this.gameWindow.gameManager.currentUser.leave++;
                        this.gameWindow.gameManager.saveGame();
                    }
                }
                this.gameWindow.switchToPanel(FinalScreen.class);
                return null;
            });
            GameLogic.initializeGame(newGame);
            this.gameWindow.gameManager.loadGameAndSwitchToGameView(newGame, optionFunctions);
        });

        levelPanel.add(level1Button);
        levelPanel.add(level2Button);
        levelPanel.add(level3Button);
        levelPanel.add(level4Button);

        this.add(levelPanel);
    }

    private JButton createLevelButton(String levelName) {
        JButton button = new FancyButton(levelName);
        button.setFont(new Font("", Font.BOLD, 24)); // Increase font size
        button.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        return button;
    }
}
