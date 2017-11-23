package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.Variables;

/**
 * Created by Sanli on 19/11/2017.
 */

public class SocketInstance {
    private static Socket socketInstance = null;
    //public static final String SERVER_URL = "http://192.168.0.197:4040";
    public static Socket restartSocket(){
        try {
            socketInstance = IO.socket(Variables.GAME_SERVER);
            socketInstance.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return  socketInstance;
    }
    public static Socket socket() {
        if(socketInstance == null) {
            try{
                socketInstance = IO.socket(Variables.GAME_SERVER);
                socketInstance.connect();
            }catch(Exception e){
                Log.d("socketInstance", e.getMessage());
            }
        }
        return socketInstance;
    }
}
