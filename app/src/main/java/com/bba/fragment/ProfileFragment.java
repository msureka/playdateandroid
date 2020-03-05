package com.bba.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bba.playdate1.BasicActivity;
import com.bba.playdate1.QuestionActivity;
import com.bba.playdate1.R;
import com.bba.playdate1.SettingActivity;
import com.bba.playdate1.UrlClass;
import com.bba.playdate1.Utility;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by spielmohitp on 2/24/2017.
 */
public class ProfileFragment extends Fragment {


    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ExecutorService mExecutor;

    public static Intent createIntent(Activity activity, Uri uri) {
        Intent intent = new Intent(activity, ProfileFragment.class);
        intent.setData(uri);
        return intent;
    }

     android.support.v7.app.AlertDialog alertDialog_Box;
     Bitmap bitmap;
    private Uri file;
public static Bitmap bitmaps;

    ProgressDialog progressDialog;
    public static String tag = "inActivity";

    View v;
    ImageView SettingBtn, QuestionImg;
    TextView PlaceTxt, NameTxt, EditImgTxt;
    EditText Emoji1, Emoji2, Emoji3;
    Intent intent;
    public static ImageView ProfileImg;
    ImageView GenderImg;

    int img_gefult_boy_girl;
    //take Image
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    public  static Uri uriImage=null;
    public  SharedPreferences pref;
     SharedPreferences.Editor editor;
    android.app.AlertDialog alert;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    ContentValues values;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        SettingBtn = (ImageView) v.findViewById(R.id.setting_img);
        QuestionImg = (ImageView) v.findViewById(R.id.question_btn);
        NameTxt = (TextView) v.findViewById(R.id.name_txt);
        Emoji1 = (EditText) v.findViewById(R.id.emoji1_txt);
        Emoji2 = (EditText) v.findViewById(R.id.emoji2_txt);
        Emoji3 = (EditText) v.findViewById(R.id.emoji3_txt);
        PlaceTxt = (TextView) v.findViewById(R.id.place_txt);
        EditImgTxt = (TextView) v.findViewById(R.id.edit_pic_txt);
        ProfileImg = (ImageView) v.findViewById(R.id.profileImgview1);
        GenderImg = (ImageView) v.findViewById(R.id.back_imgview);

        Typeface font_kg=Typeface.createFromAsset(getActivity().getAssets(),"font/kg_feeling_regular.ttf");
        NameTxt.setTypeface(font_kg);


        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UpdateInformation,
                new IntentFilter("updateprofile_info"));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(image_upload_call_image,
                new IntentFilter("image_upload_call"));




        if (pref.getString("gender","").equalsIgnoreCase("BOY")) {
            GenderImg.setImageResource(R.drawable.boypictureframe);
            ProfileImg.setImageResource(R.drawable.defaultboy);
            img_gefult_boy_girl = R.drawable.defaultboy;
        } else
            {
            GenderImg.setImageResource(R.drawable.girlpictureframe);
            ProfileImg.setImageResource(R.drawable.defaultgirl);
                img_gefult_boy_girl = R.drawable.defaultgirl;
        }
String str_name_age=pref.getString("fname","")+", "+pref.getString("age","");
        NameTxt.setText(str_name_age);
        PlaceTxt.setText(pref.getString("city","") + ", " +pref.getString("country",""));
        Emoji1.setText(pref.getString("emoji1",""));
        Emoji2.setText(pref.getString("emoji2",""));
        Emoji3.setText(pref.getString("emoji3",""));

