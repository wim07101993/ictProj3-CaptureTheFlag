package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories;

/**
 * Created by wimva on 25/11/2017.
 */

public interface ISingletonFactory<T> {
    T get(Object... args) throws Exception;

    T get();

    T createNew(Object... args) throws Exception;
}
