package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.QuizFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.Utils;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.GameTimer;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.OnGameTimerFinishedListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.BeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.OnScanListener;

/**
 * Activity for the main activity.
 */
public class GameActivity extends AppCompatActivity implements OnScanListener, OnGameTimerFinishedListener {

    private final static String TAG = GameActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 1;

    private View mContentView;
    private GameTimer gameTimer;
    private TextView timerTextView;
    private RelativeLayout quizLayout;
    private ConstraintLayout mainLayout;
    private QuizFragment quizFragment;

    private BeaconScanner beaconScanner;
    private BluetoothAdapter bluetoothAdapter;
    private static double SIGNAL_THRESHOLD = 2;

    public Flags flags;
    public Beacon currentBeacon;
    public String myTeam = Team.TEAM_ORANGE;
    private long cooldownLeft=0;

    private boolean beaconWithCooldown=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ask for needed permissions
        flags = new Flags();
        // check BTLE support
        checkIfBLEIsSupported();
        askPermissions();
        timerTextView = (TextView) findViewById(R.id.txtTimeLeft);
        gameTimer = new GameTimer(timerTextView, 5);
        gameTimer.addListener(this);
        mContentView = findViewById(R.id.content);
        quizLayout = (RelativeLayout) findViewById(R.id.quizLayout);
        mainLayout = (ConstraintLayout) findViewById(R.id.content);
        quizFragment = (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment);
        quizFragment.addActivity(this);

        bluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        beaconScanner = new BeaconScanner(bluetoothAdapter);
        beaconScanner.setScanEventListener(this);
        beaconScanner.start();

        showQuestion(false);

        makeAppFullScreen();
    }

    // ----- bluetooth stuff ------

    /**
     * Checks if BT LE is supported. If not, the app is closed after showing a message.
     */
    private void checkIfBLEIsSupported() {
        // check if BT LE is supported
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            // show toast
            Utils.toast(getApplicationContext(), "BLE not supported");
            // end app
            finish();
        }
    }

    /**
     * Asks for the needed permissions
     */
    private void askPermissions() {
        // create BT intent
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // starts the activity depending on the result
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        // check for needed permissions and if they are granted, move on
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Logging
            Log.w(TAG, "Location access not granted!");
            // If not granted ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 42);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED) {
                // show toast
                Utils.toast(getApplicationContext(), "Please turn on Bluetooth");
            }else {
                beaconScanner.start();
            }
        }
    }

    // -------------
    public void showQuestion(boolean showQuestion) {
        if (showQuestion) {
            mainLayout.setVisibility(View.INVISIBLE);
            quizLayout.setVisibility(View.VISIBLE);

        } else {
            mainLayout.setVisibility(View.VISIBLE);
            quizLayout.setVisibility(View.INVISIBLE);

            currentBeacon = null;

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
        double beaconSignalStrength = beacon.getRelativeRssi();

        if(beaconSignalStrength < SIGNAL_THRESHOLD){
            if (quizLayout.getVisibility() == View.VISIBLE)
                return;

            if (!(currentBeacon == null)) {
                if (currentBeacon.getAddress().equals(beacon.getAddress()))
                {

                }

            }
            Object flagResult =flags.findFlag(beacon, myTeam);
            if((flagResult.getClass().equals(Boolean.class))){
                boolean result = (Boolean)flagResult;
                if(!result)
                {

                    beaconWithCooldown = false;
                    currentBeacon = beacon;
                    quizFragment.setCurrentBeacon(beacon);
                    showQuestion(true);
                }
            }else{

                Date dateCooldownLeft = (Date)flagResult;
                Date now = Calendar.getInstance().getTime();
                cooldownLeft = dateCooldownLeft.getTime()-now.getTime();
                beaconWithCooldown=(cooldownLeft>1010);



            }


        }
    }

    @Override
    public void OnGameTimerFinished() {
        Utils.toast(getApplicationContext(), "Game Finished, you have captured " + flags.getRegisteredFlags().size() + " flags");
        timerTextView.setText("Finished");

    }
}
