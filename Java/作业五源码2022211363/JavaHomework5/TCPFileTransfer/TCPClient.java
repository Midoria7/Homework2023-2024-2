package TCPFileTransfer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the IP of server:");
        String serverIP = scanner.nextLine();
        System.out.println("Please enter the port of server:");
        int port = scanner.nextInt();
        scanner.nextLine();

        try (Socket socket = new Socket(serverIP, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // 发送文件
            System.out.print("Enter path of file to send to server: ");
            String sendFilePath = scanner.nextLine();
            File fileToSend = new File(sendFilePath);
            byte[] fileData = Files.readAllBytes(fileToSend.toPath());
            FileTransferObject fileToSendObject = new FileTransferObject(fileToSend.getName(), fileData);
            oos.writeObject(fileToSendObject);
            System.out.println("File sent: " + fileToSend.getName());

            // 接收文件对象
            System.out.println("Waiting for file from server...");
            FileTransferObject receivedFile = (FileTransferObject) ois.readObject();
            System.out.print("Enter directory to save the received file from server: ");
            String receiveDirectoryPath = scanner.nextLine();
            File receiveDirectory = new File(receiveDirectoryPath);
            if (!receiveDirectory.exists()) {
                receiveDirectory.mkdirs();
            }
            File outputFile = new File(receiveDirectory, receivedFile.getFileName());
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(receivedFile.getFileData());
            }
            System.out.println("File received and saved to: " + outputFile.getPath());
        }
        System.out.println("Connection closed");
        scanner.close();
    }
}
