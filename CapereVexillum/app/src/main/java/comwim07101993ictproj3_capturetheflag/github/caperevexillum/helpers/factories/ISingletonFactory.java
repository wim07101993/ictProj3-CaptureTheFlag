package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by wimva on 25/11/2017.
 */

public interface ISingletonFactory<T> {
    T get(Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException;
    T get();

    T createNew(Object... args) throws IllegalAccessException, InstantiationException, InvocationTargetException;
}
