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

var teams;
io.on('connection', function(socket){
    console.log("connected");
    
    socket.emit("test","test");
  
    socket.on("start",(endTime) => time.sync(io,endTime));

    socket.on("askFlags",()=> flagsClass.askFlags(socket));
    socket.on("addFlag",(flag)=> flagsClass.addFlag(io, socket, flag));
    socket.on("updateFlag",(flag)=> flagsClass.updateFlag(io, socket, flag));

    teamClass.staticTeam();
    teamClass.staticTeam();
    socket.on("askTeams",() => teamClass.askTeams(socket));
    socket.on("addPlayer", (team, player) => teamClass.addPlayer(team, player));
    socket.on("addTeam", (team) => teamClass.addTeam(team));
});



console.log("listening")
server.listen(4040);