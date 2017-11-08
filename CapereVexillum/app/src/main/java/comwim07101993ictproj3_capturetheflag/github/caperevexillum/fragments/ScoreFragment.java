package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey;


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
    private StateManager stateManager;
    private View view;
    private GameActivity gameActivity;
    private Vector<Flag> flags;

    private int amountFlagsGreen;
    private int amountFlagsRed;
    private double scoreGreen;
    private double scoreRed;
    private double scoreBalance;

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

        setViewValues();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setScores(int redScore,int greenScore){
        scoreRed = (double)redScore+0.01;
        scoreGreen = (double)greenScore+0.01;
        amountFlagsRedView.setText(((int)scoreRed)+"");
        amountFlagsGreenView.setText(((int)scoreGreen)+"");


        scoreBalanceProgressbar.setMax((int)(scoreGreen+scoreRed));
        scoreBalanceProgressbar.setProgress((int)scoreRed);
    }
    private void setViewValues(){
        try {
            if (stateManager !=null){
               /*
               //todo statemanager include
                flags = ((Flags)stateManager.get(StateManagerKey.FLAGS)).getRegisteredFlags();

                for (Flag flag: flags) {

                    switch (flag.getTeam()){
                        case "red": amountFlagsRed++;
                            break;
                        case "green":  amountFlagsGreen++;
                            break;
                        default: break;
                    }

                }*/
            }else {
                scoreRed = 0.01;
                scoreGreen = 0.01;
                amountFlagsRed = 0;
                amountFlagsGreen = 0;



            }
            scoreBalance= scoreGreen/(scoreGreen+scoreRed)*100.0;

            amountFlagsRedView.setText(amountFlagsRed+"");
            amountFlagsGreenView.setText(amountFlagsGreen+"");

            scoreBalanceProgressbar.setProgress((int)scoreBalance);
        }catch (Exception ex){

        }



    }

}

