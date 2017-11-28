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
    public T get(Object... args) throws Exception {
        if (getStaticProduct() == null) {
            createNew(args);
        }
        return getStaticProduct();
    }

    @Override
    public T get() {
        return getStaticProduct();
    }

    @Override
    public T createNew(Object... args) throws Exception {
        T product = null;

        Constructor<T>[] constructors = (Constructor<T>[]) productType.getConstructors();

        if (ArrayHelpers.IsNullOrEmpty(constructors)) {
            throw new NoSuchMethodException("Cannot get the constructor for type " + productType + ".");
        }

        Exception exception = null;
        for (Constructor<T> constructor : constructors) {
            try {
                product = constructor.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                exception = e;
            }
        }

        if (product == null) {
            if (exception != null) {
                throw exception;
            }
            throw new Exception("Something went wrong while creatin an instance of " + productType.getSimpleName());
        }

        setStaticProduct(product);
        return get(args);
    }

    protected abstract T getStaticProduct();

    protected abstract void setStaticProduct(T value);
}
