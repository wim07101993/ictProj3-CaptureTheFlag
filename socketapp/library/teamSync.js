import team from '../db/teams';

export default {
    teams = [],

    sync : function(socket){
        var showcaseFunction = this.showcaseFunction();
        console.log("teamsync");
        socket.emit(showcaseFunction);
    },
    
    showcaseFunction: function(){
        return "showcaseFunction"
    }
}