package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces;

import java.util.Observer;

/**
 * Created by Wim Van Laer on 20/10/2017.
 * <p>
 * The IStateChangedListener is used to observe to changes in the StateManager. To observe to the
 * changes, you need to implement this interface and use the method addStateChangedListener to add
 * the class that implements this interface to the list of listeners.
 * <p>
 * When something changes in the StateManager, the method stateChanged is invoked. It hold as
 * parameters the changed keys and the state manager in which the change happened.
 */

public interface IStateChangedListener extends Observer {

}
