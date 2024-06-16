package game.util;

import javax.swing.*;
import java.awt.*;

public class ImageUtil {
    public static ImageIcon createScaledIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
