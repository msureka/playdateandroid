package com.bba.playdate1;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.io.File;
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
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by spielmohitp on 2/16/2017.
 */
public class WelcomeActivity extends AppCompatActivity {
    private static final Location TODO = null;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;


    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    Location location;
    Double MyLat, MyLong;
    public static String CityName = "";
    public static String StateName = "";
    public static String CountryName = "";

    public static AlertDialog alertDialog_Box;

    private static FirebaseAuth mAuth;
    public String City;
    public String Country;
    String FacebookID;

    public boolean canGetLocation = false;
    protected LocationManager locationManager;
    ProgressDialog progressDialog;

    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static int LOCATION_SETTINGS_REQUEST = 0x1;
    String str_onresume_falg = "no";

    String FBEmail, FBGender, FBName, FbfirstName, FblastName, FBProfilepic;
    Button FbBtn;
    TextView PlayDateTxt, TermTxt;

    public static ArrayList<String> list_frnds = new ArrayList<>();

    //FB Variables
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    //Fbcallback class
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(

                                        JSONObject object,
                                        GraphResponse response) {

                                    Log.e("Fb response: ", response + "");
                                    try {



                                        FacebookID = object.getString("id").toString();
                                        FBEmail="";
                                        FBGender="";
                                        if (object.has("email"))
                                        {
                                            FBEmail = object.getString("email").toString();
                                        }

                                        if (object.has("name"))
                                        {
                                            FBName = object.getString("name").toString();
                                        }


                                        if (object.has("gender"))
                                        {
                                            FBGender = object.getString("gender").toString();
                                        }

                                        FbfirstName = object.getString("first_name");
                                        FblastName = object.getString("last_name");
                                        String Str_friends="";

                                        if (object.has("friends"))
                                        {
                                             Str_friends = object.getString("friends");
                                            JSONObject jsonObject = new JSONObject(Str_friends);
                                            String str_frbd1 = jsonObject.getString("data");

                                            final JSONArray jsonArray = new JSONArray(str_frbd1);

                                            Thread th = new Thread(new Runnable() {
                                                private long startTime = System.currentTimeMillis();

                                                public void run() {
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject jsonObject1 = null;
                                                        try {
                                                            jsonObject1 = new JSONObject(String.valueOf(jsonArray.get(i)));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        try {
                                                            list_frnds.add(jsonObject1.getString("id"));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        try {
                                                            Thread.sleep(1000);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            });
                                            th.start();

                                        }


                                        FBProfilepic = "https://graph.facebook.com/" + FacebookID + "/picture?type=large";



                                        editor.putString("userid", FacebookID);
                                        editor.putString("fname", FbfirstName);
                                        editor.putString("lname", FblastName);
                                        editor.putString("gender", FBGender);
                                        editor.commit();



                                        Handler refresh1 = new Handler(Looper.getMainLooper());
                                        refresh1.post(new Runnable() {
                                            public void run() {
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            //  sampleUserNameString = emai;
                                                            new Communication_Signupwithfb().execute();

//                                                            Communication_Signupwithfb task_activity = new Communication_Signupwithfb();
//                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//                                                                task_activity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                                                            else
//                                                                task_activity.execute();


                                                            // Your implementation goes here
                                                        } catch (Exception ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    }
                                                }).start();
                                            }
                                        });


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

            Bundle parameters = new Bundle();
            parameters.putString("fields","id,name,email,gender,first_name,last_name,friends");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
             progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FB Initialize
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        if (pref.getString("Loginplay", "").equalsIgnoreCase("yes"))
        {
            editor.putString("Loginplay", "yes");
            editor.commit();

            Intent intents = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intents);

        } else {

            //Initialize variable
            FbBtn = (Button) findViewById(R.id.loginbutton);

            GradientDrawable bgShape = (GradientDrawable) FbBtn.getBackground();
            bgShape.mutate();
            bgShape.setColor(getResources().getColor(R.color.darkgray));
            FbBtn.setEnabled(false);


            PlayDateTxt = (TextView) findViewById(R.id.apptitle_textview);
            TermTxt = (TextView) findViewById(R.id.term_textview);
            TextView suggtxt = (TextView) findViewById(R.id.Sugg_textviww);

            String s1="";
            if (pref.getString("leftswipe","").equalsIgnoreCase(""))
            {
                s1="";
            }
            else
            {
                s1 = pref.getString("leftswipe", "");
            }
            editor.clear();
            editor.clear();
            File sharedPreferenceFile = new File("/data/data/" + getPackageName() + "/shared_prefs/");
            File[] listFiles = sharedPreferenceFile.listFiles();
            for (File file : listFiles) {
                file.delete();
            }


            editor.putString("leftswipe",s1);
            editor.commit();
            LoginManager.getInstance().logOut();


            PackageInfo info;
            try {
                info = this.getPackageManager().getPackageInfo("com.bba.playdate1", PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md;
                    md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String something = new String(Base64.encode(md.digest(), 0));
                    //String something = new String(Base64.encodeBytes(md.digest()));
                    Log.e("hash key", something);
                }
            } catch (PackageManager.NameNotFoundException e1) {
                Log.e("name not found", e1.toString());
            } catch (NoSuchAlgorithmException e) {
                Log.e("no such an algorithm", e.toString());
            } catch (Exception e) {
                Log.e("exception", e.toString());
            }

            Spannable span = Spannable.Factory.getInstance().newSpannable("By signing in, you agree to our Terms of Service and Privacy Policy");
            span.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play-date.ae/terms.html"));
                    startActivity(browserIntent);
                }
            }, 32, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


// All the rest will have the same spannable.
            ClickableSpan cs = new ClickableSpan() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play-date.ae/privacy.html"));
                    startActivity(browserIntent);
                }
            };

