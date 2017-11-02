import Flag from "../db/flags"
export default {
    flags:[],
    askFlags: function(socket){
        socket.emit("answerFlags",JSON.stringify(this.flags));
    },
    addFlag: function(io, socket, flag){
        let inputFlag = JSON.parse(flag);
        this.flags.push(new Flag(inputFlag.beaconMAC , inputFlag.team , inputFlag.cooldownTime));
        io.emit("addedFlag",JSON.stringify(this.flags));
    },
    updateFlag: function(io, socket, flag){
        let inputFlag = JSON.parse(flag);
        index = flags.findIndex(flag => flag.beaconMAC == inputFlag.beaconMAC);
        flags[index] = new Flag(inputFlag.beaconMAC , inputFlag.team , inputFlag.cooldownTime);
        io.emit("updatedFlag",JSON.stringify(this.flags));
    },
}
