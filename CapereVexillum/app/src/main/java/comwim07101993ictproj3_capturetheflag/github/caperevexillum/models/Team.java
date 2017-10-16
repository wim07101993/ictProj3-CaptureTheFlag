package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import java.util.Vector;

/**
 * Created by Michiel on 12/10/2017.
 */

public class Team {
    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    //Team identifier team green
    public static String TEAM_GREEN="green";
    //Team identifier team orange
    public static String TEAM_ORANGE ="orange";

    //Property that keeps track of this Team's specific alignment
    private String teamIdentifier;

    //Property that keeps track of the players in this Team
    private Vector<User> Users = new Vector<User>();

    //Property that keeps track of this Team's score
    private int score;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    //Constructor creates an instance of Team
    //setting the teamIdentifier and score
    private Team(String teamIdentifier){
        //Sets the teamIdentifier to what should be either green or orange
        this.teamIdentifier = teamIdentifier;
        //Sets the Team's score to zero
        score = 0;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    //Method adds new players to the Users vector
    public void addUser(User newUser){
        Users.add(newUser);
    }

    /* ------------------------- GETTERS ------------------------- */

    //Returns the Users vector
    public Vector<User> getUsers() {
        return Users;
    }

    //Returns the Team's score
    public int getScore() {
        return score;
    }

    /* ------------------------- SETTERS ------------------------- */

    //Sets the Users vector's content
    public void setUsers(Vector<User> users) {
        Users = users;
    }

    //Sets the Team's score
    public void setScore(int score) {
        this.score = score;
    }
}
