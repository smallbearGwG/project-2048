package game.view;

import game.GameWindow;
import game.util.ImageUtil;

import javax.swing.*;

public class Choose extends BasicView {
    public Choose(GameWindow game) {
        super(game);
    }

    @Override
    public void viewLayoutSetting() {
        JButton leave = new JButton(ImageUtil.createScaledIcon("resource/image/choose/Storymode.png", 300, 150));
        leave.setBounds(100, 50, 300, 150);
        leave.setBorderPainted(false);
        leave.setContentAreaFilled(false);

        JButton normal = new JButton(ImageUtil.createScaledIcon("resource/image/choose/Normal.png", 300, 150));
        normal.setBounds(100, 230, 300, 150);
        normal.setBorderPainted(false);
        normal.setContentAreaFilled(false);

        JButton diy = new JButton(ImageUtil.createScaledIcon("resource/image/choose/DIY.png", 300, 150));
        diy.setBounds(100, 400, 300, 150);
        diy.setBorderPainted(false);
        diy.setContentAreaFilled(false);

        this.add(leave);
        this.add(normal);
        this.add(diy);

        //正常模式(跳转存档界面)
        normal.addActionListener(e -> {
            this.gameWindow.gameManager.archiveMode();
        });

        //关卡模式下的游戏启动
        leave.addActionListener(e -> {
            this.gameWindow.gameManager.leaveMode();
        });

        //diy模式下的游戏启动
        diy.addActionListener(e -> {
            this.gameWindow.gameManager.diyMode();
        });
    }
}
