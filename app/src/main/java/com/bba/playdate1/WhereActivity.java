package com.bba.playdate1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bba.fragment.DiscoverFragment;

/**
 * Created by spielmohitp on 2/27/2017.
 */
public class WhereActivity extends AppCompatActivity {

    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    RelativeLayout MyCityRow, MyCountryRow, AnyWhereRow;
    TextView SaveTxt;
    ImageView MyCityImg, MyCountryImg, AnyWhereImg;
    Intent intent;
    int PRIVATE_MODE = 0;
    String str_distance="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        //init variables
        MyCityRow = (RelativeLayout) findViewById(R.id.r2);
        MyCountryRow = (RelativeLayout) findViewById(R.id.r3);
        AnyWhereRow = (RelativeLayout) findViewById(R.id.r4);
        SaveTxt = (TextView) findViewById(R.id.save_txt);
        TextView SaveTxt1 = (TextView) findViewById(R.id.title_txt);
        MyCityImg = (ImageView) findViewById(R.id.mycity_img);
        MyCountryImg = (ImageView) findViewById(R.id.mycountry_img);
        AnyWhereImg = (ImageView) findViewById(R.id.world_img);

        SaveTxt.setEnabled(true);

        if (SettingActivity.Where.equalsIgnoreCase("CITY")) {
            MyCityImg.setVisibility(View.VISIBLE);
            MyCountryImg.setVisibility(View.INVISIBLE);
            AnyWhereImg.setVisibility(View.INVISIBLE);
            SettingActivity.Where = "CITY";
        } else if (SettingActivity.Where.equalsIgnoreCase("COUNTRY")) {
            MyCityImg.setVisibility(View.INVISIBLE);
            MyCountryImg.setVisibility(View.VISIBLE);
            AnyWhereImg.setVisibility(View.INVISIBLE);
            SettingActivity.Where = "COUNTRY";
        } else {
            MyCityImg.setVisibility(View.INVISIBLE);
            MyCountryImg.setVisibility(View.INVISIBLE);
            AnyWhereImg.setVisibility(View.VISIBLE);
            SettingActivity.Where = "GLOBAL";
        }

        MyCityRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                MyCityImg.setVisibility(View.VISIBLE);
                MyCountryImg.setVisibility(View.INVISIBLE);
                AnyWhereImg.setVisibility(View.INVISIBLE);
                SettingActivity.Where = "CITY";
                str_distance= "CITY";
            }
        });


        MyCountryRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                MyCityImg.setVisibility(View.INVISIBLE);
                MyCountryImg.setVisibility(View.VISIBLE);
                AnyWhereImg.setVisibility(View.INVISIBLE);
                SettingActivity.Where = "COUNTRY";
                str_distance= "COUNTRY";
            }
        });

        AnyWhereRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTxt.setTextColor(Color.parseColor("#000000"));
                SaveTxt.setEnabled(true);
                MyCityImg.setVisibility(View.INVISIBLE);
                MyCountryImg.setVisibility(View.INVISIBLE);
                AnyWhereImg.setVisibility(View.VISIBLE);
                SettingActivity.Where = "GLOBAL";
                str_distance= "GLOBAL";
            }
        });

        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (SettingActivity.whereflag.equalsIgnoreCase("yes"))
{
    finish();;

}
else
{

    editor.putString("distance",str_distance);
    editor.commit();
    finish();
    WhereActivity.this.overridePendingTransition(R.anim.slide_up, R.anim.stay);
}


            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}
