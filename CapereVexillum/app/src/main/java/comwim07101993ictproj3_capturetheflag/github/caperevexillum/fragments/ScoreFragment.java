package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */


    private TextView amountFlagsRedView;
    private TextView amountFlagsGreenView;
    private ProgressBar scoreBalanceProgressbar;
    private IStateManager stateManager;
    private View view;
    private GameActivity gameActivity;
    private Vector<Flag> flags;

    private int amountFlagsGreen;
    private int amountFlagsRed;
    private int flagGreen;
    private int flagRed;
    private double scoreGreen;
    private double scoreRed;

    private double scoreBalance;
    private Socket socket;

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        view = inflater.inflate(R.layout.fragment_score,container,false);

        gameActivity = (GameActivity) getActivity();

        amountFlagsRedView = (TextView) view.findViewById(R.id.aantalFlagsRood);
        amountFlagsGreenView = (TextView) view.findViewById(R.id.aantalFlagsGroen);
        scoreBalanceProgressbar = (ProgressBar) view.findViewById(R.id.scoreVerhoudingProgressBar);
        stateManager = gameActivity.getStateManager();
        stateManager.getSocketService().getSocket().on("syncTeamScore",syncScoreListenner);
        return view;
    }
    Emitter.Listener syncScoreListenner = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Gson gson = new Gson();
            Team[] teams = gson.fromJson((String)args[0], Team[].class) ;

            String red="1";
            String green="1";
            for(Team team : teams){
                if(team.getTeamName().equals("orange")){
                    red = team.getScore()+"";
                    if(red.equals("0"))
                        red ="1";
                }
                if(team.getTeamName().equals("green")){
                    green = team.getScore()+"";
                    if(green.equals("0"))
                        green="1";
                }
            }

            scoreRed = Double.parseDouble(red);
            scoreGreen = Double.parseDouble(green);
            setScoreHandler.obtainMessage(1).sendToTarget();
        }
    };


    Handler setScoreHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if((scoreGreen+scoreRed)!=0){


            scoreBalanceProgressbar.setMax((int)(scoreGreen + scoreRed));
            scoreBalanceProgressbar.setProgress((int) scoreRed);
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFlags(int redScore, int greenScore){

        this.flagGreen=greenScore;
        this.flagRed=redScore;
        setFlagHandler.obtainMessage(1).sendToTarget();


    }
    Handler setFlagHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            amountFlagsRedView.setText(flagRed+"");
            amountFlagsGreenView.setText(flagGreen+"");

        }
    };
}



