package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

/**
 * Created by wimva on 9/11/2017.
 */

public class LobbySettings {

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


    /* ------------------------- SETTERS ------------------------- */

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public void setLobbyPassword(String lobbyPassword) {
        this.lobbyPassword = lobbyPassword;
    }

    public void setTotalGameTime(float totalGameTime) {
        this.totalGameTime = totalGameTime;
    }


}
