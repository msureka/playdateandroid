package com.bba.playdate1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bba.fragment.ProfileFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class FrirndReqActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    public SharedPreferences pref;
    String Str_matchedfbid, str_Accep_Decline, Str_User2_url, Str_userfbid;
    TextView nametxt, placetxt, nametxt1;
    ImageView proimg;
    Button Accept, decline;
    android.support.v7.app.AlertDialog alertDialog_Box;
    ProgressDialog progressDialog;

    public static JSONObject req_json_obj;
    public static Activity Activity_matchreq_remove = null;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frirnd_req);
        nametxt1 = (TextView) findViewById(R.id.gaga_txt);
        placetxt = (TextView) findViewById(R.id.place_txt);
        nametxt = (TextView) findViewById(R.id.name_txt);

        proimg = (ImageView) findViewById(R.id.profileImgview);

        ImageView back_imgview = (ImageView) findViewById(R.id.back_imgview);


        Accept = (Button) findViewById(R.id.accept_btn);
        decline = (Button) findViewById(R.id.decline_btn);
        Activity_matchreq_remove = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        if (req_json_obj != null) {

            try {
                Str_matchedfbid = req_json_obj.getString("matchedfbid");
                Str_User2_url = req_json_obj.getString("profilepic");
                Str_userfbid = pref.getString("userid", "");


                nametxt.setText(req_json_obj.getString("fname") + ", " + req_json_obj.getString("age"));
                placetxt.setText(req_json_obj.getString("city") + ", " + req_json_obj.getString("country"));

                int int_defaultimg = 0;
                if (req_json_obj.getString("gender").equalsIgnoreCase("Boy"))
                {
                    int_defaultimg = R.drawable.defaultboy;
                    back_imgview.setImageResource(R.drawable.boypictureframe);
                } else {
                    int_defaultimg = R.drawable.defaultgirl;
                    back_imgview.setImageResource(R.drawable.girlpictureframe);
                }

                Picasso.with(FrirndReqActivity.this).load(Str_User2_url).fit().centerCrop()
                        .placeholder(int_defaultimg)
                        .into(proimg);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_Accep_Decline = "ACCEPT";

                progressDialog = new ProgressDialog(FrirndReqActivity.this);
                progressDialog.setMessage("Accepting..."); // Setting Message
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Communication_Accept_Decline myTask = new Communication_Accept_Decline();

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
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_Accep_Decline = "DECLINE";
                progressDialog = new ProgressDialog(FrirndReqActivity.this);
                progressDialog.setMessage("Decline..."); // Setting Message
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Communication_Accept_Decline myTask = new Communication_Accept_Decline();
                                myTask.execute();


                            // Your implementation goes here
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }

        });
    }

    //getDattaStrem for all Php Connection.....

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


    //Emojii update ..........php connection


    public class Communication_Accept_Decline extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {


                URL url = new URL(UrlClass.acceptdecline);
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("fbid1", Str_userfbid);
                postDataParams.put("fbid2", Str_matchedfbid);
                postDataParams.put("request", str_Accep_Decline);


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
            if (result != null) {
                if (str_Accep_Decline.equalsIgnoreCase("DECLINE"))
                {
                    finish();
                }
                else

                    {
                    Intent intent1 = new Intent(FrirndReqActivity.this, Chating_Activity.class);
                    Chating_Activity.Msg_Record_obj = req_json_obj;//Array_MessagesFriends.get((Integer) llGallery.getTag());
                    Chating_Activity.chatflag = "accept";
                    startActivity(intent1);


                }
            } else {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(FrirndReqActivity.this);
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

    @Override
    public void onBackPressed() {

    }
}