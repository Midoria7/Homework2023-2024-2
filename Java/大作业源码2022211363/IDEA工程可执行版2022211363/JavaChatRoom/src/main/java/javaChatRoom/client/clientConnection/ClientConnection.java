package javaChatRoom.client.clientConnection;

import javaChatRoom.common.communicationManager.CommunicationManager;
import java.net.Socket;
import java.io.IOException;

public class ClientConnection {
    private CommunicationManager commManager;
    private String serverAddress;
    private int serverPort;

    public ClientConnection(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        Socket socket = new Socket(serverAddress, serverPort);
        commManager = new CommunicationManager(socket);
    }

    public void send(Object obj) {
        commManager.sendObject(obj);
    }

    public Object receive() {
        return commManager.receiveObject();
    }

    public void close() {
        if (commManager != null) {
            commManager.close();
        }
    }

    public boolean isConnected() {
        return commManager != null && !commManager.getSocket().isClosed();
    }
}
