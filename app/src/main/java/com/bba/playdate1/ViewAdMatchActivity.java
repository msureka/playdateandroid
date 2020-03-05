package com.bba.playdate1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by spielmohitp on 3/7/2017.
 */
public class ViewAdMatchActivity extends Activity {


    String MatchFbidString, TitleString, AdDescriptionString, SubTitleString, ColorRedString, ColorGreenString, ColorBlueString, ImageTitleString, ImageLogo1String, ImageLogo2String, ImageLogo3String, GagaPicString, MatchDateString, FnameString, GenderString, ProfilePicString;
    TextView SubTitleTxt, TitleTxt, DecsTxt,txt_name,text_back;
    public static JSONObject Record_obj_AddView;
    int id;
    String ColorRed, ColorGreen, ColorBlue, ImageTitle, ImageLogo1, ImageLogo2, ImageLogo3;
    ImageView GaGaImg, Logo1Img, Logo2Img, Logo3Img,Logo4Img, Logo5Img;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_adpopup);


        SubTitleTxt = (TextView) findViewById(R.id.subtitle_txt);
        TitleTxt = (TextView) findViewById(R.id.title_txt);
        DecsTxt = (TextView) findViewById(R.id.desc_txt);

        txt_name = (TextView) findViewById(R.id.nametxt_ad);
        text_back = (TextView) findViewById(R.id.back_txt_ad);

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        GaGaImg = (ImageView) findViewById(R.id.gagaImg);
        Logo1Img = (ImageView) findViewById(R.id.logo1_img);
        Logo2Img = (ImageView) findViewById(R.id.logo2_img);
        Logo3Img = (ImageView) findViewById(R.id.logo3_img);
        Logo4Img = (ImageView) findViewById(R.id.logo4_img);
        Logo5Img = (ImageView) findViewById(R.id.logo5_img);

        Logo1Img.setVisibility(View.INVISIBLE);
        Logo2Img.setVisibility(View.INVISIBLE);
        Logo3Img.setVisibility(View.INVISIBLE);
        Logo4Img.setVisibility(View.INVISIBLE);
        Logo5Img.setVisibility(View.INVISIBLE);


        if (Record_obj_AddView != null) {

            try {

                DecsTxt.setText(Record_obj_AddView.getString("addescription"));
                TitleTxt.setText(Record_obj_AddView.getString("title"));
                SubTitleTxt.setText(Record_obj_AddView.getString("subtitle"));
                txt_name.setText(Record_obj_AddView.getString("fname"));

                Display display = getWindowManager().getDefaultDisplay();
                int width = display.getWidth(); // ((display.getWidth()*20)/100)
                int height = display.getHeight();// ((display.getHeight()*30)/100)
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,display.getWidth()-(display.getHeight()/5));
                GaGaImg.setLayoutParams(parms);

                Picasso.with(ViewAdMatchActivity.this)
                        .load(Record_obj_AddView.getString("imagetitle"))
                        // .placeholder(R.drawable.defaultboy)
                        //.memoryPolicy(MemoryPolicy.NO_CACHE)// optional
                        //.error(R.drawable.error)       // optional
                        // optional

                        .into(GaGaImg, new Callback() {
                            @Override
                            public void onSuccess() {
                                // pb.setVisibility(View.INVISIBLE);

                            }

                            @Override
                            public void onError() {
                                // pb.setVisibility(View.INVISIBLE);
                            }
                        })
                ;


//                SubTitle = obj.getString("subtitle");
                ColorRed = Record_obj_AddView.getString("colourred");
                ColorGreen = Record_obj_AddView.getString("colourgreen");
                ColorBlue = Record_obj_AddView.getString("colourblue");
                ImageTitle = Record_obj_AddView.getString("imagetitle");
                ImageLogo1 = Record_obj_AddView.getString("imagelogo1");
                ImageLogo2 = Record_obj_AddView.getString("imagelogo2");
                ImageLogo3 = Record_obj_AddView.getString("imagelogo3");




//                Str_matchedfbid = Record_obj_AddView.getString("matchedfbid");
//                Str_user1_url = Record_obj_AddView.getString("imagetitle");
//
//                Str_userfbid = pref.getString("userid", "");
//                prfile_name.setText(Msg_Record_obj.getString("fname"));
//                String str_createdate="You matched with "+Msg_Record_obj.getString("fname")+" on "+Msg_Record_obj.getString("matchdate");
//                text_createDate.setText(str_createdate);


                if (ImageLogo3.equalsIgnoreCase("") && ImageLogo2.equalsIgnoreCase("") && !(ImageLogo1.equalsIgnoreCase("")))
                {
                    Logo1Img.setVisibility(View.VISIBLE);
                    Logo2Img.setVisibility(View.INVISIBLE);
                    Logo3Img.setVisibility(View.INVISIBLE);
                    Logo4Img.setVisibility(View.INVISIBLE);
                    Logo5Img.setVisibility(View.INVISIBLE);

                    Picasso.with(ViewAdMatchActivity.this)
                            .load(ImageLogo1)
                            .fit()// optional
                            .into(Logo1Img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });
                }
                else if (ImageLogo3.equalsIgnoreCase("") && !(ImageLogo2.equalsIgnoreCase("")) && !(ImageLogo1.equalsIgnoreCase("")))
                {

                    Logo1Img.setVisibility(View.INVISIBLE);
                    Logo2Img.setVisibility(View.INVISIBLE);
                    Logo3Img.setVisibility(View.INVISIBLE);
                    Logo4Img.setVisibility(View.VISIBLE);
                    Logo5Img.setVisibility(View.VISIBLE);



                    Picasso.with(ViewAdMatchActivity.this)
                            .load(ImageLogo1)
                            .fit()// optional
                            .into(Logo4Img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });


                    Picasso.with(ViewAdMatchActivity.this)
                            .load(ImageLogo2)// optional
                            .fit()
                            .into(Logo5Img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });
                }
                else if (!(ImageLogo3.equalsIgnoreCase("")) && !(ImageLogo2.equalsIgnoreCase("")) && !(ImageLogo1.equalsIgnoreCase("")))
                {
                    Logo1Img.setVisibility(View.VISIBLE);
                    Logo2Img.setVisibility(View.VISIBLE);
                    Logo3Img.setVisibility(View.VISIBLE);
                    Logo4Img.setVisibility(View.INVISIBLE);
                    Logo5Img.setVisibility(View.INVISIBLE);

                    Picasso.with(ViewAdMatchActivity.this)
                            .load(ImageLogo1)// optional
                            .fit()
                            .into(Logo1Img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });


                    Picasso.with(ViewAdMatchActivity.this)
                            .load(ImageLogo2)// optional
                            .fit()
                            .into(Logo2Img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });

                    Picasso.with(ViewAdMatchActivity.this)
                            .load(ImageLogo3)// optional
                            .fit()
                            .into(Logo3Img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // pb.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onError() {
                                    // pb.setVisibility(View.INVISIBLE);
                                }
                            });
                }
                else
                {
                    Logo1Img.setVisibility(View.INVISIBLE);
                    Logo2Img.setVisibility(View.INVISIBLE);
                    Logo3Img.setVisibility(View.INVISIBLE);
                    Logo4Img.setVisibility(View.INVISIBLE);
                    Logo5Img.setVisibility(View.INVISIBLE);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }










    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }
}
