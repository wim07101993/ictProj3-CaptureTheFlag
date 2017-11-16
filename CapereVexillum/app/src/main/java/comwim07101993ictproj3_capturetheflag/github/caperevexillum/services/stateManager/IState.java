package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.lang.reflect.Type;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;


public interface IState {
    Type getType();

    boolean canSet();
    boolean canGet();
}
