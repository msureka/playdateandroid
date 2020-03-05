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

/**
 * Created by spielmohitp on 2/20/2017.
 */
public class MeetProfileActivity extends AppCompatActivity {

    public static String LikeToMeetString="";
    //variables
    TextView BackTxt, NextTxt, TitleTxt, mortxt, anytxt, afttxt;
    LinearLayout MorningRow, AnyTimeRow, AfterSchoolRow;
    public static Activity removeActivity_MeetProfileActivity;
    Intent intent;

    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetprofile);
        removeActivity_MeetProfileActivity=this;

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        //init variabes
        BackTxt = (TextView) findViewById(R.id.back_txt);
        mortxt = (TextView) findViewById(R.id.outdoor_txt);
        anytxt = (TextView) findViewById(R.id.anytime_txt);
        afttxt = (TextView) findViewById(R.id.school_txt);
        NextTxt = (TextView) findViewById(R.id.next_txt);
        TitleTxt = (TextView) findViewById(R.id.titletxt);
        MorningRow = (LinearLayout) findViewById(R.id.rl1);
        AnyTimeRow = (LinearLayout) findViewById(R.id.rl2);
        AfterSchoolRow = (LinearLayout) findViewById(R.id.rl3);

        //Back text Set Visible
        BackTxt.setVisibility(View.VISIBLE);



        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("meetprofile_row").equalsIgnoreCase("yes"))
            {

            }
            else
            {
                LikeToMeetString=pref.getString("icanmeet","");
            }
        }
        else
        {

            LikeToMeetString=pref.getString("icanmeet","");
        }

        //
        //set Title Text
        TitleTxt.setText("3/5");


            if (LikeToMeetString.length()==0) {
                NextTxt.setEnabled(false);
                NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
            }
            else
                {

                    //check LikeToMeetString value
                    if (LikeToMeetString.equalsIgnoreCase("Mornings")) {
                        //it is Mornings
                        // MorningRow img selected and other 2 deselected
                        MorningRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                        AfterSchoolRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        AnyTimeRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));

                        mortxt.setTypeface(Typeface.DEFAULT_BOLD);
                    } else if (LikeToMeetString.equalsIgnoreCase("Anytime")) {
                        //it is Anytime
                        // AnytimeRow img selected and other 2 deselected
                        AnyTimeRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                        AfterSchoolRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        MorningRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));

                        anytxt.setTypeface(Typeface.DEFAULT_BOLD);
                    } else if (LikeToMeetString.equalsIgnoreCase("After School")) {
                        //it is After School
                        // AfterSchoolRow img selected and other 2 deselected
                        AfterSchoolRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                        AnyTimeRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                        MorningRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));

                        afttxt.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                NextTxt.setEnabled(true);
                NextTxt.setTextColor(Color.parseColor("#000000"));
            }







        //Next text onclick function
        NextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {
                    if (extras.getString("meetprofile_row").equalsIgnoreCase("yes"))
                    {
                        intent = new Intent(MeetProfileActivity.this, FavroiteProfileActivity.class);
                        intent.putExtra("favprofile_row","yes");
                        startActivity(intent);
                    }
                    else
                    {
                        editor.putString("icanmeet",LikeToMeetString);
                        editor.commit();
                        intent = new Intent(MeetProfileActivity.this, FavroiteProfileActivity.class);
                        intent.putExtra("favprofile_row","no");
                        startActivity(intent);
                    }
                }
                else
                {

                    editor.putString("icanmeet",LikeToMeetString);
                    editor.commit();
                    intent = new Intent(MeetProfileActivity.this, FavroiteProfileActivity.class);
                    intent.putExtra("favprofile_row","no");
                    startActivity(intent);
                }




            }
        });

        //Back text onclick function
        BackTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    //show funtion called when one of three btn from Anytime,after shool or morning clicked
    public void show(View view) {
        //  boolean aaa = false;

        //Next textview enable
        NextTxt.setEnabled(true);
        NextTxt.setTextColor(Color.parseColor("#000000"));

        //check which button clicked
        if (view == findViewById(R.id.rl1)) {
            //it is Mornings
            // MorningRow img selected and other 2 deselected
            MorningRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
            AfterSchoolRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            AnyTimeRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));

            mortxt.setTypeface(Typeface.DEFAULT_BOLD);
            anytxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            afttxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set  LikeToMeetString variable as Mornings
            LikeToMeetString = "Mornings";
            //Toast.makeText(getApplicationContext(),LikeToMeetString,Toast.LENGTH_LONG).show();
        } else if (view == findViewById(R.id.rl2)) {

            //it is Anytime
            // Anytime img selected and other 2 deselected
            AnyTimeRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
            AfterSchoolRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            MorningRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));


            anytxt.setTypeface(Typeface.DEFAULT_BOLD);
            mortxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            afttxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set  LikeToMeetString variable as Anytime
            LikeToMeetString = "Anytime";
            // Toast.makeText(getApplicationContext(),LikeToMeetString,Toast.LENGTH_LONG).show();
        } else if (view == findViewById(R.id.rl3)) {

            //it is After School
            // After School img selected and other 2 deselected
            AfterSchoolRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
            AnyTimeRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
            MorningRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));


            afttxt.setTypeface(Typeface.DEFAULT_BOLD);
            mortxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            anytxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set  LikeToMeetString variable as After School
            LikeToMeetString = "After School";
            // Toast.makeText(getApplicationContext(),LikeToMeetString,Toast.LENGTH_LONG).show();
        }


    }


}
