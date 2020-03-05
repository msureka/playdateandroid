package com.bba.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bba.playdate1.Add_profilematch_Activity;
import com.bba.playdate1.Data;
import com.bba.playdate1.MainActivity;
import com.bba.playdate1.R;
import com.bba.playdate1.SettingActivity;
import com.bba.playdate1.UrlClass;
import com.bba.playdate1.ViewAdMatchActivity;
import com.bba.playdate1.ViewMatchActivity;
import com.bba.playdate1.WhereActivity;
import com.bba.playdate1.setting_meetups_user1;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Callback;
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
import org.json.JSONArray;
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
public class DiscoverFragment extends Fragment {

    android.support.v7.app.AlertDialog alertDialog_Box;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static ArrayList<Data> array;
    String SwipeFbid = "";
    public static String matchswipe = "no";

    ViewHolder viewHolder;
    public List<Data> parkingList;
    public String TabCount = "2";
    public String FlagFbid, str_flag_desc = "", str_FlagStatus = "";
    public String SwipeValue;
    //Main Screen Variable
    ImageView ProfileImg, GenderImg;
    TextView PlayDateTxt, LodingTxt;
    //element declaration
    View v;
    int totalrow = 0;

    String FlagResult, SwipeResult;
    int Positionvalue;
    String a11, resultflag;
    ImageView pb;
    TextView NomoreProfileTxt, DiscoverPlaceTXt, txt_taplod;
    Button ChangeDiscoverBtn;
    RelativeLayout TipRow;
    //progressbar variable
    ProgressDialog progressBar;


    int PRIVATE_MODE = 0;


    //Alert Dailog variable
    android.app.AlertDialog alert;
    String Type, Title, AdDescription, SubTitle, ColorRed, ColorGreen, ColorBlue, ImageTitle, ImageLogo1, ImageLogo2, ImageLogo3, FbId, UserId, EmailId, Fname, Gender, BirthDate, EngLang, ArbicLang, FrenchLang, City, Country, Description, LikeToPlay, ICanMeet, Activity1, Activity2, Activity3, Emoji1, Emoji2, Emoji3, SuperHero, ProfilePic, Age;
    String PopupMatchFbid, PopupMatchDate, PopupType, PopupTitle, PopupAdDescription, PopupSubTitle, PopupColorRed, PopupColorGreen, PopupColorBlue, PopupImageTitle, PopupImageLogo1, PopupImageLogo2, PopupImageLogo3, PopupUserId, PopupEmailId, PopupFname, PopupGender, PopupEngLang, PopupArbicLang, PopupFrenchLang, PopupCity, PopupCountry, PopupDescription, PopupLikeToPlay, PopupICanMeet, PopupActivity1, PopupActivity2, PopupActivity3, PopupEmoji1, PopupEmoji2, PopupEmoji3, PopupSuperHero, PopupProfilePic, PopupAge, PopUpGagaPic;
    private SwipeFlingAdapterView flingContainer;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        v = inflater.inflate(R.layout.fragment_discover, container, false);

        flingContainer = (SwipeFlingAdapterView) v.findViewById(R.id.frame);
        ProfileImg = (ImageView) v.findViewById(R.id.profileImgview);
        GenderImg = (ImageView) v.findViewById(R.id.back_imgview);
        // PlayDateTxt = (TextView) v.findViewById(R.id.playdate_txt);
        pb = (ImageView) v.findViewById(R.id.progressBar1);
        LodingTxt = (TextView) v.findViewById(R.id.textView4);
        DiscoverPlaceTXt = (TextView) v.findViewById(R.id.discover_txt);
        NomoreProfileTxt = (TextView) v.findViewById(R.id.textView5);
        txt_taplod = (TextView) v.findViewById(R.id.textView55);
        ChangeDiscoverBtn = (Button) v.findViewById(R.id.searchbtn);
        TipRow = (RelativeLayout) v.findViewById(R.id.tiprow);

        TextView txt_playdate_heding = (TextView) v.findViewById(R.id.playdate_txt_title);


        Typeface font_kg = Typeface.createFromAsset(getActivity().getAssets(), "font/kg_feeling_regular.ttf");
        txt_playdate_heding.setTypeface(font_kg);

        LodingTxt.setEnabled(false);

        RotateAnimation rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setRepeatCount(Animation.INFINITE);
        pb.startAnimation(rotate);


        array = new ArrayList<>();

        //Load Profiles
        //  getProfiles();

        Communication_LookingProfile myTask = new Communication_LookingProfile();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            myTask.execute();

        ProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Load Profiles
                //  getProfiles();

                Communication_LookingProfile myTask = new Communication_LookingProfile();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    myTask.execute();

                RotateAnimation rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setRepeatMode(Animation.REVERSE);
                rotate.setRepeatCount(Animation.INFINITE);
                pb.startAnimation(rotate);
                pb.setVisibility(View.VISIBLE);
                TipRow.setVisibility(View.INVISIBLE);
                NomoreProfileTxt.setVisibility(View.INVISIBLE);

