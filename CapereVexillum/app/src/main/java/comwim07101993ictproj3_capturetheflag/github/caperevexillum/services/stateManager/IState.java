package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.lang.reflect.Type;


public interface IState<TKey> {
    TKey getKey();

    Object getValue();

    void setValue(Object value);

    Type getType();
}
