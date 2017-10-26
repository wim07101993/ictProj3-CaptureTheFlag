import Team from '../db/teams';

export default {
    teams : [],

    askTeams(socket){
        this.staticTeam();
        socket.emit("teamsResponse", JSON.stringify(teams));
        console.log("askTeams");    
    },

    staticTeam(){
        this.teams.push(new Team("testTeam", 5, ["john","sam", "dirk"]));
    }
}