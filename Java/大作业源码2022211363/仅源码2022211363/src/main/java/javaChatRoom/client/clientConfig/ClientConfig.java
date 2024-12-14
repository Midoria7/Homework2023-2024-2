package javaChatRoom.client.clientConfig;

public class ClientConfig {
    private String username;
    private boolean isBroadcast;
    private boolean isAnonymous;

    public ClientConfig() {
        this.username = "";
        this.isBroadcast = true; // 默认为广播模式
        this.isAnonymous = false; // 默认为非匿名模式
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public void setBroadcast(boolean broadcast) {
        isBroadcast = broadcast;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public void reset() {
        this.username = "";
        this.isBroadcast = true;
        this.isAnonymous = false;
    }
}
