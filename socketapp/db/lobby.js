export default class Lobby {
    id = "";
    name = "";
    password = "";
    time = "";
    players = [];

    constructor(id, name, password, time, players){
        this.id = id;
        this.name = name;
        this.password = password;
        this.time = time;
        this.players = players;
    }

}
