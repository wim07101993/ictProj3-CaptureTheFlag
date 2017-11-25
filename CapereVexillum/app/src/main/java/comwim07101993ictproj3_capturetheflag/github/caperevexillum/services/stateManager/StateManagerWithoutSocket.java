package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.MapHelpers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.ANotifier;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 25/11/2017.
 */

public class StateManagerWithoutSocket
        extends ANotifier<IStateManagerKey>
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


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public StateManagerWithoutSocket(SharedPreferences sharedPreferences) {
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
            return null;
        }
        return map.get(key);
    }


    /* ------------------------- SETTERS ------------------------- */

    @Override
    public void setSerializable(IStateManagerKey key, ISerializable value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(objects)) {
            objects = new HashMap<>();
        }

        updateState(key, objects, value);
    }

    @Override
    public void setInt(IStateManagerKey key, int value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }
        updateState(key, ints, value);
    }

    @Override
    public void setLong(IStateManagerKey key, long value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }
        updateState(key, longs, value);
    }

    @Override
    public void setFloat(IStateManagerKey key, float value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }
        updateState(key, floats, value);
    }

    @Override
    public void setString(IStateManagerKey key, String value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }
        updateState(key, strings, value);
    }

    @Override
    public void setBoolean(IStateManagerKey key, boolean value) {
        checkIfTypesMatch(key, value);
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            booleans = new HashMap<>();
        }
        updateState(key, booleans, value);
    }

    protected <T> void updateState(IStateManagerKey key, Map<IStateManagerKey, T> map, T value) {
        if (!registeredKeys.contains(key)) {
            registeredKeys.add(key);
        }

        map.put(key, value);
        notifyListeners(key, value);
    }
}
