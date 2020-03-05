package com.bba.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bba.playdate1.Contact_lists;
import com.bba.playdate1.CreateNewActivity;
import com.bba.playdate1.Facebook_add_friends;
import com.bba.playdate1.MainActivity;
import com.bba.playdate1.R;
import com.bba.playdate1.UrlClass;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

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

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by spielmohitp on 2/24/2017.
 */
public class FriendsFragment extends Fragment {

    public static JSONArray Array_AllFriends = null;
    public static JSONArray Array_MatchFriends = null;
    public static JSONArray Array_RequestFriends = null;
    public static JSONArray Array_MessagesFriends = null;
    public static JSONArray Array_MeetupsData = null;
    public static JSONArray Array_MeetupsData_All = null;

    public static  String str_meetup_chat_flag;

    public static String Str_chat_buge = "0", Str_meetup_budge = "0";
    //element declaration
    static View v;
    static ImageView PlusImg;
    static ImageView PlusImg1;
    static ImageView qbtn;
    static ImageView joinbtn;
    static TextView addtxt;
    static TextView createtxt;
    static TextView jointxt;
    static ProgressBar progressbar1;
    static ProgressBar progressbar2;


    ProgressDialog progressDialog;
    static TextView titletxt;
    static TextView titletxt1;
    static TextView chatb;
    static TextView meetb;
    static RelativeLayout rr;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView r1;



    String FriendResult,str_eventcodes="", str_emailAdd="",str_phonenumber="",str_flagresult="no";
    ProgressDialog progressBar;
    android.support.v7.app.AlertDialog alertDialog_Box;

    String a;
    String count = null;

    ImageView buge_indicator_chat, buge_indicator_meetup;
    FrameLayout fr;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();





