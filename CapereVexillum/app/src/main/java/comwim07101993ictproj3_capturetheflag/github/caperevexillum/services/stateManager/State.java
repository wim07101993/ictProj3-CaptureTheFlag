package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.lang.reflect.Type;
import java.security.InvalidParameterException;

class State<TKey> implements IState<TKey> {
    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private TKey key;
    private Object value;
    private Type type;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public <T> State(TKey key, Type type, T value) {
        this.key = key;
        this.type = value.getClass();
        this.value = value;
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /* ------------------------- GETTERS ------------------------- */

    @Override
    public TKey getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return type;
    }

    /* ------------------------- SETTERS ------------------------- */

    @Override
    public void setValue(Object value) {
        if (value.getClass() == type)
            this.value = value;
        else throw new InvalidParameterException("Value is of wrong type");
    }
}
