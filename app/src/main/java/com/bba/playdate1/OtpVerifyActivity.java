package com.bba.playdate1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class OtpVerifyActivity extends AppCompatActivity
{

    Button  verify, resend;
    EditText  otpCode;
    private FirebaseAuth mAuth;
    String verifivationcode="";
    static String mobileno="";
    ProgressDialog progressDialog;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    android.support.v7.app.AlertDialog alertDialog_Box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        verify = (Button) findViewById(R.id.verifybtn);
        resend = (Button) findViewById(R.id.resend_btn1);
        otpCode = (EditText) findViewById(R.id.otpcodetxt);

        mAuth = FirebaseAuth.getInstance();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        verify.setEnabled(false);


        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            verifivationcode=extras.getString("verificationid");
            mobileno=extras.getString("mobileno");

        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(OtpVerifyActivity.this);
                progressDialog.setMessage("Sending..."); // Setting Message
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                hideKeyboard(OtpVerifyActivity.this);

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifivationcode, otpCode.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

finish();


            }
        });
        otpCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String mobilrnoString1 = otpCode.getText().toString();
                if (mobilrnoString1.length() < 1) {
                    verify.setEnabled(false);
                    verify.setBackgroundDrawable(getResources().getDrawable(R.drawable.blackborderbtn));
                } else {
                    verify.setEnabled(true);
                    verify.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            //Toast.makeText(OtpVerifyActivity.this, "Verification done", Toast.LENGTH_LONG).show();





                            new SendPostRequest().execute();






                        }
                    }
                });
    }
    public void hideKeyboard(OtpVerifyActivity mainActivity) {
        // Check if no view has focus:
        View view = OtpVerifyActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) OtpVerifyActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.verifymobile);
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("fbid",pref.getString("userid",""));
                postDataParams.put("mobileno",mobileno);




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
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            JSONArray jsonarray=null;
            JSONObject Jsonobject=null;
            try {





              if (result.equalsIgnoreCase("nullerror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(OtpVerifyActivity.this);
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
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(OtpVerifyActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Server encountered an error in verifying your mobile number. Please try again later.");
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
                else if (result.equalsIgnoreCase("alreadyregistered"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(OtpVerifyActivity.this);
                    builder1.setTitle("Already registered");
                    builder1.setMessage("Sorry your mobile number has already been registered by some other user. Please try using another mobile number.");
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

                    if(Jsonobject.getString("registration_status").equalsIgnoreCase("NEWUSER"))
                    {

                        Intent  intent = new Intent(OtpVerifyActivity.this, MainProfileActivity.class);
                        startActivity(intent);


                    }
                    else if(Jsonobject.getString("registration_status").equalsIgnoreCase("COMPLETE"))
                    {
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
                        editor.putString("englang",Jsonobject.getString("English"));
                        editor.putString("frenchlang",Jsonobject.getString("French"));
                        editor.putString("arabiclang",Jsonobject.getString("Arabic"));


                        ArrayList<String> Array_Languge_three = new ArrayList<>();

                        if (!Jsonobject.getString("English").equalsIgnoreCase(""))
                        {
                            Array_Languge_three.add(Jsonobject.getString("English"));
                        }
                        if (!Jsonobject.getString("French").equalsIgnoreCase(""))
                        {
                            Array_Languge_three.add(Jsonobject.getString("French"));
                        }
                        if (!Jsonobject.getString("Arabic").equalsIgnoreCase(""))
                        {
                            Array_Languge_three.add(Jsonobject.getString("Arabic"));
                        }

                        JSONArray Array_lang=new JSONArray(Array_Languge_three);
                        editor.putString("language",Array_lang.toString());


                        editor.putString("Loginplay","yes");
                        editor.commit();

                        Intent intent = new Intent(OtpVerifyActivity.this, MainActivity.class);
                        startActivity(intent);


                    }



                }
//                else
//                {
//                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(OtpVerifyActivity.this);
//                    builder1.setTitle("Server timeout");
//                    builder1.setMessage("Server timeout");
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
//                }


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
