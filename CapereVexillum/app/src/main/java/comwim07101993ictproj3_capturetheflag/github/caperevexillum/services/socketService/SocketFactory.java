package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories.SingletonFactory;

/**
 * Created by wimva on 25/11/2017.
 */

public final class SocketFactory extends SingletonFactory<SocketService> {

    private static SocketService socketService;

    public SocketFactory() {
        super(SocketService.class);
    }

    @Override
    protected SocketService getStaticProduct() {
        return socketService;
    }

    @Override
    protected void setStaticProduct(SocketService value) {
        socketService = value;
    }
}