package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

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

public class JoinLobbyActivity extends AppCompatActivity implements View.OnClickListener{

    Socket socket;

    EditText lobbyNameEditText;
    EditText lobbyPasswordEditText;
    EditText playerNameEditText;

    Button joinLobbyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);

        joinLobbyButton = (Button)findViewById(R.id.join_lobby_button);
        joinLobbyButton.setOnClickListener(this);

        lobbyNameEditText = (EditText)findViewById(R.id.lobby_name_edittext);
        lobbyPasswordEditText = (EditText)findViewById(R.id.lobby_password_edittext);
        playerNameEditText = (EditText)findViewById(R.id.playername_edittext);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.join_lobby_button){
            if(socket == null){
                try{
                    socket = IO.socket(GameActivity.SERVER_URL);
                    socket.connect();
                }catch(URISyntaxException e){
                    Log.d("JoinLobbyActivity", e.getMessage());
                }
            }

            if(socket != null){
                socket.emit("checkCredentials", lobbyNameEditText.getText(), lobbyPasswordEditText.getText() , playerNameEditText.getText());
                socket.on("getLobbyId", getLobbyId);
            }
        }
    }
   Emitter.Listener getLobbyId= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("JoinLobbyActivity", "test");
        }
    };


}
