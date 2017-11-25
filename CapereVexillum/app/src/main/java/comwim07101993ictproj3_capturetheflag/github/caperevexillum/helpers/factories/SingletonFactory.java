package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by wimva on 25/11/2017.
 */

public abstract class SingletonFactory<T> implements ISingletonFactory<T> {

    private T product;
    private Class<T> productType;

    protected SingletonFactory(Class<T> productType) {
        this.productType = productType;
    }

    @Override
    public T get(Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (product == null) {
            try {
                Constructor<T> constructor = productType.getConstructor(productType);
                product = constructor.newInstance(args);
            } catch (NoSuchMethodException e) {
                String error = "Cannot get the constructor for type " + productType + ".";
                throw new UnsupportedOperationException(error);
            }
        }

        return product;
    }

    @Override
    public T get() {
        return product;
    }

    @Override
    public T createNew(Object... args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        product = null;
        return get(args);
    }
}
