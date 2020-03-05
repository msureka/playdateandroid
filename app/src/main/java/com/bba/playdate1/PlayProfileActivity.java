package com.bba.playdate1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayProfileActivity extends AppCompatActivity {
    public static String LikeToPlayString="";
    //variables
    TextView BackTxt, NextTxt, TitleTxt, outdoortxt, Everehere, Indoor;
    LinearLayout OutdoorRow, EverywhereRow, IndoorRow;
    Intent intent;
    ArrayList<String>Arra_AllRecord=new ArrayList<>();
    public static Activity removeActivity_playProfileActivity;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playprofile);
        removeActivity_playProfileActivity = this;

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        //init variabes
        BackTxt = (TextView) findViewById(R.id.back_txt);
        outdoortxt = (TextView) findViewById(R.id.outdoor_txt);
        Everehere = (TextView) findViewById(R.id.anywhere_txt);
        Indoor = (TextView) findViewById(R.id.indoor_txt);
        NextTxt = (TextView) findViewById(R.id.next_txt);
        TitleTxt = (TextView) findViewById(R.id.titletxt);
        OutdoorRow = (LinearLayout) findViewById(R.id.rl1);
        EverywhereRow = (LinearLayout) findViewById(R.id.rl2);
        IndoorRow = (LinearLayout) findViewById(R.id.rl3);

        TextView play_txt = (TextView) findViewById(R.id.play_txt);








        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("playprofile_row").equalsIgnoreCase("yes"))
            {
                Arra_AllRecord.add(PlayProfileActivity.LikeToPlayString);
                Arra_AllRecord.add(SuperHeroProfileActivity.Str_superHero);
                Arra_AllRecord.add(FavroiteProfileActivity.Activity1);
                Arra_AllRecord.add(FavroiteProfileActivity.Activity2);
                Arra_AllRecord.add(FavroiteProfileActivity.Activity3);
                Arra_AllRecord.add(MeetProfileActivity.LikeToMeetString);

            }
            else
            {
                LikeToPlayString=pref.getString("liketoplay","");
            }
        }
        else
        {

            LikeToPlayString=pref.getString("liketoplay","");
        }



        TitleTxt.setText("2/5");


            if (LikeToPlayString.length()==0) {
                NextTxt.setEnabled(false);
                NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
            }
            else

            {


                    if (LikeToPlayString.equalsIgnoreCase("Outdoor")) {
                        //it is Outdoor
                        // OutdoorRow img selected and other 2 deselected
                        OutdoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                        IndoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        EverywhereRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        outdoortxt.setTypeface(Typeface.DEFAULT_BOLD);
                    } else if (LikeToPlayString.equalsIgnoreCase("Everywhere")) {
                        //it is Everywhere
                        // EverywhereRow img selected and other 2 deselected
                        EverywhereRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                        IndoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        OutdoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        Everehere.setTypeface(Typeface.DEFAULT_BOLD);
                    } else if (LikeToPlayString.equalsIgnoreCase("Indoor")) {
                        //it is Indoor
                        // IndoorRow img selected and other 2 deselected
                        IndoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                        EverywhereRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        OutdoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        Indoor.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                NextTxt.setEnabled(true);
                NextTxt.setTextColor(Color.parseColor("#000000"));


            }



        NextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {
                    if (extras.getString("playprofile_row").equalsIgnoreCase("yes"))
                    {
                        intent = new Intent(PlayProfileActivity.this, MeetProfileActivity.class);
                        intent.putExtra("meetprofile_row","yes");
                        startActivity(intent);
                    }
                    else
                    {
                        editor.putString("liketoplay",LikeToPlayString);
                        editor.commit();
                        intent = new Intent(PlayProfileActivity.this, MeetProfileActivity.class);
                        intent.putExtra("meetprofile_row","no");
                        startActivity(intent);
                    }
                }
                else
                {
                    editor.putString("liketoplay",LikeToPlayString);
                    editor.commit(); intent = new Intent(PlayProfileActivity.this, MeetProfileActivity.class);
                    intent.putExtra("meetprofile_row","no");
                    startActivity(intent);
                }




            }
        });


        BackTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SettingActivity.str_suprtheroSave.equalsIgnoreCase("no")) {


                }
                else
                {
                    if (Arra_AllRecord.size() !=0) {
                        PlayProfileActivity.LikeToPlayString = Arra_AllRecord.get(0);
                        SuperHeroProfileActivity.Str_superHero = Arra_AllRecord.get(1);
                        FavroiteProfileActivity.Activity1 = Arra_AllRecord.get(2);
                        FavroiteProfileActivity.Activity2 = Arra_AllRecord.get(3);
                        FavroiteProfileActivity.Activity3 = Arra_AllRecord.get(4);
                        MeetProfileActivity.LikeToMeetString = Arra_AllRecord.get(5);
                    }
                }
              finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackTxt.performClick();
    }

    //show funtion called when one of three btn from indoor,outdoor or Everywhere clicked
    public void show(View view) {
        //  boolean aaa = false;

        //Next textview enable
        NextTxt.setEnabled(true);
        NextTxt.setTextColor(Color.parseColor("#000000"));

        //check which button clicked
        if (view == findViewById(R.id.rl1)) {
            //it is Outdoor
            // OutdoorRow img selected and other 2 deselected
            OutdoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
            IndoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            EverywhereRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            outdoortxt.setTypeface(Typeface.DEFAULT_BOLD);
            Everehere.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            Indoor.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set  LikeToMeetString variable as Outdoor
            LikeToPlayString = "Outdoor";
            //Toast.makeText(getApplicationContext(),LikeToPlayString,Toast.LENGTH_LONG).show();
        } else if (view == findViewById(R.id.rl2)) {
            //it is Everywhere
            // EverywhereRow img selected and other 2 deselected
            EverywhereRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
            IndoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            OutdoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            Everehere.setTypeface(Typeface.DEFAULT_BOLD);
            outdoortxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            Indoor.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set  LikeToMeetString variable as Everywhere
            LikeToPlayString = "Everywhere";
            //Toast.makeText(getApplicationContext(),LikeToPlayString,Toast.LENGTH_LONG).show();
        } else if (view == findViewById(R.id.rl3)) {
            //it is Indoor
            // IndoorRow img selected and other 2 deselected
            IndoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
            EverywhereRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            OutdoorRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            Indoor.setTypeface(Typeface.DEFAULT_BOLD);
            outdoortxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            Everehere.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set  LikeToMeetString variable as Indoor
            LikeToPlayString = "Indoor";
            // Toast.makeText(getApplicationContext(),LikeToPlayString,Toast.LENGTH_LONG).show();
        }


    }

}
