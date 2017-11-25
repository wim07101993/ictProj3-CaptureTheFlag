package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories.SingletonFactory;

/**
 * Created by wimva on 25/11/2017.
 */

public final class SocketFactory extends SingletonFactory<SocketService> {

    public SocketFactory() {
        super(SocketService.class);
    }
}
