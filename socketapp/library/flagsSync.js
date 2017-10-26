import Flag from "../db/flags"
export default {
    flags:[],
    askFlags: function(socket){
        socket.emit("answerFlags",JSON.stringify(this.flags));
    },
    addFlags: function(io, socket, flag){
        let responseFlag = JSON.parse(flag);
        this.flags.push(new Flag(responseFlag.beaconMAC , responseFlag.team , responseFlag.cooldownTime));
        io.emit("addedFlags",JSON.stringify(this.flags));
    },
}
