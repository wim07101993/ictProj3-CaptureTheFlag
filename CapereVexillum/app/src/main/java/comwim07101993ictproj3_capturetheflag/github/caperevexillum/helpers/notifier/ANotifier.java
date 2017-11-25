package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ListHelpers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.MapHelpers;

/**
 * Created by wimva on 25/11/2017.
 */

public abstract class ANotifier<TToListenFor>
        implements INotifier<IListener<INotifier, TToListenFor>, TToListenFor> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    /* A map with lists of listeners that need to listen for a specific change. */
    private Map<TToListenFor, List<IListener<INotifier, TToListenFor>>> specificListeners;
    /* A list of listeners that listen to every change that is made */
    private List<IListener<INotifier, TToListenFor>> listeners;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * addListener adds the given listener that listens to all changes.
     *
     * @param listener is the listener to add.
     * @return whether the listener has been added or not.
     */
    @Override
    public boolean addListener(IListener<INotifier, TToListenFor> listener) {
        if (ListHelpers.IsNullOrEmpty(listeners)) {
            listeners = new Vector<>();
        }
        return listeners.add(listener);
    }

    /**
     * addSpecificListener adds a listener that listens to specific changes.
     *
     * @param listener is the listener to add.
     * @param key is the key which the listener wants to listen to
     * @return whether the listener was added or not.
     */
    @Override
    public boolean addSpecificListener(IListener<INotifier, TToListenFor> listener, TToListenFor key) {
        if (MapHelpers.IsNullOrEmpty(specificListeners)) {
            specificListeners = new HashMap<>();
        }

        if (specificListeners.get(key) != null) {
            return specificListeners.get(key).add(listener);
        }

        Vector<IListener<INotifier, TToListenFor>> newListenersList = new Vector<>();
        if (!newListenersList.add(listener)) {
            return false;
        }

        specificListeners.put(key, newListenersList);
        return true;
    }

    /**
     * removeListener removes a lister that listens to all changes.
     *
     * @param listener is the listener to remove
     * @return whether the listener was removed or not.
     */
    @Override
    public boolean removeListener(IListener listener) {
        return !ListHelpers.IsNullOrEmpty(listeners) &&
                listeners.remove(listener);
    }

    /**
     * removeSpecificListener removes a listener that listens to specific changes.
     *
     * @param listener is the listener to remove.
     * @param key is the key to which the listener was listening
     * @return whether the listener was removed.
     */
    @Override
    public boolean removeSpecificListen(IListener<INotifier, TToListenFor> listener, TToListenFor key) {
        if (MapHelpers.IsNullOrEmpty(specificListeners)) {
            return false;
        }

        List<IListener<INotifier, TToListenFor>> listeners = specificListeners.get(key);
        return !ListHelpers.IsNullOrEmpty(listeners) && listeners.remove(listener);
    }

    /**
     * notifyListeners notifies all the listener that listen to changes of the given key.
     *
     * @param key is the key of the listeners that need to be notified.
     */
    @Override
    public void notifyListeners(TToListenFor key, Object args) {
        // check if there are listeners to notify
        if (!ListHelpers.IsNullOrEmpty(listeners)) {
            // notify all listeners that listen to all keys
            for (IListener<INotifier, TToListenFor> listener : listeners) {
                listener.notify(this, key, args);
            }
        }

        // check if there are specific listeners at all
        if (!MapHelpers.IsNullOrEmpty(specificListeners)) {
            // get the list of specific listeners
            List<IListener<INotifier, TToListenFor>> listeners = specificListeners.get(key);

            // check if there are listeners that need to listen to this key.
            if (!ListHelpers.IsNullOrEmpty(listeners)){
                // notify all listeners that listen to specific key
                for (IListener<INotifier, TToListenFor> listener : listeners){
                    listener.notify(this, key, args);
                }
            }
        }
    }
}
