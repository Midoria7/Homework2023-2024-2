package TCPFileTransfer;

import java.io.Serializable;

public class FileTransferObject implements Serializable {
    private String fileName;
    private byte[] fileData;

    public FileTransferObject(String fileName, byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
