package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases;

import android.content.Intent;
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

    protected IGameController gameController;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */
    public void startActivity(Class activity){
        startActivity(new Intent(this.getBaseContext(), activity));
        finish();
    }
    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateManager();
        gameController.setContext(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameController != null)
            gameController.save();
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

        if (gameController == null) {
            try {
                gameController = GameStateManagerFactory.createNew(
                        PreferenceManager.getDefaultSharedPreferences(this));

                if (!clearSharedPreferences) {
                    gameController.load();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        if (clearSharedPreferences) {
            gameController.clear();
        }
    }

    public IGameController getGameController() {
        return gameController;
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
