import Flag from "../db/flags"
export default {
    flags:[],
    askFlags: function(socket){
        socket.emit("response",JSON.stringify(this.flags));
    },
    updateFlags: function(io, socket, flag){
        let responseFlag = JSON.parse(flag);
        this.flags.push(new Flag(responseFlag.beaconMAC , responseFlag.team , responseFlag.cooldownTime));
        io.emit("response",JSON.stringify(this.flags));
    },
}
