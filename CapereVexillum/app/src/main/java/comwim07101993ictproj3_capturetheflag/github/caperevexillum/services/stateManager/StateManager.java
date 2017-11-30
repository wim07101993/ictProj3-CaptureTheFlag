package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketValueChangedArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock.MockSocketFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by Wim Van Laer on 20/10/2017.
 * <p>
 * StateManager is a state manager that extends the abstractStateManager (and thereby implements the
 * IStateManager interface).
 * <p>
 * The StateManagerKey enum is used as key for the abstract class.
 */

public class StateManager extends StateManagerWithoutSocket implements Observer {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = GameActivity.class.getSimpleName();

    private ISocketService<ISocketKey> socketService;
    private static ISocketFactory socketFactory;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * StateManager is the constructor for the class StateManager.
     */
    StateManager() {
        super();

        initSocket();
    }

    /**
     * StateManager is the constructor for the class StateManager.
     *
     * @param sharedPreferences the shared preferences to load and save the current state from and to.
     */
    StateManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);

        initSocket();
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void initSocket() {
        if (socketFactory == null) {
            if (getBoolean(EStateManagerKey.USE_MOCK_SOCKET_SERVICE)){
                   socketFactory = new MockSocketFactory();
            }else{
                socketFactory = new SocketFactory();
            }
        }

        try {
            socketService = (ISocketService<ISocketKey>) socketFactory.get(
                    getString(EStateManagerKey.SOCKET_SERVER_ADDRESS),
                    getInt(EStateManagerKey.SOCKET_PORT_NUMBER));
            socketService.addObserver(this);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
    }

    // TODO WIM: WEAK POINT: TEST METHOD (args converter)
    @Override
    public void update(Observable observable, Object args) {
        if (!(args instanceof SocketValueChangedArgs)) {
            return;
        }

        ISocketKey socketKey = ((SocketValueChangedArgs) args).getKey();
        Object socketArgs = ((SocketValueChangedArgs) args).getArgs();

        for (IStateManagerKey stateManagerKey : EStateManagerKey.values()) {
            if (stateManagerKey.getSocketOnKey() == socketKey) {
                super.updateState(
                        stateManagerKey,
                        getMapForClass(stateManagerKey.getValueClass()),
                        ArgsConverter.ConvertSocketArgsToStateManagerState(
                                stateManagerKey,
                                socketArgs
                        )
                );
            }
        }
    }

    public void restartSocket() {
        try {
            socketService = (ISocketService<ISocketKey>) socketFactory.createNew(
                    getString(EStateManagerKey.SOCKET_SERVER_ADDRESS),
                    getInt(EStateManagerKey.SOCKET_PORT_NUMBER));
            socketService.addObserver(this);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
    }

    /* ------------------------- GETTERS ------------------------- */

    @Nullable
    @Override
    protected <T> T getState(IStateManagerKey key, Map<IStateManagerKey, T> map) {
        ESocketEmitKey askKey = key.getSocketEmitAskKey();
        if (askKey != null && socketService.isConnected()) {
            socketService.Send(askKey, null);
        }

        return super.getState(key, map);
    }

    public ISocketService<ISocketKey> getSocketService() {
        return socketService;
    }



    /* ------------------------- SETTERS ------------------------- */

    @Override
    protected <T> T updateState(IStateManagerKey key, Map<IStateManagerKey, T> map, T value) {
        ESocketEmitKey putKey = key.getSocketEmitPutKey();
        if (putKey != null) {
            socketService.Send(putKey, ArgsConverter.ConvertStateManagerStateToSocketArgs(putKey, value));
        }

        return super.updateState(key, map, value);
    }

}