    private BroadcastReceiver UpdateInformation_timer = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Communication_friends myTask = new Communication_friends();

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



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {


                        Communication_meetups_event myTask1 = new Communication_meetups_event();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            myTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else
                            myTask1.execute();


                        // Your implementation goes here
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();

            if (Str_chat_buge.equalsIgnoreCase("0")) {

                buge_indicator_chat.setVisibility(View.INVISIBLE);

            } else {
                buge_indicator_chat.setVisibility(View.VISIBLE);
            }

            if (Str_meetup_budge.equalsIgnoreCase("0")) {
                buge_indicator_meetup.setVisibility(View.INVISIBLE);
            } else {
                buge_indicator_meetup.setVisibility(View.VISIBLE);
            }


        }
    };
    private BroadcastReceiver question_action_meetupscalls = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {

            meetb.setSoundEffectsEnabled(false);
            meetb.performClick();



//         BlankFragment.searchbar.clearFocus();
//                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//               imm.hideSoftInputFromWindow( BlankFragment.searchbar.getWindowToken(), 0);
//
//                MainActivity.click_friend_val="meet";
//
//                MeetsupFragment meetsupFragment = new MeetsupFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.home_fragment1, meetsupFragment);
//                fragmentTransaction.commit();
//
//                createtxt.setVisibility(View.VISIBLE);
//                jointxt.setVisibility(View.VISIBLE);
//                addtxt.setVisibility(View.INVISIBLE);
//                PlusImg1.setVisibility(View.VISIBLE);
//                PlusImg.setVisibility(View.INVISIBLE);
//                qbtn.setVisibility(View.INVISIBLE);
//                joinbtn.setVisibility(View.VISIBLE);
//                progressbar1.setVisibility(View.VISIBLE);
//                progressbar2.setVisibility(View.INVISIBLE);
//
//                meetb.setTextColor(getResources().getColor(R.color.black1));
//                chatb.setTextColor(getResources().getColor(R.color.darkgray));
//                titletxt.setVisibility(View.INVISIBLE);
//                titletxt1.setVisibility(View.VISIBLE);



        }
    };
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

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        v = inflater.inflate(R.layout.fragment_friends, container, false);


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();



        PlusImg = (ImageView) v.findViewById(R.id.plus_img);
        PlusImg1 = (ImageView) v.findViewById(R.id.plus_img1);
        joinbtn = (ImageView) v.findViewById(R.id.joinbtn);
        jointxt = (TextView) v.findViewById(R.id.jointxt);
        qbtn = (ImageView) v.findViewById(R.id.question_btn);
        addtxt = (TextView) v.findViewById(R.id.addtxt);
        createtxt = (TextView) v.findViewById(R.id.createtxt);
        titletxt = (TextView) v.findViewById(R.id.titletxt);
        titletxt1 = (TextView) v.findViewById(R.id.titletxt1);
        rr = (RelativeLayout) v.findViewById(R.id.rrr);
        progressbar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
        progressbar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        chatb = (TextView) v.findViewById(R.id.btnchat);
        meetb = (TextView) v.findViewById(R.id.btnmeet);
        r1 = (ImageView) v.findViewById(R.id.redimageView2);

        str_flagresult="no";


        buge_indicator_chat = (ImageView) v.findViewById(R.id.redimageView2);
        buge_indicator_meetup = (ImageView) v.findViewById(R.id.redimageView3);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UpdateInformation_timer,
                new IntentFilter("updateprofile_timer"));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(question_action_meetupscalls,
                new IntentFilter("question_action_meetupscall"));


        //String a= MainActivity.CountResult;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Communication_friends myTask = new Communication_friends();

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




        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Communication_meetups_event myTask1 = new Communication_meetups_event();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        myTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        myTask1.execute();


                    // Your implementation goes here
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();



        progressbar1.setVisibility(View.INVISIBLE);




        if (pref.getString("gender","").equalsIgnoreCase("Boy"))
        {
            progressbar1.getProgressDrawable().setColorFilter(Color.rgb(207, 222, 244), PorterDuff.Mode.SRC_IN);
            progressbar2.getProgressDrawable().setColorFilter(Color.rgb(207, 222, 244), PorterDuff.Mode.SRC_IN);
        } else {
            progressbar1.getProgressDrawable().setColorFilter(Color.rgb(244, 194, 194), PorterDuff.Mode.SRC_IN);
            progressbar2.getProgressDrawable().setColorFilter(Color.rgb(244, 194, 194), PorterDuff.Mode.SRC_IN);
        }


        titletxt.setTypeface(MainActivity.font_helvitica_bold);
        titletxt1.setTypeface(MainActivity.font_helvitica_bold);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment1, new BlankFragment());
        fragmentTransaction.commit();





   if (MainActivity.click_friend_val.equalsIgnoreCase("meet")) {
            //it if 'profile'
            //tansaction of fragment to Profile Fragment
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_fragment1, new MeetsupFragment());
            fragmentTransaction.commit();
            createtxt.setVisibility(View.VISIBLE);
            jointxt.setVisibility(View.VISIBLE);
            addtxt.setVisibility(View.INVISIBLE);
            PlusImg1.setVisibility(View.VISIBLE);
            PlusImg.setVisibility(View.INVISIBLE);
            qbtn.setVisibility(View.INVISIBLE);
            joinbtn.setVisibility(View.VISIBLE);
            progressbar1.setVisibility(View.VISIBLE);
            progressbar2.setVisibility(View.INVISIBLE);

            titletxt.setVisibility(View.INVISIBLE);
            titletxt1.setVisibility(View.VISIBLE);

       meetb.setTextColor(getResources().getColor(R.color.black1));
       chatb.setTextColor(getResources().getColor(R.color.darkgray));
       meetb.setTypeface(MainActivity.font_helvitica_medium);
       chatb.setTypeface(MainActivity.font_helvitica_medium);

        } else {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_fragment1, new BlankFragment());
            fragmentTransaction.commit();
            addtxt.setVisibility(View.VISIBLE);
            createtxt.setVisibility(View.INVISIBLE);
            PlusImg1.setVisibility(View.INVISIBLE);
            PlusImg.setVisibility(View.VISIBLE);
            jointxt.setVisibility(View.INVISIBLE);
            joinbtn.setVisibility(View.INVISIBLE);
            progressbar1.setVisibility(View.INVISIBLE);
            progressbar2.setVisibility(View.VISIBLE);
            qbtn.setVisibility(View.VISIBLE);

            titletxt1.setVisibility(View.INVISIBLE);
            titletxt.setVisibility(View.VISIBLE);

       chatb.setTextColor(getResources().getColor(R.color.black1));
       meetb.setTextColor(getResources().getColor(R.color.darkgray));
       chatb.setTypeface(MainActivity.font_helvitica_medium);
       meetb.setTypeface(MainActivity.font_helvitica_medium);

        }



        qbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

          if( BlankFragment.searchbar !=null)
          {
           BlankFragment.searchbar.clearFocus();
           InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(BlankFragment.searchbar.getWindowToken(), 0);
          }
           hideKeyboard();

                    Intent intent1 = new Intent("question_action_friend");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

                    editor.putString("question_view","yes");
                    editor.commit();

            }
        });










        createtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( MeetsupFragment.searchbar !=null) {
                    MeetsupFragment.searchbar.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(MeetsupFragment.searchbar.getWindowToken(), 0);
                }

    Intent intent=new Intent(getActivity(),CreateNewActivity.class);
                CreateNewActivity.obj_editmeetups=null;
                CreateNewActivity.str_editeventid=null;
    startActivity(intent);


            }
        });

        jointxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

