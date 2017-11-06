import Team from '../db/team';

export default {
    teams : [],

    askTeams(socket){
        socket.emit("answerTeams", JSON.stringify(this.teams));
    },

    addTeam(io, team){
        let inputTeam = JSON.parse(team);

        let teamsWithSameName = this.teams.filter(function(t){return t.teamName == inputTeam.teamName});

        if(teamsWithSameName.length == 0){
            this.teams.push(inputTeam);
            io.emit("addedTeam", JSON.stringify(this.teams));
        }
    },

    addPlayer(io, teamName, player){
        let selectedTeamIndex = this.teams.findIndex(function(t){ return t.teamName == teamName});

        if(selectedTeamIndex != -1){
            let inputTeam = this.teams[selectedTeamIndex];
            
            let team = new Team(inputTeam.teamName, inputTeam.score, inputTeam.players);

            team.addPlayer(player);
            io.emit("addedPlayer", JSON.stringify(inputTeam));
        }
    },
    
    addStaticTeams(){
        let team = new Team("testTeam", 5, ["john","sam", "dirk"]);
        let team2 = new Team("testTeam2", 19, ["ronny","johnny", "ronald"]);
        this.teams.push(team);
        this.teams.push(team2);
    }
}