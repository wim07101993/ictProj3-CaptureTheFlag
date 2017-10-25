const server = require('http').createServer();

const io = require('socket.io')(server, {
  path: '/',
  serveClient: true,
  // below are engine.IO options
  pingInterval: 10000,
  pingTimeout: 5000,
  cookie: false
});
import timeClass from "./library/timesync";
import flagsClass from "./library/flagsSync";
import teamClass from "./library/teamSync";


var flags;
var teams;
var time;
io.on('connection', function(socket){
    console.log("connected");
    
    socket.emit("test","test");
    io.on("askTime",(socket) => time.sync(socket,time));
    io.on("askFlags",(socket)=> flags.sync(socket,flags));
    io.on("askTeam",(socket) => teams.sync(socket,teams))

});



console.log("listening")
server.listen(3000);