package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

/**
 * Created by wimva on 25/11/2017.
 */

public final class SocketFactory {

    private static SocketService socketService;

    public synchronized static SocketService get() {
        if (socketService == null) {
            return createNew(null, 0);
        }
        return socketService;
    }

    public synchronized static SocketService set(SocketService value) {
        socketService = value;
        return socketService;
    }

    public synchronized static SocketService createNew(String serverAddress, int port) {
        socketService = new SocketService(serverAddress, port);
        return socketService;
    }
}
