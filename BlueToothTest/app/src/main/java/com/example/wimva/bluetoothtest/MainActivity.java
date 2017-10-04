package com.example.wimva.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;

    // hash map to store all found bt devices and be able to search quickly trough them
    private HashMap<String, BTLEDevice> btDevicesHashMap;

    // list of all devices to show in view
    private ArrayList<BTLEDevice> btDevicesArrayList;
    // adapter to show list of bt devices in view
    private ListAdapterBTLEDevices adapter;

    private Button btnScan;

    // instance to detect when the bluetooth state changes (on/off)
    private BroadcastReceiverBTState btStateUpdateReceiver;
    // instance to scan for btle devices
    private BTLEScanner btleScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();
        }

        // create instances of fields
        btDevicesHashMap = new HashMap<>();

        btDevicesArrayList = new ArrayList<>();
        adapter = new ListAdapterBTLEDevices(this, R.layout.btle_device_list_item, btDevicesArrayList);

        btnScan = (Button) findViewById(R.id.btn_scan);

        btStateUpdateReceiver = new BroadcastReceiverBTState(getApplicationContext());

        btleScanner = new BTLEScanner(this, 7500, -75);

        // list-view to show all device in
        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        // add list-view to view
        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);

        // create listener for the scan-button
        findViewById(R.id.btn_scan).setOnClickListener(this);
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

    /**
     * Called when the scan button is clicked.
     *
     * @param v The view that was clicked
     */
    @Override
    public void onClick(View v) {
        // if the button is clicked => start of stop scanning
        if (v.getId() == R.id.btn_scan) {
            if (!btleScanner.isScanning()) {
                startScan();
            } else {
                stopScan();
            }
        }
    }

    /**
     * Adds a device to the ArrayList and Hashmap that the ListAdapter is keeping track of.
     *
     * @param device the BluetoothDevice to be added
     * @param rssi   the rssi of the BluetoothDevice
     */
    public void addDevice(BluetoothDevice device, int rssi) {
        String address = device.getAddress();

        // if hashmap doesn't contain device, add it
        // else update the signal strength
        if (!btDevicesHashMap.containsKey(address)) {
            BTLEDevice btleDevice = new BTLEDevice(device);
            btleDevice.setRSSI(rssi);

            btDevicesHashMap.put(address, btleDevice);
            btDevicesArrayList.add(btleDevice);
        } else {
            btDevicesHashMap.get(address).setRSSI(rssi);
        }

        // update view
        adapter.notifyDataSetChanged();
    }

    /**
     * Clears the ArrayList and Hashmap the ListAdapter is keeping track of.
     * Starts BTLEScanner.
     * Changes the scan button text.
     */
    public void startScan() {
        // set button text
        btnScan.setText("Scanning...");

        // clear device list
        btDevicesArrayList.clear();
        btDevicesHashMap.clear();

        // update view
        adapter.notifyDataSetChanged();

        // start the scanner
        btleScanner.start();
    }

    /**
     * Stops BTLEScanner
     * Changes the scan button text.
     */
    public void stopScan() {
        btnScan.setText("Scan Again");
        btleScanner.stop();
    }
}
