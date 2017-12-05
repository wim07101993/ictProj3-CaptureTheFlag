package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.GameStateManagerFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IGameController;

/**
 * Created by wimva on 17/11/2017.
 */

public abstract class AActivityWithStateManager extends AppCompatActivity {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = AActivityWithStateManager.class.getSimpleName();

    protected IGameController stateManager;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateManager();
        stateManager.setContext(this);
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
            try {
                stateManager = GameStateManagerFactory.createNew(
                        PreferenceManager.getDefaultSharedPreferences(this));

                if (!clearSharedPreferences) {
                    stateManager.load();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        if (clearSharedPreferences) {
            stateManager.clear();
        }

        if (stateManager.getSerializable(EStateManagerKey.FLAGS) == null) {
            stateManager.setSerializable(EStateManagerKey.FLAGS, new Flags());
        }
    }

    public IGameController getStateManager() {
        return stateManager;
    }

    public void showToast(final String message) {
        final AppCompatActivity context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.w(getTAG(), "Error while making toast" + e.getMessage());
                }
            }
        });
    }

    protected abstract String getTAG();

}
