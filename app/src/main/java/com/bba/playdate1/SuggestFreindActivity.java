package com.bba.playdate1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SuggestFreindActivity extends AppCompatActivity {

    public static String str_flag_event;
    TextView send, nofrndtxt;
    ImageView backbtn, backbtn1;
    MultiAutoCompleteTextView multxt;
    TextView send1, sugg;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog_Box;
    ProgressDialog progressDialog;
    ProgressBar progressbar_suggested;
    JSONArray Array_suggestedFriends = null;
    JSONArray Array_suggestedFriends1 = null;
    ListView listview_sugestedFriend;
    int total_Row = 0;
    String str_search_txt = "";
    TextView txt_sujetion_result;

    SearchView searchbar;

    ArrayList<String> Array_Friendlistid = new ArrayList<>();

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
        setContentView(R.layout.activity_suggest_freind);

        Array_suggestedFriends1 = new JSONArray();
        searchbar = (SearchView) findViewById(R.id.search_suggested);
        backbtn = (ImageView) findViewById(R.id.backimg);
        backbtn1 = (ImageView) findViewById(R.id.backimg1);
        send = (TextView) findViewById(R.id.sendfrndbtn);
        listview_sugestedFriend = (ListView) findViewById(R.id.listview_sugested);

        progressbar_suggested = (ProgressBar) findViewById(R.id.progress_suggested);
        progressbar_suggested.setVisibility(View.VISIBLE);

        send1 = (TextView) findViewById(R.id.textView7);
        sugg = (TextView) findViewById(R.id.textView8);
        txt_sujetion_result = (TextView) findViewById(R.id.txt_sujetion_result);

        txt_sujetion_result.setVisibility(View.INVISIBLE);


        if (str_flag_event.equalsIgnoreCase("createevent")) {
            send.setEnabled(true);
            send.setTextColor(getResources().getColor(R.color.black));

        } else {
            send.setEnabled(false);
            send.setTextColor(getResources().getColor(R.color.lightgray));

        }

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(SuggestFreindActivity.this);
                finish();
            }
        });

        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(SuggestFreindActivity.this);
                finish();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Communication_createeventFriends myTask = new Communication_createeventFriends();

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


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                searchbar.clearFocus();
                InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(searchbar.getWindowToken(), 0);

                progressDialog = new ProgressDialog(SuggestFreindActivity.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);


                if (str_flag_event.equalsIgnoreCase("createevent")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Communication_createEvent myTask = new Communication_createEvent();

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
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Communication_EditInviteEventFriends myTask = new Communication_EditInviteEventFriends();

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

            }

        });


        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.d("seach_query", query);
                // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                Array_suggestedFriends = new JSONArray();
                if (Array_suggestedFriends1 != null)

                {
                    if (TextUtils.isEmpty(newText.toString())) {

                        str_search_txt = "";
                        Array_suggestedFriends = Array_suggestedFriends1;

                    } else {

                        for (int i = 0; i < Array_suggestedFriends1.length(); i++) {

                            try {
                                String string = Array_suggestedFriends1.getJSONObject(i).getString("fname");
                                str_search_txt = newText;
                                if ((string.toLowerCase()).contains(newText.toLowerCase())) {

                                    Log.e("constraint_str11", string);
                                    Array_suggestedFriends.put(Array_suggestedFriends1.getJSONObject(i));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                    }
                    if (Array_suggestedFriends != null) {
                        total_Row = Array_suggestedFriends.length();
                        listview_sugestedFriend.invalidate();
                        ((BaseAdapter) listview_sugestedFriend.getAdapter()).notifyDataSetChanged();

                    }


                } else {
                    total_Row = 0;
                }
                return false;
            }
        });


        CustomAdapter2 Customadpter_chat = new CustomAdapter2();
        listview_sugestedFriend.setAdapter(Customadpter_chat);

        listview_sugestedFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                // String s = listView1.getItemAtPosition(i).toString();


                ImageView imageViews = (ImageView) view.findViewById(R.id.image_select);
                //imageViews.setBackgroundColor(Color.RED);
                // imageViews.setImageResource(android.R.color.transparent);
                try {
                    JSONObject obj_vallues = new JSONObject(String.valueOf(Array_suggestedFriends.getString((Integer) view.getTag())));
                    String Str_fbid = obj_vallues.getString("fbid");
                    if (Array_Friendlistid.size() != 0) {
                        if (Array_Friendlistid.contains(Str_fbid)) {
                            imageViews.setImageResource(0);
                            int indexid = Array_Friendlistid.indexOf(Str_fbid);
                            Array_Friendlistid.remove(indexid);

                        } else {

                            imageViews.setImageResource(R.drawable.checkmark);
                            Array_Friendlistid.add(Str_fbid);
                        }
                    } else {
                        imageViews.setImageResource(R.drawable.checkmark);
                        Array_Friendlistid.add(Str_fbid);

                    }

                    if (str_flag_event.equalsIgnoreCase("createevent")) {

                    } else {
                        if (Array_Friendlistid.size() == 0) {
                            send.setEnabled(false);
                            send.setTextColor(getResources().getColor(R.color.lightgray));
                        } else {
                            send.setEnabled(true);
                            send.setTextColor(getResources().getColor(R.color.black));

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    private class CustomAdapter2 extends BaseAdapter {
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);


        @Override
        public int getCount() {
            return total_Row;
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

//[{"fbid":"10154143609282724",
//                    "fname":"aarav2",
//                    "profilepic":"http:\/\/play-date.ae\/app\/profileimages\/10154143609282724.jpg"}]


            try {

                JSONObject obj_vallues = new JSONObject(String.valueOf(Array_suggestedFriends.getString(i)));
                view = getLayoutInflater().inflate(R.layout.suggested_friends_listrow, null);


                ImageView image_profile = (ImageView) view.findViewById(R.id.profile_suugested);
                ImageView image_profile_select = (ImageView) view.findViewById(R.id.image_select);
                TextView txt_profilename = (TextView) view.findViewById(R.id.suggested_name);
                view.setTag(i);
                image_profile_select.setTag(i);

                Picasso.with(SuggestFreindActivity.this).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                        .placeholder(R.drawable.defaultboy)
                        .into(image_profile);


                String Str_name_txt = obj_vallues.getString("fname");


                if (str_search_txt.length() != 0) {
                    if ((Str_name_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                        int startPos = (Str_name_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                        int endPos = startPos + str_search_txt.length();

                        Spannable spanText = Spannable.Factory.getInstance().newSpannable(Str_name_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                        spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        txt_profilename.setText(spanText, TextView.BufferType.SPANNABLE);
                    } else {
                        txt_profilename.setText(Str_name_txt);
                        txt_profilename.setTextColor(getResources().getColor(R.color.black));
                    }


                } else {
                    txt_profilename.setText(Str_name_txt);
                    txt_profilename.setTextColor(getResources().getColor(R.color.black));

                }


                String str_friend_id = obj_vallues.getString("fbid");
                if (Array_Friendlistid.contains(str_friend_id)) {

                    image_profile_select.setImageResource(R.drawable.checkmark);

                } else {

                    image_profile_select.setImageResource(0);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
        }
    }

    public class Communication_createEvent extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.createevent);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("title", CreateNewActivity.titileString);
                postDataParams.put("location", CreateNewActivity.locString);
                postDataParams.put("description", CreateNewActivity.optionalString);

                SimpleDateFormat format_editdate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
                SimpleDateFormat format_editdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");


                Date editdate=null;
                String date_editdate="";

                try {
                    editdate = format_editdate.parse(CreateNewActivity.convetdate);
                    date_editdate =format_editdate1.format(editdate);

                } catch (ParseException e) {
                    e.printStackTrace();}


                postDataParams.put("eventdate",date_editdate);
                String string_friendid = "";
                if (Array_Friendlistid.size() == 0) {
                    string_friendid = "";
                } else {
                    string_friendid = Array_Friendlistid.toString();
                    string_friendid = string_friendid.replace("[", "");
                    string_friendid = string_friendid.replace("]", "");
                }


                postDataParams.put("friendlist", string_friendid);


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

                if (result.equalsIgnoreCase("nullerror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
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


                } else if (result.equalsIgnoreCase("inserterror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
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


                } else if (result.equalsIgnoreCase("expired")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Meetups date has been expired. please checkup your enter date and time.");
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


                } else

                {

                    if (str_flag_event.equalsIgnoreCase("createevent")) {

                        if (Array_Friendlistid.size() == 0) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder31 = new AlertDialog.Builder(SuggestFreindActivity.this);
                            builder31.setTitle("Share it!");
                            builder31.setMessage("You have not added any friends to your meet-up, you can also share it with your friends later in your meet-up screen.");

                            builder31.setCancelable(false);
                            builder31.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            CreateNewActivity.removeActivity_CreateNewActivity.finish();
                                            finish();
                                        }
                                    });
                            alertDialog_Box = builder31.create();
                            alertDialog_Box.show();
                        } else
                            {
                            CreateNewActivity.removeActivity_CreateNewActivity.finish();
                            finish();
                        }
                    }


                }


            }
        }
    }

    public class Communication_EditInviteEventFriends extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.eventdetails_invitefriends);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", setting_meetups_user1.str_meetupsetting);
                String string_friendid = "";
                if (Array_Friendlistid.size() == 0) {
                    string_friendid = "";
                } else {
                    string_friendid = Array_Friendlistid.toString();
                    string_friendid = string_friendid.replace("[", "");
                    string_friendid = string_friendid.replace("]", "");
                }


                postDataParams.put("friendlist", string_friendid);


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

                if (result.equalsIgnoreCase("nullerror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
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


                } else if (result.equalsIgnoreCase("inserterror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
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


                } else if (result.equalsIgnoreCase("expired")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Meetups date has been expired. please checkup your enter date and time.");
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


                } else if (result.equalsIgnoreCase("done")) {
                    finish();
                    Intent intent1 = new Intent("update_editmeetup");
                    LocalBroadcastManager.getInstance(SuggestFreindActivity.this).sendBroadcast(intent1);
                } else

                {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("Server time out.");
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

    public class Communication_createeventFriends extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            try {

                URL url = null;
                JSONObject postDataParams = new JSONObject();

                if (str_flag_event.equalsIgnoreCase("createevent")) {
                    url = new URL(UrlClass.createevent_addfriends);
                    postDataParams.put("fbid", pref.getString("userid", ""));
                } else {
                    url = new URL(UrlClass.eventdetails_addfriends);
                    postDataParams.put("fbid", pref.getString("userid", ""));
                    postDataParams.put("eventid", setting_meetups_user1.str_meetupsetting);
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

                Log.d("Exception_php", e.getMessage() + "");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("suggested server", result);
            progressbar_suggested.setVisibility(View.INVISIBLE);

            if (result != null) {

                if (result.equalsIgnoreCase("nullerror")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestFreindActivity.this);
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
                    txt_sujetion_result.setVisibility(View.VISIBLE);

                } else {

                    if (result.length() >= 50) {
                        try {
                            Array_suggestedFriends = new JSONArray(result);
                            Array_suggestedFriends1 = new JSONArray(result);
                            if (Array_suggestedFriends != null) {
                                total_Row = Array_suggestedFriends.length();

                                CustomAdapter2 Customadpter_chat = new CustomAdapter2();
                                listview_sugestedFriend.setAdapter(Customadpter_chat);
                                txt_sujetion_result.setVisibility(View.INVISIBLE);

                            } else {
                                txt_sujetion_result.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        txt_sujetion_result.setVisibility(View.VISIBLE);
                    }


                }


            }
        }
    }
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}

