package com.example.wimva.bluetoothtest.Models;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;


/**
 * A Beacon represents an iBeacon. It is used by the BeaconScanner.
 *
 * You can only create a beacon from a scanResult with the createBeaconFromScanResult method!
 */
public class Beacon {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    // bluetooth device
    private BluetoothDevice bluetoothDevice;

    // signal strength
    private int rssi;

    // transmission power of the beacon
    private int power;

    /* ----------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR --------------------- */
    /* ----------------------------------------------------------- */

    /**
     * This Constructor is made private because no beacon can be created without a valid scanResult.
     *
     * The reason that we did not make a constructor with a scanResult is that we want to be able to
     * return null.
     */
    private Beacon() {

    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * Creates a Beacon from a BluetoothLeScanner result.
     *
     * @param result the result from a BluetoothLeScanner's scan
     * @return A beacon if the result was a beacon, else null.
     */
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
        // (for questions ask Steffen Vandegaer)
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
        if (isBeacon){
            // create Beacon
            Beacon scannedBeacon = new Beacon();
            // set bluetoothDevice
            scannedBeacon.bluetoothDevice = result.getDevice();
            // set rssi
            scannedBeacon.rssi = result.getRssi();
            // set power (sort of batterypower???)
            scannedBeacon.power = bytesScanRecord[startByte + 24];

            // return created Beacon
            return scannedBeacon;
        }

        // This ain't no beacon!! => return null
        return null;
    }

    /* ------------------------- GETTERS ------------------------- */

    /**
     * @return signal strength of the beacon
     */
    public int getRssi() {
        return rssi;
    }

    /**
     * @return sort of batterypower??? (used to find the relative RSSI)
     */
    public int getPower() {
        return power;
    }

    /**
     * getRelativeRssi is a method to calculate the relative rssi.
     * It uses an algorithm to calculate that with the rssi and the
     * transmission power.
     *
     * @return relative RSSI (ratio between battery power and RSSI).
     */
    public double getRelativeRssi() {
        // check if rssi = 0
        if (rssi == 0) {
            return -1;
        }

        // some strange algorithm part 2
        // (for questions ask Steffen Vandegaer)
        double ratio = rssi * 1.0 / power;
        if (ratio < 1) {
            return Math.pow(ratio, 10);
        }

        return 0.89976 * Math.pow(ratio, 7.7095) + 0.111;
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

    /* ------------------------- SETTERS ------------------------- */

    /**
     * Sets the value of the rssi.
     *
     * @param rssi
     */
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    /**
     * Sets the value of the power (used to find the relative RSSI).
     * @param power
     */
    public void setPower(int power) {
        this.power = power;
    }
}
