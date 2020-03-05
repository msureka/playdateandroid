package com.bba.playdate1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bba.adapter.CustomPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class Tutorial extends Activity {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    ImageView firstimg;
    SharedPreferences prefs = null;
    //PageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        firstimg = (ImageView) findViewById(R.id.firstpage_img);
        prefs = getSharedPreferences("com.bba.playdate", MODE_PRIVATE);

        mCustomPagerAdapter = new CustomPagerAdapter(this);
        final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        indicator.setViewPager(mViewPager);
        //indicator.setVisibility(View.INVISIBLE);
        //Toast.makeText(getApplicationContext()," "+prefs.getBoolean("firstrun", true),Toast.LENGTH_LONG).show();

        if (prefs.getBoolean("firstrun", true)) {

            firstimg.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.INVISIBLE);
            indicator.setVisibility((View.INVISIBLE));
        } else {

            firstimg.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            indicator.setVisibility((View.VISIBLE));

        }


        final float density = getResources().getDisplayMetrics().density;
        firstimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstimg.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
                indicator.setVisibility((View.VISIBLE));
                prefs.edit().putBoolean("firstrun", false).commit();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        //  if (prefs.getBoolean("firstrun", true)) {
        // Do first run stuff here then set 'firstrun' as false
        // using the following line to edit/commit prefs
        //    Log.e("hi","hello");
        // }
        ////  else
        // {
        //    Log.e("bye","tata");
        // }
    }


    @Override
    public void onBackPressed() {
        System.exit(0);
        // your code.
    }


}

