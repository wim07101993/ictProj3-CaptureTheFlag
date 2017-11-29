package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;

import java.util.Observable;
import java.util.Observer;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateChangedArgs;

public class CreateLobbyActivity extends AActivityWithStateManager implements View.OnClickListener {

    private static final String TAG = CreateLobbyActivity.class.getSimpleName();

    private EditText lobbyNameEditText, passwordEditText, timeEditText, playerNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        playerNameEditText = (EditText) findViewById(R.id.playername_edittext);
        lobbyNameEditText = (EditText) findViewById(R.id.lobbyname_edittext);
        passwordEditText = (EditText) findViewById(R.id.lobbypassword_edittext);
        timeEditText = (EditText) findViewById(R.id.lobbytime_edittext);

        findViewById(R.id.createLobby).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        LobbySettings lobbySettings = new LobbySettings(
                lobbyNameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                Float.parseFloat(timeEditText.getText().toString()),
                playerNameEditText.getText().toString()
        );

        stateManager.addObserver(lobbyCreatedObserver);
        stateManager.setSerializable(EStateManagerKey.LOBBY_SETTINGS, lobbySettings);
    }

    @Override
    protected String getTAG() {
        return TAG;
    }


    private Observer lobbyCreatedObserver = new Observer() {
        @Override
        public void update(Observable observable, Object args) {
            if (!(args instanceof StateChangedArgs)) {
                return;
            }

            StateChangedArgs stateChangedArgs = (StateChangedArgs) args;
            if (stateChangedArgs.getKey() != EStateManagerKey.LOBBY_SETTINGS) {
                return;
            }

            Object newValue = stateChangedArgs.getNewValue();
            if (!(newValue instanceof LobbySettings)) {
                return;
            }

            LobbySettings lobbySettings = (LobbySettings) newValue;
            if (lobbySettings.getId() != -1) {
                stateManager.setInt(EStateManagerKey.LOBBY_ID, lobbySettings.getId());
                stateManager.setString(EStateManagerKey.PLAYER_NAME, lobbySettings.getHostName());
                stateManager.setBoolean(EStateManagerKey.IS_HOST, true);

                startActivity(new Intent(CreateLobbyActivity.this, LobbyActivity.class));
            } else if (lobbySettings.getHostName() == null) {
                showToast("Playername already exists");
            } else if (lobbySettings.getName() == null) {
                showToast("Lobbyname already exists");
            } else {
                showToast("Could not create lobby");
            }
        }
    };

}


