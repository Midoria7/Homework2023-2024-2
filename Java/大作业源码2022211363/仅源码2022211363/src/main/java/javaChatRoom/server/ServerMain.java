package javaChatRoom.server;

import javaChatRoom.server.serverApp.ServerApp;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javaChatRoom.server.serverLogger.ServerLogger;

public class ServerMain {
    public static void main(String[] args) {
        // 设置界面外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ServerLogger.writeError("Error setting look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            ServerApp serverApp = new ServerApp();
            serverApp.setVisible(true);
            serverApp.setTitle("Server");
        });
    }
}
