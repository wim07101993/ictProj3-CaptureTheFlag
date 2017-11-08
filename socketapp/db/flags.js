export default class Flag{
    beaconMAC="";
    cooldownTime="";
    cooldownTimer="";
    team=""; 
    timerFixer="";
    constructor(beaconMAC,cooldownTime,cooldownTimer,team,timerFixer){
        this.beaconMAC = beaconMAC;
        this.cooldownTime = cooldownTime;
        this.cooldownTimer = cooldownTimer;
        this.team = team;
        this.timerFixer = timerFixer;
    
    }
    
}


