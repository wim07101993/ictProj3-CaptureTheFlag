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

io.on('connection', function(socket){
    console.log("connected");
  
    if (players.length ==0){
      console.log("host connected")
      players.push(socket);
      socket.emit("host","");
    }else{
      console.log("client connected")
      flagsClass.askFlags(socket);
      timeClass.clientStart(socket);
    }
    
  
    socket.on("start",(duration) => timeClass.start(io,socket,duration));
    socket.on("syncTime",(timeLeft) => timeClass.syncTime(io,socket,timeLeft))
    socket.on("askTime",() => timeClass.askTime(io,socket));

    socket.on("askFlags",()=> flagsClass.askFlags(socket));
    socket.on("addFlag",(flag)=> flagsClass.addFlag(io, socket, flag));
    socket.on("updateFlag",(flag)=> flagsClass.updateFlag(io, socket, flag));
    socket.on("removeFlag",(flag)=> flagsClass.removeFlag(io, socket, flag));
    teamClass.addStaticTeams();
    socket.on("askTeams",() => teamClass.askTeams(socket));
    socket.on("addPlayer", (teamName, player) => teamClass.addPlayer(io, teamName, player));
    socket.on("addTeam", (team) => teamClass.addTeam(io, team));
});



console.log("listening")
server.listen(4040);