                txt_taplod.setVisibility(View.INVISIBLE);
                LodingTxt.setVisibility(View.VISIBLE);
                LodingTxt.setText("Looking for profiles...");

            }
        });

        ChangeDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingActivity.whereflag = "no";
                Intent intent = new Intent(getActivity(), WhereActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
            }
        });

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }


            @Override
            public void onLeftCardExit(Object dataObject) {


String s1=pref.getString("leftswipe","");

                if (pref.getString("leftswipe","").equalsIgnoreCase("no") || pref.getString("leftswipe","").equalsIgnoreCase(""))

                {


                    SwipeFbid = "";
                    SwipeFbid = parkingList.get(0).getFbId();
                    SwipeValue = "LEFT";
                    // Toast.makeText(getActivity().getApplicationContext(),"Position :"+0+"  swipe LEFT"+SwipeFbid,Toast.LENGTH_LONG).show();



                    new AlertDialog.Builder(getActivity())
                            .setTitle("Tip: Left swipes")
                            .setMessage("If you left swipe on a profile it will be discarded and will not show up again on your search. Are you sure?")
                            .setPositiveButton("Not Interested", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    doSomeTask2();

                                    array.remove(0);
                                    parkingList = array;
                                    totalrow = array.size();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            flingContainer.invalidate();
                                            ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();

                                        }
                                    });

                                    if (array.size()==0)
                                    {
                                        Communication_LookingProfile myTask = new Communication_LookingProfile();

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        else
                                            myTask.execute();
                                    }

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                    parkingList = array;
                                    totalrow = array.size();

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            flingContainer.invalidate();
                                            ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();

                                        }
                                    });
                                }
                            })
                            .show();



                }
                else
                {

                    SwipeFbid = "";
                    SwipeFbid = parkingList.get(0).getFbId();
                    SwipeValue = "LEFT";



                    array.remove(0);
                    parkingList = array;
                    totalrow = array.size();

//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {

                            flingContainer.invalidate();
                            ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();

//                        }
//                    });
                    doSomeTask2();
                    if (array.size()==0)
                    {
                        Communication_LookingProfile myTask = new Communication_LookingProfile();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else
                            myTask.execute();
                    }

                }


                editor.putString("leftswipe","yes");
                editor.commit();








            }

            @Override
            public void onRightCardExit(Object dataObject) {

                SwipeFbid = "";
                SwipeFbid = parkingList.get(0).getFbId();
                SwipeValue = "RIGHT";
                // Toast.makeText(getActivity().getApplicationContext(), "Position :" + 0 + "  swipe Right" + SwipeFbid, Toast.LENGTH_LONG).show();
                doSomeTask2();

                array.remove(0);
                totalrow = array.size();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        flingContainer.invalidate();
                        ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();

                    }
                });

                if (array.size()==0)
                {
                    Communication_LookingProfile myTask = new Communication_LookingProfile();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        myTask.execute();
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                // view.findViewById(R.id.background).setAlpha(1.0);

                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();

                if (parkingList.get(itemPosition).getType().equalsIgnoreCase("PROFILE")) {

                    view.findViewById(R.id.overlapImage).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.gagaImg).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.logo1_img).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.logo2_img).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.logo3_img).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.title_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.subtitle_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.addesc_txt).setVisibility(View.INVISIBLE);

                    if (TabCount.equalsIgnoreCase("2")) {
                        TabCount = "1";

                        ImageView im = (ImageView) view.findViewById(R.id.circle_img);
                        im.setImageResource(R.drawable.circle2);
                        //invisible view 1
                        view.findViewById(R.id.back_imgview).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.profileImgview).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.name_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.place_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.emoji1_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.emoji2_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.emoji3_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.desc_txt).setVisibility(View.INVISIBLE);

                        //visible view 2
                        view.findViewById(R.id.speaktxt).setVisibility(View.VISIBLE);

                        //set language
                        if (parkingList.get(itemPosition).getFrenchLang().equalsIgnoreCase("") || parkingList.get(itemPosition).getFrenchLang() == null) {

                            //viewHolder.Lang1.setVisibility(View.INVISIBLE);
                            if (parkingList.get(itemPosition).getArbicLang().equalsIgnoreCase("") || parkingList.get(itemPosition).getArbicLang() == null) {
                                view.findViewById(R.id.lang1_txt).setVisibility(View.VISIBLE);
                                view.findViewById(R.id.lang2_txt).setVisibility(View.INVISIBLE);
                                view.findViewById(R.id.lang3_txt).setVisibility(View.INVISIBLE);
                            } else {
                                view.findViewById(R.id.lang1_txt).setVisibility(View.INVISIBLE);
                                view.findViewById(R.id.lang2_txt).setVisibility(View.VISIBLE);
                                view.findViewById(R.id.lang3_txt).setVisibility(View.VISIBLE);
                            }
                        } else {
                            view.findViewById(R.id.lang1_txt).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.lang2_txt).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.lang3_txt).setVisibility(View.VISIBLE);

                        }
                        view.findViewById(R.id.liketxt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.likeimg).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.likevalue_txt1).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.meettxt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.meetimg).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.meetvalue_txt1).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.favtxt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.act1).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.act2).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.act3).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.super_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.super_value_txt).setVisibility(View.VISIBLE);


                    } else {
                        TabCount = "2";
                        ImageView im = (ImageView) view.findViewById(R.id.circle_img);
                        im.setImageResource(R.drawable.circle1);
                        //invisible view 1
                        view.findViewById(R.id.back_imgview).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.profileImgview).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.name_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.place_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.emoji1_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.emoji2_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.emoji3_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.desc_txt).setVisibility(View.VISIBLE);


                        //visible view 2
                        view.findViewById(R.id.speaktxt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.lang1_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.lang2_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.lang3_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.liketxt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.likeimg).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.likevalue_txt1).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.meettxt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.meetimg).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.meetvalue_txt1).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.favtxt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.act1).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.act2).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.act3).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.super_txt).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.super_value_txt).setVisibility(View.INVISIBLE);
                    }
                } else {

                    view.findViewById(R.id.overlapImage).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.gagaImg).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.logo1_img).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.logo2_img).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.logo3_img).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.title_txt).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.subtitle_txt).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.addesc_txt).setVisibility(View.VISIBLE);

                    view.findViewById(R.id.desc_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.profileImgview).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.back_imgview).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.name_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.place_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.lang1_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.lang2_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.lang3_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.likeimg).setVisibility(View.INVISIBLE);

                    view.findViewById(R.id.likevalue_txt1).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.meetimg).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.meetvalue_txt1).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.act1).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.act2).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.act3).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.super_value_txt).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.circle_img).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.flag_img).setVisibility(View.INVISIBLE);
                }


            }
        });


        MyAppAdapter myAppAdapter = new MyAppAdapter();
        flingContainer.setAdapter(myAppAdapter);


        return v;
    }




    // Async Method to Call HTTP Connection Metod in Background
    public int doSomeTask1() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                resultflag = setFlagProfile();

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

    // Async Method to Call HTTP Connection Metod in Background
    public int doSomeTask2() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected Void doInBackground(final Void... params) {
                SwipeResult = swipe();
                return null;
            }

            @Override
            protected void onPostExecute(final Void result) {

                if (SwipeResult.equalsIgnoreCase("nullerror")) {

                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getActivity());
                    builder1.setMessage("Something is Missing..");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    alert = builder1.create();
                    alert.show();

                } else if (SwipeResult.equalsIgnoreCase("nofbid")) {

                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getActivity());
                    builder1.setMessage("FdID is Null..");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    alert = builder1.create();
                    alert.show();

                } else if (SwipeResult.equalsIgnoreCase("")) {
                    matchswipe = "yes";
                } else {
                    // Intent intent = new Intent(getActivity(),ViewMatchActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // intent.putExtras(eventname_bundle);
                    // startActivity(intent);
                    // getActivity().overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    setPopUp();
                }

            }
        }.execute();

        return 100;
    }

    //Actual Function handling http connection and  Checking User Deatails on Server
    public String setFlagProfile() {


        String text = "";
        BufferedReader reader = null;
        // Send data
        try {

            Log.d("fmtyhtgfm", a11);
            // Toast.makeText(getApplicationContext(),"in register user",Toast.LENGTH_SHORT).show();
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(UrlClass.flagprofile);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
//            nameValuePair.add(new BasicNameValuePair("fbid1", WelcomeActivity.FBCity));
            nameValuePair.add(new BasicNameValuePair("fbid2", FlagFbid));
            nameValuePair.add(new BasicNameValuePair("flagreason", "a11"));
            nameValuePair.add(new BasicNameValuePair("flagfrom", ""));

            Log.e("data", nameValuePair.toString());
            //Toast.makeText(getApplicationContext(),"fname"+fnameString+" ln" +lnameString+" email "+emailString+" mobile "+mobilenoString+" pass "+passString+" radio "+pTypeString,Toast.LENGTH_SHORT).show();


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.e("Flag Response", "In Discover: " + responseStr);
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

    //Actual Function handling http connection and set swipeon Server
    public String swipe() {


        String text = "";
        BufferedReader reader = null;
        // Send data
        try {


            // Toast.makeText(getApplicationContext(),"in register user",Toast.LENGTH_SHORT).show();
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(UrlClass.swipe); //Url Passing

            // Name Value List for element passing to php.
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("fbid1", pref.getString("userid", "")));
            nameValuePair.add(new BasicNameValuePair("fbid2", SwipeFbid));
            nameValuePair.add(new BasicNameValuePair("swipe", SwipeValue));


            Log.e("data", nameValuePair.toString());
            //Toast.makeText(getApplicationContext(),"fname"+fnameString+" ln" +lnameString+" email "+emailString+" mobile "+mobilenoString+" pass "+passString+" radio "+pTypeString,Toast.LENGTH_SHORT).show();


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = httpClient.execute(httpPost); // Http post Execution
                HttpEntity resEntity = response.getEntity(); //response

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.e("Swipe Response", "In Discover: " + responseStr);
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

    //Actual Function handling http connection and  flag profile on Server
    public String getUsersProfiles() {


        String text = "";
        BufferedReader reader = null;
        // Send data
        try {


            // Toast.makeText(getApplicationContext(),"in register user",Toast.LENGTH_SHORT).show();
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();

            // Creating HTTP Post
            HttpPost httpPost = new HttpPost(UrlClass.getprofiles);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);


            nameValuePair.add(new BasicNameValuePair("fbid", pref.getString("userid", "")));
            nameValuePair.add(new BasicNameValuePair("distance", pref.getString("distance", "")));
            nameValuePair.add(new BasicNameValuePair("agegroup", pref.getString("agegroup", "")));
            nameValuePair.add(new BasicNameValuePair("makefriendswith", pref.getString("makefriendswith", "")));
            nameValuePair.add(new BasicNameValuePair("city", pref.getString("city", "")));
            nameValuePair.add(new BasicNameValuePair("country", pref.getString("country", "")));
            nameValuePair.add(new BasicNameValuePair("birthdate", pref.getString("birthdate", "")));

            Log.e("getprofiledata", nameValuePair.toString());
            //Toast.makeText(getApplicationContext(),"fname"+fnameString+" ln" +lnameString+" email "+emailString+" mobile "+mobilenoString+" pass "+passString+" radio "+pTypeString,Toast.LENGTH_SHORT).show();


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    // Log.e("UpdateUser Response", "In SuperHero: " +  responseStr);
                    Log.i("UpdateUser Response", "In SuperHero: " + responseStr);
                    text = responseStr;

                    // you can add an if statement here and do other actions based on the response
                }

            } catch (UnsupportedEncodingException e) {
                // writing error to Log
                text = "neterror";
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder1.setTitle("Oops");
                builder1.setMessage("Server time out!");
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
                Log.e("in catch 1", e.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {

            Handler mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message message) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Server time out!");
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
            };


            //loading.dismiss();

        }
        return text;
    }

    public void setPopUp() {
        try {


            JSONArray jsonarray = new JSONArray(SwipeResult);

            JSONObject obj = new JSONObject(jsonarray.getString(0));

            PopupType = obj.getString("profiletype");
            Log.d("discoverdate", PopupType);
            if (obj.getString("profiletype").equalsIgnoreCase("AD")) {

                if (obj.getString("adstatus").equalsIgnoreCase("ACTIVE")) {

//                        Intent intent = new Intent(getActivity(), ViewAdMatchActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("data_swipe", jsonarray.toString());
//                        startActivity(intent);

                    Intent intent = new Intent(getActivity(), Add_profilematch_Activity.class);
                    Add_profilematch_Activity.chatsrecord_add = obj;
                    startActivity(intent);

                } else if (obj.getString("adstatus").equalsIgnoreCase("DEACTIVE")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("You seem to have missed out on the promotion! Better luck next time!");
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
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("You seem to have missed out on the promotion as it has been sold-out! Better luck next time!");
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


            } else

            {

                Intent intent = new Intent(getActivity(), ViewMatchActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ViewMatchActivity.chatsrecord = obj;

                startActivity(intent);

            }


        } catch (Exception e) {
            Log.e("Json Exception ", e.toString());
        }

    }

    public static class ViewHolder {
        public static FrameLayout background;

        public ImageView BackImg, ProfileImg, CircleImg, FlagImg, LikeImg, MeetImg,
                AdRebbinImg, AdMainImg, AdImgLogo1, AdImgLogo2, AdImgLogo3, AdImgLogo4, AdImgLogo5;
        public TextView NameTxt, PlaceTxt, Lang1, Lang2, Lang3, E1, E2, E3, LikeTxt, MeetTxt,
                Act1Txt, Act2Txt, Act3Txt, SuperTxt, DescriptionTxt, speaktxttitle, meettxt_title, AdsubTitleTXt,
                AdTitleTxt, liketxt_title, AdDescTxt, favtxt_title, super_txt_title;


    }

    private class MyAppAdapter extends BaseAdapter {

        public Context context;
        View rowView;
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
//        private MyAppAdapter(ArrayList<Data> apps, Context context) {
//            parkingList = apps;

//            this.context = context;
//            //Toast.makeText(getActivity().getApplicationContext(),"data"+parkingList.get(0).getDescription(),Toast.LENGTH_LONG).show();
//
//
//        }
//

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getCount() {
            return totalrow;
        }


        @Override
        @SuppressWarnings("deprecation")
        public View getView(int position, View convertView, ViewGroup parent) {

            rowView = convertView;

            //Setup Font


            if (rowView == null) {

                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();

                viewHolder.DescriptionTxt = (TextView) rowView.findViewById(R.id.desc_txt);
                viewHolder.ProfileImg = (ImageView) rowView.findViewById(R.id.profileImgview);
                viewHolder.BackImg = (ImageView) rowView.findViewById(R.id.back_imgview);
                viewHolder.NameTxt = (TextView) rowView.findViewById(R.id.name_txt);
                viewHolder.PlaceTxt = (TextView) rowView.findViewById(R.id.place_txt);
                viewHolder.Lang1 = (TextView) rowView.findViewById(R.id.lang1_txt);
                viewHolder.Lang2 = (TextView) rowView.findViewById(R.id.lang2_txt);
                viewHolder.Lang3 = (TextView) rowView.findViewById(R.id.lang3_txt);
                viewHolder.E1 = (TextView) rowView.findViewById(R.id.emoji1_txt);
                viewHolder.E2 = (TextView) rowView.findViewById(R.id.emoji2_txt);
                viewHolder.E3 = (TextView) rowView.findViewById(R.id.emoji3_txt);
                viewHolder.LikeImg = (ImageView) rowView.findViewById(R.id.likeimg);
                viewHolder.LikeTxt = (TextView) rowView.findViewById(R.id.likevalue_txt1);
                viewHolder.MeetImg = (ImageView) rowView.findViewById(R.id.meetimg);
                viewHolder.MeetTxt = (TextView) rowView.findViewById(R.id.meetvalue_txt1);
                viewHolder.Act1Txt = (TextView) rowView.findViewById(R.id.act1);
                viewHolder.Act2Txt = (TextView) rowView.findViewById(R.id.act2);
                viewHolder.Act3Txt = (TextView) rowView.findViewById(R.id.act3);
                viewHolder.SuperTxt = (TextView) rowView.findViewById(R.id.super_value_txt);
                viewHolder.CircleImg = (ImageView) rowView.findViewById(R.id.circle_img);
                viewHolder.FlagImg = (ImageView) rowView.findViewById(R.id.flag_img);
                viewHolder.AdRebbinImg = (ImageView) rowView.findViewById(R.id.overlapImage);
                viewHolder.AdMainImg = (ImageView) rowView.findViewById(R.id.gagaImg);
                viewHolder.AdImgLogo1 = (ImageView) rowView.findViewById(R.id.logo1_img);
                viewHolder.AdImgLogo2 = (ImageView) rowView.findViewById(R.id.logo2_img);
                viewHolder.AdImgLogo3 = (ImageView) rowView.findViewById(R.id.logo3_img);
                viewHolder.AdImgLogo4 = (ImageView) rowView.findViewById(R.id.logo4_img);
                viewHolder.AdImgLogo5 = (ImageView) rowView.findViewById(R.id.logo5_img);

                viewHolder.AdTitleTxt = (TextView) rowView.findViewById(R.id.title_txt);
                viewHolder.AdsubTitleTXt = (TextView) rowView.findViewById(R.id.subtitle_txt);
                viewHolder.AdDescTxt = (TextView) rowView.findViewById(R.id.addesc_txt);
                viewHolder.speaktxttitle = (TextView) rowView.findViewById(R.id.speaktxt);
                viewHolder.liketxt_title = (TextView) rowView.findViewById(R.id.liketxt);
                viewHolder.meettxt_title = (TextView) rowView.findViewById(R.id.meettxt);
                viewHolder.favtxt_title = (TextView) rowView.findViewById(R.id.favtxt);
                viewHolder.super_txt_title = (TextView) rowView.findViewById(R.id.super_txt);


                viewHolder.Lang1.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.Lang2.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.Lang3.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.LikeTxt.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.MeetTxt.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.Act1Txt.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.Act2Txt.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.Act3Txt.setTypeface(MainActivity.font_helvitica_bold);
                viewHolder.SuperTxt.setTypeface(MainActivity.font_helvitica_bold);

                viewHolder.speaktxttitle.setTypeface(MainActivity.font_helvitica_light);
                viewHolder.liketxt_title.setTypeface(MainActivity.font_helvitica_light);
                viewHolder.meettxt_title.setTypeface(MainActivity.font_helvitica_light);
                viewHolder.favtxt_title.setTypeface(MainActivity.font_helvitica_light);
                viewHolder.super_txt_title.setTypeface(MainActivity.font_helvitica_light);

                viewHolder.PlaceTxt.setTypeface(MainActivity.Font_helvetica_regular);
                viewHolder.DescriptionTxt.setTypeface(MainActivity.Font_helvetica_regular);


                viewHolder.NameTxt.setTypeface(MainActivity.font_kg);


                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (parkingList.get(position).getType().equalsIgnoreCase("PROFILE")) {
                viewHolder.DescriptionTxt.setVisibility(View.VISIBLE);
                viewHolder.ProfileImg.setVisibility(View.VISIBLE);
                viewHolder.BackImg.setVisibility(View.VISIBLE);
                viewHolder.NameTxt.setVisibility(View.VISIBLE);
                viewHolder.PlaceTxt.setVisibility(View.VISIBLE);
                viewHolder.E1.setVisibility(View.VISIBLE);
                viewHolder.E2.setVisibility(View.VISIBLE);
                viewHolder.E3.setVisibility(View.VISIBLE);
                viewHolder.Lang1.setVisibility(View.INVISIBLE);
                viewHolder.Lang2.setVisibility(View.INVISIBLE);
                viewHolder.Lang3.setVisibility(View.INVISIBLE);
                viewHolder.LikeImg.setVisibility(View.INVISIBLE);
                viewHolder.LikeTxt.setVisibility(View.INVISIBLE);
                viewHolder.MeetImg.setVisibility(View.INVISIBLE);
                viewHolder.MeetTxt.setVisibility(View.INVISIBLE);
                viewHolder.Act1Txt.setVisibility(View.INVISIBLE);
                viewHolder.Act2Txt.setVisibility(View.INVISIBLE);
                viewHolder.Act3Txt.setVisibility(View.INVISIBLE);
                viewHolder.SuperTxt.setVisibility(View.INVISIBLE);
                viewHolder.CircleImg.setVisibility(View.VISIBLE);
                viewHolder.FlagImg.setVisibility(View.VISIBLE);
                viewHolder.AdRebbinImg.setVisibility(View.INVISIBLE);
                viewHolder.AdMainImg.setVisibility(View.INVISIBLE);
                viewHolder.AdImgLogo1.setVisibility(View.INVISIBLE);
                viewHolder.AdImgLogo2.setVisibility(View.INVISIBLE);
                viewHolder.AdImgLogo3.setVisibility(View.INVISIBLE);
                viewHolder.AdImgLogo4.setVisibility(View.INVISIBLE);
                viewHolder.AdImgLogo5.setVisibility(View.INVISIBLE);

                viewHolder.AdTitleTxt.setVisibility(View.INVISIBLE);
                viewHolder.AdsubTitleTXt.setVisibility(View.INVISIBLE);
                viewHolder.AdDescTxt.setVisibility(View.INVISIBLE);

                Positionvalue = position;

                viewHolder.DescriptionTxt.setLineSpacing(1.5f,1.2f);
                viewHolder.DescriptionTxt.setText(parkingList.get(position).getDescription() + " ");
                viewHolder.NameTxt.setText(parkingList.get(position).getFname() + ", " + parkingList.get(position).getAge());
                viewHolder.PlaceTxt.setText(parkingList.get(position).getCity() + ",  " + parkingList.get(position).getCountry());

                viewHolder.E1.setText(parkingList.get(position).getEmoji1());
                viewHolder.E2.setText(parkingList.get(position).getEmoji2());
                viewHolder.E3.setText(parkingList.get(position).getEmoji3());


                //set language
                if (parkingList.get(position).getFrenchLang().equalsIgnoreCase("") || parkingList.get(position).getFrenchLang() == null) {
                    if (parkingList.get(position).getArbicLang().equalsIgnoreCase("") || parkingList.get(position).getArbicLang() == null) {
                        viewHolder.Lang2.setText("");
                        viewHolder.Lang3.setText("");
                        viewHolder.Lang1.setText((parkingList.get(position).getEngLang()));
                    } else {
                        viewHolder.Lang1.setText("");
                        viewHolder.Lang2.setText((parkingList.get(position).getEngLang()));
                        viewHolder.Lang3.setText((parkingList.get(position).getArbicLang()));
                    }
                } else {
                    viewHolder.Lang1.setText((parkingList.get(position).getArbicLang()));
                    viewHolder.Lang2.setText((parkingList.get(position).getEngLang()));
                    viewHolder.Lang3.setText((parkingList.get(position).getFrenchLang()));
                }

                //set I Like 2 Play Value
                viewHolder.LikeTxt.setText((parkingList.get(position).getLikeToPlay()));
                if (parkingList.get(position).getLikeToPlay().equalsIgnoreCase("Indoor")) {
                    viewHolder.LikeImg.setImageResource(R.drawable.indoor);
                } else if (parkingList.get(position).getLikeToPlay().equalsIgnoreCase("Outdoor")) {
                    viewHolder.LikeImg.setImageResource(R.drawable.outdoor);
                } else {
                    viewHolder.LikeImg.setImageResource(R.drawable.everywhere);
                }

                //set I can meet Value
                viewHolder.MeetTxt.setText((parkingList.get(position).getICanMeet()));
                if (parkingList.get(position).getICanMeet().equalsIgnoreCase("After school")) {
                    viewHolder.MeetImg.setImageResource(R.drawable.afterschool);
                } else if (parkingList.get(position).getICanMeet().equalsIgnoreCase("Mornings")) {
                    viewHolder.MeetImg.setImageResource(R.drawable.mornings);
                } else {
                    viewHolder.MeetImg.setImageResource(R.drawable.anytime);
                }

                // set Activities
                viewHolder.Act1Txt.setText(parkingList.get(position).getActivity1());
                viewHolder.Act2Txt.setText(parkingList.get(position).getActivity2());
                viewHolder.Act3Txt.setText(parkingList.get(position).getActivity3());

                //set SuperHero
                viewHolder.SuperTxt.setText(parkingList.get(position).getSuperHero());

                if (parkingList.get(position).getGender().equalsIgnoreCase("BOY")) {
                    viewHolder.BackImg.setImageResource(R.drawable.boypictureframe);
                } else {
                    viewHolder.BackImg.setImageResource(R.drawable.girlpictureframe);
                }
                try {

                    Picasso.with(getActivity())
                            .load(parkingList.get(position).getProfilePic())
                            .placeholder(R.drawable.defaultboy)
                            //.memoryPolicy(MemoryPolicy.NO_CACHE)// optional
                            //.error(R.drawable.error)       // optional
                            .resize(200, 200)               // optional
                            .into(viewHolder.ProfileImg, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });
                } catch (Exception e) {
                    Log.e(" ", e.toString());
                }
                viewHolder.FlagImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {


                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.custom_dialog_invite_email_mobile, null);
                        TextView textview_title = alertLayout.findViewById(R.id.textview_alert_invite_title);
                        TextView textview_title_dialog = alertLayout.findViewById(R.id.textview_alert_invite_message);
                        final EditText textview_email_No_enter = alertLayout.findViewById(R.id.textview_emailtext);
                        final TextView textview_canced = alertLayout.findViewById(R.id.textview_alert_msg_cancel);
                        final TextView textview_ok = alertLayout.findViewById(R.id.textview_alert_msg_ok);

                        RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited2);

                        textview_title.setText("Flag Reason?");
                        textview_title_dialog.setText("Please mention the reason of flagging this user:");
                        textview_email_No_enter.setHint("Remark");


                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        // this is set the view from XML inside AlertDialog
                        alert.setView(alertLayout);
                        // disallow cancel of AlertDialog on click of back button and outside touch
                        alert.setCancelable(false);
                        final AlertDialog dialog1 = alert.create();
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.show();


                        GradientDrawable bgShape = (GradientDrawable) backround_view.getBackground();
                        bgShape.mutate();
                        bgShape.setColor(Color.WHITE);

                        GradientDrawable bgShape1 = (GradientDrawable) textview_email_No_enter.getBackground();
                        bgShape1.mutate();
                        bgShape1.setColor(Color.WHITE);
                        textview_ok.setText("Flag");
                        textview_ok.setEnabled(false);
                        textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                        textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {


                                if (textview_email_No_enter.getText().toString().length() < 1) {

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
                            public void onClick(View view) {

                                dialog1.dismiss();

                            }
                        });

                        textview_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog1.dismiss();
                                str_FlagStatus = "Flagyes";
                                str_flag_desc = textview_email_No_enter.getText().toString();

                                SwipeFbid = "";
                                SwipeFbid = parkingList.get(0).getFbId();
                                SwipeValue = "LEFT";


                                Intent intent1 = new Intent("discover_buttontap_forswipe");
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);


                                doSomeTask2();


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            Communication_flag myTask = new Communication_flag();

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
            } else {

                viewHolder.DescriptionTxt.setVisibility(View.INVISIBLE);
                viewHolder.ProfileImg.setVisibility(View.INVISIBLE);
                viewHolder.BackImg.setVisibility(View.INVISIBLE);
                viewHolder.NameTxt.setVisibility(View.INVISIBLE);
                viewHolder.PlaceTxt.setVisibility(View.INVISIBLE);
                viewHolder.Lang1.setVisibility(View.INVISIBLE);
                viewHolder.Lang2.setVisibility(View.INVISIBLE);
                viewHolder.Lang3.setVisibility(View.INVISIBLE);
                viewHolder.E1.setVisibility(View.INVISIBLE);
                viewHolder.E2.setVisibility(View.INVISIBLE);
                viewHolder.E3.setVisibility(View.INVISIBLE);
                viewHolder.LikeImg.setVisibility(View.INVISIBLE);
                viewHolder.LikeTxt.setVisibility(View.INVISIBLE);
                viewHolder.MeetImg.setVisibility(View.INVISIBLE);
                viewHolder.MeetTxt.setVisibility(View.INVISIBLE);
                viewHolder.Act1Txt.setVisibility(View.INVISIBLE);
                viewHolder.Act2Txt.setVisibility(View.INVISIBLE);
                viewHolder.Act3Txt.setVisibility(View.INVISIBLE);
                viewHolder.SuperTxt.setVisibility(View.INVISIBLE);
                viewHolder.CircleImg.setVisibility(View.INVISIBLE);
                viewHolder.FlagImg.setVisibility(View.INVISIBLE);
                viewHolder.AdRebbinImg.setVisibility(View.VISIBLE);
                viewHolder.AdMainImg.setVisibility(View.VISIBLE);

                viewHolder.AdImgLogo1.setVisibility(View.VISIBLE);
                viewHolder.AdImgLogo2.setVisibility(View.VISIBLE);
                viewHolder.AdImgLogo3.setVisibility(View.VISIBLE);
                viewHolder.AdImgLogo4.setVisibility(View.VISIBLE);
                viewHolder.AdImgLogo5.setVisibility(View.VISIBLE);



                viewHolder.AdTitleTxt.setVisibility(View.VISIBLE);
                viewHolder.AdsubTitleTXt.setVisibility(View.VISIBLE);
                viewHolder.AdDescTxt.setVisibility(View.VISIBLE);

                Display display = getActivity().getWindowManager().getDefaultDisplay();
                int width = display.getWidth(); // ((display.getWidth()*20)/100)
                int height = display.getHeight();// ((display.getHeight()*30)/100)
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,display.getWidth()-(display.getHeight()/5));
                viewHolder.AdMainImg.setLayoutParams(parms);

                try {
                    Picasso.with(getActivity())
                            .load(parkingList.get(position).getImageTitle())
                            // optional
                            .into(viewHolder.AdMainImg, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });
                } catch (Exception e) {

                }

               try
               {
                   if (parkingList.get(position).getImageLogo3().equalsIgnoreCase("") && parkingList.get(position).getImageLogo2().equalsIgnoreCase("") && !(parkingList.get(position).getImageLogo1().equalsIgnoreCase("")))
                   {
                       viewHolder.AdImgLogo1.setVisibility(View.VISIBLE);
                           viewHolder.AdImgLogo3.setVisibility(View.INVISIBLE);
                           viewHolder.AdImgLogo2.setVisibility(View.INVISIBLE);
                          viewHolder.AdImgLogo4.setVisibility(View.INVISIBLE);
                         viewHolder.AdImgLogo5.setVisibility(View.INVISIBLE);

                                                   Picasso.with(getActivity())
                                    .load(parkingList.get(position).getImageLogo1())// optional
                                                           .fit()
                                    .into(viewHolder.AdImgLogo1, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            // pb.setVisibility(View.INVISIBLE);

                                        }

                                        @Override
                                        public void onError() {
                                            // pb.setVisibility(View.INVISIBLE);
                                        }
                                    });
                   }
                   else if (parkingList.get(position).getImageLogo3().equalsIgnoreCase("") && !(parkingList.get(position).getImageLogo2().equalsIgnoreCase("")) && !(parkingList.get(position).getImageLogo1().equalsIgnoreCase("")))
                   {
                       viewHolder.AdImgLogo1.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo2.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo3.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo4.setVisibility(View.VISIBLE);
                       viewHolder.AdImgLogo5.setVisibility(View.VISIBLE);


                       Picasso.with(getActivity())
                               .load(parkingList.get(position).getImageLogo1())// optional
                               .fit()
                               .into(viewHolder.AdImgLogo4, new Callback() {
                                   @Override
                                   public void onSuccess() {
                                       // pb.setVisibility(View.INVISIBLE);

                                   }

                                   @Override
                                   public void onError() {
                                       // pb.setVisibility(View.INVISIBLE);
                                   }
                               });


                       Picasso.with(getActivity())
                               .load(parkingList.get(position).getImageLogo2())// optional
                               .fit()
                               .into(viewHolder.AdImgLogo5, new Callback() {
                                   @Override
                                   public void onSuccess() {
                                       // pb.setVisibility(View.INVISIBLE);

                                   }

                                   @Override
                                   public void onError() {
                                       // pb.setVisibility(View.INVISIBLE);
                                   }
                               });
                   }
                   else if (!(parkingList.get(position).getImageLogo3().equalsIgnoreCase("")) && !(parkingList.get(position).getImageLogo2().equalsIgnoreCase("")) && !(parkingList.get(position).getImageLogo1().equalsIgnoreCase("")))
                   {
                       viewHolder.AdImgLogo1.setVisibility(View.VISIBLE);
                       viewHolder.AdImgLogo2.setVisibility(View.VISIBLE);
                       viewHolder.AdImgLogo3.setVisibility(View.VISIBLE);
                       viewHolder.AdImgLogo4.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo5.setVisibility(View.INVISIBLE);

                       Picasso.with(getActivity())
                               .load(parkingList.get(position).getImageLogo1())// optional
                               .fit()
                               .into(viewHolder.AdImgLogo1, new Callback() {
                                   @Override
                                   public void onSuccess() {
                                       // pb.setVisibility(View.INVISIBLE);

                                   }

                                   @Override
                                   public void onError() {
                                       // pb.setVisibility(View.INVISIBLE);
                                   }
                               });


                       Picasso.with(getActivity())
                               .load(parkingList.get(position).getImageLogo2())// optional
                               .fit()
                               .into(viewHolder.AdImgLogo2, new Callback() {
                                   @Override
                                   public void onSuccess() {
                                       // pb.setVisibility(View.INVISIBLE);

                                   }

                                   @Override
                                   public void onError() {
                                       // pb.setVisibility(View.INVISIBLE);
                                   }
                               });

                       Picasso.with(getActivity())
                               .load(parkingList.get(position).getImageLogo3())// optional
                               .fit()
                               .into(viewHolder.AdImgLogo3, new Callback() {
                                   @Override
                                   public void onSuccess() {
                                       // pb.setVisibility(View.INVISIBLE);

                                   }

                                   @Override
                                   public void onError() {
                                       // pb.setVisibility(View.INVISIBLE);
                                   }
                               });
                   }
                   else
                   {
                       viewHolder.AdImgLogo1.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo2.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo3.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo4.setVisibility(View.INVISIBLE);
                       viewHolder.AdImgLogo5.setVisibility(View.INVISIBLE);
                   }

               } catch (Exception e) {

                }


                viewHolder.AdTitleTxt.setText(parkingList.get(position).getTitle());

                viewHolder.AdsubTitleTXt.setText(parkingList.get(position).getSubTitle());
                viewHolder.AdDescTxt.setText(parkingList.get(position).getAdDescription());


            }

            return rowView;
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

    public class Communication_flag extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.flagprofile);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2", SwipeFbid);
                postDataParams.put("flagreason", str_flag_desc);

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


            if (result != null) {


            }


        }
    }
