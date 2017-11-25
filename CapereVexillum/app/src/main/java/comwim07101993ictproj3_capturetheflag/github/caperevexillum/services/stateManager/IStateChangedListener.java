package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.IListener;

/**
 * Created by Wim Van Laer on 20/10/2017.
 *
 * The IStateChangedListener is used to listen to changes in the StateManager. To listen to the
 * changes, you need to implement this interface and use the method addStateChangedListener to add
 * the class that implements this interface to the list of listeners.
 *
 * When something changes in the StateManager, the method stateChanged is invoked. It hold as
 * parameters the changed keys and the state manager in which the change happened.
 */

public interface IStateChangedListener<TKey> extends IListener<IStateManager<TKey>, TKey> {

}
