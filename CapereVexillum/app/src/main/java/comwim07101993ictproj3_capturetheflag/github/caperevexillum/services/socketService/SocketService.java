package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ArrayHelpers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer.AObservable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketService;

/**
 * Created by wimva on 25/11/2017.
 */

class SocketService
        extends AObservable
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
            socket.connect();

            registerListeners();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners() {
        Object values =  ESocketOnKey.values();

        for (final ESocketOnKey key : ESocketOnKey.values()) {
            if(key.getStringIdentifier().equals(null))
                return;
            socket.on(key.getStringIdentifier(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Class c = key.getValueClass();
                    if (args.getClass().isAssignableFrom(c)) {
                        SocketValueChangedArgs socketValueChangedArgs = new SocketValueChangedArgs(key, args);
                        notifyObservers(socketValueChangedArgs);
                    } else if (!ArrayHelpers.IsNullOrEmpty(args) && args[0].getClass().isAssignableFrom(c)) {
                        SocketValueChangedArgs socketValueChangedArgs = new SocketValueChangedArgs(key, args[0]);
                        notifyObservers(socketValueChangedArgs);
                    }
                }
            });
        }
    }
    
    @Override
    public void connect() {
        socket.connect();
    }

    @Override
    public void send(ISocketKey key, Object value) {
        if (key.getMode() != ISocketKey.EMode.EMIT) {
            throw new UnsupportedOperationException("The key " + key.getStringIdentifier() + " does not support sending.");
        }

        Class c = key.getValueClass();
        String identifier = key.getStringIdentifier();

        Log.d(TAG, "sending to server: " + key + " : " + value);
        if (c == null) {
            socket.emit(identifier);
        } else if (value.getClass().isAssignableFrom(c)) {
            socket.emit(identifier, value);
        } else {
            String error = "Value not of right type. (Type is " + value.getClass() + ". Needed type is " + c + ".";
            throw new IllegalArgumentException(error);
        }
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

    @Override
    public boolean isConnected() {
        return socket.connected();
    }

    @Deprecated
    public Socket getSocket() {
        return socket;
    }

    @NonNull
    @Override
    public String getTAG() {
        return TAG;
    }

    /* ------------------------- SETTERS ------------------------- */

}
