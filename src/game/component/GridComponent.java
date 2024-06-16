package game.component;


import javax.swing.*;
import java.awt.*;

public class GridComponent extends JComponent {
    static Font font = new Font("", Font.BOLD, 42);
    public int number;

    public GridComponent(int number) {
        this.number = number;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int arcWidth = 20;
        int arcHeight = 20;
        int borderThickness = 5;

        if (number > 0) {
            g2d.setColor(Color.white);
            g2d.fillRoundRect(borderThickness, borderThickness, getWidth() - 2 * borderThickness, getHeight() - 2 * borderThickness, arcWidth, arcHeight);
            g2d.setColor(getColorByNumber(number));
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
            int textWidth = metrics.stringWidth(String.valueOf(number));
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g2d.drawString(String.valueOf(number), x, y);
        } else {
            g2d.setColor(Color.white);
            g2d.fillRoundRect(borderThickness, borderThickness, getWidth() - 2 * borderThickness, getHeight() - 2 * borderThickness, arcWidth, arcHeight);
        }

        g2d.setColor(getColorByNumber(number));
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.drawRoundRect(borderThickness / 2, borderThickness / 2, getWidth() - borderThickness, getHeight() - borderThickness, arcWidth, arcHeight);

        g2d.dispose();
    }

    public Color getColorByNumber(int number) {
        return switch (number) {
            case 2 -> new Color(255, 20, 20);
            case 4 -> new Color(255, 180, 145);
            case 8 -> new Color(65, 105, 225);
            case 16 -> new Color(0, 155, 125);
            case 32 -> new Color(255, 127, 80);
            case 64 -> new Color(255, 69, 0);
            case 128 -> new Color(185, 138, 130);
            case 256 -> new Color(238, 203, 173);
            case 512 -> new Color(0, 0, 128);
            case 1024 -> new Color(0, 0, 139);
            case 2048 -> new Color(138, 43, 226);
            default -> new Color(209, 209, 209);
        };
    }
}
