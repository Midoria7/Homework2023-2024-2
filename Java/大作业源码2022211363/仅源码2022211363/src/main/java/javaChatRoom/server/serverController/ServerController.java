package javaChatRoom.server.serverController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import javaChatRoom.server.clientHandler.ClientHandler;
import javaChatRoom.server.userManager.UserManager;
import javaChatRoom.server.serverLogger.ServerLogger;

public class ServerController {
    private ServerSocket serverSocket;
    private final int port;
    private UserManager userManager;
    private volatile boolean running = true;
    private boolean isClosed = false;  // 新增状态标志

    public ServerController(int port) {
        this.port = port;
        this.userManager = new UserManager();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            ServerLogger.writeInfo("Server started on port " + port);

            new Thread(this::handleConsoleInput).start();

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ServerLogger.writeInfo("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                    ClientHandler clientHandler = new ClientHandler(clientSocket, userManager);
                    clientHandler.start();
                } catch (SocketException e) {
                    if (!running) {
                        ServerLogger.writeInfo("Server socket closed, shutting down.");
                    } else {
                        ServerLogger.writeError("Unexpected socket error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            if (!running) {
                ServerLogger.writeInfo("Server is shutting down.");
            } else {
                ServerLogger.writeError("Server error: " + e.getMessage());
            }
        } finally {
            if (!isClosed) {
                stop();
            }
        }
    }

    private void handleConsoleInput() {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (running && (input = consoleReader.readLine()) != null) {
                switch (input.trim().toLowerCase()) {
                    case "list":
                        listOnlineUsers();
                        break;
                    case "listall":
                        listAllUsers();
                        break;
                    case "quit":
                        stopServer();
                        break;
                    default:
                        ServerLogger.writeWarning("Unknown command: " + input);
                }
            }
        } catch (IOException e) {
            ServerLogger.writeError("Error reading from console: " + e.getMessage());
        }
    }

    private void listOnlineUsers() {
        if (userManager.getOnlineUsernames().isEmpty()) {
            ServerLogger.writeInfo("No users online.");
            return;
        }
        userManager.getOnlineUsernames().forEach(ServerLogger::writeInfo);
    }

    public List<String> getOnlineUsers() {
        return userManager.getOnlineUsernames();
    }

    private void listAllUsers() {
        if (userManager.getAllUsernames().isEmpty()) {
            ServerLogger.writeInfo("No users registered.");
            return;
        }
        userManager.getAllUsernames().forEach(ServerLogger::writeInfo);
    }

    public List<String> getAllUsers() {
        return userManager.getAllUsernames();
    }

    public void stopServer() {
        ServerLogger.writeInfo("Shutting down server...");
        running = false;
        stop();
    }

    public void stop() {
        if (!isClosed) {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                isClosed = true; // 更新状态标志
                ServerLogger.writeInfo("All connections closed.");
            } catch (IOException e) {
                ServerLogger.writeError("Error closing server: " + e.getMessage());
            }
        }
    }
}
