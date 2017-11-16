
import Lobby from '../db/lobby';

export default{
    lobbies=[],
    getLobbey(id){
        return lobbies.filter((lobby)=> {return lobby.id==(id)});
    },
    createLobby(name,password,time){
        let lobby = new Lobby(this.lobbies.length, name, password, time, []);
        this.lobbies.push(lobby);
    },
    emmit(lobby,emmitkey,emmitvalue){
        let players = lobby.players;
        players.forEach(function(player) {
            player.socket.emmit(emmitkey,emmitvalue)
        }, this);
    },

    joinLobby(lobbyName, lobbyPassword, playerName){
      let lobby = this.lobbies.filter((lobby)=>{return lobby.name==lobbyName});
      if(lobby[0]!=undefined){
        lobby = lobby[0];
        let name = playerName;
        let team = null;
        lobbies[lobby.id].players.push({"name":name,"team":team,"socket":socket});
        console.log("player "+name+" joined lobby "+lobby.id+":"+lobby.name);
      }
}
