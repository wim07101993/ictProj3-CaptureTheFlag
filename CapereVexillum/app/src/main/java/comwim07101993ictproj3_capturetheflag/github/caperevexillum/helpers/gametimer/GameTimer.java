package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer;


import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sanli on 12/10/2017.
 */

public class GameTimer {
    Timer timer;

    TimerTask UpdateTimer;


    private Date DateEndTime;
    private Date dateCcurrentTime;
    private long longTimeOver;
    private Date dateTimeOver;
    private String stringTimeOver;
    TextView textViewTime;
    OnGameTimerFinishedListener onGameTimerFinishedListener;
    /***
     *
     * @param textViewTime Give a textview that wich will have the timer as text
     * @param timeInMinutes Give the amount of time for the countdown
     */
    Emitter.Listener syncTime = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String time = (String) args[0];
            float timer = Float.parseFloat(time);
            int timeInMinutes = (int)timer;
            Calendar endDownCal = Calendar.getInstance();
            int seconds =Math.abs  ((int)(timeInMinutes*100-timer*100));
            endDownCal.add(Calendar.SECOND,seconds);
            endDownCal.add(Calendar.MINUTE,timeInMinutes);
            DateEndTime = endDownCal.getTime();
        }
    };
    public GameTimer(TextView textViewTime, float timeInMinutes, Socket socket){
        timer = new Timer();
        socket.on("reSyncTime" , syncTime);
        Calendar endDownCal = Calendar.getInstance();
        int seconds = (int)(timeInMinutes- (int)timeInMinutes)*100;
        endDownCal.add(Calendar.SECOND,seconds);
        endDownCal.add(Calendar.MINUTE,(int)timeInMinutes);


        DateEndTime = endDownCal.getTime();



        dateCcurrentTime = Calendar.getInstance().getTime();
        this.textViewTime = textViewTime;

        UpdateTimer = new TimerTask() {
            @Override
            public void run() {
                dateCcurrentTime = Calendar.getInstance().getTime();
                longTimeOver = DateEndTime.getTime() - dateCcurrentTime.getTime();
                dateTimeOver =new Date(longTimeOver);
                if(longTimeOver>0){
                    timerHandler.obtainMessage(1).sendToTarget();
                }else {
                    finishHandler.obtainMessage(1).sendToTarget();
                }

            }
        };
        timer.scheduleAtFixedRate(UpdateTimer,0,1000);

    }
    public void addListener(OnGameTimerFinishedListener onGameTimerFinishedListener){
        this.onGameTimerFinishedListener =onGameTimerFinishedListener;
    }
    Handler finishHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            onGameTimerFinishedListener.OnGameTimerFinished();
            timer.cancel();
            timer.purge();
        }
    };
    Handler timerHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            long minutes = TimeUnit.MILLISECONDS.toMinutes(longTimeOver);

            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
            if(minutes<60){
                sdf = new SimpleDateFormat("mm:ss");

            }
            String strDate = sdf.format(dateTimeOver);
            String tijdOver =strDate;

            textViewTime.setText(tijdOver);
        }
    };



}
