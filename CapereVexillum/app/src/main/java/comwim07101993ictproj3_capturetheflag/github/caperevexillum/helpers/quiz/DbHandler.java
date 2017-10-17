package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Sanli on 17/10/2017.
 */

public class DbHandler {
    //Array van Vraag en Antwoorden
    private Vector<Quiz> vraagEnAntwoorden = new Vector<Quiz>();

    public DbHandler(){

        //toevoegen vragen en antwoorden van de vraag
        String vraagString = "sven is beest";

        Quiz vraag = new Quiz(vraagString,new ArrayList<String>(){{
            add("fout1");
            add("fout2");
            add("fout3");
        }},"juist");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("test123",new ArrayList<String>(){{
            add("fout4");
            add("fout5");
            add("fout6");
            add("fout8");
            add("fout7");
        }},"juist");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("Van welk land komt Hakan?",new ArrayList<String>(){{
            add("Kazachstan");
            add("Rusland");
        }},"Turkije");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("Waarom is michiel ros?",new ArrayList<String>(){{
            add("Van papa gekregen");
        }},"Van mama gekregen");
        vraagEnAntwoorden.add(vraag);
        vraag = new Quiz(vraagString,new ArrayList<String>(){{
            add("fout4");
            add("fout5");
            add("fout6");
            add("fout8");
            add("fout7");
        }},"juist");
        vraagEnAntwoorden.add(vraag);
    }

    //geef de vraag en antwoorden terug op een bepaalde plaats
    public Quiz getVraagEnAntwoord(Integer index) {
        return vraagEnAntwoorden.get(index);
    }
}
