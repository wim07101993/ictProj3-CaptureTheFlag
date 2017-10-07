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
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.wimva.bluetoothtest.Helpers.BroadcastReceiverBTState;
import com.example.wimva.bluetoothtest.Helpers.Scanner.BeaconScanner;
import com.example.wimva.bluetoothtest.Helpers.Scanner.OnScanListener;
import com.example.wimva.bluetoothtest.Helpers.Utils;
import com.example.wimva.bluetoothtest.Models.Beacon;
import com.example.wimva.bluetoothtest.R;
import com.example.wimva.bluetoothtest.Views.BeaconsListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

    // hash map to store all found bt devices and be able to search quickly trough them
    private HashMap<String, Beacon> btDevicesHashMap;
    // list of all devices to show in view
    private ArrayList<Beacon> btDevicesArrayList;
    // adapter to show list of bt devices in view
    private Beacon ClosestBeacon;
    private BeaconsListAdapter adapter;

    private Button btnScan;
    private SeekBar skbSensitivity;
    private TextView txtSensitivity;

    private Handler handler = new Handler();

    // instance to detect when the bluetooth state changes (on/off)
    private BroadcastReceiverBTState btStateUpdateReceiver;
    // instance to scan for btle devices
    private BeaconScanner beaconBeaconScanner;

    private int signalThreshold = -75;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void startScan() {
        // set button text
        btnScan.setText(R.string.stop_scan);

        // clear device list
        btDevicesArrayList.clear();
        btDevicesHashMap.clear();

        // update view
        adapter.notifyDataSetChanged();

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
        btDevicesHashMap = new HashMap<>();
        btDevicesArrayList = new ArrayList<>();

        adapter = new BeaconsListAdapter(this, R.layout.btle_device_list_item, btDevicesArrayList);

        // scan button
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(this);

        // sensitivity text field and seek bar
        skbSensitivity = (SeekBar) findViewById(R.id.skb_sensitivity);
        skbSensitivity.setOnSeekBarChangeListener(this);
        txtSensitivity = (TextView) findViewById(R.id.txt_sensitivity);
        txtSensitivity.setText(Integer.toString(-skbSensitivity.getProgress()));

        // list-view to show all device in
        ListView listView = new ListView(this);
        listView.setAdapter(adapter);

        // add list-view to view
        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);
    }

    private void scheduleCleanUpUnFoundBeacons() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int countBefore = btDevicesArrayList.size();

                for (Beacon b : btDevicesArrayList) {
                    long lastFoundAt = b.getLastFoundAt().getTime();
                    long now = Calendar.getInstance().getTime().getTime();
                    long timeDiff = Math.abs(lastFoundAt - now);

                    if (timeDiff > 900){
                        btDevicesHashMap.remove(b.getAddress());
                        btDevicesArrayList.remove(b);
                    }
                }

                if (countBefore > btDevicesArrayList.size()){
                    adapter.notifyDataSetChanged();
                }
            }
        }, 0, 1000);
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

        //scheduleCleanUpUnFoundBeacons();
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
        if (v.getId() == R.id.btn_scan) {
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
        txtSensitivity.setText(Integer.toString(-skbSensitivity.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        signalThreshold = -seekBar.getProgress();
        btDevicesHashMap.clear();
        btDevicesArrayList.clear();

        adapter.notifyDataSetChanged();
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
        String address = beacon.getAddress();
        int power = beacon.getPower();

        // if hashmap doesn't contain beacon, add it
        // else update the signal strength
        if (!btDevicesHashMap.containsKey(address)) {
            if (power > signalThreshold) {
                btDevicesHashMap.put(address, beacon);
                btDevicesArrayList.add(beacon);
            } else {
                btDevicesHashMap.remove(address);
                btDevicesArrayList.remove(address);
            }
        } else if (power > signalThreshold) {
            btDevicesHashMap.get(address).setRssi(beacon.getRssi());
            btDevicesHashMap.get(address).setPower(beacon.getPower());
        }

        // update view
        adapter.notifyDataSetChanged();
    }
}
