package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

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
    public String Serialize() {
        return new Gson().toJson(this);
    }

    @Override
    public void Deserialize(String serializedObject) {
        LobbySettings This = new Gson().fromJson(serializedObject, LobbySettings.class);
        name = This.name;
        password = This.password;
        totalGameTime = This.totalGameTime;
        hostName = This.hostName;
        id = This.id;
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

}
