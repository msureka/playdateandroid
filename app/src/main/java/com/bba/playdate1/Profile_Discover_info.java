package com.bba.playdate1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile_Discover_info extends AppCompatActivity {
     ImageView BackImg, ProfileImg, CircleImg, LikeImg, MeetImg,image_circle;
     TextView NameTxt, PlaceTxt, Lang1, Lang2, Lang3, E1, E2, E3, LikeTxt, MeetTxt,
            Act1Txt, Act2Txt, Act3Txt, SuperTxt, DescriptionTxt, speaktxttitle, meettxt_title,
             liketxt_title,  favtxt_title, super_txt_title,txt_back;

    RelativeLayout relativeLayout1,relativeLayout2;



    public  static JSONObject Record_obj_infoprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__discover_info);

        relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout_one) ;
        relativeLayout2=(RelativeLayout)findViewById(R.id.relativeLayout_two) ;

        relativeLayout1.setVisibility(View.VISIBLE);
        relativeLayout2.setVisibility(View.INVISIBLE);




        DescriptionTxt = (TextView) findViewById(R.id.desc_txt_info);
        ProfileImg = (ImageView) findViewById(R.id.profileImgview_info);
        image_circle = (ImageView) findViewById(R.id.circle_img_info);
        BackImg = (ImageView) findViewById(R.id.back_imgview_info);
        txt_back=(TextView)findViewById(R.id.back_txt_ad_info);
        NameTxt = (TextView) findViewById(R.id.name_txt_info);
        PlaceTxt = (TextView) findViewById(R.id.place_txt_info);
        Lang1 = (TextView) findViewById(R.id.lang1_txt_info);
        Lang2 = (TextView) findViewById(R.id.lang2_txt_info);
        Lang3 = (TextView) findViewById(R.id.lang3_txt_info);
        E1 = (TextView) findViewById(R.id.emoji1_txt_info);
        E2 = (TextView) findViewById(R.id.emoji2_txt_info);
        E3 = (TextView) findViewById(R.id.emoji3_txt_info);
        LikeImg = (ImageView) findViewById(R.id.likeimg_info);
        LikeTxt = (TextView) findViewById(R.id.likevalue_txt1_info);
        MeetImg = (ImageView) findViewById(R.id.meetimg_info);
        MeetTxt = (TextView) findViewById(R.id.meetvalue_txt1_info);
        Act1Txt = (TextView) findViewById(R.id.act1_info);
        Act2Txt = (TextView) findViewById(R.id.act2_info);
        Act3Txt = (TextView) findViewById(R.id.act3_info);
        SuperTxt = (TextView) findViewById(R.id.super_value_txt_info);
        CircleImg = (ImageView) findViewById(R.id.circle_img_info);


        speaktxttitle = (TextView) findViewById(R.id.speaktxt_info);
        liketxt_title = (TextView) findViewById(R.id.liketxt_info);
        meettxt_title = (TextView) findViewById(R.id.meettxt_info);
        favtxt_title = (TextView) findViewById(R.id.favtxt_info);
        super_txt_title = (TextView) findViewById(R.id.super_txt_info);

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();;

            }
        });
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_circle.setImageResource(R.drawable.circle2);
                relativeLayout2.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.INVISIBLE);


            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_circle.setImageResource(R.drawable.circle1);
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.INVISIBLE);

            }
        });

        image_circle.setImageResource(R.drawable.circle1);


        Lang1.setTypeface(MainActivity.font_helvitica_bold);
        Lang2.setTypeface(MainActivity.font_helvitica_bold);
        Lang3.setTypeface(MainActivity.font_helvitica_bold);
        LikeTxt.setTypeface(MainActivity.font_helvitica_bold);
        MeetTxt.setTypeface(MainActivity.font_helvitica_bold);
        Act1Txt.setTypeface(MainActivity.font_helvitica_bold);
        Act2Txt.setTypeface(MainActivity.font_helvitica_bold);
        Act3Txt.setTypeface(MainActivity.font_helvitica_bold);
        SuperTxt.setTypeface(MainActivity.font_helvitica_bold);

        speaktxttitle.setTypeface(MainActivity.font_helvitica_light);
        liketxt_title.setTypeface(MainActivity.font_helvitica_light);
        meettxt_title.setTypeface(MainActivity.font_helvitica_light);
        favtxt_title.setTypeface(MainActivity.font_helvitica_light);
        super_txt_title.setTypeface(MainActivity.font_helvitica_light);

        PlaceTxt.setTypeface(MainActivity.Font_helvetica_regular);
        DescriptionTxt.setTypeface(MainActivity.Font_helvetica_regular);


        NameTxt.setTypeface(MainActivity.font_kg);

        if (Record_obj_infoprofile != null) {

            try {

                NameTxt.setText(Record_obj_infoprofile.getString("fname")+", "+Record_obj_infoprofile.getString("age"));
                String str_place=(Record_obj_infoprofile.getString("city"))+", "+(Record_obj_infoprofile.getString("country"));
                PlaceTxt.setText(str_place);

                E1.setText(Record_obj_infoprofile.getString("emoji1"));
                E2.setText(Record_obj_infoprofile.getString("emoji2"));
                E3.setText(Record_obj_infoprofile.getString("emoji3"));

                LikeTxt.setText(Record_obj_infoprofile.getString("liketoplay"));
                MeetTxt.setText(Record_obj_infoprofile.getString("icanmeet"));

                Act1Txt.setText(Record_obj_infoprofile.getString("activity1"));
                Act2Txt.setText(Record_obj_infoprofile.getString("activity2"));
                Act3Txt.setText(Record_obj_infoprofile.getString("activity3"));

                SuperTxt.setText(Record_obj_infoprofile.getString("superhero"));
                DescriptionTxt.setText(Record_obj_infoprofile.getString("description"));


                String str_gender=Record_obj_infoprofile.getString("gender");
                String englang=Record_obj_infoprofile.getString("englang");
                String arabiclang=Record_obj_infoprofile.getString("arabiclang");
                String frenchlang=Record_obj_infoprofile.getString("frenchlang");




                if (str_gender.equalsIgnoreCase("Boy")) {
                    BackImg.setImageResource(R.drawable.boypictureframe);
                    ProfileImg.setImageResource(R.drawable.defaultboy);

                } else
                {
                    BackImg.setImageResource(R.drawable.girlpictureframe);
                    ProfileImg.setImageResource(R.drawable.defaultgirl);

                }

                if (!englang.equalsIgnoreCase("") && !arabiclang.equalsIgnoreCase("") && !frenchlang.equalsIgnoreCase(""))
                {
                    Lang1.setVisibility(View.VISIBLE);
                    Lang2.setVisibility(View.VISIBLE);
                    Lang3.setVisibility(View.VISIBLE);
                    Lang1.setText(englang);
                    Lang2.setText(arabiclang);
                    Lang3.setText(frenchlang);
                }
                else if  (!englang.equalsIgnoreCase("") && !arabiclang.equalsIgnoreCase("") && frenchlang.equalsIgnoreCase(""))
                {
                    Lang1.setVisibility(View.INVISIBLE);
                    Lang2.setVisibility(View.VISIBLE);
                    Lang3.setVisibility(View.VISIBLE);

                    Lang2.setText(englang);
                    Lang3.setText(arabiclang);

                }
                else if  (!englang.equalsIgnoreCase("") && arabiclang.equalsIgnoreCase("") && !frenchlang.equalsIgnoreCase(""))
                {
                    Lang1.setVisibility(View.INVISIBLE);
                    Lang2.setVisibility(View.VISIBLE);
                    Lang3.setVisibility(View.VISIBLE);

                    Lang2.setText(englang);
                    Lang3.setText(frenchlang);


                }
                else if  (englang.equalsIgnoreCase("") && !arabiclang.equalsIgnoreCase("") && !frenchlang.equalsIgnoreCase(""))
                {
                    Lang1.setVisibility(View.INVISIBLE);
                    Lang2.setVisibility(View.VISIBLE);
                    Lang3.setVisibility(View.VISIBLE);

                    Lang2.setText(arabiclang);
                    Lang3.setText(frenchlang);


                }
                else if (!englang.equalsIgnoreCase("") && arabiclang.equalsIgnoreCase("") && frenchlang.equalsIgnoreCase(""))
                    {
                        Lang1.setVisibility(View.VISIBLE);
                        Lang2.setVisibility(View.INVISIBLE);
                        Lang3.setVisibility(View.INVISIBLE);
                        Lang1.setText(englang);
                        Lang2.setText("");
                        Lang3.setText("");
                    }
                    else  if (englang.equalsIgnoreCase("") && !arabiclang.equalsIgnoreCase("") && frenchlang.equalsIgnoreCase(""))
                    {
                        Lang1.setVisibility(View.VISIBLE);
                        Lang2.setVisibility(View.INVISIBLE);
                        Lang3.setVisibility(View.INVISIBLE);
                        Lang1.setText(arabiclang);
                        Lang2.setText("");
                        Lang3.setText("");
                    }
                    else  if (englang.equalsIgnoreCase("") && arabiclang.equalsIgnoreCase("") && !frenchlang.equalsIgnoreCase(""))
                    {
                        Lang1.setVisibility(View.VISIBLE);
                        Lang2.setVisibility(View.INVISIBLE);
                        Lang3.setVisibility(View.INVISIBLE);
                        Lang1.setText(frenchlang);
                        Lang2.setText("");
                        Lang3.setText("");
                    }
                    else
                    {
                        Lang1.setVisibility(View.VISIBLE);
                        Lang2.setVisibility(View.VISIBLE);
                        Lang3.setVisibility(View.VISIBLE);
                        Lang1.setText("");
                        Lang2.setText("");
                        Lang3.setText("");
                    }

                Picasso.with(Profile_Discover_info.this)
                        .load(Record_obj_infoprofile.getString("profilepic"))
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









            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }
}
