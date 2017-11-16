import Team from '../db/team';

export default {
    teams : [],

    askTeams(socket){
        socket.emit("syncTeam", JSON.stringify(this.teams));
    },

    addTeam(io, team){
        let inputTeam = JSON.parse(team);

        let teamsWithSameName = this.teams.filter(function(t){return t.teamName == inputTeam.teamName});

        if(teamsWithSameName.length == 0){
            this.teams.push(inputTeam);
            io.emit("syncTeam", JSON.stringify(this.teams));
        }
    },

    addPlayer(io, teamName, player){
        let selectedTeamIndex = this.teams.findIndex(function(t){ return t.teamName == teamName});

        if(selectedTeamIndex != -1){
            let inputTeam = this.teams[selectedTeamIndex];

            let team = new Team(inputTeam.teamName, inputTeam.score);

            team.addPlayer(player);
            io.emit("syncTeam", JSON.stringify(inputTeam));
        }
    },

    addStaticTeams(){
        let teamOrange = new Team("orange", 0, []);
        let teamGreen = new Team("green", 0, []);
        let teamNo = new Team("no_team", 0, []);
        this.teams.push(teamOrange);
        this.teams.push(teamGreen);
        this.teams.push(teamNo);
    },

    distributePlayers(lobbyPlayers){

    }
}
