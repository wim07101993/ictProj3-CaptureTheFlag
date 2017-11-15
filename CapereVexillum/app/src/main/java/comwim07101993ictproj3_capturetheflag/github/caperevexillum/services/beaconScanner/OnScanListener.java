package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;

/**
 * Created by Wim Van Laer on 6/10/2017.
 * <p>
 * Interface to detect scanner changes.
 */
public interface OnScanListener {

    /**
     * Invokes when the scan is stopped.
     */
    void onScanStopped();

    /**
     * Invokes when the scan is stopped.
     */
    void onScanStarted();

    /**
     * Invokes when a beacon is found.
     */
    void onBeaconFound(IBeacon beacon);
}
