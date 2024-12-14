package javaChatRoom.server.serverLogger;

import org.junit.jupiter.api.Test;

public class ServerLoggerTest {

    @Test
    void testServerLogger() {
        ServerLogger.writeInfo("This is an informational message");
        ServerLogger.writeDebug("This is a debug-level message");
        ServerLogger.writeWarning("This is a warning message");
        ServerLogger.writeError("This is an error message");
    }
}
