package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;

import java.util.Timer;
import java.util.TimerTask;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.MockBeacon;

/**
 * Created by wimva on 11/11/2017.
 */

public class MockBeaconScanner extends ABeaconScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static IBeacon[] beacons = new IBeacon[]{
            new MockBeacon(-50, 50, "mockBeacon1"),
            new MockBeacon(-50, 50, "mockBeacon2"),
            new MockBeacon(-50, 50, "mockBeacon3"),
            new MockBeacon(-50, 50, "mockBeacon4"),
            new MockBeacon(-50, 50, "mockBeacon5"),
    };

    private static int currentBeaconIndex = 0;

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
    @Override
    public boolean start() {
        setIsScanning(true);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentBeaconIndex >= beacons.length) {
                    currentBeaconIndex = 0;
                }

                notifyListeners(beacons[currentBeaconIndex]);
                currentBeaconIndex++;
            }
        }, 10000, 10000);

        return true;
    }

    /**
     * Stops the scanner.
     * Listeners are notified when the scanner stops.
     */
    @Override
    public void stop() {
        setIsScanning(false);
    }


}
