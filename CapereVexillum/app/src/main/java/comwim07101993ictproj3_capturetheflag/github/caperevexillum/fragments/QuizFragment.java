package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.DbHandler;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {


    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */
    static final int CATEGORY = 1;
    static final String TAG = QuizFragment.class.getSimpleName();


    private View view;
    //lijst met buttons die getoond worden
    private List<Button> buttons;

    //variabele declareren (main)
    private TextView question;
    private Integer nQuestions;
    private Integer count;
    private Quiz questionAndAnswer;
    private GameActivity gameActivity;

    private List<Quiz> quizList;
    private Flag currentFlag;
    private String myTeam;
    //layout settings
    LinearLayout linearLayout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    private StateManager stateManager;
    private DbHandler db_handler;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */
    public void setTeam(String myTeam) {
        this.myTeam = myTeam;
    }

    public void setCurrentFlag(Flag currentFlag) {
        this.currentFlag = currentFlag;
    }

    public void addActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        myTeam = gameActivity.MY_TEAM;
        stateManager = gameActivity.getStateManager();

    }

    public void setup() {
        //locale waarde
        db_handler = new DbHandler();
        count = 0;
        buttons = new ArrayList<>();
        nQuestions = 3;
        linearLayout = view.findViewById(R.id.buttonsLayout);
        ;

        question = (TextView) view.findViewById(R.id.questionTextView);

        //Eerste question afhalen
        questionAndAnswer = db_handler.getVraagEnAntwoord(count);

        //buttons toevoegen aan layout
        createButtons();
    }

    //buttons dynamish aanmaken aan de hand van aantal antwoorden
    private void createButtons() {
        question.setText(questionAndAnswer.getVraag());
        linearLayout.removeAllViews();

        for (int i = 0; i < questionAndAnswer.getAantalAntwoorden() - 1; i++) {
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
    boolean checkAnswerQuestion(Button button) {

        //als de question juist is toon dan de volgende
        //of als alle vragen zijn geweest ga naar de functie capturedFlag
        //anders ga naar de functie einde quiz
        if (button.getText() == questionAndAnswer.getJuisteAntwoord()) {
            count++;
            if (nQuestions - 1 >= count) {
                questionAndAnswer = db_handler.getVraagEnAntwoord(count);

                createButtons();
            } else {
                //Quiz capture and cooldown

                capturedFlag();
            }

        } else {
            endQuiz();

            return false;
        }
        return true;
    }

    //zet variabele terug normaal en toont een melding dat het antwoord fout was
    //het toont een nieuwe question en zet de antwoorden erbij
    public void endQuiz() {
        count = 0;
        Toast.makeText(gameActivity.getApplicationContext(), "You failed to capture the flag", Toast.LENGTH_SHORT).show();


        //flag.team=
        currentFlag.CaptureAndCooldown(currentFlag.getTeam());
        stateManager.setSerializable(EStateManagerKey.CAPTURED_FLAG, currentFlag);
//        Flags flags = ((Flags) stateManager.getSerializable(EStateManagerKey.FLAGS));
//        flags.addFlag(currentFlag);
//        stateManager.setSerializable(EStateManagerKey.FLAGS, flags);

        gameActivity.showQuiz(false);
    }

    //geeft een melding dat de vragen juist waren en de vlag overgenomen is
    //zet de variabele terug tegoei
    public void capturedFlag() {
        Toast.makeText(gameActivity.getApplicationContext(), "You captured the flag", Toast.LENGTH_SHORT).show();
        //Flag flag = new Flag(currentBeacon);
        currentFlag.team = myTeam;
        //flag.team=
        currentFlag.CaptureAndCooldown(gameActivity.MY_TEAM);
        stateManager.setSerializable(EStateManagerKey.CAPTURED_FLAG, currentFlag);
        //Flags flags = ((Flags) stateManager.getSerializable(EStateManagerKey.FLAGS));
        //flags.addFlag(currentFlag);
        //stateManager.setSerializable(EStateManagerKey.FLAGS, flags);

        count = 0;
        gameActivity.showQuiz(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz, container, false);
        setup();
        return view;
    }

    /* ------------------------- OnClickListener ------------------------- */

    //kijkt of het antwoord juist is
    @Override
    public void onClick(View view) {

        checkAnswerQuestion((Button) view);

    }
}








