package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.OnStateChangedListener;

/**
 * Created by wimva on 11/11/2017.
 */

public interface IBeaconScanner {

    boolean start();
    void stop();

    void addOnScanListener(OnScanListener l);
    void removeOnScanListener(OnScanListener l);

    boolean isScanning();
}
