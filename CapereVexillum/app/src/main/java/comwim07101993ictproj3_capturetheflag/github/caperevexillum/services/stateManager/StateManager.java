package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 25/11/2017.
 */

public class StateManager extends AStateManager<IStateManagerKey> {

    private static final String TAG = StateManagerWithSocket.class.getSimpleName();


    /**
     * StateManagerWithSocket is the constructor for the class StateManagerWithSocket.
     */
    StateManager() {
        super();
    }

    /**
     * StateManagerWithSocket is the constructor for the class StateManagerWithSocket.
     *
     * @param sharedPreferences the shared preferences to load and save the current state from and to.
     */
    StateManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }


    @Override
    protected IStateManagerKey convertStringToKey(String string) {
        return EStateManagerKey.convertFromString(string);
    }

    @NonNull
    @Override
    public String getTAG() {
        return TAG;
    }

}
