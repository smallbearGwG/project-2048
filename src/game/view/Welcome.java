package game.view;

import game.GameWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Welcome extends BasicView {

    public JButton login;
    public JButton register;
    public JButton light;

    public Welcome(GameWindow game) {
        super(game);
    }

    @Override
    public void viewLayoutSetting() {
        JLabel title = new JLabel(new ImageIcon("resource/image/welcome/GAMETITLE.png"));
        title.setBounds(20, 20, 548, 257);

        login = new JButton(new ImageIcon("resource/image/welcome/login_JB.png"));
        login.setBounds(100, 300, 200, 75);
        login.setBorderPainted(false);
        login.setContentAreaFilled(false);

        register = new JButton(new ImageIcon("resource/image/welcome/register_JB.png"));
        register.setBounds(100, 400, 200, 75);
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);

        light = new JButton(new ImageIcon("resource/image/welcome/light_JB.png"));
        light.setBounds(100, 500, 200, 75);
        light.setBorderPainted(false);
        light.setContentAreaFilled(false);

        this.add(title);
        this.add(login);
        this.add(register);
        this.add(light);

        login.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameWindow.switchToPanel(Login.class);
            }
        });
        register.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameWindow.switchToPanel(Register.class);
            }
        });
        light.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameWindow.gameManager.lightGame();
            }
        });
    }

    @Override
    public JPanel replaceTopBar() {
        return null;
    }
}