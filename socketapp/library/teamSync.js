import Team from '../db/teams';

export default {
    teams : [],

    askTeams(socket){
        socket.emit("answerTeams", JSON.stringify(this.teams));
    },

    addTeam(io, team){
        let inputTeam = JSON.parse(team);

        let teamsWithSameName = this.teams.filter(function(t){return t.teamName == inputTeam.teamName});

        if(teamsWithSameName == null){
            this.teams.push(inputTeam);
            io.emit("addedTeam", JSON.stringify(this.teams));
        }
    },

    addPlayer(io, team, player){
        let inputTeam = JSON.parse(team);
        let selectedTeamIndex = this.teams.findIndex(t => t.teamName == inputTeam.teamName);
        
        if(selectedTeamIndex != null){
            let teamToAddPlayerTo = this.teams[selectedTeamIndex];
            teamToAddPlayerTo.addPlayer(player);
            io.emit("addedPlayer", JSON.stringify(teamToAddPlayerTo));
        }
    },
    
    addStaticTeams(){
        let team = new Team("testTeam", 5, ["john","sam", "dirk"]);
        let team2 = new Team("testTeam2", 19, ["ronny","johnny", "ronald"]);
        this.teams.push(team);
        this.teams.push(team2);
    }
}