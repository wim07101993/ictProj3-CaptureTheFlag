package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.StateManager;

import android.app.Activity;

import java.util.List;

/**
 * Created by Sanli on 19/10/2017.
 */

public class StateManager extends AbstractStateManager<StateManagerKey> implements IStateManager<StateManagerKey> {

    /* ----------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* ----------------------------------------------------------- */

    protected StateManager(Activity activity, String sharedPreferencesName) {
        super(activity, sharedPreferencesName);
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /* ------------------------- GETTERS ------------------------- */

    @Override
    protected synchronized Object internalGet(StateManagerKey key, List<StateManagerKey> changedKeys)
            throws IllegalArgumentException {
        return currentState.get(key);
    }

    /* ------------------------- SETTERS ------------------------- */


    @Override
    protected synchronized void internalSet(StateManagerKey key, Object value, List<StateManagerKey> changedKeys)
            throws IllegalArgumentException {
        currentState.put(key, value);
        changedKeys.add(key);
    }
}
