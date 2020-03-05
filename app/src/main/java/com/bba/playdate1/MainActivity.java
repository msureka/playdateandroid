package com.bba.playdate1;

import android.*;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bba.fragment.DiscoverFragment;
import com.bba.fragment.FriendsFragment;
import com.bba.fragment.ProfileFragment;

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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends FragmentActivity {


    //variables
    public static Fragment fragment = null;
    String FragmentValue = "discover";

    public static String click_friend_val = "chat";

    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static String CountResult;
    static Timer home_timer;
    static TimerTask timerTask;
    static Handler mHandler = new Handler();
    ImageView ProfileBtn, DiscoverBtn, FriendsBtn;
    TextView BadgeTxt, protxt, distxt, frndtxt;
    View v;
    RelativeLayout r1;
    Timer timer_connections;
    String str_profile_buttonflag="no",str_discover_buttonflag="yes",str_friends_buttonflag="no";;

    RelativeLayout mRelativeLayout;
    Button txt_tketour,txt_chatnext,txt_Gotit;
    public  PopupWindow mPopupWindow,mPopupWindow2,mPopupWindow3;
  public  static Typeface Font_helvetica_regular,font_helvitica_bold,font_helvitica_light,font_helvitica_light_oblique,font_kg,font_helvitica_medium,font_helvitica_normal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        FragmentValue = "discover";
        font_kg=Typeface.createFromAsset(MainActivity.this.getAssets(),"font/kg_feeling_regular.ttf");

         Font_helvetica_regular
                =Typeface.createFromAsset(MainActivity.this.getAssets(),"font/helvetica_regular.ttf");
        font_helvitica_normal
                =Typeface.createFromAsset(MainActivity.this.getAssets(),"font/helvetica_normal.ttf");
        font_helvitica_medium
                =Typeface.createFromAsset(MainActivity.this.getAssets(),"font/helvetica_medium.ttf");
        font_helvitica_light
                =Typeface.createFromAsset(MainActivity.this.getAssets(),"font/helvetica_light.ttf");
        font_helvitica_light_oblique
                =Typeface.createFromAsset(MainActivity.this.getAssets(),"font/helvetica_light_oblique.ttf");
         font_helvitica_bold
                =Typeface.createFromAsset(MainActivity.this.getAssets(),"font/helvetica_bold.ttf");




        //get current view
        v = getCurrentFocus();

        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(question_FriendsFragmentButton,
                new IntentFilter("question_action_friend"));

        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(discover_buttontap_forswipeData,
                new IntentFilter("discover_buttontap_forswipe"));


        //init variable
        ProfileBtn = (ImageView) findViewById(R.id.profile_btn);
        DiscoverBtn = (ImageView) findViewById(R.id.discover_btn);
        FriendsBtn = (ImageView) findViewById(R.id.friend_btn);
        BadgeTxt = (TextView) findViewById(R.id.badge_txt);
        protxt = (TextView) findViewById(R.id.profiletxt);
        distxt = (TextView) findViewById(R.id.discovertxt);
        frndtxt = (TextView) findViewById(R.id.friendtxt);
        r1 = (RelativeLayout) findViewById(R.id.footer);
        mRelativeLayout=(RelativeLayout)findViewById(R.id.relative_popup);
        mRelativeLayout.setVisibility(View.INVISIBLE);
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        /*keyhash();*/

        if (pref.getString("gender","").equalsIgnoreCase("BOY")) {
            r1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bluetextbox));
        } else {
            r1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pinktextbox));
        }
        // Toast.makeText(getApplicationContext()," Name "+WelcomeActivity.Fname,Toast.LENGTH_LONG).show();

        // set footer btns default images
        ProfileBtn.setImageResource(R.drawable.profile);
        DiscoverBtn.setImageResource(R.drawable.discover_s);
        FriendsBtn.setImageResource(R.drawable.friends);



        distxt.setTextColor(Color.parseColor("#000000"));
        protxt.setTextColor(Color.parseColor("#b4b4b4"));
        frndtxt.setTextColor(Color.parseColor("#b4b4b4"));
        BadgeTxt.setVisibility(View.INVISIBLE);

        // Log.e("toyBornTime", "" + toyBornTime);

        //check FragmentValue value
      /*  if(FragmentValue.equalsIgnoreCase("discover")) {
            //it if 'profile'
            //tansaction of fragment to Profile Fragment
            getFragmentManager().beginTransaction().add(R.id.home_fragment, new DiscoverFragment()).commit();
        }
        else
        {
            //tansaction of fragment to Discover Fragment
            getFragmentManager().beginTransaction().add(R.id.home_fragment, new ProfileFragment()).commit();
        }*/


        timer_connections = new Timer();
        timer_connections.scheduleAtFixedRate(new TimerTask() {

                                                  @Override
                                                  public void run()
                                                  {
                                                      new Thread(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              try {



                                                                  Communication_friendscount myTask = new Communication_friendscount();

                                                                  if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                                                                      myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                                  else
                                                                      myTask.execute();



                                                                  Intent intent1 = new Intent("updateprofile_timer");
                                                                  LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent1);

                                                              } catch (Exception ex) {
                                                                  ex.printStackTrace();
                                                              }
                                                          }
                                                      }).start();
                                                  }

                                              },
                0,1000);



        Intent i = getIntent();
        String meechat = "";//MeetChatActivity.FragmentValue1;
        String meechat1 = "";
        String frahmentCrop = i.getExtras().getString("fragmentcrop");
        String setting = i.getExtras().getString("setting");
        String pager = i.getExtras().getString("pager");


        if (FragmentValue.equalsIgnoreCase("friends")) {
            //it if 'profile'
            //tansaction of fragment to Profile Fragment
            getFragmentManager().beginTransaction().add(R.id.home_fragment, new FriendsFragment()).commit();

            frndtxt.setTextColor(Color.parseColor("#000000"));
            protxt.setTextColor(Color.parseColor("#b4b4b4"));
            distxt.setTextColor(Color.parseColor("#b4b4b4"));
            frndtxt.setTypeface(Typeface.DEFAULT_BOLD);
            distxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            protxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ProfileBtn.setImageResource(R.drawable.profile);
            DiscoverBtn.setImageResource(R.drawable.discover);
            FriendsBtn.setImageResource(R.drawable.friends_s);
        }  else if (FragmentValue.equalsIgnoreCase("profile")) {
            //tansaction of fragment to Discover Fragment
            getFragmentManager().beginTransaction().add(R.id.home_fragment, new ProfileFragment()).commit();

            distxt.setTextColor(Color.parseColor("#b4b4b4"));
            protxt.setTextColor(Color.parseColor("#000000"));
            frndtxt.setTextColor(Color.parseColor("#b4b4b4"));

            protxt.setTypeface(Typeface.DEFAULT_BOLD);
            distxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            frndtxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ProfileBtn.setImageResource(R.drawable.profile_s);
            DiscoverBtn.setImageResource(R.drawable.discover);
            FriendsBtn.setImageResource(R.drawable.friends);

        }

        else if (FragmentValue.equalsIgnoreCase("discover")) {
            //tansaction of fragment to Discover Fragment
            getFragmentManager().beginTransaction().add(R.id.home_fragment, new DiscoverFragment()).commit();

            distxt.setTextColor(Color.parseColor("#000000"));
            protxt.setTextColor(Color.parseColor("#b4b4b4"));
            frndtxt.setTextColor(Color.parseColor("#b4b4b4"));
            distxt.setTypeface(Typeface.DEFAULT_BOLD);
            protxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            frndtxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ProfileBtn.setImageResource(R.drawable.profile);
            DiscoverBtn.setImageResource(R.drawable.discover_s);
            FriendsBtn.setImageResource(R.drawable.friends);
        }
    }

    /*private void keyhash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.spielmini.caretodare",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }*/

    // Timer Function
    public void startTimer() {
        //set a new Timer
        home_timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        home_timer.schedule(timerTask, 1000, 5000); //
    }

    public void stoptimertask(View view) {
        //stop the timer, if it's not already null
        if (home_timer != null) {
            home_timer.cancel();
            home_timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                mHandler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        //calling Method to get count
                        doSomeTask();

                    }
                });
            }
        };
    }

    @Override
    public void onBackPressed() {

    }

    //Fotter button click Function
    //check which button clicked
    //intend to Fragment clicked
    public void clickFunk(View view) {




        if (view == findViewById(R.id.profile_btn) || view == findViewById(R.id.profiletxt)) {
            //Pfrofile btn clicked

            //set ProfileFragment to  fragment variable

            if (str_profile_buttonflag.equalsIgnoreCase("no")) {
                fragment = new ProfileFragment();
                FragmentValue="profile";
                //set profile btn img as clicked
                ProfileBtn.setImageResource(R.drawable.profile_s);
                DiscoverBtn.setImageResource(R.drawable.discover);
                FriendsBtn.setImageResource(R.drawable.friends);

                protxt.setTypeface(Typeface.DEFAULT_BOLD);

                distxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                frndtxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                protxt.setTextColor(Color.parseColor("#000000"));
                distxt.setTextColor(Color.parseColor("#b4b4b4"));
                frndtxt.setTextColor(Color.parseColor("#b4b4b4"));
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }
                if (mPopupWindow2 != null)

                {
                    mPopupWindow2.dismiss();
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }


                if (mPopupWindow3 != null) {
                    mPopupWindow3.dismiss();
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }
                str_friends_buttonflag = "no";
                str_discover_buttonflag="no";
                str_profile_buttonflag="yes";

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.home_fragment, fragment);
                fragmentTransaction.commit();
            }

        } else if (view == findViewById(R.id.discover_btn )|| view == findViewById(R.id.discovertxt)){
            //discore btn click

            //set DiscoverFragment to  fragment variable
            FragmentValue="discover";
            if (str_discover_buttonflag.equalsIgnoreCase("no")) {
                fragment = new DiscoverFragment();
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }

                if (mPopupWindow2 != null) {
                    mPopupWindow2.dismiss();
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }


                if (mPopupWindow3 != null) {
                    mPopupWindow3.dismiss();
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }


                //set discore btn img as clicked
                ProfileBtn.setImageResource(R.drawable.profile);
                DiscoverBtn.setImageResource(R.drawable.discover_s);
                FriendsBtn.setImageResource(R.drawable.friends);

                distxt.setTypeface(Typeface.DEFAULT_BOLD);
                protxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                frndtxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                distxt.setTextColor(Color.parseColor("#000000"));
                protxt.setTextColor(Color.parseColor("#b4b4b4"));
                frndtxt.setTextColor(Color.parseColor("#b4b4b4"));
                str_friends_buttonflag = "no";
                str_profile_buttonflag="no";
                str_discover_buttonflag="yes";

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.home_fragment, fragment);
                fragmentTransaction.commit();
            }

        } else if (view == findViewById(R.id.friend_btn)|| view == findViewById(R.id.friendtxt)) {
            //friend btn click
            if (str_friends_buttonflag.equalsIgnoreCase("no")){

                FragmentValue="friends";
            if (mPopupWindow !=null)
            {

                    mRelativeLayout.setVisibility(View.VISIBLE);
                    mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.black1));
                    mRelativeLayout.setAlpha(0.7f);
                    mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);





            }

            if (mPopupWindow2 !=null)
            {

                mRelativeLayout.setVisibility(View.VISIBLE);
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                mPopupWindow2.showAtLocation(mRelativeLayout, Gravity.TOP,0,0);


            }

            if (mPopupWindow3 !=null)
            {

                mRelativeLayout.setVisibility(View.VISIBLE);
                mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                mPopupWindow3.showAtLocation(mRelativeLayout, Gravity.TOP,0,0);


            }


            //set FriendsFragment to  fragment variable
            fragment = new FriendsFragment();

            //set profile btn img as clicked
            ProfileBtn.setImageResource(R.drawable.profile);
            DiscoverBtn.setImageResource(R.drawable.discover);
            FriendsBtn.setImageResource(R.drawable.friends_s);

            frndtxt.setTypeface(Typeface.DEFAULT_BOLD);
            distxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            protxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            frndtxt.setTextColor(Color.parseColor("#000000"));
            protxt.setTextColor(Color.parseColor("#b4b4b4"));
            distxt.setTextColor(Color.parseColor("#b4b4b4"));
            str_friends_buttonflag="yes";
            str_profile_buttonflag="no";
            str_discover_buttonflag="no";

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.home_fragment, fragment);
                fragmentTransaction.commit();
            }

        }




    }

    //Backgroung Process
    //Function to call php page for Getting count of request and arrive
    public int doSomeTask() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected Void doInBackground(final Void... params) {

                //get Msg count
                CountResult = getCount();
                return null;
            }

            @Override
            protected void onPostExecute(final Void result) {


                //check  Msg Count Result
                if (CountResult !=null) {
                    if (CountResult.equalsIgnoreCase("nullerror")) {
                        //it is error
                        //hide badge
                        BadgeTxt.setVisibility(View.INVISIBLE);
                    } else {
                        try {
                            JSONArray jsonarr = new JSONArray(CountResult);
                            JSONObject obj_resul = new JSONObject(jsonarr.getString(0));
                                    String Str_total=obj_resul.getString("total").toString();
                                    String Str_chat=obj_resul.getString("chat").toString();
                                    String Str_meetup=obj_resul.getString("playdate").toString();


                            if (Str_total.equalsIgnoreCase("0")) {
                                BadgeTxt.setVisibility(View.INVISIBLE);
                            } else {
                                BadgeTxt.setVisibility(View.VISIBLE);
                                BadgeTxt.setText(obj_resul.getString("total"));
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }.execute();
        // result=registerUser();


        return 100;

    }


    //Actual Function handling http connection and Getting count of request and arrive
    public String getCount() {
        String text = "";
        BufferedReader reader = null;
        // Send data
        try {
            // Toast.makeText(getApplicationContext(),"in register user",Toast.LENGTH_SHORT).show();
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(UrlClass.count);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("fbid", pref.getString("userid","")));


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.e("Count Response", "Response: " + responseStr);
                    text = responseStr;

                    // you can add an if statement here and do other actions based on the response
                }

            } catch (UnsupportedEncodingException e) {
                // writing error to Log
                text = "error";
                //loading.dismiss();
                // Toast.makeText(getApplicationContext(),"in register user catch",Toast.LENGTH_SHORT).show();
                Log.e("in catch 1", e.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {
            // writing exception to log
            Log.e("in catch 1", e.toString());
            //Toast.makeText(getApplicationContext(),"in register user catch 2",Toast.LENGTH_SHORT).show();
            text = "error";
            //loading.dismiss();

        }
        return text;
    }



    // Friens Count Php...

    public class  Communication_friendscount extends AsyncTask<String, Void, String>  {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.count);
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

//            progressDialog.dismiss();

            JSONArray jsonarray=null;

            if(result !=null) {



                if (result.length() >= 30) {

                    try {


                        jsonarray=new JSONArray(result);
                        JSONObject obj_resul = jsonarray.getJSONObject(0);

                            String Str_total=obj_resul.getString("total").toString();
                            String Str_chat=obj_resul.getString("chat").toString();
                            String Str_meetup=obj_resul.getString("playdate").toString();
                        if (Str_chat !=null)
                        {
                                FriendsFragment.Str_chat_buge = Str_chat;
                        }

                        if (Str_meetup !=null)
                        {
                            FriendsFragment.Str_meetup_budge = Str_meetup;
                        }
                        if (Str_total !=null) {
                            if (Str_total.equalsIgnoreCase("0")) {
                                BadgeTxt.setVisibility(View.INVISIBLE);
                            } else {
                                BadgeTxt.setVisibility(View.VISIBLE);
                                BadgeTxt.setText(obj_resul.getString("total"));
                            }
                        }








                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }



            }
//
//                else if (result.equalsIgnoreCase("error"))
//                {
//                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(SuperHeroProfileActivity.this);
//                    builder1.setTitle("Oops");
//                    builder1.setMessage("Your Facebook Account Id seems to be absent. Please login and try again.");
//                    builder1.setCancelable(false);
//                    builder1.setPositiveButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    // finish();
//                                }
//                            });
//                    alertDialog_Box = builder1.create();
//                    alertDialog_Box.show();
//
//
//
//
//                }




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


    private BroadcastReceiver discover_buttontap_forswipeData = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            mRelativeLayout.setVisibility(View.VISIBLE);
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.black1));
            mRelativeLayout.setAlpha(0.7f);
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.chathelp,null);
            txt_tketour = (Button) customView.findViewById(R.id.txt_taketourbtn);

            txt_tketour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPopupWindow.dismiss();
                    mPopupWindow=null;
                    mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                    View customView = inflater.inflate(R.layout.chathelplayout,null);

                    txt_chatnext = (Button) customView.findViewById(R.id.nextbtn1);

                    TextView txtView_1 = (TextView) customView.findViewById(R.id.txt_sec);

                    txtView_1.setText(Html.fromHtml("you can add more friends by clicking on the +ADD button on the top right of the screen"));
                    txtView_1.setText(Html.fromHtml("<html><body>you can add more friends by clicking on the <font size=14 color=yellow>+ADD </font> button on the top right of the screen.</body><html>"));



                    txt_chatnext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            mPopupWindow2.dismiss();
                            mPopupWindow=null;
                            mPopupWindow2=null;
                            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.meethelplayout,null);
                            txt_Gotit= (Button) customView.findViewById(R.id.gotbtn);
                            TextView txtView = (TextView) customView.findViewById(R.id.txt_meets2);
                            TextView txtView1 = (TextView) customView.findViewById(R.id.txt_meets3);

                            txtView.setText(Html.fromHtml("You can create a meet-up of your own and invite your friends by clicking on the +CREATE button on the top right of the screen."));
                            txtView.setText(Html.fromHtml("<html><body>You can create a meet-up of your own and invite your friends by clicking on the <font size=14 color=yellow>+CREATE </font> button on the top right of the screen.</body><html>"));

                            txtView1.setText(Html.fromHtml("If you receive a special Play:Date meet-up code,enter it by cliking on the JOIN button on the top left of the screen."));
                            txtView1.setText(Html.fromHtml("<html><body>If you receive a special Play:Date meet-up code,enter it by cliking on the <font size=14 color=yellow>JOIN </font> button on the top left of the screen.</body><html>"));



                            txt_Gotit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mRelativeLayout.setVisibility(View.INVISIBLE);
                                    mPopupWindow3.dismiss();
                                    mPopupWindow=null;
                                    mPopupWindow2=null;
                                    mPopupWindow3=null;


                                }
                            });

                            mPopupWindow3 = new PopupWindow(
                                    customView,
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );

                            // Set an elevation value for popup window
                            // Call requires API level 21
                            if(Build.VERSION.SDK_INT>=21){
                                mPopupWindow3.setElevation(5.0f);
                            }

                            mPopupWindow3.showAtLocation(mRelativeLayout, Gravity.TOP,0,0);

                            Intent intent1 = new Intent("question_action_meetupscall");
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent1);

                        }
                    });



                    mPopupWindow2 = new PopupWindow(
                            customView,
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );

                    // Set an elevation value for popup window
                    // Call requires API level 21
                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow2.setElevation(5.0f);
                    }

                    mPopupWindow2.showAtLocation(mRelativeLayout, Gravity.TOP,0,0);

                }
            });

            mPopupWindow = new PopupWindow(
                    customView,
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            // Set an elevation value for popup window
            // Call requires API level 21
            if(Build.VERSION.SDK_INT>=21){
                mPopupWindow.setElevation(5.0f);
            }

            mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);




        }
    };
    private BroadcastReceiver question_FriendsFragmentButton = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            mRelativeLayout.setVisibility(View.VISIBLE);
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.black1));
            mRelativeLayout.setAlpha(0.7f);
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.chathelp,null);
            txt_tketour = (Button) customView.findViewById(R.id.txt_taketourbtn);

            txt_tketour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPopupWindow.dismiss();
                    mPopupWindow=null;
                    mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                    View customView = inflater.inflate(R.layout.chathelplayout,null);

                    txt_chatnext = (Button) customView.findViewById(R.id.nextbtn1);


                    TextView txtView_1 = (TextView) customView.findViewById(R.id.txt_sec);

                    txtView_1.setText(Html.fromHtml("you can add more friends by clicking on the +ADD button on the top right of the screen"));
                    txtView_1.setText(Html.fromHtml("<html><body>you can add more friends by clicking on the <font size=14 color=yellow>+ADD </font> button on the top right of the screen.</body><html>"));



                    txt_chatnext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            mPopupWindow2.dismiss();
                            mPopupWindow=null;
                            mPopupWindow2=null;
                            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.meethelplayout,null);
                            txt_Gotit= (Button) customView.findViewById(R.id.gotbtn);
                            TextView txtView = (TextView) customView.findViewById(R.id.txt_meets2);
                            TextView txtView1 = (TextView) customView.findViewById(R.id.txt_meets3);

                            txtView.setText(Html.fromHtml("You can create a meet-up of your own and invite your friends by clicking on the +CREATE button on the top right of the screen."));
                            txtView.setText(Html.fromHtml("<html><body>You can create a meet-up of your own and invite your friends by clicking on the <font size=14 color=yellow>+CREATE </font> button on the top right of the screen.</body><html>"));

                            txtView1.setText(Html.fromHtml("If you receive a special Play:Date meet-up code,enter it by cliking on the JOIN button on the top left of the screen."));
                            txtView1.setText(Html.fromHtml("<html><body>If you receive a special Play:Date meet-up code,enter it by cliking on the <font size=14 color=yellow>JOIN </font> button on the top left of the screen.</body><html>"));



                            txt_Gotit.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {

                                                                mRelativeLayout.setVisibility(View.INVISIBLE);
                                                                 mPopupWindow3.dismiss();
                                                                 mPopupWindow=null;
                                                                 mPopupWindow2=null;
                                                                 mPopupWindow3=null;


                                                             }
                                                         });

                                    mPopupWindow3 = new PopupWindow(
                                    customView,
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );

                            // Set an elevation value for popup window
                            // Call requires API level 21
                            if(Build.VERSION.SDK_INT>=21){
                                mPopupWindow3.setElevation(5.0f);
                            }

                            mPopupWindow3.showAtLocation(mRelativeLayout, Gravity.TOP,0,0);

                            Intent intent1 = new Intent("question_action_meetupscall");
                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent1);

                        }
                    });



                    mPopupWindow2 = new PopupWindow(
                            customView,
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );

                    // Set an elevation value for popup window
                    // Call requires API level 21
                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow2.setElevation(5.0f);
                    }

                    mPopupWindow2.showAtLocation(mRelativeLayout, Gravity.TOP,0,0);

                }
            });

            mPopupWindow = new PopupWindow(
                    customView,
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            // Set an elevation value for popup window
            // Call requires API level 21
            if(Build.VERSION.SDK_INT>=21){
                mPopupWindow.setElevation(5.0f);
            }

            mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);




        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


            }
        }
    }

}
