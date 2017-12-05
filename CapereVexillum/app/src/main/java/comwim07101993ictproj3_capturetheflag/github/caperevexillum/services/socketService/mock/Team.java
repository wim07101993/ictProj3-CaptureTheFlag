package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock;

/**
 * Created by wimva on 30/11/2017.
 */

public class Team {
    private String teamName;
    private double score;


    public Team(String teamName, double score) {
        this.teamName = teamName;
        this.score = score;
    }


    public String getTeamName() {
        return teamName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
