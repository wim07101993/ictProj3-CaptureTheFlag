package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.QuizFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.GameTimer;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.BeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.OnScanListener;

/**
 * Activity for the main activity.
 */
public class MainActivity extends AppCompatActivity implements OnScanListener{

    private View mContentView;
    private GameTimer gameTimer;
    private TextView timerTextView ;
    private RelativeLayout quizLayout;
    private ConstraintLayout mainLayout;
    private QuizFragment quizFragment;
    private BeaconScanner beaconScanner;
    private BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerTextView = (TextView) findViewById(R.id.txtTimeLeft);
        gameTimer = new GameTimer(timerTextView,30);
        mContentView = findViewById(R.id.content);
        quizLayout = (RelativeLayout) findViewById(R.id.quizLayout);
        mainLayout = (ConstraintLayout) findViewById(R.id.content);
        quizFragment = (QuizFragment)getFragmentManager().findFragmentById(R.id.quizFragment);
        quizFragment.addActivity(this);

        bluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        beaconScanner = new BeaconScanner(bluetoothAdapter);
        beaconScanner.setScanEventListener(this);
        beaconScanner.start();

        makeAppFullScreen();
    }
    public void showQuestion(boolean showQuestion){
        if(showQuestion){
        mainLayout.setVisibility(View.INVISIBLE);
        quizLayout.setVisibility(View.VISIBLE);
            beaconScanner.stop();
        }else{
            mainLayout.setVisibility(View.VISIBLE);
            quizLayout.setVisibility(View.INVISIBLE);
            beaconScanner.start();
        }
    }


    private void makeAppFullScreen() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onScanStopped() {

    }

    @Override
    public void onScanStarted() {

    }

    @Override
    public void onBeaconFound(Beacon beacon) {
        showQuestion(true);
    }
}
