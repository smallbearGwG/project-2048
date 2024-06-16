package game;

import game.data.GameData;
import game.data.UserData;
import game.util.GameLogic;
import game.view.*;

import javax.swing.*;
import java.util.*;
import java.util.function.Supplier;


public class GameManager {
    public Game currentGameView;
    public UserData currentUser;
    public GameData currentGame;
    public GameWindow gameWindow;
    public ArchiveManager archiveManager;
    public BGMManager bgmManager;
    public boolean bgmPlay;
    public Stack<GameData> preGames;
    public boolean gameStart;

    public GameManager() {
        archiveManager = new ArchiveManager();
        bgmManager = new BGMManager();

        //check
        System.out.println("开始检查存档...ok!");
        if (!archiveManager.checkAndInitArchive()) {
            System.out.println("存档文件创建失败");
        }
        System.out.println("archives文件夹...ok!");
        List<String> files = archiveManager.getArchiveList();
        System.out.println("检查存档文件...ok!");
        System.out.println("存档文件信息:" + Arrays.toString(files.toArray()));
        //todo:错误处理 需要弹窗
    }

    public void showWindow() {
        gameWindow = new GameWindow(this);
        gameWindow.setVisible(true);
        gameWindow.switchToPanel(Welcome.class);
        bgmManager.play();
        bgmPlay = true;
    }

    public void loadUser(String name) {
        System.out.println("加载用户" + name);
        this.currentUser = archiveManager.loadUser(name);

        this.gameWindow.switchToPanel(Choose.class);
    }

    public void unloadUser() {
        System.out.println("退出用户" + currentUser.name);
        this.currentGame = null;
        this.currentUser = null;

        //退出到Welcome
        this.gameWindow.hideTopBar();
        this.gameWindow.switchToPanel(Welcome.class);

        this.gameWindow.keyListener = false;
    }

    public boolean checkUser() {
        return currentUser != null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //游客模式运行游戏
    public void lightGame() {
        GameData newGame = new GameData(4, 2048);
        GameLogic.initializeGame(newGame);
        Map<String, Supplier<?>> optionFunctions = new HashMap<>();
        optionFunctions.put("quit", () -> {
            this.gameWindow.hideTopBar();
            this.gameWindow.switchToPanel(Welcome.class);
            this.gameWindow.keyListener = false;
            return null;
        });
        this.loadGameAndSwitchToGameView(newGame, optionFunctions);
    }

    /**
     * 加载当前用户存档(点击正常模式)
     */
    public void archiveMode() {
        if (this.currentUser != null) {
            this.gameWindow.switchToPanel(Archive.class);
        } else {
            JOptionPane.showMessageDialog(null, "当前没有用户登入!程序错误");
        }
    }

    /**
     * 关卡模式
     */
    public void leaveMode() {
        if (this.currentUser != null) {
            if (this.currentUser.leave < 0) {
                //需要显示fullscreen
                this.gameWindow.switchToPanel(FullScreen.class);
            } else {
                this.gameWindow.switchToPanel(Leave.class);
            }
        } else {
            JOptionPane.showMessageDialog(null, "当前没有用户登入!程序错误");
        }
    }

    /**
     * diy模式
     */
    public void diyMode() {
        if (this.currentUser != null) {
            this.gameWindow.switchToPanel(Diy.class);
        } else {
            JOptionPane.showMessageDialog(null, "当前没有用户登入!程序错误");
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 所有的游戏加载必须依靠这个方法)
     */
    public void loadGameAndSwitchToGameView(GameData gameData, Map<String, Supplier<?>> optionFunctions) {
        this.currentGame = gameData;
        this.gameWindow.keyListener = true;
        //绑定监听器和view
        this.currentGameView = (Game) this.gameWindow.switchToPanel(Game.class, optionFunctions);
        gameStart = true;
        preGames = new Stack<>();
    }

    /**
     * 保存游戏
     */
    public void saveGame() {
        if (currentUser != null) {
            this.archiveManager.serialize(currentUser);
            gameStart = false;
            this.preGames = null;
        } else {
            JOptionPane.showMessageDialog(null, "没有用户登入 无法保存");
        }
    }

    /**
     * 游戏的主要逻辑
     *
     * @param directionCode
     */
    public void doGame(int directionCode) {
        if (GameLogic.isGameOver(currentGame) > 0) return;
        if (currentGameView != null) {
            System.out.println("do GAME code:" + directionCode);
            //深度克隆
            this.preGames.push(this.currentGame.clone());
            switch (directionCode) {
                case 0:
                    GameLogic.moveUp(currentGame);
                    break;
                case 1:
                    GameLogic.moveDown(currentGame);
                    break;
                case 2:
                    GameLogic.moveLeft(currentGame);
                    break;
                case 3:
                    GameLogic.moveRight(currentGame);
                    break;
            }
            this.currentGame.steps++;
            this.currentGame.printBoard();
            this.currentGameView.drawGameByGame(currentGame);

            // Check for win condition
            if (GameLogic.isGameWon(currentGame)) {
                JOptionPane.showMessageDialog(null, "恭喜！你达到了目标数字 " + currentGame.targetNumber + "，你赢了！");
            }
        } else {
            JOptionPane.showMessageDialog(null, "游戏未正确加载！发生未知错误");
        }
    }

    /**
     * 获取上一步
     */
    public void doPrev() {
        if (!preGames.isEmpty()) {
            this.currentGame = preGames.pop();
            this.currentGame.printBoard();
            this.currentGameView.drawGameByGame(currentGame);
        } else {
            JOptionPane.showMessageDialog(null, "没有上一步操作可以撤销");
        }
    }
}