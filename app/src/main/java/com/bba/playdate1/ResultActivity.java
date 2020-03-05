package com.bba.playdate1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bba.fragment.ProfileFragment;
import com.isseiaoki.simplecropview.util.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class ResultActivity extends AppCompatActivity {
  private static final String TAG = ResultActivity.class.getSimpleName();
  private static ImageView mImageView;
  private ExecutorService mExecutor;
  android.support.v7.app.AlertDialog alertDialog_Box;
  TextView txt_babkbutton,button_savePicture,txt_babkbutton1;
ProgressDialog progressDialog;
  static Bitmap bitmaps;
  public static SharedPreferences pref;
  SharedPreferences.Editor editor;

  public static Intent createIntent(Activity activity, Uri uri) {
    Intent intent = new Intent(activity, ResultActivity.class);
    intent.setData(uri);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);
    pref =getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    editor = pref.edit();


    txt_babkbutton=(TextView)findViewById(R.id.back_txt_resultimage);
    txt_babkbutton1=(TextView)findViewById(R.id.back_txt_resultimage1);
    button_savePicture=(TextView)findViewById(R.id.txt_saveResultimage);
    mImageView = (ImageView) findViewById(R.id.result_image);

    txt_babkbutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v)
      {

        MainProfileActivity.bitmaps=null;
        MainProfileActivity.profileImgview=null;
      finish();

      }
    });
    txt_babkbutton1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v)
      {

        MainProfileActivity.bitmaps=null;
        MainProfileActivity.profileImgview=null;
        finish();

      }
    });
    button_savePicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v)
      {


        if (pref.getString("Loginplay", "").equalsIgnoreCase("yes"))
        {
          mExecutor.shutdown();
          progressDialog = new ProgressDialog(ResultActivity.this);
          progressDialog.setMessage("Save picture..."); // Setting Message
          progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
          progressDialog.show(); // Display Progress Dialog
          progressDialog.setCancelable(false);




                        Communication_updateprofilePicture myTask1 = new Communication_updateprofilePicture();

                            myTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




        }
        else
        {


          MainProfileActivity.adddpictxt.setVisibility(View.INVISIBLE);
          MainProfileActivity.menparenttxt.setVisibility(View.INVISIBLE);
          if (mImageView.getDrawable()!=null)
          {
            MainProfileActivity.profileImgview.setImageBitmap(((BitmapDrawable) mImageView.getDrawable()).getBitmap());
            MainProfileActivity.bitmaps = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
          }


          BasicActivity.Basicactivity_remove.finish();
          finish();
        }


      }
    });


    mExecutor = Executors.newSingleThreadExecutor();

    final Uri uri = getIntent().getData();
    mExecutor.submit(new LoadScaledImageTask(this, uri, mImageView, calcImageSize()));
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
  public class  Communication_updateprofilePicture extends AsyncTask<String, Void, String> {

    protected void onPreExecute()
    {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected String doInBackground(String... arg0) {


      try {



        URL url = new URL(UrlClass.updateimage);
        JSONObject postDataParams = new JSONObject();




        postDataParams.put("fbid",pref.getString("userid",""));

        if (bitmaps !=null)
        {

          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, baos);
          byte[] imageBytes = baos.toByteArray();
          String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
          postDataParams.put("profileimage",encodedImage);
        }
        else
        {
          postDataParams.put("profileimage","");
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

        Log.d("Exception_php", e.getMessage()+"");
        return new String("Exception: " + e.getMessage());
      }

    }

    @Override
    protected void onPostExecute(String result) {

      progressDialog.dismiss();
      if (result !=null) {

        if (result.equalsIgnoreCase("nullerror")) {
          android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
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
          android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
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


        } else if (result.equalsIgnoreCase("imageerror")) {
          android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
          builder1.setTitle("Oops");
          builder1.setMessage("Could not upload the profile image you have selected. Please try again.");
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


        } else if (result.equalsIgnoreCase("selecterror")) {
          android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
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
          android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
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
        else if (result.equalsIgnoreCase("imageupdated"))
        {

        }
        else if (result.equalsIgnoreCase("Exception: Network is unreachable"))
        {
          android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
          builder1.setTitle("Oops");
          builder1.setMessage("Network is unreachable");
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



          try {
            JSONArray arr_result=new JSONArray(result);
JSONObject obj_result=new JSONObject(String.valueOf(arr_result.get(0)));

            String str_result= obj_result.getString("profilepic");

            Picasso.with(ResultActivity.this).invalidate(str_result);
                    Picasso.with(ResultActivity.this)
                .load(str_result)
                .placeholder(R.drawable.defaultboy)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(ProfileFragment.ProfileImg);

            editor.putString("profilepic",str_result);
            editor.commit();

            BasicActivity.Basicactivity_remove.finish();
            finish();
          } catch (JSONException e) {
            e.printStackTrace();
          }


        }

      }
      else
      {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ResultActivity.this);
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


  @Override
  protected void onDestroy() {
    mExecutor.shutdown();
    super.onDestroy();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }



  private int calcImageSize() {
    DisplayMetrics metrics = new DisplayMetrics();
    Display display = getWindowManager().getDefaultDisplay();
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

    @Override
    public void run() {
      final int exifRotation = Utils.getExifOrientation(context, uri);
      int maxSize = Utils.getMaxSize();
      int requestSize = Math.min(width, maxSize);
      try {
        final Bitmap sampledBitmap = Utils.decodeSampledBitmapFromUri(context, uri, requestSize);
        mHandler.post(new Runnable() {
          @Override
          public void run() {

            imageView.setImageMatrix(Utils.getMatrixFromExifOrientation(exifRotation));
            Bitmap imageWithBG = Bitmap.createBitmap(sampledBitmap.getWidth(), sampledBitmap.getHeight(),sampledBitmap.getConfig());  // Create another image the same size
            imageWithBG.eraseColor(Color.WHITE);  // set its background to white, or whatever color you want
            Canvas canvas = new Canvas(imageWithBG);  // create a canvas to draw on the new image
            canvas.drawBitmap(sampledBitmap, 0f, 0f, null); // draw old image on the background
            sampledBitmap.recycle();

            imageView.setImageBitmap(imageWithBG);
            //imageView.setImageBitmap(Bitmap.CompressFormat.JPEG, imageWithBG);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            imageWithBG.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//
//
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, sampledBitmap.getWidth(),
//                    sampledBitmap.getHeight(), false));
            mImageView.setImageBitmap(imageWithBG);
            if (pref.getString("Loginplay", "").equalsIgnoreCase("yes"))
            {

              bitmaps = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
              ProfileFragment.bitmaps = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            }



          }
        });
      } catch (OutOfMemoryError e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }
}
