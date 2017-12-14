package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.quiz.Quiz;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Beacon.IBeacon;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.dataService.DataServiceApi;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.EStateManagerKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineQuizFragment extends Fragment implements View.OnClickListener {


    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */
    static final int CATEGORY = 1;
    static final String TAG = OnlineQuizFragment.class.getSimpleName();
    DataServiceApi dataService;

    private View view;
    private List<Button> buttons;
    private Flag currentFlag;
    private String myTeam;
    private TextView question;
    private Integer nQuestions;
    private Integer count;
    private Quiz questionAndAnswer;
    private GameActivity gameActivity;
    private IBeacon currentBeacon;
    private List<Quiz> quiz;

    //layout settings
    LinearLayout linearLayout;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    private IStateManager stateManager;

    public void setTeam(String myTeam) {
        this.myTeam = myTeam;
    }

    public void setCurrentFlag(Flag currentFlag) {
        this.currentFlag = currentFlag;
    }

    final Response.Listener listener = new Response.Listener<List<Quiz>>() {
        @Override
        public void onResponse(List<Quiz> response) {
            quiz = response;
            Collections.shuffle(quiz);
            count = 0;
            questionAndAnswer = quiz.get(count);
            createButtons();
        }
    };
    final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, error + "");
            Toast.makeText(gameActivity, "Can't fetch questions", Toast.LENGTH_SHORT).show();
            gameActivity.showQuiz(false);
        }
    };

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public void setCurrentBeacon(IBeacon beacon) {
        currentBeacon = beacon;
    }

    public void addActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;

        dataService = new DataServiceApi(gameActivity);
        stateManager = gameActivity.getGameController();
        setup();

    }

    public void getQuestions() {
        dataService.getRandomQuestions(listener, errorListener, nQuestions, CATEGORY);
    }

    public void setup() {
        buttons = new ArrayList<>();
        nQuestions = 3;
        //todo nQuestions = equal to amount flags team has in statemanager
        linearLayout = view.findViewById(R.id.buttonsLayout);
        ;
        question = (TextView) view.findViewById(R.id.questionTextView);
        //dataService.getRandomQuestions(listener, errorListener, nQuestions, CATEGORY);
    }

    //buttons dynamish aanmaken aan de hand van aantal antwoorden
    private void createButtons() {
        question.setText(questionAndAnswer.getQuestion());
        linearLayout.removeAllViews();

        for (int i = 0; i < questionAndAnswer.getAnswers().size(); i++) {
            Button button = new Button(getActivity());
            button.setText(questionAndAnswer.getAnswer(i).getAnswer());
            button.setTextSize(14);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setLayoutParams(params);
            button.setOnClickListener(this);
            Log.d("questionAndAnswer", questionAndAnswer.getAnswer(i).getAnswer());
            buttons.add(button);
            linearLayout.addView(button);
        }
    }

    //kijkt of de antwoorden juist zijn en returnt true or false
    boolean checkAnswerQuestion(Button button) {
        //als de question juist is toon dan de volgende
        //of als alle vragen zijn geweest ga naar de functie capturedFlag
        //anders ga naar de functie einde quiz
        if (questionAndAnswer.getAnswer(button.getId()).isAnswerCorrect().equals("1")) {
            Log.d("questionAndAnswer", questionAndAnswer.getAnswer(button.getId()).isAnswerCorrect());
            count++;
            if ((nQuestions - 1) >= count) {
                questionAndAnswer = quiz.get(count);
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

//    //zet variabele terug normaal en toont een melding dat het antwoord fout was
//    //het toont een nieuwe question en zet de antwoorden erbij

    public void endQuiz() {
        count = 0;
        Toast.makeText(gameActivity.getApplicationContext(), "You failed to capture the flag", Toast.LENGTH_SHORT).show();

        currentFlag.setCooldownTime();

        gameActivity.setCurrentFlag(currentFlag);
        gameActivity.showQuiz(false);
    }

    //geeft een melding dat de vragen juist waren en de vlag overgenomen is
    //zet de variabele terug tegoei
    public void capturedFlag() {
        Toast.makeText(gameActivity.getApplicationContext(), "You captured the flag", Toast.LENGTH_SHORT).show();
        currentFlag.team = myTeam;

        currentFlag.CaptureAndCooldown(gameActivity.MY_TEAM);
        stateManager.setSerializable(EStateManagerKey.CAPTURED_FLAG, currentFlag);

        count = 0;
        gameActivity.showQuiz(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_online_quiz, container, false);
        dataService = new DataServiceApi(getActivity());

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