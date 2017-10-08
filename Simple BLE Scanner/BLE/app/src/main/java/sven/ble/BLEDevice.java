package sven.ble;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Sven on 05/10/2017.
 */

public class BLEDevice {
    private BluetoothDevice bluetoothDevice;
    private int rssi;

    public BLEDevice(BluetoothDevice bluetoothDevice, int rssi){
        this.bluetoothDevice = bluetoothDevice;
        this.rssi = rssi;
    }

    public String getAddress(){
        return bluetoothDevice.getAddress();
    }

    public String getName(){
        return bluetoothDevice.getName();
    }

    public int getRssi(){
        return this.rssi;
    }

    public void setRssi(int rssi){
        this.rssi = rssi;
    }
}
