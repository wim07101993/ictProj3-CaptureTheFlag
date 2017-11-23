package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.Beacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService.DataService;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment2 extends Fragment implements View.OnClickListener {


    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */
    static final int CATEGORY = 1;
    static  final  String TAG = QuizFragment.class.getSimpleName();
    DataService dataService;

    private View view;
    //lijst met buttons die getoond worden
    private List<Button> buttons;

    //variabele declareren (main)
    private TextView question;
    private Integer nQuestions;
    private Integer count;
    private Quiz questionAndAnswer;
    private GameActivity gameActivity;
    private IBeacon currentBeacon;
    private List<Quiz> quizList;

    //layout settings
    LinearLayout linearLayout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    private StateManager stateManager;


    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public void setCurrentBeacon(IBeacon beacon){
        currentBeacon = beacon;
    }

    public void addActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;

        dataService=new DataService(gameActivity);
        setup();
        stateManager = gameActivity.getStateManager();

    }

    public void setup(){
        //locale waarde

        final Response.Listener  listener = new Response.Listener<List<Quiz>>() {
            @Override
            public void onResponse(List<Quiz> response) {
                quizList = response;
                //Eerste question afhalen
                count = 0;
                questionAndAnswer = quizList.get(count);
                //buttons toevoegen aan layout
                createButtons();
            }
        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        };
        //TODO Georges statemanager.GET(MY_FLAGS);

        buttons = new ArrayList<>();
        nQuestions = 3;
        linearLayout = view.findViewById(R.id.buttonsLayout);;

        question = (TextView) view.findViewById(R.id.questionTextView);
        dataService.getRandomQuestions(listener,errorListener,3,CATEGORY);


    }

    //buttons dynamish aanmaken aan de hand van aantal antwoorden
    private void createButtons(){
        question.setText(questionAndAnswer.getVraag());
        linearLayout.removeAllViews();

        for(int i = 0; i< questionAndAnswer.getAantalAntwoorden()-1; i++ ){
            Button button = new Button(getActivity());
            button.setText(questionAndAnswer.getAntwoord(i));
            button.setTextSize(14);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setLayoutParams(params);
            button.setOnClickListener(this);

            buttons.add(button);
            linearLayout.addView(button);
        }
    }

    //kijkt of de antwoorden juist zijn en returnt true or false
    boolean checkAnswerQuestion(Button button){

        //als de question juist is toon dan de volgende
        //of als alle vragen zijn geweest ga naar de functie capturedFlag
        //anders ga naar de functie einde quiz
        if(button.getText()== questionAndAnswer.getJuisteAntwoord()){
            count++;
            if (nQuestions -1 >= count){
                questionAndAnswer = quizList.get(count);

                createButtons();
            }
            else{
                //Quiz capture and cooldown

                capturedFlag();
            }

        }else{
            endQuiz();

            return false;
        }
        return true;
    }

    //zet variabele terug normaal en toont een melding dat het antwoord fout was
    //het toont een nieuwe question en zet de antwoorden erbij
    public  void endQuiz(){
        count = 0;
        Toast.makeText(gameActivity.getApplicationContext(),"You failed to capture the flag", Toast.LENGTH_SHORT).show();
        Flag flag = new Flag(currentBeacon);
        flag.CaptureAndCooldown(Team.NO_TEAM);

        //((Flags)stateManager.get(StateManagerKey.FLAGS)).addFlag(flag);
        gameActivity.showQuiz(false);
    }

    //geeft een melding dat de vragen juist waren en de vlag overgenomen is
    //zet de variabele terug tegoei
    public void capturedFlag(){
        Toast.makeText(gameActivity.getApplicationContext(),"You captured the flag", Toast.LENGTH_SHORT).show();
        Flag flag = new Flag(currentBeacon);
        flag.CaptureAndCooldown(gameActivity.MY_TEAM);
        ((Flags)stateManager.get(StateManagerKey.FLAGS)).addFlag(flag);

        count=0;
        gameActivity.showQuiz(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz,container,false);
        //setup();

        return view;
    }

    /* ------------------------- OnClickListener ------------------------- */

    //kijkt of het antwoord juist is
    @Override
    public void onClick(View view) {

        checkAnswerQuestion( (Button)view);

    }
}








