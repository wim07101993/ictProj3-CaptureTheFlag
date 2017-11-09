package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Vector;



/**
 * Created by Michiel on 12/10/2017.
 */

public class Flags {
    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    /**
     * Property that keeps track of all the flags
     */
    private Vector<Flag> registeredFlags = new Vector<Flag>();
    private IFlagSync flagSyncListener;
    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * Constructor creates an instance of Flags
     * no properties need to be set
     */
    Socket socket;
    public Flags(){/*Nothing to do here*/}
    Gson gson =new Gson();
      /* ----------------------------------------------------------- */
    /* ------------------------- listeners ------------------------- */
    /* ----------------------------------------------------------- */
    Emitter.Listener resyncFlags= new Emitter.Listener() {
          @Override
          public void call(Object... args) {

              String request = (String) args[0];
              Flag[] requestedFlags = gson.fromJson(request,Flag[].class);
              Vector<Flag> newFlags = new Vector(Arrays.asList(requestedFlags));
              registeredFlags = newFlags;
              updateScoreInView.obtainMessage(1).sendToTarget();
          }
      };
    Handler updateScoreInView = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            flagSyncListener.syncFlags();
        }};
    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * Method adds new flags to the registeredFlags vector
     *
     * @param flag the flag to add
     */
    public void addFlag(Flag flag){

        registeredFlags.add(flag);

        String sendValue = gson.toJson(flag).toString();
        socket.emit("addFlag",sendValue);
    }

    /**
     * Method scans for an existing flag in the vector registeredFlags
     * based on the registered beaconMAC of a flag
     *
     * @param beacon beacon to search the flag of
     * @return whether the flag exists or not
     */
    public Object findFlag(Beacon beacon, String team){
        //The beaconMAC the function is looking for

        String beaconMAC = beacon.getAddress();
        int index = 0;
        //Iterates over every flag registered in the registeredFlags vector
        for (Flag flag : registeredFlags){
            index++;
            //Checks if the currently iterated flag's beaconMAC matches
            // the beaconMAC the function is looking for
            if (flag.getBeaconMAC().equals(beaconMAC)){
                //If the beaconMAC's match the function returns true
                if(flag.team.equals(team))
                    return true;
                else{
                    if (!flag.getCooldown())
                    {

                        return flag.getCooldownTime();

                    }else
                    {
                        if (flag.getTeam().equals(Team.NO_TEAM)){
                            registeredFlags.remove(index-1);
                        }
                        return false;
                    }


                }

            }

        }
        //If the function found no existing flag with a
        //matching beaconMAC the function returns false
        return false;
    }

    /* ------------------------- GETTERS ------------------------- */

    /**
     * @return the registeredFlags vector
     */
    public Vector<Flag> getRegisteredFlags() {
        return registeredFlags;
    }

    /* ------------------------- SETTERS ------------------------- */

    /**
     * @param registeredFlags the registeredFlags vector's content
     */
    public void setRegisteredFlags(Vector<Flag> registeredFlags) {

        this.registeredFlags = registeredFlags;
    }

    public void removeBeacon(Beacon beacon) {
        int index = 0;
        //Iterates over every flag registered in the registeredFlags vector
        for (Flag flag : registeredFlags){
            index++;
            //Checks if the currently iterated flag's beaconMAC matches
            // the beaconMAC the function is looking for
            if (flag.getBeaconMAC().equals(beacon.getAddress())){
                registeredFlags.remove(index);
            }
        }
    }
    public int getFlagByTeam(String team){
        int amountOfFlags=0;
        for(Flag flag : registeredFlags){
            if(flag.team.equals(team))
                amountOfFlags++;
        }
        return  amountOfFlags;
    }
    public void setSyncFlagListener(IFlagSync flagSyncListener){
        this.flagSyncListener = flagSyncListener;
    }
    public void addSocket(Socket socket) {
        this.socket = socket;
        socket.on("syncFlags",resyncFlags);
    }
}
