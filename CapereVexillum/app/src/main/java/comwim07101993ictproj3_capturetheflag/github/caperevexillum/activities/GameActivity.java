package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
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

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.CooldownTimerFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.QuizFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.ScoreFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.Utils;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.GameTimer;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.OnGameTimerFinishedListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.IFlagSync;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.BeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.IBeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.MockBeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.OnScanListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;


public class GameActivity extends AActivityWithStateManager implements OnScanListener, IFlagSync {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = GameActivity.class.getSimpleName();


    private static final boolean USE_BLUETOOTH = true;
    private static final int GAME_DURATION_IN_MINUTES = 30;

    public float gameTime;

    // TODO Someone: create in socket
    public static final String MY_TEAM = Team.TEAM_ORANGE;

    /* ------------------------- View elements ------------------------- */

    private TextView timerTextView;
    private RelativeLayout quizLayout;
    private ConstraintLayout mainLayout;
    private CooldownTimerFragment cooldownFragment;
    private QuizFragment quizFragment;
    private ScoreFragment scoreFragment;

    public CooldownTimerFragment cooldownUpdatable;

    private boolean isStartQuizActivityOpen = false;

    /* ------------------------- Beacon scanner ------------------------- */

    private static final int START_QUIZ_ACTIVITY = 70;
    private static final double SIGNAL_THRESHOLD = 2;
    private IBeaconScanner beaconScanner;
    private boolean beaconWithCooldown = false;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void makeAppFullScreen() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mainLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void showQuiz(boolean showQuestion) {

        isStartQuizActivityOpen = false;
        if (showQuestion) {
            mainLayout.setVisibility(View.INVISIBLE);
            quizLayout.setVisibility(View.VISIBLE);

        } else {
            mainLayout.setVisibility(View.VISIBLE);
            quizLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void hideCooldownFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(cooldownFragment);

        try {
            ft.commit();
        } catch (Exception e) {
            // TODO SOMEONE: solve the error that comes when taskmanager is opened on the device
            Log.e(TAG, e.getMessage());
        }
    }

    public void showCooldownFragment(Date flagResult) {
        Date now = Calendar.getInstance().getTime();
        // TODO HAKAN: Polish code
        long cooldownLeft = flagResult.getTime() - now.getTime();

        beaconWithCooldown = (cooldownLeft > 1010);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (!beaconWithCooldown) {
            ft.hide(cooldownFragment);
        } else {

            if (cooldownUpdatable != null) {
                cooldownUpdatable.update((float) (cooldownLeft / 1000));
            }
            ft.show(cooldownFragment);
        }

        try {
            ft.commit();
        } catch (Exception e) {
            // TODO SOMEONE: solve the error that comes when taskmanager is opened on the device
            Log.e(TAG, e.getMessage());
        }
    }

    /* ------------------------- Init methods ------------------------- */

    @SuppressWarnings("UnusedAssignment")
    private void initBeaconScanner() {

        if (false&&USE_BLUETOOTH && BeaconScanner.isBLESupported(this)) {
            BeaconScanner.askPermissions(this);
            BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (bluetoothManager != null) {
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                beaconScanner = new BeaconScanner(bluetoothAdapter);
            }
        } else {
            beaconScanner = new MockBeaconScanner();
        }

        beaconScanner.addOnScanListener(this);
        beaconScanner.start();
    }

    // TODO Safe remove this method
    private void initSocket() {



        Flags flags = (Flags) stateManager.get(StateManagerKey.FLAGS);

        flags.setSyncFlagListener(this);

        stateManager.set(StateManagerKey.FLAGS, flags);
    }

    private void initView() {

        quizLayout = (RelativeLayout) findViewById(R.id.quizLayout);
        mainLayout = (ConstraintLayout) findViewById(R.id.content);
        quizFragment = (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment);
        quizFragment.addActivity(this);
        scoreFragment = (ScoreFragment) getFragmentManager().findFragmentById(R.id.scoreFragment);

        cooldownFragment = (CooldownTimerFragment) getFragmentManager().findFragmentById(R.id.cooldownFragment);
    }
    GameTimer gt;
    private void initGameTimer() {

        timerTextView = (TextView) findViewById(R.id.txtTimeLeft);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(cooldownFragment);
        gt=new GameTimer(timerTextView,30);
        ft.commit();
    }

    /* ------------------------- Lifecycle methods ------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        try{
            initBeaconScanner();
        }catch(Exception ex){
            Log.e("gameActivity",ex.getMessage());
        }

        try{
            initView();
            initGameTimer();

        }catch(Exception ex){
            Log.e("gameActivity",ex.getMessage());
        }


        //showQuiz(false);
       // makeAppFullScreen();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initStateManager();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case BeaconScanner.REQUEST_ENABLE_BT:
                if (resultCode == RESULT_CANCELED) {
                    Utils.toast(getApplicationContext(), "Please turn on Bluetooth");
                } else {
                    beaconScanner.start();
                }
                break;

            case START_QUIZ_ACTIVITY:
                isStartQuizActivityOpen = false;
                if (resultCode == 1) {
                    showQuiz(true);
                }
        }
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

        if ((
                beacon.getRelativeRssi() > SIGNAL_THRESHOLD
                ||quizLayout.getVisibility() == View.VISIBLE
                )) {
            return;
        }

        Flag flag = ((Flags) stateManager.get(StateManagerKey.FLAGS)).findFlag(beacon, MY_TEAM);

        if (flag != null && flag.getCooldown()) {
            showCooldownFragment(flag.getCooldownTime());
        } else {
            //hideCooldownFragment();
            beaconWithCooldown = false;
            quizFragment.setCurrentBeacon(beacon);

            if (!isStartQuizActivityOpen) {
                Intent intent = new Intent(this, StartQuizActivity.class);
                startActivityForResult(intent, START_QUIZ_ACTIVITY);
                isStartQuizActivityOpen = true;
            }
        }
    }

    /* ------------------------- syncFlags ------------------------- */

    @Override
    public void syncFlags() {
        Flags flags = (Flags) stateManager.get(StateManagerKey.FLAGS);
        int redFlags = flags.getFlagByTeam(Team.TEAM_ORANGE);
        int greenFlags = flags.getFlagByTeam(Team.TEAM_GREEN);
        scoreFragment.setScores(redFlags, greenFlags);
    }

    /* ------------------------- Getters ------------------------- */

    public StateManager getStateManager() {
        return stateManager;
    }


    /* ------------------------------------------------------------- */
    /* ------------------------- LISTENERS ------------------------- */
    /* ------------------------------------------------------------- */

    /* ------------------------- SOCKET ------------------------- */

    Emitter.Listener becomeHost = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    Emitter.Listener startTimer = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            stateManager.set(StateManagerKey.GAME_STARTED, true);
            String request = (String) args[0];
            gameTime = Float.parseFloat(request);
            startTimeHandler.obtainMessage(1).sendToTarget();

        }
    };
    Handler startTimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GameTimer gameTimer = new GameTimer(timerTextView, gameTime);
            gameTimer.addListener(new OnGameTimerFinishedListener() {
                @Override
                public void OnGameTimerFinished() {
                    Flags flags = (Flags) stateManager.get(StateManagerKey.FLAGS);
                    Utils.toast(getApplicationContext(), "Game Finished, you have captured "
                            + flags.getRegisteredFlags().size() +
                            " flags");
                    timerTextView.setText(R.string.finished);
                    stateManager.set(StateManagerKey.GAME_STARTED, false);
                }
            });
        }
    };
}
