package javaChatRoom.server.serverLogger;

import java.io.IOException;
import java.util.logging.*;
import javax.swing.JTextArea;

public class ServerLogger {
    private static final Logger LOGGER = Logger.getLogger(ServerLogger.class.getName());
    private static JTextArea logArea;

    static {
        setupLoggers();
    }

    public static void setLogArea(JTextArea logArea) {
        ServerLogger.logArea = logArea;
    }
    private static void setupLoggers() {
        LOGGER.setUseParentHandlers(false);
        CustomFormatter customFormatter = new CustomFormatter();

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        consoleHandler.setFormatter(customFormatter);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("server.log", true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(customFormatter);
        } catch (SecurityException | IOException e) {
            LOGGER.log(Level.SEVERE, "File logger not working.", e);
        }

        LOGGER.addHandler(consoleHandler);
        if (fileHandler != null) {
            LOGGER.addHandler(fileHandler);
        }

        LOGGER.setLevel(Level.FINE);
    }

    public static void writeInfo(String message) {
        if (logArea != null) {
            String logString = logFormatter(new LogRecord(Level.INFO, message));
            logArea.append(logString);
        }
        LOGGER.logp(Level.INFO, getCallerClassName(), "", message);
    }

    public static void writeWarning(String message) {
        if (logArea != null) {
            String logString = logFormatter(new LogRecord(Level.WARNING, message));
            logArea.append(logString);
        }
        LOGGER.logp(Level.WARNING, getCallerClassName(), "", message);
    }

    public static void writeError(String message) {
        if (logArea != null) {
            String logString = logFormatter(new LogRecord(Level.SEVERE, message));
            logArea.append(logString);
        }
        LOGGER.logp(Level.SEVERE, getCallerClassName(), "", message);
    }

    public static void writeDebug(String message) {
        if (logArea != null) {
            String logString = logFormatter(new LogRecord(Level.FINE, message));
            logArea.append(logString);
        }
        LOGGER.logp(Level.FINE, getCallerClassName(), "", message);
    }

    private static String getCallerClassName() {
        return new Throwable().getStackTrace()[2].getClassName();
    }

    private static String logFormatter(LogRecord record) {
        return String.format("[%1$s] %2$tF %2$tT : %3$s %n",
                getLeverName(record.getLevel().getName()),
                new java.util.Date(record.getMillis()),
                record.getMessage());
    }

    private static String getLeverName(String level) {
        if (level.equals("SEVERE")) {
            return "ERROR";
        } else if (level.equals("FINE")) {
            return "DEBUG";
        } else {
            return level;
        }
    }
}
