package com.example.m0z.stopwatch;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CountDownFragment extends Fragment {

    long countNumber;
    long interval;

    private TextView countText;
    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss:SS", Locale.JAPAN);

    private Button setTimer;
    private Button startButton;
    private Button stopButton;

    private CountDown countDown;

    //フラグメントをViewPagerAdapterに渡すのだ
    public static android.support.v4.app.Fragment newInstance(int position){
        CountDownFragment countDownFlag = new CountDownFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CountDown",position);
        countDownFlag.setArguments(bundle);

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.countdown_timer, container, false);

        // 3分 = 3 * 60 * 1000 = 180000 msec
        countNumber = 180000;
        // インターバル msec
        interval = 10;

        countText = (TextView)v.findViewById(R.id.countTimer);
        startButton = (Button)v.findViewById(R.id.countStart);
        stopButton = (Button)v.findViewById(R.id.countStop);

        countDown = new CountDown(countNumber, interval);
        countText.setText(dataFormat.format(countNumber));

        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //開始
                countDown.start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止
                countDown.cancel();
                countText.setText(dataFormat.format(countNumber));
            }
        });

        return v;
    }

    class CountDown extends CountDownTimer{

        //コンストラクタ
        CountDown(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 残り時間を分、秒、ミリ秒に分割
            //long mm = millisUntilFinished / 1000 / 60;
            //long ss = millisUntilFinished / 1000 % 60;
            //long ms = millisUntilFinished - ss * 1000 - mm * 1000 * 60;
            //timerText.setText(String.format("%1$02d:%2$02d.%3$03d", mm, ss, ms));
            countText.setText(dataFormat.format(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            //完了時
            countText.setText(dataFormat.format(0));
        }
    }
}
