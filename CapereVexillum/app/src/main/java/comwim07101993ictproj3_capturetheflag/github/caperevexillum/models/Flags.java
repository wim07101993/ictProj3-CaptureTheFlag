package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;


/**
 * Created by Michiel on 12/10/2017.
 */

public class Flags implements ISerializable {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    /**
     * Property that keeps track of all the flags
     */
    private Vector<Flag> registeredFlags = new Vector<>();

    // private IFlagSync flagSyncListener;



    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * Constructor creates an instance of Flags
     * no properties need to be set
     */
    public Flags() {/*Nothing to do here*/}



    /* ------------------------------------------------------------- */
    /* ------------------------- LISTENERS ------------------------- */
    /* -------------------------------------------))---------------- */

    Emitter.Listener resyncFlags = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                String request = (String) args[0];
                Log.d("resyncflags", request);
                Flag[] requestedFlags = new Gson().fromJson(request, Flag[].class);
                registeredFlags = (Vector<Flag>) new Vector(Arrays.asList(requestedFlags));
                //updateScoreInView.obtainMessage(1).sendToTarget();
                //  flagSyncListener.syncFlags();

            } catch (Exception ignored) {
            }
        }
    };



    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * Method adds new flags to the registeredFlags vector
     *
     * @param flag the flag to add
     */
    public void addFlag(Flag flag) {
        registeredFlags.add(flag);
//        Socket socket = SocketInstance.socket();
//        String sendValue = new Gson().toJson(flag);
//        socket.emit("captureFlag", sendValue);
    }

    /**
     * Method scans for an existing flag in the vector registeredFlags
     * based on the registered beaconMAC of a flag
     *
     * @param beacon beacon to search the flag of
     * @return whether the flag exists or not
     */
    public Flag findFlag(IBeacon beacon, String team) {
        //The beaconMAC the function is looking for
        String beaconMAC = beacon.getAddress();
        int index = 0;

        //Iterates over every flag registered in the registeredFlags vector
        for (Flag flag : registeredFlags) {
            index++;
            //Checks if the currently iterated flag's beaconMAC matches
            // the beaconMAC the function is looking for
            if (!flag.getBeaconMAC().equals(beaconMAC)) {
                continue;
            }

            //If the beaconMAC's match the function returns true
            if (flag.getBeaconMAC().equals(beacon.getAddress())) {
                return flag;
            }
        }
        //If the function found no existing flag with a
        //matching beaconMAC the function returns null
        return null;
    }

    @Override
    public String Serialize() {
        return new Gson().toJson(registeredFlags);
    }

    @Override
    public void Deserialize(String serializedObject) {
        Vector<Flag> flags = new Gson().fromJson(serializedObject, new TypeToken<Vector<Flag>>() {
        }.getType());

        this.registeredFlags = flags;
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
        for (Flag flag : registeredFlags) {
            index++;
            //Checks if the currently iterated flag's beaconMAC matches
            // the beaconMAC the function is looking for
            if (flag.getBeaconMAC().equals(beacon.getAddress())) {
                registeredFlags.remove(index);
            }
        }
    }

    public int getFlagByTeam(String team) {
        int amountOfFlags = 0;
        for (Flag flag : registeredFlags) {
            if (flag.team.equals(team))
                amountOfFlags++;
        }
        return amountOfFlags;
    }
//
//    public void setSyncFlagListener(IFlagSync flagSyncListener) {
//        this.flagSyncListener = flagSyncListener;
//    }
//
//    public void startSocketListener() {
//    //    Socket socket = SocketInstance.socket();
//     //   socket.on("syncFlags", resyncFlags);
//    }

}
