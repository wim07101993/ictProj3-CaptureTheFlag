package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;

public class EndActivity extends AActivityWithStateManager implements View.OnClickListener {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = EndActivity.class.getSimpleName();

    private Button leave;
    private Button restart;
    private Integer LobbyID = 0;
    private String playerName;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        leave = (Button) findViewById(R.id.leaveButton);
        restart = (Button) findViewById(R.id.restartButton);

        leave.setOnClickListener(this);
        restart.setOnClickListener(this);
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

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
        LobbyID = (Integer) stateManager.getInt(EStateManagerKey.LOBBY_ID);
        stateManager.getSocketService().getSocket().emit("restartLobby", LobbyID);
    }

    private void leaveLobby() {
        LobbyID = (Integer) stateManager.getInt(EStateManagerKey.LOBBY_ID);
        playerName = (String) stateManager.getString(EStateManagerKey.PLAYER_NAME);
        stateManager.getSocketService().getSocket().emit("leaveLobby", LobbyID, playerName);
    }
}
