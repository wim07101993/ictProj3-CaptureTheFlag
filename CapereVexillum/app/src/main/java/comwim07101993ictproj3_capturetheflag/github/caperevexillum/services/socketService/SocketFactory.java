package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

/**
 * Created by wimva on 25/11/2017.
 */

public final class SocketFactory {

    private static SocketService socketService;

    public synchronized static SocketService get() {
        return socketService;
    }

    public synchronized static SocketService get(String serverAddress, int port) {
        if (socketService == null) {
            return createNew(serverAddress, port);
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
