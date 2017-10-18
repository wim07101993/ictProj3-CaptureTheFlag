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
        String vraagString = "Waar is je hart spreekwoordelijk gemaakt als je geen gevoel hebt?";

        Quiz vraag = new Quiz(vraagString,new ArrayList<String>(){{
            add("goud");
            add("zilver");
            add("zand");
        }},"steen");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("Wat is de kleinste euromunt?",new ArrayList<String>(){{

            add("2 Cent");
            add("5 Cent");
            add("10 Cent");
            add("20 Cent");
        }},"1 Cent");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("Hoeveel dagen heeft de maand augustus?",new ArrayList<String>(){{

            add("30");
        }},"31");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("Welke vogel valt spreekwoordelijk van het dak wanneer het warm is?",new ArrayList<String>(){{
            add("Ekster");
            add("Specht");
        }},"Mus");
        vraagEnAntwoorden.add(vraag);
        vraag = new Quiz("Waar is iemand met claustrofobie bang voor?",new ArrayList<String>(){{
            add("Kleine ruimtes");

        }},"Sinterklaas");
        vraagEnAntwoorden.add(vraag);

        vraag = new Quiz("Met hoeveel pionnen begint iedere speler bij het bordspel “mens erger je niet”?",new ArrayList<String>(){{
            add("2");
            add("6");
            add("1");

        }},"4");
        vraagEnAntwoorden.add(vraag);
        vraag = new Quiz("Welk automerk bracht de automodellen Golf, Polo en Passat uit?",new ArrayList<String>(){{
            add("Ford");
            add("BMW");

        }},"Volkswagen");
        vraagEnAntwoorden.add(vraag);
    }


    //geef de vraag en antwoorden terug op een bepaalde plaats
    public Quiz getVraagEnAntwoord(Integer index) {
        return vraagEnAntwoorden.get(index);
    }
}
