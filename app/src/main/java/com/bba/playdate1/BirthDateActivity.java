package com.bba.playdate1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by spielmohitp on 2/18/2017.
 **/
public class BirthDateActivity extends AppCompatActivity {

    String BirthDateString;
    //Varibales
    TextView SaveTxt;
    EditText DateEditTxt;
    Intent intent;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    Date date = null;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bithdate);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();





        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("dob_row").equalsIgnoreCase("yes"))
            {
                BirthDateString=SettingActivity.BirthDate;

            }
            else
            {
                BirthDateString=pref.getString("birthdate","");


            }
        }
        else
        {

            BirthDateString=pref.getString("birthdate","");

        }


        // variable Intialitaion
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView born_txt = (TextView) findViewById(R.id.born_txt);
SaveTxt = (TextView) findViewById(R.id.birthsave_txt);
        DateEditTxt = (EditText) findViewById(R.id.birth_edittxt);




        //if Date Edittext already filled enable Save button(Save Textview)  else Disable save btn
        if (BirthDateString.length() > 0)
        {
            SaveTxt.setEnabled(true);
            SaveTxt.setTextColor(Color.parseColor("#000000"));
        } else
            {
                SaveTxt.setEnabled(false);
                SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));

        }

        // set Text To Date EditText if already entered before
        // Textwatcher on date EditText to check text entered or edit null


        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date = format1.parse(BirthDateString);
            String Str_DOb = format2.format(date);
            DateEditTxt.setText(Str_DOb);
            BirthDateString=format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }





        // Date EditText data fatch
        DateEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (DateEditTxt.getText().length() <= 0) {
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
                //getting Date EditTExt String in String Variable.

                if (DateEditTxt.getText().length() <= 0) {
                    // About text lenth less than 0 disable save btn
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
        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {



                    String Str_DOb = null;
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    try {
                        date = format2.parse(BirthDateString);
                        Str_DOb = format1.format(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {

                        if (extras.getString("dob_row").equalsIgnoreCase("yes")) {
                            SettingActivity.BirthDate = Str_DOb;
                            Intent intent1 = new Intent("registerinfo_setting");
                            LocalBroadcastManager.getInstance(BirthDateActivity.this).sendBroadcast(intent1);

                        } else {

                            editor.putString("birthdate", Str_DOb);
                            editor.commit();
                            Intent intent1 = new Intent("registerinfo");
                            // intent1.putExtra("message", mapstr);
                            LocalBroadcastManager.getInstance(BirthDateActivity.this).sendBroadcast(intent1);

                        }
                    } else {

                        editor.putString("birthdate", Str_DOb);
                        editor.commit();
                        Intent intent1 = new Intent("registerinfo");
                        // intent1.putExtra("message", mapstr);
                        LocalBroadcastManager.getInstance(BirthDateActivity.this).sendBroadcast(intent1);

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
    //Onclick function called when click on Date EditText
    //it open calander set the selected date  to  date editText.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onclick(View v) {

       Calendar calendar =Calendar.getInstance();
       if (date !=null) {
           calendar.setTime(date);
       }
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
       DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
           SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                System.out.print(dateFormatter.format(newDate.getTime()));


                BirthDateString = dateFormatter.format(newDate.getTime());
                DateEditTxt.setText(BirthDateString);

            }
        }, yy, mm, dd);

        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.YEAR, -12);

        datePicker.getDatePicker().setMinDate(cal.getTimeInMillis());

        datePicker.show();
    }

}
