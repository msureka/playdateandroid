package com.bba.playdate1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by spielmohitp on 2/20/2017.
 */
public class SuperHeroProfileActivity extends AppCompatActivity {

    TextView BackTxt, NextTxt, TitleTxt, LestPlayTxt;
    RelativeLayout LestPlayLable;
    EditText SuperHeroEditText;
    Intent intent;
    Bundle bu;
    String BitmapString;
    public static String Str_superHero;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static FirebaseAuth mAuth;
    android.support.v7.app.AlertDialog alertDialog_Box;
    ProgressDialog progressDialog;
    int PRIVATE_MODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_superheroprofile);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        Intent intent = new Intent(this, MyFirebaseMessengingService.class);
        startService(intent);


        BackTxt = (TextView) findViewById(R.id.back_txt);
        NextTxt = (TextView) findViewById(R.id.next_txt);
        TitleTxt = (TextView) findViewById(R.id.titletxt);
        SuperHeroEditText = (EditText) findViewById(R.id.editText);
        LestPlayLable = (RelativeLayout) findViewById(R.id.lets_lable);
        LestPlayTxt = (TextView) findViewById(R.id.letspaly_txt);
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        //Back text Set Visible
        BackTxt.setVisibility(View.VISIBLE);

        //set Title Text
        TitleTxt.setText("5/5");






       // img_demo.setImageBitmap(MainProfileActivity.bm);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("superprofile_row").equalsIgnoreCase("yes"))
            {
                LestPlayTxt.setText("Save");
                SuperHeroEditText.setText(Str_superHero);
            }
            else
            {
                LestPlayTxt.setText("Let's play");
                SuperHeroEditText.setText("");

            }
        }
        else
        {
            LestPlayTxt.setText("Let's play");
            SuperHeroEditText.setText("");

        }





        NextTxt.setVisibility(View.INVISIBLE);

        // Superhero Edittext text Fatch function
        SuperHeroEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                SuperHeroEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});

                if ( SuperHeroEditText.getText().length() <= 0) {

                    LestPlayTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    LestPlayLable.setEnabled(false);
                    NextTxt.setEnabled(false);
                } else {
                    LestPlayTxt.setTextColor(Color.parseColor("#000000"));
                    LestPlayLable.setEnabled(true);
                    NextTxt.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


                if ( SuperHeroEditText.getText().length() <= 0) {

                    LestPlayTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    LestPlayLable.setEnabled(false);
                    NextTxt.setEnabled(false);
                } else {
                    LestPlayTxt.setTextColor(Color.parseColor("#000000"));
                    LestPlayLable.setEnabled(true);
                    NextTxt.setEnabled(true);
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

        //Back text onclick function
        NextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //Lets Play text onclick function
        LestPlayLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {
                    if (extras.getString("superprofile_row").equalsIgnoreCase("yes"))
                    {
                        Str_superHero=SuperHeroEditText.getText().toString();
                        SettingActivity.str_suprtheroSave="no";
                        PlayProfileActivity.removeActivity_playProfileActivity.finish();
                        MeetProfileActivity.removeActivity_MeetProfileActivity.finish();
                        FavroiteProfileActivity.removeActivity_FavroiteProfileActivity.finish();
                        finish();
                    }
                    else
                    {
                        progressDialog = new ProgressDialog(SuperHeroProfileActivity.this);
                        progressDialog.setMessage("Loading..."); // Setting Message
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //  sampleUserNameString = emai;
                                    new Communication_signupstep5().execute();

                                    // Your implementation goes here
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
                else
                {
                    progressDialog = new ProgressDialog(SuperHeroProfileActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //  sampleUserNameString = emai;
                                new Communication_signupstep5().execute();

                                // Your implementation goes here
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();

                }



            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }






    public class  Communication_signupstep5 extends AsyncTask<String, Void, String>  {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {


                String Str_Lang=pref.getString("language","");



                    URL url = new URL(UrlClass.step5);
                JSONObject postDataParams = new JSONObject();


                String Str_Lang_Eng="",Str_Lang_Arb="",Str_Lang_Fre="";

                mAuth = FirebaseAuth.getInstance();
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

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
                postDataParams.put("fname",pref.getString("user_name",""));
                postDataParams.put("lname",pref.getString("lname",""));
                postDataParams.put("gender",pref.getString("gender",""));




//                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
//                Date date = format2.parse(pref.getString("birthdate",""));
//
//                String Str_DOb = format1.format(date);

                postDataParams.put("birthdate",pref.getString("birthdate",""));
                postDataParams.put("city",WelcomeActivity.CityName);
                postDataParams.put("country",WelcomeActivity.CountryName);
                postDataParams.put("devicetoken",refreshedToken);
                postDataParams.put("platform","android");
                postDataParams.put("englang",Str_Lang_Eng);
                postDataParams.put("arabiclang",Str_Lang_Arb);
                postDataParams.put("frenchlang",Str_Lang_Fre);
                postDataParams.put("description",pref.getString("user_about",""));
                postDataParams.put("liketoplay",pref.getString("liketoplay",""));
                postDataParams.put("icanmeet",pref.getString("icanmeet",""));
                postDataParams.put("activity1",pref.getString("activity1",""));
                postDataParams.put("activity2",pref.getString("activity2",""));
                postDataParams.put("activity3",pref.getString("activity3",""));
                postDataParams.put("emoji1",pref.getString("emoji1",""));
                postDataParams.put("emoji2",pref.getString("emoji2",""));
                postDataParams.put("emoji3",pref.getString("emoji3",""));
                postDataParams.put("superhero",SuperHeroEditText.getText().toString());
                if (MainProfileActivity.bitmaps !=null)
                {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    MainProfileActivity.bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    postDataParams.put("profileimage",encodedImage);
                }
                else
                {
                    postDataParams.put("profileimage","");
                }

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



                if(result.length()>=30)
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
                    editor.putString("profilepic",Jsonobject.getString("profilepic"));
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

                    editor.putString("Loginplay","yes");
                    editor.commit();

                    Intent intent = new Intent(SuperHeroProfileActivity.this, Tutorial.class);
                    startActivity(intent);


                }

                else if (result.equalsIgnoreCase("error"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
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
                else if (result.equalsIgnoreCase("updateerror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Error in creating your account. Please try again.");
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
                else if (result.equalsIgnoreCase("imagenull"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("You seem to have not selected a profile image. Please try again.");
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
                else if (result.equalsIgnoreCase("imageerror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Could not upload the profile image you have selected. Please try again.");
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
                else if (result.equalsIgnoreCase("selecterror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Could not retrieve your Account Id. Please login and try again.");
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
                else if (result.equalsIgnoreCase("nameerror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
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


                }
                else if (result.equalsIgnoreCase("descriptionerror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
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


                }
                else if (result.equalsIgnoreCase("superheroerror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
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
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Could not create account due to unknown error. Please contact support@play-date.ae.");
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




}
