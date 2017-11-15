package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;

/**
 * Created by wimva on 11/11/2017.
 */

public class MockBeacon extends ABeacon {

    private String address;

    public MockBeacon(int rssi, int power, String address) {
        setRssi(rssi);
        setPower(power);
        this.address = address;
    }

    @Override
    public String getAddress() {
        return address;
    }

}
