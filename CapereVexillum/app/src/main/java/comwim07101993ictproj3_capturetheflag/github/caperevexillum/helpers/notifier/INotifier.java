package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier;

/**
 * Created by wimva on 25/11/2017.
 */

public interface INotifier<TListener, TToListenFor> {
    boolean addListener(TListener listener);
    boolean addSpecificListener(TListener listener, TToListenFor key);

    boolean removeListener(TListener listener);
    boolean removeSpecificListen(TListener listener, TToListenFor key);

    void notifyListeners(TToListenFor key, Object args);
}
