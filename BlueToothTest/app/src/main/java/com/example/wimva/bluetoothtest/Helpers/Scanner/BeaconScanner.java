package com.example.wimva.bluetoothtest.Helpers.Scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;

import com.example.wimva.bluetoothtest.Models.Beacon;

import java.util.ArrayList;
import java.util.List;


public class BeaconScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private List<OnScanListener> eventListeners = new ArrayList<>();

    // scanner to get bluetooth devices
    private BluetoothLeScanner scanner;
    private BluetoothAdapter adapter;

    // boolean to indicate if adapter is scanning
    private boolean scanning;
    // instance to handle threading
    private Handler handler;

    // callback when scanned
    private ScanCallback scanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(final int callbackType, final ScanResult result) {
                    if (!Beacon.isBeacon(result)) {
                        return;
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (OnScanListener l : eventListeners) {
                                l.onBeaconFound(new Beacon(result));
                            }
                        }
                    });
                }
            };

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public BeaconScanner(BluetoothAdapter adapter, int signalStrength) {
        // create instances of fields
        this.adapter = adapter;
        scanner = adapter.getBluetoothLeScanner();
        handler = new Handler();
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public boolean start() {
        if (adapter == null || !adapter.isEnabled()) {
            return false;
        }

        scanning = true;
        scanner.startScan(scanCallback);

        for (OnScanListener l : eventListeners) {
            l.onScanStarted();
        }

        return true;
    }

    public void stop() {
        scanning = false;
        scanner.stopScan(scanCallback);

        for (OnScanListener l : eventListeners) {
            l.onScanStopped();
        }
    }

    public void setScanEventListener(OnScanListener l) {
        eventListeners.add(l);
    }

    public void removeScanEventListener(OnScanListener l) {
        eventListeners.remove(l);
    }

    /* ------------------------- GETTERS ------------------------- */

    public boolean isScanning() {
        return scanning;
    }
}
