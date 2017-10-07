package com.example.wimva.bluetoothtest.Models;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;


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

    private Date lastFoundAt;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    @SuppressWarnings("unused")
    private Beacon() {
        lastFoundAt = Calendar.getInstance().getTime();
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public static Beacon createBeaconFromScanResult(@NonNull final ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord == null) {
            return null;
        }
        byte[] bytesScanRecord = result.getScanRecord().getBytes();

        int startByte;
        boolean isBeacon = false;
        // some strange algorithm
        for (startByte = 2; startByte <= 5; startByte++) {
            if (((int) bytesScanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                    ((int) bytesScanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                isBeacon = true;
                break;
            }
        }

        if (isBeacon){
            Beacon scannedBeacon = new Beacon();
            scannedBeacon.bluetoothDevice = result.getDevice();
            scannedBeacon.rssi = result.getRssi();
            scannedBeacon.power = bytesScanRecord[startByte + 24];

            return scannedBeacon;
        }

        return null;
    }

    public void resetLastFoundAt() {
        lastFoundAt = Calendar.getInstance().getTime();
    }

    /* ------------------------- GETTERS ------------------------- */

    public int getRssi() {
        return rssi;
    }

    public int getPower() {
        return power;
    }

    /**
     * getRelativeRssi is a method to calculate the relative rssi.
     * It uses an algorithm to calculate that with the rssi and the
     * transmission power.
     */
    public double getRelativeRssi() {
        if (rssi == 0) {
            return -1;
        }

        double ratio = rssi * 1.0 / power;
        if (ratio < 1) {
            return Math.pow(ratio, 10);
        }

        return 0.89976 * Math.pow(ratio, 7.7095) + 0.111;
    }

    public String getAddress() {
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getAddress();
    }

    public Date getLastFoundAt() {
        return lastFoundAt;
    }

    /* ------------------------- SETTERS ------------------------- */

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
