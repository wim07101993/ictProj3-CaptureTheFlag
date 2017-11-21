import Team from '../db/team';


export default class Lobby {
    id = "";
    name = "";
    password = "";
    time = "";
    players = [];
    teams = [];
    playersockets=[];
    flags=[];
    constructor(id, name, password, time, players){
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = time;
        this.players = players;
        this.addStaticTeams();
        
    }
    captureFlags(flag){
        console.log(JSON.parse(flags));
        this.emit("syncFlags",this.flags)
    } 
    addStaticTeams(){
        let teamOrange = new Team("orange", 0);
        let teamGreen = new Team("green", 0);
        let teamNo = new Team("no_team", 0);
        this.teams.push(teamOrange);
        this.teams.push(teamGreen);
        this.teams.push(teamNo);
    }

    addPlayer(name,  socket){
        this.players.push({"name":name,"team":{"teamname":"no_team","score":"0"}});
        this.playersockets.push({"name":name,socket});
    }
    emit(notifier,data){
         
        for(let player of this.playersockets){
         
            player.socket.emit(notifier,data)
        }
    }

}
