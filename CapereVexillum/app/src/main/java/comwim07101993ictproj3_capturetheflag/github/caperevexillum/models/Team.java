package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

/**
 * Created by Michiel on 12/10/2017.
 */

public class Team {

    public static String TEAM_GREEN="green";
    public static String TEAM_ORANGE ="orange";
    public static String NO_TEAM = "no_team";

    private String teamName;
    private int score;


    public Team(String teamName, int score){
        this.teamName = teamName;
        this.score = score;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
