package game.util;

public class TimeUtil {
    public static String formatTime(long seconds) {
        long hours = seconds / 3600;
        long remainingSeconds = seconds % 3600;
        long minutes = remainingSeconds / 60;
        long secs = remainingSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
