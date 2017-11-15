package comwim07101993ictproj3_capturetheflag.github.caperevexillum.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;



import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities.GameActivity;



public class CooldownTimerFragment extends Fragment  {


    //CircleProgressbar progress;
    ProgressBar progress;
    View view;
    GameActivity gameActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cooldown_timer,container,false);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = (GameActivity) getActivity();
        gameActivity.cooldownUpdatable = this;


        gameActivity.cooldownUpdatable=this;

       // progress.setProgressWithAnimation(50);
    }



    public void update(float progressValue) {


        progress.setProgress((int)progressValue);
    }
}
