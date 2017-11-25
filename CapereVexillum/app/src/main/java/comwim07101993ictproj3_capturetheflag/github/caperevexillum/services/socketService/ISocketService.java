package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

/**
 * Created by wimva on 25/11/2017.
 */

public interface ISocketService<TKey> {
    void Send(TKey key, Object value);

    String getServerAddress();
    int getServerPort();
}
