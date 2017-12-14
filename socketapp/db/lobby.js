import Team from '../db/team';
import timeClass from '../library/timeSync';


export default class Lobby {
    id = "";
    name = "";
    password = "";
    time = "";
    players = [];
    teams = [];
    playersockets=[];
    flags=[];
    timeInterval;
    timer=null;
    scoreInterval;
 
    constructor(id, name, password, time, players){
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = time;
        this.players = players;
        this.addStaticTeams();
        
    }
    deleteLobby(){
        console.log("deleting lobby");
        delete this.players;
        delete this.teams;
        this.name="";
        this.id="";
        clearInterval(this.scoreInterval);
        clearInterval(this.timeInterval);
        delete this.flags;
        if(this.timer!=null){this.timer=null};
    }
    showEndScreen(){
        try{
        let greenScore=0;
        let redScore=0;
        let finalMessage="The winner is: Team ";
        this.teams.filter((team)=>{
            if(team.teamName=="orange"){
                redScore= team.score;
            }
            if(team.teamName=="green"){
                greenScore= team.score;
            }
        });
        if(greenScore>redScore) {
            finalMessage+="Green"
        }else if(redScore > greenScore){
            finalMessage+="Orange"
        }else{
            finalMessage="It's a draw";
        }
        this.emit("endScreen",finalMessage);
        this.deleteLobby()
    }catch(e){}
    }
    captureFlags(flag){
        try {
            let index=-1;
            let newflags;
            var length=0;
            if(this.flags.length!=0){
            newflags=this.flags.filter((compareFlag)=>{
                try {
                    if(compareFlag.beaconMAC== JSON.parse(flag).beaconMAC){
                        index++;
                        return true;
                    }
                    return false;
                } catch (error) {
                    return false;
                }
              
            })
            length = newflags.length;
        }
            if(length==0){

                this.flags.push(JSON.parse(flag));
                let flags = JSON.stringify(this.flags);

            }else{
                this.flags[index]=JSON.parse(flag);
            }
            
            this.emit("syncFlags",JSON.stringify(this.flags));
        } catch (error) {
            
        }
        
    } 
    startScore(){
        try {
            
            let parent = this;
            setInterval(()=>parent.countScore(parent),5000);
        } catch (error) {
            
        }
    }
    countScore(parent){
        try {
            
            let flagIndex=0;
            let scoreOrange=0;
            let scoreGreen=0;
            
            for(flagIndex; flagIndex<parent.flags.length;flagIndex++){
                let flag = parent.flags[flagIndex];
                if(flag != undefined){
                    if(flag.team=="orange"){
                        scoreOrange+=5;
                    }
                    if(flag.team=="green"){
                        scoreGreen+=5;
                    }
                }
            }
                parent.teams.filter((team)=>{
                if(team.teamName=="orange"){
                    team.score+=scoreOrange;
                    scoreOrange=team.score;
                    console.log("orange:"+scoreOrange)
                }
                if(team.teamName=="green"){
                    team.score+=scoreGreen;
                    scoreGreen=team.score;
                    console.log("green:"+scoreGreen)
                }
            });
            //parent.emit("syncScore",JSON.stringify({"green":scoreGreen,"orange":scoreOrange}));
            parent.emit("syncTeamScore",JSON.stringify(parent.teams));
        } catch (error) {
            
        }
    }
    addStaticTeams(){
        try {
            
            let teamOrange = new Team("orange", 0);
            let teamGreen = new Team("green", 0);
            let teamNo = new Team("no_team", 0);
            this.teams.push(teamOrange);
            this.teams.push(teamGreen);
            this.teams.push(teamNo);
        } catch (error) {
            
        }
    }

    addPlayer(name,  socket){
        try {
            
            this.players.push({"name":name,"team":{"teamName":"no_team","score":"0"}});
            this.playersockets.push({"name":name,socket});
        } catch (error) {
            
        }
    }
    emit(notifier,data){
         try {
             
             let playerIndex =0;
             for(playerIndex; playerIndex<this.playersockets.length; playerIndex++){
                try {
                    let player = this.playersockets[playerIndex]
                    if(player!=undefined){
                        player.socket.emit(notifier,data)
                    }
                } catch (error) {
                    
                }
             }
         } catch (error) {
             
         }
       
    }
}
