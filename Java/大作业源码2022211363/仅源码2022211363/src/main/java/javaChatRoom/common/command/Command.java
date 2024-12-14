package javaChatRoom.common.command;

import java.io.Serializable;

public class Command implements Serializable {
    public enum CommandType {
        USERLIST, QUIT, REGISTERREQUEST, REGISTERRESPONSE, LOGINREQUEST, LOGINRESPONSE, ERROR
    }
    private final String[] args;
    private final CommandType commandType;

    public Command(CommandType commandType, String... args) {
        this.commandType = commandType;
        this.args = args;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getArgs() {
        return args;
    }
}
