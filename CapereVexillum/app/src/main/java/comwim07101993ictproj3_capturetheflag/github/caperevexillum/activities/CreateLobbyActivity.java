package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;

public class CreateLobbyActivity extends AActivityWithStateManager implements View.OnClickListener {


    private EditText lobbyNameEditText, passwordEditText, timeEditText, playerNameEditText;
    private Button createLobbyButton;
    private String playerName, lobbyName, lobbyPassword, lobbyTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        playerNameEditText = (EditText) findViewById(R.id.playername_edittext);
        lobbyNameEditText = (EditText) findViewById(R.id.lobbyname_edittext);
        passwordEditText = (EditText) findViewById(R.id.lobbypassword_edittext);
        timeEditText = (EditText) findViewById(R.id.lobbytime_edittext);

        createLobbyButton = (Button) findViewById(R.id.createLobby);
        createLobbyButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        //stateManager.restartSocket();


        playerName = playerNameEditText.getText().toString();
        lobbyName = lobbyNameEditText.getText().toString();
        lobbyPassword = passwordEditText.getText().toString();
        lobbyTime = timeEditText.getText().toString();
        stateManager.setString(EStateManagerKey.PLAYER_NAME, playerName);
        stateManager.setBoolean(EStateManagerKey.IS_HOST, true);
        stateManager.setInt(EStateManagerKey.LOBBY_ID, 0);
        stateManager.getSocketService().getSocket().on("lobbyExists", lobbyExists);
        stateManager.getSocketService().getSocket().on("getLobbyId", getLobbyId);
        stateManager.getSocketService().getSocket().on("playerNameUnavailable", playerNameUnavailable);
        stateManager.getSocketService().getSocket().emit("createLobby", playerName, lobbyName, lobbyPassword, lobbyTime);
    }

    Emitter.Listener lobbyExists = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            showToast("Lobby name exists");
        }
    };

    Emitter.Listener getLobbyId = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // navigate
            // String test = (String) args[0];
            Intent goToLobby;
            Integer lobbyID = (Integer) args[0];
            goToLobby = new Intent(CreateLobbyActivity.this, LobbyActivity.class);
            // Navigate to lobby
            goToLobby.putExtra("playerName", playerName);
            goToLobby.putExtra("isHost", true);
            goToLobby.putExtra("lobbyID", lobbyID);
            startActivity(goToLobby);
        }
    };

    Emitter.Listener playerNameUnavailable = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            showToast("Player name unavailable");
        }
    };

    private void showToast(final String msg) {
        final CreateLobbyActivity context = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Log.d("Create lobby", "error showing toast");
                }
            }
        });
    }


}


