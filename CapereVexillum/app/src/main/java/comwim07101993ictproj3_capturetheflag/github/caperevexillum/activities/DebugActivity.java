package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;

public class DebugActivity extends AppCompatActivity {
    private RelativeLayout quizLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        quizLayout = (RelativeLayout) findViewById(R.id.quizLayout2);
        quizLayout.setVisibility(View.VISIBLE);

    }
}
