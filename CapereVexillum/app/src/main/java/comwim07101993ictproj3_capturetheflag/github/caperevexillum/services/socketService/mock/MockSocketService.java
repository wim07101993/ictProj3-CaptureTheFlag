package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock;

import android.support.annotation.NonNull;

import com.github.nkzawa.socketio.client.Socket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.AObservable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketOnKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketValueChangedArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;

/**
 * Created by wimva on 30/11/2017.
 */

public class MockSocketService
        extends AObservable
        implements ISocketService<ISocketKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = MockSocketService.class.getSimpleName();

    private Map<ISocketKey, Object> values = new HashMap<>();

    private List<Lobby> lobbies = new Vector<>();
    private List<Player> players = new Vector<>();

    private boolean connected;

    private String serverIPAddress;
    private int serverPort;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public MockSocketService(String serverIPAddress, int serverPort) {

        this.serverIPAddress = serverIPAddress;
        this.serverPort = serverPort;

        initSocket();
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void initSocket() {
        connect();
    }

    @Override
    public void connect() {
        connected = true;
    }

    @Override
    public void Send(final ISocketKey key, final Object value) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1);
                } catch (InterruptedException ignored) {

                }

                if (key instanceof ESocketEmitKey) {
                    handleSocketEmit((ESocketEmitKey) key, value);
                } else if (key instanceof ESocketOnKey) {
                    handleSocketOn((ESocketOnKey) key, value);
                }
            }
        };
        t.start();
    }

    private void handleSocketEmit(ESocketEmitKey key, Object value) {
        switch (key) {

            case ASK_FLAGS:
            case ASK_TEAMS:
            case ASK_TIME:
                notifyObservers(values.get(key));
                break;
            case CAPTURE_FLAG:
                break;
            case JOIN_LOBBY:
                break;
            case LOBBY_SETTINGS:
                createLobby(value);
                break;
        }
    }

    private void createLobby(Object value) {
        if (!(value instanceof String)) {
            return;
        }

        LobbySettings lobbySettings = new LobbySettings();
        lobbySettings.deserialize((String) value);

        if (getLobby(lobbySettings.getName()) != null) {
            lobbySettings.setName(null);
            notifyObservers(new SocketValueChangedArgs(ESocketOnKey.WAS_LOBBY_CREATED, lobbySettings.serialize()));
        } else {
            lobbies.add(new Lobby(
                    lobbies.size(),
                    lobbySettings.getName(),
                    lobbySettings.getPassword(),
                    lobbySettings.getTotalGameTime(),
                    new Vector<Player>()));

            joinLobby(lobbySettings);
        }
    }

    private void joinLobby(LobbySettings lobbySettings) {
        Lobby lobby = getLobby(lobbySettings.getName());

        if (lobby == null) {
            lobbySettings.setName(null);
        } else {
            lobby.addPlayer(new Player(lobbySettings.getHostName()));
            lobbySettings.setId(lobby.getId());
        }

        notifyObservers(new SocketValueChangedArgs(ESocketOnKey.WAS_LOBBY_CREATED, lobbySettings.serialize()));
    }

    private void handleSocketOn(ESocketOnKey key, Object value) {
        switch (key) {

            case HOST:
                break;
            case WAS_LOBBY_CREATED:
                break;
            case START_GAME:
                break;
            case RESYNC_TIME:
                break;
            case SYNC_FLAGS:
                break;
            case SYNC_TEAM:
                break;
            case GET_LOBBY_ID:
                break;
        }
    }

    /* ------------------------- GETTERS ------------------------- */

    @Override
    public String getServerAddress() {
        return serverIPAddress;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Deprecated
    @Override
    public Socket getSocket() {
        return null;
    }

    @NonNull
    @Override
    public String getTAG() {
        return TAG;
    }

    private Lobby getLobby(String name) {
        if (name == null) {
            return null;
        }

        for (Lobby lobby : lobbies) {
            if (lobby.getName().equals(name)) {
                return lobby;
            }
        }

        return null;
    }

    private Player getPlayer(String playerName, String lobbyName) {
        Lobby lobby = getLobby(lobbyName);

        if (lobby == null) {
            return null;
        }

        for (Player player : lobby.getPlayers()) {
            if (player.getName() == playerName) {
                return player;
            }
        }

        return null;
    }
}
