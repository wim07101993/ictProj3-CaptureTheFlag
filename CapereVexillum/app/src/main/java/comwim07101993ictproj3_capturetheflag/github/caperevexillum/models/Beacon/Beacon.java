package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Created by Wim en Sven on 3/10/2017
 * <p>
 * A Beacon represents an iBeacon. It is used by the BeaconScanner.
 * <p>
 * You can only create a beacon from a scanResult with the createBeaconFromScanResult method!
 */
public class Beacon extends ABeacon {

    private BluetoothDevice bluetoothDevice;

    /**
     * Creates a Beacon from a BluetoothLeScanner result.
     *
     * @param result the result from a BluetoothLeScanner's scan
     * @return A beacon if the result was a beacon, else null.
     */
    @Nullable
    public static Beacon createBeaconFromScanResult(@NonNull final ScanResult result) {
        // get the scan record
        ScanRecord scanRecord = result.getScanRecord();
        // if scan record doesn't exist
        // => return null
        if (scanRecord == null) {
            return null;
        }

        // get data from scan record
        byte[] bytesScanRecord = result.getScanRecord().getBytes();

        // byte at which the beacon data starts
        int startByte;
        // bool to indicate whether result is a beacon
        boolean isBeacon = false;

        // some strange algorithm to determine whether the result is a beacon
        // (for QuizFragment ask Steffen Vandegaer)
        for (startByte = 2; startByte <= 5; startByte++) {
            if (((int) bytesScanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                    ((int) bytesScanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                // It's a beacon!!
                isBeacon = true;
                break;
            }
        }

        // if result is a Beacon
        // => create one from the data.
        if (isBeacon) {
            // create Beacon
            Beacon scannedBeacon = new Beacon();
            // set bluetoothDevice
            scannedBeacon.bluetoothDevice = result.getDevice();
            // set rssi
            scannedBeacon.setRssi(result.getRssi());
            // set power (sort of batterypower???)
            scannedBeacon.setPower(bytesScanRecord[startByte + 24]);

            // return created Beacon
            return scannedBeacon;
        }

        // This ain't no beacon!! => return null
        return null;
    }

    /**
     * @return the MAC-address of the Beacon
     */
    public String getAddress() {
        // if bluetoothDevice doesn't exist (Beacon is not properly created)
        // => return null
        if (bluetoothDevice == null) {
            return null;
        }

        // return the address
        return bluetoothDevice.getAddress();
    }
}
