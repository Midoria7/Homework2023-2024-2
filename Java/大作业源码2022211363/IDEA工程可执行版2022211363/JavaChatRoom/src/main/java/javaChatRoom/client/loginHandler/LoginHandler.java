package javaChatRoom.client.loginHandler;

import javaChatRoom.client.clientConfig.ClientConfig;
import javaChatRoom.client.clientConnection.ClientConnection;
import javaChatRoom.common.command.Command;

public class LoginHandler {
    private ClientConnection connection;
    private ClientConfig config;
    private String username;
    private String password;

    public LoginHandler(ClientConnection connection, ClientConfig config, String username, String password) {
        this.connection = connection;
        this.config = config;
        this.username = username;
        this.password = password;
    }

    public boolean performLogin() {
        System.out.println(username + " " + password);
        connection.send(new Command(Command.CommandType.LOGINREQUEST, username, password));
        Object response = connection.receive();

        if (response instanceof Command) {
            Command command = (Command) response;
            if ("SUCCESS".equals(command.getArgs()[0])) {
                System.out.println("Login successful.");
                config.setUsername(username);
                return true;
            } else {
                System.out.println("Login failed, wrong username or password.");
            }
        } else {
            System.out.println("Invalid response from server.");
        }
        return false;
    }
}
