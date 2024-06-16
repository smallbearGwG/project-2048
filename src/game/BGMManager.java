package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BGMManager {
    private Clip clip;

    public BGMManager() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resource/bgm.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0); // 确保从头开始播放
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.flush(); // 清除当前音频数据
            clip.setFramePosition(0); // 重置播放位置
        }
    }
}
