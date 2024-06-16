package game.view;

import game.GameWindow;
import game.component.FancyButton;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.function.Supplier;

public class BasicView extends JPanel {
    public Map<String, Supplier<?>> optionFunctions;
    public GameWindow gameWindow;

    public BasicView(GameWindow game) {
        this.gameWindow = game;
        this.setLayout(null);
        this.viewLayoutSetting();

        JPanel topContainer = this.replaceTopBar();
        if (topContainer != null) this.gameWindow.replaceAndShowTopBar(topContainer);
    }

    public void viewLayoutSetting() {
    }

    public JPanel replaceTopBar() {
        //顶部替换
        //顶部内容替换
        JPanel topContainer = new JPanel();
        topContainer.setOpaque(false);
        JLabel userInfo = new JLabel("用户:" + this.gameWindow.gameManager.currentUser.name);
        topContainer.add(userInfo);
        JButton exitButton = new FancyButton("退出");

        //退出按钮
        exitButton.addActionListener((e) -> this.gameWindow.gameManager.unloadUser());

        JButton toggleMusicButton = new FancyButton(this.gameWindow.gameManager.bgmPlay ? "关闭音乐" : "开启音乐");
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
        JButton helpButton = new FancyButton("帮助");
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(getParent(), "<Chapter 0>\n" + "Once,there was an arrival…\n" + "A bang which gave birth to time and space\n" + "Once, there was an arrival…\n" + "A bang which set a planet spinning in that space \n" + "Once,there was an arrival…\n" + "A bang which gave rise to life as we know it\n" + "\n" + "And then came the next ......\n" + "\n" + "<Intro to Game 2048 (shown on CRT screen)>\n" + "2048 is a highly addictive and challenging puzzle game where\n" + "the objective is to slide numbered tiles on a grid to combine\n" + "them and create a tile with the number 2048. The game starts\n" + "with two tiles featuring the numbers 2 and 4, randomly placed\n" + "on a 4x4 grid. Players must swipe to move all tiles on the grid\n" + "in the chosen direction. Tiles with the same number will merge\n" + "into one with a value equal to the sum of the two tiles.\n" + "The game continues until the 2048 tile is created or the grid\n" + "is filled up and no more moves are possible.");
        });

        topContainer.add(toggleMusicButton);
        topContainer.add(helpButton);
        topContainer.add(exitButton);

        return topContainer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = new ImageIcon("resource/image/welcome/BackGround1.gif");
        g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
