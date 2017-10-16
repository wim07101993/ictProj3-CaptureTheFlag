package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import android.content.Context;
import android.os.CountDownTimer;

import java.util.Timer;
import java.util.TimerTask;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.MainActivity;

/**
 * Created by Michiel on 12/10/2017.
 */

public class Flag {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    //Property that keeps track of this Flag's beacon's MAC address
    private String beaconMAC;

    //Property that keeps track of whether this Flag is in cooldown or not
    private boolean cooldown;

    Timer timer;
    TimerTask UpdateTimer;

    //Team identifier no team
    private static final String NO_TEAM = "no team";

    //Property that keeps track of this specific Flag's Team alignment
    private String team;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    //Constructor creates an instance of Flag
    //setting the beaconMAC to the beacon's MAC,
    //setting the cooldown on false and the team alignment to no team
    private Flag(Beacon beacon, Timer coolDownTimer){
        //Sets the Flag's beaconMAC to the beacon's MAC address
        beaconMAC = beacon.getAddress();
        //Sets the Flag's cooldown to false
        cooldown = false;
        //Sets the Team alignment to no team
        team = NO_TEAM;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    //Method enables cooldown for this Flag
    public void coolDownTimer(int timeInMillis, final MainActivity parent){
        //Create an instance of a Timer object
        timer = new Timer();
        //Set the task the Timer executes
        //after a set amount of time (thirty seconds)
        UpdateTimer = new TimerTask() {
            @Override
            public void run() {
                //Set this Flag's cooldown property to false
                cooldown = false;
                //Cancel the timer
                timer.cancel();
            }
        };
    }
    //Method changes this Flag's team alignment
    //and activates cooldown for this Flag
    public void CaptureAndCooldown(String teamIdentifier) {
        //Checks whether this flag is not already in cooldown
        if(cooldown == false) {
            //Sets this Flag's team alignment to the team that captured it
            team = teamIdentifier;
            //Sets this Flag to cooldown
            cooldown = true;
            //Start the Timer to end the cooldown in thirty seconds
            timer.scheduleAtFixedRate(UpdateTimer, 30000, 1000);

        }
    }


    /* ------------------------- GETTERS ------------------------- */

    //Returns the Flag's beaconMAC
    public String getBeaconMAC() {
        return beaconMAC;
    }

    //Returns the cooldown boolean
    public boolean getCooldown() {
        return cooldown;
    }

    //Returns the Flag's team alignment
    public String getTeam() {
        return team;
    }

    /* ------------------------- SETTERS ------------------------- */

    //Sets the Flag's beaconMAC
    public void setBeaconMAC(Beacon beacon) {
        this.beaconMAC = beacon.getAddress();
    }

    //Sets the Flag's cooldown
    public void setCooldown(boolean cooldown) {
        this.cooldown = cooldown;
    }

    //Sets the Flag's team alignment
    public void setTeam(String team) {
        this.team = team;
    }
}