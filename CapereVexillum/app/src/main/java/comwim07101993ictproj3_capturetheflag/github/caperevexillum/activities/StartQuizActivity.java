package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;

public class StartQuizActivity extends AppCompatActivity{

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        fragment = (Fragment) getFragmentManager().findFragmentById(R.id.startQuizFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (fragment.isDetached()) {
            this.finishActivity(0);
        }
    }
}
