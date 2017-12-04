package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;

/**
 * Created by wimva on 30/11/2017.
 */

public class MockSocketFactory {

    private static ISocketService socketService;

    public synchronized static ISocketService get() {
        if (socketService == null) {
            return createNew(null, 0);
        }
        return socketService;
    }

    public synchronized static ISocketService set(MockSocketService value) {
        socketService = value;
        return socketService;
    }

    public synchronized static ISocketService createNew(String serverUrl, int port) {
        socketService = new MockSocketService(serverUrl, port);
        return socketService;
    }
}
