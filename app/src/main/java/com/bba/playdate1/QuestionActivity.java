package com.bba.playdate1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Button btn=(Button)findViewById(R.id.gotit_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your code that you want to execute on this button click
              /*  Intent i = new Intent(mContext,GotIT.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);*/
             if (SettingActivity.questionflag.equalsIgnoreCase("yes"))
             {
                 finish();
                 overridePendingTransition(R.anim.stay, R.anim.slide_down);
             }
             else
                 {


                 Intent i = new Intent(QuestionActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 i.putExtra("fragmentcrop", "crop");
                 startActivity(i);
             }
                //overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_right_out);
            }

        });
    }
}
