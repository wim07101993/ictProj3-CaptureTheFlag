package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock;

import java.util.List;

/**
 * Created by wimva on 30/11/2017.
 */

public class Lobby {
    private int id;
    private String name;
    private String password;
    private Float time;
    private List<Player> players;
    private Team[] teams = {};
    //private List playerSockets;
    private List<Flag> flags;

    public Lobby(int id, String name, String password, Float time, List<Player> players) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = time;
        this.players = players;
    }


    public void addPlayer(Player player) {
        players.add(player);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Float getTime() {
        return time;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Team[] getTeams() {
        return teams;
    }

    public List<Flag> getFlags() {
        return flags;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }
}
