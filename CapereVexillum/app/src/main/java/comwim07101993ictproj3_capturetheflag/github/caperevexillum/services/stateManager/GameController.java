package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Observable;
import java.util.Observer;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.LobbyActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketOnKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketValueChangedArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IGameController;

/**
 * Created by wimva on 4/12/2017.
 */

public class GameController extends StateManagerWithSocket implements IGameController {

    private Activity context;
    private static Gson gson = new Gson();


    /**
     * StateManagerWithSocket is the constructor for the class StateManagerWithSocket.
     */
    GameController() {
        super();
    }

    /**
     * StateManagerWithSocket is the constructor for the class StateManagerWithSocket.
     *
     * @param sharedPreferences the shared preferences to load and save the current state from and to.
     */
    GameController(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }


    @Override
    public void createLobby(LobbySettings lobbySettings) {
        socketService.send(ESocketEmitKey.CREATE_LOBBY, gson.toJson(lobbySettings));

        socketService.addObserver(new Observer() {
            @Override
            public synchronized void update(Observable observable, Object arg) {
                if (!(arg instanceof SocketValueChangedArgs)) {
                    return;
                }

                SocketValueChangedArgs socketArg = (SocketValueChangedArgs) arg;
                if (socketArg.getKey() != ESocketOnKey.WAS_LOBBY_CREATED) {
                    return;
                }

                socketService.deleteObserver(this);
                LobbySettings lobbySettings = gson.fromJson((String) socketArg.getArgs(), LobbySettings.class);

                if (lobbySettings.getId() != -1) {
                    setInt(EStateManagerKey.LOBBY_ID, lobbySettings.getId());
                    setString(EStateManagerKey.PLAYER_NAME, lobbySettings.getHostName());
                    setBoolean(EStateManagerKey.IS_HOST, true);

                    context.startActivity(new Intent(context, LobbyActivity.class));
                } else if (lobbySettings.getHostName() == null) {
                    showToast("Playername already exists");
                } else if (lobbySettings.getName() == null) {
                    showToast("Lobbyname already exists");
                } else {
                    showToast("Could not create lobby");
                }
            }
        });
    }

    @Override
    public void leaveLobby() {
        showToast("Host left, leaving lobby...");
        if (getBoolean(EStateManagerKey.IS_HOST)){
            socketService.send(ESocketEmitKey.HOST_LEFT, true);
        } else {
            socketService.send(ESocketEmitKey.LEAVE_LOBBY, true);
        }
    }

    @Override
    public void joinTeam(String team) {
        socketService.send(ESocketEmitKey.JOIN_TEAM, team);

        socketService.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object arg) {
                if (!(arg instanceof SocketValueChangedArgs)) {
                    return;
                }

                SocketValueChangedArgs socketArg = (SocketValueChangedArgs)arg;
                if (socketArg.getKey() != ESocketOnKey.JOINED_LOBBY){
                    return;
                }

                socketService.deleteObserver(this);
                String team = (String)socketArg.getArgs();
                if (team == null || team.equals("")){
                    setString(EStateManagerKey.MY_TEAM, team);
                }else {
                    showToast("Could not join team.");
                }
            }
        });
    }

    @Override
    public void leaveTeam() {
        joinTeam(Team.NO_TEAM);
    }

    @Override
    public void startGame() {

    }

    @Override
    public synchronized void setContext(Activity context) {
        this.context = context;
    }

    @Override
    public synchronized Activity getContext() {
        return context;
    }

    private void showToast(final String message) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.w(getTAG(), "Error while making toast" + e.getMessage());
                }
            }
        });
    }
}
