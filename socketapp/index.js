const server = require('http').createServer();

const io = require('socket.io')(server, {
  path: '/',
  serveClient: true,
  // below are engine.IO options
  pingInterval: 10000,
  pingTimeout: 5000,
  cookie: false
});

import timeClass from "./library/timesync"
import flagsClass from "./library/flagsSync"
import teamClass from "./library/teamSync"
let players=[];
var teams;
let lobbies = [];
io.on('connection', function(socket){
    console.log("connected");
    let hasLobby=true;
    let lobbyId=null;
    
    // time
    if(hasLobby==true){
    socket.on("start",(duration) => timeClass.start(io,socket,duration));
    socket.on("syncTime",(timeLeft) => timeClass.syncTime(io,socket,timeLeft))
    socket.on("askTime",() => timeClass.askTime(io,socket));

    // flags
    socket.on("askFlags",()=> flagsClass.askFlags(socket));
    socket.on("addFlag",(flag)=> flagsClass.addFlag(io, socket, flag));
    socket.on("updateFlag",(flag)=> flagsClass.updateFlag(io, socket, flag));
    socket.on("removeFlag",(flag)=> flagsClass.removeFlag(io, socket, flag));
    
    // teams
    teamClass.addStaticTeams();
    socket.on("askTeams",() => teamClass.askTeams(socket));
    socket.on("addPlayer", (teamName, player) => teamClass.addPlayer(io, teamName, player));
    socket.on("addTeam", (team) => teamClass.addTeam(io, team));
    }
    // lobby
    socket.on("checkCredentials", (lobbyName, lobbyPassword, playerName) => {
      let lobby =lobbies.filter((lobby)=>{return lobby.name==lobbyName});
      if(lobby[0]!=undefined){
        lobby = lobby[0];
        let name=playerName;
        let team = null;
        lobbies[lobby.id].players.push({"name":name,"team":team,"socket":socket});
        console.log("player "+name+" joined lobby "+lobby.id+":"+lobby.name);
      }
    });
    socket.on("disconnectFromLobby", () => console.log("someone disconnected from lobby"));

    socket.on("createLobby",(id,name,password,time)=>{
      
      let lobby={"id":id,"name":name,"password":password,"time":time,"players":[]};
      
      lobbies[id]=lobby;
      console.log("*")
      console.log("*")
      console.log("*")
      console.log(JSON.stringify(lobbies))
    })

});


console.log("listening")
server.listen(4040);