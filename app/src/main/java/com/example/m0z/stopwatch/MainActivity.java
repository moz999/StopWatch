package com.example.m0z.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        Runnable, View.OnClickListener{

    private long startTime;

    private TextView timerText;
    private Button startButton;

    private final Handler handler = new Handler();
    private volatile boolean stopRun = false;

    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss:SS", Locale.JAPAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView)findViewById(R.id.Timer);
        timerText.setText(dataFormat.format(0));

        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

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
                }
            });
        }
    }
}
