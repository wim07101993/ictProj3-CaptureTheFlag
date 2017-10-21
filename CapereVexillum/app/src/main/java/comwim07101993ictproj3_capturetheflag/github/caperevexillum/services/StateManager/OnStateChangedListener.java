package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.StateManager;

import java.util.List;

/**
 * Created by Wim Van Laer on 20/10/2017.
 *
 * The OnStateChangedListener is used to listen to changes in the StateManager. To listen to the
 * changes, you need to implement this interface and use the method addStateChangedListener to add
 * the class that implements this interface to the list of listeners.
 *
 * When something changes in the StateManager, the method StateChanged is invoked. It hold as
 * parameters the changed keys and the state manager in which the change happened.
 */

public interface OnStateChangedListener<TKey> {

    /**
     * StateChanged is the method that gets invoked when a state changes in the state manager. To
     * subscribe to these changes, you need to add the class that needs to listen to the listeners-
     * list with the addStateChangedListener method of the state manager.
     *
     * @param changedKeys is the list of keys which values changed.
     * @param manager is the manager in which the changes happened.
     */
    public void StateChanged(List<TKey> changedKeys, IStateManager manager);
}
