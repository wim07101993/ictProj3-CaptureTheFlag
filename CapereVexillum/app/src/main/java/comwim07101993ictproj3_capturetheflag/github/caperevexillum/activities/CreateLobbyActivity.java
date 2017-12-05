package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;

public class CreateLobbyActivity extends AActivityWithStateManager implements View.OnClickListener {

    private static final String TAG = CreateLobbyActivity.class.getSimpleName();

    private EditText lobbyNameEditText;
    private EditText passwordEditText;
    private EditText timeEditText;
    private EditText playerNameEditText;


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
        stateManager.createLobby(new LobbySettings(
                lobbyNameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                Float.parseFloat(timeEditText.getText().toString()),
                playerNameEditText.getText().toString()
        ));
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

}


