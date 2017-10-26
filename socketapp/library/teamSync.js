import Team from '../db/teams';

export default {
    teams : [],

    askTeams(socket){
        socket.emit("response", JSON.stringify(this.teams));
        console.log("askTeams");    
    },

    addTeam(team){

    },

    addPlayer(team, player){

    },
    
    staticTeam(){
        let team = new Team("testTeam", 5, ["john","sam", "dirk"]);
        this.teams.push(team);
    }
}