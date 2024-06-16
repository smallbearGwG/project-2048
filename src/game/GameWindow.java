package game;

import game.view.BasicView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.function.Supplier;

public class GameWindow extends JFrame {
    public final GameManager gameManager;
    public Class currentViewName;
    private JPanel viewPanel;
    private JPanel mainPanel;
    private JPanel topPanel;
    public boolean keyListener;

    public GameWindow(GameManager gameManager) {
        this.gameManager = gameManager;

        this.setSize(1027, 768);
        this.setTitle("2048 V1.0");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);

        viewPanel = new JPanel(new CardLayout());
        mainPanel.add(viewPanel, BorderLayout.CENTER);

        topPanel = new JPanel();
        topPanel.setVisible(false);
        topPanel.setBackground(new Color(234, 234, 234));
        this.add(topPanel, BorderLayout.NORTH);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (keyListener) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            System.out.println("按下上方向键");
                            gameManager.doGame(0);
                            break;
                        case KeyEvent.VK_DOWN:
                            System.out.println("按下下方向键");
                            gameManager.doGame(1);
                            break;
                        case KeyEvent.VK_LEFT:
                            System.out.println("按下左方向键");
                            gameManager.doGame(2);
                            break;
                        case KeyEvent.VK_RIGHT:
                            System.out.println("按下右方向键");
                            gameManager.doGame(3);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    public BasicView switchToPanel(Class<?> viewClassName) {
        return switchToPanel(viewClassName, null);
    }

    public BasicView switchToPanel(Class<?> viewClassName, Map<String, Supplier<?>> optionFunctions) {
        viewPanel.removeAll();
        try {
            viewPanel.removeAll();
            BasicView basicView = (BasicView) viewClassName.getDeclaredConstructor(GameWindow.class).newInstance(this);
            basicView.optionFunctions = optionFunctions;
            viewPanel.add(basicView);
            viewPanel.revalidate();
            viewPanel.repaint();
            //fix key listener
            this.requestFocusInWindow();
            currentViewName = viewClassName;
            return basicView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void replaceAndShowTopBar(JPanel container) {
        topPanel.removeAll();
        topPanel.add(container);
        topPanel.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void hideTopBar() {
        topPanel.setVisible(false);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showBar() {
        topPanel.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
