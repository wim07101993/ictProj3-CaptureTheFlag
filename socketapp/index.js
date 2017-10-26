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
    socket.on("addFlags",(flag)=> flagsClass.addFlags(io, socket, flag));
    socket.on("updateFlags",(flag)=> flagsClass.updateFlags(io, socket, flag));

    socket.on("askTeams",() => teamClass.askTeams(socket));
});



console.log("listening")
server.listen(4040);