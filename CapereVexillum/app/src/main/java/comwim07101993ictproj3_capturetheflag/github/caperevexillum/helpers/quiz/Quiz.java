package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sanli on 17/10/2017.
 */
@Deprecated
public class Quiz {
    private String vraag;
    private String juisteAntwoord;

    //lijst van arrays maken
    private ArrayList<String> fouteAntwoorden;
    private ArrayList<String> totaalAntwoorden;

    //stelt de vraag en antwoorden in
    public Quiz(String iniVraag, ArrayList<String> iniFouteAntwoorden, String iniJuisteAntwoord){

        this.vraag = iniVraag;
        this.fouteAntwoorden = iniFouteAntwoorden;
        this.juisteAntwoord = iniJuisteAntwoord;

        totaalAntwoorden= fouteAntwoorden;
        totaalAntwoorden.add(juisteAntwoord);
    }


    // geeft de vraag terug
    public String getVraag() {
        return vraag;
    }

    //geeft een lijst met foute antwoorden terug
    public List<String> getFouteAntwoorden() {
        return fouteAntwoorden;
    }

    //geeft het juiste antwoord terug
    public String getJuisteAntwoord() {
        return juisteAntwoord;
    }

    //geeft aan hoeveel antwoorden er zijn
    public Integer getAantalAntwoorden(){
        return fouteAntwoorden.size() + 1;
    }

    //geeft een lijst van alle antwoorden terug
    //in onbepaalde volgorde
    public List<String> getTotaalAntwoorden(){
        Collections.shuffle(totaalAntwoorden);
        return  totaalAntwoorden;
    }

    //geeft van een bepaalde locatie terug hoeveel antwoorden er zijn
    public String getAntwoord(int index){
        return  totaalAntwoorden.get(index);
    }
}
