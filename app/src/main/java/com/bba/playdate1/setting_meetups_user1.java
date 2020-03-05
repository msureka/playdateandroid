package com.bba.playdate1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.facebook.FacebookSdk.getApplicationContext;

public class setting_meetups_user1 extends AppCompatActivity {

    public static String str_meetupsetting;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    String str_meetupcreatorid = "";
    JSONArray jsonarray = null;
    JSONArray Array_Inviting = new JSONArray();
    JSONArray Array_Attending = new JSONArray();

    android.support.v7.app.AlertDialog alertDialog_Box;
    String str_emailAdd, str_phonenumber;

    TextView textView_back,imageView_share2;

    ListView listview_meetup_details;
    private BroadcastReceiver UpdateInformation = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Communication_eventDetails myTask = new Communication_eventDetails();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else
                            myTask.execute();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_meetups_user1);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        LocalBroadcastManager.getInstance(this).registerReceiver(UpdateInformation,
                new IntentFilter("update_editmeetup"));

        progressDialog = new ProgressDialog(setting_meetups_user1.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);


        textView_back = (TextView) findViewById(R.id.back_txt_meetupsettingback);
        imageView_share2 = (TextView) findViewById(R.id.text_meetups_setting_share);
        listview_meetup_details = (ListView) findViewById(R.id.listview_mettup_details);
        listview_meetup_details.setVisibility(View.INVISIBLE);


        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        imageView_share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {


                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Play:Date");
                    String sAux = "\nYou have been invited to a Play:Date meet-up!\n\nEnter the event code:\n" + str_meetupsetting +" to join the meet-up.\n\nDownload Play:Date on your iPhone from http://www.play-date.ae and find new friends for your children!";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

//
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Communication_eventDetails myTask = new Communication_eventDetails();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        myTask.execute();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }

    protected void onDestroy() {

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(UpdateInformation);


    }

    public class Communication_eventDetails extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.eventdetails);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", str_meetupsetting);


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
            Log.d("Result server==", result);
            if (result != null) {

                if (result.equalsIgnoreCase("noeventid")) {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(setting_meetups_user1.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("The event could not be found as it seems to have been expired or deleted by the creator.");
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

                    finish();
                    ;

                }
                if (result.length() >= 30) {


                    try {

                        jsonarray = new JSONArray(result);
                        Array_Inviting = new JSONArray();
                        Array_Attending = new JSONArray();

                        JSONObject Js_obj = new JSONObject(String.valueOf(jsonarray.get(0)));
                        CreateNewActivity.obj_editmeetups = Js_obj;
                        CreateNewActivity.str_editeventid = str_meetupsetting;

                        for (int i = 1; i < jsonarray.length(); i++) {
                            JSONObject obj_details = new JSONObject(String.valueOf(jsonarray.get(i)));

                            if (obj_details.getString("invitedstatus").equalsIgnoreCase("INVITED")) {

                                Array_Inviting.put(obj_details);

                            }
                            if (obj_details.getString("attendingstatus").equalsIgnoreCase("ATTENDING")) {
                                Array_Attending.put(obj_details);
                            }


                        }

                        listview_meetup_details.setVisibility(View.VISIBLE);
                        CustomAdapter2 Customadpter_meetup_details = new CustomAdapter2();
                        listview_meetup_details.setAdapter(Customadpter_meetup_details);


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }


            }


        }
    }

    public class Communication_removemeetup extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.removeevent);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", str_meetupsetting);


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

                if (result.equalsIgnoreCase("noeventid")) {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(setting_meetups_user1.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("The event could not be found as it seems to have been expired or deleted by the creator.");
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

                    finish();
                    ;

                }
                if (result.equalsIgnoreCase("done")) {

                    Chating_meetups_Activity.Activity_chatting_meetup_remove.finish();
                    finish();

                }


            }


        }
    }

    public class Communication_deletemeetup extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.deleteevent);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", str_meetupsetting);


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

                if (result.equalsIgnoreCase("noeventid")) {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(setting_meetups_user1.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("The event could not be found as it seems to have been expired or deleted by the creator.");
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

                    finish();
                    ;

                }
                if (result.equalsIgnoreCase("done")) {

                    Chating_meetups_Activity.Activity_chatting_meetup_remove.finish();
                    finish();

                }


            }


        }
    }

    private class CustomAdapter2 extends BaseAdapter {
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);


        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


            if (i == 0) {
                view = getLayoutInflater().inflate(R.layout.mettupsetting_rowfrist, null);
                if (jsonarray != null) {
                    JSONObject Js_obj = null;
                    try {
                        Js_obj = new JSONObject(String.valueOf(jsonarray.get(0)));

                        CreateNewActivity.obj_editmeetups = Js_obj;
                        CreateNewActivity.str_editeventid = str_meetupsetting;


                        TextView textview_titles = (TextView) view.findViewById(R.id.textview_setting_title);
                        TextView textView_eventid = (TextView) view.findViewById(R.id.textview_setting_eventcode);
                        TextView textview_location = (TextView) view.findViewById(R.id.textview_setting_location);
                        TextView textView_date = (TextView) view.findViewById(R.id.textview_setting_eventdate);
                        TextView textView_fname = (TextView) view.findViewById(R.id.textview_setting_name);
                        ImageView imageView_profile = (ImageView) view.findViewById(R.id.image_setting_profile);


                        textview_titles.setTypeface(MainActivity.font_helvitica_bold);
                        textView_eventid.setTypeface(MainActivity.font_helvitica_light_oblique);
                        textview_location.setTypeface(MainActivity.font_helvitica_light);
                        textView_date.setTypeface(MainActivity.font_helvitica_light);
                        textView_fname.setTypeface(MainActivity.font_helvitica_medium);


                        String str_nameprofile = "Created by: " + Js_obj.getString("fname");
                        String str_eventid = "(" + str_meetupsetting + ")";
                        textview_titles.setText(Js_obj.getString("eventtitle"));
                        textView_eventid.setText(str_eventid);
                        textview_location.setText(Js_obj.getString("eventlocation"));
                        textView_date.setText(Js_obj.getString("eventdateformat"));
                        textView_fname.setText(str_nameprofile);
                        Picasso.with(setting_meetups_user1.this).load(Js_obj.getString("profilepic")).fit().centerCrop()
                                .placeholder(R.drawable.defaultgirl)
                                .into(imageView_profile);
                        str_meetupcreatorid = Js_obj.getString("creatorfbid");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (i == 1) {
                view = getLayoutInflater().inflate(R.layout.section_one_newmatchs, null);


                LinearLayout llGallery = (LinearLayout) view.findViewById(R.id.ImagesHsw);

                TextView borderline = (TextView) view.findViewById(R.id.list_topline);


                TextView title = (TextView) view.findViewById(R.id.title_chating);
                HorizontalScrollView Scroll_view = (HorizontalScrollView) view.findViewById(R.id.horizontal_scr);


                title.setVisibility(View.VISIBLE);


                if (Array_Attending.length() != 0) {

                    borderline.setVisibility(View.VISIBLE);
                    String str_titlesattending = "Attending (" + Array_Attending.length() + ") ";
                    title.setText(str_titlesattending);
                    title.setTypeface(MainActivity.font_helvitica_bold);


                    for (int x = 0; x < Array_Attending.length(); x++) {
                        final View vi = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.images_list, null);
                        ImageView image_profile = (ImageView) vi.findViewById(R.id.imageView_img);
                        TextView name_txt = (TextView) vi.findViewById(R.id.editText2_txt);
                        name_txt.setTypeface(MainActivity.font_helvitica_light);

                        vi.setTag(x);


                        try {
                            JSONObject obj_vallues = new JSONObject(Array_Attending.getString(x));
                            name_txt.setText(obj_vallues.getString("fname"));

                            Picasso.with(setting_meetups_user1.this).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(image_profile);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        llGallery.addView(vi);
                    }
                } else {
                    borderline.setVisibility(View.INVISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
                    params.height = 0;
                    params.topMargin = 0;

                    RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) Scroll_view.getLayoutParams();
                    params1.height = 0;
                    params1.topMargin = 0;
                    Scroll_view.setLayoutParams(params1);
                    title.setLayoutParams(params);


                }


            }
            if (i == 2) {
                view = getLayoutInflater().inflate(R.layout.section_one_newmatchs, null);


                LinearLayout llGallery = (LinearLayout) view.findViewById(R.id.ImagesHsw);

                TextView borderline = (TextView) view.findViewById(R.id.list_topline);


                TextView title = (TextView) view.findViewById(R.id.title_chating);
                HorizontalScrollView Scroll_view = (HorizontalScrollView) view.findViewById(R.id.horizontal_scr);


                title.setVisibility(View.VISIBLE);

                if (Array_Inviting.length() != 0) {

                    borderline.setVisibility(View.VISIBLE);
                    String str_titlesattending = "Invited (" + Array_Inviting.length() + ") ";
                    title.setText(str_titlesattending);
                    title.setTypeface(MainActivity.font_helvitica_bold);


                    for (int x = 0; x < Array_Inviting.length(); x++) {
                        final View vi = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.images_list, null);
                        ImageView image_profile = (ImageView) vi.findViewById(R.id.imageView_img);
                        TextView name_txt = (TextView) vi.findViewById(R.id.editText2_txt);
                        name_txt.setTypeface(MainActivity.font_helvitica_light);
                        vi.setTag(x);


                        try {
                            JSONObject obj_vallues = new JSONObject(Array_Inviting.getString(x));
                            name_txt.setText(obj_vallues.getString("fname"));

                            Picasso.with(setting_meetups_user1.this).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(image_profile);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        llGallery.addView(vi);
                    }
                } else {
                    borderline.setVisibility(View.INVISIBLE);
                    title.setText("");
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
                    params.height = 0;
                    params.topMargin = 0;

                    RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) Scroll_view.getLayoutParams();
                    params1.height = 0;
                    params1.topMargin = 0;
                    Scroll_view.setLayoutParams(params1);
                    title.setLayoutParams(params);

                }
            }
            if (i == 3) {
                view = getLayoutInflater().inflate(R.layout.meetupsetting_rowthrid, null);

                TextView textview_first_invite_playdate = (TextView) view.findViewById(R.id.textview_setting_first);
                TextView textView_second_invite_other = (TextView) view.findViewById(R.id.textview_setting_seconds);
                TextView textview_edit_meetup = (TextView) view.findViewById(R.id.textview_setting_Thrid);
                TextView textView_delete_meetup = (TextView) view.findViewById(R.id.textview_setting_Four);

                textview_first_invite_playdate.setTypeface(MainActivity.font_helvitica_bold);
                textView_second_invite_other.setTypeface(MainActivity.font_helvitica_bold);
                textview_edit_meetup.setTypeface(MainActivity.font_helvitica_bold);
                textView_delete_meetup.setTypeface(MainActivity.font_helvitica_bold);

                if (pref.getString("userid", "").equalsIgnoreCase(str_meetupcreatorid)) {
                    textview_first_invite_playdate.setText("Invite Play:Date friends");
                    textview_first_invite_playdate.setVisibility(View.VISIBLE);
                    textView_second_invite_other.setVisibility(View.VISIBLE);
                    textview_edit_meetup.setVisibility(View.VISIBLE);
                    textView_delete_meetup.setVisibility(View.VISIBLE);
                } else {
                    textview_first_invite_playdate.setVisibility(View.VISIBLE);
                    textview_first_invite_playdate.setText("Remove this meetup");
                    textView_second_invite_other.setVisibility(View.INVISIBLE);
                    textview_edit_meetup.setVisibility(View.INVISIBLE);
                    textView_delete_meetup.setVisibility(View.INVISIBLE);
                }


                textview_first_invite_playdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (pref.getString("userid", "").equalsIgnoreCase(str_meetupcreatorid)) {
                            Intent i = new Intent(setting_meetups_user1.this, SuggestFreindActivity.class);
                            SuggestFreindActivity.str_flag_event = "invite";
                            startActivity(i);

                        } else {
                            new AlertDialog.Builder(setting_meetups_user1.this)
                                    .setTitle("Remove Meetup")
                                    .setMessage("Are you sure you want to remove yourself from this meetup?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                            progressDialog = new ProgressDialog(setting_meetups_user1.this);
                                            progressDialog.setMessage("Removing..."); // Setting Message
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                            progressDialog.show(); // Display Progress Dialog
                                            progressDialog.setCancelable(false);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Communication_removemeetup myTask = new Communication_removemeetup();

                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                        else
                                                            myTask.execute();

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
                    }
                });

                textView_second_invite_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        LayoutInflater inflater = getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.custom_dilog_invites, null);
                        TextView textview_contactinvite = alertLayout.findViewById(R.id.textview_contactsinvite);
                        TextView textview_emailinvite = alertLayout.findViewById(R.id.textview_emailinvite);
                        TextView textview_mobileinvite = alertLayout.findViewById(R.id.textview_mobileinvite);
                        TextView textview_canceinvite = alertLayout.findViewById(R.id.textview_Cancelinvite);
                        RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_invited);


                        AlertDialog.Builder alert = new AlertDialog.Builder(setting_meetups_user1.this);
                        // this is set the view from XML inside AlertDialog
                        alert.setView(alertLayout);
                        // disallow cancel of AlertDialog on click of back button and outside touch
                        alert.setCancelable(false);
                        final AlertDialog dialog = alert.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();


                        GradientDrawable bgShape = (GradientDrawable) backround_view.getBackground();
                        bgShape.mutate();
                        bgShape.setColor(Color.WHITE);


                        textview_contactinvite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(setting_meetups_user1.this, Contact_lists.class);
                                Contact_lists.str_invite_type = "meetup";
                                startActivity(intent);

                            }
                        });


                        textview_emailinvite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                LayoutInflater inflater = getLayoutInflater();
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
                                textview_email_No_enter.requestFocus();
                                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                                AlertDialog.Builder alert = new AlertDialog.Builder(setting_meetups_user1.this);
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

                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                        dialog1.dismiss();

                                    }
                                });

                                textview_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                        dialog1.dismiss();

                                        str_emailAdd = textview_email_No_enter.getText().toString();
                                        str_phonenumber = "";
                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        shareIntent.setType("text/plain");
                                        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{str_emailAdd});
                                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download Play:Date");
                                        shareIntent.putExtra(Intent.EXTRA_TEXT,  "\nYou have been invited to a Play:Date meet-up!\n\nEnter the event code:\n" + str_meetupsetting +" to join the meet-up.\n\nDownload Play:Date on your phone from http://www.play-date.ae and find new friends for your children!");
                                        shareIntent.setType("message/rfc822");
                                        startActivity(Intent.createChooser(shareIntent, "Pick an Email provider"));

                                    }
                                });


                            }
                        });

                        textview_mobileinvite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                dialog.dismiss();