if ( MeetsupFragment.searchbar !=null)
{
    MeetsupFragment.searchbar.clearFocus();
    InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm1.hideSoftInputFromWindow(MeetsupFragment.searchbar.getWindowToken(), 0);
}
                hideKeyboard();

                LayoutInflater inflater =getActivity().getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                textview_title.setText("Event Code");
                textview_title_dialog.setText("Enter the event code of the event that you wish to join.");
                textview_email_No_enter.setHint("Enter event code");

                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                final AlertDialog dialog1 = alert.create();
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();


                GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                bgShape.mutate();
                bgShape.setColor(Color.WHITE);

                GradientDrawable bgShape1 = (GradientDrawable)textview_email_No_enter.getBackground();
                bgShape1.mutate();
                bgShape1.setColor(Color.WHITE);

                textview_ok.setText("Join");
                textview_ok.setEnabled(false);
                textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        if (textview_email_No_enter.getText().toString().length() < 1 )
                        {

                            textview_ok.setEnabled(false);
                            textview_ok.setTextColor(getResources().getColor(R.color.lightgray));
                        } else {
                            textview_ok.setEnabled(true);
                            textview_ok.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                });


                textview_canced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        hideKeyboard();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);

                        str_eventcodes="";
                        dialog1.dismiss();

                    }
                });

                textview_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        str_eventcodes=textview_email_No_enter.getText().toString();
                        //   str_emailAdd="";
                        dialog1.dismiss();
                        hideKeyboard();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Joining..."); // Setting Message
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Communication_joinfriends myTask = new Communication_joinfriends();

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



            }



        });


        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v)
            {

if ( MeetsupFragment.searchbar !=null) {
    MeetsupFragment.searchbar.clearFocus();
    InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm1.hideSoftInputFromWindow(MeetsupFragment.searchbar.getWindowToken(), 0);
}
                hideKeyboard();

                LayoutInflater inflater =getActivity().getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                textview_title.setText("Event Code");
                textview_title_dialog.setText("Enter the event code of the event that you wish to join.");
                textview_email_No_enter.setHint("Enter event code");

                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                final AlertDialog dialog1 = alert.create();
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();


                GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                bgShape.mutate();
                bgShape.setColor(Color.WHITE);

                GradientDrawable bgShape1 = (GradientDrawable)textview_email_No_enter.getBackground();
                bgShape1.mutate();
                bgShape1.setColor(Color.WHITE);

                textview_ok.setText("Join");
                textview_ok.setEnabled(false);
                textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        if (textview_email_No_enter.getText().toString().length() < 1 )
                        {

                            textview_ok.setEnabled(false);
                            textview_ok.setTextColor(getResources().getColor(R.color.lightgray));
                        } else {
                            textview_ok.setEnabled(true);
                            textview_ok.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                });


                textview_canced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                    hideKeyboard();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);

                        str_eventcodes="";
                        dialog1.dismiss();

                    }
                });

                textview_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        str_eventcodes=textview_email_No_enter.getText().toString();
                     //   str_emailAdd="";
                        dialog1.dismiss();
                        hideKeyboard();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Joining..."); // Setting Message
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Communication_joinfriends myTask = new Communication_joinfriends();

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



            }



        });


        chatb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


