import flag from "../db/flags"
export default {
    flags:[],
    askFlags: function(socket){
        socket.emit("answerFlags",JSON.stringify(this.flags));
    },
    updateFlagss: function(io, socket, flag){
        let responeFlag = JSON.parse(flag);
        flags.push(new flag(flag.beaconMAC , flag.cooldownTime , flag.team));
        io.emit("answerFlags",JSON.stringify(this.flags));
    },
}
