package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateChangedArgs;

public class EndActivity extends AActivityWithStateManager implements View.OnClickListener {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = EndActivity.class.getSimpleName();

    private Button leave;
    private TextView finalMessageTextView;
    private Integer LobbyID = 0;
    private String finalMessage;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        finalMessage = gameController.getString(EStateManagerKey.END_MESSAGE);
        finalMessageTextView=(TextView) findViewById(R.id.finalMessageTextView);
        finalMessageTextView.setText(finalMessage);
        leave = (Button) findViewById(R.id.leaveButton);


        leave.setOnClickListener(this);
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leaveButton:
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }


}
