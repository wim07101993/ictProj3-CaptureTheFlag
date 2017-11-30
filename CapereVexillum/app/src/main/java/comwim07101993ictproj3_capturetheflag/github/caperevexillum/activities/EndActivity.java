package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

public class EndActivity extends AActivityWithStateManager implements View.OnClickListener{

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private Button leave;
    private Button restart;
    private Integer LobbyID = 0;
    private String playerName;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        leave = (Button) findViewById(R.id.leaveButton);
        restart = (Button) findViewById(R.id.restartButton);

        leave.setOnClickListener(this);
        restart.setOnClickListener(this);
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leaveButton:
                leaveLobby();
                break;

            case R.id.restartButton:
                restartLobby();
                break;
        }
    }

    private void restartLobby() {
        LobbyID = (Integer) stateManager.get(StateManagerKey.LOBBY_ID);
        socket.emit("restartLobby", LobbyID);
    }

    private void leaveLobby() {
        LobbyID = (Integer) stateManager.get(StateManagerKey.LOBBY_ID);
        playerName = (String) stateManager.get(StateManagerKey.PLAYER_NAME);
        socket.emit("leaveLobby", LobbyID, playerName);
    }
}
