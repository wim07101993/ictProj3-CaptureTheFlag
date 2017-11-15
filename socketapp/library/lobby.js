
export default{
    lobbies=[],
    getLobbey(id){
        return lobbies.filter((lobby)=> {return lobby.id==(id)});
    },
    createLobby(settings,lobbies){

    },
    emmit(lobby,emmitkey,emmitvalue){
        let players = lobby.players;
        players.forEach(function(player) {
            player.socket.emmit(emmitkey,emmitvalue)
        }, this);
    },



}