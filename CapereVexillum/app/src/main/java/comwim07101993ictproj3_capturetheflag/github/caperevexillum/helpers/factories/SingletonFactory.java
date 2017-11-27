package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ArrayHelpers;

/**
 * Created by wimva on 25/11/2017.
 */

public abstract class SingletonFactory<T> implements ISingletonFactory<T> {

    private Class<T> productType;

    protected SingletonFactory(Class<T> productType) {
        this.productType = productType;
    }

    @Override
    public T get(Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (getStaticProduct() == null) {
            try {
                Constructor<T>[] constructors = (Constructor<T>[]) productType.getConstructors();
                if (ArrayHelpers.IsNullOrEmpty(constructors)) {
                    throw new NoSuchMethodException();
                }
                Constructor<T> constructor = constructors[0];
                setStaticProduct(constructor.newInstance(args));
            } catch (NoSuchMethodException e) {
                String error = "Cannot get the constructor for type " + productType + ".";
                throw new UnsupportedOperationException(error);
            }
        }

        return getStaticProduct();
    }

    @Override
    public T get() {
        return getStaticProduct();
    }

    @Override
    public T createNew(Object... args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        setStaticProduct(null);
        return get(args);
    }

    protected abstract T getStaticProduct();

    protected abstract void setStaticProduct(T value);
}
