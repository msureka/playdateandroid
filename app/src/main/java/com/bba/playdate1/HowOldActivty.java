package com.bba.playdate1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by spielmohitp on 2/27/2017.
 */
public class HowOldActivty extends AppCompatActivity {


    //variable
    RelativeLayout AllAgeRow, YongerRow, SameAsRow;
    TextView SaveTxt;
    ImageView AllAgeTickImg, YongerTickImg, SameAsTickImg;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old);


        //init variable
        AllAgeRow = (RelativeLayout) findViewById(R.id.r55);
        YongerRow = (RelativeLayout) findViewById(R.id.r66);
        SameAsRow = (RelativeLayout) findViewById(R.id.r77);
        SaveTxt = (TextView) findViewById(R.id.save_txt);
        AllAgeTickImg = (ImageView) findViewById(R.id.allage_img);
        YongerTickImg = (ImageView) findViewById(R.id.twoyr_img);
        SameAsTickImg = (ImageView) findViewById(R.id.mineage_img);




        //Enable Save Text
        SaveTxt.setEnabled(true);

        //Check user Selected Age for Friends
        if (SettingActivity.HowOld.equalsIgnoreCase("0")) {
            //it is 0
            //set check tick visible of sameasme row and invisibale of allage and yonger row
            AllAgeTickImg.setVisibility(View.INVISIBLE);
            YongerTickImg.setVisibility(View.INVISIBLE);
            SameAsTickImg.setVisibility(View.VISIBLE);
            ///set Setting Activity Howold Variable as 0
            SettingActivity.HowOld = "0";
        } else if (SettingActivity.HowOld.equalsIgnoreCase("1")) {
            //it is 1
            //set check tick visible of yonger row and invisibale of allage and sameasme row
            AllAgeTickImg.setVisibility(View.INVISIBLE);
            YongerTickImg.setVisibility(View.VISIBLE);
            SameAsTickImg.setVisibility(View.INVISIBLE);
            ///set Setting Activity Howold Variable as 1
            SettingActivity.HowOld = "1";
        } else {
            //it is 2
            //set check tick visible of allage row and invisibale of yonger and sameasme row
            AllAgeTickImg.setVisibility(View.VISIBLE);
            YongerTickImg.setVisibility(View.INVISIBLE);
            SameAsTickImg.setVisibility(View.INVISIBLE);
            ///set Setting Activity Howold Variable as 2
            SettingActivity.HowOld = "2";
        }

        //on click Allage Row
        AllAgeRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save txt enable
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                ///set check tick visible of allage row and invisibale of yonger and sameasme row
                AllAgeTickImg.setVisibility(View.VISIBLE);
                YongerTickImg.setVisibility(View.INVISIBLE);
                SameAsTickImg.setVisibility(View.INVISIBLE);
                SettingActivity.HowOld = "2";
            }
        });


        //on click yonger Row
        YongerRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save txt enable sdfffffffffff
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                AllAgeTickImg.setVisibility(View.INVISIBLE);
                YongerTickImg.setVisibility(View.VISIBLE);
                SameAsTickImg.setVisibility(View.INVISIBLE);
                ///set Setting Activity Howold Variable as 1
                SettingActivity.HowOld = "1";
            }
        });


        SameAsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save txt enable
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                //set check tick visible of sameasme row and invisibale of allage and yonger row
                AllAgeTickImg.setVisibility(View.INVISIBLE);
                YongerTickImg.setVisibility(View.INVISIBLE);
                SameAsTickImg.setVisibility(View.VISIBLE);
                SettingActivity.HowOld = "0";
            }
        });


        //On click Save Text
        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("registerinfo_setting");
                LocalBroadcastManager.getInstance(HowOldActivty.this).sendBroadcast(intent1);

                finish();

            }
        });

    }

    //on Back Presseed
    public void onBackPressed() {
        super.onBackPressed();
        //Intent to Setting Activity
        Intent intent1 = new Intent("registerinfo_setting");
        LocalBroadcastManager.getInstance(HowOldActivty.this).sendBroadcast(intent1);
        finish();


    }
}
