import Team from '../db/team';


export default class Lobby {
    id = "";
    name = "";
    password = "";
    time = "";
    players = [];
    teams = [];

    constructor(id, name, password, time, players){
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = time;
        this.players = players;
        this.addStaticTeams();
    }

    addStaticTeams(){
        let teamOrange = new Team("orange", 0);
        let teamGreen = new Team("green", 0);
        let teamNo = new Team("no_team", 0);
        this.teams.push(teamOrange);
        this.teams.push(teamGreen);
        this.teams.push(teamNo);
    }

    addPlayer(name, team, socket){
        this.players.push({"name":name,"team":team, socket});
    }
    emit(notifier,data){
         
        for(let player of this.players){
         
            player.socket.emit(notifier,data)
        }
    }

}
