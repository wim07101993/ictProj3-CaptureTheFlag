export default {
    time:0,
    start: function(io,socket,time){

        this.time = time;
        
        io.emit("timeStart",time+"");
        let parent = this;
        setInterval(function(){parent.calculateTime(parent,io,socket)},1000)

    },
    clientStart:function(socket){
        socket.emit("start",this.time+"");
    },
    syncTime:function(io,socket,parent){
        io.emit("reSyncTime",parent.time-0.4+"");

    },
    calculateTime(parent,io,socket){
        parent.time-=0.01;

       let seconds = Math.floor((((parent.time%1)+0.000001)*100))-40;
       //sync every minute

       if((seconds<=1) && ( seconds>=0)){
        console.log("");
            console.log("time",parent.time);
            console.log("")
            parent.time = Math.floor(parent.time)-0.015;
            console.log("timeafter:", parent.time);
            return;
        }
        if((seconds%10)==0){

         parent.syncTime(io,socket,parent);
        }
    }
}
