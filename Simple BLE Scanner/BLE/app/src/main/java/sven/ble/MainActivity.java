package sven.ble;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Callback describing what should happen when the LeScanner gets a scan result
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {
            addDevice(result.getDevice(), result.getRssi());
        }
    };

    // -- Widgets --
    private ListView devicesListView;
    private Button startScanButton;
    private Button stopScanButton;
    private ProgressBar progressBar;

    // -- Data structure --
    // Adapter to show BLEDevices in listview
    private DevicesArrayAdapter arrayAdapter;
    // Array to hold BLEDevices (datasource for DevicesArrayAdapter)
    private ArrayList<BLEDevice> deviceArrayList;
    // Hashmap to find existing BLEDevices quickly
    private HashMap<String, BLEDevice> deviceHashMap;

    // -- Bluetooth --
    private final static int REQUEST_ENABLE_BT = 1;
    private boolean scanning = false;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for BLE support, destroy activity if BLE is not supported
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Prompt for location permissions if not yet granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w("BleActivity", "Location access not granted!");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 42);
        }

        // Get the device bluetooth adapter
        bluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            requestEnableBluetooth();
        } else {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        }


        // Widgets init
        startScanButton = (Button) findViewById(R.id.startScanButton);
        startScanButton.setOnClickListener(this);
        stopScanButton = (Button) findViewById(R.id.stopScanButton);
        stopScanButton.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        deviceHashMap = new HashMap<>();
        deviceArrayList = new ArrayList<>();

        arrayAdapter = new DevicesArrayAdapter(this, R.layout.listview_item, deviceArrayList);
        devicesListView = (ListView) findViewById(R.id.devicesListView);
        devicesListView.setAdapter(arrayAdapter);
    }

    // ---- METHODS ----
    // OnClickListener method
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // "start scanning"-button pressed
            case R.id.startScanButton:
                // If Bluetooth not enabled
                if (!bluetoothAdapter.isEnabled()) {
                    requestEnableBluetooth();
                } else {
                    startScanning();
                }
                break;

            // "stop scanning"-button pressed
            case R.id.stopScanButton:
                stopScanning();
                break;
        }
    }

    // Method to start BLE scan
    private void startScanning() {
        // Clear ListView
        deviceArrayList.clear();
        deviceHashMap.clear();
        arrayAdapter.notifyDataSetChanged();

        // Start scanning
        scanning = true;
        bluetoothLeScanner.startScan(scanSettings, scanCallback);

        // Show spinning progress bar
        progressBar.setVisibility(View.VISIBLE);
    }

    // Method to stop BLE scan
    private void stopScanning() {
        // Stop scanning
        scanning = false;
        bluetoothLeScanner.stopScan(scanCallback);

        // Hide spinning progress bar
        progressBar.setVisibility(View.INVISIBLE);
    }

    // Method to add a BLEDevice to the ListView
    public void addDevice(BluetoothDevice device, int rssi) {
        String address = device.getAddress();
        // if hashmap doesn't contain device, add it
        // else update the signal strength
        if (!deviceHashMap.containsKey(address)) {
            BLEDevice bleDevice = new BLEDevice(device, rssi);

            deviceHashMap.put(address, bleDevice);
            deviceArrayList.add(bleDevice);
        } else {
            deviceHashMap.get(address).setRssi(rssi);
        }

        // update view
        arrayAdapter.notifyDataSetChanged();
    }

    // Method to request Bluetooth to be enabled (via standard Intent)
    private void requestEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    // This method checks if bluetooth was actually enabled and gets the LeScanner so it can be used
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, getIntent());
        if (resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        }
    }

    // Lifecycle method
    @Override
    protected void onPause() {
        super.onPause();

        // Stop scanning when activity closes to prevent battery drain
        if(scanning) stopScanning();
    }
}