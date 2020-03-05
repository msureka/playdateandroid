package com.bba.playdate1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditmeetActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 1111;
    public static String titile3;
    public static String locString3;
    public static String optionalString3;
    public static String strdate3, strtime11, editdate,Eventid;
    EditText titleEdittext, locationeditext, optionedittext;
    ProgressDialog progressBar;
    String ChatResult;
    TextView timehr, ampm, day1;
    private TextView tvDisplayDate, save, cancel;
    private DatePicker dpResult;
    private Button btnChangeDate;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    // private int day;
    Intent in;
    Bundle bu;
    private int year;
    private int month;
    private int day;
    private int hours, minute, second;
    String day33,time33;

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
            String strtime1 = timehr.getText().toString();
            strtime11 = (strtime11 + ":00");
            Log.d("time23232", strtime11);
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
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("/").append(day).append("/").append(year)
                    .append(" "));

            strdate3 = tvDisplayDate.getText().toString();
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
            Date d2 = new Date(selectedYear, selectedMonth, selectedDay - 1);
            String dayOfTheWeek2 = sdf2.format(d2);
            day1.setText(dayOfTheWeek2);

            Log.d("jjjjjjjj11", strdate3);
            Log.d("dayjjjjjjjj11", day1 + "");
            // set selected date into datepicker also
            //  dpResult.init(year, month, day, null);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmeet);


        timehr = (TextView) findViewById(R.id.timetxt);

        day1 = (TextView) findViewById(R.id.daytxt);
        cancel = (TextView) findViewById(R.id.canceltxt);
        titleEdittext = (EditText) findViewById(R.id.edittext_meetuptitle);
        optionedittext = (EditText) findViewById(R.id.infotxt);
        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        locationeditext = (EditText) findViewById(R.id.location);
        save = (TextView) findViewById(R.id.savenewtxt);
        TextView save1 = (TextView) findViewById(R.id.textView22);



        try {
            in = getIntent();
            bu = in.getExtras();
            Eventid = bu.getString("eventid");
            titile3 = bu.getString("eventtitle");
            locString3 = bu.getString("location");
            strdate3 = bu.getString("eventdate");
            optionalString3 = bu.getString("eventdescription");
    } catch (Exception e) {
        }

////current date time
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("d MMM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        SimpleDateFormat format3 = new SimpleDateFormat("hh:mm a");
        String date11 = strdate3;
        try {
            Date dateNew = format1.parse(date11);
            editdate = format2.format(dateNew);
            time33 = format3.format(dateNew);
            day33=sdf2.format(dateNew);
            Log.d("time33editdate", time33);
            Log.d("day33editdate", day33);
            System.out.println(editdate);
            Log.d("editdate", editdate);
        } catch (ParseException e) {
            e.printStackTrace();}

        tvDisplayDate.setText(editdate);
        titleEdittext.setText(titile3);
        locationeditext.setText(locString3);
        optionedittext.setText(optionalString3);
        timehr.setText(time33);
        day1.setText(day33);


        titleEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                titile3 = titleEdittext.getText().toString();
                Log.d("vhngeeeee", titile3);
            }
        });
        tvDisplayDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                strdate3 = tvDisplayDate.getText().toString();
                Log.d("vhngeeeeestrdate3", strdate3);
            }
        });
        locationeditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                locString3 = locationeditext.getText().toString();
                Log.d("vhngeeeeestrdate6", locString3);
            }
        });
        optionedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                optionalString3 = optionedittext.getText().toString();
                Log.d("vhngeeeeestrdate4", optionalString3);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(EditmeetActivity.this, R.style.MyTheme1);
                progressBar.setCancelable(true);
                progressBar.setMessage("Meet-ups editing...");
                progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                progressBar.setCanceledOnTouchOutside(true);

                //reset progress bar status
                progressBarStatus = 0;

                //reset filesize

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {

                            // process some tasks
                            // call function to get chat
                            //php called in this function.in background
                            progressBarStatus = doSomeTask();

                            // your computer is too fast, sleep 1 second
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    if (Thread.currentThread() != null) {
                                        // ok, Php executed,
                                        if (progressBarStatus >= 100) {
                                            //hide progressbar
                                            progressBar.dismiss();

                                            if (ChatResult.equalsIgnoreCase("nullerror")) {

                                               // Toast.makeText(getApplicationContext(), "nullerror", Toast.LENGTH_LONG).show();
                                            } else if (ChatResult.equalsIgnoreCase("noeventid")) {

                                              //  Toast.makeText(getApplicationContext(), "noeventid ", Toast.LENGTH_LONG).show();
                                            } else if (ChatResult.equalsIgnoreCase("inserterror")) {

                                              //  Toast.makeText(getApplicationContext(), "inserterror ", Toast.LENGTH_LONG).show();
                                            } else {
                                                //Got caht in Joson Decode json data\
                                                //Toast.makeText(getActivity().getApplicationContext()," got data ",Toast.LENGTH_LONG).show();                 try {
                                                try {
                                                    JSONArray jsonarray = new JSONArray(ChatResult);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                finish();

                                            }
                                        }

                                    }
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }


                    }
                }).start();
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

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date

            {

                final DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, year, month,
                        day) {


                    @Override
                    public void onDateChanged(DatePicker view, int myear, int monthOfYear, int dayOfMonth) {
                        if (myear < year)
                            view.updateDate(year, month, day);

                        if (monthOfYear < month && year == myear)
                            view.updateDate(year, month, day);

                        if (dayOfMonth < day && myear == year && monthOfYear == month)
                            view.updateDate(year, month, day);

                    }
                };
                return _date;
            }
            case TIME_DIALOG_ID: {
                return new TimePickerDialog(this, timePickerListener, hours, minute,
                        true);


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
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        timehr.setText(aTime);
        String strtime1 = timehr.getText().toString();
        strtime11 = (strtime11 + ":00");
        Log.d("time23232", strtime11);
    }

    public int doSomeTask() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //call getchat function
                ChatResult = getChat();

            }

            @Override
            protected Void doInBackground(final Void... params) {

                return null;
            }

            @Override
            protected void onPostExecute(final Void result) {


            }
        }.execute();

        return 100;
    }

    public String getChat() {


        String text = "";
        BufferedReader reader = null;
        // Send data
        try {
            //    String date=(strdate3+" "+strtime11);

            // Toast.makeText(getApplicationContext(),"in register user",Toast.LENGTH_SHORT).show();
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(UrlClass.editevent);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("fbid", ""));
            nameValuePair.add(new BasicNameValuePair("title", titile3));
            nameValuePair.add(new BasicNameValuePair("eventdate", strdate3));
            nameValuePair.add(new BasicNameValuePair("description", optionalString3));
            nameValuePair.add(new BasicNameValuePair("location", locString3));
            nameValuePair.add(new BasicNameValuePair("eventid",Eventid));

            Log.e("edit meet data", nameValuePair.toString());


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.e("edidevent Response", "In Friend: " + responseStr);
                    text = responseStr;

                    // you can add an if statement here and do other actions based on the response
                }

            } catch (UnsupportedEncodingException e) {
                // writing error to Log
                text = "neterror";
                //loading.dismiss();
                // Toast.makeText(getApplicationContext(),"in register user catch",Toast.LENGTH_SHORT).show();
                Log.e("in catch 1", e.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {
            // writing exception to log
            Log.e("in catch 1", e.toString());
            //Toast.makeText(getApplicationContext(),"in register user catch 2",Toast.LENGTH_SHORT).show();
            text = "neterror";
            //loading.dismiss();

        }
        return text;
    }


}