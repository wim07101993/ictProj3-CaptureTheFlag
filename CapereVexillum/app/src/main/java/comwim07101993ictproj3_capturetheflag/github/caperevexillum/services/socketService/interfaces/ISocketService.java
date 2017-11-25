package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.IListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.INotifier;

/**
 * Created by wimva on 25/11/2017.
 */

public interface ISocketService<TKey> extends INotifier<IListener<INotifier, TKey>, TKey> {
    void Send(TKey key, Object value);

    String getServerAddress();
    int getServerPort();
}
