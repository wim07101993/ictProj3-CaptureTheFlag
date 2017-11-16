package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

public class JoinLobbyActivity extends AppCompatActivity implements View.OnClickListener {

    private StateManager stateManager;
    private Socket socket;

    private EditText lobbyNameEditText;
    private EditText lobbyPasswordEditText;
    private EditText playerNameEditText;

    private Button joinLobbyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);

        joinLobbyButton = (Button) findViewById(R.id.join_lobby_button);
        joinLobbyButton.setOnClickListener(this);

        lobbyNameEditText = (EditText) findViewById(R.id.lobby_name_edittext);
        lobbyPasswordEditText = (EditText) findViewById(R.id.lobby_password_edittext);
        playerNameEditText = (EditText) findViewById(R.id.playername_edittext);

        // TODO Sven: get statemanager from previous activity via Intent
//        initStateManager();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.join_lobby_button) {
            // If socket doesn't exist, get socket and connect to it
            if (socket == null) {
                try {
                    socket = IO.socket(GameActivity.SERVER_URL);
                    socket.connect();
                } catch (URISyntaxException e) {
                    Log.d("JoinLobbyActivity", e.getMessage());
                }
            }

            if (socket != null) {
                // Send join lobby request to server
                socket.emit("joinLobby", lobbyNameEditText.getText(), lobbyPasswordEditText.getText(), playerNameEditText.getText());
                // Link listener for server answer
                socket.on("getLobbyId", getLobbyId);

                // Navigate to lobby
                Intent goToLobby = new Intent(this, LobbyActivity.class);
                startActivity(goToLobby);
//                stateManager.set(StateManagerKey.IS_HOST, false);
            }
        }
    }

    Emitter.Listener getLobbyId = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String lobbyID = (String) args[0];
//            stateManager.set(StateManagerKey.LOBBY_ID, lobbyID);
        }
    };

//    private void initStateManager() {
//
//        if (stateManager == null) {
//            stateManager = new StateManager(
//                    PreferenceManager.getDefaultSharedPreferences(this)
//            );
//            stateManager.load();
//        }
//
//        if (stateManager.get(StateManagerKey.FLAGS) == null) {
//            stateManager.set(StateManagerKey.FLAGS, new Flags());
//        }
//    }
}
