package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment{

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */


    private TextView amountFlagsRedView;
    private TextView amountFlagsGreenView;
    private ProgressBar scoreBalanceProgressbar;
    private StateManager stateManager;
    private View view;
    private GameActivity gameActivity;

    private int amountFlagsGreen;
    private int amoountFlagsRed;
    private int scoreGreen;
    private int scoreRed;
    private int scoreBalance;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public ScoreFragment() {
        // Required empty public constructor
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    public void addActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score, container, false);
        amountFlagsRedView = (TextView) view.findViewById(R.id.aantalFlagsRood);
        amountFlagsGreenView = (TextView) view.findViewById(R.id.aantalFlagsGroen);
        scoreBalanceProgressbar = (ProgressBar) view.findViewById(R.id.scoreVerhoudingProgressBar);

        return view;
    }

    private void fillProgressBar(){
        scoreBalance= scoreGreen/(scoreGreen+scoreRed)*100;
        scoreBalanceProgressbar.setProgress(scoreBalance);

    }
    private void setAmountFlags(){
        amountFlagsRedView.setText(amoountFlagsRed);
        amountFlagsGreenView.setText(amountFlagsGreen);
    }



    public void dummyData(){
        amountFlagsRedView.setText("5");
        amountFlagsRedView.setText("7");

        scoreGreen = 300;
        scoreRed = 700;
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
