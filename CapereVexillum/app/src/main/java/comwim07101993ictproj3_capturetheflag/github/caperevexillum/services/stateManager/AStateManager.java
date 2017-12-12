package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 4/12/2017.
 */

public abstract class AStateManager<TKey extends IStateManagerKey>
        extends AObservable
        implements IStateManager<TKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = StateManagerWithSocket.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    protected Map<TKey, ISerializable> objects;
    protected Map<TKey, Integer> ints;
    protected Map<TKey, Long> longs;
    protected Map<TKey, Float> floats;
    protected Map<TKey, String> strings;
    protected Map<TKey, Boolean> booleans;

    private List<TKey> registeredKeys;

    private Map<TKey, AObserver> observers;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    AStateManager() {
        registeredKeys = new Vector<>();
    }

    AStateManager(SharedPreferences sharedPreferences) {
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

    private void checkIfTypesMatch(IStateManagerKey key, Object value) {
        if (value != null && !value.getClass().isAssignableFrom(key.getValueClass())) {
            String error = "Value " + value + " not of right type for key " + key + ". (" +
                    value.getClass().getSimpleName() +" != " + key.getValueClass();
            throw new IllegalArgumentException(error);
        } else if (value == null && PrimitiveDefaults.getDefaultValue(key.getValueClass()) != null) {
            String error = "Type for key " + key + " cant not be null.";
            throw new IllegalArgumentException(error);
        }
    }

    Map getMapForClass(Class c) {
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

        for (TKey registeredKey : registeredKeys) {
            load(registeredKey);
        }

        return true;
    }

    private void loadRegisteredKeys() {
        Set<String> stringKeys = new HashSet<>();
        stringKeys = sharedPreferences.getStringSet(EStateManagerKey.REGISTERED_KEYS.toString(), stringKeys);

        for (String stringKey : stringKeys) {
            registeredKeys.add(convertStringToKey(stringKey));
        }
    }

    protected abstract TKey convertStringToKey(String string);

    private void load(TKey key) {
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
        } else {
            Log.w(TAG, "tried to load state of unknown type: " + key.getValueClass());
        }
    }

    private void loadObject(TKey key, Class c) throws ClassCastException {
        if (MapHelpers.IsNullOrEmpty(objects)) {
            objects = new HashMap<>();
        }

        try {
            ISerializable serializable = (ISerializable) c.newInstance();
            serializable.deserialize(
                    sharedPreferences.getString(key.toString(), serializable.serialize()));

            setSerializable(key, serializable);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void loadInt(TKey key) {
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }

        setInt(key, sharedPreferences.getInt(key.toString(), 0));
    }

    private void loadLong(TKey key) {
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }

        setLong(key, sharedPreferences.getLong(key.toString(), 0));
    }

    private void loadFloat(TKey key) {
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }

        setFloat(key, sharedPreferences.getFloat(key.toString(), 0));
    }

    private void loadString(TKey key) {
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }

        setString(key, sharedPreferences.getString(key.toString(), null));
    }

    private void loadBoolean(TKey key) {
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

    private void saveRegisteredKeys(@NonNull SharedPreferences.Editor editor) {
        Set<String> stringKeys = new HashSet<>();

        for (TKey key : registeredKeys) {
            if (key.needsToBeStored()) {
                stringKeys.add(key.toString());
            }
        }

        editor.putStringSet(EStateManagerKey.REGISTERED_KEYS.toString(), stringKeys);
    }

    private void saveObjects(@NonNull SharedPreferences.Editor editor) {
        putInEditor(editor, objects, new AStateManager.ISharedPreferenceSaver<TKey>() {
            @Override
            public void putFunction(SharedPreferences.Editor editor, TKey key) {
                ISerializable value = baseGetState(key, objects);
                if (value != null) {
                    editor.putString(key.toString(), value.serialize());
                } else {
                    editor.putString(key.toString(), null);
                }
            }
        });
    }

    private void saveInts(@NonNull SharedPreferences.Editor editor) {
        putInEditor(editor, ints, new AStateManager.ISharedPreferenceSaver<TKey>() {
            @Override
            public void putFunction(SharedPreferences.Editor editor, TKey key) {
                editor.putInt(key.toString(), baseGetState(key, ints));
            }
        });
    }

    private void saveLongs(@NonNull SharedPreferences.Editor editor) {
        putInEditor(editor, longs, new AStateManager.ISharedPreferenceSaver<TKey>() {
            @Override
            public void putFunction(SharedPreferences.Editor editor, TKey key) {
                editor.putLong(key.toString(), baseGetState(key, longs));
            }
        });
    }

    private void saveFloats(@NonNull SharedPreferences.Editor editor) {
        putInEditor(editor, floats, new AStateManager.ISharedPreferenceSaver<TKey>() {
            @Override
            public void putFunction(SharedPreferences.Editor editor, TKey key) {
                editor.putFloat(key.toString(), baseGetState(key, floats));
            }
        });
    }

    private void saveStrings(@NonNull SharedPreferences.Editor editor) {
        putInEditor(editor, strings, new AStateManager.ISharedPreferenceSaver<TKey>() {
            @Override
            public void putFunction(SharedPreferences.Editor editor, TKey key) {
                editor.putString(key.toString(), baseGetState(key, strings));
            }
        });
    }

    private void saveBooleans(@NonNull SharedPreferences.Editor editor) {
        putInEditor(editor, booleans, new AStateManager.ISharedPreferenceSaver<TKey>() {
            @Override
            public void putFunction(SharedPreferences.Editor editor, TKey key) {
                editor.putBoolean(key.toString(), baseGetState(key, booleans));
            }
        });
    }

    private <T> void putInEditor(SharedPreferences.Editor editor, Map<TKey, T> map, AStateManager.ISharedPreferenceSaver saver) {
        if (MapHelpers.IsNullOrEmpty(map)) {
            return;
        }

        for (IStateManagerKey key : map.keySet()) {
            if (key.needsToBeStored()) {
                saver.putFunction(editor, key);
            }
        }
    }


    /* ------------------------- GETTERS ------------------------- */

    @Override
    public ISerializable getSerializable(TKey key) {
        try {
            return getState(key, objects);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
        return null;
    }

    @Override
    public Integer getInt(TKey key) {
        try {
            return getState(key, ints);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Integer) PrimitiveDefaults.getDefaultValue(Integer.class);
    }

    @Override
    public Long getLong(TKey key) {
        try {
            return getState(key, longs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Long) PrimitiveDefaults.getDefaultValue(Long.class);
    }

    @Override
    public Float getFloat(TKey key) {
        try {
            return getState(key, floats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Float) PrimitiveDefaults.getDefaultValue(Float.class);
    }

    @Override
    public String getString(TKey key) {
        try {
            return getState(key, strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) PrimitiveDefaults.getDefaultValue(String.class);
    }

    @Override
    public Boolean getBoolean(TKey key) {
        try {
            return getState(key, booleans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Boolean) PrimitiveDefaults.getDefaultValue(Boolean.class);
    }

    @Nullable
    protected <T> T getState(TKey key, Map<TKey, T> map) throws Exception {
        return baseGetState(key, map);
    }

    protected final <T> T baseGetState(TKey key, Map<TKey, T> map) {
        if (MapHelpers.IsNullOrEmpty(map)) {
            return (T) key.getDefaultValue();
        }

        if (!map.containsKey(key)) {
            return (T) key.getDefaultValue();
        }

        return map.get(key);
    }

    @NonNull
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public ISocketService getSocketService() {
        return null;
    }


    /* ------------------------- SETTERS ------------------------- */

    @Override
    public void SetSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public <T extends ISerializable> ISerializable setSerializable(final TKey key, T value) {
        checkIfTypesMatch(key, value);
        if (objects == null) {
            objects = new HashMap<>();
        }

        if (value instanceof Observable || value instanceof IObservable || value instanceof ObservableList) {
            if (observers == null) {
                observers = new HashMap<>();
            }

            AObserver observer = observers.get(key);
            if (observer == null) {
                observer = new AStateManager.StateObserver(key);
            }

            if (value instanceof Observable) {
                ((Observable) value).addObserver(observer);
            } else if (value instanceof IObservable) {
                ((IObservable) value).addObserver(observer);
            } else {
                ((ObservableList) value).addOnListChangedCallback(observer);
            }
        }

        return updateState(key, objects, value);
    }

    @Override
    public Integer setInt(TKey key, Integer value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }
        return updateState(key, ints, value);
    }

    @Override
    public Long setLong(TKey key, Long value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }
        return updateState(key, longs, value);
    }

    @Override
    public Float setFloat(TKey key, Float value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }
        return updateState(key, floats, value);
    }

    @Override
    public String setString(TKey key, String value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }
        return updateState(key, strings, value);
    }

    @Override
    public Boolean setBoolean(TKey key, Boolean value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            booleans = new HashMap<>();
        }
        return updateState(key, booleans, value);
    }

    protected <T> T updateState(TKey key, Map<TKey, T> map, T value) {
        checkIfTypesMatch(key, value);
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

    protected interface ISharedPreferenceSaver<TKey> {
        void putFunction(SharedPreferences.Editor editor, TKey key);
    }
}
