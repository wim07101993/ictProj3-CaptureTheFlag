package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier;

/**
 * Created by wimva on 25/11/2017.
 */

public interface IListener<TNotifier, TToListenFor> {
    void notify(TNotifier sender, TToListenFor key, Object args);
}
