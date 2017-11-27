package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.ValueChangedArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 27/11/2017.
 */

public class StateChangedArgs<T> extends ValueChangedArgs<T> {
    private IStateManagerKey key;

    StateChangedArgs(T oldValue, T newValue, IStateManagerKey key) {
        super(oldValue, newValue);
        this.key = key;
    }

    public IStateManagerKey getKey() {
        return key;
    }
}
