package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;

public class JoinLobbyActivity extends AActivityWithStateManager implements View.OnClickListener {

    private static final String TAG = JoinLobbyActivity.class.getSimpleName();

    private EditText lobbyNameEditText;
    private EditText lobbyPasswordEditText;
    private EditText playerNameEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backButtonEnabled=true;
        setContentView(R.layout.activity_join_lobby);

        findViewById(R.id.join_lobby_button).setOnClickListener(this);
        gameController.setBoolean(EStateManagerKey.IS_HOST,false);
        lobbyNameEditText = (EditText) findViewById(R.id.lobby_name_edittext);
        lobbyPasswordEditText = (EditText) findViewById(R.id.lobby_password_edittext);
        playerNameEditText = (EditText) findViewById(R.id.playername_edittext);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.join_lobby_button) {

            gameController.joinLobby(
                    new LobbySettings(
                            lobbyNameEditText.getText().toString(),
                            lobbyPasswordEditText.getText().toString(),
                            0.0f,
                            playerNameEditText.getText().toString()
                    )
            );
        }
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

}