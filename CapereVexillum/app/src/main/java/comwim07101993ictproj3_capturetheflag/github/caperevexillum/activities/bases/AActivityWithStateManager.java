package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.nkzawa.socketio.client.Socket;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

/**
 * Created by wimva on 17/11/2017.
 */

public abstract class AActivityWithStateManager extends AppCompatActivity {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    protected StateManager stateManager;
    protected Socket socket;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stateManager != null)
            stateManager.save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initStateManager();
    }

    protected void initStateManager(){
        initStateManager(false);
    }

    protected void initStateManager(boolean clearSharedPreferences) {

        if (stateManager == null) {
            stateManager = new StateManager(
                    PreferenceManager.getDefaultSharedPreferences(this)
            );

            if (!clearSharedPreferences) {
                stateManager.load();
            }
        }

        if (clearSharedPreferences) {
            stateManager.clear();
        }

        if (stateManager.get(StateManagerKey.FLAGS) == null) {
            stateManager.set(StateManagerKey.FLAGS, new Flags());
        }

        socket = stateManager.getSocket();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public Socket getSocket() {
        return socket;
    }

}
