package comwim07101993ictproj3_capturetheflag.github.caperevexillum.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michiel on 12/10/2017.
 */
public class Flag {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    /**
     * Property that keeps track of this Flag's beacon's MAC address
     */
    private String beaconMAC;

    /**
     * Property that keeps track of whether this Flag is in cooldown or not
     */
    private int cooldownTime=30;
    private Date cooldownTimer;

    private boolean timerFixer = false;
    /**
     * Team identifier no team
     */
    private static final String NO_TEAM = "no team";

    /**
     * Property that keeps track of this specific Flag's Team alignment
     */
    public String team;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    /**
     * Constructor creates an instance of Flag
     * setting the beaconMAC to the beacon's MAC,
     * setting the cooldown on false and the team alignment to no team
     *
     * @param beacon beacon that represents the flag
     */
    public Flag(Beacon beacon) {
        //Sets the Flag's beaconMAC to the beacon's MAC address
        beaconMAC = beacon.getAddress();
        //Sets the Flag's cooldown to false

        //Sets the Team alignment to no team
        team = NO_TEAM;
        cooldownTimer = Calendar.getInstance().getTime();
        cooldownTimer.setMinutes(cooldownTimer.getMinutes()+cooldownTime);

    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * Method enables cooldown for this Flag
     */


    /**
     * Method changes this Flag's team alignment
     * and activates cooldown for this Flag
     *
     * @param teamIdentifier team that captured the flag
     */
    public void CaptureAndCooldown(String teamIdentifier) {

            //Sets this Flag's team alignment to the team that captured it
            team = teamIdentifier;
            //Sets this Flag to cooldown
        Calendar coolDownCal = Calendar.getInstance();
        coolDownCal.add(Calendar.SECOND,cooldownTime);
        cooldownTimer = coolDownCal.getTime();




    }


    /* ------------------------- GETTERS ------------------------- */

    /**
     * @return the Flag's beaconMAC
     */
    public String getBeaconMAC() {
        return beaconMAC;
    }

    /**
     * @return whether this Flag is in cooldown or not
     */
    public boolean getCooldown() {
        Date nowTime = Calendar.getInstance().getTime();

        long cooldown = cooldownTimer.getTime();
        long now = nowTime.getTime();
        return (now > cooldown);
    }
    public Date getCooldownTime() {
        return  cooldownTimer;
    }
    /**
     * @return the Flag's team alignment
     */
    public String getTeam() {
        return team;
    }

    /* ------------------------- SETTERS ------------------------- */

    /**
     * @param beacon the Flag's beaconMAC
     */
    public void setBeaconMAC(Beacon beacon) {
        this.beaconMAC = beacon.getAddress();
    }



    /**
     * @param team the Flag's team alignment
     */
    public void setTeam(String team) {
        this.team = team;
    }
}