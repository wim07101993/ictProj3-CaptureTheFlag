package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.util.Log;

import com.google.gson.Gson;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;

/**
 * Created by Sven on 16/11/2017.
 */

public class Player implements ISerializable{

    /* ------------------------- FIELDS ------------------------- */

    private String name;
    private Team team;
    private int lobbyId=-1;

    /* ------------------------- CONSTRUCTOR ------------------------- */

    public void setLobbyId(int lobbyId){
        this.lobbyId=lobbyId;
    }
    public Player(String name, Team team){
        this.name = name;
        this.team = team;
    }

    /* ------------------------- METHODS ------------------------- */

    @Override
    public String serialize() {
        try{
            return new Gson().toJson(this);}
        catch(Exception ex){
            Log.d("Model=>Player", "serialize: Player");
            throw new RuntimeException();
        }
    }

    @Override
    public void deserialize(String serializedObject) {
        Log.d("Model=>player", "deserialize: player"+serializedObject);
        try{
            Player This = new Gson().fromJson(serializedObject, Player.class);

            name = This.name;
            team = This.team;

        }catch(Exception ex){
            Log.d("Model=>player", "deserialize: Player");
            throw new RuntimeException();

        }
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
