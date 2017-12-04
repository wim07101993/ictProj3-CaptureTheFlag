package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManager;

/**
 * Created by wimva on 28/11/2017.
 */

public class StateManagerFactory {
    private static IStateManager stateManager;

    public synchronized static IStateManager get() {
        if (stateManager == null) {
            return createNew(null);
        }
        return stateManager;
    }

    public synchronized static IStateManager set(IStateManager value) {
        stateManager = value;
        return stateManager;
    }

    public synchronized static IStateManager createNew(SharedPreferences sharedPreferences) {
        stateManager = new StateManager(sharedPreferences);
        return stateManager;
    }
}
