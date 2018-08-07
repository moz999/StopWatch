package com.example.m0z.stopwatch;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TimerFragmentPagerAdapter extends FragmentPagerAdapter{

    private Context context;

    //------------------------
    // コンストラクタ
    //------------------------
    public TimerFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return CountDownFragment.newInstance(position);

    }

    @Override
    public int getCount() {
        return 3;
    }
}
