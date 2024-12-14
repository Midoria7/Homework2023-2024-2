package javaChatRoom.common.communicationManager;

import javaChatRoom.server.serverLogger.ServerLogger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class CommunicationManager {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public CommunicationManager(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendObject(Object obj) {
        try {
            outputStream.writeObject(obj);
            outputStream.flush();
        } catch (IOException e) {
            ServerLogger.writeError("Error sending object: " + e.getMessage());
        }
    }

    public Object receiveObject() {
        try {
            return inputStream.readObject();
        } catch (Exception e){
            System.exit(0);
            return null;
        }
    }

    public void close() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            ServerLogger.writeError("Error closing connections: " + e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
