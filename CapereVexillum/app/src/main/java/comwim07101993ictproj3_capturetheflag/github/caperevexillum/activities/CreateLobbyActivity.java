package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.HardwareInfo;

public class CreateLobbyActivity extends AppCompatActivity  {
    String macAddress;
    Socket socket;
    int id=1;
    TextView name,password,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);
        name = (TextView)findViewById(R.id.lobbyNameTextview);
        password = (TextView)findViewById(R.id.lobbyPasswordTextView);
        time = (TextView)findViewById(R.id.lobbyTimeTextView);
        try{
            socket = IO.socket(GameActivity.SERVER_URL);
            socket.connect();
        }catch(URISyntaxException e){
            Log.d("createlobbyactivity", e.getMessage());
        }
        macAddress = HardwareInfo.getMac();
        Button createButton = (Button)findViewById(R.id.createLobby);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString=name.getText()+"";
                String passwordString=password.getText()+"";
                String timeString=time.getText()+"";

                socket.emit("createLobby",macAddress,nameString,passwordString,timeString);
            }
        });



    }


}
