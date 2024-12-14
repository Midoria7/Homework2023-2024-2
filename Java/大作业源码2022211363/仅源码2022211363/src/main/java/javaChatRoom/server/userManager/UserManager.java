package javaChatRoom.server.userManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import javaChatRoom.server.clientHandler.ClientHandler;
import javaChatRoom.server.serverLogger.ServerLogger;

public class UserManager {
    private List<User> users;
    private List<String> onlineUsers;
    private Map<String, ClientHandler> userConnections;
    private static final String USER_DATA_PATH = "user.ser";

    public UserManager() {
        users = Collections.synchronizedList(new ArrayList<>());
        onlineUsers = Collections.synchronizedList(new ArrayList<>());
        userConnections = new HashMap<>();
        loadUsers();
    }

    public synchronized void clearUsers() {
        users.clear();
        saveUsers();
    }

    private synchronized void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_DATA_PATH))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            ServerLogger.writeError("Error loading users: " + e.getMessage());
            users = Collections.synchronizedList(new ArrayList<>()); // Start with an empty list if loading failed
        }
    }

    public synchronized boolean isValidUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    public synchronized boolean registerUser(String username, String password) {
        if (users.stream().noneMatch(u -> u.getUsername().equals(username))) {
            User newUser = new User(username, password);
            users.add(newUser);
            saveUsers();
            return true; // Registration successful
        }
        return false; // Username already exists
    }

    public synchronized void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public synchronized void removeUser(User user) {
        users.remove(user);
        saveUsers();
    }

    private synchronized void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            ServerLogger.writeError("Error saving users: " + e.getMessage());
        }
    }

    public synchronized void userLogIn(String user, ClientHandler handler) {
        if (!onlineUsers.contains(user)) {
            onlineUsers.add(user);
            userConnections.put(user, handler);
        } else {
            ServerLogger.writeWarning("User " + user + " is already logged in");
        }
    }

    public synchronized void userLoggedOut(ClientHandler handler) {
        String username = onlineUsers.stream()
                .filter(user -> userConnections.get(user).equals(handler))
                .findFirst()
                .orElse(null);
        if (username != null) {
            onlineUsers.remove(username);
            userConnections.remove(username);
        } else {
            ServerLogger.writeError("ClientHandler not found in userConnections");
        }
    }

    public synchronized List<ClientHandler> getConnectedClients() {
        return new ArrayList<>(userConnections.values());
    }

    public synchronized List<String> getOnlineUsernames() {
        return new ArrayList<>(onlineUsers);
    }

    public synchronized List<String> getAllUsernames() {
        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    public synchronized ClientHandler getClientHandler(String username) {
        return userConnections.get(username);
    }
}
