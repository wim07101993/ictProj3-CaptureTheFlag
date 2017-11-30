package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.bases.AActivityWithStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.CooldownTimerFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.OnlineQuizFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments.ScoreFragment;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.Utils;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.GameTimer;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer.OnGameTimerFinishedListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.Variables;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.BeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.IBeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.MockBeaconScanner;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner.OnScanListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateChangedArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;


public class GameActivity extends AActivityWithStateManager implements OnScanListener, Observer {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = GameActivity.class.getSimpleName();

    Beacon currentBeacon;
    private static final boolean USE_BLUETOOTH = false;
    private static final int GAME_DURATION_IN_MINUTES = 30;

    public float gameTime;

    // TODO Someone: create in socket
    public String MY_TEAM = Team.NO_TEAM;

    /* ------------------------- View elements ------------------------- */

    private TextView timerTextView;
    private RelativeLayout quizLayout2;
    private ConstraintLayout mainLayout;
    private CooldownTimerFragment cooldownFragment;
    private OnlineQuizFragment onlineQuizFragment;
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
    public String getTeam() {
        return MY_TEAM;
    }

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
            onlineQuizFragment.getQuestions();
            quizLayout2.setVisibility(View.VISIBLE);

        } else {
            mainLayout.setVisibility(View.VISIBLE);
            quizLayout2.setVisibility(View.INVISIBLE);
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

        if (USE_BLUETOOTH && BeaconScanner.isBLESupported(this)) {
            BeaconScanner.askPermissions(this);
            BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (bluetoothManager != null) {
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                beaconScanner = new BeaconScanner(bluetoothAdapter);
            }
        } else if (Variables.DEBUG) {
            beaconScanner = new MockBeaconScanner();
        }

        beaconScanner.addOnScanListener(this);
        beaconScanner.start();
    }

    // TODO Safe remove this method
//    private void initSocket() {
//        Flags flags = (Flags) stateManager.getSerializable(EStateManagerKey.FLAGS);
//        //flags.setSyncFlagListener(this);
//        //flags.startSocketListener();
//        stateManager.setSerializable(EStateManagerKey.FLAGS, flags);
//    }

    private void initView() {

        quizLayout2 = (RelativeLayout) findViewById(R.id.quizLayout2);
        mainLayout = (ConstraintLayout) findViewById(R.id.content);
        onlineQuizFragment = (OnlineQuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment2);
        onlineQuizFragment.addActivity(this);
        scoreFragment = (ScoreFragment) getFragmentManager().findFragmentById(R.id.scoreFragment);

        cooldownFragment = (CooldownTimerFragment) getFragmentManager().findFragmentById(R.id.cooldownFragment);
        Bundle extras = getIntent().getExtras();
        MY_TEAM = extras.getString("myTeam", Team.NO_TEAM);
    }

    GameTimer gt;

    private void initGameTimer() {

        timerTextView = (TextView) findViewById(R.id.txtTimeLeft);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(cooldownFragment);
        // TODO WIM: get statemanager out of gametimer
        gt = new GameTimer(stateManager, timerTextView, 30);
        ft.commit();
    }

    /* ------------------------- Lifecycle methods ------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        try {
            initBeaconScanner();
        } catch (Exception ex) {
            Log.e("gameActivity", ex.getMessage());
        }

        try {
            initView();
            initGameTimer();
            //initSocket();
            stateManager.addObserver(this);

        } catch (Exception ex) {
            Log.e("gameActivity", ex.getMessage());
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
        if (currentBeacon != null) {
            if (currentBeacon.equals(beacon)) {
                return;
            }
        }

        if ((beacon.getRelativeRssi() > SIGNAL_THRESHOLD || quizLayout2.getVisibility() == View.VISIBLE)) {
            return;
        }

        Flag flag = ((Flags) stateManager.getSerializable(EStateManagerKey.FLAGS)).find(beacon);
        if (flag != null) {
            //it's a nested if because get team would return on null
            //return if my team already has the flag
            if (flag.getTeam().equals(MY_TEAM)) {
                hideCooldownFragment();
                return;
            }
            //return if i can't capture the flag
            if (flag.getCooldown()) {
                showCooldownFragment(flag.getCooldownTime());
                return;
            }
        }

        beaconWithCooldown = false;
        if (flag == null) {
            flag = new Flag(beacon);
            flag.setTeam(Team.NO_TEAM);
        }
        onlineQuizFragment.setCurrentFlag(flag);
        if (!isStartQuizActivityOpen) {
            Intent intent = new Intent(this, StartQuizActivity.class);
            startActivityForResult(intent, START_QUIZ_ACTIVITY);
            isStartQuizActivityOpen = true;
        }

    }


    /* ------------------------- Getters ------------------------- */

    public StateManager getStateManager() {
        return stateManager;
    }

    @Override
    protected String getTAG() {
        return TAG;
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
            stateManager.setBoolean(EStateManagerKey.GAME_STARTED, true);
            String request = (String) args[0];
            gameTime = Float.parseFloat(request);
            startTimeHandler.obtainMessage(1).sendToTarget();

        }
    };
    Handler startTimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GameTimer gameTimer = new GameTimer(stateManager, timerTextView, gameTime);
            gameTimer.addListener(new OnGameTimerFinishedListener() {
                @Override
                public void OnGameTimerFinished() {
                    Flags flags = (Flags) stateManager.getSerializable(EStateManagerKey.FLAGS);
                    Utils.toast(getApplicationContext(), "Game Finished, you have captured "
                            + flags.size() +
                            " flags");
                    timerTextView.setText(R.string.finished);
                    stateManager.setBoolean(EStateManagerKey.GAME_STARTED, false);
                    startEndActivity();
                    stateManager.setBoolean(EStateManagerKey.GAME_STARTED, false);
                }
            });
        }
    };

    private void startEndActivity() {
        Intent endIntent = new Intent(this, EndActivity.class);
        startActivity(endIntent);
    }

    @Override
    public void update(Observable observable, Object args) {
        if (!(args instanceof StateChangedArgs)) {
            return;
        }

        IStateManagerKey key = ((StateChangedArgs) args).getKey();

        if (key == EStateManagerKey.FLAGS) {
            try {
                Flags flags = (Flags) stateManager.getSerializable(EStateManagerKey.FLAGS);
                int redFlags = flags.getNumberOfFlagsOfTeam(Team.TEAM_ORANGE);
                int greenFlags = flags.getNumberOfFlagsOfTeam(Team.TEAM_GREEN);
                scoreFragment.setFlags(redFlags, greenFlags);
            } catch (Exception err) {
                Log.e("Lobby activity", "show toast");
            }
        }
    }
}
