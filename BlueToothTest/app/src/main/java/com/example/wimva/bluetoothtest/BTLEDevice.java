package com.example.wimva.bluetoothtest;

import android.bluetooth.BluetoothDevice;


class BTLEDevice {
    // bluetooth device
    private BluetoothDevice bluetoothDevice;
    // signal strength
    private int rssi;

    public BTLEDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    public int getRSSI() {
        return rssi;
    }
}