if (Emoji1.getText().length() !=0)
{
    int emojiCount = 0;
//
    for (int i = 0; i < Emoji1.length(); i++)
    {
        int type = Character.getType(Emoji1.getText().charAt(i));
        if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
        {
            emojiCount++;
        }
    }

    if (Emoji1.getText().length() == 1)
    {


        Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



    } else if ((emojiCount) == 2)
    {
        Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


    }
}

        if (Emoji2.getText().length() !=0)
        {
            int emojiCount = 0;
//
            for (int i = 0; i < Emoji2.length(); i++)
            {
                int type = Character.getType(Emoji2.getText().charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                {
                    emojiCount++;
                }
            }

            if (Emoji2.getText().length() == 1)
            {


                Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



            } else if ((emojiCount) == 2)
            {
                Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


            }
        }


        if (Emoji3.getText().length() !=0)
        {
            int emojiCount = 0;
//
            for (int i = 0; i < Emoji3.length(); i++)
            {
                int type = Character.getType(Emoji3.getText().charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                {
                    emojiCount++;
                }
            }

            if (Emoji3.getText().length() == 1)
            {


                Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



            } else if ((emojiCount) == 2)
            {
                Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


            }
        }






        Emoji1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji1.length(); i++)
                {
                    int type = Character.getType(Emoji1.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji1.getText().length() == 1)
                {


                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                int emojiCount = 0;
//
                for (int i = 0; i < Emoji1.length(); i++)
                {
                    int type = Character.getType(Emoji1.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }
                if (Emoji1.getText().length() == 0)
                {
                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});

                }
                if (Emoji1.getText().length() == 1)
                {
                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                    Emoji2.requestFocus();
                    update_emoji();


                }
                else if ((emojiCount) == 2)
                {
                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                    Emoji2.requestFocus();
                    update_emoji();


                }
                else
                {
                    if (Emoji1.getText().length() != 0)
                    {
                        Emoji2.requestFocus();
                    }

                }



            }

            @Override
            public void afterTextChanged(Editable s)
            {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji1.length(); i++)
                {
                    int type = Character.getType(Emoji1.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji1.getText().length() == 1)
                {


                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }




        });

        // Textwatcher on Emoji 2 EditText to check text entered or edit null
        Emoji2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji2.length(); i++)
                {
                    int type = Character.getType(Emoji2.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji2.getText().length() == 1)
                {


                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int emojiCount = 0;
//
                for (int i = 0; i < Emoji2.length(); i++)
                {
                    int type = Character.getType(Emoji2.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }
                if (Emoji2.getText().length() == 0)
                {
                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                }
                if (Emoji2.getText().length() == 1)
                {
                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                    Emoji3.requestFocus();
                    update_emoji();


                }
                else if ((emojiCount) == 2)
                {
                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                    Emoji3.requestFocus();
                    update_emoji();


                }
                else
                {
                    if (Emoji2.getText().length() != 0)
                    {
                        Emoji3.requestFocus();
                    }

                }



            }
            @Override
            public void afterTextChanged(Editable editable) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji2.length(); i++)
                {
                    int type = Character.getType(Emoji2.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji2.getText().length() == 1)
                {


                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

        });

        // Textwatcher on Emoji 3 EditText to check text entered or edit null
        Emoji3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji3.length(); i++)
                {
                    int type = Character.getType(Emoji3.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji3.getText().length() == 1)
                {


                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {


                int emojiCount = 0;
//
                for (int i = 0; i < Emoji3.length(); i++)
                {
                    int type = Character.getType(Emoji3.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }
                if (Emoji3.getText().length() == 0)
                {
                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                }
                if (Emoji3.getText().length() == 1)
                {
                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                    Emoji3.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Emoji3.getWindowToken(), 0);
                    update_emoji();


                }
                else if ((emojiCount) == 2)
                {
                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                    Emoji3.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Emoji3.getWindowToken(), 0);
                    update_emoji();


                }
                else
                {
                    if (Emoji3.getText().length() != 0)
                    {
                        Emoji3.clearFocus();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Emoji3.getWindowToken(), 0);
                    }

                }







            }

            @Override
            public void afterTextChanged(Editable s) {
                int emojiCount = 0;
//
                for (int i = 0; i < Emoji3.length(); i++)
                {
                    int type = Character.getType(Emoji3.getText().charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL)
                    {
                        emojiCount++;
                    }
                }

                if (Emoji3.getText().length() == 1)
                {


                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});



                } else if ((emojiCount) == 2)
                {
                    Emoji3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});


                }
            }
        });





        SettingBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {


                hideKeyboard();



                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);

            }
        });

        QuestionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                intent = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);

            }
        });

    if (pref.getString("profilepic","").length() !=0)
    {
        String pro_image=pref.getString("profilepic", "");

    Picasso.with(getActivity()).load(pro_image).fit().centerCrop()
            .placeholder(img_gefult_boy_girl)
            .into(ProfileImg);
}

        ProfileImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                hideKeyboard();

                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run() {


                        selectImage();

                    }
                });



                return false;
            }
        });
        mExecutor = Executors.newSingleThreadExecutor();

        final Uri uri = getActivity().getIntent().getData();
        mExecutor.submit(new LoadScaledImageTask(getActivity(), uri, ProfileImg, calcImageSize()));
        ProfileImg.buildDrawingCache();
        //bm = ProfileImg.getDrawingCache();

        return v;
    }





    private void update_emoji()

    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Communication_updateEmojis myTask = new Communication_updateEmojis();

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



    //UpdateImage  method
    //Actual Function handling http connection and  Checking User Deatails on Server


    private String bitmapToBase64(Bitmap bitmap) {

        //Toast.makeText(getApplicationContext(),"base64",Toast.LENGTH_SHORT).show();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e("Log In gallary", "image size in large");
        }
        return null;//Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    private BroadcastReceiver image_upload_call_image = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent)
        {


        }
    };



    private BroadcastReceiver UpdateInformation = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {


            RelativeLayout r1 = (RelativeLayout) ((Activity)getActivity()).findViewById(R.id.footer);



            String str_name_age=pref.getString("fname","")+", "+pref.getString("age","");
            NameTxt.setText(str_name_age);


            if (pref.getString("gender","").equalsIgnoreCase("BOY")) {
                GenderImg.setImageResource(R.drawable.boypictureframe);
                img_gefult_boy_girl = R.drawable.defaultboy;
                r1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bluetextbox));
            } else
            {
                GenderImg.setImageResource(R.drawable.girlpictureframe);
                img_gefult_boy_girl = R.drawable.defaultgirl;
                r1.setBackgroundDrawable(getResources().getDrawable(R.drawable.pinktextbox));
            }



        }
    };

    @Override
    public void onDestroy()
    {

        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(UpdateInformation);




    }
    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

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
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds options to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }
    private int calcImageSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return Math.min(Math.max(metrics.widthPixels, metrics.heightPixels), 2048);
    }
    public static class LoadScaledImageTask implements Runnable {
        private Handler mHandler = new Handler(Looper.getMainLooper());
        Context context;
        Uri uri;
        ImageView imageView;
        int width;

        public LoadScaledImageTask(Context context, Uri uri, ImageView imageView, int width) {
            this.context = context;
            this.uri = uri;
            this.imageView = imageView;
            this.width = width;
        }

        @Override public void run() {
            final int exifRotation = com.isseiaoki.simplecropview.util.Utils.getExifOrientation(context, uri);
            int maxSize = com.isseiaoki.simplecropview.util.Utils.getMaxSize();
            int requestSize = Math.min(width, maxSize);
            try {
                final Bitmap sampledBitmap = com.isseiaoki.simplecropview.util.Utils.decodeSampledBitmapFromUri(context, uri, requestSize);
                mHandler.post(new Runnable() {
                    @Override public void run() {
                        imageView.setImageMatrix(com.isseiaoki.simplecropview.util.Utils.getMatrixFromExifOrientation(exifRotation));
                        imageView.setImageBitmap(sampledBitmap);
                    }
                });
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


    public class  Communication_updateEmojis extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {



                URL url = new URL(UrlClass.saveemoji);
                JSONObject postDataParams = new JSONObject();





                postDataParams.put("fbid",pref.getString("userid",""));

                postDataParams.put("emoji1",Emoji1.getText().toString());
                postDataParams.put("emoji2",Emoji2.getText().toString());
                postDataParams.put("emoji3",Emoji3.getText().toString());




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

                Log.d("Exception_php", e.getMessage()+"");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {


            if (result !=null) {

                if (result.equalsIgnoreCase("done"))
                {

                    editor.putString("emoji1",Emoji1.getText().toString());
                    editor.putString("emoji2",Emoji2.getText().toString());
                    editor.putString("emoji3",Emoji3.getText().toString());
                    editor.commit();

                }
               else if (result.equalsIgnoreCase("nullerror")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Could not retrieve your Account Id. Please login and try again.");
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


                } else if (result.equalsIgnoreCase("imagenull")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("You seem to have not selected a profile image. Please try again.");
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


                } else if (result.equalsIgnoreCase("updateerror")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Error in updating your emojis. Please try again.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();


                } else if (result.equalsIgnoreCase("selecterror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Could not retrieve your Facebook Account Id. Please login and try again.");
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


                } else if (result.equalsIgnoreCase("nofbid")) {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder1.setTitle("Oops");
                    builder1.setMessage("Your account does not exist or it has been de-activated by the administrator. Please contact us at support@play-date.ae");
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
            else
            {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//
//            }
//        }
//    }


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
                            Intent intent = new Intent(getActivity(), BasicActivity.class);
                            startActivity(intent);

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
                uriImage = null;
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Log.d("width of bitmap", bitmap.getWidth() + "");
                Log.d("height of bitmap", bitmap.getHeight() + "");


                uriImage = getImageUri(getApplicationContext(), bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(getActivity(), BasicActivity.class);
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
    private void hideKeyboard() {

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        Emoji1.clearFocus();
        imm.hideSoftInputFromWindow( Emoji1.getWindowToken(), 0);

        Emoji2.clearFocus();
        imm.hideSoftInputFromWindow( Emoji2.getWindowToken(), 0);
          Emoji3.clearFocus();
            imm.hideSoftInputFromWindow( Emoji3.getWindowToken(), 0);


//            View view = getActivity().getCurrentFocus();
//            if (view != null) {
//                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
    }
}
