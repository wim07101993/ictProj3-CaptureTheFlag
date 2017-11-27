package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;

public class JoinLobbyActivity extends AppCompatActivity implements View.OnClickListener {

    private StateManager stateManager;


    private EditText lobbyNameEditText;
    private EditText lobbyPasswordEditText;
    private EditText playerNameEditText;

    private Button joinLobbyButton;
    private int lobbyID;
    private Intent goToLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);

        joinLobbyButton = (Button) findViewById(R.id.join_lobby_button);
        joinLobbyButton.setOnClickListener(this);

        lobbyNameEditText = (EditText) findViewById(R.id.lobby_name_edittext);
        lobbyPasswordEditText = (EditText) findViewById(R.id.lobby_password_edittext);
        playerNameEditText = (EditText) findViewById(R.id.playername_edittext);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.join_lobby_button) {
            if (view.getId() == R.id.join_lobby_button) {
                stateManager.getSocketService().getSocket().emit("joinLobby", lobbyNameEditText.getText(), lobbyPasswordEditText.getText(), playerNameEditText.getText().toString());
                // Link listener for server answer
                stateManager.getSocketService().getSocket().on("getLobbyId", getLobbyId);
                stateManager.getSocketService().getSocket().on("playerNameUnavailable", playerNameUnavailable);
                stateManager.getSocketService().getSocket().on("lobbyNotFound", lobbyNotFound);

                goToLobby = new Intent(this, LobbyActivity.class);

            }
        }
    }

    Emitter.Listener getLobbyId = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            lobbyID = (int) args[0];

            Log.d("JOINLOBBY", String.valueOf(lobbyID));

            // Navigate to lobby
            goToLobby.putExtra("playerName", playerNameEditText.getText().toString());
            goToLobby.putExtra("isHost", false);
            goToLobby.putExtra("lobbyID", lobbyID);

            stateManager.setString(EStateManagerKey.PLAYER_NAME, playerNameEditText.getText().toString());
            stateManager.setBoolean(EStateManagerKey.IS_HOST, false);
            stateManager.setInt(EStateManagerKey.LOBBY_ID, lobbyID);

            startActivity(goToLobby);
        }
    };

    Emitter.Listener lobbyNotFound = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // TODO Sven: show toast
            showToast("Lobby not found");
        }
    };

    Emitter.Listener playerNameUnavailable = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // TODO Sven: show toast
            showToast("Player name unavailable");
        }
    };

    private void showToast(final String msg) {
        final JoinLobbyActivity context = this;
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