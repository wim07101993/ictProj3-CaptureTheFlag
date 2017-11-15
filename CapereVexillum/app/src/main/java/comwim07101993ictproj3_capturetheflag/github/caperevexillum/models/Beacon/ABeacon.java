package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;

/**
 * Created by wimva on 11/11/2017.
 */

public abstract class ABeacon implements IBeacon {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    // signal strength
    private int rssi;

    // transmission power of the beacon
    private int power;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

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
        // (for QuizFragment ask Steffen Vandegaer)
        double ratio = rssi * 1.0 / power;
        if (ratio < 1) {
            return Math.pow(ratio, 10);
        }

        return 0.89976 * Math.pow(ratio, 7.7095) + 0.111;
    }

    /**
     * @return the MAC-address of the Beacon
     */
    public abstract String getAddress();

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
     *
     * @param power
     */
    public void setPower(int power) {
        this.power = power;
    }
}
