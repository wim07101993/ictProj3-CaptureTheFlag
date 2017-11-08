package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

import static android.content.SharedPreferences.Editor;

/**
 * Created by Wim Van Laer on 20/10/2017.
 * <p>
 * AbstractStateManager is a state manager that implements the IStateManger. The type TKey is
 * used to identify the different states.
 *
 * @see IStateManager
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractStateManager<TKey> implements IStateManager<TKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    /**
     * savedValues is the SharedPreferences object used to save
     * and load the current state (currentState)
     */
    protected SharedPreferences savedValues;
    /**
     * sharedPreferencesName is the name with which the savedValues are saved and loaded to and
     * from the database.
     */
    protected String sharedPreferencesName;

    /**
     * currentState is the current state of the state manager.
     */
    protected Map<TKey, Object> currentState = new HashMap<>();

    /**
     * stateChangedListeners is a list of all the listeners that need to be updated when a state
     * changes.
     * <p>
     * To add listeners to this list the addStateChangedListener method is used.
     * To remove listeners form this list the removeStateChangedListener method is used.
     */
    protected List<OnStateChangedListener> stateChangedListeners = new Vector<>();

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * AbstractStateManger is the constructor for the abstract class AbstractStateManager.
     * <p>
     * It sets the savedValues to the given sharedPreferences and the sharedPreferencesName to
     * the given value.
     *
     * @param sharedPreferences     the shared preferences to load and save the current state from and to.
     * @param sharedPreferencesName the name of the shared preferences in the database.
     */
    protected AbstractStateManager(SharedPreferences sharedPreferences, String sharedPreferencesName) {
        // set global fields
        this.sharedPreferencesName = sharedPreferencesName;
        savedValues = sharedPreferences;

        // load previous saved state.
        load();
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * addStateChangedListener adds the given OnStateChangedListener to the list of
     * listeners so that it may receive updates when a state changed.
     *
     * @param listener is the OnStateChangedListener to add to the list of listeners.
     */
    public void addStateChangedListener(OnStateChangedListener listener) {
        stateChangedListeners.add(listener);
    }

    /**
     * removeStateChangedListener removes the given OnStateChangedListener from the
     * list of listeners so that it may no longer receive updates when a state changed.
     *
     * @param listener is the OnStateChangedListener to remove from the list of listeners.
     */
    public void removeStateChangedListener(OnStateChangedListener listener) {
        stateChangedListeners.remove(listener);
    }

    /**
     * notifyListeners notifies all listeners from the stateChangedListeners that some states
     * have been changed.
     *
     * @param keys are the keys of the states that have been changed.
     */
    public void notifyListeners(List<TKey> keys) {
        // loop over all listeners
        for (OnStateChangedListener l : stateChangedListeners) {
            // invoke stateChangedMethod
            //noinspection unchecked
            l.stateChanged(keys, this);
        }
    }

    /**
     * notifyListeners is supposed to notify all listeners from the stateChangedListeners that some
     * state has been changed.
     *
     * @param key is the key of the state that has been changed.
     */
    public void notifyListeners(TKey key) {
        List<TKey> keys = new ArrayList<>();
        keys.add(key);
        notifyListeners(keys);
    }

    /**
     * save is supposed to save the current state to a database.
     *
     * @return boolean to indicate whether the state has been saved or not.
     */
    public synchronized boolean save() {
        // Create new Gson for converting json.
        Gson gson = new Gson();
        // get the editor from the saved values
        Editor editor = savedValues.edit();
        // add the current state to the saved values
        editor.putString(sharedPreferencesName, gson.toJson(currentState));
        // apply changes.
        editor.apply();
        return true;
    }

    /**
     * load loads the previous state from a database.
     *
     * @return boolean to indicate whether an old state has been restored.
     */
    public synchronized boolean load() {
        // get the value from the saved values
        String json = savedValues.getString(sharedPreferencesName, null);

        // if value equals null => create new map
        if (json == null) {
            currentState = new HashMap<>();
            return false;
        }

        Gson gson = new Gson();
        // Create type to convert json to.
        Type t = new TypeToken<Map<TKey, Object>>() {
        }.getType();
        // set current state to the converted json.
        this.currentState = gson.fromJson(json, t);

        return true;
    }

    /* ------------------------- GETTERS ------------------------- */

    /**
     * get returns the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    public synchronized Object get(TKey key)
            throws IllegalArgumentException {
        List<TKey> changedKeys = new Vector<>();
        Object ret = internalGet(key, changedKeys);
        notifyListeners(changedKeys);
        return ret;
    }


    /**
     * internalGet is the method that gets called when someone tries to get the state of a key.
     * This method is supposed to do the internal handling of the change of a state when the state
     * is called.
     *
     * @param key         key is the key of which the state is asked.
     * @param changedKeys chagedKeys are the keys that changed in the chain of getting (and maybe
     *                    setting) states.
     * @return The value of the asked state.
     * @throws IllegalArgumentException It is possible that when an argument is passed, the argument
     *                                  is not valid. In that case, the exception is thrown.
     */
    protected abstract Object internalGet(TKey key, List<TKey> changedKeys)
            throws IllegalArgumentException;

    /* ------------------------- SETTERS ------------------------- */

    /**
     * set is sets the state of the TKey key with the value value.
     *
     * @param key   is the key to set the value of.
     * @param value is the value to set the state to.
     */
    public synchronized void set(TKey key, Object value)
            throws IllegalArgumentException {
        List<TKey> changedKeys = new Vector<>();
        internalSet(key, value, changedKeys);
        notifyListeners(changedKeys);
    }

    /**
     * internalSet is the method that gets called when someone tries to set the state of a key.
     * This method is supposed to do the internal handling of the change of a state when the state
     * is set.
     *
     * @param key         key is the key of which the value is set.
     * @param value       value is the value that the state should be set to.
     * @param changedKeys changedKeys are the keys that changed in the chain of setting (and maybe
     *                    getting) states.
     * @throws IllegalArgumentException It is possible that when an argument is passed, the argument
     *                                  is not valid. In that case, the exception is thrown.
     */
    protected abstract void internalSet(TKey key, Object value, List<TKey> changedKeys)
            throws IllegalArgumentException;
}