if (MeetsupFragment.searchbar !=null)
{
    MeetsupFragment.searchbar.clearFocus();
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(MeetsupFragment.searchbar.getWindowToken(), 0);
}
                meetb.setSoundEffectsEnabled(true);
                MainActivity.click_friend_val="chat";
                BlankFragment blankFragment = new BlankFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home_fragment1, blankFragment);
                fragmentTransaction.commit();
                addtxt.setVisibility(View.VISIBLE);
                createtxt.setVisibility(View.INVISIBLE);
                PlusImg1.setVisibility(View.INVISIBLE);
                PlusImg.setVisibility(View.VISIBLE);
                jointxt.setVisibility(View.INVISIBLE);
                joinbtn.setVisibility(View.INVISIBLE);
                progressbar1.setVisibility(View.INVISIBLE);
                progressbar2.setVisibility(View.VISIBLE);
                qbtn.setVisibility(View.VISIBLE);

                chatb.setTextColor(getResources().getColor(R.color.black1));
                meetb.setTextColor(getResources().getColor(R.color.darkgray));
                titletxt1.setVisibility(View.INVISIBLE);
                titletxt.setVisibility(View.VISIBLE);




            }
        });
        meetb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( BlankFragment.searchbar !=null) {
                    BlankFragment.searchbar.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(BlankFragment.searchbar.getWindowToken(), 0);
                }

                MainActivity.click_friend_val="meet";
                MeetsupFragment meetsupFragment = new MeetsupFragment();

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_fragment1, meetsupFragment);
                    fragmentTransaction.commit();

                createtxt.setVisibility(View.VISIBLE);
                jointxt.setVisibility(View.VISIBLE);
                addtxt.setVisibility(View.INVISIBLE);
                PlusImg1.setVisibility(View.VISIBLE);
                PlusImg.setVisibility(View.INVISIBLE);
                qbtn.setVisibility(View.INVISIBLE);
                joinbtn.setVisibility(View.VISIBLE);
                progressbar1.setVisibility(View.VISIBLE);
                progressbar2.setVisibility(View.INVISIBLE);

                meetb.setTextColor(getResources().getColor(R.color.black1));
                chatb.setTextColor(getResources().getColor(R.color.darkgray));
                titletxt.setVisibility(View.INVISIBLE);
                titletxt1.setVisibility(View.VISIBLE);


            }
        });

        PlusImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( BlankFragment.searchbar !=null) {
                    BlankFragment.searchbar.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(BlankFragment.searchbar.getWindowToken(), 0);
                }
                hideKeyboard();

                Intent i = new Intent(getApplicationContext(), CreateNewActivity.class);
                startActivity(i);
            }
        });


        PlusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {

                if ( BlankFragment.searchbar !=null) {
                    BlankFragment.searchbar.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(BlankFragment.searchbar.getWindowToken(), 0);
                }
                hideKeyboard();

                LayoutInflater inflater =getActivity().getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.custom_dialog_addlist_invites, null);

                TextView textview_facebookinvite = alertLayout.findViewById(R.id.textview_facebook_invite1);
                TextView textview_contactinvite = alertLayout.findViewById(R.id.textview_contacts_invite1);
                TextView textview_emailinvite = alertLayout.findViewById(R.id.textview_email_invite1);
                TextView textview_mobileinvite = alertLayout.findViewById(R.id.textview_mobile_invite1);
                TextView textview_canceinvite = alertLayout.findViewById(R.id.textview_Cancel_invite1);
                TextView textview_post_Fbinvite = alertLayout.findViewById(R.id.textview_post_fb_invite1);

                RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited_list1);


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                final AlertDialog dialog = alert.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                bgShape.mutate();
                bgShape.setColor(Color.WHITE);


                textview_facebookinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog.dismiss();
                        hideKeyboard();
                        Intent intent=new Intent(getActivity(),Facebook_add_friends.class);
                        startActivity(intent);



                    }
                });





                textview_contactinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog.dismiss();
                        hideKeyboard();
