package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;

public class StartActivity extends AppCompatActivity {
    Button joinButton;
    Button createButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        joinButton = (Button)findViewById(R.id.joinLobbyButton);
        createButton=(Button)findViewById(R.id.createLobbyButton);
        final StartActivity parent = this;
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, JoinLobbyActivity.class);
                startActivity(intent);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent, CreateLobbyActivity.class);
                startActivity(intent);
            }
        });

    }
}
