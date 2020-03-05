package com.bba.playdate1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by spielmohitp on 3/10/2017.
 */
public class ProfileActivity extends AppCompatActivity {

    public TextView DescriptionTxt;
    public ImageView BackImg;
    public ImageView ProfileImg;
    public ImageView CircleImg;
    public ImageView FlagImg;
    public ImageView LikeImg;
    public ImageView MeetImg;
    public TextView NameTxt;
    public TextView PlaceTxt;
    public TextView Lang1;
    public TextView Lang2;
    public TextView Lang3;
    public TextView LikeTxt;
    public TextView MeetTxt;
    public TextView Act1Txt;
    public TextView Act2Txt;
    public TextView Act3Txt;
    public TextView Emoji1;
    public TextView Emoji2;
    public TextView Emoji3;
    public TextView SuperTxt;
    public String TabCount = "2";
    //variable
    Intent in;
    Bundle bu;
    String ProfileTypeString, MatchFbIdString, FnameString, GenderString, ProfilePicString, TypeString, MessageString, MatchDateString, MsgReadString, MsgDateString, UserdIdString, EmailIdString, EngLangString, ArbicLangString, FranchLangString, CityString, CountryString, DescriptionString, LikeToPlayString, ICanMeetString, Activity1String, Activity2String, Activity3String, Emoji1String, Emoji2String, Emoji3String, SuperHeroString, AgeString, ChatTypeString, ImgUrlString, TitleString, AdDescriptionString, SubTitleString, ColorRedString, ColorGreenString, ColorBlueString, ImageTitleString, ImgLogo1String, ImgLogo2String, ImgLogo3String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getCurrentView
        View view = (View) findViewById(R.id.main);

        //init View
        DescriptionTxt = (TextView) findViewById(R.id.desc_txt);
        ProfileImg = (ImageView) findViewById(R.id.profileImgview);
        BackImg = (ImageView) findViewById(R.id.back_imgview);
        NameTxt = (TextView) findViewById(R.id.name_txt);
        PlaceTxt = (TextView) findViewById(R.id.place_txt);
        Lang1 = (TextView) findViewById(R.id.lang1_txt);
        Lang2 = (TextView) findViewById(R.id.lang2_txt);
        Lang3 = (TextView) findViewById(R.id.lang3_txt);
        LikeImg = (ImageView) findViewById(R.id.likeimg);
        LikeTxt = (TextView) findViewById(R.id.likevalue_txt);
        MeetImg = (ImageView) findViewById(R.id.meetimg);
        MeetTxt = (TextView) findViewById(R.id.meetvalue_txt);
        Act1Txt = (TextView) findViewById(R.id.act1);
        Act2Txt = (TextView) findViewById(R.id.act2);
        Act3Txt = (TextView) findViewById(R.id.act3);
        Emoji1 = (TextView) findViewById(R.id.emoji1_txt);
        Emoji2 = (TextView) findViewById(R.id.emoji2_txt);
        Emoji3 = (TextView) findViewById(R.id.emoji3_txt);
        SuperTxt = (TextView) findViewById(R.id.super_value_txt);
        CircleImg = (ImageView) findViewById(R.id.circle_img);
        FlagImg = (ImageView) findViewById(R.id.flag_img);
        //Setup Font

