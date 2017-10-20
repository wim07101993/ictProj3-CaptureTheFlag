module.exports = {
    sync: function(socket, flags){
        var test = this.showcaseFunction();
        console.log("flagsyncy");
        socket.emit(test);
    },
    showcaseFunction: function(){
        return "test"
    }

}
