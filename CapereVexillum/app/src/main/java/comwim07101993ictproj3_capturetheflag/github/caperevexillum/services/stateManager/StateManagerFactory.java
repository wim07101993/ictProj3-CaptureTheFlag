package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories.SingletonFactory;

/**
 * Created by wimva on 28/11/2017.
 */

public class StateManagerFactory extends SingletonFactory<StateManager> {
    private static StateManager stateManager;

    public StateManagerFactory() {
        super(StateManager.class);
    }

    @Override
    protected StateManager getStaticProduct() {
        return stateManager;
    }

    @Override
    protected void setStaticProduct(StateManager value) {
        stateManager = value;
    }
}
