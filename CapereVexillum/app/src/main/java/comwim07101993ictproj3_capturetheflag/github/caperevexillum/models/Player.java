package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import com.google.gson.Gson;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;

/**
 * Created by Sven on 16/11/2017.
 */

public class Player implements ISerializable{

    /* ------------------------- FIELDS ------------------------- */

    private String name;
    private Team team;


    /* ------------------------- CONSTRUCTOR ------------------------- */

    public Player(String name, Team team){
        this.name = name;
        this.team = team;
    }

    /* ------------------------- METHODS ------------------------- */

    @Override
    public String Serialize() {
        return new Gson().toJson(this);
    }

    @Override
    public void Deserialize(String serializedObject) {
        Player This = new Gson().fromJson(serializedObject, Player.class);

        name = This.name;
        team = This.team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
