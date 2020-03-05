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


public class TalkToActivity extends AppCompatActivity {

    RelativeLayout BoyRow, GirlRow, BoyGirlRow;
    TextView SaveTxt;
    ImageView BoyTickImg, GirlTickImg, BoyGirlTickImg;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);


        BoyRow = (RelativeLayout) findViewById(R.id.r99);
        GirlRow = (RelativeLayout) findViewById(R.id.r111);
        BoyGirlRow = (RelativeLayout) findViewById(R.id.r222);
        SaveTxt = (TextView) findViewById(R.id.save_txt);
        BoyTickImg = (ImageView) findViewById(R.id.tboy_img);
        GirlTickImg = (ImageView) findViewById(R.id.tgirl_img);
        BoyGirlTickImg = (ImageView) findViewById(R.id.tbg_img);



        SaveTxt.setEnabled(true);

        if (SettingActivity.TalkTo.equalsIgnoreCase("BOYSANDGIRLS")) {
            BoyGirlTickImg.setVisibility(View.VISIBLE);
            BoyTickImg.setVisibility(View.INVISIBLE);
            GirlTickImg.setVisibility(View.INVISIBLE);
            SettingActivity.TalkTo = "BOYSANDGIRLS";
        } else if (SettingActivity.TalkTo.equalsIgnoreCase("BOY")) {
            BoyGirlTickImg.setVisibility(View.INVISIBLE);
            BoyTickImg.setVisibility(View.VISIBLE);
            GirlTickImg.setVisibility(View.INVISIBLE);
            SettingActivity.TalkTo = "BOY";
        } else {
            BoyGirlTickImg.setVisibility(View.INVISIBLE);
            BoyTickImg.setVisibility(View.INVISIBLE);
            GirlTickImg.setVisibility(View.VISIBLE);
            SettingActivity.TalkTo = "GIRL";
        }

        BoyGirlRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                BoyGirlTickImg.setVisibility(View.VISIBLE);
                BoyTickImg.setVisibility(View.INVISIBLE);
                GirlTickImg.setVisibility(View.INVISIBLE);
                SettingActivity.TalkTo = "BOYSANDGIRLS";
            }
        });


        BoyRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                BoyGirlTickImg.setVisibility(View.INVISIBLE);
                BoyTickImg.setVisibility(View.VISIBLE);
                GirlTickImg.setVisibility(View.INVISIBLE);
                SettingActivity.TalkTo = "BOY";
            }
        });

        GirlRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                BoyGirlTickImg.setVisibility(View.INVISIBLE);
                BoyTickImg.setVisibility(View.INVISIBLE);
                GirlTickImg.setVisibility(View.VISIBLE);
                SettingActivity.TalkTo = "GIRL";
            }
        });

        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent("registerinfo_setting");
                LocalBroadcastManager.getInstance(TalkToActivity.this).sendBroadcast(intent1);
                finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent1 = new Intent("registerinfo_setting");
        LocalBroadcastManager.getInstance(TalkToActivity.this).sendBroadcast(intent1);
        finish();


    }
}


