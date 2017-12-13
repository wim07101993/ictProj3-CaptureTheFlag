export default class {
    time=0;
    timeStart(io,socket,time,timeInterval){

        this.time = time;
        let parent = this;
        timeInterval= setInterval(function(){parent.calculateTime(parent,io,socket)},1000)

    }
    clientStart(socket){
        socket.emit("start",this.time+"");
    }
    syncTime(io,socket,parent){
        try {
            console.log("timesync:"+(parent.time-0.4)+"");    
            io.emit("reSyncTime",parent.time-0.4+"");    
        } catch (error) {
            console.log("resync error")
        }
        

    }
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
        if(seconds<0.01){
            io.emit("endScreen","");
        }
    }
}
