package com.example.wimva.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class BTLEScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private MainActivity ma;

    // scanner to get bluetooth devices
    private BluetoothLeScanner scanner;
    private BluetoothAdapter adapter;
    // boolean to indicate if adapter is scanning
    private boolean scanning;
    // instance to handle threading
    private Handler handler;

    // duration of a scan
    private long scanPeriod;
    // signal threshold to find devices
    private int signalStrength;

    // callback when scanned
    private ScanCallback scanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(final int callbackType, final ScanResult result) {
                    final int rssi = result.getRssi();

                    if (rssi > signalStrength) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ma.addDevice(result.getDevice(), rssi);
                            }
                        });
                    }
                }
            };

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    BTLEScanner(MainActivity mainActivity, long scanPeriod, int signalStrength) {
        // create instances of fields
        ma = mainActivity;

        adapter = ((BluetoothManager) ma.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        scanner = adapter.getBluetoothLeScanner();
        handler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    boolean isScanning() {
        return scanning;
    }

    void start() {
        // checks if the bluetooth is turned on and starts the scan
        if (!Utils.checkBluetooth(adapter)) {
            Utils.requestUserBluetooth(ma);
            ma.stopScan();
        } else {
            scanLeDevice(true);
        }
    }

    void stop() {
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

                    scanner.stopScan(scanCallback);

                    ma.stopScan();
                }
            }, scanPeriod);

            scanning = true;
            scanner.startScan(scanCallback);
//            scanner.startLeScan(uuids, leScanCallback);
        } else if (!enable) {
            scanning = false;
            scanner.stopScan(scanCallback);
        }
    }


}
