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
public class GenderActivity extends AppCompatActivity {

    //variable
    RelativeLayout BoyRow, GirlRow;
    TextView SaveTxt;
    ImageView BoyTickImg, GirlTickImg;
    Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);


        //init variable
        BoyRow = (RelativeLayout) findViewById(R.id.r22);
        GirlRow = (RelativeLayout) findViewById(R.id.r33);
        SaveTxt = (TextView) findViewById(R.id.save_txt);
        BoyTickImg = (ImageView) findViewById(R.id.boy_img);
        GirlTickImg = (ImageView) findViewById(R.id.girl_img);


        //Enable Save Text
        SaveTxt.setEnabled(true);

        //Check user Gender
        if (SettingActivity.Gender.equalsIgnoreCase("Boy")) {
            //it is Boy
            //set check tick visible to boy row and invisibale to girl row
            BoyTickImg.setVisibility(View.VISIBLE);
            GirlTickImg.setVisibility(View.INVISIBLE);
            SettingActivity.Gender = "Boy";
        } else
            {
            //it is Girl
            //set check tick invisible to boy row and visibale to girl row
            BoyTickImg.setVisibility(View.INVISIBLE);
            GirlTickImg.setVisibility(View.VISIBLE);
            SettingActivity.Gender = "Girl";
        }


        BoyRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save txt enable
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                BoyTickImg.setVisibility(View.VISIBLE);
                GirlTickImg.setVisibility(View.INVISIBLE);
                SettingActivity.Gender = "Boy";
            }
        });


        //on click Girl Row
        GirlRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save txt enable
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                BoyTickImg.setVisibility(View.INVISIBLE);
                GirlTickImg.setVisibility(View.VISIBLE);
                SettingActivity.Gender = "Girl";
            }
        });


        //On click Save Text
        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent("registerinfo_setting");
                LocalBroadcastManager.getInstance(GenderActivity.this).sendBroadcast(intent1);
                finish();

            }
        });
    }


    //on Back Presseed
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent("registerinfo_setting");
        LocalBroadcastManager.getInstance(GenderActivity.this).sendBroadcast(intent1);
        finish();


    }

}
