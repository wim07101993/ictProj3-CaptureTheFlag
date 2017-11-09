import Flag from "../db/flags"
export default {
    flags:[],
    askFlags: function(socket){
        socket.emit("syncFlags",JSON.stringify(this.flags));
    },
    addFlag: function(io, socket, flag){
        console.log("flag");
        console.log(flag);
        let inputFlag = (JSON.parse(flag));
        this.flags.push(new Flag(inputFlag.beaconMAC,inputFlag.cooldownTime,inputFlag.cooldownTimer,inputFlag.team,inputFlag.timerFixer));
        console.log("");
        console.log("flags");
        console.log(JSON.stringify(this.flags));
        io.emit("syncFlags",JSON.stringify(this.flags));
    },
    updateFlag: function(io, socket, flag){
        let inputFlag = JSON.parse(flag);
        index = flags.findIndex(flag => flag.beaconMAC == inputFlag.beaconMAC);
        flags[index] = new Flag(inputFlag.beaconMAC , inputFlag.team , inputFlag.cooldownTime);
        console.log("");
        console.log("flags");
        console.log(JSON.stringify(this.flags));
        io.emit("syncFlags",JSON.stringify(this.flags));
    },
    
    
}
