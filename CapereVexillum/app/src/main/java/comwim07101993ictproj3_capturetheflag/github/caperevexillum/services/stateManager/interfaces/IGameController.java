package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces;

import android.app.Activity;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;

/**
 * Created by wimva on 4/12/2017.
 */

public interface IGameController
        extends IStateManager<IStateManagerKey> {

    void createLobby(LobbySettings lobbySettings);

    void leaveLobby();

    void joinTeam(String team);

    void leaveTeam();

    void startGame();

    void setContext(Activity context);

    Activity getContext();
}
