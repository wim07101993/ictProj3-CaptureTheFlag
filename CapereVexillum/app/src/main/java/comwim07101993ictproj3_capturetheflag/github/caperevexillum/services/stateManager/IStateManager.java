package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

/**
 * Created by Wim Van Laer on 20/10/2017.
 *
 * The IStateManager is an interface for a state manager. If you want an abstract with most of the
 * methods implemented you can use the AbstractStateManager.
 *
 * TKey is the type of the keys to identify the different states.
 */

public interface IStateManager<TKey> {


    /**
     * addStateChangedListener is supposed to add the given OnStateChangedListener to the list of
     * listeners so that it may receive updates when a state changed.
     *
     * @param listener is the OnStateChangedListener to add to the list of listeners.
     */
    void addStateChangedListener(OnStateChangedListener listener);

    /**
     * removeStateChangedListener is supposed to remove the given OnStateChangedListener from the
     * list of listeners so that it may no longer receive updates when a state changed.
     *
     * @param listener is the OnStateChangedListener to remove from the list of listeners.
     */
    void removeStateChangedListener(OnStateChangedListener listener);

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    Object get(TKey key);

    /**
     * set is supposed to set the state of the TKey key with the value value.
     *
     * @param key is the key to set the value of.
     * @param value is the value to set the state to.
     */
    void set(TKey key, Object value);

    /**
     * save is supposed to save the current state to a database.
     */
    void save();

    /**
     * load is supposed to load the previous state from a database.
     */
    void load();
}
