package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;

public class EndActivity extends AppCompatActivity{

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */
    TextView winOrLoseLabel;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        winOrLoseLabel = (TextView) findViewById(R.id.winOrLoseLabel);

        Bundle extras = getIntent().getExtras();
        String winOrLose = extras.getString("winnertext");
        winOrLoseLabel.setText(winOrLose);
    }
}