///////..Looking Profiles......


    public class Communication_LookingProfile extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {


                URL url = new URL(UrlClass.getprofiles);
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("distance", pref.getString("distance", ""));
                postDataParams.put("agegroup", pref.getString("agegroup", ""));
                postDataParams.put("makefriendswith", pref.getString("makefriendswith", ""));
                postDataParams.put("city", pref.getString("city", ""));
                postDataParams.put("country", pref.getString("country", ""));
                postDataParams.put("birthdate", pref.getString("birthdate", ""));


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


            if (result != null) {

                if (result.equalsIgnoreCase("Exception: timeout")) {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Server has been time out. please try again");
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

                } else if (result.equalsIgnoreCase("Exception:timeout")) {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Server has been time out. please try again");
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

                } else if (result.equalsIgnoreCase("nullerror")) {

                    pb.clearAnimation();
                    pb.setVisibility(View.INVISIBLE);
                    LodingTxt.setVisibility(View.INVISIBLE);
                    NomoreProfileTxt.setVisibility(View.VISIBLE);
                    txt_taplod.setVisibility(View.VISIBLE);
                    LodingTxt.setEnabled(true);

                } else if (result.equalsIgnoreCase("nofbid")) {

                    pb.clearAnimation();
                    pb.setVisibility(View.INVISIBLE);
                    LodingTxt.setVisibility(View.INVISIBLE);

                    NomoreProfileTxt.setVisibility(View.VISIBLE);
                    txt_taplod.setVisibility(View.VISIBLE);
                    LodingTxt.setEnabled(true);

                } else if (result.equalsIgnoreCase("noprofiles")) {


                    pb.clearAnimation();

                    LodingTxt.setVisibility(View.INVISIBLE);

                    if (pref.getString("distance", "").equalsIgnoreCase("CITY")) {
                        pb.setVisibility(View.INVISIBLE);
                        TipRow.setVisibility(View.VISIBLE);
                        ChangeDiscoverBtn.setText("Search my country");
                        DiscoverPlaceTXt.setText("Discover people from your country");
                    } else if (pref.getString("distance", "").equalsIgnoreCase("COUNTRY")) {
                        pb.setVisibility(View.INVISIBLE);
                        TipRow.setVisibility(View.VISIBLE);
                        ChangeDiscoverBtn.setText("Search the world");
                        DiscoverPlaceTXt.setText("Discover people from around the world");
                    } else {
                        TipRow.setVisibility(View.GONE);
                        pb.setVisibility(View.INVISIBLE);
                    }
                    NomoreProfileTxt.setVisibility(View.VISIBLE);
                    txt_taplod.setVisibility(View.VISIBLE);
                    LodingTxt.setVisibility(View.INVISIBLE);
                    LodingTxt.setEnabled(true);
                    pb.setVisibility(View.INVISIBLE);


                } else {


                    pb.setVisibility(View.INVISIBLE);
                    LodingTxt.setVisibility(View.INVISIBLE);
                    NomoreProfileTxt.setVisibility(View.VISIBLE);
                    txt_taplod.setVisibility(View.VISIBLE);
                    LodingTxt.setVisibility(View.INVISIBLE);
                    LodingTxt.setEnabled(true);
                    pb.clearAnimation();
                    if (result != null || result.length() >= 77) {

                        try {


                            JSONArray jsonarray = new JSONArray(result);

                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject obj = new JSONObject(jsonarray.getString(i));

                                Type = obj.getString("profiletype");
                                Log.d("hhhhjj77", Type);

                                if (Type.equalsIgnoreCase("AD")) {

                                    FbId = obj.getString("fbid");
                                    Title = obj.getString("title");
                                    AdDescription = obj.getString("addescription");
                                    SubTitle = obj.getString("subtitle");
                                    ColorRed = obj.getString("colourred");
                                    ColorGreen = obj.getString("colourgreen");
                                    ColorBlue = obj.getString("colourblue");
                                    ImageTitle = obj.getString("imagetitle");
                                    ImageLogo1 = obj.getString("imagelogo1");
                                    ImageLogo2 = obj.getString("imagelogo2");
                                    ImageLogo3 = obj.getString("imagelogo3");
                                    UserId = "";
                                    EmailId = "";
                                    Fname = "";
                                    Gender = "";
                                    BirthDate = "";
                                    EngLang = "";
                                    ArbicLang = "";
                                    FrenchLang = "";
                                    City = "";
                                    Country = "";
                                    Description = "";
                                    LikeToPlay = "";
                                    ICanMeet = "";
                                    Activity1 = "";
                                    Activity2 = "";
                                    Activity3 = "";
                                    Emoji1 = "";
                                    Emoji2 = "";
                                    Emoji3 = "";
                                    SuperHero = "";
                                    ProfilePic = "";
                                    Age = "";


                                } else {

                                    FbId = obj.getString("fbid");
                                    Title = "";
                                    AdDescription = "";
                                    SubTitle = "";
                                    ColorRed = "";
                                    ColorGreen = "";
                                    ColorBlue = "";
                                    ImageTitle = "";
                                    ImageLogo1 = "";
                                    ImageLogo2 = "";
                                    ImageLogo3 = "";
                                    UserId = obj.getString("userid");
                                    //EmailId = obj.getString("emailid");
                                    EmailId = "";
                                    Fname = obj.getString("fname");
                                    Gender = obj.getString("gender");
                                    BirthDate = obj.getString("birthdate");
                                    EngLang = obj.getString("englang");
                                    ArbicLang = obj.getString("arabiclang");
                                    FrenchLang = obj.getString("frenchlang");
                                    City = obj.getString("city");
                                    Country = obj.getString("country");
                                    Description = obj.getString("description");
                                    LikeToPlay = obj.getString("liketoplay");
                                    ICanMeet = obj.getString("icanmeet");
                                    Activity1 = obj.getString("activity1");
                                    Activity2 = obj.getString("activity2");
                                    Activity3 = obj.getString("activity3");
                                    Emoji1 = obj.getString("emoji1");
                                    Emoji2 = obj.getString("emoji2");
                                    Emoji3 = obj.getString("emoji3");
                                    SuperHero = obj.getString("superhero");
                                    ProfilePic = obj.getString("profilepic");
                                    Age = obj.getString("age");


                                    //intent =new Intent(WelcomeActivity.this,WelcomeActivity.class);
                                    //startActivity(intent);
                                }

                                array.add(new Data(Type, FbId, Title, AdDescription, SubTitle, ColorRed, ColorGreen, ColorBlue, ImageTitle, ImageLogo1, ImageLogo2, ImageLogo3, UserId, EmailId, Fname, Gender, BirthDate, EngLang, ArbicLang, FrenchLang, City, Country, Description, LikeToPlay, ICanMeet, Activity1, Activity2, Activity3, Emoji1, Emoji2, Emoji3, SuperHero, ProfilePic, Age));

                            }


                            totalrow = array.size();
                            parkingList = array;

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    flingContainer.invalidate();
                                    ((BaseAdapter) flingContainer.getAdapter()).notifyDataSetChanged();

                                }
                            });


                        } catch (Exception e) {
                            Log.e("Json Exception ", e.toString());
                        }
                    } else {


                        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                        builder1.setTitle("Oops");
                        builder1.setMessage("Server has been given error");
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

                        pb.clearAnimation();

                        LodingTxt.setVisibility(View.INVISIBLE);

                        if (pref.getString("distance", "").equalsIgnoreCase("CITY")) {
                            pb.setVisibility(View.INVISIBLE);
                            TipRow.setVisibility(View.VISIBLE);
                            ChangeDiscoverBtn.setText("Search my country");
                            DiscoverPlaceTXt.setText("Discover people from your country");
                        } else if (pref.getString("distance", "").equalsIgnoreCase("COUNTRY")) {
                            pb.setVisibility(View.INVISIBLE);
                            TipRow.setVisibility(View.VISIBLE);
                            ChangeDiscoverBtn.setText("Search the world");
                            DiscoverPlaceTXt.setText("Discover people from around the world");
                        } else {
                            TipRow.setVisibility(View.GONE);
                            pb.setVisibility(View.INVISIBLE);
                        }
                        NomoreProfileTxt.setVisibility(View.VISIBLE);
                        txt_taplod.setVisibility(View.VISIBLE);
                        LodingTxt.setVisibility(View.INVISIBLE);
                        LodingTxt.setEnabled(true);
                        pb.setVisibility(View.INVISIBLE);


                    }
                }
            }
            else
            {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder1.setTitle("Oops");
                builder1.setMessage("Server has been given error");
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
}






