package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.StartQuizFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.Utils;

import static comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey.QUIZ_STARTER;

public class StartQuizActivity extends AppCompatActivity implements View.OnClickListener{

    //private Fragment fragment;
    View view;
    // List for listeners for changes
    private StartQuizFragment.OnFragmentInteractionListener mListener;

    // Instance buttons
    private Button YesButton;
    private Button NoButton;
    private GameActivity gameActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        YesButton = (Button) findViewById(R.id.YesButton);
        NoButton = (Button) findViewById(R.id.NoButton);

        // set the listener for the buttons
        YesButton.setOnClickListener(this);
        NoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        if (view == YesButton) {


            setResult(1, intent);

            finish();
        }
        else if (view == NoButton) {
            setResult(2, intent);
            finish();
        }
    }

}
