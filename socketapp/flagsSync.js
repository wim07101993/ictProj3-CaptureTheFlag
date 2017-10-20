module.exports = {
    sync: function(socket, flags){
        var test = this.showcaseFunction();
        console.log("flagsyncy");
        socket.emit("updateFlags",JSON.stringify(flags));
        

    },
    showcaseFunction: function(){
        return "test"
    }

}
