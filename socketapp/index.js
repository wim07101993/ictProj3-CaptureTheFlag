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
    let hasLobby=false;
    let lobbyId;
    
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
    socket.on("checkCredentials", (lobbyName, lobbyPassword) => console.log("checkCredentials req received: " + lobbyName + " - " + lobbyPassword));
    socket.on("disconnectFromLobby", () => console.log("someone disconnected from lobby"));

    socket.on("create",(id,name,password,time)=>{
      
      let lobby={"id":id,"name":name,"password":password,"time":time,"players":[]};
      lobbies[id]=lobby;
      JSON.stringify(lobby);
    })

});



console.log("listening")
server.listen(4040);