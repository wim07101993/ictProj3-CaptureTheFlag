package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;

/**
 * Created by wimva on 11/11/2017.
 */

public interface IBeacon {

    /**
     * @return signal strength of the beacon
     */
    int getRssi();

    /**
     * Sets the value of the rssi.
     *
     * @param rssi
     */
    void setRssi(int rssi);

    /**
     * @return sort of batterypower??? (used to find the relative RSSI)
     */
    int getPower();

    /**
     * Sets the value of the power (used to find the relative RSSI).
     *
     * @param power
     */
    void setPower(int power);

    /**
     * getRelativeRssi is a method to calculate the relative rssi.
     * It uses an algorithm to calculate that with the rssi and the
     * transmission power.
     *
     * @return relative RSSI (ratio between battery power and RSSI).
     */
    double getRelativeRssi();

    /**
     * @return the MAC-address of the Beacon
     */
    String getAddress();
}
