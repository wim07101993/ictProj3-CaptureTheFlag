package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.StateManager;

import java.util.List;

/**
 * Created by wimva on 20/10/2017.
 */

public interface OnStateChangedListener {
    public void StateChanged(List<StateManagerKey> changedKeys);
}
