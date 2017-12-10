package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer;

/**
 * Created by wimva on 27/11/2017.
 */

public class ValueChangedArgs<T> implements IValueChangedArgs<T> {

    private T oldValue;
    private T newValue;

    public ValueChangedArgs(T oldValue, T newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public T getOldValue() {
        return oldValue;
    }

    @Override
    public T getNewValue() {

        return newValue;
    }
}
