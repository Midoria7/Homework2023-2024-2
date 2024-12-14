package TCPFileTransfer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Scanner;

public class TCPServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8008);
        System.out.println("Server is listening on port 8008");
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = serverSocket.accept();
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            System.out.println("Client connected");

            // 接收文件对象
            System.out.println("Waiting for file from client...");
            FileTransferObject receivedFile = (FileTransferObject) ois.readObject();
            System.out.print("Enter directory to save the received file: ");
            String directoryPath = scanner.nextLine();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File outputFile = new File(directory, receivedFile.getFileName());
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(receivedFile.getFileData());
            }
            System.out.println("File received and saved to: " + outputFile.getPath());

            // 发送文件对象
            System.out.print("Enter path of file to send to client: ");
            String filePath = scanner.nextLine();
            File fileToSend = new File(filePath);
            byte[] fileData = Files.readAllBytes(fileToSend.toPath());
            FileTransferObject fileToSendObject = new FileTransferObject(fileToSend.getName(), fileData);
            oos.writeObject(fileToSendObject);
            System.out.println("File sent: " + fileToSend.getName());
        }
        System.out.println("Connection closed");
        scanner.close();
    }
}
