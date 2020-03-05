package com.bba.playdate1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bba.fragment.MeetsupFragment;
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
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Facebook_add_friends extends AppCompatActivity {

    ProgressDialog progressDialog;
    ListView list_facebook;
    TextView txt_back,txt_result;
    ProgressBar progress_bar;
    int total_Row=0;
    JSONArray Array_facebookdata=null;
    JSONArray Array_facebookdata1=null;
String Str_fbid2="";
     SharedPreferences pref;
     SharedPreferences.Editor editor;

    String str_search_txt="";
    SearchView searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_add_friends);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        Array_facebookdata1=new JSONArray();
        searchbar=(SearchView)findViewById(R.id.search_fb);

        list_facebook=(ListView)findViewById(R.id.listview_facebook);
        txt_back=(TextView)findViewById(R.id.back_fb);
        txt_result=(TextView)findViewById(R.id.text_resul_fb);
        progress_bar=(ProgressBar)findViewById(R.id.progress_bar_fb);
        txt_result.setVisibility(View.INVISIBLE);




        txt_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });



        CustomAdapter2 Customadpter_chat = new CustomAdapter2();
        list_facebook.setAdapter(Customadpter_chat);
        progress_bar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Communication_fb_Friends myTask = new Communication_fb_Friends();

                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        myTask.execute();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.d("seach_query",query);
                // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                Array_facebookdata = new JSONArray();
                if (Array_facebookdata1 != null)

                {
                    if (TextUtils.isEmpty(newText.toString())) {

                        str_search_txt = "";
                        Array_facebookdata = Array_facebookdata1;

                    } else
                    {

                        for (int i = 0; i < Array_facebookdata1.length(); i++) {

                            try {
                                String string = Array_facebookdata1.getJSONObject(i).getString("name");
                                str_search_txt = newText;
                                if ((string.toLowerCase()).contains(newText.toLowerCase())) {

                                    Log.e("constraint_str11", string);
                                    Array_facebookdata.put(Array_facebookdata1.getJSONObject(i));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }


                    }
                    if (Array_facebookdata !=null)
                    {
                        total_Row=Array_facebookdata.length();

                        list_facebook.invalidate();
                        ((BaseAdapter) list_facebook.getAdapter()).notifyDataSetChanged();
                    }




                }
                else
                {
                    total_Row=0;
                }
                return false;
            }
        });







    }

    private class CustomAdapter2 extends BaseAdapter {
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);


        @Override
        public int getCount()
        {
            return total_Row;
        }

        @Override
        public Object getItem(int i)
        {
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {



                try {
                    JSONObject obj_vallues = new JSONObject(String.valueOf(Array_facebookdata.getString(i)));

                    int drawable = 0;


                    view = inflater.inflate(R.layout.facebook_add_friends, null, false);


                    ImageView image_profile = (ImageView) view.findViewById(R.id.fb_profileimage);
                    TextView text_name = (TextView) view.findViewById(R.id.fb_name);
                    TextView textView_add = (TextView) view.findViewById(R.id.fb_add);

                    textView_add.setTag(i);
                    view.setTag(i);



                    if (obj_vallues.getString("gender").equalsIgnoreCase("Boy")) {
                        drawable = R.drawable.defaultboy;
                    } else {
                        drawable = R.drawable.defaultgirl;
                    }

                    Picasso.with(Facebook_add_friends.this).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                            .placeholder(drawable)
                            .into(image_profile);

                    String Str_name_txt=obj_vallues.getString("name");

                    if (str_search_txt.length() !=0) {
                        if ((Str_name_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                            int startPos = (Str_name_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                            int endPos = startPos + str_search_txt.length();

                            Spannable spanText = Spannable.Factory.getInstance().newSpannable(Str_name_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                            spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            text_name.setText(spanText, TextView.BufferType.SPANNABLE);
                        }
                        else
                        {
                            text_name.setText(Str_name_txt);
                            text_name.setTextColor(getResources().getColor(R.color.black));
                        }



                    }
                    else
                    {
                        text_name.setText(Str_name_txt);
                        text_name.setTextColor(getResources().getColor(R.color.black));

                    }


                    textView_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            try {
                                JSONObject obj_selctVal = new JSONObject(String.valueOf(Array_facebookdata.getString((Integer) v.getTag())));
                                Str_fbid2=obj_selctVal.getString("friendfbid");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressDialog = new ProgressDialog(Facebook_add_friends.this);
                            progressDialog.setMessage("Sending..."); // Setting Message
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            progressDialog.show(); // Display Progress Dialog
                            progressDialog.setCancelable(false);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        Communication_fb_Friends_add myTask = new Communication_fb_Friends_add();

                                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                                            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        else
                                            myTask.execute();


                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }









            return view;
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


    public class Communication_fb_Friends extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.invite_fb);
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

            progress_bar.setVisibility(View.INVISIBLE);

            if (result != null) {

if (result.equalsIgnoreCase("nofriends"))
{
    txt_result.setVisibility(View.VISIBLE);
    total_Row=0;
    Array_facebookdata = new JSONArray();
    Array_facebookdata1 = new JSONArray();

    runOnUiThread (new Thread(new Runnable() {
        public void run()
        {
            list_facebook.invalidate();
            ((BaseAdapter) list_facebook.getAdapter()).notifyDataSetChanged();

        }
    }));
}
else if (result.length() >= 30) {

                    try {

                        Array_facebookdata = new JSONArray(result);
                        Array_facebookdata1 = new JSONArray(result);

                      if (Array_facebookdata !=null)
                      {
                          total_Row=Array_facebookdata.length();
                      }
                      else
                      {
                          total_Row=0;
                      }


                            runOnUiThread (new Thread(new Runnable() {
                                public void run()
                                {
                                    list_facebook.invalidate();
                                    ((BaseAdapter) list_facebook.getAdapter()).notifyDataSetChanged();

                                }
                            }));


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }
                else
                {

                        txt_result.setVisibility(View.VISIBLE);
                    total_Row=0;
                }


            }
            else
            {
                txt_result.setVisibility(View.VISIBLE);
                total_Row=0;
            }


        }
    }
    public class Communication_fb_Friends_add extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.invite_addfriend);
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2",Str_fbid2);

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

                if (result.equalsIgnoreCase("done"))
                {
                    progressDialog.dismiss();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Communication_fb_Friends myTask = new Communication_fb_Friends();

                                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                                    myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                else
                                    myTask.execute();


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }




            }



        }
    }
}
