package comwim07101993ictproj3_capturetheflag.github.caperevexillum.activities;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.R;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.HardwareInfo;

public class CreateLobbyActivity extends AppCompatActivity {
    String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);
        List<NetworkInterface> all = null;
        String macAdres="";
        macAdres = HardwareInfo.getMac();



    }
    public View.OnClickListener createLobbey()= new View.OnClickListener(){
        
    }
}
