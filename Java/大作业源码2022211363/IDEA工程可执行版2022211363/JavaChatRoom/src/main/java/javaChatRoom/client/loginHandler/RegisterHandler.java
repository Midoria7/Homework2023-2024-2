package javaChatRoom.client.loginHandler;

import javaChatRoom.client.clientConfig.ClientConfig;
import javaChatRoom.client.clientConnection.ClientConnection;
import javaChatRoom.common.command.Command;

public class RegisterHandler {
    private ClientConnection connection;
    private ClientConfig config;
    private String username;
    private String password;

    public RegisterHandler(ClientConnection connection, ClientConfig config, String username, String password) {
        this.connection = connection;
        this.config = config;
        this.username = username;
        this.password = password;
    }

    public boolean performRegister() {
        System.out.println(username + " " + password);
        connection.send(new Command(Command.CommandType.REGISTERREQUEST, username, password));
        Object response = connection.receive();

        if (response instanceof Command) {
            Command command = (Command) response;
            if ("SUCCESS".equals(command.getArgs()[0])) {
                System.out.println("Register successful.");
//                config.setUsername(username);
                return true;
            } else {
                System.out.println("Register failed, already exist username.");
            }
        } else {
            System.out.println("Invalid response from server.");
        }
        return false;
    }
}
