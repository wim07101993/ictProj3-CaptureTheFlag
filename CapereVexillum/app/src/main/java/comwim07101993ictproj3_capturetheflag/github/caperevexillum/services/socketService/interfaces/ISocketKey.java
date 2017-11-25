package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces;

/**
 * Created by wimva on 25/11/2017.
 */

public interface ISocketKey {
    String getStringIdentifier();
    EMode getMode();
    Class getValueClass();

    enum EMode {
        ON, EMIT
    }
}
