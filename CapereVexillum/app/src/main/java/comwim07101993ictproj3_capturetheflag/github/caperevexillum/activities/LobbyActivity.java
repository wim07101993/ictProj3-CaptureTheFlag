package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URISyntaxException;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;

public class LobbyActivity extends AppCompatActivity implements View.OnClickListener{
    // UI elements
    Button joinTeamGreenButton;
    Button joinTeamOrangeButton;
    Button startGameButton;
    Button leaveLobbyButton;
    Boolean isHost = true;

    // Socket (to be replaced with socket in background service??)
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Link UI with code
        joinTeamGreenButton = (Button)findViewById(R.id.teamGreenJoinButton);
        joinTeamOrangeButton = (Button)findViewById(R.id.teamOrangeJoinButton);
        startGameButton = (Button)findViewById(R.id.startButton);
        leaveLobbyButton = (Button)findViewById(R.id.leaveButton);

        // Set listener for all buttons to this
        joinTeamGreenButton.setOnClickListener(this);
        joinTeamOrangeButton.setOnClickListener(this);
        startGameButton.setOnClickListener(this);
        leaveLobbyButton.setOnClickListener(this);

        // Set startbutton visible for host
        if (isHost){
            startGameButton.setVisibility(View.VISIBLE);
        }
        else {
            startGameButton.setVisibility(View.INVISIBLE);
        }

        try{
            socket = IO.socket(GameActivity.SERVER_URL);
            socket.connect();
            socket.on("getPlayersResult", getPlayersResult);
        }catch(URISyntaxException e){
            Log.d("LobbyActivity", e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id){
            case R.id.teamGreenJoinButton:
                joinTeamOrange();
                socket.emit("getPlayers", 0);
                break;

            case R.id.teamOrangeJoinButton:
                joinTeamGreen();
                socket.emit("getPlayers", 0);
                break;

            case R.id.startButton:
                startGame();
                break;

            case R.id.leaveButton:
                leaveLobby();
                break;
        }
    }

    private void joinTeamOrange() {
        if(socket != null){
            socket.emit("joinTeamOrange");
        }
    }

    private void joinTeamGreen() {
        if(socket != null){
            socket.emit("joinTeamGreen");
        }
    }

    private void startGame() {
        // Start on socket
        if(socket != null){
            socket.emit("startLobby");
        }
        // Start game activity
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    private void leaveLobby() {
        // Tell the socket that we're leaving this lobby
        if(socket != null){
            socket.emit("disconnectFromLobby");
            socket.disconnect();
            // TODO Sven: navigate elsewhere?
            onBackPressed();
        }
    }

    Emitter.Listener getPlayersResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String players = (String) args[0];
            Log.d("DINGES", players);
            Gson gson = new Gson();
            List<String> playerList = gson.fromJson(players, new TypeToken<List<String>> (){}.getType());
        }
    };
}