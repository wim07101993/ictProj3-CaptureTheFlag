package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.List;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.DbHandler;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {


    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    //variabele van de klasse db_handler
    private DbHandler db_handler;
    private View view;
    //lijst met buttons die getoond worden
    private List<Button> buttons;

    //variabele declareren (main)
    private TextView vraag;
    private Integer aantalVragen;
    private Integer count;
    private Quiz vraagEnAntwoord;
    private GameActivity gameActivity;
    private Beacon currentBeacon;
    //layout settings
    LinearLayout linearLayout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    private StateManager stateManager;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public QuizFragment() {

    }


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public void setCurrentBeacon(Beacon beacon){
        currentBeacon = beacon;
    }

    public void addActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.stateManager = gameActivity.getStateManager();
    }

    public void setup(){
        //locale waarde
        db_handler = new DbHandler();
        count = 0;
        buttons = new ArrayList<>();
        aantalVragen = 3;
        linearLayout =  (LinearLayout) view.findViewById(R.id.buttonsLayout);;

        vraag = (TextView)  view.findViewById(R.id.vraagTextView);

        //Eerste vraag afhalen
        vraagEnAntwoord = db_handler.getVraagEnAntwoord(count);

        //buttons toevoegen aan layout
        createButtons();
    }

    //buttons dynamish aanmaken aan de hand van aantal antwoorden
    private void createButtons(){
        vraag.setText(vraagEnAntwoord.getVraag());
        linearLayout.removeAllViews();

        for(int i = 0; i<vraagEnAntwoord.getAantalAntwoorden()-1;i++ ){
            Button button = new Button(getActivity());
            button.setText(vraagEnAntwoord.getAntwoord(i));
            button.setTextSize(14);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setLayoutParams(params);
            //button.setSingleLine(true);
            //button.setHeight((int    )"wrap_content");
            button.setOnClickListener(this);

            buttons.add(button);
            linearLayout.addView(button);
        }

    }

    //kijkt of de antwoorden juist zijn en returnt true or false
    boolean checkAnswerQuestion(Button button){

        //als de vraag juist is toon dan de volgende
        //of als alle vragen zijn geweest ga naar de functie overnemenSucces
        //anders ga naar de functie einde quiz
        if(button.getText()== vraagEnAntwoord.getJuisteAntwoord()){
            count++;
            if (aantalVragen-1 >= count){
                vraagEnAntwoord = db_handler.getVraagEnAntwoord(count);

                createButtons();
            }
            else{
                //Quiz capture and cooldown

                overnemenSucces();
            }

        }else{
            eindeQuiz();

            return false;
        }
        return true;
    }

    //zet variabele terug normaal en toont een melding dat het antwoord fout was
    //het toont een nieuwe vraag en zet de antwoorden erbij
    public  void eindeQuiz(){
        count = 0;
        Toast.makeText(gameActivity.getApplicationContext(),"You failed to capture the flag", Toast.LENGTH_SHORT).show();
        Flag flag = new Flag(currentBeacon);
        flag.CaptureAndCooldown(Team.NO_TEAM);
        ((Flags)stateManager.get(StateManagerKey.FLAGS)).addFlag(flag);
        gameActivity.showQuestion(false);
    }

    //geeft een melding dat de vragen juist waren en de vlag overgenomen is
    //zet de variabele terug tegoei
    public void overnemenSucces(){
        Toast.makeText(gameActivity.getApplicationContext(),"You captured the flag", Toast.LENGTH_SHORT).show();
        Flag flag = new Flag(currentBeacon);
        flag.CaptureAndCooldown(gameActivity.getMyTeam());
        ((Flags)stateManager.get(StateManagerKey.FLAGS)).addFlag(flag);
        count=0;
        gameActivity.showQuestion(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz,container,false);
        setup();
        return view;
    }

    /* ------------------------- OnClickListener ------------------------- */

    //kijkt of het antwoord juist is
    @Override
    public void onClick(View view) {

        checkAnswerQuestion( (Button)view);

    }
}








