package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.gametimer;


import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

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
    public GameTimer(TextView textViewTime, int timeInMinutes){
        timer = new Timer();
        DateEndTime = Calendar.getInstance().getTime();
        DateEndTime.setMinutes(DateEndTime.getMinutes()+timeInMinutes);
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
