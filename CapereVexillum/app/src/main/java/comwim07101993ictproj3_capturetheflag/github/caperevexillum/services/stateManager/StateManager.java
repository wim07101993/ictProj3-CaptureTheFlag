package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
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

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final Map<StateManagerKey, Type> KEY_TYPE_MAP = new HashMap<StateManagerKey, Type>() {{
        put(StateManagerKey.FLAGS, Flags.class);
        //put(StateManagerKey.CURRENT_ACTIVITY, )
        put(StateManagerKey.LOBBY_SETTINGS, LobbySettings.class);
        put(StateManagerKey.USER_ID, String.class);
        put(StateManagerKey.TEAMS, new TypeToken<List<Team>>() {
        }.getType());
        put(StateManagerKey.SCORE, long.class);

        // TODO Wim: Safe remove
        put(StateManagerKey.MY_TEAM, String.class);
        put(StateManagerKey.CURRENT_BEACON, Beacon.class);
    }};


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * StateManager is the constructor for the class StateManager.
     *
     * @param sharedPreferences the shared preferences to load and save the current state from and to.
     */
    public StateManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /* ------------------------- GETTERS ------------------------- */

    @Override
    protected Map<StateManagerKey, Type> getKeyTypeMap() {
        return KEY_TYPE_MAP;
    }

    /**
     * internalGet is the method that gets called when someone tries to get the state of a key.
     * This method does the internal handling of the change of a state when the state
     * is called.
     *
     * @param key         key is the key of which the state is asked.
     * @param changedKeys changedKeys are the keys that changed in the chain of getting (and maybe
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
        this.currentState.put(key, value);
        changedKeys.add(key);
    }
}
