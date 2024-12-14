package javaChatRoom.server.serverApp;

import javaChatRoom.server.serverController.ServerController;
import javaChatRoom.server.serverLogger.ServerLogger;

import javax.swing.*;
import java.awt.*;

public class ServerApp extends JFrame {
    private JButton startButton, stopButton, showOnlineUsersButton, showAllUsersButton;
    private JTextArea logArea;
    private ServerController serverController;

    public ServerApp() {
        createUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
    }

    private void createUI() {
        logArea = new JTextArea(22, 70);
        logArea.setEditable(false);

        ServerLogger.setLogArea(logArea);  // Set the log area for ServerLogger

        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        showOnlineUsersButton = new JButton("Show Online Users");
        showAllUsersButton = new JButton("Show All Users");

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(logArea));
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(showOnlineUsersButton);
        panel.add(showAllUsersButton);

        addEventHandlers();
        add(panel);
    }

    private void addEventHandlers() {
        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());
        showOnlineUsersButton.addActionListener(e -> displayUserList(true));
        showAllUsersButton.addActionListener(e -> displayUserList(false));
    }

    private void startServer() {
        logArea.append("Starting server...\n");
        new Thread(() -> {
            serverController = new ServerController(12345);
            serverController.start();
        }).start();
    }

    private void stopServer() {
        if (serverController != null) {
            serverController.stopServer();
            logArea.append("Server stopped.\n");
        }
    }

    private void displayUserList(boolean online) {
        java.util.List<String> users = online ? serverController.getOnlineUsers() : serverController.getAllUsers();
        SwingUtilities.invokeLater(() -> createUserListWindow(users, online ? "Online Users" : "All Users"));
    }

    private void createUserListWindow(java.util.List<String> users, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JList<String> userList = new JList<>(users.toArray(new String[0]));
        boolean isEmpty = users.isEmpty();
        if (isEmpty) {
            userList.setListData(new String[]{"No users"});
        }

        userList.setCellRenderer(new UserListCellRenderer(isEmpty)); // 传递标志

        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
