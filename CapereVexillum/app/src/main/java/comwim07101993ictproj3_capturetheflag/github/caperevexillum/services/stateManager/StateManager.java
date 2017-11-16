package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.PrimitiveDefaults;
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

    private static final String TAG = GameActivity.class.getSimpleName();

    private static final Map<StateManagerKey, Type> KEY_TYPE_MAP = new HashMap<StateManagerKey, Type>() {{
        put(StateManagerKey.TEAMS, new TypeToken<List<Team>>() {
        }.getType());

        put(StateManagerKey.FLAGS, Flags.class);
        put(StateManagerKey.LOBBY_SETTINGS, LobbySettings.class);
        put(StateManagerKey.USER_ID, String.class);
        put(StateManagerKey.SCORE, long.class);
        put(StateManagerKey.LOBBY_ID, String.class);
        put(StateManagerKey.GAME_TIME, Float.class);
        put(StateManagerKey.GAME_STARTED, boolean.class);
    }};

    private static final String SERVER_URL = "http://192.168.137.1:4040";
    private static final boolean USE_SOCKET = true;
    private static final int GAME_DURATION_IN_MINUTES = 30;


    private final Socket socket = initSocket();


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
        initSocket();
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
        Object value = currentState.get(key);

        if (value == null) {
            Type type = KEY_TYPE_MAP.get(key);
            return PrimitiveDefaults.getDefaultValue(type);
        }

        return value;
    }

    public Socket getSocket() {
        return socket;
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