Intent intent=new Intent(getActivity(),Contact_lists.class);
Contact_lists.str_invite_type="friend";
startActivity(intent);

                    }
                });


                textview_emailinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog.dismiss();
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                        TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                        TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                        final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                        final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                        final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                        RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                        textview_title.setText("Invite Friends");
                        textview_title_dialog.setText("Enter the email address of the user you wish to invite.");
                        textview_email_No_enter.setHint("Enter email address");
                        textview_email_No_enter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


                        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        // this is set the view from XML inside AlertDialog
                        alert.setView(alertLayout);
                        // disallow cancel of AlertDialog on click of back button and outside touch
                        alert.setCancelable(false);
                        final AlertDialog dialog1 = alert.create();
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.show();


                        GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                        bgShape.mutate();
                        bgShape.setColor(Color.WHITE);

                        GradientDrawable bgShape1 = (GradientDrawable)textview_email_No_enter.getBackground();
                        bgShape1.mutate();
                        bgShape1.setColor(Color.WHITE);

                        textview_ok.setEnabled(false);
                        textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                        textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {



                                if (textview_email_No_enter.getText().toString().length() < 1 )
                                {

                                    textview_ok.setEnabled(false);
                                    textview_ok.setTextColor(getResources().getColor(R.color.lightgray));
                                } else {
                                    textview_ok.setEnabled(true);
                                    textview_ok.setTextColor(getResources().getColor(R.color.colorAccent));
                                }
                            }
                            @Override
                            public void afterTextChanged(Editable s) {

                            }

                        });


                        textview_canced.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                hideKeyboard();
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                dialog1.dismiss();

                            }
                        });

                        textview_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                hideKeyboard();
                                dialog1.dismiss();

                                str_emailAdd=textview_email_No_enter.getText().toString();
                                str_phonenumber="";
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{str_emailAdd});
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download Play:Date");
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, \n\n Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\nI have been using it since a while, and it would be great if you could download it! \n\n Visit http://www.play-date.ae to download it on your mobile phone! \n\n Thanks!");
                                shareIntent.setType("message/rfc822");
                                startActivity(Intent.createChooser(shareIntent, "Pick an Email provider"));

                            }
                        });



                    }
                });

                textview_mobileinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog.dismiss();



                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                        TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                        TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                        final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                        final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                        final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                        RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                        textview_title.setText("Invite Friends");
                        textview_title_dialog.setText("Enter the mobile number with country code of the user you wish to invite.");
                        textview_email_No_enter.setHint("Enter mobile number");
                        textview_email_No_enter.setInputType(InputType.TYPE_CLASS_PHONE);
                        textview_email_No_enter.requestFocus();

                        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        // this is set the view from XML inside AlertDialog
                        alert.setView(alertLayout);
                        // disallow cancel of AlertDialog on click of back button and outside touch
                        alert.setCancelable(false);
                        final AlertDialog dialog1 = alert.create();
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.show();


                        GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                        bgShape.mutate();
                        bgShape.setColor(Color.WHITE);

                        GradientDrawable bgShape1 = (GradientDrawable)textview_email_No_enter.getBackground();
                        bgShape1.mutate();
                        bgShape1.setColor(Color.WHITE);

                        textview_ok.setEnabled(false);
                        textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                        textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {


                                if (textview_email_No_enter.getText().toString().length() < 1 )
                                {

                                    textview_ok.setEnabled(false);
                                    textview_ok.setTextColor(getResources().getColor(R.color.lightgray));
                                } else {
                                    textview_ok.setEnabled(true);
                                    textview_ok.setTextColor(getResources().getColor(R.color.colorAccent));
                                }
                            }
                            @Override
                            public void afterTextChanged(Editable s) {

                            }

                        });


                        textview_canced.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                hideKeyboard();
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                dialog1.dismiss();


                            }
                        });

                        textview_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                hideKeyboard();

                                str_phonenumber=textview_email_No_enter.getText().toString();
                                str_emailAdd="";
                                dialog1.dismiss();
                                String msg= "Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\n Visit http://www.play-date.ae to start using the app today!";
                                Intent sendIntent = new Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sendIntent.setData(Uri.parse("sms:" + str_phonenumber));
                                sendIntent.putExtra("sms_body", msg);
                                startActivity(sendIntent);

                            }
                        });



                    }
                });
                textview_canceinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        hideKeyboard();

                        dialog.dismiss();;


                    }
                });


                textview_post_Fbinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog.dismiss();;

                        ShareDialog shareDialog ;

                        shareDialog = new ShareDialog(getActivity());
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareLinkContent linkContent = new ShareLinkContent.Builder()

                                    .setContentTitle("Download Play:Date from the Google Play Store!")
                                    .setContentDescription(
                                            "Hey, \n\n Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\nI have been using it since a while, and it would be great if you could download it! \n\n Visit http://www.play-date.ae to download it on your mobile phone! \n\n Thanks!")
                                    .setContentUrl(Uri.parse("http://www.play-date.ae/"))
                                    .build();
                            shareDialog.show(linkContent);  // Show facebook ShareDialog
                        }



                    }
                });

            }

        });
        addtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                BlankFragment.searchbar.clearFocus();
