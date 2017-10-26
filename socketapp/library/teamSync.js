import Team from '../db/teams';

export default {
    teams : [],

    askTeams(socket){
        this.staticTeam();
        socket.emit("response", JSON.stringify(this.teams));
        console.log("askTeams");    
    },

    staticTeam(){
        let team = new Team("testTeam", 5, ["john","sam", "dirk"]);
        this.teams.push(team);
    }
}