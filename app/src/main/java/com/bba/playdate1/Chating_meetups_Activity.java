package com.bba.playdate1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albinmathew.photocrop.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Chating_meetups_Activity extends AppCompatActivity {

    public static JSONObject Msg_Record_obj_meetupchat;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    ImageView camera;
    String Str_user1_url, Str_User2_url, Str_matchedfbid, Str_userfbid, Str_message, Str_Chattype, Str_ChateEncodedImg, Str_Img_Width, Str_Img_Height;

    Context context;
    JSONArray jsonarray = null;
    Bitmap bitmap;
    private Uri file;
    int listSize = 0;
    public  static Activity Activity_chatting_meetup_remove;
    private ChatArrayAdaptertext chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private TextView BackButton,text_createDate,txt_share;
    private ImageView setting_meetup;
    ProgressDialog progressDialog;
    String str_statusval,str_statusval_cantgo,str_statusval_im;
    private boolean side = false;
    private BroadcastReceiver UpdateInformation_list = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Communication_chat_meetup myTask = new Communication_chat_meetup();

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
        setContentView(R.layout.activity_chating_meetups_);


        Activity_chatting_meetup_remove=this;

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        listSize = 0;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        LocalBroadcastManager.getInstance(Chating_meetups_Activity.this).registerReceiver(UpdateInformation_list,
                new IntentFilter("updateprofile_timer"));

        buttonSend = (Button) findViewById(R.id.meetupschat_send);
        camera = (ImageView) findViewById(R.id.camera_meetupchat);
        listView = (ListView) findViewById(R.id.meeups_listview);
        setting_meetup=(ImageView)findViewById(R.id.meetups_setting);

        chatText = (EditText) findViewById(R.id.textview_meetupchat_msg);
        BackButton = (TextView) findViewById(R.id.back_txt_meetupchat);
        text_createDate = (TextView) findViewById(R.id.textvie_meetup_creatdate);
        txt_share = (TextView) findViewById(R.id.txt_meetups_setting_share2);
        text_createDate.setText("");



        progressDialog = new ProgressDialog(Chating_meetups_Activity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        if (Msg_Record_obj_meetupchat != null) {

            try {



                String str_createDate="";
                Str_matchedfbid = Msg_Record_obj_meetupchat.getString("eventid");
                String Str_creatorfbid = Msg_Record_obj_meetupchat.getString("creatorfbid");
                Str_user1_url =pref.getString("profilepic","");
                Str_User2_url = Msg_Record_obj_meetupchat.getString("profilepic");
                Str_userfbid = pref.getString("userid", "");

                if (Str_userfbid.equalsIgnoreCase(Str_creatorfbid))
                {
                     str_createDate="You have created this meetup on "+ Msg_Record_obj_meetupchat.getString("createdate");
                }
                 else
                {
                    str_createDate="You have joined this meetup on "+ Msg_Record_obj_meetupchat.getString("createdate");
                }
                text_createDate.setText(str_createDate);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        chatText.addTextChangedListener(new TextWatcher() {
            @Override


            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)

            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (chatText.length() > 0) {


                    Str_Chattype = "TEXT";


                    buttonSend.setBackground(getResources().getDrawable(R.drawable.btn_gray));
                    buttonSend.setEnabled(true);


                } else

                {
                    buttonSend.setBackground(getResources().getDrawable(R.drawable.btn_shape));
                    buttonSend.setEnabled(false);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatText.clearFocus();
                InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(chatText.getWindowToken(), 0);
                selectImage();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatText.clearFocus();
                InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(chatText.getWindowToken(), 0);
                finish();
                ;

            }
        });





        txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {


                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Play:Date");
                    String sAux = "\n You have been invited to a Play:Date meet-up!\n\nEnter the event code:\n" + Str_matchedfbid +" to join the meet-up.\n\nDownload Play:Date on your iPhone from http://www.play-date.ae and find new friends for your children!";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }

            }
        });

        setting_meetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatText.clearFocus();
                InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(chatText.getWindowToken(), 0);
                Intent intens=new Intent(Chating_meetups_Activity.this,setting_meetups_user1.class);
                setting_meetups_user1.str_meetupsetting=Str_matchedfbid;
                startActivity(intens);

            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Str_Chattype.equalsIgnoreCase("TEXT")) {
                    Str_Img_Height = "";
                    Str_Img_Width = "";
                    Str_ChateEncodedImg = "";
                    Str_message = chatText.getText().toString();
                }
                else

                {

                    Str_message = "";
                }


                            Communication_addChat_meetup myTask = new Communication_addChat_meetup();

                            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                myTask.execute();



                chatText.setText("");

            }
        });

        listSize = 0;
        chatArrayAdapter = new ChatArrayAdaptertext();
        listView.setAdapter(chatArrayAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Communication_chat_meetup myTask = new Communication_chat_meetup();

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



    protected Rect getLocationOnScreen(View mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }
    //Friends add chat.......

    @Override
    public void onDestroy() {

        super.onDestroy();
        LocalBroadcastManager.getInstance(Chating_meetups_Activity.this).unregisterReceiver(UpdateInformation_list);


    }

    private class ChatArrayAdaptertext extends BaseAdapter {
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return listSize;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


            if (jsonarray != null) {
                try {







                    //    [{"message":"Don't miss out on our amazing offers for you and your little one. Be sure to have fun and enjoy the World of Playdate!","senderfbid":"play-date","receiverfbid":"777369135751064","msgdate":"2018-04-27 06:28:12","chattype":"TEXT","imageurl":"","imageheight":"","imagewidth":""}]
                    JSONObject Js_obj = new JSONObject(String.valueOf(jsonarray.get(i)));
                    JSONObject Js_obj1=null;
                    String Str_DateDisplay="";
                    String date_first,date_sec;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1=null;
                    Date date2=null;
                    date_first = Js_obj.getString("msgdate");

                    if (i==0)
                    {

                        try
                        {

                            date1 = format.parse(date_first);
                            Str_DateDisplay = outputformat.format(date1);


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        Js_obj1 = new JSONObject(String.valueOf(jsonarray.get(i-1)));

                        date_sec = Js_obj1.getString("msgdate");
                        try
                        {
                            date1 = format.parse(date_first);
                            date2 = format.parse(date_sec);
                            if (date1.equals(date2))
                            {
                                Str_DateDisplay="";
                            }
                            else
                            {
                                Str_DateDisplay = outputformat.format(date1);
                            }



                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


        Integer boy_girl_roundxml=0, boy_girl_defaultimg=0;
                    if (pref.getString("gender", "").equalsIgnoreCase("Boy")) {


                        boy_girl_roundxml=R.drawable.boy_roundxml;
                        boy_girl_defaultimg=R.drawable.defaultboy;

                    } else
                    {
                        boy_girl_roundxml=R.drawable.girl_roundxml;
                        boy_girl_defaultimg=R.drawable.defaultgirl;
                    }




                    if (Js_obj.getString("chattype").equalsIgnoreCase("TEXT")) {
                        if (Str_userfbid.equalsIgnoreCase(Js_obj.getString("senderfbid"))) {

                            view = inflater.inflate(R.layout.rigth_txt, null, false);
                            TextView textVie3w1 = (TextView) view.findViewById(R.id.textView_right);
                            ImageView imageView1 = (ImageView) view.findViewById(R.id.profileImgview1_rght);
                            TextView text_date1 = (TextView) view.findViewById(R.id.date_txt);


                            textVie3w1.setText(Js_obj.getString("message"));

                            if (Str_DateDisplay.equalsIgnoreCase(""))
                            {
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text_date1.getLayoutParams();
                                params.height = 0;
                                params.topMargin = 0;
                                text_date1.setLayoutParams(params);
                            }
                            else
                            {
                                text_date1.setText(Str_DateDisplay);
                            }



                            Picasso.with(getApplicationContext()).load(Str_user1_url).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageView1);

                                textVie3w1.setBackgroundResource(boy_girl_roundxml);


                        } else
                            {


                                view = inflater.inflate(R.layout.lefttxt, null, false);
                                TextView textView = (TextView) view.findViewById(R.id.textView);
                                TextView text_date = (TextView) view.findViewById(R.id.date_txt);


                                ImageView imageView = (ImageView) view.findViewById(R.id.profileImgview1);
                                textView.setText(Js_obj.getString("message"));


                                if (Str_DateDisplay.equalsIgnoreCase(""))
                                {
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text_date.getLayoutParams();
                                    params.height = 0;
                                    params.topMargin = 0;
                                    text_date.setLayoutParams(params);
                                }
                                else
                                {
                                    text_date.setText(Str_DateDisplay);
                                }




                                Picasso.with(getApplicationContext()).load(Str_User2_url).fit().centerCrop()
                                        .placeholder(boy_girl_defaultimg)
                                        .into(imageView);

                                textView.setBackgroundResource(R.drawable.yellowtextbox);






                        }
                    }
                   else if (Js_obj.getString("chattype").equalsIgnoreCase("EVENT"))
                     {
                         view = inflater.inflate(R.layout.meetups_calander_chat, null, false);

                         TextView textView_date = (TextView) view.findViewById(R.id.date_txt_meetupchat);
                         TextView textview_titles = (TextView) view.findViewById(R.id.textView_right_title);
                         TextView textview_location = (TextView) view.findViewById(R.id.textView_right_location);
                         TextView textView_subtitle = (TextView) view.findViewById(R.id.textView_right_subtitle);
                         TextView textvie_IM = (TextView) view.findViewById(R.id.textView_right_Im);
                         TextView textView_CantGo = (TextView) view.findViewById(R.id.textView_right_cantgo);
                         TextView textview_addCal = (TextView) view.findViewById(R.id.textView_right_AddCalander);


                          textView_date.setTypeface(MainActivity.font_helvitica_medium);
                          textview_titles.setTypeface(MainActivity.font_helvitica_bold);
                          textview_location.setTypeface(MainActivity.font_helvitica_light);
                          textView_subtitle.setTypeface(MainActivity.font_helvitica_light);
                          textvie_IM.setTypeface(MainActivity.font_helvitica_bold);
                          textView_CantGo.setTypeface(MainActivity.font_helvitica_bold);
                          textview_addCal.setTypeface(MainActivity.font_helvitica_bold);

                         ImageView imageView_left = (ImageView) view.findViewById(R.id.mettup_pro_Imgview_left);
                         ImageView imageView_right = (ImageView) view.findViewById(R.id.mettup_pro_Imgview_rght);
                         textvie_IM.setTag(i-1);
                         textView_CantGo.setTag(i-1);

                         if (Str_DateDisplay.equalsIgnoreCase(""))
                         {
                             RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView_date.getLayoutParams();
                             params.height = 0;
                             params.topMargin = 0;
                             textView_date.setLayoutParams(params);
                         }
                         else
                         {
                             textView_date.setText(Str_DateDisplay);
                         }


                     final String str_eventtitle,str_eventlocation,str_eventdate,str_eventdescription,str_eventdateformat;

                         str_eventtitle=Js_obj.getString("eventtitle");
                         str_eventlocation=Js_obj.getString("eventlocation");
                         str_eventdateformat=Js_obj.getString("eventdateformat");
                         str_eventdate=Js_obj.getString("eventdate");
                         str_eventdescription = Js_obj.getString("eventdescription");



                         textview_titles.setText(str_eventtitle);
                         textview_location.setText(str_eventlocation);
                         textView_subtitle.setText(str_eventdateformat);
                         if (Js_obj.getString("attendingstatus").equalsIgnoreCase("ATTENDING"))
                         {
                             textvie_IM.setBackgroundResource(R.drawable.btn_white_border);
                             textView_CantGo.setBackgroundResource(R.drawable.verifybtn_border);
                             str_statusval_im="ATTENDING";
                             str_statusval_cantgo="UNKNOWN";

                         }
                         else  if (Js_obj.getString("attendingstatus").equalsIgnoreCase("NOTATTENDING"))
                         {
                             textView_CantGo.setBackgroundResource(R.drawable.btn_white_border);
                             textvie_IM.setBackgroundResource(R.drawable.verifybtn_border);
                             str_statusval_cantgo="NOTATTENDING";
                             str_statusval_im="UNKNOWN";
                         }
                         else if(Js_obj.getString("attendingstatus").equalsIgnoreCase("UNKNOWN"))
                         {
                             textView_CantGo.setBackgroundResource(R.drawable.verifybtn_border);
                             textvie_IM.setBackgroundResource(R.drawable.verifybtn_border);
                             str_statusval_cantgo="UNKNOWN";
                             str_statusval_im="UNKNOWN";

                         }





                         textview_addCal.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view)
                             {

                               //str_eventdate
                                 Intent intent = new Intent(Intent.ACTION_INSERT);
                                 intent.setType("vnd.android.cursor.item/event");
                                 intent.putExtra(CalendarContract.Events.TITLE, str_eventtitle);
                                 intent.putExtra(CalendarContract.Events.EVENT_LOCATION, str_eventlocation);
                                 intent.putExtra(CalendarContract.Events.DESCRIPTION, str_eventdescription);

                              //   GregorianCalendar calDate = new GregorianCalendar();
                                // GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15,12,12);

                                 Calendar cal = Calendar.getInstance();
                                 // "eventdate":"2018-06-20 12:38:00","

                                 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                 Date datecal=null;

//
                                 try {
                                     datecal = format1.parse(str_eventdate);
                                     cal.setTime(datecal);

                                 } catch (ParseException e) {
                                     e.printStackTrace();
                                 }


                                 intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                         cal.getTimeInMillis());
                                 intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                         cal.getTimeInMillis());

                                 intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);


                                 intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                                 intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                                 startActivity(intent);

                             }
                         });


                         textvie_IM.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view)
                             {
                                 if(str_statusval_im.equalsIgnoreCase("ATTENDING"))
                                 {
                                     str_statusval="UNKNOWN";

                                 }
                                 else
                                 {
                                     str_statusval="ATTENDING";


                                 }

                                 new Thread(new Runnable() {
                                     @Override
                                     public void run() {
                                         try {

                                             Communication_chat_meetup_event myTask = new Communication_chat_meetup_event();

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

                         textView_CantGo.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view)
                             {

                                 if(str_statusval_cantgo.equalsIgnoreCase("NOTATTENDING"))
                                 {
                                     str_statusval="UNKNOWN";


                                 }
                                 else
                                 {
                                     str_statusval="NOTATTENDING";
                                 }

                                 new Thread(new Runnable() {
                                     @Override
                                     public void run() {
                                         try {

                                             Communication_chat_meetup_event myTask = new Communication_chat_meetup_event();

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




                        if (Str_userfbid.equalsIgnoreCase(Js_obj.getString("senderfbid"))) {

                            imageView_left.setVisibility(View.INVISIBLE);
                            imageView_right.setVisibility(View.VISIBLE);
                            Picasso.with(getApplicationContext()).load(Str_user1_url).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageView_right);


                        } else {

                            imageView_left.setVisibility(View.VISIBLE);
                            imageView_right.setVisibility(View.INVISIBLE);

                            Picasso.with(getApplicationContext()).load(Js_obj.getString("profilepic")).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageView_left);


                        }
                    }

                    else
                    {




                        if (Str_userfbid.equalsIgnoreCase(Js_obj.getString("senderfbid")))
                        {
                            view = inflater.inflate(R.layout.imageleft, null, false);
                            final ImageView imageViewleft = (ImageView) view.findViewById(R.id.imagelefts);
                            ImageView imageView2 = (ImageView) view.findViewById(R.id.profileImgview_image);

                            TextView text_date1 = (TextView) view.findViewById(R.id.date_txt_img1);


                            if (Str_DateDisplay.equalsIgnoreCase(""))
                            {
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text_date1.getLayoutParams();
                                params.height = 0;
                                params.topMargin = 0;
                                text_date1.setLayoutParams(params);
                            }
                            else
                            {
                                text_date1.setText(Str_DateDisplay);
                            }



                            Picasso.with(getApplicationContext()).load(Str_User2_url).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageView2);



                            Picasso.with(getApplicationContext()).load(Js_obj.getString("imageurl")).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageViewleft);


                            imageViewleft.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    Bitmap bitmap = ((BitmapDrawable)imageViewleft.getDrawable()).getBitmap();
                                    SimpleSampleActivity.drawableBitmap=bitmap;

                                    Intent intent=new Intent(Chating_meetups_Activity.this,SimpleSampleActivity.class);
                                    startActivity(intent);
                                }
                            });

                        } else {

                            view = inflater.inflate(R.layout.imgerigth, null, false);
                            final ImageView imageViewriht = (ImageView) view.findViewById(R.id.imgerigth_img);
                            ImageView imageView3 = (ImageView) view.findViewById(R.id.profileImgview1_imagel);

                            TextView text_date1 = (TextView) view.findViewById(R.id.date_txt_img2);


                            if (Str_DateDisplay.equalsIgnoreCase(""))
                            {
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text_date1.getLayoutParams();
                                params.height = 0;
                                params.topMargin = 0;
                                text_date1.setLayoutParams(params);
                            }
                            else
                            {
                                text_date1.setText(Str_DateDisplay);
                            }

                            Picasso.with(getApplicationContext()).load(Str_user1_url).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageView3);

                            Picasso.with(getApplicationContext()).load(Js_obj.getString("imageurl")).fit().centerCrop()
                                    .placeholder(boy_girl_defaultimg)
                                    .into(imageViewriht);


                            imageViewriht.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {


                                    Bitmap bitmap = ((BitmapDrawable)imageViewriht.getDrawable()).getBitmap();
                                    SimpleSampleActivity.drawableBitmap=bitmap;
                                    Intent intent=new Intent(Chating_meetups_Activity.this,SimpleSampleActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return view;
        }
    }

    public class Communication_chat_meetup extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.eventchat);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", Str_matchedfbid);


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


                if (result.length() >= 30) {

                    try {

                        jsonarray = new JSONArray(result);

                        JSONObject obj_resul = jsonarray.getJSONObject(0);
                        if (jsonarray != null) {
                            listSize = jsonarray.length();
//                            chatArrayAdapter = new ChatArrayAdaptertext();
//                            listView.setAdapter(chatArrayAdapter);





                            runOnUiThread (new Thread(new Runnable() {
                                public void run()
                                {
                                    listView.invalidate();

                                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
//                                    listView.setSelection(listView.getCount() - 1);
                                }
                            }));




                        }


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }


            }


        }
    }

    public class Communication_addChat_meetup extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.addeventchat);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", Str_matchedfbid);
                postDataParams.put("chatcount", "");
                postDataParams.put("message", Str_message);
                postDataParams.put("chattype", Str_Chattype);
                postDataParams.put("chatimage", Str_ChateEncodedImg);
                postDataParams.put("imagewidth", Str_Img_Width);
                postDataParams.put("imageheight", Str_Img_Height);


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


            if (result != null) {


                if (result.length() >= 30) {

                    try {

                        jsonarray = new JSONArray(result);
                        JSONObject obj_resul = jsonarray.getJSONObject(0);
                        if (jsonarray != null) {
                            listSize = jsonarray.length();
                            chatArrayAdapter = new ChatArrayAdaptertext();
                            listView.setAdapter(chatArrayAdapter);
                        }


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }


            }


        }
    }

    public class Communication_chat_meetup_event extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.eventattending);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid", pref.getString("userid", ""));
                postDataParams.put("eventid", Str_matchedfbid);
                postDataParams.put("status", str_statusval);


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



                    try {

                        jsonarray = new JSONArray(result);

                        JSONObject obj_resul = jsonarray.getJSONObject(0);
                        if (jsonarray != null) {
                            listSize = jsonarray.length();
//                            chatArrayAdapter = new ChatArrayAdaptertext();
//                            listView.setAdapter(chatArrayAdapter);



                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Communication_chat_meetup myTask = new Communication_chat_meetup();

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


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

            }


        }
    }
    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Chating_meetups_Activity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {


                    dialog.dismiss();





                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = Uri.fromFile(getOutputMediaFile());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

                    startActivityForResult(intent, 100);




                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 200);



                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds options to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //ProfileImg.setImageBitmap(loadBitmap(String.valueOf(file)));

                if (file != null) {

                    bitmap = loadBitmap(String.valueOf(file));

                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(new Runnable() {
                        public void run() {



                            if (bitmap.getHeight()>=500 && bitmap.getWidth()>=500)
                            {
                                bitmap=Bitmap.createScaledBitmap(bitmap, 500, 500, false);

                            }
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);


                            Str_Img_Width = String.valueOf(254);
                            Str_Img_Height = String.valueOf(300);

                            byte[] imageBytes = bytes.toByteArray();
                            Str_ChateEncodedImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                            Str_message = "";
                            Str_Chattype = "IMAGE";

                            Communication_addChat_meetup myTask = new Communication_addChat_meetup();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                myTask.execute();

                            chatText.setText("");

                        }
                    });


                }
            }
        }
        else if (requestCode == 200)
        {


            if (data !=null) {

                Uri selectedImage = data.getData();
                try {

                    editor.putString("select_photo", "no");
                    editor.commit();

                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);

                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(new Runnable() {
                        public void run() {

                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                            if (bitmap.getHeight()>=500 && bitmap.getWidth()>=500)
                            {
                                bitmap=Bitmap.createScaledBitmap(bitmap, 500, 500, false);

                            }
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
                            byte[] imageBytes = bytes.toByteArray();


                            Str_Img_Width = String.valueOf(254);
                            Str_Img_Height = String.valueOf(300);


                            Str_ChateEncodedImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            Str_message="";
                            Str_Chattype="IMAGE";


                            Communication_addChat_meetup myTask = new Communication_addChat_meetup();

                            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                myTask.execute();

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        }

    }
    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return bm;
    }
}
