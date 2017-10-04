package com.example.wimva.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;


class BTLEScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private MainActivity ma;

    // adapter to get bluetooth devices
    private BluetoothAdapter bluetoothAdapter;
    // boolean to indicate if adapter is scanning
    private boolean scanning;
    // instance to handle threading
    private Handler handler;

    // duration of a scan
    private long scanPeriod;
    // signal threshold to find devices
    private int signalStrength;

    // callback when scanned
    private BluetoothAdapter.LeScanCallback leScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    final int newRSSI = rssi;
                    if (rssi > signalStrength) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ma.addDevice(device, newRSSI);
                            }
                        });
                    }
                }
            };

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public BTLEScanner(MainActivity mainActivity, long scanPeriod, int signalStrength) {
        // create instances of fields
        ma = mainActivity;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) ma.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        handler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public boolean isScanning() {
        return scanning;
    }

    public void start() {
        // checks if the bluetooth is turned on and starts the scan
        if (!Utils.checkBluetooth(bluetoothAdapter)) {
            Utils.requestUserBluetooth(ma);
            ma.stopScan();
        }
        else {
            scanLeDevice(true);
        }
    }

    public void stop() {
        scanLeDevice(false);
    }

    // If you want to scan for only specific types of peripherals,
    // you can instead call startLeScan(UUID[], BluetoothAdapter.LeScanCallback),
    // providing an array of UUID objects that specify the GATT services your app supports.
    private void scanLeDevice(final boolean enable) {
        // start scanning if enable is true and isn't already scanning
        if (enable && !scanning) {
            Utils.toast(ma.getApplicationContext(), "Starting BLE scan...");

            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.toast(ma.getApplicationContext(), "Stopping BLE scan...");

                    scanning = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);

                    ma.stopScan();
                }
            }, scanPeriod);

            scanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
//            bluetoothAdapter.startLeScan(uuids, leScanCallback);
        }
        else if (!enable){
            scanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
    }


}
