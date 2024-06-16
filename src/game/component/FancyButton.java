package game.component;

import javax.swing.*;
import java.awt.*;

// 自定义按钮类
public class FancyButton extends JButton {

    public FancyButton(String text) {
        super(text);
        setPreferredSize(new Dimension(150, 25)); // 设置按钮尺寸
        setFont(new Font("", Font.BOLD, 16));
        setForeground(Color.BLACK);
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
    }

    // 绘制圆角矩形按钮外观
    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque() && (getModel().isPressed() || getModel().isSelected())) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // 绘制圆角矩形边框
    }
}
