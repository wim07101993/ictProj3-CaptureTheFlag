export default {
    time:0,
    start: function(io,socket,time){
        
        this.time = time;
        
        io.emit("start",time+"");
        let parent = this;
        setInterval(function(){parent.calculateTime(parent,io,socket)},1000)
        
    },
    clientStart:function(socket){
        socket.emit("start",this.time+"");
    },
    syncTime:function(io,socket,parent){
        io.emit("reSyncTime",parent.time+"");

    },
    calculateTime(parent,io,socket){
        parent.time-=0.01;
        
       let seconds = Math.floor((((parent.time%1)+0.000001)*100))-40;
        if(seconds<=2 && seconds>0){
            console.log("minute over")
            parent.time = Math.floor(parent.time)-1;
            this.syncTime(io,socket,parent);
        }
    }
}