package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

/**
 * Created by Wim Van Laer on 20/10/2017.
 * <p>
 * StateManager is a state manager that extends the abstractStateManager (and thereby implements the
 * IStateManager interface).
 * <p>
 * The StateManagerKey enum is used as key for the abstract class.
 *
 * @see AbstractStateManager
 */

public class StateManager extends AbstractStateManager<StateManagerKey> {

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * StateManager is the constructor for the class StateManager.
     *
     * @param sharedPreferences     the shared preferences to load and save the current state from and to.
     * @param sharedPreferencesName the name of the shared preferences in the database.
     */
    public StateManager(SharedPreferences sharedPreferences, String sharedPreferencesName) {
        super(sharedPreferences, sharedPreferencesName);
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    public synchronized boolean load() {
        // get the value from the saved values
        String json = savedValues.getString(sharedPreferencesName, null);

        // if value equals null => create new map
        if (json == null) {
            currentState = new HashMap<>();
            return false;
        }

        Gson gson = new Gson();
        // Create type to convert json to.
        Type t = new TypeToken<Map<StateManagerKey, Object>>() {
        }.getType();

        // set current state to the converted json.
        Map<StateManagerKey, Object> currentStateWithStringKeys = gson.fromJson(json, t);
        currentState = currentStateWithStringKeys;

        return true;
    }

    /* ------------------------- GETTERS ------------------------- */

    /**
     * internalGet is the method that gets called when someone tries to get the state of a key.
     * This method does the internal handling of the change of a state when the state
     * is called.
     *
     * @param key         key is the key of which the state is asked.
     * @param changedKeys chagedKeys are the keys that changed in the chain of getting (and maybe
     *                    setting) states.
     * @return The value of the asked state.
     * @throws IllegalArgumentException It is possible that when an argument is passed, the argument
     *                                  is not valid. In that case, the exception is thrown.
     */
    @Override
    protected synchronized Object internalGet(StateManagerKey key, List<StateManagerKey> changedKeys)
            throws IllegalArgumentException {
        return currentState.get(key);
    }

    /* ------------------------- SETTERS ------------------------- */

    /**
     * internalSet is the method that gets called when someone tries to set the state of a key.
     * This method does the internal handling of the change of a state when the state
     * is set.
     *
     * @param key         key is the key of which the value is set.
     * @param value       value is the value that the state should be set to.
     * @param changedKeys changedKeys are the keys that changed in the chain of setting (and maybe
     *                    getting) states.
     * @throws IllegalArgumentException It is possible that when an argument is passed, the argument
     *                                  is not valid. In that case, the exception is thrown.
     */
    @Override
    protected synchronized void internalSet(StateManagerKey key, Object value, List<StateManagerKey> changedKeys)
            throws IllegalArgumentException {
        currentState.put(key, value);
        changedKeys.add(key);
    }
}
