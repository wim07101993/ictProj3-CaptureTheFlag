package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Player;

public class LobbyActivity extends AActivityWithStateManager implements View.OnClickListener {
    // UI elements
    private Button joinTeamGreenButton;
    private Button joinTeamOrangeButton;
    private Button startGameButton;
    private Button leaveLobbyButton;

    private ListView teamOrangeListView;
    private ListView teamGreenListView;
    private ListView noTeamListView;

    // Socket (to be replaced with socket in background service??)


    private Boolean isHost = true;
    private String playerName = "";
    private int lobbyID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Link UI with code
        joinTeamGreenButton = (Button) findViewById(R.id.teamGreenJoinButton);
        joinTeamOrangeButton = (Button) findViewById(R.id.teamOrangeJoinButton);
        startGameButton = (Button) findViewById(R.id.startButton);
        leaveLobbyButton = (Button) findViewById(R.id.leaveButton);

        teamOrangeListView = (ListView) findViewById(R.id.teamOrangeListView);
        teamGreenListView = (ListView) findViewById(R.id.teamGreenListView);
        noTeamListView = (ListView) findViewById(R.id.noTeamListView);

        // Set listener for all buttons to this
        joinTeamGreenButton.setOnClickListener(this);
        joinTeamOrangeButton.setOnClickListener(this);
        startGameButton.setOnClickListener(this);
        leaveLobbyButton.setOnClickListener(this);

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        playerName = extras.getString("playerName", "PLAYERNAME_NOT_FOUND");
        isHost = extras.getBoolean("isHost", false);
        lobbyID = extras.getInt("lobbyID", 0);

        // Set startbutton visible for host
        if (isHost) {
            startGameButton.setVisibility(View.VISIBLE);
        } else {
            startGameButton.setVisibility(View.INVISIBLE);
        }


            socket.on("timeStart", startGameActivityListener);
            socket.on("getPlayersResult", getPlayersResult);
            socket.on("leaveLobby", leaveLobby);
            socket.emit("getPlayers", lobbyID);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.teamGreenJoinButton:
                joinTeamGreen();
                break;

            case R.id.teamOrangeJoinButton:
                joinTeamOrange();
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
        if (socket != null) {
            socket.emit("joinTeam", lobbyID, "orange", playerName);
        }
    }

    private void joinTeamGreen() {
        if (socket != null) {
            socket.emit("joinTeam", lobbyID, "green", playerName);
        }
    }

    private void startGame() {


            socket.emit("startLobby", lobbyID);

    }

    private void leaveLobby() {
        // Tell the socket that we're leaving this lobby
        if (socket != null && socket.connected()) {
            if (isHost) {
                socket.emit("hostLeft", lobbyID);
            } else {
                socket.emit("leaveLobby", lobbyID, playerName);
            }
            socket.disconnect();
        }
        finish();
    }

    Emitter.Listener getPlayersResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String players = (String) args[0];
            Gson gson = new Gson();
            ArrayList<Player> playerList = gson.fromJson(players, new TypeToken<ArrayList<Player>>() {
            }.getType());

            ArrayList<Player> teamGreen = new ArrayList<Player>();
            ArrayList<Player> teamOrange = new ArrayList<Player>();
            ArrayList<Player> noTeam = new ArrayList<Player>();

            for (Player p : playerList) {
                switch (p.getTeam().getTeamname()) {
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

    Emitter.Listener leaveLobby = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            showToast("Host left, leaving lobby...");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    leaveLobby();
                }
            });
        }
    };
    public LobbyActivity parent;
    public void startGameActivity(){

        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);

    }
    Emitter.Listener startGameActivityListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            startGameActivity();
        }
    };

    private void updateUI(ArrayList<Player> playerList, final ListView listView) {
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

    private void showToast(final String msg) {
        final LobbyActivity context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}