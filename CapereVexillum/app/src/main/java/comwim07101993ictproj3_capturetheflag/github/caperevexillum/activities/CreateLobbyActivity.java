package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketInstance;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.IStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.OnStateChangedListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

public class CreateLobbyActivity extends AActivityWithStateManager implements View.OnClickListener  {

    private StateManager stateManager;

    private EditText lobbyNameEditText, passwordEditText, timeEditText, playerNameEditText;
    private Button createLobbyButton;
    private String playerName, lobbyName, lobbyPassword, lobbyTime;
    private Intent goToLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        playerNameEditText = (EditText)findViewById(R.id.playername_edittext);
        lobbyNameEditText = (EditText)findViewById(R.id.lobbyname_edittext);
        passwordEditText = (EditText)findViewById(R.id.lobbypassword_edittext);
        timeEditText = (EditText)findViewById(R.id.lobbytime_edittext);

        createLobbyButton = (Button)findViewById(R.id.createLobby);
        createLobbyButton.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        socket = SocketInstance.restartSocket();
        goToLobby = new Intent(this, LobbyActivity.class);
        playerName = playerNameEditText.getText().toString();
        lobbyName = lobbyNameEditText.getText().toString();
        lobbyPassword = passwordEditText.getText().toString();
        lobbyTime = timeEditText.getText().toString();
        socket.on("lobbyExists", lobbyExists);
        socket.on("getLobbyId", getLobbyId);
        socket.on("playerNameUnavailable", playerNameUnavailable);
        socket.emit("createLobby", playerName, lobbyName, lobbyPassword, lobbyTime);
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

            int lobbyID = (int) args[0];

            // Navigate to lobby
            goToLobby.putExtra("playerName", playerName);
            goToLobby.putExtra("isHost", true);
            goToLobby.putExtra("lobbyID", lobbyID);

            stateManager.set(StateManagerKey.PLAYER_NAME, playerName);
            stateManager.set(StateManagerKey.IS_HOST, true);
            stateManager.set(StateManagerKey.LOBBY_ID, lobbyID);

            startActivity(goToLobby);
        }
    };

    Emitter.Listener playerNameUnavailable = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            showToast("Player name unavailable");
        }
    };

    private void showToast(final String msg){
        final CreateLobbyActivity context = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }catch(Exception ex){
                    Log.d("Create lobby","error showing toast");
                }
            }
        });
    }


}


