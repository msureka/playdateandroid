package com.bba.playdate1;

import android.annotation.SuppressLint;
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
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
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

import com.bba.fragment.DiscoverFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Chating_Activity extends AppCompatActivity {

    public static Bitmap bm;
    public static JSONObject Msg_Record_obj;
    Bitmap bitmap;
    private Uri file;
    public Date comdare;
    ImageView camera;
    String SendMessage, Str_user1_url, Str_User2_url, Str_matchedfbid, Str_userfbid, Str_message, Str_Chattype, Str_ChateEncodedImg, Str_Img_Width, Str_Img_Height;
    String str_flag_desc;
    public  static String chatflag="";
    Context context;
    JSONArray jsonarray = null;
    String encodedImage,Str_profiletype="";
    android.support.v7.app.AlertDialog alertDialog_Box;
    String Messagedate;
    int listSize = 0;
    SharedPreferences.Editor editor;
    public SharedPreferences pref;
    private ChatArrayAdaptertext chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private TextView BackButton,prfile_name,text_createDate;
    private ImageView profileImgview,Image_threedots;
    ProgressDialog progressDialog;
    private boolean side = false;
    private BroadcastReceiver UpdateInformation_list = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Communication_chat myTask = new Communication_chat();

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
        setContentView(R.layout.activity_chating);





        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        listSize = 0;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        LocalBroadcastManager.getInstance(Chating_Activity.this).registerReceiver(UpdateInformation_list,
                new IntentFilter("updateprofile_timer"));
        Log.d("Json message Obj", Str_matchedfbid + "");
        buttonSend = (Button) findViewById(R.id.send);
        camera = (ImageView) findViewById(R.id.camera);

        listView = (ListView) findViewById(R.id.msgview);

        chatText = (EditText) findViewById(R.id.msg);
        BackButton = (TextView) findViewById(R.id.back_txt);
        prfile_name=(TextView)findViewById(R.id.nametxt);
        profileImgview = (ImageView) findViewById(R.id.profileImgview);
        Image_threedots = (ImageView) findViewById(R.id.dots_img);



        text_createDate = (TextView) findViewById(R.id.textvie_chats_creatdate);
        text_createDate.setText("");

        progressDialog = new ProgressDialog(Chating_Activity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        if (Msg_Record_obj != null) {

            try {
                Str_matchedfbid = Msg_Record_obj.getString("matchedfbid");
                Str_user1_url =pref.getString("profilepic","");
                if (Msg_Record_obj.getString("profiletype").equalsIgnoreCase("PROFILE")) {

                    Str_profiletype="PROFILE";
                    Str_User2_url = Msg_Record_obj.getString("profilepic");

                }
                else

                {
                    Str_profiletype="AD";

                    Str_User2_url = Msg_Record_obj.getString("imagetitle");


                }


                Str_userfbid = pref.getString("userid", "");
                prfile_name.setText(Msg_Record_obj.getString("fname"));
                String str_createdate="You matched with "+Msg_Record_obj.getString("fname")+" on "+Msg_Record_obj.getString("matchdate");
                text_createDate.setText(str_createdate);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (Str_matchedfbid.equalsIgnoreCase("play-date"))
        {
            Image_threedots.setVisibility(View.INVISIBLE);

        }
    else
        {

            Image_threedots.setVisibility(View.VISIBLE);
        }



        Picasso.with(getApplicationContext()).load(Str_User2_url).fit().centerCrop()
                .placeholder(R.drawable.defaultgirl)
                .into(profileImgview);

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
        Image_threedots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                chatText.clearFocus();
                InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(chatText.getWindowToken(), 0);

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.custom_dialog_unfriend_threedots, null);
                TextView textview_unfriend = alertLayout.findViewById(R.id.textview_unfriends);
                TextView textview_Flag_app = alertLayout.findViewById(R.id.textview_flagapropriate);
                TextView textview_canceinvite = alertLayout.findViewById(R.id.textview_Cancel_flagappropriate);
                RelativeLayout backround_view = alertLayout.findViewById(R.id.cutom_dilaog_threedot);


                AlertDialog.Builder alert = new AlertDialog.Builder(Chating_Activity.this);
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


                textview_unfriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        dialog.dismiss();




                            //deleteAccount show Alert
                            new AlertDialog.Builder(Chating_Activity.this)
                                    .setTitle("Unfriend?")
                                    .setMessage("Are you sure you want to Unfriend this user? You will not be able to connect again in the future and all your chats will be deleted.")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                            progressDialog = new ProgressDialog(Chating_Activity.this);
                                            progressDialog.setMessage("Deleting..."); // Setting Message
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                            progressDialog.show(); // Display Progress Dialog
                                            progressDialog.setCancelable(false);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {

                                                        Communication_unfriend myTask = new Communication_unfriend();

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
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();



                    }
                });


                textview_Flag_app.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();

                        LayoutInflater inflater = Chating_Activity.this.getLayoutInflater();
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


                        AlertDialog.Builder alert = new AlertDialog.Builder(Chating_Activity.this);
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
                        textview_ok.setText("Flag");
                        textview_ok.setEnabled(false);
                        textview_ok.setTextColor(getResources().getColor(R.color.lightgray));

                        textview_email_No_enter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after)
                            {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count)
                            {


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

                                dialog1.dismiss();

                            }
                        });

                        textview_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                dialog1.dismiss();


                                str_flag_desc=textview_email_No_enter.getText().toString();



                                            progressDialog = new ProgressDialog(Chating_Activity.this);
                                            progressDialog.setMessage("Loading..."); // Setting Message
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                            progressDialog.show(); // Display Progress Dialog
                                            progressDialog.setCancelable(false);

                                            Communication_flag myTask = new Communication_flag();

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            else
                                                myTask.execute();





                            }
                        });


                    }
                });


                textview_canceinvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();;


                    }
                });

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


                if (chatflag.equalsIgnoreCase("accept"))
               {
                   FrirndReqActivity.Activity_matchreq_remove.finish();
                   finish();
               }
               else if (chatflag.equalsIgnoreCase("viewmatch"))
               {
                   ViewMatchActivity.Activity_view_match_remove.finish();
                   finish();
               }
               else
               {
                   finish();
               }


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
                } else {

                    Str_message = "";
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            Communication_addChat myTask = new Communication_addChat();

                            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                myTask.execute();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
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

                    Communication_chat myTask = new Communication_chat();

                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        myTask.execute();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();



        profileImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Msg_Record_obj != null) {


                    if (Str_profiletype.equalsIgnoreCase("PROFILE"))
                    {
                        Profile_Discover_info.Record_obj_infoprofile=Msg_Record_obj;
                        Intent intent = new Intent(Chating_Activity.this, Profile_Discover_info.class);
                        startActivity(intent);

                    } else

                    {

                        ViewAdMatchActivity.Record_obj_AddView=Msg_Record_obj;
                        Intent intent = new Intent(Chating_Activity.this, ViewAdMatchActivity.class);
                        startActivity(intent);


                    }
                }


            }
        });

        prfile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Msg_Record_obj != null) {


                    if (Str_profiletype.equalsIgnoreCase("PROFILE"))
                    {

                        Profile_Discover_info.Record_obj_infoprofile=Msg_Record_obj;
                        Intent intent = new Intent(Chating_Activity.this, Profile_Discover_info.class);
                        startActivity(intent);
                    } else

                    {

                        ViewAdMatchActivity.Record_obj_AddView=Msg_Record_obj;
                        Intent intent = new Intent(Chating_Activity.this, ViewAdMatchActivity.class);
                        startActivity(intent);


                    }
                }


            }
        });


    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        boolean handleReturn = super.dispatchTouchEvent(ev);