//                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow( BlankFragment.searchbar.getWindowToken(), 0);
//                hideKeyboard();
//                PlusImg.performClick();

                if ( BlankFragment.searchbar !=null)
                {
                    BlankFragment.searchbar.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(BlankFragment.searchbar.getWindowToken(), 0);
                }
                    hideKeyboard();

                    LayoutInflater inflater =getActivity().getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.custom_dialog_addlist_invites, null);

                    TextView textview_facebookinvite = alertLayout.findViewById(R.id.textview_facebook_invite1);
                    TextView textview_contactinvite = alertLayout.findViewById(R.id.textview_contacts_invite1);
                    TextView textview_emailinvite = alertLayout.findViewById(R.id.textview_email_invite1);
                    TextView textview_mobileinvite = alertLayout.findViewById(R.id.textview_mobile_invite1);
                    TextView textview_canceinvite = alertLayout.findViewById(R.id.textview_Cancel_invite1);
                    TextView textview_post_Fbinvite = alertLayout.findViewById(R.id.textview_post_fb_invite1);

                    RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited_list1);


                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);
                    final AlertDialog dialog = alert.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();


                    GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                    bgShape.mutate();
                    bgShape.setColor(Color.WHITE);


                    textview_facebookinvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            dialog.dismiss();
                            hideKeyboard();
                            Intent intent=new Intent(getActivity(),Facebook_add_friends.class);
                            startActivity(intent);



                        }
                    });





                    textview_contactinvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            dialog.dismiss();
                            hideKeyboard();
                            Intent intent=new Intent(getActivity(),Contact_lists.class);
                            Contact_lists.str_invite_type="friend";
                            startActivity(intent);

                        }
                    });


                    textview_emailinvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            dialog.dismiss();
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                            TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                            TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                            final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                            final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                            final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                            RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                            textview_title.setText("Invite Friends");
                            textview_title_dialog.setText("Enter the email address of the user you wish to invite.");
                            textview_email_No_enter.setHint("Enter email address");
                            textview_email_No_enter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


                            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            // this is set the view from XML inside AlertDialog
                            alert.setView(alertLayout);
                            // disallow cancel of AlertDialog on click of back button and outside touch
                            alert.setCancelable(false);
                            final AlertDialog dialog1 = alert.create();
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog1.show();


                            GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                            bgShape.mutate();
                            bgShape.setColor(Color.WHITE);

                            GradientDrawable bgShape1 = (GradientDrawable)textview_email_No_enter.getBackground();
                            bgShape1.mutate();
                            bgShape1.setColor(Color.WHITE);

                            textview_ok.setEnabled(false);
                            textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                            textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {



                                    if (textview_email_No_enter.getText().toString().length() < 1 )
                                    {

                                        textview_ok.setEnabled(false);
                                        textview_ok.setTextColor(getResources().getColor(R.color.lightgray));
                                    }
                                    else
                                    {
                                        textview_ok.setEnabled(true);
                                        textview_ok.setTextColor(getResources().getColor(R.color.colorAccent));

                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {

                                }

                            });


                            textview_canced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {

                                    hideKeyboard();
                                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                    dialog1.dismiss();

                                }
                            });

                            textview_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {

                                    hideKeyboard();
                                    dialog1.dismiss();

                                    str_emailAdd=textview_email_No_enter.getText().toString();
                                    str_phonenumber="";
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{str_emailAdd});
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download Play:Date");
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, \n\n Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\nI have been using it since a while, and it would be great if you could download it! \n\n Visit http://www.play-date.ae to download it on your mobile phone! \n\n Thanks!");
                                    shareIntent.setType("message/rfc822");
                                    startActivity(Intent.createChooser(shareIntent, "Pick an Email provider"));

                                }
                            });



                        }
                    });

                    textview_mobileinvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            dialog.dismiss();
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                            TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                            TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                            final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                            final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                            final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                            RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                            textview_title.setText("Invite Friends");
                            textview_title_dialog.setText("Enter the mobile number with country code of the user you wish to invite.");
                            textview_email_No_enter.setHint("Enter mobile number");
                            textview_email_No_enter.setInputType(InputType.TYPE_CLASS_PHONE);
                            textview_email_No_enter.requestFocus();

                            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            // this is set the view from XML inside AlertDialog
                            alert.setView(alertLayout);
                            // disallow cancel of AlertDialog on click of back button and outside touch
                            alert.setCancelable(false);
                            final AlertDialog dialog1 = alert.create();
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog1.show();


                            GradientDrawable bgShape = (GradientDrawable)backround_view.getBackground();
                            bgShape.mutate();
                            bgShape.setColor(Color.WHITE);

                            GradientDrawable bgShape1 = (GradientDrawable)textview_email_No_enter.getBackground();
                            bgShape1.mutate();
                            bgShape1.setColor(Color.WHITE);

                            textview_ok.setEnabled(false);
                            textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                            textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {


                                    if (textview_email_No_enter.getText().toString().length() < 1 )
                                    {

                                        textview_ok.setEnabled(false);
                                        textview_ok.setTextColor(getResources().getColor(R.color.lightgray));
                                    } else {
                                        textview_ok.setEnabled(true);
                                        textview_ok.setTextColor(getResources().getColor(R.color.colorAccent));
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {

                                }

                            });


                            textview_canced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {

                                    hideKeyboard();
                                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                    dialog1.dismiss();


                                }
                            });

                            textview_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {

                                    hideKeyboard();

                                    str_phonenumber=textview_email_No_enter.getText().toString();
                                    str_emailAdd="";
                                    dialog1.dismiss();
                                    String msg= "Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\n Visit http://www.play-date.ae to start using the app today!";
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    sendIntent.setData(Uri.parse("sms:" + str_phonenumber));
                                    sendIntent.putExtra("sms_body", msg);
                                    startActivity(sendIntent);

                                }
                            });



                        }
                    });
                    textview_canceinvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            hideKeyboard();

                            dialog.dismiss();;


                        }
                    });


                    textview_post_Fbinvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {

                            dialog.dismiss();;

                            ShareDialog shareDialog ;

                            shareDialog = new ShareDialog(getActivity());
                            if (ShareDialog.canShow(ShareLinkContent.class)) {
                                ShareLinkContent linkContent = new ShareLinkContent.Builder()

                                        .setContentTitle("Download Play:Date from the Google Play Store!")
                                        .setContentDescription(
                                                "Hey, \n\n Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\nI have been using it since a while, and it would be great if you could download it! \n\n Visit http://www.play-date.ae to download it on your mobile phone! \n\n Thanks!")
                                        .setContentUrl(Uri.parse("http://www.play-date.ae/"))
                                        .build();
                                shareDialog.show(linkContent);  // Show facebook ShareDialog
                            }



                        }
                    });


            }
        });


        if (pref.getString("question_view","").equalsIgnoreCase("no"))
        {
            Intent intent1 = new Intent("question_action_friend");
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

            editor.putString("question_view","yes");
            editor.commit();
        }


        return v;
    }

    public int doSomeTaskjoin() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                FriendResult = joindata();

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

    private String joindata() {

        String text = "";
        BufferedReader reader = null;
        // Send data
        try {


            // Toast.makeText(getApplicationContext(),"in register user",Toast.LENGTH_SHORT).show();
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(UrlClass.joinevent);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("fbid", ""));
            nameValuePair.add(new BasicNameValuePair("eventid", a));
            Log.d("111aaaaaaaaaa", "aaaaaaaa");
            Log.e("ssssssdata", nameValuePair.toString());
            //Toast.makeText(getApplicationContext(),"fname"+fnameString+" ln" +lnameString+" email "+emailString+" mobile "+mobilenoString+" pass "+passString+" radio "+pTypeString,Toast.LENGTH_SHORT).show();


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.e("join Response11", "In Friend: " + responseStr);
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
    // Friens Count Php...

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {

        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(UpdateInformation_timer);


    }

    public class Communication_friends extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.getfrnd);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fbid", pref.getString("userid", ""));
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

