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

    private String lobbyName;
    private String lobbyPassword;
    private float totalGameTime;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public LobbySettings(String lobbyName, String lobbyPassword, float totalGameTime) {
        this.lobbyName = lobbyName;
        this.lobbyPassword = lobbyPassword;
        this.totalGameTime = totalGameTime;
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
        lobbyName = This.getLobbyName();
        lobbyPassword = This.getLobbyPassword();
        totalGameTime = This.getTotalGameTime();
    }

    /* ------------------------- GETTERS ------------------------- */

    public String getLobbyName() {
        return lobbyName;
    }

    public String getLobbyPassword() {
        return lobbyPassword;
    }

    public float getTotalGameTime() {
        return totalGameTime;
    }

}
