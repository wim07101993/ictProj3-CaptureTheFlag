import Lobby from '../db/lobby';
import timeClass from './timeSync';
const JSON = require('circular-json');
export default{
    lobbies:[],
    playersOrange:[],
    playersGreen:[],
    playersNoTeam:[],
    parent:null,
    getLobbey(id){
        return this.lobbies.filter((lobby)=> {return lobby.id==(id)});
    },
    addParent(parent){
      this.parent=parent;
    },
    createLobby(io, socket, playerName, lobbyName , password, time){
      let lobbyFilter = this.lobbies.filter((lobby) => {return lobby.name == lobbyName});
      console.log("creating lobby:"+lobbyName)
      if(lobbyFilter[0] != undefined){
        console.log("failed creating:"+lobbyName)
        socket.emit("lobbyExists");
      }else{
        let lobby = new Lobby(this.lobbies.length, lobbyName, password, time, []);
        this.lobbies.push(lobby);
        console.log("created lobby: " + lobby.name + " - " + lobby.password + " - " + lobby.time);
        this.joinLobby(io, socket, lobbyName, password, playerName);
      }
    },

    joinLobby(io, socket, lobbyName, lobbyPassword, playerName){
      let lobby = this.lobbies.filter((lobby)=>{return (lobby.name==lobbyName && lobby.password == lobbyPassword)});
      let resultLobby = lobby[0];
      this.parent.lobby=resultLobby;
      if(resultLobby != undefined){
        let playerFilter = resultLobby.players.filter((player)=>{return player.name == playerName});
        let resultPlayer = playerFilter[0];

        if(resultPlayer != undefined){
          socket.emit("playerNameUnavailable");
          console.log("playerNameUnavailable");
        }else{
          resultLobby.addPlayer(playerName, resultLobby.teams[2],socket);
          socket.emit("getLobbyId", resultLobby.id);
          this.getPlayers(resultLobby.id, io);
        }
      }else{
        socket.emit("lobbyNotFound");
        console.log("lobbyNotFound");
      }
    },

    leaveLobby(lobbyId, playerName, io){
      let lobby = this.lobbies[lobbyId];

      if(lobby != undefined){
        let playerIndex = lobby.players.findIndex(player => player.name == playerName);
      
        if(playerIndex > -1){
          lobby.players.splice(playerIndex, 1);
          this.getPlayers(lobbyId, io);
        }
      }
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
        let lobby = this.lobbies[lobbyID];
        
        if(lobby != undefined){
          
          lobby.emit("getPlayersResult", JSON.stringify(lobby.players));
        }
    },

    joinTeam(lobbyID, team, playername, io){
      let lobby = this.lobbies[lobbyID];
      
      let playerFilter = lobby.players.filter((player) => {return player.name == playername});
      
      if(playerFilter[0] != undefined){
        let player = playerFilter[0];

        let teamFilter = lobby.teams.filter((t) => {return t.teamname == team});

        if(teamFilter[0] != undefined){
          let team = teamFilter[0];
          player.team = team;
          console.log(player.name + " joined team " + player.team.teamname);
          this.getPlayers(lobbyID, io);
        }
      }
    },

    setupTime(lobbyID, duration){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
      if (lobby != undefined) {
        lobby[0].time = duration;
      }
    },

    startTime(lobbyID, io, socket){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
      if (lobby != undefined) {
        console.log(io);
        timeClass.timeStart(io, socket, lobby[0].time);
      }
    },

    hostLeft(io, lobbyID){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
      if (lobby != undefined) {
        lobby=lobby[0];
        lobby.emit("leaveLobby","");
        this.lobbies.splice(lobbyID, 1);
      }
      
    }
}