package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Player;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Players;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateChangedArgs;

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
        if (gameController.getBoolean(EStateManagerKey.IS_HOST)) {
            startGameButton.setVisibility(View.VISIBLE);
        } else {
            startGameButton.setVisibility(View.INVISIBLE);
        }

        gameController.addObserver(stateChangeObserver);
        //gameController.getSerializable(EStateManagerKey.PLAYERS);
        gameController.askPlayers();

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
                gameController.joinTeam(Team.TEAM_GREEN);
                break;

            case R.id.teamOrangeJoinButton:
                gameController.joinTeam(Team.TEAM_ORANGE);
                break;

            case R.id.startButton:
                gameController.startGame();
                break;

            case R.id.leaveButton:
                gameController.leaveLobby();
                break;
        }
    }

    public LobbyActivity parent;

    public void startGameActivity() {
        Intent i = new Intent(this, GameActivity.class);
        String team = gameController.getString(EStateManagerKey.MY_TEAM);
        if (team.equals(EStateManagerKey.MY_TEAM.getDefaultValue())) {
            Random rand = new Random();

            int n = rand.nextInt(50) + 1;
            if (n < 25) {
                gameController.setString(EStateManagerKey.MY_TEAM, Team.TEAM_GREEN);
            } else {
                gameController.setString(EStateManagerKey.MY_TEAM, Team.TEAM_ORANGE);
            }
        }
        showToast("Your team is:" + team);
        i.putExtra("myTeam", team);
        startActivity(i);

    }

    private void updateUI(List<Player> playerList, final ListView listView) {
        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for (Player player : playerList) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Player", player.getName());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"Player"};
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

    private void gameStarted(List<Player> players) {
        if (players.size() > 0) {
            gameController.setString(EStateManagerKey.MY_TEAM, players.get(0).getTeam().getTeamName());
            startGameActivity();
        }
    }

    private void gotPlayersResult(List<Player> players) {
        List<Player> teamGreen = new ArrayList<>();
        List<Player> teamOrange = new ArrayList<>();
        List<Player> noTeam = new ArrayList<>();

        for (Player p : players) {

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
            if (p.getName().equals(gameController.getString(EStateManagerKey.PLAYER_NAME))) {
                gameController.setString(EStateManagerKey.MY_TEAM, p.getTeam().getTeamName());
            }
        }
        updateUI(noTeam, noTeamListView);
        updateUI(teamOrange, teamOrangeListView);
        updateUI(teamGreen, teamGreenListView);
    }

    private Observer stateChangeObserver = new Observer() {

        @Override
        public void update(Observable observable, Object args) {
            if (!(args instanceof StateChangedArgs)) {
                return;
            }

            StateChangedArgs stateChangedArgs = (StateChangedArgs) args;
            switch ((EStateManagerKey) stateChangedArgs.getKey()) {
                case GAME_STARTED:
                    gameStarted((List<Player>) stateChangedArgs.getNewValue());
                    break;
                case PLAYERS:
                    Players players = (Players) stateChangedArgs.getNewValue();
                    gotPlayersResult(players);
                    break;
                case LEAVE_LOBBY:
                    finishActivity();
                    break;
            }
        }
    };
    public void finishActivity(){
        Handler myHandler = new Handler(Looper.getMainLooper());

        myHandler.post( new Runnable() {
            public void run() {
                 finish();
            }
        } );
    }
}