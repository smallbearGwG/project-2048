package game.view;


import game.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FullScreen extends BasicView {
    private final List<ImageIcon> slides = new ArrayList<>();
    private int currentSlideIndex;

    public FullScreen(GameWindow game) {
        super(game);
        this.slides.add(new ImageIcon("resource/image/storeMode/leave1.gif"));
        this.slides.add(new ImageIcon("resource/image/storeMode/leave2.jpg"));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("fullScreen 鼠标按下");
                nextSlide();
            }
        });
        this.gameWindow.hideTopBar();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (slides != null && !slides.isEmpty()) {
            g.drawImage(slides.get(currentSlideIndex).getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void nextSlide() {
        currentSlideIndex++;
        if (currentSlideIndex >= slides.size()) {
            currentSlideIndex = 0;
            this.gameWindow.gameManager.currentUser.leave = 0;
            this.gameWindow.gameManager.saveGame();
            //todo:
            this.gameWindow.showBar();
            this.gameWindow.switchToPanel(Leave.class);
        }
        repaint();
    }

}
