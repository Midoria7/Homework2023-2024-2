package javaChatRoom.server.serverLogger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return String.format("[%1$s] %2$tF %2$tT %3$s: %4$s %n",
                getLeverName(record.getLevel().getName()),
                new java.util.Date(record.getMillis()),
                getShortClassName(record.getSourceClassName()) + " " + record.getSourceMethodName(),
                record.getMessage());
    }

    private String getShortClassName(String className) {
        String[] parts = className.split("\\.");
        return parts[parts.length - 1];
    }

    private String getLeverName(String level) {
        if (level.equals("SEVERE")) {
            return "ERROR";
        } else if (level.equals("FINE")) {
            return "DEBUG";
        } else {
            return level;
        }
    }
}
