package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.StateManager;

/**
 * Created by wimva on 20/10/2017.
 */

public interface IStateManager<TKey> {

    void addStateChangedListener(OnStateChangedListener listener);
    void removeStateChangedListener(OnStateChangedListener listener);

    Object get(TKey key);
    void set(TKey key, Object value);

    void save();
    void load();
}
