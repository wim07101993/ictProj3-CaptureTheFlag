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

        console.log("player "+ name +" joined lobby "+ lobby.id +":"+ lobby.name);
        this.getPlayers(lobby.id, io)
      }
    },

    leaveLobby(lobbyID){

    },

    distributePlayers(lobbyID){
      console.log("start");
        let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (Lobby[0] != undefined) {
          for (var player in Lobby[0].players) {
            if (player.team.teamName == "orange") {
              playersOrange.push(player);
              console.log(playersOrange);
            }
            else if (player.team.teamName == "green") {
              playersGreen.push(player);
              console.log(playersGreen);
            }
            else if (player.team.teamName == "no_team") {
              playersNoTeam.push(player);
              console.log(playersNoTeam);
            }
          }
          let index = 0;
          while(playersNoTeam.length > 0) {
            if (teamOrange.length <= teamGreen.length) {
              playersOrange.push(playersNoTeam[index]);
              for(var player in Lobby[0].players) {
                if (player.playerName == playersNoTeam[index].playerName) {
                  player.team.teamName = "orange";
                }
              }
              index++;
            }
          }
        }
    },

    getPlayers(lobbyID, io){
        let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});

        if(lobby[0] != undefined){
            io.emit("getPlayersResult", JSON.stringify(lobby[0].players));
        }
    },

    joinTeam(lobbyID, team){

    },

    startTime(lobbyID){

    }
}
