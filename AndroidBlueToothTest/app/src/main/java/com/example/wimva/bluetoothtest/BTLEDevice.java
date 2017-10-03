package com.example.wimva.bluetoothtest;

import android.bluetooth.BluetoothDevice;

class BTLEDevice {

    /*----------------------------------------------*/
    /*------------------- FIELDS -------------------*/
    /*----------------------------------------------*/

    private BluetoothDevice bluetoothDevice;
    private int rssi;

    /*---------------------------------------------------*/
    /*------------------- CONSTRUCTOR -------------------*/
    /*---------------------------------------------------*/

    public BTLEDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    /*-----------------------------------------------*/
    /*------------------- METHODS -------------------*/
    /*-----------------------------------------------*/

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
