package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.IListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.INotifier;

/**
 * Created by Wim Van Laer on 20/10/2017.
 * Last Updated by Wim Van Laer on 25/11/2017.
 *
 * The IStateManager is an interface for a state manager. If you want an abstract with most of the
 * methods implemented you can use the AStateManager.
 *
 * TKey is the type of the keys to identify the different states.
 */

public interface IStateManager<TKey>
        extends INotifier<IListener<INotifier, TKey>, TKey> {

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    ISerializable getSerializable(TKey key);

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    Integer getInt(TKey key);

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    Long getLong(TKey key);

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    Float getFloat(TKey key);

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    String getString(TKey key);

    /**
     * get is supposed to return the state behind of TKey key.
     *
     * @param key is the key to get value of.
     * @return The value of the state behind of TKey key.
     */
    Boolean getBoolean(TKey key);


    /**
     * setLong is supposed to setLong the state of the TKey key with the value value.
     *
     * @param key   is the key to setLong the value of.
     * @param value is the value to setLong the state to.
     */
    void setSerializable(TKey key, ISerializable value);

    /**
     * setLong is supposed to setLong the state of the TKey key with the value value.
     *
     * @param key   is the key to setLong the value of.
     * @param value is the value to setLong the state to.
     */
    void setInt(TKey key, int value);

    /**
     * setLong is supposed to setLong the state of the TKey key with the value value.
     *
     * @param key   is the key to setLong the value of.
     * @param value is the value to setLong the state to.
     */
    void setLong(TKey key, long value);

    /**
     * setLong is supposed to setLong the state of the TKey key with the value value.
     *
     * @param key   is the key to setLong the value of.
     * @param value is the value to setLong the state to.
     */
    void setFloat(TKey key, float value);

    /**
     * setLong is supposed to setLong the state of the TKey key with the value value.
     *
     * @param key   is the key to setLong the value of.
     * @param value is the value to setLong the state to.
     */
    void setString(TKey key, String value);

    /**
     * setLong is supposed to setLong the state of the TKey key with the value value.
     *
     * @param key   is the key to setLong the value of.
     * @param value is the value to setLong the state to.
     */
    void setBoolean(TKey key, boolean value);

    /**
     * save is supposed to save the current state to a database.
     *
     * @return boolean to indicate whether the state has been saved or not.
     */
    boolean save();

    /**
     * load is supposed to load the previous state from a database.
     *
     * @return boolean to indicate whether an old state has been restored.
     */
    boolean load();

    /**
     * clear is supposed to clear all the data stored on the device.
     */
    void clear();
}
