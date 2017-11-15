export default class Team {
    teamname = "";
    score = "";
    players = [];

    constructor(teamname, score, players){
        this.teamname = teamname;
        this.score = score;
        this.players = players;
    }

    addPlayer(player){
        this.players.push(player);
    }

}