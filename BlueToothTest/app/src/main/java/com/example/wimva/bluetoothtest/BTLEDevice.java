package com.example.wimva.bluetoothtest;

import android.bluetooth.BluetoothDevice;


class BTLEDevice {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    // bluetooth device
    private BluetoothDevice bluetoothDevice;

    // signal strength
    private int rssi;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    BTLEDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /* ------------------------- GETTERS ------------------------- */

    String getAddress() {
        return bluetoothDevice.getAddress();
    }

    String getName() {
        return bluetoothDevice.getName();
    }

    /* ------------------------- SETTERS ------------------------- */

    void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    int getRSSI() {
        return rssi;
    }
}
