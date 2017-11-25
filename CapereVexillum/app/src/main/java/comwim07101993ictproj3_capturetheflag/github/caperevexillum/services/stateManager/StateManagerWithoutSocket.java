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

/**
 * Created by wimva on 25/11/2017.
 */

public class StateManagerWithoutSocket
        extends ANotifier<EStateManagerKey>
        implements IStateManager<EStateManagerKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = StateManager.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    private Map<EStateManagerKey, ISerializable> objects;
    private Map<EStateManagerKey, Integer> ints;
    private Map<EStateManagerKey, Long> longs;
    private Map<EStateManagerKey, Float> floats;
    private Map<EStateManagerKey, String> strings;
    private Map<EStateManagerKey, Boolean> booleans;

    private List<EStateManagerKey> registeredKeys;


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


    /* ------------------------- LOAD ------------------------- */

    @Override
    public boolean load() {
        if (sharedPreferences == null) {
            return false;
        }

        loadRegisteredKeys();

        for (EStateManagerKey registeredKey : registeredKeys) {
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

    protected void load(EStateManagerKey key) {
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

    protected void loadObject(EStateManagerKey key, Class c) throws ClassCastException {
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

    protected void loadInt(EStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }

        setInt(key, sharedPreferences.getInt(key.toString(), 0));
    }

    protected void loadLong(EStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }

        setLong(key, sharedPreferences.getLong(key.toString(), 0));
    }

    protected void loadFloat(EStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }

        setFloat(key, sharedPreferences.getFloat(key.toString(), 0));
    }

    protected void loadString(EStateManagerKey key) {
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }

        setString(key, sharedPreferences.getString(key.toString(), null));
    }

    protected void loadBoolean(EStateManagerKey key) {
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

        for (EStateManagerKey key : registeredKeys) {
            stringKeys.add(key.toString());
        }

        editor.putStringSet(EStateManagerKey.REGISTERED_KEYS.toString(), stringKeys);
    }

    protected void saveObjects(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(objects)) {
            return;
        }

        for (EStateManagerKey key : objects.keySet()) {
            editor.putString(key.toString(), getISerializable(key).Serialize());
        }
    }

    protected void saveInts(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(ints)) {
            return;
        }

        for (EStateManagerKey key : ints.keySet()) {
            editor.putInt(key.toString(), getInt(key));
        }
    }

    protected void saveLongs(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(longs)) {
            return;
        }

        for (EStateManagerKey key : longs.keySet()) {
            editor.putLong(key.toString(), getLong(key));
        }
    }

    protected void saveFloats(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(floats)) {
            return;
        }

        for (EStateManagerKey key : floats.keySet()) {
            editor.putFloat(key.toString(), getFloat(key));
        }
    }

    protected void saveStrings(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(strings)) {
            return;
        }

        for (EStateManagerKey key : strings.keySet()) {
            editor.putString(key.toString(), getString(key));
        }
    }

    protected void saveBooleans(@NonNull SharedPreferences.Editor editor) {
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            return;
        }

        for (EStateManagerKey key : booleans.keySet()) {
            editor.putBoolean(key.toString(), getBoolean(key));
        }
    }


    /* ------------------------- GETTERS ------------------------- */

    @Override
    public ISerializable getISerializable(EStateManagerKey key) {
        return getState(key, objects);
    }

    @Override
    public Integer getInt(EStateManagerKey key) {
        return getState(key, ints);
    }

    @Override
    public Long getLong(EStateManagerKey key) {
        return getState(key, longs);
    }

    @Override
    public Float getFloat(EStateManagerKey key) {
        return getState(key, floats);
    }

    @Override
    public String getString(EStateManagerKey key) {
        return getState(key, strings);
    }

    @Override
    public Boolean getBoolean(EStateManagerKey key) {
        return getState(key, booleans);
    }

    @Nullable
    protected <T> T getState(EStateManagerKey key, Map<EStateManagerKey, T> map) {
        if (MapHelpers.IsNullOrEmpty(map)) {
            return null;
        }
        return map.get(key);
    }


    /* ------------------------- SETTERS ------------------------- */

    @Override
    public void setSerializable(EStateManagerKey key, ISerializable value) {
        if (MapHelpers.IsNullOrEmpty(objects)) {
            objects = new HashMap<>();
        }
        updateState(key, objects, value);
    }

    @Override
    public void setInt(EStateManagerKey key, int value) {
        if (MapHelpers.IsNullOrEmpty(ints)) {
            ints = new HashMap<>();
        }
        updateState(key, ints, value);
    }

    @Override
    public void setLong(EStateManagerKey key, long value) {
        if (MapHelpers.IsNullOrEmpty(longs)) {
            longs = new HashMap<>();
        }
        updateState(key, longs, value);
    }

    @Override
    public void setFloat(EStateManagerKey key, float value) {
        if (MapHelpers.IsNullOrEmpty(floats)) {
            floats = new HashMap<>();
        }
        updateState(key, floats, value);
    }

    @Override
    public void setString(EStateManagerKey key, String value) {
        if (MapHelpers.IsNullOrEmpty(strings)) {
            strings = new HashMap<>();
        }
        updateState(key, strings, value);
    }

    @Override
    public void setBoolean(EStateManagerKey key, boolean value) {
        if (MapHelpers.IsNullOrEmpty(booleans)) {
            booleans = new HashMap<>();
        }
        updateState(key, booleans, value);
    }

    protected <T> void updateState(EStateManagerKey key, Map<EStateManagerKey, T> map, T value) {
        if (!registeredKeys.contains(key)) {
            registeredKeys.add(key);
        }

        map.put(key, value);
        notifyListeners(key, value);
    }
}
