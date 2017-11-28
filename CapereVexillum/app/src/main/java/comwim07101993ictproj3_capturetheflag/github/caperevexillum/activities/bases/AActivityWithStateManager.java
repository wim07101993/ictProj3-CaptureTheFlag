package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.Utils;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManagerFactory;

/**
 * Created by wimva on 17/11/2017.
 */

public abstract class AActivityWithStateManager extends AppCompatActivity {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = AActivityWithStateManager.class.getSimpleName();

    protected StateManager stateManager;
    protected StateManagerFactory stateManagerFactory;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
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

    protected void initStateManager() {
        initStateManager(false);
    }

    protected void initStateManager(boolean clearSharedPreferences) {

        if (stateManager == null) {
            if (stateManagerFactory == null) {
                stateManagerFactory = new StateManagerFactory();
            }

            try {
                stateManager = stateManagerFactory.get(
                        PreferenceManager.getDefaultSharedPreferences(this));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            if (!stateManager.getSocketService().isConnected()) {
                Utils.toast(this, "Could not connect to server");
            }

            if (!clearSharedPreferences) {
                stateManager.load();
            }
        }

        if (clearSharedPreferences) {
            stateManager.clear();
        }

        if (stateManager.getSerializable(EStateManagerKey.FLAGS) == null) {
            stateManager.setSerializable(EStateManagerKey.FLAGS, new Flags());
        }
    }

    public StateManager getStateManager() {
        return stateManager;
    }

}
