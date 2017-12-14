import Index from "./index";
const server = require('http').createServer();

const io = require('socket.io')(server, {
  path: '/',
  serveClient: true,
  // below are engine.IO options
  pingInterval: 10000,
  pingTimeout: 5000,
  cookie: false
});

var index= new Index();
index.server=io;
index.OnInit();



let port = 4040;
console.log("listening on port:"+port);
server.listen(port);