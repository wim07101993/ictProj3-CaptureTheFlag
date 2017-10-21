package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;

import java.util.List;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;


/**
 * Created by Wim Van Laer on 6/10/2017.
 * <p>
 * BeaconScanner uses bluetooth to scan for beacons.
 * <p>
 * You can listen for detection, start of scanning
 * and stop of scanning by implementing the OnScanListener and adding it by using the method
 * setScanEventListener.
 * If you stop listening, unsubscribe by using the removeScanEventListener method.
 * <p>
 * You can start scanning for beacons using the Start method and stop scanning with the stop method
 * <p>
 * With the IsScanning method you can ask if the scanner is scanning.
 */
public class BeaconScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    // List of listeners for changes
    private List<OnScanListener> eventListeners = new Vector<>();

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
                    // create beacon from scan result (if result is no beacon, null is returned)
                    final Beacon scannedBeacon = Beacon.createBeaconFromScanResult(result);
                    if (scannedBeacon != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // notify each of the listeners
                                for (OnScanListener l : eventListeners) {
                                    l.onBeaconFound(scannedBeacon);
                                }
                            }
                        });
                    }
                }
            };



    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */


    /**
     * Constructor to construct a BeaconScanner instance.
     *
     * @param adapter BluetoothAdapter to get the scanner from
     */
    public BeaconScanner(BluetoothAdapter adapter) {
        // create instances of fields
        this.adapter = adapter;

        // set scanner and handler
        scanner = adapter.getBluetoothLeScanner();
        handler = new Handler();
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * Starts the scanner.
     * If the adapter is null or not enabled, the scan will not start.
     * Listeners are notified when the scanner starts.
     *
     * @return whether the scan started
     */
    public boolean start() {
        // check if adapter exists and is enabled
        if (adapter == null || !adapter.isEnabled()) {
            return false;
        }

        // start the scan
        scanning = true;
        scanner.startScan(scanCallback);

        // notify listeners
        for (OnScanListener l : eventListeners) {
            l.onScanStarted();
        }

        return true;
    }

    /**
     * Stops the scanner.
     * Listeners are notified when the scanner stops.
     */
    public void stop() {
        // stop scanning
        scanning = false;
        scanner.stopScan(scanCallback);

        // notify listeners
        for (OnScanListener l : eventListeners) {
            l.onScanStopped();
        }
    }

    /**
     * Adds l to the listener list
     *
     * @param l listener to add to the listener list
     */
    public void setScanEventListener(OnScanListener l) {
        // add l to the listeners
        eventListeners.add(l);
    }

    /**
     * Removes l to the listener list
     *
     * @param l listener to remove from the listener list
     */
    public void removeScanEventListener(OnScanListener l) {
        // check if listeners contains l
        if (eventListeners.contains(l)) {
            // remove l from the listeners
            eventListeners.remove(l);
        }
    }

    /* ------------------------- GETTERS ------------------------- */

    /**
     * @return the scanner state.
     */
    public boolean isScanning() {
        // return the scanner state
        return scanning;
    }
}
