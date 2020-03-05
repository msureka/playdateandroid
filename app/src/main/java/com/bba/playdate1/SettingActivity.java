package com.bba.playdate1;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

//import android.icu.text.SimpleDateFormat;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by spielmohitp on 2/24/2017.
 */
public class SettingActivity extends AppCompatActivity {

    private static final Location TODO = null;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public static String Name = "";
    public static String BirthDate = "";
    public static String Language1 = "";
    public static String Language2 = "";
    public static String Language3 = "";
    public static String Desc = "";
    public static String Gender ="";
    public static String TalkTo = "";
    public static String HowOld = "";
    public static String Where = "";
    public static String Matchswith = "";
    public static String Messageswith ="";
    public static String str_suprtheroSave ="yes";
    public static String fage;

    public static String questionflag="yes";
    public static String whereflag="yes";


    ProgressDialog progressDialog;
    public String RestoreBool;
    TextView DoneTxt, MakefrndwidValue, Allagealue, Wherevalue, NameValue, GenderValue, BirthdateValue, LangugeValue,text_version;
    SwitchCompat MatchesSwitch, MessageSwitch;
    Intent intent;
    RelativeLayout MakeFrndRow, HowOldRow, WhereRow, NameRow, GenderRow, BirthDateRow, ISpeakRow, DescriptionRow, ProfileCardRow, PushMatchRow, PushMessagesRow, InviteFrndRow, LogOutRow, DeleteRow, TermsRow, PrivacyRow;
    SharedPreferences sharedpreferences;

    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    android.support.v7.app.AlertDialog alertDialog_Box;
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



       // io.github.rockerhieu.emojicon.EmojiconTextView

        //FB Initialize
        FacebookSdk.sdkInitialize(getApplicationContext());

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        LocalBroadcastManager.getInstance(this).registerReceiver(UpdateInformation,
                new IntentFilter("registerinfo_setting"));


        DoneTxt = (TextView) findViewById(R.id.donetxt);
        MakeFrndRow = (RelativeLayout) findViewById(R.id.make_frnd_lable);
        HowOldRow = (RelativeLayout) findViewById(R.id.how_old_lable);
        WhereRow = (RelativeLayout) findViewById(R.id.where_lable);
        NameRow = (RelativeLayout) findViewById(R.id.name_lable);
        GenderRow = (RelativeLayout) findViewById(R.id.gender_lable);
        BirthDateRow = (RelativeLayout) findViewById(R.id.birth_lable);
        ISpeakRow = (RelativeLayout) findViewById(R.id.speak_lable);
        DescriptionRow = (RelativeLayout) findViewById(R.id.desc_lable);
        ProfileCardRow = (RelativeLayout) findViewById(R.id.cards_lable);
        PushMatchRow = (RelativeLayout) findViewById(R.id.matches_lable);
        PushMessagesRow = (RelativeLayout) findViewById(R.id.message_lable);
        InviteFrndRow = (RelativeLayout) findViewById(R.id.invite_lable);
        LogOutRow = (RelativeLayout) findViewById(R.id.logout_lable);
        DeleteRow = (RelativeLayout) findViewById(R.id.delete_lable);
        TermsRow = (RelativeLayout) findViewById(R.id.term_lable);
        PrivacyRow = (RelativeLayout) findViewById(R.id.privacy_lable);
        MakefrndwidValue = (TextView) findViewById(R.id.boy_girl_txt);
        Allagealue = (TextView) findViewById(R.id.age_txt);
        Wherevalue = (TextView) findViewById(R.id.city_txt);
        NameValue = (TextView) findViewById(R.id.name_value_txt);
        GenderValue = (TextView) findViewById(R.id.gender_value_txt);
        BirthdateValue = (TextView) findViewById(R.id.birth_value_txt);
        LangugeValue = (TextView) findViewById(R.id.speak_value_txt);
        text_version=(TextView)findViewById(R.id.info_txt_setting);

