package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.databinding.ObservableArrayList;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;


/**
 * Created by Michiel on 12/10/2017.
 * Last updated by Wim on 28/11/2017
 */

public class Flags extends ObservableArrayList<Flag> implements ISerializable {

    @Override
    public String serialize() {

        try{
            return new Gson().toJson(this);}
        catch(Exception ex){
            Log.d("Model=>Flags", ex.getMessage());
           // throw new RuntimeException();
        }
        return "";
    }
    public void clearThis(){
        this.clear();
    }
    @Override
    public void deserialize(String serializedObject) {
        try {
            ObservableArrayList<Flag> flags = new Gson().fromJson(serializedObject, new TypeToken<ObservableArrayList<Flag>>() {
            }.getType());
            this.clear();
            this.addAll(flags);
        }
        catch(Exception ex){
                Log.d("Models=>Flags", ex.getMessage());
//                throw new RuntimeException();

        }
    }

    /**
     * find scans for an existing flag in the vector registeredFlags
     * based on the registered beaconMAC of a flag
     *
     * @param beacon beacon to search the flag of
     * @return whether the flag exists or not
     */
    public Flag find(IBeacon beacon) {
        if(beacon==null){
            return null;
        }

        //The beaconMAC the function is looking for
        String beaconMAC = beacon.getAddress();

        //Iterates over every flag registered in the registeredFlags vector
        for (Flag flag : this) {
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

    public void remove(Beacon beacon) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getBeaconMAC().equals(beacon.getAddress())) {
                remove(i);
                i--;
            }
        }
    }

    public int getNumberOfFlagsOfTeam(String team) {
        int amountOfFlags = 0;
        for (int i = 0; i < size(); i++) {
            if (get(i).team.equals(team)) {
                amountOfFlags++;
            }
        }
        return amountOfFlags;
    }

}
