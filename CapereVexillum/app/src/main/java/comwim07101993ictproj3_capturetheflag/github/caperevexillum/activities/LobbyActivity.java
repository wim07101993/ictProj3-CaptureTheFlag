package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Player;

public class LobbyActivity extends AppCompatActivity implements View.OnClickListener{
    // UI elements
    private Button joinTeamGreenButton;
    private Button joinTeamOrangeButton;
    private Button startGameButton;
    private Button leaveLobbyButton;

    private ListView teamOrangeListView;
    private ListView teamGreenListView;
    private ListView noTeamListView;

    // Socket (to be replaced with socket in background service??)
    private Socket socket;

    private Boolean isHost = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Link UI with code
        joinTeamGreenButton = (Button)findViewById(R.id.teamGreenJoinButton);
        joinTeamOrangeButton = (Button)findViewById(R.id.teamOrangeJoinButton);
        startGameButton = (Button)findViewById(R.id.startButton);
        leaveLobbyButton = (Button)findViewById(R.id.leaveButton);

        teamOrangeListView = (ListView)findViewById(R.id.teamOrangeListView);
        teamGreenListView = (ListView)findViewById(R.id.teamGreenListView);
        noTeamListView = (ListView)findViewById(R.id.noTeamListView);

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
            // TODO Sven: LOBBY_ID uit statemanager halen
            socket.emit("getPlayers", 0);
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
        // Start on socket
        if(socket != null){
            socket.emit("startLobby");
        }
        // Start game activity
       // Intent i = new Intent(this, GameActivity.class);
       // startActivity(i);
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
            //TODO Sven of Nick: Fix this stuff
            String players = (String) args[0];
            Gson gson = new Gson();
            ArrayList<Player> playerList = gson.fromJson(players, new TypeToken<ArrayList<Player>> (){}.getType());

            ArrayList<Player> teamGreen = new ArrayList<Player>();
            ArrayList<Player> teamOrange = new ArrayList<Player>();
            ArrayList<Player> noTeam = new ArrayList<Player>();

            for(Player p : playerList){
                switch(p.getTeam().getTeamname()){
                    case "orange":
                        teamOrange.add(p);
                        break;

                    case "green":
                        teamGreen.add(p);
                        break;

                    case "no_team":
                        noTeam.add(p);
                        break;
                }
            }
            updateUI(noTeam, noTeamListView);
            updateUI(teamOrange, teamOrangeListView);
            updateUI(teamGreen, teamGreenListView);
        }
    };

    private void updateUI(ArrayList<Player> playerList, final ListView listView){
        // get the items for the feed
        ArrayList<Player> players = playerList;

        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        for (Player player : players) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("player", player.getName());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"player"};
        int[] to = {R.id.item_textview};

        // create and set the adapter
        final SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
            }
        });

    }
}