//            progressDialog.dismiss();

            JSONArray jsonarray = null;

            if (result != null) {


                if (result.length() >= 30) {

                    try {


                        Array_MatchFriends = new JSONArray();
                        Array_RequestFriends = new JSONArray();
                        Array_MessagesFriends = new JSONArray();
                        Array_AllFriends = new JSONArray(result);
                        for (int i = 0; i < Array_AllFriends.length(); i++) {
                            JSONObject Jsonobject = Array_AllFriends.getJSONObject(i);
                            if (Jsonobject.getString("type").equalsIgnoreCase("match"))
                            {
                                Array_MatchFriends.put(Jsonobject);

                            } else if (Jsonobject.getString("type").equalsIgnoreCase("request"))
                            {
                                Array_RequestFriends.put(Jsonobject);
                            } else
                                {


                                if (Array_MessagesFriends.length() == 0) {
                                    Array_MessagesFriends.put(Jsonobject);
                                } else
                                    {

                                    for (int J = Array_MessagesFriends.length() - 1; J < Array_MessagesFriends.length(); J++) {
                                        JSONObject Jsonobject1 = Array_MessagesFriends.getJSONObject(J);
                                        String fbMatch = Jsonobject.getString("matchedfbid");
                                        String fbMatch2 = Jsonobject1.getString("matchedfbid");

                                        if (!fbMatch2.equalsIgnoreCase(fbMatch)) {

                                            Array_MessagesFriends.put(Jsonobject);
                                            break;
                                        }

                                    }
                                }


                            }

                        }
                        Intent intent1 = new Intent("updatefriends_list");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

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

    public class Communication_joinfriends extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.joinevent);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", str_eventcodes);


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

            JSONArray jsonarray = null;

            if (result != null) {

                if (result.equalsIgnoreCase("error"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
            else if (result.equalsIgnoreCase("inserterror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
                }
                else if (result.equalsIgnoreCase("noeventid"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("no event id.");
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

                }
                else if (result.equalsIgnoreCase("alreadyinvited"))
                {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("alreadyinvited");
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
//                else
//                {
//                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
//                    builder1.setTitle("Oops");
//                    builder1.setMessage("Server could not connect.");
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


            }


        }
    }

    public class Communication_meetups_event extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.events);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fbid", pref.getString("userid", ""));
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

//            progressDialog.dismiss();

            JSONArray jsonarray = null;

            if (result != null) {


                if (result.length() >= 30) {

                    try {

                        Array_MeetupsData_All = new JSONArray();
                        Array_MeetupsData = new JSONArray();

                        Array_MeetupsData_All = new JSONArray(result);



                            for (int i=0; i<Array_MeetupsData_All.length(); i++)
                            {
                                JSONObject Jsonobject = Array_MeetupsData_All.getJSONObject(i);
                                if (Array_MeetupsData.length()== 0)
                                {
                                    Array_MeetupsData.put(Jsonobject);
                                }
                                else
                                {

                                    for (int J=Array_MeetupsData.length()-1; J<Array_MeetupsData.length(); J++)
                                    {

                                        JSONObject Jsonobject1 = Array_MeetupsData.getJSONObject(J);
                                        String fbMatch = Jsonobject.getString("eventid");
                                        String fbMatch2 = Jsonobject1.getString("eventid");

                                        if (!fbMatch2.equalsIgnoreCase(fbMatch)) {

                                            Array_MeetupsData.put(Jsonobject);
                                            break;
                                        }



                                    }
                                }
                            }

                        str_flagresult="no";

                        Intent intent1 = new Intent("updatefriends_list_meetups");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }
                else
                {
                    if (result.equalsIgnoreCase("[]"))
                    {
                        if (str_flagresult.equalsIgnoreCase("no")) {
                            Array_MeetupsData_All = new JSONArray();
                            Array_MeetupsData = new JSONArray();

                            Intent intent1 = new Intent("updatefriends_list_meetups");
                            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

                            str_flagresult="yes";
                        }
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
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}