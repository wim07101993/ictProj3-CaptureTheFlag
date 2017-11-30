package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories.SingletonFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketFactory;

/**
 * Created by wimva on 30/11/2017.
 */

public class MockSocketFactory
        extends SingletonFactory<MockSocketService>
        implements ISocketFactory<MockSocketService> {

    private static MockSocketService socketService;

    public MockSocketFactory() {
        super(MockSocketService.class);
    }

    @Override
    protected MockSocketService getStaticProduct() {
        return socketService;
    }

    @Override
    protected void setStaticProduct(MockSocketService value) {
        socketService = value;
    }

}
