package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.beaconScanner;

import android.os.AsyncTask;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.MockBeacon;

/**
 * Created by wimva on 11/11/2017.
 */

public class MockBeaconScanner extends ABeaconScanner {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private Scanner scanner = new Scanner();

    private static IBeacon[] beacons = new IBeacon[]{
            new MockBeacon(-50, 50, "mockBeacon1"),
            new MockBeacon(-50, 50, "mockBeacon2"),
            new MockBeacon(-50, 50, "mockBeacon3"),
            new MockBeacon(-50, 50, "mockBeacon4"),
            new MockBeacon(-50, 50, "mockBeacon5"),
    };


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
        scanner.execute();
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


    /* ------------------------------------------------------------------ */
    /* ------------------------- NESTED CLASSES ------------------------- */
    /* ------------------------------------------------------------------ */

    private class Scanner extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int i = 0;
            while (isScanning()) {
                try {
                    this.wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i >= beacons.length) {
                    i = 0;
                }

                final IBeacon beacon = beacons[i];
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyListeners(beacon);
                    }
                });
            }

            return null;
        }
    }
}
