export default {
    sync: function(io,socket,time){
        io.emit("start",time);
        
    }
}