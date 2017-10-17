package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

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

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * Constructor creates an instance of Flags
     * no properties need to be set
     */
    public Flags(){
        //Nothing to do here
    }

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
    }

    /**
     * Method scans for an existing flag in the vector registeredFlags
     * based on the registered beaconMAC of a flag
     *
     * @param beacon beacon to search the flag of
     * @return whether the flag exists or not
     */
    public boolean findFlag(Beacon beacon){
        //The beaconMAC the function is looking for

        String beaconMAC = beacon.getAddress();
        //Iterates over every flag registered in the registeredFlags vector
        for (Flag flag : registeredFlags){
            //Checks if the currently iterated flag's beaconMAC matches
            // the beaconMAC the function is looking for
            if (flag.getBeaconMAC().equals(beaconMAC)){
                //If the beaconMAC's match the function returns true
                return true;
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
}
