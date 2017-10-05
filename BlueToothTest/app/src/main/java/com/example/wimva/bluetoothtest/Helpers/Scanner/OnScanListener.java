package com.example.wimva.bluetoothtest.Helpers.Scanner;

import com.example.wimva.bluetoothtest.Models.Beacon;

public interface OnScanListener {
    void onScanStopped();
    void onScanStarted();

    void onBeaconFound(Beacon beacon);
}
