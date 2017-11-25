package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ArrayHelpers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.ANotifier;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.IListener;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.notifier.INotifier;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;

/**
 * Created by wimva on 25/11/2017.
 */

class SocketService
        extends ANotifier<ISocketKey>
        implements ISocketService<ISocketKey> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static final String TAG = SocketService.class.getSimpleName();

    private String serverIPAddress;
    private int serverPort;

    private Socket socket;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public SocketService(String serverIPAddress, int serverPort) {

        this.serverIPAddress = serverIPAddress;
        this.serverPort = serverPort;

        initSocket();
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    private void initSocket() {
        try {
            socket = IO.socket(serverIPAddress + ":" + serverPort);

            registerListeners();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners() {
        for (final ESocketOnKey key : ESocketOnKey.values()) {
            socket.on(key.getStringIdentifier(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Class c = key.getValueClass();

                    if (args.getClass().isAssignableFrom(c)) {
                        notifyListeners(key, args);
                    } else if (!ArrayHelpers.IsNullOrEmpty(args) && args[0].getClass().isAssignableFrom(c)) {
                        notifyListeners(key, args[0]);
                    }
                }
            });
        }
    }

    @Override
    public void Send(ISocketKey key, Object value) {
        if (key.getMode() != ISocketKey.EMode.EMIT) {
            throw new UnsupportedOperationException("The key " + key.getStringIdentifier() + " does not support sending.");
        }

        Class c = key.getValueClass();
        String identifier = key.getStringIdentifier();

        if (c == null) {
            socket.emit(identifier);
        } else if (value.getClass().isAssignableFrom(c)) {
            socket.emit(identifier, value);
        } else {
            String error = "Value not of right type. (Type is " + value.getClass() + ". Needed type is " + c + ".";
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public boolean addSpecificListener(IListener<INotifier, ISocketKey> listener, ISocketKey key) {
        if (key.getMode() == ISocketKey.EMode.ON) {
            return super.addSpecificListener(listener, key);
        }
        throw new UnsupportedOperationException("The key " + key.getStringIdentifier() + " does not support listening.");
    }

    /* ------------------------- GETTERS ------------------------- */

    @Override
    public String getServerAddress() {
        return serverIPAddress;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }


    /* ------------------------- SETTERS ------------------------- */

}
