package javaChatRoom.common.message;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private final String sender;
    private final String receiver; // "ALL" for public messages
    private final String content;
    private final Boolean isBroadcast;
    private final Boolean isAnonymous;
    private final Date timestamp;

    public Message(String sender, String receiver, String content, Boolean isBroadcast, Boolean isAnonymous) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isBroadcast = isBroadcast;
        this.isAnonymous = isAnonymous;
        this.timestamp = new Date(); // Capture the time of message creation
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsBroadcast() {
        return isBroadcast;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", isBroadcast=" + isBroadcast +
                ", isAnonymous=" + isAnonymous +
                ", timestamp=" + timestamp +
                '}';
    }
}
