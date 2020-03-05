package com.bba.playdate1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class BasicActivity extends AppCompatActivity {

  public  static Activity Basicactivity_remove;
  TextView txt_babkbutton,txt_babkbutton1;
  private static final String TAG = BasicActivity.class.getSimpleName();

  public static Intent createIntent(Activity activity) {
    return new Intent(activity, BasicActivity.class);
  }

  // Lifecycle Method ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_basic);

    Basicactivity_remove=this;
    if(savedInstanceState == null){
      getSupportFragmentManager().beginTransaction().add(R.id.container, BasicFragment.newInstance()).commit();
    }

    // apply custom font
    FontUtils.setFont(findViewById(R.id.root_layout));
    txt_babkbutton=(TextView)findViewById(R.id.back_txt_Basic);
    txt_babkbutton1=(TextView)findViewById(R.id.back_txt_Basic1);


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

  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }



  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void startResultActivity(Uri uri) {
    if (isFinishing()) return;
    // Start ResultActivity
    startActivity(ResultActivity.createIntent(this, uri));
  }
}
