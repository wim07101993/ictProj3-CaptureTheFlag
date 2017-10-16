package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import java.util.Vector;

/**
 * Created by Michiel on 12/10/2017.
 */

public class Flags {
    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    //Property that keeps track of all the flags
    private Vector<Flag> registeredFlags = new Vector<Flag>();

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    //Constructor creates an instance of Flags
    //no properties need to be set
    private Flags(){
        //Nothing to do here
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    //Method adds new flags to the registeredFlags vector
    public void addFlag(Flag flag){
        registeredFlags.add(flag);
    }

    //Method scans for an existing flag in the vector registeredFlags
    //based on the registered beaconMAC of a flag
    public boolean findFlag(Beacon beacon){
        //The beaconMAC the function is looking for
        String beaconMAC = beacon.getAddress();
        //Iterates over every flag registered in the registeredFlags vector
        for (Flag flag : registeredFlags){
            //Checks if the currently iterated flag's beaconMAC matches
            // the beaconMAC the function is looking for
            if (flag.getBeaconMAC() == beaconMAC){
                //If the beaconMAC's match the function returns true
                return true;
            }
        }
        //If the function found no existing flag with a
        //matching beaconMAC the function returns false
        return false;
    }

    /* ------------------------- GETTERS ------------------------- */

    //Returns the registeredFlags vector
    public Vector<Flag> getRegisteredFlags() {
        return registeredFlags;
    }

    /* ------------------------- SETTERS ------------------------- */

    //Sets the registeredFlags vector's content
    public void setRegisteredFlags(Vector<Flag> registeredFlags) {
        this.registeredFlags = registeredFlags;
    }
}
