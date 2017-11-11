package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;

import android.os.Handler;

import java.util.List;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;

/**
 * Created by wimva on 11/11/2017.
 */

public abstract class ABeaconScanner implements IBeaconScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    // List of listeners for changes
    private List<OnScanListener> eventListeners = new Vector<>();

    // boolean to indicate if adapter is isScanning
    private boolean isScanning;
    // instance to handle threading
    protected Handler handler = new Handler();


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
    public abstract boolean start();

    /**
     * Stops the scanner.
     * Listeners are notified when the scanner stops.
     */
    public abstract void stop();

    /**
     * Adds l to the listener list
     *
     * @param l listener to add to the listener list
     */
    public void addOnScanListener(OnScanListener l) {
        // add l to the listeners
        eventListeners.add(l);
    }

    /**
     * Removes l to the listener list
     *
     * @param l listener to remove from the listener list
     */
    public void removeOnScanListener(OnScanListener l) {
        // check if listeners contains l
        if (eventListeners.contains(l)) {
            // remove l from the listeners
            eventListeners.remove(l);
        }
    }

    protected void notifyListeners(IBeacon beacon) {
        if (beacon != null) {
            for (OnScanListener l : eventListeners) {
                l.onBeaconFound(beacon);
            }
            return;
        }

        if (isScanning){
            for (OnScanListener l : eventListeners) {
                l.onScanStarted();
            }
        }else{
            for (OnScanListener l : eventListeners) {
                l.onScanStopped();
            }
        }
    }

    /* ------------------------- GETTERS ------------------------- */

    /**
     * @return the scanner state.
     */
    public boolean isScanning() {
        return isScanning;
    }

    /* ------------------------- GETTERS ------------------------- */

    protected void setIsScanning(boolean value) {
        this.isScanning = value;
        notifyListeners(null);
    }
}
