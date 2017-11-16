package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;

public class LobbyActivity extends AppCompatActivity implements View.OnClickListener{
    // UI elements
    Button joinTeamGreenButton;
    Button joinTeamOrangeButton;
    Button startGameButton;
    Button leaveLobbyButton;

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

        try{
            socket = IO.socket(GameActivity.SERVER_URL);
            socket.connect();
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
                break;

            case R.id.teamOrangeJoinButton:
                joinTeamGreen();
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
        //TODO Nick: verdeling overige spelers
        //TODO Nick: connectie socket
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
}
