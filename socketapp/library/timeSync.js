export default {
    time:0,
    timeStart(io,socket,time,timeInterval){

        this.time = time;
        
        //io.emit("timeStart",time+"");
        let parent = this;
        timeInterval= setInterval(function(){parent.calculateTime(parent,io,socket)},1000)

    },
    clientStart:function(socket){
        socket.emit("start",this.time+"");
    },
    syncTime:function(io,socket,parent){
        try {
            console.log("resync complete")
            io.emit("reSyncTime",parent.time-0.4+"");    
        } catch (error) {
            console.log("resync error")
        }
        

    },
    calculateTime(parent,io,socket){
        parent.time-=0.01;

       let seconds = Math.floor((((parent.time%1)+0.000001)*100))-40;
       //sync every minute

       if((seconds<=1) && ( seconds>=0)){
      
            parent.time = Math.floor(parent.time)-0.015;
      
            return;
        }
        if((seconds%10)==0){

         parent.syncTime(io,socket,parent);
        }
    }
}
