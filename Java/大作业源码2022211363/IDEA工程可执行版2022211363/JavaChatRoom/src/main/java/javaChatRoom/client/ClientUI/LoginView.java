package javaChatRoom.client.ClientUI;

import javaChatRoom.client.clientConfig.ClientConfig;
import javaChatRoom.client.clientConnection.ClientConnection;
import javaChatRoom.client.loginHandler.LoginHandler;
import javaChatRoom.client.loginHandler.RegisterHandler;
import javaChatRoom.client.messageHandler.MessageHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{
    private JButton loginButton;
    private JTextField serverAddressField;
    private JTextField serverPortField;
    private JTextField usernameField;
    private JPanel chatRoomLoginPanel;
    private JLabel serverIPLabel;
    private JLabel serverPortLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;

    public LoginView() {

        super("LoginView");


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String serverAddress = serverAddressField.getText();
                int serverPort = Integer.parseInt(serverPortField.getText());
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Connect and perform login
                ClientConnection connection = new ClientConnection(serverAddress, serverPort);
                ClientConfig config = new ClientConfig();
                try {
                    connection.connect();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginView.this, "Failed to connect to server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LoginHandler loginHandler = new LoginHandler(connection, config, username, password);

                if (loginHandler.performLogin()) {
                    dispose(); // Close the login window
                    ChatView chatView = new ChatView();
                    MessageHandler messageHandler = new MessageHandler(connection, config);
                    chatView.setMessageHandler(messageHandler);
                    messageHandler.setChatView(chatView);
                    chatView.setVisible(true);
                    messageHandler.startHandling(); // Start handling messages
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Login failed, please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String serverAddress = serverAddressField.getText();
                int serverPort = Integer.parseInt(serverPortField.getText());
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Connect and perform register
                ClientConnection connection = new ClientConnection(serverAddress, serverPort);
                ClientConfig config = new ClientConfig();
                try {
                    connection.connect();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginView.this, "Failed to connect to server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                RegisterHandler registerHandler = new RegisterHandler(connection, config, username, password);

                if (registerHandler.performRegister()) {
                    JOptionPane.showMessageDialog(LoginView.this, "Register successful.", "Register Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Register failed, please try again.", "Register Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setContentPane(chatRoomLoginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginView().chatRoomLoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