//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                    if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
//                                            == PackageManager.PERMISSION_GRANTED) {
//                                        Log.d("perrr","Permission is granted");
//
//                                    } else {
//
//                                        Log.d("perrr","Permission is revoked");
//                                        ActivityCompat.requestPermissions(setting_meetups_user1.this, new String[]{android.Manifest.permission.SEND_SMS}, 0);
//
//                                    }
//                                }

                                LayoutInflater inflater = getLayoutInflater();
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

                                AlertDialog.Builder alert = new AlertDialog.Builder(setting_meetups_user1.this);
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

                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);
                                        dialog1.dismiss();

                                    }
                                });

                                textview_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        str_phonenumber = textview_email_No_enter.getText().toString();
                                        str_emailAdd = "";

                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(textview_email_No_enter.getWindowToken(), 0);

                                        dialog1.dismiss();



                                String msg= "\nYou have been invited to a Play:Date meet-up!\n\nEnter the event code:\n" + str_meetupsetting +" to join the meet-up.\n\nDownload Play:Date on your phone from http://www.play-date.ae and find new friends for your children!";;
                                Intent sendIntent = new Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sendIntent.setData(Uri.parse("sms:" + str_phonenumber));
                                sendIntent.putExtra("sms_body", msg);
                                startActivity(sendIntent);


//
//                                try {
//                                    SmsManager smsManager = SmsManager.getDefault();
//                                    smsManager.sendTextMessage(str_phonenumber, null, "Play:Date is a great app to find friend for your children.", null, null);
//                                    Toast.makeText(getApplicationContext(), "Message Sent",
//                                            Toast.LENGTH_LONG).show();
//                                } catch (Exception ex) {
//                                    Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
//                                            Toast.LENGTH_LONG).show();
//                                    ex.printStackTrace();
//                                }

                                    }
                                });


                            }
                        });
                        textview_canceinvite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                ;


                            }
                        });

                    }


                });

                textview_edit_meetup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(setting_meetups_user1.this, CreateNewActivity.class);
                        startActivity(intent);
                    }
                });

                textView_delete_meetup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        new AlertDialog.Builder(setting_meetups_user1.this)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to delete this meetup?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        progressDialog = new ProgressDialog(setting_meetups_user1.this);
                                        progressDialog.setMessage("Deleting..."); // Setting Message
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                        progressDialog.show(); // Display Progress Dialog
                                        progressDialog.setCancelable(false);

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Communication_deletemeetup myTask = new Communication_deletemeetup();

                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                                        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                    else
                                                        myTask.execute();

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


            }


            return view;
        }
    }

}
