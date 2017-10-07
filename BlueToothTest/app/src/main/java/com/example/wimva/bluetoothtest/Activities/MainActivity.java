package com.example.wimva.bluetoothtest.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.wimva.bluetoothtest.Helpers.BroadcastReceiverBTState;
import com.example.wimva.bluetoothtest.Helpers.Scanner.BeaconScanner;
import com.example.wimva.bluetoothtest.Helpers.Scanner.OnScanListener;
import com.example.wimva.bluetoothtest.Helpers.Utils;
import com.example.wimva.bluetoothtest.Models.Beacon;
import com.example.wimva.bluetoothtest.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, OnScanListener {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    @SuppressWarnings("unused")
    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;
    private static final int TIME_UNTIL_NOT_FOUND = 2000;

    private Beacon closestBeacon;

    private SeekBar skbSensitivity;
    private TextView txtSensitivity;

    private Button btnScan;

    private TextView txtAddress;
    private TextView txtSignalStrength;
    private int signalThreshold = 5;

    // instance to detect when the bluetooth state changes (on/off)
    private BroadcastReceiverBTState btStateUpdateReceiver;
    // instance to scan for btle devices
    private BeaconScanner beaconBeaconScanner;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void startScan() {
        // set button text
        btnScan.setText(R.string.stop_scan);

        setClosestBeacon(null);

        // start the scanner
        beaconBeaconScanner.start();
    }

    private void stopScan() {
        btnScan.setText(R.string.scan_again);
        beaconBeaconScanner.stop();
    }

    private void checkIfBLEIsSupported() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();
        }
    }

    private void askPermissions() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Location access not granted!");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 42);
        }
    }

    private void initializeComponents() {
        // scan button
        btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);

        // sensitivity text field and seek bar
        skbSensitivity = (SeekBar) findViewById(R.id.skbSensitivity);
        skbSensitivity.setOnSeekBarChangeListener(this);
        signalThreshold = skbSensitivity.getProgress();

        txtSensitivity = (TextView) findViewById(R.id.txtSensitivity);
        txtSensitivity.setText(Integer.toString(skbSensitivity.getProgress()));

        // beacon details
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtSignalStrength = (TextView) findViewById(R.id.txtSignalStrength);
    }

    private void scheduleCleanupBeacons() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                removeClosestBeaconIfNotFound();
            }
        }, 0, TIME_UNTIL_NOT_FOUND / 2);
    }

    private void removeClosestBeaconIfNotFound() {
        if (closestBeacon == null) {
            return;
        }

        final double timeDiff = Calendar.getInstance().getTime().getTime() - closestBeacon.getLastFoundAt().getTime();

        if (timeDiff > TIME_UNTIL_NOT_FOUND) {
            Log.w(TAG, "Removing " + closestBeacon.getAddress() + " because it is not longer found");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    setClosestBeacon(null);
                }
            });
        }
    }

    /* ------------------------- SETTERS ------------------------- */

    private synchronized void setClosestBeacon(Beacon beacon) {
        closestBeacon = beacon;

        if (closestBeacon != null) {
            txtAddress.setText(closestBeacon.getAddress());
            txtSignalStrength.setText(Double.toString(Utils.round(closestBeacon.getRelativeRssi(), 2)));
        } else {
            txtAddress.setText("");
            txtSignalStrength.setText("");
        }
    }

    /* ------------------------- ACTIVITY ------------------------- */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfBLEIsSupported();
        askPermissions();

        initializeComponents();

        btStateUpdateReceiver = new BroadcastReceiverBTState(getApplicationContext());

        BluetoothAdapter btAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        beaconBeaconScanner = new BeaconScanner(btAdapter, skbSensitivity.getProgress());
        beaconBeaconScanner.setScanEventListener(this);

        scheduleCleanupBeacons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(btStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(btStateUpdateReceiver);
        stopScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED) {
                Utils.toast(getApplicationContext(), "Please turn on Bluetooth");
            }
        }
    }

    /* ------------------------- CLICK LISTENER ------------------------- */

    @Override
    public void onClick(View v) {
        // if the button is clicked => start of stop scanning
        if (v.getId() == R.id.btnScan) {
            if (!beaconBeaconScanner.isScanning()) {
                startScan();
            } else {
                stopScan();
            }
        }
    }

    /* ------------------------- SEEK BAR CHANGE ------------------------- */

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        txtSensitivity.setText(Integer.toString(skbSensitivity.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        signalThreshold = seekBar.getProgress();
        setClosestBeacon(null);
    }

    /* ------------------------- SCAN CHANGE ------------------------- */

    @Override
    public void onScanStopped() {
        Utils.toast(getApplicationContext(), "scan stopped...");
    }

    @Override
    public void onScanStarted() {
        Utils.toast(getApplicationContext(), "scan started...");
    }

    @Override
    public void onBeaconFound(Beacon beacon) {
        double newBeaconSignalStrength = beacon.getRelativeRssi();

        if (closestBeacon != null && beacon.getAddress().equals(closestBeacon.getAddress())) {
            if (newBeaconSignalStrength > signalThreshold) {
                setClosestBeacon(null);
            } else {
                setClosestBeacon(beacon);
            }
            return;
        }

        if (newBeaconSignalStrength < signalThreshold) {
            Log.w(TAG, "Closest beacon changed. New closest beacon: " + beacon.getAddress());

            if (closestBeacon == null) {
                setClosestBeacon(beacon);
            }

            //if the beacon is not detected for a certain time, it is considered not found.
            double timeDiff = Calendar.getInstance().getTime().getTime() - closestBeacon.getLastFoundAt().getTime();
            if (timeDiff > TIME_UNTIL_NOT_FOUND || newBeaconSignalStrength > closestBeacon.getRelativeRssi()) {
                setClosestBeacon(beacon);
            }
        }
    }
}
