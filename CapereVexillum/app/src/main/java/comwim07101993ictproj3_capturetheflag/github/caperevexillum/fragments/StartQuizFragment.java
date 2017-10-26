package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.StateManager;

import static comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums.StateManagerKey.QUIZ_STARTER;

public class StartQuizFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private Button YesButton;
    private Button NoButton;
    private GameActivity gameActivity;
    private StateManager stateManager;

    public StartQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameActivity = (GameActivity) getActivity();
        stateManager = gameActivity.getStateManager();

        YesButton = (Button) getView().findViewById(R.id.JaButton);
        NoButton = (Button) getView().findViewById(R.id.NeenButton);

        YesButton.setOnClickListener(this);
        NoButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_quiz, container, false);
    }

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

    @Override
    public void onClick(View view) {
        if (view == YesButton) {
            stateManager.set(QUIZ_STARTER, true);
        }
        else if (view == NoButton) {
            stateManager.set(QUIZ_STARTER, false);
        }
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
     */
    public interface OnFragmentInteractionListener {

    }
}
