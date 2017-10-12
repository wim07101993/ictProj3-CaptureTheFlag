package com.example.wimva.bluetoothtest.Helpers.Scanner;

import com.example.wimva.bluetoothtest.Models.Beacon;

/**
 * Interface to detect scanner changes.
 */
public interface OnScanListener {

    /**
     * Invokes when the scan is stopped.
     */
    void onScanStopped();

    /**
     * Invokes when the scan is stopped.
     */
    void onScanStarted();

    /**
     * Invokes when a beacon is found.
     */
    void onBeaconFound(Beacon beacon);
}
