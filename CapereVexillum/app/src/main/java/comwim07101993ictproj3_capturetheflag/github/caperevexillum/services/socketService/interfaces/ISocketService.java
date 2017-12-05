package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces;

import com.github.nkzawa.socketio.client.Socket;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.IObservable;

/**
 * Created by wimva on 25/11/2017.
 */

public interface ISocketService<TKey> extends IObservable {
    void send(TKey key, Object value);

    void connect();

    String getServerAddress();

    int getServerPort();

    boolean isConnected();

    @Deprecated
    Socket getSocket();
}
