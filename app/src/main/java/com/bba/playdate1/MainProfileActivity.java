package com.bba.playdate1;

import android.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.isseiaoki.simplecropview.util.*;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by spielmohitp on 2/18/2017.
 */
public class MainProfileActivity extends AppCompatActivity {




    String Str_name="", Str_about="",Str_Lang="",Str_datebith="",GenderString="",oldTextString="";
    public  static Uri uriImage=null;
public static  Activity Mainprofile_remove_Activity;

    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;

    private Uri file;
     String namwString;
    public static Bitmap bitmaps;

    TextView emoji_txt;
     TextView NextTxt, NameTxt, BirthDateTxt, AboutMeTxt, SpeakLangTxt, TitleTxt;

    public static TextView adddpictxt,menparenttxt;
    RelativeLayout NameRow;
    LinearLayout AboutRow,BirthRow,SpeakRow;
    EditText Emoji1EdiTxt, Emoji2EdiTxt, Emoji3EdiTxt;

    Button MaleBtn, FemaleBtn;

    public static ImageView profileImgview;

    ImageView backimgview;
    //public static String a;
    String name = "", language = "", date = "", aboutme = "",Str_editetxt1="";
    //take Image variable
    private int REQUEST_CAMERA = 0, SELECT_FILE = 2;
    private String userChoosenTask;

    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainprofile);


        Mainprofile_remove_Activity=this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        LocalBroadcastManager.getInstance(this).registerReceiver(UpdateInformation,
                new IntentFilter("registerinfo"));





        TitleTxt = (TextView) findViewById(R.id.titletxt);
        NextTxt = (TextView) findViewById(R.id.next_txt);
        NameTxt = (TextView) findViewById(R.id.name_Text);
        BirthDateTxt = (TextView) findViewById(R.id.birth_editText);
        AboutMeTxt = (TextView) findViewById(R.id.about_edittxt);
        SpeakLangTxt = (TextView) findViewById(R.id.speak_Text);
        MaleBtn = (Button) findViewById(R.id.male_button);
        FemaleBtn = (Button) findViewById(R.id.female_button);
        profileImgview = (ImageView) findViewById(R.id.profileImgview);
        backimgview = (ImageView) findViewById(R.id.back_imgview);
        Emoji1EdiTxt = (EditText) findViewById(R.id.emoji1_txt);
        Emoji2EdiTxt = (EditText) findViewById(R.id.emoji2_txt);
        Emoji3EdiTxt = (EditText) findViewById(R.id.emoji3_txt);
        NameRow = (RelativeLayout) findViewById(R.id.namerl);
        BirthRow = (LinearLayout) findViewById(R.id.bdayrl);
        SpeakRow = (LinearLayout) findViewById(R.id.spealrl);
        AboutRow = (LinearLayout) findViewById(R.id.aboutrl);


         emoji_txt = (TextView) findViewById(R.id.emoji_txt);
        TextView nameTxtw = (TextView) findViewById(R.id.nameTxtw);
        TextView row1_textView = (TextView) findViewById(R.id.row1_textView);
        TextView speak_TextView = (TextView) findViewById(R.id.speak_TextView);
        TextView about_TextView = (TextView) findViewById(R.id.about_TextView);
        adddpictxt = (TextView) findViewById(R.id.adddpictxt);
        menparenttxt = (TextView) findViewById(R.id.menparenttxt);





        //set Next Textview enable
        NextTxt.setEnabled(false);
        NextTxt.setTextColor(Color.parseColor("#b4b4b4"));


        try {

            //set values to view
            NameTxt.setText("");
            namwString = NameTxt.toString();

            SpeakLangTxt.setText("");






            BirthDateTxt.setText("");




            Emoji1EdiTxt.setText("");
            Emoji2EdiTxt.setText("");
            Emoji3EdiTxt.setText("");

            FemaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.gaycricle));
            MaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.gaycricle));

        } catch (Exception e) {

        }

        //On Profile Img on click
        profileImgview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (ContextCompat.checkSelfPermission(MainProfileActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainProfileActivity.this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
                }

                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run() {


                        selectImage();

                    }
                });
                return false;
            }
        });


        // Textwatcher on Emoji 1 EditText to check text entered or edit null
        Emoji1EdiTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji1EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji1EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji1EdiTxt.getText().length() == 1)
                {


                    Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

               int emojiCount = 0;
//
                for (int i = 0; i < Emoji1EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji1EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }
                if (Emoji1EdiTxt.getText().length() == 0)
                {
                    Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                }
  if (Emoji1EdiTxt.getText().length() == 1)
    {
        Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

        Emoji2EdiTxt.requestFocus();


    }
    else if ((emojiCount) == 2)
    {
        Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        Emoji2EdiTxt.requestFocus();


    }
    else
  {
      if (Emoji1EdiTxt.getText().length() != 0)
      {
          Emoji2EdiTxt.requestFocus();
      }

  }



            }

            @Override
            public void afterTextChanged(Editable s)
            {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji1EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji1EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji1EdiTxt.getText().length() == 1)
                {


                    Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji1EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }
        });

        // Textwatcher on Emoji 2 EditText to check text entered or edit null
        Emoji2EdiTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji2EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji2EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji2EdiTxt.getText().length() == 1)
                {


                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int emojiCount = 0;
//
                for (int i = 0; i < Emoji2EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji2EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }
                if (Emoji2EdiTxt.getText().length() == 0)
                {
                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                }
                if (Emoji2EdiTxt.getText().length() == 1)
                {
                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                    Emoji3EdiTxt.requestFocus();


                }
                else if ((emojiCount) == 2)
                {
                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                    Emoji3EdiTxt.requestFocus();


                }
                else
                {
                    if (Emoji2EdiTxt.getText().length() != 0)
                    {
                        Emoji3EdiTxt.requestFocus();
                    }

                }



            }
            @Override
            public void afterTextChanged(Editable editable) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji2EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji2EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji2EdiTxt.getText().length() == 1)
                {


                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji2EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

        });

        // Textwatcher on Emoji 3 EditText to check text entered or edit null
        Emoji3EdiTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji3EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji3EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji3EdiTxt.getText().length() == 1)
                {


                    Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {


                    int emojiCount = 0;
//
                    for (int i = 0; i < Emoji3EdiTxt.length(); i++)
                    {
                        int type = Character.getType(Emoji3EdiTxt.getText().charAt(i));
                        if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                        {
                            emojiCount++;
                        }
                    }
                    if (Emoji3EdiTxt.getText().length() == 0)
                    {
                        Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                    }
                    if (Emoji3EdiTxt.getText().length() == 1)
                    {
                        Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                        Emoji3EdiTxt.clearFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Emoji3EdiTxt.getWindowToken(), 0);


                    }
                    else if ((emojiCount) == 2)
                    {
                        Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                        Emoji3EdiTxt.clearFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Emoji3EdiTxt.getWindowToken(), 0);


                    }
                    else
                    {
                        if (Emoji3EdiTxt.getText().length() != 0)
                        {
                            Emoji3EdiTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Emoji3EdiTxt.getWindowToken(), 0);
                        }

                    }







            }

            @Override
            public void afterTextChanged(Editable s) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji3EdiTxt.length(); i++)
                {
                    int type = Character.getType(Emoji3EdiTxt.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji3EdiTxt.getText().length() == 1)
                {


                    Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji3EdiTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }
        });


        //Next text onclick function
        NextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.putString("emoji1",String.valueOf(Emoji1EdiTxt.getText()));
                editor.putString("emoji2",String.valueOf(Emoji2EdiTxt.getText()));
                editor.putString("emoji3",String.valueOf(Emoji3EdiTxt.getText()));
                editor.commit();

                Intent intent = new Intent(MainProfileActivity.this, PlayProfileActivity.class);
                intent.putExtra("playprofile_row","no");
                startActivity(intent);

            }
        });


        //Male Btn onclick function
        MaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set male button selected
                MaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bluecircle));
                //set female button deselected
                FemaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.gaycricle));


                backimgview.setImageResource(R.drawable.boypictureframe);

                // backimgview.setBackgroundDrawable(getResources().getDrawable(R.drawable.boypictureframe));


                GenderString = "Boy";
                editor.putString("gender",GenderString);
                editor.commit();


                if (Str_name.length() !=0 &&Str_about.length()!=0 && Str_Lang.length()!=0 && Str_datebith.length() !=0 && GenderString.length() !=0)
                {
                    NextTxt.setEnabled(true);
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                }
                else
                {
                    NextTxt.setEnabled(false);
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                }

            }
        });

        //FeMale Btn onclick function
        FemaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set male button deselected
                MaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.gaycricle));
                //set female button selected
                FemaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.pinkcircle));


                backimgview.setImageResource(R.drawable.girlpictureframe);
                //set String Variable as Girl
                GenderString = "Girl";
                editor.putString("gender",GenderString);
                editor.commit();

                if (Str_name.length() !=0 &&Str_about.length()!=0 && Str_Lang.length()!=0 && Str_datebith.length() !=0 && GenderString.length() !=0)
                {
                    NextTxt.setEnabled(true);
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                }
                else
                {
                    NextTxt.setEnabled(false);
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                }



            }
        });

        //Name Row onclik function
        NameRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent to NameActivity
                Intent intent = new Intent(MainProfileActivity.this, NameActivity.class);
                intent.putExtra("name_row","no");
                startActivity(intent);

            }
        });

        //AboutMe Row onclik function
        AboutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to AboutMeActivity
                Intent intent = new Intent(MainProfileActivity.this, AboutMeActivity.class);
                intent.putExtra("about_row","no");
                startActivity(intent);


            }
        });

        //BirthDate Row onclik function
        BirthRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainProfileActivity.this, BirthDateActivity.class);
                intent.putExtra("dob_row","no");
                startActivity(intent);


            }
        });

        //Language Row onclik function
        SpeakRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainProfileActivity.this, LanguagesNewActivity.class);
                intent.putExtra("speak_row","no");
                startActivity(intent);


            }
        });





    }





    protected void setupParent(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParent(innerView);
            }
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    //On back Pressed
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //disconnectFromFacebook();
    }

    @Override
    public void onResume() {
        super.onResume();
        //    disconnectFromFacebook();

    }



    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        // This is somewhat like [[NSNotificationCenter defaultCenter] removeObserver:name:object:]

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(UpdateInformation);
        LoginManager.getInstance().logOut();


    }




    private BroadcastReceiver UpdateInformation = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {

           Str_name=pref.getString("user_name","");
             Str_about=pref.getString("user_about","");
             Str_Lang=pref.getString("language","");

            Str_datebith=pref.getString("birthdate","");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = format1.parse(Str_datebith);
                String Str_DOb = format2.format(date);
                BirthDateTxt.setText(Str_DOb);
            } catch (ParseException e) {
                e.printStackTrace();
            }








            if (Str_Lang !=null ||! Str_Lang.equalsIgnoreCase(""))
            {
                try {
                    JSONArray array=new JSONArray(Str_Lang);
                    if (array.length()==3)
                    {
                        String concatStr=array.get(0)+","+array.get(1)+","+array.get(2);
                        SpeakLangTxt.setText(concatStr);
                    }
                    if (array.length()==2)
                    {
                        String concatStr=array.get(0)+","+array.get(1);
                        SpeakLangTxt.setText(concatStr);
                    }
                    if (array.length()==1)
                    {
                        String concatStr= (String)array.get(0);
                        SpeakLangTxt.setText(concatStr);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                SpeakLangTxt.setText("");
            }





            if (Str_about.length()<=0)
            {
                AboutMeTxt.setText("add here");
            }
            else
                {
                    AboutMeTxt.setText("edit here");
                }
            NameTxt.setText(Str_name);





            if (Str_name.length() !=0 &&Str_about.length()!=0 && Str_Lang.length()!=0 && Str_datebith.length() !=0 && GenderString.length() !=0)
            {
                NextTxt.setEnabled(true);
                NextTxt.setTextColor(Color.parseColor("#000000"));
            }
            else
            {
                NextTxt.setEnabled(false);
                NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
            }





        }
    };

    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainProfileActivity.this);

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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override

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

        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //ProfileImg.setImageBitmap(loadBitmap(String.valueOf(file)));


                uriImage = null;
                if (file !=null) {
                    bitmap = loadBitmap(String.valueOf(file));

                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(new Runnable() {
                        public void run() {


                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            Log.d("width of bitmap", bitmap.getWidth() + "");
                            Log.d("height of bitmap", bitmap.getHeight() + "");

                            uriImage = getImageUri(getApplicationContext(), bitmap);
                            Intent intent = new Intent(MainProfileActivity.this, BasicActivity.class);
                            startActivity(intent);

                        }
                    });

                }
            }
        }
        else if (requestCode == 200) {

            if (data != null) {
                try {
                    Uri selectedImage = data.getData();
                    editor.putString("select_photo", "no");
                    editor.commit();
                    uriImage = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    Log.d("width of bitmap", bitmap.getWidth() + "");
                    Log.d("height of bitmap", bitmap.getHeight() + "");


                    uriImage = getImageUri(getApplicationContext(), bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(MainProfileActivity.this, BasicActivity.class);
                startActivity(intent);
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
