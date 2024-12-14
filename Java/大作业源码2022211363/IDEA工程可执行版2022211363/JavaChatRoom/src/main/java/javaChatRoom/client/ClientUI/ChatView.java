package javaChatRoom.client.ClientUI;

import javaChatRoom.client.messageHandler.MessageHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatView extends JFrame {
    private JPanel chatWindowPanel;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane messageWindowPanel;
    private JTextPane messageTextPane;
    private MessageHandler messageHandler;

    public ChatView() {
        super("ChatView");
        setContentPane(chatWindowPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                performCloseOperation();
            }
        });
        pack();
        setVisible(true);
        sendButton.addActionListener(this::sendMessage);
    }

    private void performCloseOperation() {
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?", "User Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            messageHandler.sendMessage("@@quit");
            dispose();
        }
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    private void sendMessage(ActionEvent e) {
        String messageText = inputField.getText();
        if (!messageText.isEmpty()) {
            inputField.setText("");
            messageHandler.sendMessage(messageText);
        }
    }

    public void displayMessage(String message, int level) {
        Color messageColor = getMessageColor(level);

        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = messageTextPane.getStyledDocument();
            Style style = messageTextPane.addStyle("Color Style", null);
            StyleConstants.setForeground(style, messageColor);

            String[] parts = message.split("\n", 2);
            try {
                if (parts.length > 0) {
                    doc.insertString(doc.getLength(), parts[0], style);
                }
                if (parts.length > 1) {
                    Style style2 = messageTextPane.addStyle("Black Style", null);
                    StyleConstants.setForeground(style2, Color.BLACK);
                    StyleConstants.setFontSize(style2, 20);

                    doc.insertString(doc.getLength(), "\n" + parts[1], style2);
                }
                doc.insertString(doc.getLength(), "\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }


    private Color getMessageColor(int level) {
        return switch (level) {
            case 0 -> Color.BLACK;
            case 1 -> Color.decode("#4682B4");
            case 2 -> Color.decode("#2E8B57");
            case 3 -> Color.decode("#FF8C00");
            case 4 -> Color.RED;
            default -> Color.GRAY;
        };
    }
}
