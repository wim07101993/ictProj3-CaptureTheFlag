package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.Manifest;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.CooldownTimerFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.QuizFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.ScoreFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.Utils;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.GameTimer;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.OnGameTimerFinishedListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.IFlagSync;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.BeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.OnScanListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.IStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.OnStateChangedListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

/**
 * Activity for the main activity.
 */
public class GameActivity extends AppCompatActivity implements OnScanListener,
        OnGameTimerFinishedListener, OnStateChangedListener<StateManagerKey>, IFlagSync {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private final String serverURL = "http://192.168.137.1:4040";

    private final static String TAG = GameActivity.class.getSimpleName();
    private boolean startquiz = false;
    private GameTimer gameTimer;
    private StateManager stateManager;

    /* ------------------------- View elements ------------------------- */

    private TextView timerTextView;
    private View mContentView;
    private RelativeLayout quizLayout;
    private ConstraintLayout mainLayout;
    private CooldownTimerFragment cooldownFragment;
    private QuizFragment quizFragment;
    public CooldownTimerFragment cooldownUpdatable;
    private boolean startActivityOpen = false;
    /* ------------------------- Beacon scanner ------------------------- */
    private static final int START_QUIZ_ACTIVITY = 70;
    private static final int REQUEST_ENABLE_BT = 1;
    private static double SIGNAL_THRESHOLD = 2;
    private BeaconScanner beaconScanner;
    private BluetoothAdapter bluetoothAdapter;
    public Flags flags;
    private Socket socket;
    private long cooldownLeft = 0;
    private boolean beaconWithCooldown = false;
    private boolean host = false;
    private boolean gameStarted = false;
    private int gameDurtationInMinutes = 30;
    public float timerValue;
    public OnGameTimerFinishedListener onGameTimerFinishedListener;
    ScoreFragment scoreFragment;
    /* ------------------------- Teams ------------------------- */

    public String myTeam = Team.TEAM_ORANGE;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    //this code gets triggered when the server assigns you as  the host

    Emitter.Listener becomeHost = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            host = true;
            socket.emit("start", gameDurtationInMinutes);
        }
    };
    Emitter.Listener startTimer = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            gameStarted = true;
            String request = (String) args[0];
            float time = Float.parseFloat(request);
            timerValue = time;
            startTimeHandler.obtainMessage(1).sendToTarget();

        }
    };
    Handler startTimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            gameTimer = new GameTimer(timerTextView, timerValue, socket);
            gameTimer.addListener(onGameTimerFinishedListener);
        }
    };

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

    public void showQuiz(boolean showQuestion) {
        startActivityOpen = false;
        if (showQuestion) {
            mainLayout.setVisibility(View.INVISIBLE);
            quizLayout.setVisibility(View.VISIBLE);

        } else {
            mainLayout.setVisibility(View.VISIBLE);
            quizLayout.setVisibility(View.INVISIBLE);

            stateManager.set(StateManagerKey.CURRENT_BEACON, null);
        }
    }


    private void checkIfNecessaryKeysExist() {
        if (stateManager.get(StateManagerKey.FLAGS) == null) {
            stateManager.set(StateManagerKey.FLAGS, new Flags());
        }
        if (stateManager.get(StateManagerKey.MY_TEAM) == null) {
            stateManager.set(StateManagerKey.MY_TEAM, Team.TEAM_ORANGE);
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
            } else {
                beaconScanner.start();
            }
        }
        if (requestCode == START_QUIZ_ACTIVITY) {
            startquiz = false;
            if (resultCode == 1) {
                showQuiz(true);
            }

        }
    }

    /* ------------------------- Lifecycle methods ------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        flags = new Flags();
        checkIfBLEIsSupported();
        askPermissions();
        onGameTimerFinishedListener = this;
        stateManager = new StateManager(
                PreferenceManager.getDefaultSharedPreferences(this)
        );
        checkIfNecessaryKeysExist();
        try {
            socket = IO.socket(serverURL);
            socket.connect();
        } catch (URISyntaxException e) {

        }
        socket.on("host", becomeHost);
        socket.on("start", startTimer);
        // Flags flags =(Flags) stateManager.get(StateManagerKey.FLAGS);
        flags.addSocket(socket);
        flags.setSyncFlagListener(this);
        //stateManager.set(StateManagerKey.FLAGS,flags);
        timerTextView = (TextView) findViewById(R.id.txtTimeLeft);
        //gameTimer = new GameTimer(timerTextView, gameDurtationInMinutes,socket);


        mContentView = findViewById(R.id.content);
        quizLayout = (RelativeLayout) findViewById(R.id.quizLayout);
        mainLayout = (ConstraintLayout) findViewById(R.id.content);
        quizFragment = (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment);
        quizFragment.addActivity(this);
        scoreFragment = (ScoreFragment) getFragmentManager().findFragmentById(R.id.scoreFragment);
        bluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        beaconScanner = new BeaconScanner(bluetoothAdapter);
        beaconScanner.addOnScanListener(this);
        beaconScanner.start();
        cooldownFragment = (CooldownTimerFragment) getFragmentManager().findFragmentById(R.id.cooldownFragment);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(cooldownFragment);
        ft.commit();
        showQuiz(false);

        makeAppFullScreen();
    }

    @Override
    protected void onStart() {
        stateManager.load();
        checkIfNecessaryKeysExist();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /* ------------------------- OnScanListener ------------------------- */

    @Override
    public void onScanStopped() {
    }

    @Override
    public void onScanStarted() {
    }

    @Override
    public void onBeaconFound(IBeacon beacon) {
        if (!gameStarted)
            return;
        if (startquiz)
            return;
        double beaconSignalStrength = beacon.getRelativeRssi();

        if (beaconSignalStrength < SIGNAL_THRESHOLD) {
            if (quizLayout.getVisibility() == View.VISIBLE)
                return;

            //Beacon currentBeacon = (Beacon) stateManager.get(StateManagerKey.CURRENT_BEACON);


            //Object flagResult = ((Flags) stateManager.get(StateManagerKey.FLAGS)).findFlag(beacon, (String) stateManager.get(StateManagerKey.MY_TEAM))
            Object flagResult = flags.findFlag(beacon, (String) stateManager.get(StateManagerKey.MY_TEAM));

            if ((flagResult.getClass().equals(Boolean.class))) {
                stopCooldown();
                boolean result = (Boolean) flagResult;
                if (!result) {
                    //new beacon found or beacon of enemey team found
                    beaconWithCooldown = false;
                    stateManager.set(StateManagerKey.CURRENT_BEACON, beacon);
                    quizFragment.setCurrentBeacon(beacon);
                    if (!startActivityOpen) {
                        Intent intent = new Intent(this, StartQuizActivity.class);
                        startActivityForResult(intent, START_QUIZ_ACTIVITY);
                        startquiz = true;
                        startActivityOpen = true;
                    }

                    //showQuiz(true);
                }
            } else {
                //cooldown
                coolDownFlag((Date) flagResult, beacon);
            }
        }
    }

    public void stopCooldown() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(cooldownFragment);
        ft.commit();
    }

    public void coolDownFlag(Date flagResult, IBeacon beacon) {
        Date dateCooldownLeft = flagResult;
        Date now = Calendar.getInstance().getTime();
        // TODO HAKAN: Polish code
        cooldownLeft = dateCooldownLeft.getTime() - now.getTime();

        beaconWithCooldown = (cooldownLeft > 1010);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (!beaconWithCooldown) {
            ft.hide(cooldownFragment);

            // ((Flags) stateManager.get(StateManagerKey.FLAGS)).removeBeacon(beacon);
        } else {

            if (cooldownUpdatable != null) {
                cooldownUpdatable.update((float) (cooldownLeft / 1000));
            }
            ft.show(cooldownFragment);
        }
        ft.commit();

    }
    /* ------------------------- OnGameTimerFinishedListener ------------------------- */

    @Override
    public void OnGameTimerFinished() {
        Utils.toast(getApplicationContext(), "Game Finished, you have captured "
                + flags.getRegisteredFlags().size() +
                " flags");
        timerTextView.setText("Finished");
        gameStarted = false;

    }

    /* ------------------------- OnStateChangedListener ------------------------- */

    @Override
    public void stateChanged(List<StateManagerKey> changedKeys, IStateManager manager) {
        if (changedKeys.contains(StateManagerKey.CURRENT_BEACON)) {

        }
    }

    /* ------------------------- Getters ------------------------- */

    public StateManager getStateManager() {
        return stateManager;
    }

    public long getCooldownLeft() {
        return cooldownLeft;
    }

    @Override
    public void syncFlags() {
        int redFlags = flags.getFlagByTeam(Team.TEAM_ORANGE);
        int greenFlags = flags.getFlagByTeam(Team.TEAM_GREEN);
        scoreFragment.setScores(redFlags, greenFlags);
    }
}
