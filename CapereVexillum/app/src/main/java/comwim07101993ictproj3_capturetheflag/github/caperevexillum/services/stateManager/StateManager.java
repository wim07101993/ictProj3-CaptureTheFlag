package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.IObservable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketFactory;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.SocketValueChangedArgs;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;
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

    private static final String SERVER_ADDRESS = "http://192.168.137.1";
    private static final int SERVER_PORT = 4040;

    private static final int GAME_DURATION_IN_MINUTES = 30;

    private ISocketService<ISocketKey> socketService;
    private static SocketFactory socketFactory;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * StateManager is the constructor for the class StateManager.
     *
     * @param sharedPreferences the shared preferences to load and save the current state from and to.
     */
    public StateManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);

        initSocket();
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void initSocket() {
        if (socketFactory == null) {
            socketFactory = new SocketFactory();
        }

        try {
            socketService = socketFactory.get(SERVER_ADDRESS, SERVER_PORT);
            socketService.addObserver(this);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    // TODO WIM: WEAK POINT: TEST METHOD (args converter)
    @Override
    public void update(Observable observable, Object args) {
        if (!(args instanceof SocketValueChangedArgs)){
            return;
        }

        ISocketKey socketKey = ((SocketValueChangedArgs)args).getKey();
        Object socketArgs = ((SocketValueChangedArgs)args).getArgs();

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
            socketService = socketFactory.createNew(SERVER_ADDRESS, SERVER_PORT);
            initSocket();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /* ------------------------- GETTERS ------------------------- */

    @Nullable
    @Override
    protected <T> T getState(IStateManagerKey key, Map<IStateManagerKey, T> map) {
        ESocketEmitKey askKey = key.getSocketEmitAskKey();
        if (askKey != null) {
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
