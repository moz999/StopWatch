package com.example.m0z.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StopWatch extends Fragment implements
        Runnable, View.OnClickListener{

    private long startTime;

    private TextView timerText;
    private Button startButton;
    private TextView pastTime;
    private String tempTime;

    private final Handler handler = new Handler();
    private volatile boolean stopRun = false;

    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss:SS", Locale.JAPAN);


    public static StopWatch newInstance(int position){

        StopWatch stopWatch = new StopWatch();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        stopWatch.setArguments(bundle);

        return stopWatch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.stop_watch, container, false);

        timerText = (TextView)v.findViewById(R.id.Timer);
        timerText.setText(dataFormat.format(0));

        pastTime = (TextView)v.findViewById(R.id.pastTime);
        pastTime.setText(dataFormat.format(0));

        startButton = (Button)v.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        Button stopButton = (Button)v.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {

        Thread thread;
        if(v == startButton){
            stopRun = false;
            thread = new Thread(this);
            thread.start();

            startTime = System.currentTimeMillis();
        }
        else{
            stopRun = true;
            timerText.setText(dataFormat.format(0));
            pastTime.setText(tempTime);
        }
    }

    @Override
    public void run() {
        // 10mm sec order
        int period = 10;

        while(!stopRun){
            // Sleep : 10mm sec
            try{
                Thread.sleep(period);

            }catch (InterruptedException e){
                e.printStackTrace();
                stopRun = true;
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long endTime = System.currentTimeMillis();
                    //カウント時間 = 経過時間 - 開始時間
                    long diffTime = (endTime - startTime);

                    timerText.setText(dataFormat.format(diffTime));
                    tempTime = String.valueOf(dataFormat.format(diffTime));
                }

            });
        }
    }
}
