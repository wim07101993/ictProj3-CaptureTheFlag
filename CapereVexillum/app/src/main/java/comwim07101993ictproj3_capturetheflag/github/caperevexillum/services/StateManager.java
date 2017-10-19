package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services;

import java.util.Date;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;

/**
 * Created by Sanli on 19/10/2017.
 */

public class StateManager {
    private Date eindTijd;
    private Vector<Team> teams = new Vector<Team>();
    private Flags flags = new Flags();
    private boolean quizStarter=false;
    private  final static int TEAM_VALUE=0;

    public void set(int key, Object value) {



        switch (key){
            case 1:
                quizStarter = (boolean)value;
                break;

        }

    }


}
