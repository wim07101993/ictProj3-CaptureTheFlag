package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;

/**
 * Created by wimva on 1/12/2017.
 */

public class Players extends Vector<Player> implements ISerializable {

    @Override
    public String serialize() {
        try{
            return new Gson().toJson(this);}
        catch(Exception ex){
            Log.d("Model=>Players", "serialize: Players");
            throw new RuntimeException();
        }
    }

    @Override
    public void deserialize(String serializedObject) {
        try{
            List<Player> playerList = new Gson().fromJson(serializedObject, new TypeToken<List<Player>>() {
            }.getType());
            this.clear();
            this.addAll(playerList);
        }catch(Exception ex){
            Log.d("Models=>Players", "deserialize: in Player");
            throw new RuntimeException();

        }
    }
}
