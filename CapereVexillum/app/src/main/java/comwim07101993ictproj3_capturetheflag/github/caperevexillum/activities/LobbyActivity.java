package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Player;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;

public class LobbyActivity extends AActivityWithStateManager implements View.OnClickListener {

    private static final String TAG = LobbyActivity.class.getSimpleName();

    private ListView teamOrangeListView;
    private ListView teamGreenListView;
    private ListView noTeamListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        teamOrangeListView = (ListView) findViewById(R.id.teamOrangeListView);
        teamGreenListView = (ListView) findViewById(R.id.teamGreenListView);
        noTeamListView = (ListView) findViewById(R.id.noTeamListView);

        findViewById(R.id.teamGreenJoinButton).setOnClickListener(this);
        findViewById(R.id.teamOrangeJoinButton).setOnClickListener(this);
        findViewById(R.id.leaveButton).setOnClickListener(this);
        Button startGameButton = (Button) findViewById(R.id.startButton);
        startGameButton.setOnClickListener(this);

        // Set startbutton visible for host
        if (stateManager.getBoolean(EStateManagerKey.IS_HOST)) {
            startGameButton.setVisibility(View.VISIBLE);
        } else {
            startGameButton.setVisibility(View.INVISIBLE);
        }

        stateManager.getSocketService().getSocket().on("startGame", startGameActivityListener);
        stateManager.getSocketService().getSocket().on("getPlayersResult", getPlayersResult);
        stateManager.getSocketService().getSocket().on("leaveLobby", leaveLobby);
        stateManager.getSocketService().getSocket().emit("getPlayers", stateManager.getInt(EStateManagerKey.LOBBY_ID));
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.teamGreenJoinButton:
                joinTeam(Team.TEAM_GREEN);
                break;

            case R.id.teamOrangeJoinButton:
                joinTeam(Team.TEAM_ORANGE);
                break;

            case R.id.startButton:
                startGame();
                break;

            case R.id.leaveButton:
                leaveLobby();
                break;
        }
    }

    private void joinTeam(String team) {
        if (stateManager.getSocketService().getSocket() != null) {
            stateManager.getSocketService().getSocket().emit(
                    "joinTeam",
                    stateManager.getInt(EStateManagerKey.LOBBY_ID),
                    "orange",
                    stateManager.getString(EStateManagerKey.PLAYER_NAME));
        }
    }

    private void startGame() {
        stateManager.getSocketService().getSocket().emit(
                "startLobby",
                stateManager.getInt(EStateManagerKey.LOBBY_ID));
    }

    private void leaveLobby() {
        // Tell the socket that we're leaving this lobby
        if (stateManager.getSocketService().getSocket() != null && stateManager.getSocketService().getSocket().connected()) {
            if (stateManager.getBoolean(EStateManagerKey.IS_HOST)) {
                stateManager.getSocketService().getSocket().emit("hostLeft", stateManager.getInt(EStateManagerKey.LOBBY_ID));
            } else {
                stateManager.getSocketService().getSocket().emit("leaveLobby", stateManager.getInt(EStateManagerKey.LOBBY_ID), stateManager.getString(EStateManagerKey.PLAYER_NAME));
            }
            stateManager.getSocketService().getSocket().disconnect();
        }
        finish();
    }

    Emitter.Listener getPlayersResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                String players = (String) args[0];
                Gson gson = new Gson();
                List<Player> playerList = gson.fromJson(players, new TypeToken<List<Player>>() {
                }.getType());


                ArrayList<Player> teamGreen = new ArrayList<>();
                ArrayList<Player> teamOrange = new ArrayList<>();
                ArrayList<Player> noTeam = new ArrayList<>();

                for (Player p : playerList) {

                    switch (p.getTeam().getTeamName()) {
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
                    if (p.getName().equals(stateManager.getString(EStateManagerKey.PLAYER_NAME))) {
                        stateManager.setString(EStateManagerKey.MY_TEAM, p.getTeam().getTeamName());
                    }
                }
                updateUI(noTeam, noTeamListView);
                updateUI(teamOrange, teamOrangeListView);
                updateUI(teamGreen, teamGreenListView);
            } catch (Exception ex) {
                Log.d("LobbyActivity", "can't parse response");
            }
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

    public void startGameActivity() {


        Intent i = new Intent(this, GameActivity.class);
        String team = stateManager.getString(EStateManagerKey.MY_TEAM);
        if (team.equals(EStateManagerKey.MY_TEAM.getDefaultValue())) {
            Random rand = new Random();

            int n = rand.nextInt(50) + 1;
            if (n < 25) {
                stateManager.setString(EStateManagerKey.MY_TEAM, Team.TEAM_GREEN);
            } else {
                stateManager.setString(EStateManagerKey.MY_TEAM, Team.TEAM_ORANGE);
            }
        }
        showToast("Your team is:" + team);
        i.putExtra("myTeam", team);
        startActivity(i);

    }

    Emitter.Listener startGameActivityListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                Gson gson = new Gson();
                String request = (String) args[0];
                Player[] players = gson.fromJson(request, Player[].class);
                for (Player player : players) {
                    if (player.getName().equals(stateManager.getString(EStateManagerKey.PLAYER_NAME))) {
                        stateManager.setString(EStateManagerKey.MY_TEAM, player.getTeam().getTeamName());
                    }
                }

            } catch (Exception ignored) {
            }

            startGameActivity();
        }
    };

    private void updateUI(ArrayList<Player> playerList, final ListView listView) {
        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for (Player player : playerList) {
            HashMap<String, String> map = new HashMap<>();
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