package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

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
        return new Gson().toJson(this);
    }

    @Override
    public void deserialize(String serializedObject) {
        List<Player> playerList = new Gson().fromJson(serializedObject, new TypeToken<List<Player>>() {
        }.getType());
        this.clear();
        this.addAll(playerList);
    }
}
