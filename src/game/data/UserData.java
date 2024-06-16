package game.data;

import java.io.Serializable;
import java.util.Objects;

public class UserData implements Serializable {
    public String name;
    public String pwd;
    //普通存档
    public GameData[] gameData;

    //todo: 在fullscreen中初始化 临时做法
    public int leave = -1;

    public UserData(String name, String pwd, int dataSize) {
        this.name = name;
        this.pwd = pwd;
        this.gameData = new GameData[dataSize];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserData userData = (UserData) obj;
        return Objects.equals(name, userData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
