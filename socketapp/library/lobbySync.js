import Lobby from '../db/lobby';

export default{
    lobbies:[],
    playersOrange:[],
    playersGreen:[],
    playersNoTeam:[],
    getLobbey(id){
        return this.lobbies.filter((lobby)=> {return lobby.id==(id)});
    },
    createLobby(name,password,time){
        let lobby = new Lobby(this.lobbies.length, name, password, time, []);
        this.lobbies.push(lobby);
        console.log(lobby.teams);
        console.log("created lobby: " + lobby.name + " - " + lobby.password + " - " + lobby.time);
    },

    joinLobby(io, lobbyName, lobbyPassword, playerName){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.name==lobbyName});
      if(lobby[0]!=undefined){
        lobby = lobby[0];
        let name = playerName;
        // use standard team "no_team"
        let team = lobby.teams[2];
        this.lobbies[lobby.id].addPlayer(playerName, team);
        this.getPlayers(lobby.id, io)
      }
    },

    leaveLobby(lobbyID){

    },

    distributePlayers(lobbyID, io){
        let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (lobby[0] != undefined) {
          for (var player of lobby[0].players) {
            if (player.team.teamname == "orange") {
              this.playersOrange.push(player);
            }
            else if (player.team.teamname == "green") {
              this.playersGreen.push(player);
            }
            else if (player.team.teamname == "no_team") {
              this.playersNoTeam.push(player);
            }
          }
          for (var index = 0; index < this.playersNoTeam.length; index++) {
            if (this.playersOrange.length <= this.playersGreen.length) {
              this.playersOrange.push(this.playersNoTeam[index]);
              for(var player of lobby[0].players) {
                if (player.name == this.playersNoTeam[index].name) {
                  player.team = lobby[0].teams[0];
                }
              }
            }else{
                this.playersGreen.push(this.playersNoTeam[index]);
                for(var player of lobby[0].players) {
                  if (player.name == this.playersNoTeam[index].name) {
                    player.team = lobby[0].teams[1];
                  }
                }
            }
          }
          this.getPlayers(lobbyID, io);
        }
    },

    getPlayers(lobbyID, io){
        let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});

        if(lobby[0] != undefined){
          console.log(lobby[0].players);
            io.emit("getPlayersResult", JSON.stringify(lobby[0].players));
        }
    },

    joinTeam(lobbyID, team, playername, io){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
      if (lobby != undefined) {
        let player = lobby.players.filter((player)=>{return player.name == playername});
        if (player != undefined) {
          let team = lobby.teams.filter((team)=>{return team.teamname == team});
          if (team != undefined) {
            player.team = team;
            this.getPlayers(lobbyID, io);
          }
        }
      }
    },

    startTime(lobbyID){

    },
}