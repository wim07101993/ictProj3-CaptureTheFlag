package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;


import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.Beacon;


/**
 * Created by Wim Van Laer on 6/10/2017.
 * <p>
 * BeaconScanner uses bluetooth to scan for beacons.
 * <p>
 * You can listen for detection, start of scanning
 * and stop of scanning by implementing the OnScanListener and adding it by using the method
 * addOnScanListener.
 * If you stop listening, unsubscribe by using the removeOnScanListener method.
 * <p>
 * You can start scanning for beacons using the Start method and stop scanning with the stop method
 * <p>
 * With the IsScanning method you can ask if the scanner is scanning.
 */
public class BeaconScanner extends ABeaconScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = BeaconScanner.class.getSimpleName();
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int REQUEST_ENABLE_COURSE_LOCATION = 2;

    // scanner to get bluetooth devices
    private BluetoothLeScanner scanner;

    private BluetoothAdapter adapter;

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
                                notifyListeners(scannedBeacon);
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
        scanner.startScan(scanCallback);
        setIsScanning(true);

        return true;
    }

    /**
     * Stops the scanner.
     * Listeners are notified when the scanner stops.
     */
    public void stop() {
        // stop scanning
        scanner.stopScan(scanCallback);
        setIsScanning(false);
    }

    /**
     * Asks for the needed permissions
     */
    public static void askPermissions(Activity activity) {

        // create BT intent
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // starts the activity depending on the result
        activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        // check for needed permissions and if they are granted, move on
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Location access not granted!");
            // If not granted ask for permission
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ENABLE_COURSE_LOCATION);
        }
    }


    public static boolean isBLESupported(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

}
