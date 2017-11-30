
import timeClass from "./library/timesync"
import flagsClass from "./library/flagsSync"
import lobbyClass from "./library/lobbySync"

export default class{
    getal=5;
    players=[];    
    lobbies=[];
    //server variable gets filled in by te bootstrapper => io
    server;
    
    //void
    OnInit(){
        console.log("starting up server");
        this.server.on("connection",(socket)=>this.OnSocketConnect(socket))
        
    }
    //void
    OnSocketConnect(socket){
        let parent=this;
        console.log("connection to the server");
        socket.on("createLobby",(playerName, lobbyName, password, time)=> lobbyClass.createLobby(this.server, socket, playerName, lobbyName, password, time,this.lobbies));
        socket.on("createLobbyNew",(settings)=>lobbyClass.createLobby(parent.server,socket,settings,this.lobbies));
        
        socket.on("joinLobby", (lobbyName, lobbyPassword, playerName) => lobbyClass.joinLobby(this.server, socket, lobbyName, lobbyPassword, playerName,this.lobbies));
        socket.on("leaveLobby", (lobbyId, playerName) => lobbyClass.leaveLobby(lobbyId, playerName, this.server,this.lobbies));
        socket.on("hostLeft", (lobbyID) => lobbyClass.hostLeft(this.server, lobbyID,this.lobbies));
        socket.on("joinTeam", (lobbyID, team, playername) => lobbyClass.joinTeam(lobbyID, team, playername, this.server,this.lobbies));
        socket.on("startLobby", (lobbyId) => {
          lobbyClass.distributePlayers(lobbyId, parent.server,parent.lobbies);
          lobbyClass.startTime(lobbyId, parent.server, socket,parent.lobbies);
          parent.AddLobbyListeners(socket);
        });
        socket.on("getPlayers", (lobbyId) => lobbyClass.getPlayers(lobbyId,socket,this.lobbies));
    }
    //void
    AddLobbyListeners(socket){
        //socket.on("timeSetup",(lobbyID, duration) => {lobbyClass.setupTime(lobbyId, duration)});
        //socket.on("syncTime",(timeLeft) => timeClass.syncTime(lobby,socket,timeLeft))
        //socket.on("askTime",() => timeClass.askTime(lobby,socket));
    
        // flags
        socket.on("askFlags",()=> flagsClass.askFlags(socket));
        socket.on("addFlag",(flag)=> flagsClass.addFlag(lobby, socket, flag));
        socket.on("updateFlag",(flag)=> flagsClass.updateFlag(lobby, socket, flag));
        socket.on("removeFlag",(flag)=> flagsClass.removeFlag(lobby, socket, flag));
    }

    //Object<lobby>
    getLobby(lobyIndex){
        return thislobbies[lobyIndex] ;
    }
}


