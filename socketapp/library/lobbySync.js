import Lobby from '../db/lobby';
import timeClass from './timeSync';
import Team from '../db/team';
//const JSON = require('circular-json');
export default{
    
    playersOrange:[],
    playersGreen:[],
    playersNoTeam:[],
    
    getLobbey(id){
      try {
        
        return this.lobbies.filter((lobby)=> {return lobby.id==(id)});
      } catch (error) {
        
      }
    },
    createLobby(io, socket,incomingSettings,lobbies){
      try {
        let settings = JSON.parse(incomingSettings);
        let playerName = settings["hostName"];
        let lobbyName = settings["name"];
        let password = settings["password"];
        let time = settings["totalGameTime"];
        let lobbyFilter = lobbies.filter((lobby) => {return lobby.name == lobbyName});
        console.log("creating lobby:"+lobbyName)
        if(lobbyFilter[0] != undefined){
          console.log("failed creating:"+lobbyName)
          settings["name"]=null;
          socket.emit("wasLobbyCreated", JSON.stringify(settings) );
          
        }else{
          let lobby = new Lobby(lobbies.length, lobbyName, password, time, []);
          lobbies.push(lobby);
          console.log("created lobby: " + lobby.name + " - " + lobby.password + " - " + lobby.time);
          this.joinLobby(io, socket, settings,lobbies);
        }
      } catch (error) {
        
      }
    },

    joinLobby(io, socket, settings,lobbies){
      try {
        let playerName = settings["hostName"];
        let lobbyName = settings["name"];
        let lobbyPassword = settings["password"];
        
        let lobby = lobbies.filter((lobby)=>{
          
          return (lobby.name==lobbyName && lobby.password == lobbyPassword)});
        let resultLobby = lobby[0];
        
        
        if(resultLobby != undefined){
          let playerFilter = resultLobby.players.filter((player)=>{return player.name == playerName});
          let resultPlayer = playerFilter[0];
  
          if(resultPlayer != undefined){
            settings["id"]=resultLobby.id;
            settings["hostName"] = null;
            settings["totalGameTime"]=resultLobby.time;
            resultLobby.addPlayer(playerName,socket);
            socket.emit("wasLobbyCreated", JSON.stringify(settings) );
          }else{
            settings["id"]=resultLobby.id;
            resultLobby.addPlayer(playerName,socket);
            socket.emit("wasLobbyCreated", JSON.stringify(settings) );
            this.getPlayers(resultLobby.id, io,lobbies);
            //set flag capture listener 
            socket.on("captureFlag",(flag)=>resultLobby.captureFlags(flag));
          }
  
        }else{
          socket.emit("lobbyNotFound");
          console.log("lobbyNotFound");
        }
      } catch (error) {
        
      }
    },

    leaveLobby(lobbyId, playerName, io,lobbies){
      try {
        
        let lobby = lobbies[lobbyId];
  
        if(lobby != undefined){
          let playerIndex = lobby.players.findIndex(player => player.name == playerName);
        
          if(playerIndex > -1){
            lobby.players.splice(playerIndex, 1);
            this.getPlayers(lobbyId, io,lobbies);
          }
        }
      } catch (error) {
        
      }
    },

    distributePlayers(lobbyID, io,lobbies){
      try {
        
        let lobby = lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (lobby[0] != undefined) {
          for (var player of lobby[0].players) {
            if (player.team.teamName == "orange") {
              this.playersOrange.push(player);
            }
            else if (player.team.teamName == "green") {
              this.playersGreen.push(player);
            }
            else if (player.team.teamName == "no_team") {
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
      } catch (error) {
        
      }
    },

    getPlayers(lobbyID, io,lobbies){
      try {
        
        let lobby = lobbies[lobbyID];
        
        if(lobby != undefined){ 
          console.log("playerssycnd")
          lobby.emit("players", JSON.stringify(lobby.players));
        }
      } catch (error) {
        console.log("error in lobbysync => getPlayers()")
      }
     
    },

    joinTeam(lobbyID, team, playername, io,lobbies){
      try { 
        console.log("joining team")
        
        let lobby = lobbies[lobbyID];
        console.log(playername);
        let playerFilter = lobby.players.filter((player) => {return player.name == playername});
        
        if(playerFilter[0] != undefined){
          let player = playerFilter[0];
  
          let teamFilter = lobby.teams.filter((t) => {return t.teamName == team});
  
          if(teamFilter[0] != undefined){
            let team = teamFilter[0];
            let teamJson= JSON.stringify(team);
  
            player.team = team;
            console.log(player.name + " joined team " + player.team.teamName);
            this.getPlayers(lobbyID, io,lobbies);
          }
        }
      } catch (error) {
        
      }
    },

    setupTime(lobbyID, duration){
      try {
        
        let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (lobby != undefined) {
          lobby[0].time = duration;
        }
      } catch (error) {
        
      }
    },

    startTime(lobbyID, io, socket,lobbies){
      try {
        let lobby = lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (lobby != undefined) {
          console.log(io);
          timeClass.timeStart(lobby[0], socket, lobby[0].time,lobby.timeInterval);

          lobby[0].startScore();
          lobby[0].emit("startGame",JSON.stringify( lobby[0].players));
        }
        
      } catch (error) {
        
      }
    },

    hostLeft(io, lobbyID,lobbies){
      try {
        let lobby = lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (lobby != undefined) {
          lobby=lobby[0];

          console.log("leave lobby");
          lobby.emit("leaveLobby","");
          lobby.deleteLobby();
        }
        
      } catch (error) {
        console.log("error in hostleft");
      }
      
    },

    restart(lobbyID){
      let lobby = lobbies.filter((lobby)=>{return lobby.id == lobbyID});
      if (lobby != undefined) {
        lobby = lobby[0];
        lobby.player.forEach(player => {
          lobby.player.team = Team[2];
        });
        lobby.flag = [];
        lobby.team[0].score = 0;
        lobby.team[1].score = 0;
      }
  }
}