        MatchesSwitch = (SwitchCompat) findViewById(R.id.matches_switch);
        MessageSwitch = (SwitchCompat) findViewById(R.id.message_switch);




        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
          String  Str_appVersonName = "Play:Date v"+pInfo.versionName.toString() +"."+ String.valueOf(pInfo.versionCode);
            text_version.setText(Str_appVersonName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




        TalkTo=pref.getString("makefriendswith","");
        HowOld=pref.getString("agegroup","");
        Where=pref.getString("distance","");
        Gender=pref.getString("gender","");
        Name=pref.getString("fname","");
        Matchswith= pref.getString("pushmatch","");
        Messageswith=pref.getString("pushmessage","");
        Desc=pref.getString("description","");
        Language1=pref.getString("language","");
        BirthDate=pref.getString("birthdate","");
        PlayProfileActivity.LikeToPlayString=pref.getString("liketoplay","");
        SuperHeroProfileActivity.Str_superHero=pref.getString("superhero","");
        FavroiteProfileActivity.Activity1=pref.getString("activity1","");
        FavroiteProfileActivity.Activity2=pref.getString("activity2","");
        FavroiteProfileActivity.Activity3=pref.getString("activity3","");
        MeetProfileActivity.LikeToMeetString=pref.getString("icanmeet","");


        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format1.parse(BirthDate);
            String Str_DOb = format2.format(date);
            BirthdateValue.setText(Str_DOb);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (Language1 !=null ||! Language1.equalsIgnoreCase(""))
        {
            try {
                JSONArray array=new JSONArray(Language1);
                if (array.length()==3)
                {
                    String concatStr=array.get(0)+","+array.get(1)+","+array.get(2);
                    LangugeValue.setText(concatStr);
                }
                if (array.length()==2)
                {
                    String concatStr=array.get(0)+","+array.get(1);
                    LangugeValue.setText(concatStr);
                }
                if (array.length()==1)
                {
                    String concatStr= (String)array.get(0);
                    LangugeValue.setText(concatStr);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            LangugeValue.setText("");
        }

        NameValue.setText(Name);

        if (TalkTo.equalsIgnoreCase("BOY")) {
            MakefrndwidValue.setText("Boy \uE001");
        } else if (TalkTo.equalsIgnoreCase("GIRL")) {
            MakefrndwidValue.setText("Girl \uE002");
        } else
            {
            MakefrndwidValue.setText("Boys & Girls \uE001\uE002");
        }

        //Age group default value
        if (HowOld.equalsIgnoreCase("2")) {
            Allagealue.setText("All ages");
        } else if (HowOld.equalsIgnoreCase("1")) {
            Allagealue.setText("2 year older/younger to me");
        } else {
            Allagealue.setText("Same as mine!");
        }


        //Where default value
        if (Where.equalsIgnoreCase("CITY")) {
            Wherevalue.setText("My city only");
        } else if (Where.equalsIgnoreCase("COUNTRY")) {
            Wherevalue.setText("My Country");
        } else {
            Wherevalue.setText("Any where in the world");
        }


        if (Gender.equalsIgnoreCase("Boy")) {
            GenderValue.setText("Boy \uE001");
        } else {
            GenderValue.setText("Girl \uE002");
        }

        if (Matchswith.equalsIgnoreCase("ON"))
        {
            MatchesSwitch.setChecked(true);
        }
        else
        {
            MatchesSwitch.setChecked(false);
        }

        if (Messageswith.equalsIgnoreCase("ON"))
        {
        MessageSwitch.setChecked(true);
        }
        else
        {
            MessageSwitch.setChecked(false);
        }


        DoneTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog = new ProgressDialog(SettingActivity.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //  sampleUserNameString = emai;
                            new Communication_updateprofile().execute();

                            // Your implementation goes here
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();


            }
        });

        NameRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, NameActivity.class);
                intent.putExtra("name_row","yes");
                startActivity(intent);

            }
        });

        BirthDateRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, BirthDateActivity.class);
                intent.putExtra("dob_row","yes");
                startActivity(intent);

            }
        });

        ISpeakRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, LanguagesNewActivity.class);
                intent.putExtra("speak_row","yes");
                startActivity(intent);
            }
        });

        DescriptionRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, AboutMeActivity.class);
                intent.putExtra("about_row","yes");
                startActivity(intent);


            }
        });

        ProfileCardRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               str_suprtheroSave ="yes";
                intent = new Intent(SettingActivity.this, PlayProfileActivity.class);
                intent.putExtra("playprofile_row","yes");
                startActivity(intent);

            }
        });

        GenderRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, GenderActivity.class);
                startActivity(intent);

            }
        });

        MakeFrndRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, TalkToActivity.class);
                startActivity(intent);


            }
        });

        HowOldRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, HowOldActivty.class);
                startActivity(intent);


            }
        });

        WhereRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.whereflag="yes";
                intent = new Intent(SettingActivity.this, WhereActivity.class);
                startActivity(intent);



            }
        });

        TermsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, TermsActivity.class);
                startActivity(intent);


            }
        });


        MatchesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (MatchesSwitch.isChecked()) {
                    Matchswith = "ON";
                }
                else {
                    Matchswith = "OFF";
                }
            }
        });



        MessageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (MessageSwitch.isChecked()) {
                    Messageswith = "ON";
                }
                else
                    {
                        Messageswith = "OFF";
                }
            }
        });

        PrivacyRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SettingActivity.this, PrivacyActivity.class);
                startActivity(intent);


            }
        });


        InviteFrndRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,"serveroverloadofficial@gmail.com");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download Play:Date");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, \n\nPlay:Date is a grate app to find friends for your childern.i have been using it scince while, and it whould be great if you could download it! \n\nVisit http://www.play-date.ae to download it onn your mobile phone! \n\nThanks!");


                emailIntent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                   /* Toast.makeText(getApplicationContext(),
                            "No email clients installed.",
                           // Toast.LENGTH_SHORT).show();*/
                }

            }
        });

        LogOutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                File sharedPreferenceFile = new File("/data/data/"+ getPackageName()+ "/shared_prefs/");
                File[] listFiles = sharedPreferenceFile.listFiles();
                for (File file : listFiles) {
                    file.delete();
                }

                LoginManager.getInstance().logOut();
                editor.clear();
                editor.clear();
                editor.putString("Loginplay","no");
                editor.commit();
                editor.putString("leftswipe","yes");
                editor.commit();
                Intent intent1 = new Intent(SettingActivity.this, WelcomeActivity.class);
                startActivity(intent1);


            }
        });

        DeleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //deleteAccount show Alert
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you really want to delete your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                progressDialog = new ProgressDialog(SettingActivity.this);
                                progressDialog.setMessage("Deleting..."); // Setting Message
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                progressDialog.show(); // Display Progress Dialog
                                progressDialog.setCancelable(false);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            //  sampleUserNameString = emai;
                                            new Communication_deleteaccount().execute();

                                            // Your implementation goes here
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }).start();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
        location_permission_checked();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }



    private BroadcastReceiver UpdateInformation = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = format1.parse(BirthDate);
                String Str_DOb = format2.format(date);
                BirthdateValue.setText(Str_DOb);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (Language1 !=null ||! Language1.equalsIgnoreCase(""))
            {
                try {
                    JSONArray array=new JSONArray(Language1);
                    if (array.length()==3)
                    {
                        String concatStr=array.get(0)+","+array.get(1)+","+array.get(2);
                        LangugeValue.setText(concatStr);
                    }
                    if (array.length()==2)
                    {
                        String concatStr=array.get(0)+","+array.get(1);
                        LangugeValue.setText(concatStr);
                    }
                    if (array.length()==1)
                    {
                        String concatStr= (String)array.get(0);
                        LangugeValue.setText(concatStr);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                LangugeValue.setText("");
            }

            NameValue.setText(Name);

            if (TalkTo.equalsIgnoreCase("BOY"))
            {
                MakefrndwidValue.setText("Boy \uE001");
            }
            else if (TalkTo.equalsIgnoreCase("GIRL"))
            {
                MakefrndwidValue.setText("Girl \uE002");
            }
            else
            {
                MakefrndwidValue.setText("Boys & Girls \uE001\uE002");
            }
            if (HowOld.equalsIgnoreCase("2")) {
                Allagealue.setText("All ages");
            } else if (HowOld.equalsIgnoreCase("1")) {
                Allagealue.setText("2 year older/younger to me");
            } else {
                Allagealue.setText("Same as mine!");
            }


            //Where default value
            if (Where.equalsIgnoreCase("CITY"))
            {
                Wherevalue.setText("My city only");
            }
            else if (Where.equalsIgnoreCase("COUNTRY")) {
                Wherevalue.setText("My Country");
            }
            else
            {
                Wherevalue.setText("Any where in the world");
            }


            if (Gender.equalsIgnoreCase("Boy"))
            {
                GenderValue.setText("Boy \uE001");
            } else
            {
                GenderValue.setText("Girl \uE002");
            }

        }
    };

    public class  Communication_updateprofile extends AsyncTask<String, Void, String>  {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {


                String Str_Lang=Language1;



                URL url = new URL(UrlClass.updateprofile);
                JSONObject postDataParams = new JSONObject();


                String Str_Lang_Eng="",Str_Lang_Arb="",Str_Lang_Fre="";



                if (Str_Lang !=null ||! Str_Lang.equalsIgnoreCase("")) {
                    try {
                        JSONArray array = new JSONArray(Str_Lang);
                        if (array.length() == 3) {
                            Str_Lang_Eng = String.valueOf(array.get(0));
                            Str_Lang_Arb = String.valueOf(array.get(1));
                            Str_Lang_Fre = String.valueOf(array.get(2));
                        }

                        if (array.length() == 2) {
                            Str_Lang_Eng = String.valueOf(array.get(0));
                            Str_Lang_Arb = String.valueOf(array.get(1));
                            Str_Lang_Fre = "";
                        }
                        if (array.length() == 1) {
                            Str_Lang_Eng = String.valueOf(array.get(0));
                            Str_Lang_Arb = " ";
                            Str_Lang_Fre = "";
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                postDataParams.put("fbid",pref.getString("userid",""));
                postDataParams.put("fname",SettingActivity.Name);
                postDataParams.put("lname","");
                postDataParams.put("gender",SettingActivity.Gender);


                postDataParams.put("birthdate",BirthDate);

                postDataParams.put("city",CityName);
                postDataParams.put("country",CountryName);

                postDataParams.put("lang1",Str_Lang_Eng);
                postDataParams.put("lang2",Str_Lang_Arb);
                postDataParams.put("lang3",Str_Lang_Fre);
                postDataParams.put("description",SettingActivity.Desc);
                postDataParams.put("liketoplay",PlayProfileActivity.LikeToPlayString);
                postDataParams.put("icanmeet",MeetProfileActivity.LikeToMeetString);
                postDataParams.put("activity1",FavroiteProfileActivity.Activity1);
                postDataParams.put("activity2",FavroiteProfileActivity.Activity2);
                postDataParams.put("activity3",FavroiteProfileActivity.Activity3);
                postDataParams.put("superhero",SuperHeroProfileActivity.Str_superHero);
                postDataParams.put("pushmatch",Matchswith);
                postDataParams.put("pushmessage",Messageswith);
                postDataParams.put("agegroup",HowOld);
                postDataParams.put("distance",Where);
                postDataParams.put("makefriendswith",TalkTo);


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

                Log.d("Exception_php", e.getMessage()+"");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            JSONArray jsonarray=null;
            JSONObject Jsonobject=null;
            try {


if (result !=null) {

    if (result.equalsIgnoreCase("nullerror")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
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


    } else if (result.equalsIgnoreCase("updateerror")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("Error in updating your account. Please try again.");
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


    } else if (result.equalsIgnoreCase("error")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
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


    } else if (result.equalsIgnoreCase("selecterror")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("Could not retrieve your Facebook Account Id. Please login and try again.");
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


    } else if (result.equalsIgnoreCase("nofbid")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("Your account does not exist or it has been de-activated by the administrator. Please contact us at support@play-date.ae");
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


    } else if (result.equalsIgnoreCase("nameerror")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("You seem to have used some special characters in your child's name. Please use alphabets/numbers only, and try again.");
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


    } else if (result.equalsIgnoreCase("descriptionerror")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("You seem to have used some special characters in your profile description. Please use alphabets/numbers only, and try again.");
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


    } else if (result.equalsIgnoreCase("superheroerror")) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
        builder1.setTitle("Oops");
        builder1.setMessage("You seem to have used some special characters while entering your favourite superhero. Please use alphabets/numbers only, and try again.");
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
    else
    {

            jsonarray = new JSONArray(result);
            Jsonobject = jsonarray.getJSONObject(0);

            editor.putString("fname",Jsonobject.getString("fname"));
            editor.putString("emailid",Jsonobject.getString("emailid"));
            editor.putString("gender",Jsonobject.getString("gender"));
            editor.putString("activity1",Jsonobject.getString("activity1"));
            editor.putString("activity2",Jsonobject.getString("activity2"));
            editor.putString("activity3",Jsonobject.getString("activity3"));
            editor.putString("agegroup",Jsonobject.getString("agegroup"));
            editor.putString("pushmatch",Jsonobject.getString("pushmatch"));
            editor.putString("pushmessage",Jsonobject.getString("pushmessage"));
            editor.putString("birthdate",Jsonobject.getString("birthdate"));
            editor.putString("city",Jsonobject.getString("city"));
            editor.putString("country",Jsonobject.getString("country"));
            editor.putString("description",Jsonobject.getString("description"));
            editor.putString("distance",Jsonobject.getString("distance"));
            editor.putString("emoji1",Jsonobject.getString("emoji1"));
            editor.putString("emoji2",Jsonobject.getString("emoji2"));
            editor.putString("emoji3",Jsonobject.getString("emoji3"));
            editor.putString("icanmeet",Jsonobject.getString("icanmeet"));
            editor.putString("liketoplay",Jsonobject.getString("liketoplay"));
            editor.putString("makefriendswith",Jsonobject.getString("makefriendswith"));
            editor.putString("age",Jsonobject.getString("age"));
            editor.putString("superhero",Jsonobject.getString("superhero"));
            editor.putString("englang",Jsonobject.getString("englang"));
            editor.putString("frenchlang",Jsonobject.getString("frenchlang"));
            editor.putString("arabiclang",Jsonobject.getString("arabiclang"));

            ArrayList<String> Array_Languge_three = new ArrayList<>();

            if (!Jsonobject.getString("englang").equalsIgnoreCase(""))
            {
                Array_Languge_three.add(Jsonobject.getString("englang"));
            }
            if (!Jsonobject.getString("frenchlang").equalsIgnoreCase(""))
            {
                Array_Languge_three.add(Jsonobject.getString("frenchlang"));
            }
            if (!Jsonobject.getString("arabiclang").equalsIgnoreCase(""))
            {
                Array_Languge_three.add(Jsonobject.getString("arabiclang"));
            }

            JSONArray Array_lang=new JSONArray(Array_Languge_three);
            editor.putString("language",Array_lang.toString());
            editor.commit();


            progressDialog.dismiss();

        Intent intent1 = new Intent("updateprofile_info");
        LocalBroadcastManager.getInstance(SettingActivity.this).sendBroadcast(intent1);
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);



    }
}
                else
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Unknown error reported from server. Please contact support@play-date.ae.");
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    //Delete Account Urls............

    public class  Communication_deleteaccount extends AsyncTask<String, Void, String>  {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {





                URL url = new URL(UrlClass.deleteaccount);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid",pref.getString("userid",""));

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

                Log.d("Exception_php", e.getMessage()+"");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();


            if (result != null) {

                if (result.equalsIgnoreCase("nullerror")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
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


                } else if (result.equalsIgnoreCase("deleted")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                    builder1.setTitle("Deleted");
                    builder1.setMessage("Your Play:Date account has been successfully deleted.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();


                                    File sharedPreferenceFile = new File("/data/data/"+ getPackageName()+ "/shared_prefs/");
                                    File[] listFiles = sharedPreferenceFile.listFiles();
                                    for (File file : listFiles) {
                                        file.delete();
                                    }

                                    LoginManager.getInstance().logOut();
                                    editor.clear();
                                    editor.clear();
                                    editor.putString("Loginplay","no");
                                    editor.commit();
                                    editor.putString("leftswipe","no");
                                    editor.commit();
                                    Intent intent1 = new Intent(SettingActivity.this, WelcomeActivity.class);
                                    startActivity(intent1);


                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                }
                else if (result.equalsIgnoreCase("selecterror")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Could not retrieve your Facebook Account Id. Please login and try again.");
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
                else if (result.equalsIgnoreCase("error")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
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


                }  else if (result.equalsIgnoreCase("nofbid")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Your account does not exist or it has been de-activated by the administrator. Please contact us at support@play-date.ae");
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


                }  else {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                    builder1.setTitle("Deleted");
                    builder1.setMessage("Your Play:Date account has been successfully deleted.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();


                                    File sharedPreferenceFile = new File("/data/data/"+ getPackageName()+ "/shared_prefs/");
                                    File[] listFiles = sharedPreferenceFile.listFiles();
                                    for (File file : listFiles) {
                                        file.delete();
                                    }

                                    LoginManager.getInstance().logOut();
                                    editor.clear();
                                    editor.clear();
                                    editor.putString("Loginplay","no");
                                    editor.commit();
                                    Intent intent1 = new Intent(SettingActivity.this, WelcomeActivity.class);
                                    startActivity(intent1);


                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                }
            } else {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                builder1.setTitle("Oops");
                builder1.setMessage("Unknown error reported from server. Please contact support@play-date.ae.");
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


        }

    }


    //For all urls PostData converted.....

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

    //location acces....

    void location_permission_checked()

    {
        //LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Location Permission");
                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (ActivityCompat.checkSelfPermission(SettingActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {



                            ActivityCompat.requestPermissions(SettingActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }

                    }
                });
                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);}});
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            } else {

                getMyCurrentLocation();
            }
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsProviderEnabled, isNetworkProviderEnabled;
            isGpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGpsProviderEnabled && !isNetworkProviderEnabled) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Location sachin Permission");
                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            } else {

                getMyCurrentLocation();
            }
        }
    }

//    {
//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
//            {
//                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//                builder.setTitle("Location Permission");
//                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
//                builder.setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener()
//                {
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override public void onClick(DialogInterface dialogInterface,int i)
//                    {requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);}});
//                builder.setNegativeButton(android.R.string.no, null);
//                builder.show();
//            }
//            else
//            {
//
//                getMyCurrentLocation();
//            }
//        }
//        else
//        {
//            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//            boolean isGpsProviderEnabled, isNetworkProviderEnabled;
//            isGpsProviderEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            if(!isGpsProviderEnabled && !isNetworkProviderEnabled){
//                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//                builder.setTitle("Location Permission");
//                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
//                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i){
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                    }});
//                builder.setNegativeButton(android.R.string.no, null);
//                builder.show();
//            }
//            else
//            {
//
//                getMyCurrentLocation();
//            }
//        }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d(GraphRequest.TAG, "coarse location permission granted");

                    getMyCurrentLocation();


                }
            }
        }
    }

    /** Check the type of GPS Provider available at that instance and  collect the location informations
     @Output Latitude and Longitude
      * */
    void getMyCurrentLocation() {

        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        //don't start listeners if no provider is enabled

        //if(!gps_enabled && !network_enabled)

        //return false;

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

        }


        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        if (network_enabled && location == null) {

            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        }
        if (network_enabled && location == null) {
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

        if (location != null) {

            MyLat = location.getLatitude();
            MyLong = location.getLongitude();
        } else {
            Location loc = getLastKnownLocation(this);
            if (loc != null) {
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
            }
        }

        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to avoid battery drainage. If you want to get location at the periodic intervals call this method using pending intent.

        try {
// Getting address from found locations.
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
            StateName = addresses.get(0).getAdminArea();
            CityName = addresses.get(0).getLocality();
            CountryName = addresses.get(0).getCountryName();
            // you can get more details other than this . like country code, state code, etc.
            System.out.println(" StateName " + StateName);
            System.out.println(" CityName " + CityName);
            System.out.println(" CountryName " + CountryName);
if (CityName.length() !=0 && CountryName.length() !=0)
{
    editor.putString("city1", CityName);
    editor.putString("country1", CountryName);
    editor.commit();
}

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Location listener class. to get location.
    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }


    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    Location location;
    Double MyLat, MyLong;
    String CityName = "";
    String StateName = "";
    String CountryName = "";


// below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location.

    public Location getLastKnownLocation(Context context)

    {
        Location location = null;
        @SuppressLint("WrongConstant") LocationManager locationmanager = (LocationManager) context.getSystemService("location");
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do {
            //System.out.println("---------------------------------------------------------------------");
            if (!iterator.hasNext())
                break;
            String s = (String) iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if (i != false && !locationmanager.isProviderEnabled(s))
                continue;
            // System.out.println("provider ===> "+s);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return TODO;
            }
            Location location1 = locationmanager.getLastKnownLocation(s);
            if(location1 == null)
                continue;
            if(location != null)
            {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if(f >= f1)
                {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if(l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
            // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
            // System.out.println("---------------------------------------------------------------------");
        } while(true);
        return location;    }
}
