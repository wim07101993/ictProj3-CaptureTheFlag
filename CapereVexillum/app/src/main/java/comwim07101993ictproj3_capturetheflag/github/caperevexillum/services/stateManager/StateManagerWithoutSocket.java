package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.Key;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.MapHelpers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.PrimitiveDefaults;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.AObservable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.AObserver;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.IObservable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.ObservableListArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 25/11/2017.
 */

public class StateManagerWithoutSocket
        extends AObservable
        implements IStateManager<IStateManagerKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = StateManager.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    protected Map<IStateManagerKey, ISerializable> objects;
    protected Map<IStateManagerKey, Integer> ints;
    protected Map<IStateManagerKey, Long> longs;
    protected Map<IStateManagerKey, Float> floats;
    protected Map<IStateManagerKey, String> strings;
    protected Map<IStateManagerKey, Boolean> booleans;

    private List<IStateManagerKey> registeredKeys;

    private Map<Key, AObserver> observers;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    StateManagerWithoutSocket() {
        registeredKeys = new Vector<>();
    }

    StateManagerWithoutSocket(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        registeredKeys = new Vector<>();
    }



    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    protected void checkIfTypesMatch(IStateManagerKey key, Object value) {
        if (!value.getClass().isAssignableFrom(key.getValueClass())) {
            String error = "Value " + value + " not of right type for key " + key + ".";
            throw new IllegalArgumentException(error);
        }
    }

    protected Map getMapForClass(Class c) {
        if (ISerializable.class.isAssignableFrom(c)) {
            return objects;
        } else if (int.class.isAssignableFrom(c)) {
            return ints;
        } else if (long.class.isAssignableFrom(c)) {
            return longs;
        } else if (float.class.isAssignableFrom(c)) {
            return floats;
        } else if (String.class.isAssignableFrom(c)) {
            return strings;
        } else if (boolean.class.isAssignableFrom(c)) {
            return booleans;
        }
        return null;
    }


    /* ------------------------- LOAD ------------------------- */

    @Override
    public boolean load() {
        if (sharedPreferences == null) {
            return false;
        }

        loadRegisteredKeys();

        for (IStateManagerKey registeredKey : registeredKeys) {
            load(registeredKey);
        }

        return true;
    }

    protected void loadRegisteredKeys() {
        Set<String> stringKeys = new HashSet<>();
        stringKeys = sharedPreferences.getStringSet(EStateManagerKey.REGISTERED_KEYS.toString(), stringKeys);

        for (String stringKey : stringKeys) {
            registeredKeys.add(EStateManagerKey.convertFromString(stringKey));
        }
    }

    protected void load(IStateManagerKey key) {
        Class c = key.getValueClass();

        if (ISerializable.class.isAssignableFrom(c)) {
            loadObject(key, c);
        } else if (Integer.class.isAssignableFrom(c)) {
            loadInt(key);
        } else if (Long.class.isAssignableFrom(c)) {
            loadLong(key);
        } else if (Float.class.isAssignableFrom(c)) {
            loadFloat(key);
        } else if (String.class.isAssignableFrom(c)) {
            loadString(key);
        } else if (Boolean.class.isAssignableFrom(c)) {
            loadBoolean(key);
        }
    }

    protected void loadObject(IStateManagerKey key, Class c) throws ClassCastException {
        if (MapHelpers.IsNullOrEmpty(objects)) {
            objects = new HashMap<>();
        }

        try {
            ISerializable serializable = (ISerializable) c.newInstance();
            serializable.Deserialize(
                    sharedPreferences.getString(key.toString(), serializable.Serialize()));

            setSerializable(key, serializable);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void loadInt(IStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }

        setInt(key, sharedPreferences.getInt(key.toString(), 0));
    }

    protected void loadLong(IStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }

        setLong(key, sharedPreferences.getLong(key.toString(), 0));
    }

    protected void loadFloat(IStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }

        setFloat(key, sharedPreferences.getFloat(key.toString(), 0));
    }

    protected void loadString(IStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }

        setString(key, sharedPreferences.getString(key.toString(), null));
    }

    protected void loadBoolean(IStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            booleans = new HashMap<>();
        }

        setBoolean(key, sharedPreferences.getBoolean(key.toString(), false));
    }


    /* ------------------------- SAVE ------------------------- */

    @Override
    public boolean save() {
        if (sharedPreferences == null) {
            return false;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        saveRegisteredKeys(editor);

        saveObjects(editor);
        saveInts(editor);
        saveLongs(editor);
        saveFloats(editor);
        saveStrings(editor);
        saveBooleans(editor);

        editor.apply();

        return true;
    }

    protected void saveRegisteredKeys(@NonNull SharedPreferences.Editor editor) {
        Set<String> stringKeys = new HashSet<>();

        for (IStateManagerKey key : registeredKeys) {
            stringKeys.add(key.toString());
        }

        editor.putStringSet(EStateManagerKey.REGISTERED_KEYS.toString(), stringKeys);
    }

    protected void saveObjects(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(objects)) {
            return;
        }

        for (IStateManagerKey key : objects.keySet()) {
            editor.putString(key.toString(), getSerializable(key).Serialize());
        }
    }

    protected void saveInts(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(ints)) {
            return;
        }

        for (IStateManagerKey key : ints.keySet()) {
            editor.putInt(key.toString(), getInt(key));
        }
    }

    protected void saveLongs(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(longs)) {
            return;
        }

        for (IStateManagerKey key : longs.keySet()) {
            editor.putLong(key.toString(), getLong(key));
        }
    }

    protected void saveFloats(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(floats)) {
            return;
        }

        for (IStateManagerKey key : floats.keySet()) {
            editor.putFloat(key.toString(), getFloat(key));
        }
    }

    protected void saveStrings(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(strings)) {
            return;
        }

        for (IStateManagerKey key : strings.keySet()) {
            editor.putString(key.toString(), getString(key));
        }
    }

    protected void saveBooleans(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            return;
        }

        for (IStateManagerKey key : booleans.keySet()) {
            editor.putBoolean(key.toString(), getBoolean(key));
        }
    }


    /* ------------------------- GETTERS ------------------------- */

    @Override
    public ISerializable getSerializable(IStateManagerKey key) {
        return getState(key, objects);
    }

    @Override
    public Integer getInt(IStateManagerKey key) {
        return getState(key, ints);
    }

    @Override
    public Long getLong(IStateManagerKey key) {
        return getState(key, longs);
    }

    @Override
    public Float getFloat(IStateManagerKey key) {
        return getState(key, floats);
    }

    @Override
    public String getString(IStateManagerKey key) {
        return getState(key, strings);
    }

    @Override
    public Boolean getBoolean(IStateManagerKey key) {
        return getState(key, booleans);
    }

    @Nullable
    protected <T> T getState(IStateManagerKey key, Map<IStateManagerKey, T> map) {
        if (MapHelpers.IsNullOrEmpty(map)) {
            return (T)key.getDefaultValue();
        }

        if (!map.containsKey(key)){
            return (T)key.getDefaultValue();
        }
        return map.get(key);
    }

    @NonNull
    @Override
    public String getTAG() {
        return TAG;
    }


    /* ------------------------- SETTERS ------------------------- */

    @Override
    public void SetSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public <T extends ISerializable> T setSerializable(final IStateManagerKey key, T value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(objects)) {
            objects = new HashMap<>();
        }

        if (!registeredKeys.contains(key)) {
            registeredKeys.add(key);
        }

        final T oldValue = (T) objects.get(key);
        final T newValue = (T) objects.put(key, value);


        if (newValue instanceof Observable || newValue instanceof IObservable || newValue instanceof ObservableList) {
            AObserver observer = observers.get(key);
            if (observer == null) {
                observer = new StateObserver(key);
            }

            if (newValue instanceof Observable) {
                ((Observable) newValue).addObserver(observer);
            } else if (newValue instanceof IObservable) {
                ((IObservable) newValue).addObserver(observer);
            } else if (newValue instanceof ObservableList) {
                ((ObservableList) newValue).addOnListChangedCallback(observer);
            }
        }

        StateChangedArgs<T> args = new StateChangedArgs<>(oldValue, newValue, key);
        notifyObservers(args);
        return newValue;
    }

    @Override
    public Integer setInt(IStateManagerKey key, Integer value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }
        return updateState(key, ints, value);
    }

    @Override
    public Long setLong(IStateManagerKey key, Long value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }
        return updateState(key, longs, value);
    }

    @Override
    public Float setFloat(IStateManagerKey key, Float value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }
        return updateState(key, floats, value);
    }

    @Override
    public String setString(IStateManagerKey key, String value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }
        return updateState(key, strings, value);
    }

    @Override
    public Boolean setBoolean(IStateManagerKey key, Boolean value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            booleans = new HashMap<>();
        }
        return updateState(key, booleans, value);
    }

    protected <T> T updateState(IStateManagerKey key, Map<IStateManagerKey, T> map, T value) {
        if (!registeredKeys.contains(key)) {
            registeredKeys.add(key);
        }

        T oldValue = map.put(key, value);
        StateChangedArgs<T> args = new StateChangedArgs<>(oldValue, value, key);
        notifyObservers(args);
        return oldValue;
    }


    /* -------------------------------------------------------------- */
    /* ------------------------- SUBCLASSES ------------------------- */
    /* -------------------------------------------------------------- */

    class StateObserver extends AObserver {

        private final IStateManagerKey key;


        StateObserver(IStateManagerKey key) {
            this.key = key;
        }

        @Override
        public void onChanged(ObservableList observableList) {
            notifyObservers(new StateChangedArgs(null, observableList, key,
                    new ObservableListArgs(observableList)));
        }

        @Override
        public void onItemRangeChanged(ObservableList observableList, int start, int count) {
            notifyObservers(
                    new StateChangedArgs(null, observableList, key,
                            new ObservableListArgs(observableList, ObservableListArgs.Mode.RANGE_CHANGED, start, count)));
        }

        @Override
        public void onItemRangeInserted(ObservableList observableList, int start, int count) {
            notifyObservers(
                    new StateChangedArgs(null, observableList, key,
                            new ObservableListArgs(observableList, ObservableListArgs.Mode.RANGE_CHANGED, start, count)));
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int from, int to, int count) {
            notifyObservers(
                    new StateChangedArgs(null, observableList, key,
                            new ObservableListArgs(observableList, from, to, count)));
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int start, int count) {
            notifyObservers(
                    new StateChangedArgs(null, observableList, key,
                            new ObservableListArgs(observableList, ObservableListArgs.Mode.RANGE_REMOVED, start, count)));
        }

        @Override
        public void update(Observable observable, Object args) {
            notifyObservers(new StateChangedArgs<>(null, observable, key, args));
        }
    }
}
