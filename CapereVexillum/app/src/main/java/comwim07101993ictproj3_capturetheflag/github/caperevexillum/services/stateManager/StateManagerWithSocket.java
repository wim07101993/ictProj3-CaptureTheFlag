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
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock.MockSocketFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by Wim Van Laer on 20/10/2017.
 * <p>
 * StateManagerWithSocket is a state manager that extends the abstractStateManager (and thereby implements the
 * IStateManager interface).
 * <p>
 * The StateManagerKey enum is used as key for the abstract class.
 */

public class StateManagerWithSocket
        extends StateManager
        implements Observer {

    private static final String TAG = GameActivity.class.getSimpleName();

    protected ISocketService<ISocketKey> socketService;


    /**
     * StateManagerWithSocket is the constructor for the class StateManagerWithSocket.
     */
    StateManagerWithSocket() {
        super();

        initSocket();
    }

    /**
     * StateManagerWithSocket is the constructor for the class StateManagerWithSocket.
     *
     * @param sharedPreferences the shared preferences to load and save the current state from and to.
     */
    StateManagerWithSocket(SharedPreferences sharedPreferences) {
        super(sharedPreferences);

        initSocket();
    }


    private void initSocket() {
        try {
            if (getBoolean(EStateManagerKey.USE_MOCK_SOCKET_SERVICE)){
                socketService = MockSocketFactory.createNew(
                        getString(EStateManagerKey.SOCKET_SERVER_ADDRESS),
                        getInt(EStateManagerKey.SOCKET_PORT_NUMBER));
            }else{
                socketService = SocketFactory.createNew(
                        getString(EStateManagerKey.SOCKET_SERVER_ADDRESS),
                        getInt(EStateManagerKey.SOCKET_PORT_NUMBER));
            }

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
            socketService = SocketFactory.createNew(
                    getString(EStateManagerKey.SOCKET_SERVER_ADDRESS),
                    getInt(EStateManagerKey.SOCKET_PORT_NUMBER));
            socketService.addObserver(this);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
    }

    @Nullable
    @Override
    protected <T> T getState(IStateManagerKey key, Map<IStateManagerKey, T> map) {
        ESocketEmitKey askKey = key.getSocketEmitAskKey();
        if (askKey != null && socketService.isConnected()) {
            socketService.send(askKey, null);
        }

        return super.getState(key, map);
    }

    @Deprecated
    @Override
    public ISocketService<ISocketKey> getSocketService() {
        return socketService;
    }

    @Override
    protected <T> T updateState(IStateManagerKey key, Map<IStateManagerKey, T> map, T value) {
        ESocketEmitKey putKey = key.getSocketEmitPutKey();
        if (putKey != null) {
            socketService.send(putKey, ArgsConverter.ConvertStateManagerStateToSocketArgs(putKey, value));
        }

        return super.updateState(key, map, value);
    }

}
