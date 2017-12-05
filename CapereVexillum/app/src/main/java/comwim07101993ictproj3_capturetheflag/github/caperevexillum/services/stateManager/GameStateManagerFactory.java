package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IGameController;

/**
 * Created by wimva on 28/11/2017.
 */

public class GameStateManagerFactory {
    private static IGameController stateManager;

    public synchronized static IGameController get() {
        if (stateManager == null) {
            return createNew(null);
        }
        return stateManager;
    }

    public synchronized static IGameController set(IGameController value) {
        stateManager = value;
        return stateManager;
    }

    public synchronized static IGameController createNew(SharedPreferences sharedPreferences) {
        stateManager = new GameController(sharedPreferences);
        return stateManager;
    }
}