        //gettting budnle data recevied from previous Activity (Chat Activity)
        try {

            in = getIntent();
            bu = in.getExtras();

            ProfileTypeString = bu.getString("profiletype");
            MatchFbIdString = bu.getString("matchedfbid");
            FnameString = bu.getString("fname");
            ;
            GenderString = bu.getString("gender");
            ProfilePicString = bu.getString("profilepic");
            TypeString = bu.getString("type");
            MessageString = bu.getString("message");
            MatchDateString = bu.getString("matchdate");
            MsgReadString = bu.getString("msgread");
            MsgDateString = bu.getString("msgdate");
            UserdIdString = bu.getString("userid");
            EmailIdString = bu.getString("emailid");
            EngLangString = bu.getString("englang");
            ArbicLangString = bu.getString("arabiclang");
            FranchLangString = bu.getString("frenchlang");
            CityString = bu.getString("city");
            CountryString = bu.getString("country");
            DescriptionString = bu.getString("description");
            LikeToPlayString = bu.getString("liketoplay");
            ICanMeetString = bu.getString("icanmeet");
            Activity1String = bu.getString("activity1");
            Activity2String = bu.getString("activity2");
            Activity3String = bu.getString("activity3");
            Emoji1String = bu.getString("emoji1");
            Emoji2String = bu.getString("emoji2");
            Emoji3String = bu.getString("emoji3");
            SuperHeroString = bu.getString("superhero");
            AgeString = bu.getString("age");
            ChatTypeString = bu.getString("chattype");
            ImgUrlString = bu.getString("imageurl");
            TitleString = bu.getString("title");
            AdDescriptionString = bu.getString("addescription");
            SubTitleString = bu.getString("subtitle");
            ColorRedString = bu.getString("colourred");
            ColorGreenString = bu.getString("colourgreen");
            ColorBlueString = bu.getString("colourblue");
            ImageTitleString = bu.getString("imagetitle");
            ImgLogo1String = bu.getString("imagelogo1");
            ImgLogo2String = bu.getString("imagelogo2");
            ImgLogo3String = bu.getString("imagelogo3");


            //  Toast.makeText(getApplicationContext(), " id "+MatchFbIdString, Toast.LENGTH_LONG).show();
            Log.e(" bundle data", bu.toString());
        } catch (Exception e) {
            Log.e("Intent Null ", e.toString());
        }


        //set Values to view
        DescriptionTxt.setText(DescriptionString);
        NameTxt.setText(FnameString);
        PlaceTxt.setText(CityString + "," + CountryString);



        //set language
        if (FranchLangString.equalsIgnoreCase("") | FranchLangString == null) {
            if (ArbicLangString.equalsIgnoreCase("") || ArbicLangString == null) {
                Lang2.setText("");
                Lang3.setText("");
                Lang1.setText(EngLangString);
            } else {
                Lang1.setText("");
                Lang2.setText(EngLangString);
                Lang3.setText(ArbicLangString);
            }
        } else {
            Lang1.setText(ArbicLangString);
            Lang2.setText(EngLangString);
            Lang3.setText(FranchLangString);
        }

        //set I Like 2 Play Value
        LikeTxt.setText(LikeToPlayString);
        if (LikeToPlayString.equalsIgnoreCase("Indoor")) {
            LikeImg.setImageResource(R.drawable.indoor);
        } else if (LikeToPlayString.equalsIgnoreCase("Outdoor")) {
            LikeImg.setImageResource(R.drawable.outdoor);
        } else {
            LikeImg.setImageResource(R.drawable.everywhere);
        }

        //set I can meet Value
        MeetTxt.setText(ICanMeetString);
        if (ICanMeetString.equalsIgnoreCase("After school")) {
            MeetImg.setImageResource(R.drawable.afterschool);
        } else if (ICanMeetString.equalsIgnoreCase("Mornings")) {
            MeetImg.setImageResource(R.drawable.mornings);
        } else {
            MeetImg.setImageResource(R.drawable.anytime);
        }

        // set Activities
        Act1Txt.setText(Activity1String);
        Act2Txt.setText(Activity2String);
        Act3Txt.setText(Activity3String);

        //set SuperHero
        SuperTxt.setText(SuperHeroString);

        if (GenderString.equalsIgnoreCase("BOY")) {
            BackImg.setImageResource(R.drawable.boypictureframe);
        } else {
            BackImg.setImageResource(R.drawable.girlpictureframe);
        }

        //set Profile Image
        Picasso.with(ProfileActivity.this)
                .load(ProfilePicString)
                .placeholder(R.drawable.defaultboy)
                //.memoryPolicy(MemoryPolicy.NO_CACHE)// optional
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

