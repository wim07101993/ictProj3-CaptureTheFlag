package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.mock;

/**
 * Created by wimva on 30/11/2017.
 */

public class Flag {
    private String beaconMac;
    private Float cooldownTime;
    private String cooldownTimer;
    private String team;
    private String timerFixer;

    public Flag(String beaconMac, Float cooldownTime, String cooldownTimer, String team, String timerFixer) {
        this.beaconMac = beaconMac;
        this.cooldownTime = cooldownTime;
        this.cooldownTimer = cooldownTimer;
        this.team = team;
        this.timerFixer = timerFixer;
    }

    public String getBeaconMac() {
        return beaconMac;
    }

    public void setBeaconMac(String beaconMac) {
        this.beaconMac = beaconMac;
    }

    public Float getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(Float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public String getCooldownTimer() {
        return cooldownTimer;
    }

    public void setCooldownTimer(String cooldownTimer) {
        this.cooldownTimer = cooldownTimer;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTimerFixer() {
        return timerFixer;
    }

    public void setTimerFixer(String timerFixer) {
        this.timerFixer = timerFixer;
    }
}
