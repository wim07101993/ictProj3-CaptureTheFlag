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

    distributePlayers(lobbyID, io){
      console.log("start");
        let lobby = this.lobbies.filter((lobby)=>{return lobby.id == lobbyID});
        if (lobby[0] != undefined) {
          for (var player of lobby[0].players) {
            if (player.team.teamname == "orange") {
              this.playersOrange.push(player);
              console.log(this.playersOrange);
            }
            else if (player.team.teamname == "green") {
              this.playersGreen.push(player);
              console.log(this.playersGreen);
            }
            else if (player.team.teamname == "no_team") {
              this.playersNoTeam.push(player);
              console.log(this.playersNoTeam);
            }
          }
          let index = 0;
          while(index < this.playersNoTeam.length) {
              console.log(index);
              console.log("while(this.playersNoTeam.length > 0");
              console.log(this.playersOrange.length + " - " + this.playersGreen.length);
            if (this.playersOrange.length <= this.playersGreen.length) {
              this.playersOrange.push(this.playersNoTeam[index]);
              for(var player of lobby[0].players) {
                if (player.name == this.playersNoTeam[index].name) {
                  player.team.teamname = "orange";
                  console.log("team orange " + player);
                }
              }
              index++;
            }else{
                this.playersGreen.push(this.playersNoTeam[index]);
                for(var player of lobby[0].players) {
                  if (player.name == this.playersNoTeam[index].name) {
                    player.team.teamname = "green";
                    console.log("team green " + player);
                  }
                }
                index++;
            }
          }
          this.getPlayers(lobbyID, io);
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
