package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.StateManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static android.content.SharedPreferences.Editor;

/**
 * Created by wimva on 20/10/2017.
 *
 *
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractStateManager<TKey> implements IStateManager<TKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    protected Context context;
    protected SharedPreferences savedValues;
    protected String sharedPreferencesName;

    protected Map<TKey, Object> currentState = new HashMap<TKey, Object>() {
    };

    protected List<OnStateChangedListener> stateChangedListeners = new Vector<>();

    /* ----------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* ----------------------------------------------------------- */

    protected AbstractStateManager(Context context, String sharedPreferencesName) {
        this.context = context;
        this.sharedPreferencesName = sharedPreferencesName;

        savedValues = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);

        load();
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public void addStateChangedListener(OnStateChangedListener listener) {
        stateChangedListeners.add(listener);
    }

    public void removeStateChangedListener(OnStateChangedListener listener) {
        stateChangedListeners.remove(listener);
    }

    private void notifyListeners(List<TKey> keys) {
        for (OnStateChangedListener l : stateChangedListeners) {
            l.StateChanged(keys, this);
        }
    }

    public synchronized void save() {
        Gson gson = new Gson();
        Editor editor = savedValues.edit();
        editor.putString(sharedPreferencesName, gson.toJson(currentState));
        editor.apply();
    }

    public synchronized void load() {
        // TODO Wim: does toJson(null) throw error?
        String json = savedValues.getString(sharedPreferencesName, null);

        if (json == null) {
            currentState = new HashMap<>();
        } else {
            Gson gson = new Gson();
            Type t = new TypeToken<Map<TKey, Object>>() {
            }.getType();
            currentState = gson.fromJson(json, t);
        }
    }

    /* ------------------------- GETTERS ------------------------- */

    public synchronized Object get(TKey key)
            throws IllegalArgumentException {
        List<TKey> changedKeys = new Vector<>();
        Object ret = internalGet(key, changedKeys);
        notifyListeners(changedKeys);
        return ret;
    }

    protected abstract Object internalGet(TKey key, List<TKey> changedKeys)
            throws IllegalArgumentException;

    /* ------------------------- SETTERS ------------------------- */

    public synchronized void set(TKey key, Object value)
            throws IllegalArgumentException {
        List<TKey> changedKeys = new Vector<>();
        internalSet(key, value, changedKeys);
        notifyListeners(changedKeys);
    }

    protected abstract void internalSet(TKey key, Object value, List<TKey> changedKeys)
            throws IllegalArgumentException;
}