//
//        View view = getCurrentFocus();
//
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//
//        if (view instanceof EditText) {
//            View innerView = getCurrentFocus();
//
//            if (ev.getAction() == MotionEvent.ACTION_UP &&
//                    !getLocationOnScreen(innerView).contains(x, y)) {
//
//                InputMethodManager input = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
//                        .getWindowToken(), 0);
//            }
//        }
//
//        return handleReturn;
//    }


    // Friends chat Php...

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
        LocalBroadcastManager.getInstance(Chating_Activity.this).unregisterReceiver(UpdateInformation_list);


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








                    if (Js_obj.getString("chattype").equalsIgnoreCase("TEXT"))
                    {
                        if (Str_userfbid.equalsIgnoreCase(Js_obj.getString("receiverfbid"))) {

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
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(imageView);

                            textView.setBackgroundResource(R.drawable.yellowtextbox);

                        } else {
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
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(imageView1);
                            if (pref.getString("gender", "").equalsIgnoreCase("Boy")) {


                                textVie3w1.setBackgroundResource(R.drawable.boy_roundxml);

                            } else {
                                textVie3w1.setBackgroundResource(R.drawable.girl_roundxml);
                            }


                        }
                    } else
                        {




                        if (Str_userfbid.equalsIgnoreCase(Js_obj.getString("receiverfbid")))
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
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(imageView2);



                            Picasso.with(getApplicationContext()).load(Js_obj.getString("imageurl")).fit().centerCrop()
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(imageViewleft);

                            imageViewleft.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    Bitmap bitmap = ((BitmapDrawable)imageViewleft.getDrawable()).getBitmap();
                                    SimpleSampleActivity.drawableBitmap=bitmap;

                                    Intent intent=new Intent(Chating_Activity.this,SimpleSampleActivity.class);
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
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(imageView3);
                            if (Js_obj.getString("imageurl").equalsIgnoreCase("")
                                    ) {


                            }
                            else {
                                Picasso.with(getApplicationContext()).load(Js_obj.getString("imageurl")).fit().centerCrop()
                                        .placeholder(R.drawable.defaultgirl)
                                        .into(imageViewriht);
                            }


                            imageViewriht.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {


                                    Bitmap bitmap = ((BitmapDrawable)imageViewriht.getDrawable()).getBitmap();
                                    SimpleSampleActivity.drawableBitmap=bitmap;
                                    Intent intent=new Intent(Chating_Activity.this,SimpleSampleActivity.class);
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

    public class Communication_chat extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {

        }

//        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.getchat);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2", Str_matchedfbid);


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


                            String date_str = obj_resul.getString("msgdate");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                comdare = format.parse(date_str);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }


            }


        }
    }

    public class Communication_addChat extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.addchat);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2", Str_matchedfbid);
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


    public class Communication_flag extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.flagprofile);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2",Str_matchedfbid);
                postDataParams.put("flagreason",str_flag_desc);
                postDataParams.put("flagfrom","chat");


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

                if (result.equalsIgnoreCase("alreadydone"))
                {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Chating_Activity.this);
                    builder1.setTitle("Flagged");
                    builder1.setMessage("You have already flagged this user, and our support team will be taking an appropriate action about it. Thank-you for your concern.");
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
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Chating_Activity.this);
                    builder1.setTitle("User Reported");
                    builder1.setMessage("Our team will check the details of this user and appropriate action will be taken. Thank-you for your help!");
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
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Chating_Activity.this);
                    builder1.setTitle("Opps");
                    builder1.setMessage("Server could not updated");
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
    public class Communication_unfriend extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {

            progressDialog.dismiss();

            try {

                URL url = new URL(UrlClass.unfriend);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2",Str_matchedfbid);


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



                if (result.equalsIgnoreCase("alreadydone"))
                {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Chating_Activity.this);
                    builder1.setTitle("Opps");
                    builder1.setMessage("You have already flagged this user, and our support team will be taking an appropriate action about it. Thank-you for your concern.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                     finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                }
                else if (result.equalsIgnoreCase("done"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Chating_Activity.this);
                    builder1.setTitle("Unfriend");
                    builder1.setMessage("You have unfriended this user, and you are no longer connected.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();

                }
                else
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Chating_Activity.this);
                    builder1.setTitle("opps");
                    builder1.setMessage("Server could not updated");
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
    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Chating_Activity.this);

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



                            if (bitmap.getHeight()>=400 && bitmap.getWidth()>=400)
                            {
                                bitmap=Bitmap.createScaledBitmap(bitmap, 400, 400, false);

                            }
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,
                                    60, bytes);



                            byte[] imageBytes = bytes.toByteArray();
                            Str_ChateEncodedImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                            Str_message = "";
                            Str_Chattype = "IMAGE";
                            Str_Img_Width = String.valueOf(254);
                            Str_Img_Height = String.valueOf(300);
                            Communication_addChat myTask = new Communication_addChat();

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

                            if (bitmap.getHeight()>=400 && bitmap.getWidth()>=400)
                            {
                                bitmap=Bitmap.createScaledBitmap(bitmap, 400, 400, false);

                            }
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
                            byte[] imageBytes = bytes.toByteArray();
                            Str_ChateEncodedImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                             Str_message="";
                            Str_Chattype="IMAGE";
                            Str_Img_Width = String.valueOf(254);
                            Str_Img_Height = String.valueOf(300);

                            Communication_addChat myTask = new Communication_addChat();

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
