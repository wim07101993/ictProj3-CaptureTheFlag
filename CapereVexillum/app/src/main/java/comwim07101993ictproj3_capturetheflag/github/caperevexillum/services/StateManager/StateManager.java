package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.StateManager;

import java.util.Date;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;

/**
 * Created by Sanli on 19/10/2017.
 */

public class StateManager {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private static StateManager instance = new StateManager();

    private Date endTime;
    private Vector<Team> teams = new Vector<Team>();
    private Flags flags = new Flags();

    private boolean quizStarter = false;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /* ------------------------- GETTERS ------------------------- */

    public static StateManager getInstance() {
        return instance;
    }

    public Object get(StateManagerKey key) {
        switch (key) {
            case END_TIME:
                return endTime;
            case TEAMS:
                return teams;
            case FLAGS:
                return flags;
            case QUIZ_STARTER:
                return quizStarter;
            default:
                throw new IllegalArgumentException("Key not know");
        }
    }

    public void Save() {

    }

    /* ------------------------- SETTERS ------------------------- */

    public void set(StateManagerKey key, Object value) throws IllegalArgumentException {
        try {
            switch (key) {
                case END_TIME:
                    endTime = (Date) value;
                    break;
                case TEAMS:
                    teams = (Vector<Team>) value;
                    break;
                case FLAGS:
                    flags = (Flags) value;
                    break;
                case QUIZ_STARTER:
                    quizStarter = (boolean) value;
                    break;
                default:
                    throw new IllegalArgumentException("Key not know");
            }
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException(
                    "The argument 'value' could not be casted to the needed type.");
        }
    }
}
