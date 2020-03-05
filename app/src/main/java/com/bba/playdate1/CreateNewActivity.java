package com.bba.playdate1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class CreateNewActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 1111;
    public static String titileString=null;
    public static String locString=null;
    public static String optionalString=null;
    public static JSONObject obj_editmeetups;
    public static String str_editeventid=null;
    public static String strdate, strtime, weekday, convetdate;
    EditText titleEdittext, locationeditext, optionedittext;
    TextView timehr, ampm, day1;
    String formatdate;
    private TextView tvDisplayDate, invite, cancel,invite_one,txt_meetups_title;
    public static Activity removeActivity_CreateNewActivity;
    private int year, month,day,hours, minute;
    Date date_editdate=null;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog_Box;
    ProgressDialog progressDialog;


    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hours = hourOfDay;
            minute = minutes;

            StringBuilder aTime = new StringBuilder().append(hours).append(':')
                    .append(minutes);

            Log.d("time1132", aTime.toString());
            timehr.setText(aTime);
            String strtime3 = timehr.getText().toString();
            strtime = (strtime3 + ":00");
            Log.d("time23232", strtime);
            updateTime(hours, minute);


        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            StringBuilder datestr1=new StringBuilder().append(year).append("/").append(month + 1).append("/").append(day);
            strdate=datestr1.toString();
            String convetdate1 = strdate + " " + strtime;

            SimpleDateFormat format3 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat format4 = new SimpleDateFormat("d MMM yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");

            try {
                Date dateNew = format3.parse(strdate);
                formatdate = format4.format(dateNew);

                Date date_conv = format2.parse(convetdate1);
                convetdate = format1.format(date_conv);

            } catch (ParseException e) {
                e.printStackTrace();
            }




            Date d2 = new Date(selectedYear, selectedMonth, selectedDay - 1);
            String dayOfTheWeek2 = sdf2.format(d2);

            day1.setText(dayOfTheWeek2);
            tvDisplayDate.setText(formatdate);
            weekday = day1.getText().toString();


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        removeActivity_CreateNewActivity=this;

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        timehr = (TextView) findViewById(R.id.timetxt);
        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        ampm = (TextView) findViewById(R.id.ampmtxt);
        day1 = (TextView) findViewById(R.id.daytxt);
        cancel = (TextView) findViewById(R.id.canceltxt);
        titleEdittext = (EditText) findViewById(R.id.edittext_meetuptitle);
        optionedittext = (EditText) findViewById(R.id.infotxt);
        locationeditext = (EditText) findViewById(R.id.location);
        invite = (TextView) findViewById(R.id.invitenewtxt);
        invite_one= (TextView) findViewById(R.id.textView22_invite);
        txt_meetups_title= (TextView) findViewById(R.id.textView22_title);
        invite.setEnabled(false);
        invite_one.setEnabled(false);

        GradientDrawable bgShape = (GradientDrawable)invite_one.getBackground();
        bgShape.mutate();
        bgShape.setColor(getResources().getColor(R.color.gray));

        invite.setBackgroundResource(R.color.gray);


        TextView invitenewtxt1 = (TextView) findViewById(R.id.invitenewtxt1);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) invitenewtxt1.getLayoutParams();
        params.height =invitenewtxt1.getHeight()+1500;
        invitenewtxt1.setLayoutParams(params);
        titleEdittext.requestFocus();


        if (obj_editmeetups !=null)
{
//    {"creatorfbid":"777369135751064",
//            "eventtitle":"sac test",
//            "eventdate":"2018-05-17 04:16:00",
//            "eventlocation":"must",
//            "createdate":"2018-05-10 10:47:32",
//            "eventdescription":"hdhdbdbd3",
//            "eventdateformat":"17th May 2018, 4:16am",
//            "fname":"sachin",
//            "gender":"Girl",
//            "profilepic":"http:\/\/play-date.ae\/app\/profileimages\/defaultgirl.jpg"}
    try
    {
        titleEdittext.setText(obj_editmeetups.getString("eventtitle"));
        locationeditext.setText(obj_editmeetups.getString("eventlocation"));
        optionedittext.setText(obj_editmeetups.getString("eventdescription"));
        String Str_date_editeventdate=obj_editmeetups.getString("eventdate");


        SimpleDateFormat format_editdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat format_editdate2 = new SimpleDateFormat("yyyy-MM-dd h:m:");


        Date editdate=null;
        Date editdate1=null;

        try {
            editdate = format_editdate.parse(Str_date_editeventdate);
            String str_date=format_editdate.format(editdate);
            editdate1 = format_editdate2.parse(str_date);




            date_editdate =editdate;//s sdf3.parse(Str_date_editeventdate);





        } catch (ParseException e) {
            e.printStackTrace();}

        invite.setText("Save");
        invite_one.setText("Save");
        txt_meetups_title.setText("Edit Play:Date");

        if (titleEdittext.getText().toString().length() < 1 || locationeditext.getText().toString().length()<1)
        {

            invite.setEnabled(false);
            invite.setBackgroundResource(R.color.gray);

            invite_one.setEnabled(false);
            GradientDrawable bgShape1 = (GradientDrawable)invite_one.getBackground();
            bgShape1.mutate();
            bgShape1.setColor(getResources().getColor(R.color.gray));


        } else {
            invite.setEnabled(true);
            invite.setBackgroundResource(R.color.Lightyellow);

            invite_one.setEnabled(true);
            GradientDrawable bgShape1 = (GradientDrawable)invite_one.getBackground();
            bgShape1.mutate();
            bgShape1.setColor(getResources().getColor(R.color.Lightyellow));
        }


    } catch (JSONException e) {
        e.printStackTrace();
    }



}


        setCurrentDateOnView();
        addListenerOnButton();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {






                InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (titleEdittext.length()!=0)
                {
                    titleEdittext.clearFocus();
                    in.hideSoftInputFromWindow(titleEdittext.getWindowToken(), 0);

                }
                else  if (locationeditext.length()!=0)
                {
                    locationeditext.clearFocus();
                    in.hideSoftInputFromWindow(locationeditext.getWindowToken(), 0);

                }
                else  if (optionedittext.length()!=0)
                {
                    optionedittext.clearFocus();
                    in.hideSoftInputFromWindow(optionedittext.getWindowToken(), 0);
                }
                else {
                    hideSoftKeyboard(CreateNewActivity.this);
                }

                finish();


            }
        });

        titleEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (titleEdittext.getText().toString().length() < 1 || locationeditext.getText().toString().length()<1)
                {

                    invite.setEnabled(false);
                    invite.setBackgroundResource(R.color.gray);

                    invite_one.setEnabled(false);
                    GradientDrawable bgShape1 = (GradientDrawable)invite_one.getBackground();
                    bgShape1.mutate();
                    bgShape1.setColor(getResources().getColor(R.color.gray));
                } else {
                    invite.setEnabled(true);
                    invite.setBackgroundResource(R.color.Lightyellow);

                    invite_one.setEnabled(true);
                    GradientDrawable bgShape1 = (GradientDrawable)invite_one.getBackground();
                    bgShape1.mutate();
                    bgShape1.setColor(getResources().getColor(R.color.Lightyellow));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }

        });



        locationeditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (titleEdittext.getText().toString().length() < 1 || locationeditext.getText().toString().length()<1)
                {

                    invite.setEnabled(false);
                    invite.setBackgroundResource(R.color.gray);
                    invite_one.setEnabled(false);
                    GradientDrawable bgShape1 = (GradientDrawable)invite_one.getBackground();
                    bgShape1.mutate();
                    bgShape1.setColor(getResources().getColor(R.color.gray));

                } else {
                    invite.setEnabled(true);
                    invite.setBackgroundResource(R.color.Lightyellow);

                    invite_one.setEnabled(true);
                    GradientDrawable bgShape1 = (GradientDrawable)invite_one.getBackground();
                    bgShape1.mutate();
                    bgShape1.setColor(getResources().getColor(R.color.Lightyellow));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        invite_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {








                try {

                    SimpleDateFormat format_editdate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
                    SimpleDateFormat format_editdate1 = new SimpleDateFormat("yyyy/MM/dd H:m");

                    Date editdate=null;
                    String date_editdate1="";


                    editdate = format_editdate.parse(convetdate);
                    date_editdate1 =format_editdate1.format(editdate);


                    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd H:m");
                    Date date3=new Date();

                    Date date2=sdf1.parse(date_editdate1);

                    String Str_DOb = sdf1.format(date3);

                  Date date1=sdf1.parse(Str_DOb);

                    if (date1.equals(date2))
                    {

                hideSoftKeyboard(CreateNewActivity.this);

                titileString=titleEdittext.getText().toString();
                locString=locationeditext.getText().toString();
                optionalString=optionedittext.getText().toString();

                if (obj_editmeetups !=null)
                {
                    progressDialog = new ProgressDialog(CreateNewActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Communication_EditmeetupEvent myTask = new Communication_EditmeetupEvent();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                    myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                else
                                    myTask.execute();


                                // Your implementation goes here
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
                else {
                    Intent i = new Intent(CreateNewActivity.this, SuggestFreindActivity.class);
                    SuggestFreindActivity.str_flag_event="createevent";
                    startActivity(i);
                }
                    }
                    else if (date1.after(date2))
                    {

                        Log.d("date1111_before",date1+"");
                        Log.d("date2222_before",date2+"");
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
                        builder1.setTitle("Oops");
                        builder1.setMessage("You have selected a Date or Time that has passed. Please change the Date or Time and try again.");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        // finish();
                                    }
                                });
                        alertDialog_Box = builder1.create();
                        alertDialog_Box.show();


                    }
                    else if (date1.before(date2))
                    {
                        hideSoftKeyboard(CreateNewActivity.this);

                        titileString=titleEdittext.getText().toString();
                        locString=locationeditext.getText().toString();
                        optionalString=optionedittext.getText().toString();

                        if (obj_editmeetups !=null)
                        {
                            progressDialog = new ProgressDialog(CreateNewActivity.this);
                            progressDialog.setMessage("Loading..."); // Setting Message
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            progressDialog.show(); // Display Progress Dialog
                            progressDialog.setCancelable(false);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        Communication_EditmeetupEvent myTask = new Communication_EditmeetupEvent();

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        else
                                            myTask.execute();


                                        // Your implementation goes here
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                        else {
                            Intent i = new Intent(CreateNewActivity.this, SuggestFreindActivity.class);
                            SuggestFreindActivity.str_flag_event="createevent";
                            startActivity(i);
                        }
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
                        builder1.setTitle("Oops");
                        builder1.setMessage("You have selected a Date or Time that has passed. Please change the Date or Time and try again.");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        // finish();
                                    }
                                });
                        alertDialog_Box = builder1.create();
                        alertDialog_Box.show();
                    }

                }catch (ParseException ex)
                {

                }


            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