// set the "test " spannable.
            span.setSpan(cs, 53, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// set the " span" spannable
            //    span.setSpan(cs, 6, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            TermTxt.setText(span);
            TermTxt.setMovementMethod(LinkMovementMethod.getInstance());
            TermTxt.setHighlightColor(Color.TRANSPARENT);
            //Initialize location manager

            Intent intent = new Intent(this, MyFirebaseMessengingService.class);
            startService(intent);

            if (ContextCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
//location updates

//            LocationRequest mLocationRequest = LocationRequest.create()
//                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                    .setInterval(10 * 1000)
//                    .setFastestInterval(1 * 1000);
//
//            LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(mLocationRequest);
          //  settingsBuilder.setAlwaysShow(true);

           // Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(WelcomeActivity.this)
             //       .checkLocationSettings(settingsBuilder.build());

//            result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//                @Override
//                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//                    try {
//                        LocationSettingsResponse response =
//                                task.getResult(ApiException.class);
//                    } catch (ApiException ex) {
//                        switch (ex.getStatusCode()) {
//                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                                try {
//                                    ResolvableApiException resolvableApiException =
//                                            (ResolvableApiException) ex;
//                                    resolvableApiException
//                                            .startResolutionForResult(WelcomeActivity.this,
//                                                    LOCATION_SETTINGS_REQUEST);
//                                } catch (IntentSender.SendIntentException e) {
//
//                                }
//                                break;
//                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//
//                                break;
//                        }
//                    }
//                }
//            });




            location_permission_checked();


        }


    }

    void location_permission_checked()
    {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Location Permission");
                builder.setMessage("The app needs location permissions. Please grant this permission to continue using the features of the app.");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {



                            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }

                    }
                });
                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);}});
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            } else {
                GradientDrawable bgShape = (GradientDrawable) FbBtn.getBackground();
                bgShape.mutate();
                bgShape.setColor(getResources().getColor(R.color.Lightyellow));
                FbBtn.setEnabled(true);
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
                GradientDrawable bgShape = (GradientDrawable) FbBtn.getBackground();
                bgShape.mutate();
                bgShape.setColor(getResources().getColor(R.color.Lightyellow));
                FbBtn.setEnabled(true);
                getMyCurrentLocation();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//if (str_onresume_falg.equalsIgnoreCase("yes"))
//{
//    location_permission_checked();
//}
//else
//{
//    str_onresume_falg="yes";
//}

        //fb login setup
        callbackManager = CallbackManager.Factory.create();

        //Fb login btn setup
        loginButton = (LoginButton) findViewById(R.id.fblogin_button);
        loginButton.setReadPermissions("public_profile", "email", "user_friends", "user_gender");

        //app fb btn setup & onclick
        FbBtn = (Button) findViewById(R.id.loginbutton);
        FbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginButton.performClick();

                loginButton.setPressed(true);

                loginButton.invalidate();

                loginButton.registerCallback(callbackManager, mCallBack);

                loginButton.setPressed(false);

                loginButton.invalidate();

                progressDialog = new ProgressDialog(WelcomeActivity.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {

    }

    public class Communication_Signupwithfb extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.signupwithfb);
                JSONObject postDataParams = new JSONObject();


                mAuth = FirebaseAuth.getInstance();
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                postDataParams.put("emailid", FBEmail);
                postDataParams.put("fname", FbfirstName);
                postDataParams.put("lname", FblastName);
                postDataParams.put("fbid", FacebookID);
                postDataParams.put("imageurl", FBProfilepic);
                postDataParams.put("gender", FBGender);
                postDataParams.put("city", CityName);
                postDataParams.put("country", CountryName);

                postDataParams.put("devicetoken", refreshedToken);
                String str_friendlist = "";
                if (list_frnds != null) {
                    str_friendlist = list_frnds.toString();
                    str_friendlist = str_friendlist.replace("[", "");
                    str_friendlist = str_friendlist.replace("]", "");
                    str_friendlist = str_friendlist.replace("{", "");
                    str_friendlist = str_friendlist.replace("}", "");

                }

                postDataParams.put("friendlist", str_friendlist);
                postDataParams.put("platform", "android");
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


            try {


                if (result.equalsIgnoreCase("nullerror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(WelcomeActivity.this);
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


                } else if (result.equalsIgnoreCase("inserterror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(WelcomeActivity.this);
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


                } else {

                    if (result != null) {
                        editor.putString("userid", FacebookID);
                        editor.commit();

                        JSONArray jsonarray = jsonarray = new JSONArray(result);
                        JSONObject Jsonobject = jsonarray.getJSONObject(0);


                        if (Jsonobject.getString("verified").equalsIgnoreCase("yes")) {

                            if (Jsonobject.getString("registration_status").equalsIgnoreCase("NEWUSER")) {
                                editor.putString("Loginplay", "no");
                                editor.commit();
                                editor.putString("question_view", "no");
                                editor.putString("leftswipe", "no");
                                editor.commit();
                                Intent intent = new Intent(WelcomeActivity.this, MainProfileActivity.class);
                                startActivity(intent);


                            }
                            else if (Jsonobject.getString("registration_status").equalsIgnoreCase("COMPLETE")) {
                                editor.putString("fname", Jsonobject.getString("fname"));
                                editor.putString("emailid", Jsonobject.getString("emailid"));
                                editor.putString("gender", Jsonobject.getString("gender"));
                                editor.putString("activity1", Jsonobject.getString("activity1"));
                                editor.putString("activity2", Jsonobject.getString("activity2"));
                                editor.putString("activity3", Jsonobject.getString("activity3"));
                                editor.putString("agegroup", Jsonobject.getString("agegroup"));
                                editor.putString("pushmatch", Jsonobject.getString("pushmatch"));
                                editor.putString("pushmessage", Jsonobject.getString("pushmessage"));
                                editor.putString("birthdate", Jsonobject.getString("birthdate"));
                                editor.putString("city", Jsonobject.getString("city"));
                                editor.putString("country", Jsonobject.getString("country"));
                                editor.putString("description", Jsonobject.getString("description"));
                                editor.putString("distance", Jsonobject.getString("distance"));
                                editor.putString("emoji1", Jsonobject.getString("emoji1"));
                                editor.putString("emoji2", Jsonobject.getString("emoji2"));
                                editor.putString("emoji3", Jsonobject.getString("emoji3"));
                                editor.putString("icanmeet", Jsonobject.getString("icanmeet"));
                                editor.putString("liketoplay", Jsonobject.getString("liketoplay"));
                                editor.putString("makefriendswith", Jsonobject.getString("makefriendswith"));
                                editor.putString("profilepic", Jsonobject.getString("profilepic"));
                                editor.putString("age", Jsonobject.getString("age"));
                                editor.putString("superhero", Jsonobject.getString("superhero"));
                                editor.putString("englang", Jsonobject.getString("englang"));
                                editor.putString("frenchlang", Jsonobject.getString("frenchlang"));
                                editor.putString("arabiclang", Jsonobject.getString("arabiclang"));


                                ArrayList<String> Array_Languge_three = new ArrayList<>();

                                if (!Jsonobject.getString("englang").equalsIgnoreCase("")) {
                                    Array_Languge_three.add(Jsonobject.getString("englang"));
                                }
                                if (!Jsonobject.getString("frenchlang").equalsIgnoreCase("")) {
                                    Array_Languge_three.add(Jsonobject.getString("frenchlang"));
                                }
                                if (!Jsonobject.getString("arabiclang").equalsIgnoreCase("")) {
                                    Array_Languge_three.add(Jsonobject.getString("arabiclang"));
                                }

                                JSONArray Array_lang = new JSONArray(Array_Languge_three);

                                editor.putString("language", Array_lang.toString());
                                editor.putString("Loginplay", "yes");

                                editor.commit();


                                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);


                            }
                        } else {

                            editor.putString("question_view", "no");
                            editor.putString("leftswipe", "no");

                            editor.commit();
                            editor.putString("Loginplay", "no");
                            editor.commit();
                            Intent intent = new Intent(WelcomeActivity.this, OTPActivity.class);
                            startActivity(intent);


                        }
                    } else {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(WelcomeActivity.this);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(GraphRequest.TAG, "coarse location permission granted");

                    getMyCurrentLocation();


                }
            }
        }


        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


            }
        }


    }

    /** Check the type of GPS Provider available at that instance and  collect the location informations
     @Output Latitude and Longitude
      * */
    void getMyCurrentLocation() {

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            GradientDrawable bgShape = (GradientDrawable) FbBtn.getBackground();
            bgShape.mutate();
            bgShape.setColor(getResources().getColor(R.color.Lightyellow));
            FbBtn.setEnabled(true);
        } else {
            Location loc = getLastKnownLocation(this);
            if (loc != null) {
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
                GradientDrawable bgShape = (GradientDrawable) FbBtn.getBackground();
                bgShape.mutate();
                bgShape.setColor(getResources().getColor(R.color.Lightyellow));
                FbBtn.setEnabled(true);
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
            if (CityName.length() != 0 && CountryName.length() != 0) {
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


// below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location.

    public Location getLastKnownLocation(Context context)
//    {
//        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
//        List<String> providers = mLocationManager.getProviders(true);
//
//
//        Location bestLocation = null;
//        for (String provider : providers) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return TODO;
//            }
//            Location l = mLocationManager.getLastKnownLocation(provider);
//            if (l == null) {
//                continue;
//            }
//            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
//                // Found best last known location: %s", l);
//                bestLocation = l;
//            }
//        }
//        return bestLocation;
//    }

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
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        return location;

    }







}
