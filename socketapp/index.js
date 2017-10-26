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
  
    io.on("start",(endTime) => time.sync(io,endTime));
    io.on("askFlags",()=> flags.sync(socket));
    io.on("askTeam",() => teams.sync(socket,teams))

});



console.log("listening")
server.listen(3000);