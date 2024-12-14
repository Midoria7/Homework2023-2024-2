package javaChatRoom.server.messageManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javaChatRoom.common.message.Message;
import javaChatRoom.server.serverLogger.ServerLogger;

public class MessageManager {
    private List<Message> messages;
    private static final String MESSAGE_DATA_PATH = "message.ser";

    public MessageManager() {
        messages = new ArrayList<>();
        loadMessages();
    }

    public void clearMessages() {
        messages.clear();
        saveMessages();
    }

    private void loadMessages() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MESSAGE_DATA_PATH))) {
            messages = (List<Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            ServerLogger.writeError("Error loading messages: " + e.getMessage());
        }
    }

    public void addMessage(Message message) {
        messages.add(message);
        saveMessages();
    }

    private void saveMessages() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MESSAGE_DATA_PATH))) {
            oos.writeObject(messages);
        } catch (IOException e) {
            ServerLogger.writeError("Error saving messages: " + e.getMessage());
        }
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages); // Return a copy to avoid external modifications
    }

    public List<Message> getMessagesForUser(String username) {
        // Return messages either sent by the user or for the user (private) or for all
        List<Message> relevantMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getReceiver().equals("ALL") ||
                    message.getSender().equals(username) ||
                    message.getReceiver().equals(username)) {
                relevantMessages.add(message);
            }
        }
        return relevantMessages;
    }
}
