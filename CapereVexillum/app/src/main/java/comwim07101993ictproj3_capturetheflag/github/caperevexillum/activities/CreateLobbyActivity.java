package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.HardwareInfo;

public class CreateLobbyActivity extends AppCompatActivity  implements View.OnClickListener {
    private Socket socket;
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

        goToLobby = new Intent(this, LobbyActivity.class);
    }


    @Override
    public void onClick(View view) {
        playerName = playerNameEditText.getText().toString();
        lobbyName = lobbyNameEditText.getText().toString();
        lobbyPassword = passwordEditText.getText().toString();
        lobbyTime = timeEditText.getText().toString();

        if(socket == null){
            try{
                socket = IO.socket(GameActivity.SERVER_URL);
                socket.on("lobbyExists", lobbyExists);
                socket.on("getLobbyId", getLobbyId);
                socket.on("playerNameUnavailable", playerNameUnavailable);
                socket.connect();
            }catch(URISyntaxException e){
                Log.d("createlobbyactivity", e.getMessage());
            }
        }

        if(!socket.connected()){
            socket.connect();
            socket.emit("createLobby", playerName, lobbyName, lobbyPassword, lobbyTime);
        }else if(socket != null && socket.connected()){
            socket.emit("createLobby", playerName, lobbyName, lobbyPassword, lobbyTime);
        }
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
            int lobbyID = (int) args[0];

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

    private void showToast(final String msg){
        final CreateLobbyActivity context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