        //on click on screen
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check tab count
                if (TabCount.equalsIgnoreCase("2")) {
                    //it is 2
                    //set tab count as 1
                    TabCount = "1";

                    ImageView im = (ImageView) findViewById(R.id.circle_img);
                    im.setImageResource(R.drawable.circle2);
                    //invisible view 1
                    findViewById(R.id.back_imgview).setVisibility(View.INVISIBLE);
                    findViewById(R.id.profileImgview).setVisibility(View.INVISIBLE);
                    findViewById(R.id.name_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.place_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.emoji1_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.emoji2_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.emoji3_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.desc_txt).setVisibility(View.INVISIBLE);

                    //visible view 2
                    findViewById(R.id.speaktxt).setVisibility(View.VISIBLE);

                    //set language
                    if (FranchLangString.equalsIgnoreCase("") || FranchLangString == null) {

                        //viewHolder.Lang1.setVisibility(View.INVISIBLE);
                        if (ArbicLangString.equalsIgnoreCase("") || ArbicLangString == null) {
                            findViewById(R.id.lang1_txt).setVisibility(View.VISIBLE);
                            findViewById(R.id.lang2_txt).setVisibility(View.INVISIBLE);
                            findViewById(R.id.lang3_txt).setVisibility(View.INVISIBLE);
                        } else {
                            findViewById(R.id.lang1_txt).setVisibility(View.INVISIBLE);
                            findViewById(R.id.lang2_txt).setVisibility(View.VISIBLE);
                            findViewById(R.id.lang3_txt).setVisibility(View.VISIBLE);
                        }
                    } else {
                        findViewById(R.id.lang1_txt).setVisibility(View.VISIBLE);
                        findViewById(R.id.lang2_txt).setVisibility(View.VISIBLE);
                        findViewById(R.id.lang3_txt).setVisibility(View.VISIBLE);

                    }
                    findViewById(R.id.liketxt).setVisibility(View.VISIBLE);
                    findViewById(R.id.likeimg).setVisibility(View.VISIBLE);
                    findViewById(R.id.likevalue_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.meettxt).setVisibility(View.VISIBLE);
                    findViewById(R.id.meetimg).setVisibility(View.VISIBLE);
                    findViewById(R.id.meetvalue_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.favtxt).setVisibility(View.VISIBLE);
                    findViewById(R.id.act1).setVisibility(View.VISIBLE);
                    findViewById(R.id.act2).setVisibility(View.VISIBLE);
                    findViewById(R.id.act3).setVisibility(View.VISIBLE);
                    findViewById(R.id.super_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.super_value_txt).setVisibility(View.VISIBLE);


                } else {

                    //set count as 2
                    TabCount = "2";
                    ImageView im = (ImageView) findViewById(R.id.circle_img);
                    im.setImageResource(R.drawable.circle1);
                    //invisible view 1
                    findViewById(R.id.back_imgview).setVisibility(View.VISIBLE);
                    findViewById(R.id.profileImgview).setVisibility(View.VISIBLE);
                    findViewById(R.id.name_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.place_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.emoji1_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.emoji2_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.emoji3_txt).setVisibility(View.VISIBLE);
                    findViewById(R.id.desc_txt).setVisibility(View.VISIBLE);


                    //visible view 2
                    findViewById(R.id.speaktxt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.lang1_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.lang2_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.lang3_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.liketxt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.likeimg).setVisibility(View.INVISIBLE);
                    findViewById(R.id.likevalue_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.meettxt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.meetimg).setVisibility(View.INVISIBLE);
                    findViewById(R.id.meetvalue_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.favtxt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.act1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.act2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.act3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.super_txt).setVisibility(View.INVISIBLE);
                    findViewById(R.id.super_value_txt).setVisibility(View.INVISIBLE);
                }


            }
        });
    }
}
