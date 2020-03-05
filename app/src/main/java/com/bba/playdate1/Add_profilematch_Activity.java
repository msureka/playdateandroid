package com.bba.playdate1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Add_profilematch_Activity extends AppCompatActivity {

    TextView LaterTxt;

String GenderString="",ProfilePicString="";
    ImageView ProfileImg;
    public  static JSONObject chatsrecord_add=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profilematch_);

        LaterTxt = (TextView) findViewById(R.id.later_txt22);
        ProfileImg = (ImageView) findViewById(R.id.profileImgview22);



        if(chatsrecord_add != null)
        {






            try {





                GenderString = chatsrecord_add.getString("gender");
                ProfilePicString = chatsrecord_add.getString("imagetitle");


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        else
        {
        }



int id=0;

        if (GenderString.equalsIgnoreCase("Boy")) {

            ProfileImg.setImageResource(R.drawable.defaultboy);
            id = R.drawable.defaultboy;

        } else {

            ProfileImg.setImageResource(R.drawable.defaultgirl);
            id = R.drawable.defaultgirl;
        }

        Picasso.with(Add_profilematch_Activity.this)
                .load(ProfilePicString)
                .placeholder(id)
                .memoryPolicy(MemoryPolicy.NO_CACHE)// optional
                //.error(R.drawable.error)       // optional
                // optional
                .into(ProfileImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        // pb.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError() {
                        // pb.setVisibility(View.INVISIBLE);
                    }
                });





        LaterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                finish();

            }
        });


    }
}
