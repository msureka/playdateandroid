package com.bba.playdate1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by spielmohitp on 2/18/2017.
 */
public class AboutMeActivity extends AppCompatActivity {


    TextView SaveTxt;
    EditText AboutEditTxt;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        // variable Intialitaion
        SaveTxt = (TextView) findViewById(R.id.aboutsave_txt);
        AboutEditTxt = (EditText) findViewById(R.id.about_editText);
        TextView title_txt = (TextView) findViewById(R.id.title_txt);
        TextView textView2 = (TextView) findViewById(R.id.textView2);







        String str_name=null;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("about_row").equalsIgnoreCase("yes"))
            {
                str_name=SettingActivity.Desc;

            }
            else {
                str_name=pref.getString("user_about","");

            }
        }
        else
        {


            str_name=pref.getString("user_about","");

        }




        if (str_name.length()<=0) {
            SaveTxt.setEnabled(false);
            SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
        } else {
            SaveTxt.setEnabled(true);
            SaveTxt.setTextColor(Color.parseColor("#000000"));
            AboutEditTxt.setText(str_name);
        }




        // Textwatcher on About EditText to check text entered or edit null
        AboutEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                AboutEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(130)});

                String Str_name= String.valueOf(AboutEditTxt.getText());

                if (Str_name.length() <= 0) {
                    // About text lenth less than 0 disable save btn
                    SaveTxt.setEnabled(false);
                    SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
                } else {
                    // About text lenth grater than 0 enable save btn
                    SaveTxt.setEnabled(true);
                    SaveTxt.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                String Str_name= String.valueOf(AboutEditTxt.getText());
                if (Str_name.length() <= 0){
                    SaveTxt.setEnabled(false);
                    SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
                } else {
                    // About text lenth grater than 0 enable save btn
                    SaveTxt.setEnabled(true);
                    SaveTxt.setTextColor(Color.parseColor("#000000"));
                }
            }
        });


        //save text onclick
        SaveTxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {






                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {

                    if ( extras.getString("about_row").equalsIgnoreCase("yes"))
                    {
                        SettingActivity.Desc=String.valueOf(AboutEditTxt.getText());
                        Intent intent1 = new Intent("registerinfo_setting");
                        LocalBroadcastManager.getInstance(AboutMeActivity.this).sendBroadcast(intent1);

                    }
                    else
                    {

                        editor.putString("user_about", String.valueOf(AboutEditTxt.getText()));
                        editor.commit();
                        Intent intent1 = new Intent("registerinfo");
                        // intent1.putExtra("message", mapstr);
                        LocalBroadcastManager.getInstance(AboutMeActivity.this).sendBroadcast(intent1);
                    }
                }
                else
                {

                    editor.putString("user_about", String.valueOf(AboutEditTxt.getText()));
                    editor.commit();
                    Intent intent1 = new Intent("registerinfo");
                    // intent1.putExtra("message", mapstr);
                    LocalBroadcastManager.getInstance(AboutMeActivity.this).sendBroadcast(intent1);
                }

                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {

            if ( extras.getString("about_row").equalsIgnoreCase("yes"))
            {
                SettingActivity.Desc=String.valueOf(AboutEditTxt.getText());
                Intent intent1 = new Intent("registerinfo_setting");
                LocalBroadcastManager.getInstance(AboutMeActivity.this).sendBroadcast(intent1);

            }
        }
        else
        {

            editor.putString("user_about", String.valueOf(AboutEditTxt.getText()));
            editor.commit();
            Intent intent1 = new Intent("registerinfo");
            // intent1.putExtra("message", mapstr);
            LocalBroadcastManager.getInstance(AboutMeActivity.this).sendBroadcast(intent1);
        }

        finish();



    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}