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

    private Date lastFoundAt;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    @SuppressWarnings("unused")
    Beacon() {
        lastFoundAt = Calendar.getInstance().getTime();
    }

    public Beacon(ScanResult scanResult) {
        if (!isBeacon(scanResult)) {
            throw new IllegalArgumentException("Scan result is not a beacon");
        }

        bluetoothDevice = scanResult.getDevice();
        rssi = scanResult.getRssi();

        lastFoundAt = Calendar.getInstance().getTime();
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public static boolean isBeacon(@NonNull final ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord == null) {
            return false;
        }
        byte[] bytesScanRecord = result.getScanRecord().getBytes();

        // some strange algorithm
        for (int startByte = 2; startByte <= 5; startByte++) {
            if (((int)bytesScanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                    ((int)bytesScanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                return true;
            }
        }

        return false;
    }

    public void resetLastFoundAt() {
        lastFoundAt = Calendar.getInstance().getTime();
    }

    /* ------------------------- GETTERS ------------------------- */

    public int getRssi() {
        return rssi;
    }

    public String getAddress() {
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getAddress();
    }

    public String getName() {
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getName();
    }

    public Date getLastFoundAt() {
        return lastFoundAt;
    }

    /* ------------------------- SETTERS ------------------------- */

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
