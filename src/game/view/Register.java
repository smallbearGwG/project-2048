package game.view;

import game.GameWindow;
import game.component.FancyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Register extends BasicView {

    public Register(GameWindow game) {
        super(game);
    }


    @Override
    public void viewLayoutSetting() {
        this.setLayout(new BorderLayout());

        // 创建顶部面板，使用FlowLayout从左到右排列
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        // 创建返回上一级按钮
        JButton backButton = new FancyButton("返回");
        backButton.setPreferredSize(new Dimension(100, 30)); // 设置返回按钮的大小
        backButton.addActionListener((ActionEvent e) -> {
            // 切换到上一级面板
            this.gameWindow.switchToPanel(Welcome.class); // 替换 "PreviousPanelName" 为实际的面板名称
        });
        topPanel.add(backButton);
        this.add(topPanel, BorderLayout.NORTH);

        // 创建中心面板，使用GridBagLayout来布局组件x
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 设置组件之间的间距

        // 创建并添加用户名标签和输入框
        JLabel usernameLabel = new JLabel("用户名:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(usernameField, gbc);

        // 创建并添加密码标签和输入框
        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(passwordField, gbc);

        // 创建并添加注册按钮
        JButton registerButton = new FancyButton("注册");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(registerButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        registerButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().toLowerCase();
            String password = new String(passwordField.getPassword()).toLowerCase();

            // 简单的打印输入的信息，可以根据需要进行处理
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            boolean check = true;
            do {
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "用户名和密码不能为空");
                    check = false;
                    break;
                }
                if (!isValidUsername(username)) {
                    JOptionPane.showMessageDialog(getParent(), "用户名只能包含字母和数字，长度为3到15个字符");
                    check = false;
                    break;
                }
                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(getParent(), "密码必须包含至少一个字母、一个数字，长度为6到20个字符");
                    check = false;
                    break;
                }
                //检查重复
                if (this.gameWindow.gameManager.archiveManager.checkUserExist(username)) {
                    JOptionPane.showMessageDialog(getParent(), username + "用户名重复, 请换一个名字注册");
                    check = false;
                }
            } while (false);

            passwordField.setText("");
            if (check) {
                usernameField.setText("");

                this.gameWindow.gameManager.archiveManager.createUser(username, password);
                JOptionPane.showMessageDialog(getParent(), "注册成功,你的用户名是" + username);
                this.gameWindow.switchToPanel(Welcome.class);
            }
        });
    }

    @Override
    public JPanel replaceTopBar() {
        return null;
    }

    private boolean isValidUsername(String username) {
        // 用户名：只能包含字母和数字，长度为3到15个字符
        return username.matches("^[a-zA-Z0-9]{3,15}$");
    }

    private boolean isValidPassword(String password) {
        // 密码：必须包含至少一个字母、一个数字，长度为6到20个字符
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$");
    }
}
