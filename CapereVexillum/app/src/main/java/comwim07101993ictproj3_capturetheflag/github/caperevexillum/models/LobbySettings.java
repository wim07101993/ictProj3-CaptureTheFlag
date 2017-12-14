package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.util.Log;

import com.google.gson.Gson;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;

/**
 * Created by wimva on 9/11/2017.
 */

public class LobbySettings implements ISerializable {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private String name;
    private String password;
    private float totalGameTime;
    private String hostName;
    private int id;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public LobbySettings(){}

    public LobbySettings(String lobbyName, String lobbyPassword, float totalGameTime, String hostName) {
        this.name = lobbyName;
        this.password = lobbyPassword;
        this.totalGameTime = totalGameTime;
        this.hostName = hostName;
        id = -1;
    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @Override
    public String serialize() {
        try{
            return new Gson().toJson(this);}
        catch(Exception ex){
            Log.d("Model=>LobbySettings", "serialize: LobbySettings");
           return "";
        }
    }

    @Override
    public void deserialize(String serializedObject) {
        try {
        LobbySettings This = new Gson().fromJson(serializedObject, LobbySettings.class);
        name = This.name;
        password = This.password;
        totalGameTime = This.totalGameTime;
        hostName = This.hostName;
        id = This.id;
        }
        catch(Exception ex){
            Log.d("Model=>LobbySettings", "deserialize: LobbySettings");


        }
    }

    /* ------------------------- GETTERS ------------------------- */

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public float getTotalGameTime() {
        return totalGameTime;
    }

    public String getHostName() {
        return hostName;
    }

    public int getId() {
        return id;
    }

    /* ------------------------- SETTERS ------------------------- */

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTotalGameTime(float totalGameTime) {
        this.totalGameTime = totalGameTime;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