try {
    SimpleDateFormat format_editdate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
    SimpleDateFormat format_editdate1 = new SimpleDateFormat("yyyy/MM/dd H:m");

    Date editdate=null;
    String date_editdate1="";


    editdate = format_editdate.parse(convetdate);
    date_editdate1 =format_editdate1.format(editdate);


    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd H:m");
    Date date3=new Date();

    Date date2=sdf1.parse(date_editdate1);

    String Str_DOb = sdf1.format(date3);

    Date date1=sdf1.parse(Str_DOb);


    if (date1.equals(date2))
    {
                hideSoftKeyboard(CreateNewActivity.this);

                titileString=titleEdittext.getText().toString();
                locString=locationeditext.getText().toString();
                optionalString=optionedittext.getText().toString();

                if (obj_editmeetups !=null)
                {
                    progressDialog = new ProgressDialog(CreateNewActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Communication_EditmeetupEvent myTask = new Communication_EditmeetupEvent();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                    myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                else
                                    myTask.execute();


                                // Your implementation goes here
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
                else {
                    Intent i = new Intent(CreateNewActivity.this, SuggestFreindActivity.class);
                    SuggestFreindActivity.str_flag_event="createevent";
                    startActivity(i);
                }
    }
    else if (date1.after(date2))
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("You have selected a Date or Time that has passed. Please change the Date or Time and try again.");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // finish();
                    }
                });
        alertDialog_Box = builder1.create();
        alertDialog_Box.show();

    }
    else if (date1.before(date2))
    {

        hideSoftKeyboard(CreateNewActivity.this);

        titileString=titleEdittext.getText().toString();
        locString=locationeditext.getText().toString();
        optionalString=optionedittext.getText().toString();

        if (obj_editmeetups !=null)
        {
            progressDialog = new ProgressDialog(CreateNewActivity.this);
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Communication_EditmeetupEvent myTask = new Communication_EditmeetupEvent();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else
                            myTask.execute();


                        // Your implementation goes here
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
        else {
            Intent i = new Intent(CreateNewActivity.this, SuggestFreindActivity.class);
            SuggestFreindActivity.str_flag_event="createevent";
            startActivity(i);
        }
    }
    else
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("You have selected a Date or Time that has passed. Please change the Date or Time and try again.");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // finish();
                    }
                });
        alertDialog_Box = builder1.create();
        alertDialog_Box.show();
    }

}catch (ParseException ex)
{

}



            }
        });


        timehr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);
            }
        });

        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);
            }
        });
        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showDialog(DATE_DIALOG_ID);
            }
        });

    }
    // display current date
    public void setCurrentDateOnView() {



         Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format2 = new SimpleDateFormat("d MMM yyyy");
        SimpleDateFormat format3 = new SimpleDateFormat("hh:mm aa");

        SimpleDateFormat format11 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
        SimpleDateFormat format22 = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");









        if (date_editdate !=null)
        {
            c.setTime(date_editdate);
            hours=date_editdate.getHours();
            minute=date_editdate.getMinutes();
        }
        else
        {

            Date currentdate = new Date();
            c.setTime(currentdate);
            hours=currentdate.getHours();
            minute=currentdate.getMinutes();
//            hours=c.get(Calendar.HOUR);
//            minute=c.get(Calendar.MINUTE);

        }
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        int dayofweek = c.get(Calendar.DAY_OF_WEEK);



        // set current date into textview
        StringBuilder datestr1=new StringBuilder().append(year).append("/").append(month + 1).append("/").append(day);
        strdate=datestr1.toString();


        String dayOfTheWeek2 = sdf1.format(c.getTime());
        String currentDateTimeString= format3.format(c.getTime());
        strtime=currentDateTimeString;

        String convetdate1 = strdate + " " + strtime;

        try {
            Date date_new = format1.parse(strdate);
            formatdate = format2.format(date_new);

            Date date_conv = format22.parse(convetdate1);
            convetdate = format11.format(date_conv);



        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvDisplayDate.setText(formatdate);
        day1.setText(dayOfTheWeek2);
        timehr.setText(currentDateTimeString);






    }

    public void addListenerOnButton()
    {


    }
 @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date

            {

                 DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, year, month,
                        day) {
                    @Override
                    public void onDateChanged(DatePicker view, int myear, int monthOfYear, int dayOfMonth)
                    {

                    }


                };
                _date.getDatePicker().setMinDate(System.currentTimeMillis());

                Calendar cal = Calendar.getInstance();

                cal.add(Calendar.YEAR, 0);

                _date.getDatePicker().setMinDate(cal.getTimeInMillis());

                return _date;




            }
            case TIME_DIALOG_ID: {

                return new TimePickerDialog(this, timePickerListener, hours, minute, false);

            }
        }
        return null;
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        StringBuilder aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet);

        timehr.setText(aTime);
        strtime = timehr.getText().toString();

        String convetdate1 = strdate + " " + strtime;

        SimpleDateFormat format3 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format4 = new SimpleDateFormat("d MMM yyyy");

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");

        try {
            Date dateNew = format3.parse(strdate);
            formatdate = format4.format(dateNew);

            Date date_conv = format2.parse(convetdate1);
            convetdate = format1.format(date_conv);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //php edit meetup event....

    public class Communication_EditmeetupEvent extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.editevent);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("title", titileString);
                postDataParams.put("location", locString);
                postDataParams.put("description", optionalString);

                SimpleDateFormat format_editdate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
                SimpleDateFormat format_editdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");


                Date editdate=null;
                String date_editdate="";

                try {
                    editdate = format_editdate.parse(convetdate);
                    date_editdate =format_editdate1.format(editdate);

                } catch (ParseException e) {
                    e.printStackTrace();}


                postDataParams.put("eventdate", date_editdate);
                postDataParams.put("eventid", str_editeventid);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {

                Log.d("Exception_php", e.getMessage() + "");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();


            if (result != null)
            {



                if (result.equalsIgnoreCase("nullerror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Your Facebook Account Id seems to be absent. Please login and try again.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                }
                else if (result.equalsIgnoreCase("noeventid"))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("noeventid.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                }

                else if (result.equalsIgnoreCase("inserterror"))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("The server encountered an error and your Play:Date could not be created. Please try again.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                } else if (result.equalsIgnoreCase("expired"))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateNewActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Meetups date has been expired. please checkup your enter date and time.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                }
                else if (result.equalsIgnoreCase("done"))

                {

                    finish();
                    Intent intent1 = new Intent("update_editmeetup");
                    LocalBroadcastManager.getInstance(CreateNewActivity.this).sendBroadcast(intent1);

                }
                else

                {

                    progressDialog.dismiss();
                    AlertDialog.Builder builder31 = new AlertDialog.Builder(CreateNewActivity.this);
                    builder31.setTitle("Share it!");
                    builder31.setMessage("Since you have not invited any in-app friends, you can share this meet-up with your other friends by going into settings.");
                    builder31.setCancelable(false);
                    builder31.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    CreateNewActivity.removeActivity_CreateNewActivity.finish();;
                                    finish();
                                }
                            });
                    alertDialog_Box = builder31.create();
                    alertDialog_Box.show();
                }
            }
        }
    }
    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}