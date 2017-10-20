module.exports = {
    sync : function(socket, teams){
        var showcaseFunction = this.showcaseFunction();
        console.log("teamsync");
        socket.emit(showcaseFunction , teams);
    },
    showcaseFunction: function(){
        return "showcaseFunction"
    }
}