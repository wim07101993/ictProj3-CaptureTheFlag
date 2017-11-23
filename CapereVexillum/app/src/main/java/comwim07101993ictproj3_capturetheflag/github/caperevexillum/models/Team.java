package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import java.util.Vector;

/**
 * Created by Michiel on 12/10/2017.
 */

public class Team {
    public static String TEAM_GREEN="green";
    public static String TEAM_ORANGE ="orange";
    public static String NO_TEAM = "no_team";

    private String teamname;
    private int score;

    public Team(String teamname, int score){
        this.teamname = teamname;
        this.score = score;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //    /* ---------------------------------------------------------- */
//    /* ------------------------- FIELDS ------------------------- */
//    /* ---------------------------------------------------------- */
//
//    /**
//     * Team identifier team green
//     */
//    public static String TEAM_GREEN="green";
//    /**
//     * Team identifier team orange
//     */
//    public static String TEAM_ORANGE ="orange";
//    /**
//     * Team identifier no team
//     */
//    public static String NO_TEAM = "no team";
//    /**
//     * Property that keeps track of this Team's specific alignment
//     */
//    private String teamIdentifier;
//
//    /**
//     * Property that keeps track of the players in this Team
//     */
//    private Vector<User> Users = new Vector<User>();
//
//    /**
//     * Property that keeps track of this Team's score
//     */
//    private int score;
//
//    /* --------------------------------------------------------------- */
//    /* ------------------------- CONSTRUCTOR ------------------------- */
//    /* --------------------------------------------------------------- */
//
//    /**
//     * Constructor creates an instance of Team
//     * setting the teamIdentifier and score
//     *
//     * @param teamIdentifier identifier of the team
//     */
//    private Team(String teamIdentifier){
//        //Sets the teamIdentifier to what should be either green or orange
//        this.teamIdentifier = teamIdentifier;
//        //Sets the Team's score to zero
//        score = 0;
//    }
//
//    /* ----------------------------------------------------------- */
//    /* ------------------------- METHODS ------------------------- */
//    /* ----------------------------------------------------------- */
//
//    /**
//     * Method adds new players to the Users vector
//     *
//     * @param newUser
//     */
//    public void addUser(User newUser){
//        Users.add(newUser);
//    }
//
//    /* ------------------------- GETTERS ------------------------- */
//
//    /**
//     * @return the Users vector
//     */
//    public Vector<User> getUsers() {
//        return Users;
//    }
//
//    /**
//     * @return the Team's score
//     */
//    public int getScore() {
//        return score;
//    }
//
//    /* ------------------------- SETTERS ------------------------- */
//
//    /**
//     * @param users the Users vector's content
//     */
//    public void setUsers(Vector<User> users) {
//        Users = users;
//    }
//
//    /**
//     * @param score the Team's score
//     */
//    public void setScore(int score) {
//        this.score = score;
//    }
}
