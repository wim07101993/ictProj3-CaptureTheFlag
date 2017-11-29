package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketOnKey;

/**
 * Created by wimva on 25/11/2017.
 */

public interface IStateManagerKey {
    Class getValueClass();

    ESocketOnKey getSocketOnKey();
    ESocketEmitKey getSocketEmitAskKey();
    ESocketEmitKey getSocketEmitPutKey();

    Object getDefaultValue();
    boolean needsToBeStored();
}
