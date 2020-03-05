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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by spielmohitp on 2/18/2017.
 */
public class NameActivity extends AppCompatActivity {


    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView SaveTxt;
    EditText NameEditTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        //init variabes
        SaveTxt = (TextView) findViewById(R.id.namesave_txt);
        NameEditTxt = (EditText) findViewById(R.id.pname_editText);
        TextView my_child_txt = (TextView) findViewById(R.id.my_child_txt);
        TextView tiltetxt = (TextView) findViewById(R.id.tiltetxt);
        // Disable Save Txt
        SaveTxt.setEnabled(true);
        SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
        NameEditTxt.setSingleLine(true);
        NameEditTxt.setMaxLines(1);
        NameEditTxt.setLines(1);
        NameEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});



        String str_name=null;
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("name_row").equalsIgnoreCase("yes"))
            {
                str_name=SettingActivity.Name;


            }
            else
            {
                str_name=pref.getString("user_name","");
            }
        }
        else
    {

        str_name=pref.getString("user_name","");
    }




        if (str_name.length()<=0)
        {
            NameEditTxt.setText("");
            SaveTxt.setEnabled(false);
            SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
        } else
            {
            SaveTxt.setEnabled(true);
            SaveTxt.setTextColor(Color.parseColor("#000000"));
                NameEditTxt.setText(str_name);
        }





        // Textwatcher on Name EditText to check text entered or edit null
        NameEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                NameEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
               String Str_name= String.valueOf(NameEditTxt.getText());

                if (Str_name.length() <= 0) {
                    SaveTxt.setEnabled(false);
                    SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
                } else
                    {
                    SaveTxt.setEnabled(true);
                    SaveTxt.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                NameEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
                String Str_name= String.valueOf(NameEditTxt.getText());

                if (Str_name.length() <= 0) {
                    SaveTxt.setEnabled(false);
                    SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
                } else {
                    SaveTxt.setEnabled(true);
                    SaveTxt.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        //save text onclick
        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {

            if ( extras.getString("name_row").equalsIgnoreCase("yes"))
            {
                SettingActivity.Name=String.valueOf(NameEditTxt.getText());
                Intent intent1 = new Intent("registerinfo_setting");
                // intent1.putExtra("message", mapstr);
                LocalBroadcastManager.getInstance(NameActivity.this).sendBroadcast(intent1);

            }
            else
            {
                editor.putString("user_name", String.valueOf(NameEditTxt.getText()));
                editor.commit();

                Intent intent1 = new Intent("registerinfo");
                // intent1.putExtra("message", mapstr);
                LocalBroadcastManager.getInstance(NameActivity.this).sendBroadcast(intent1);
            }
                }
                else
                    {

                    editor.putString("user_name", String.valueOf(NameEditTxt.getText()));
                    editor.commit();

                        Intent intent1 = new Intent("registerinfo");
                        // intent1.putExtra("message", mapstr);
                        LocalBroadcastManager.getInstance(NameActivity.this).sendBroadcast(intent1);
                }

                finish();



            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}
