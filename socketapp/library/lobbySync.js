import Lobby from '../db/lobby';

export default{
    lobbies:[],
    getLobbey(id){
        return this.lobbies.filter((lobby)=> {return lobby.id==(id)});
    },
    createLobby(name,password,time){
        let lobby = new Lobby(this.lobbies.length, name, password, time, []);
        this.lobbies.push(lobby);
        console.log(lobby.teams);
        console.log("created lobby: " + lobby.name + " - " + lobby.password + " - " + lobby.time);
    },
    emmit(lobby,emmitkey,emmitvalue){
        let players = lobby.players;
        players.forEach(function(player) {
            player.socket.emmit(emmitkey,emmitvalue)
        }, this);
    },

    joinLobby(socket, lobbyName, lobbyPassword, playerName){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.name==lobbyName});
      if(lobby[0]!=undefined){
        lobby = lobby[0];
        let name = playerName;
        let team = lobby.teams[2];
        this.lobbies[lobby.id].addPlayer(playerName, team);
       
        console.log("player "+name+" joined lobby "+lobby.id+":"+lobby.name);
        console.log(this.lobbies[lobby.id].players);
      }
    },

    distributePlayers(lobbyID){
        
    }
}
