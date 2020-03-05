package com.bba.playdate1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by spielmohitp on 3/7/2017.
 */
public class ViewMatchActivity extends Activity {

    Intent intent, in;
    Bundle bu;
    String TypeString, MatchFbidString, MatchDateString, FnameString, GenderString, ProfilePicString, UserIdString, EmailIdString, EngLangString, ArbicLangString, FrenchLangString, CityString, CountryString, DescriptionString, LikeToPlayString, ICanMeetString, Activity1String, Activity2String, Activity3String, Emoji1String, Emoji2String, Emoji3String, SuperHeroString, AgeString;
    TextView LaterTxt, GagaTxt, YouMeTxt;
    Button LetsChatBtn;
    int id;
    public  static  Activity Activity_view_match_remove;
    ImageView GenderImg, ProfileImg;
public  static JSONObject chatsrecord=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewmatch);


        LaterTxt = (TextView) findViewById(R.id.later_txt);
        GagaTxt = (TextView) findViewById(R.id.gaga_txt);
        YouMeTxt = (TextView) findViewById(R.id.youme_txt);
        LetsChatBtn = (Button) findViewById(R.id.letschat_btn);
        ProfileImg = (ImageView) findViewById(R.id.profileImgview);
        GenderImg = (ImageView) findViewById(R.id.back_imgview);
        Activity_view_match_remove=this;

        if(chatsrecord != null)
        {






            try {





                GenderString = chatsrecord.getString("gender");
                ProfilePicString = chatsrecord.getString("profilepic");
                UserIdString = chatsrecord.getString("userid");

                TypeString = chatsrecord.getString("profiletype");

        String str_title="You and "+chatsrecord.getString("fname")+" both want to play!";
                YouMeTxt.setText(str_title);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        else
            {
        }





        if (GenderString.equalsIgnoreCase("Boy")) {
            GenderImg.setImageResource(R.drawable.boypictureframe);
            ProfileImg.setImageResource(R.drawable.defaultboy);
            id = R.drawable.defaultboy;

        } else {
            GenderImg.setImageResource(R.drawable.girlpictureframe);
            ProfileImg.setImageResource(R.drawable.defaultgirl);
            id = R.drawable.defaultgirl;
        }

        Picasso.with(ViewMatchActivity.this)
                .load(ProfilePicString)
                .placeholder(id)
                .memoryPolicy(MemoryPolicy.NO_CACHE)// optional
                //.error(R.drawable.error)       // optional
                .resize(200, 200)               // optional
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



        LetsChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent1 = new Intent(ViewMatchActivity.this, Chating_Activity.class);
                Chating_Activity.Msg_Record_obj = chatsrecord;//Array_MessagesFriends.get((Integer) llGallery.getTag());
                Chating_Activity.chatflag = "viewmatch";
                startActivity(intent1);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }
}
