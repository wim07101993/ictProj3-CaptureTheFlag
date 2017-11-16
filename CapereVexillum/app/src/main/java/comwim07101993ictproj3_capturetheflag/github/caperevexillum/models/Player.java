package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

/**
 * Created by Sven on 16/11/2017.
 */

public class Player {
    private String name;
    private Team team;

    public Player(String name, Team team){
        this.name = name;
        this.team = team;
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
