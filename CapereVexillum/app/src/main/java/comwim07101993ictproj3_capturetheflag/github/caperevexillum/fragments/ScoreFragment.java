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


    private TextView aantalFlagsRoodView;
    private TextView aantalFlagsGroenView;
    private ProgressBar scoreVerhoudingProgressbar;
    private StateManager stateManager;
    private View view;
    private GameActivity gameActivity;

    private int aantalFlagsGroen;
    private int aantalFlagsRood;
    private int scoreGroen;
    private int scoreRood;
    private int scoreVerhouding;

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

        aantalFlagsRoodView = (TextView) view.findViewById(R.id.aantalFlagsRood);
        aantalFlagsGroenView = (TextView) view.findViewById(R.id.aantalFlagsGroen);
        scoreVerhoudingProgressbar = (ProgressBar) view.findViewById(R.id.scoreVerhoudingProgressBar);

        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    private void fillProgressBar(){
        scoreVerhouding= scoreGroen/(scoreGroen+scoreRood)*100;
        scoreVerhoudingProgressbar.setProgress(scoreVerhouding);

    }
    private void setAantalFlags(){
        aantalFlagsRoodView.setText(aantalFlagsRood);
        aantalFlagsGroenView.setText(aantalFlagsGroen);
    }



    public void dummyData(){
        aantalFlagsRoodView.setText("5");
        aantalFlagsGroenView.setText("7");

        scoreGroen = 300;
        scoreRood = 700;
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
