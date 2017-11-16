const server = require('http').createServer();

const io = require('socket.io')(server, {
  path: '/',
  serveClient: true,
  // below are engine.IO options
  pingInterval: 10000,
  pingTimeout: 5000,
  cookie: false
});

//imports
import timeClass from "./library/timesync"
import flagsClass from "./library/flagsSync"
import lobbyClass from "./library/lobbySync"

lobbyClass.createLobby("test", "test", 30);

io.on('connection', function(socket){
    console.log("connected");
    let hasLobby=true;
    let lobbyId=null;

    // time
    if(hasLobby==true){
    socket.on("timeStart",(duration) => timeClass.start(io,socket,duration));
    socket.on("syncTime",(timeLeft) => timeClass.syncTime(io,socket,timeLeft))
    socket.on("askTime",() => timeClass.askTime(io,socket));

    // flags
    socket.on("askFlags",()=> flagsClass.askFlags(socket));
    socket.on("addFlag",(flag)=> flagsClass.addFlag(io, socket, flag));
    socket.on("updateFlag",(flag)=> flagsClass.updateFlag(io, socket, flag));
    socket.on("removeFlag",(flag)=> flagsClass.removeFlag(io, socket, flag));
    }

    // lobby
    socket.on("createLobby",(name,password,time)=> lobbyClass.createLobby(name, password, time));
    socket.on("joinLobby", (lobbyName, lobbyPassword, playerName) => lobbyClass.joinLobby(io, lobbyName, lobbyPassword, playerName));
    socket.on("disconnectFromLobby", () => console.log("someone disconnected from lobby"));
    socket.on("startLobby", (lobbyId) => {
      //lobbyClass.distributePlayers(lobbies[lobbyId].players);
      // lobbyClass.timeStart(io, socket, duration);
    });
    socket.on("getPlayers", (lobbyId) => lobbyClass.getPlayers(lobbyId,socket));

});


console.log("listening")
server.listen(4040);
