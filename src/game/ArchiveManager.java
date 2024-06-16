package game;

import game.data.UserData;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArchiveManager {
    private static final String SAVE_FILE_NAME = ".sav";
    private static final int SAVE_SIZE = 20;
    public String archiveDirPath = Paths.get("").toAbsolutePath().toString() + File.separator + "archives";
    Pattern pattern = Pattern.compile(".*\\.sav$");

    public ArchiveManager() {
    }

    public boolean checkAndInitArchive() {
        File archiveDir = new File(archiveDirPath);
        if (!archiveDir.exists()) {
            return archiveDir.mkdirs();
        }
        return true;
    }

    public boolean checkUserExist(String name) {
        List<String> users = getArchiveList();
        System.out.println(users);
        if (users != null) {
            for (String user : users) {
                if (user.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLogin(String name, String pwd) {
        UserData data = deserialize(name);
        return Objects.equals(data.pwd, pwd);
    }

    public List<String> getArchiveList() {
        List<String> userList = new ArrayList<>();
        if (checkAndInitArchive()) {
            File archiveDir = new File(archiveDirPath);
            File[] files = archiveDir.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile()) {
                    Matcher matcher = pattern.matcher(file.getName());
                    if (matcher.matches()) {
                        userList.add(file.getName().replace(SAVE_FILE_NAME, ""));
                    }
                }
            }
            return userList;
        }
        return null;
    }

    public UserData createUser(String name, String pwd) {
        UserData data = new UserData(name, pwd, SAVE_SIZE);
        this.serialize(data);
        return data;
    }

    public UserData loadUser(String name) {
        return this.deserialize(name);
    }

    public String getUserFilePath(UserData userData) {
        return archiveDirPath + File.separator + userData.name + SAVE_FILE_NAME;
    }

    public void serialize(UserData userData) {
        String fileName = this.getUserFilePath(userData);
        try (FileOutputStream fileOut = new FileOutputStream(fileName); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(userData);
            System.out.println("Serialized data is saved in " + fileName);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public UserData deserialize(String fileName) {
        fileName = archiveDirPath + File.separator + fileName + SAVE_FILE_NAME;
        UserData userData = null;
        try (FileInputStream fileIn = new FileInputStream(fileName); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            userData = (UserData) in.readObject();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return userData;
    }
}
