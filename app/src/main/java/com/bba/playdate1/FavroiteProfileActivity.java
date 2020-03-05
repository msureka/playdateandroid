package com.bba.playdate1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class FavroiteProfileActivity extends AppCompatActivity {

     public  static  String Activity1 = "", Activity2 = "", Activity3 ="";
    public boolean ParkBool, MovieBool, SwinningBool, BoardGamesBool, BankingBool, BikeRidesBool, AnimalsBool, PintingBool, SportsBool, CrawlingBool, BlockBool, SandpitBool;
    public static Activity removeActivity_FavroiteProfileActivity;
    TextView BackTxt, NextTxt, TitleTxt;
    TextView ParkTxt, MovieTxt, SwinningTxt, BoardGamesTxt, BankingTxt, BikeRidesTxt, AnimalsTxt, PintingTxt, SportsTxt, CrawlingTxt, BlocksTxt, SandpitTxt;
    ArrayList<String> Favroites;
    Intent intent;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriteprofile);
        removeActivity_FavroiteProfileActivity=this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        //init variable.
        BackTxt = (TextView) findViewById(R.id.back_txt);
        NextTxt = (TextView) findViewById(R.id.next_txt);
        TitleTxt = (TextView) findViewById(R.id.titletxt);
        ParkTxt = (TextView) findViewById(R.id.park_txt);
        MovieTxt = (TextView) findViewById(R.id.movie_txt);
        SwinningTxt = (TextView) findViewById(R.id.swm_txt);
        BoardGamesTxt = (TextView) findViewById(R.id.game_txt);
        BankingTxt = (TextView) findViewById(R.id.bank_txt);
        BikeRidesTxt = (TextView) findViewById(R.id.bike_txt);
        AnimalsTxt = (TextView) findViewById(R.id.ani_txt);
        PintingTxt = (TextView) findViewById(R.id.paint_txt);
        SportsTxt = (TextView) findViewById(R.id.sport_txt);
        CrawlingTxt = (TextView) findViewById(R.id.crawling_txt);
        BlocksTxt = (TextView) findViewById(R.id.blocks_txt);
        SandpitTxt = (TextView) findViewById(R.id.sandpit_txt);

        TextView fa_txt = (TextView) findViewById(R.id.fa_txt);
        TextView pick_txt = (TextView) findViewById(R.id.pick_txt);





        //Array list to add selected Fav act in ListView
        Favroites = new ArrayList<String>();

        //Back text Set Visible
        BackTxt.setVisibility(View.VISIBLE);

        //set Title Text
        TitleTxt.setText("4/5");

        //Next Text default Value

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("favprofile_row").equalsIgnoreCase("yes"))
            {

            }
            else
            {
                Activity1=pref.getString("activity1","");
                Activity2=pref.getString("activity2","");
                Activity3=pref.getString("activity3","");
            }
        }
        else
        {

            Activity1=pref.getString("activity1","");
            Activity2=pref.getString("activity2","");
            Activity3=pref.getString("activity3","");
        }



            if (Activity1.equalsIgnoreCase("Park") || Activity2.equalsIgnoreCase("Park") || Activity3.equalsIgnoreCase("Park")) {

                Favroites.add("Park");
                ParkTxt.setTypeface(Typeface.DEFAULT_BOLD);
                ParkTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                ParkBool=true;


            }
            if (Activity1.equalsIgnoreCase("Movie") || Activity2.equalsIgnoreCase("Movie") || Activity3.equalsIgnoreCase("Movie")) {

                Favroites.add("Movie");
                MovieTxt.setTypeface(Typeface.DEFAULT_BOLD);
                MovieTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                MovieBool = true;
            }
            if (Activity1.equalsIgnoreCase("Swimming") || Activity2.equalsIgnoreCase("Swimming") || Activity3.equalsIgnoreCase("Swimming")) {

                Favroites.add("Swimming");
                SwinningTxt.setTypeface(Typeface.DEFAULT_BOLD);
                SwinningTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                SwinningBool = true;
            }
            if (Activity1.equalsIgnoreCase("Board games") || Activity2.equalsIgnoreCase("Board games") || Activity3.equalsIgnoreCase("Board games")) {

                Favroites.add("Board games");
                BoardGamesTxt.setTypeface(Typeface.DEFAULT_BOLD);
                BoardGamesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                BoardGamesBool = true;
            }
            if (Activity1.equalsIgnoreCase("Banking") || Activity2.equalsIgnoreCase("Banking") || Activity3.equalsIgnoreCase("Banking")) {
                Favroites.add("Banking");
                BankingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                BankingBool = true;
                BankingTxt.setTypeface(Typeface.DEFAULT_BOLD);
            }
            if (Activity1.equalsIgnoreCase("Bike Rides") || Activity2.equalsIgnoreCase("Bike Rides") || Activity3.equalsIgnoreCase("Bike Rides")) {

                Favroites.add("Bike Rides");
                BikeRidesTxt.setTypeface(Typeface.DEFAULT_BOLD);
                BikeRidesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                BikeRidesBool = true;
            }
            if (Activity1.equalsIgnoreCase("Animals") || Activity2.equalsIgnoreCase("Animals") || Activity3.equalsIgnoreCase("Animals")) {

                Favroites.add("Animals");
                AnimalsTxt.setTypeface(Typeface.DEFAULT_BOLD);
                AnimalsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                AnimalsBool = true;
            }
            if (Activity1.equalsIgnoreCase("Painting") || Activity2.equalsIgnoreCase("Painting") || Activity3.equalsIgnoreCase("Painting")) {
                Favroites.add("Painting");
                PintingTxt.setTypeface(Typeface.DEFAULT_BOLD);
                PintingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                PintingBool = true;
            }
            if (Activity1.equalsIgnoreCase("Sports") || Activity2.equalsIgnoreCase("Sports") || Activity3.equalsIgnoreCase("Sports")) {

                Favroites.add("Sports");
                SportsTxt.setTypeface(Typeface.DEFAULT_BOLD);
                SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                SportsBool = true;
            }
            if (Activity1.equalsIgnoreCase("Crawling") || Activity2.equalsIgnoreCase("Crawling") || Activity3.equalsIgnoreCase("Crawling")) {
                Favroites.add("Crawling");
                CrawlingTxt.setTypeface(Typeface.DEFAULT_BOLD);
                CrawlingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                CrawlingBool = true;
            }
            if (Activity1.equalsIgnoreCase("Blocks") || Activity2.equalsIgnoreCase("Blocks") || Activity3.equalsIgnoreCase("Blocks")) {

                Favroites.add("Blocks");
                BlocksTxt.setTypeface(Typeface.DEFAULT_BOLD);
                BlocksTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                BlockBool = true;
            }
            if (Activity1.equalsIgnoreCase("Sandpit") || Activity2.equalsIgnoreCase("Sandpit") || Activity3.equalsIgnoreCase("Sandpit")) {

                Favroites.add("Sandpit");
                SandpitTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                SandpitBool = true;
            }



        //setupe Selected Values

            //  Toast.makeText(getApplicationContext(),Avtivity1+"  "+Activity2+" "+Activity3, Toast.LENGTH_LONG).show();
        if (Favroites.size() == 3) {
            NextTxt.setTextColor(Color.parseColor("#000000"));
            NextTxt.setEnabled(true);
        } else {
            NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
            NextTxt.setEnabled(false);
        }



        NextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {
                    if (extras.getString("favprofile_row").equalsIgnoreCase("yes"))
                    {
                        if (Favroites.size()==3)
                        {

                            Activity1=Favroites.get(0);
                            Activity2=Favroites.get(1);
                            Activity3=Favroites.get(2);
                        }
                        else if (Favroites.size()==2)
                        {
                            Activity1=Favroites.get(0);
                            Activity2=Favroites.get(1);
                            Activity3="";
                        }
                        else if (Favroites.size()==1)
                        {
                            Activity1=Favroites.get(0);
                            Activity2="";
                            Activity3="";
                        }
                        else if (Favroites.size()==0)
                        {
                            Activity1="";
                            Activity2="";
                            Activity3="";
                        }

                        intent = new Intent(FavroiteProfileActivity.this, SuperHeroProfileActivity.class);
                        intent.putExtra("superprofile_row","yes");
                        startActivity(intent);
                    }
                    else
                    {
                        if (Favroites.size()==3)
                        {

                            editor.putString("activity1", Favroites.get(0));
                            editor.putString("activity2", Favroites.get(1));
                            editor.putString("activity3", Favroites.get(2));
                        }
                        else if (Favroites.size()==2)
                        {
                            editor.putString("activity1", Favroites.get(0));
                            editor.putString("activity2", Favroites.get(1));
                            editor.putString("activity3", "");
                        }
                        else if (Favroites.size()==1)
                        {
                            editor.putString("activity1", Favroites.get(0));
                            editor.putString("activity2", "");
                            editor.putString("activity3", "");
                        }
                        else if (Favroites.size()==0)
                        {
                            editor.putString("activity1", "");
                            editor.putString("activity2", "");
                            editor.putString("activity3", "");
                        }


                        editor.commit();

                        intent = new Intent(FavroiteProfileActivity.this, SuperHeroProfileActivity.class);
                        intent.putExtra("superprofile_row","no");
                        startActivity(intent);
                    }
                }
                else
                {

                    if (Favroites.size()==3)
                    {

                        editor.putString("activity1", Favroites.get(0));
                        editor.putString("activity2", Favroites.get(1));
                        editor.putString("activity3", Favroites.get(2));
                    }
                    else if (Favroites.size()==2)
                    {
                        editor.putString("activity1", Favroites.get(0));
                        editor.putString("activity2", Favroites.get(1));
                        editor.putString("activity3", "");
                    }
                    else if (Favroites.size()==1)
                    {
                        editor.putString("activity1", Favroites.get(0));
                        editor.putString("activity2", "");
                        editor.putString("activity3", "");
                    }
                    else if (Favroites.size()==0)
                    {
                        editor.putString("activity1", "");
                        editor.putString("activity2", "");
                        editor.putString("activity3", "");
                    }


                    editor.commit();

                    intent = new Intent(FavroiteProfileActivity.this, SuperHeroProfileActivity.class);
                    intent.putExtra("superprofile_row","no");
                    startActivity(intent);
                }




            }
        });

        //Back text onclick function
        BackTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {
                    if (extras.getString("favprofile_row").equalsIgnoreCase("yes"))
                    {
                        if (Favroites.size()==3)
                        {

                            Activity1=Favroites.get(0);
                            Activity2=Favroites.get(1);
                            Activity3=Favroites.get(2);
                        }
                        else if (Favroites.size()==2)
                        {
                            Activity1=Favroites.get(0);
                            Activity2=Favroites.get(1);
                            Activity3="";
                        }
                        else if (Favroites.size()==1)
                        {
                            Activity1=Favroites.get(0);
                            Activity2="";
                            Activity3="";
                        }
                        else if (Favroites.size()==0)
                        {
                            Activity1="";
                            Activity2="";
                            Activity3="";
                        }



                    }
                    else
                    {
                        if (Favroites.size()==3)
                        {

                            editor.putString("activity1", Favroites.get(0));
                            editor.putString("activity2", Favroites.get(1));
                            editor.putString("activity3", Favroites.get(2));
                        }
                        else if (Favroites.size()==2)
                        {
                            editor.putString("activity1", Favroites.get(0));
                            editor.putString("activity2", Favroites.get(1));
                            editor.putString("activity3", "");
                        }
                        else if (Favroites.size()==1)
                        {
                            editor.putString("activity1", Favroites.get(0));
                            editor.putString("activity2", "");
                            editor.putString("activity3", "");
                        }
                        else if (Favroites.size()==0)
                        {
                            editor.putString("activity1", "");
                            editor.putString("activity2", "");
                            editor.putString("activity3", "");
                        }



                        editor.commit();
                    }
                }
                else
                {

                    if (Favroites.size()==3)
                    {

                        editor.putString("activity1", Favroites.get(0));
                        editor.putString("activity2", Favroites.get(1));
                        editor.putString("activity3", Favroites.get(2));
                    }
                    else if (Favroites.size()==2)
                    {
                        editor.putString("activity1", Favroites.get(0));
                        editor.putString("activity2", Favroites.get(1));
                        editor.putString("activity3", "");
                    }
                    else if (Favroites.size()==1)
                    {
                        editor.putString("activity1", Favroites.get(0));
                        editor.putString("activity2", "");
                        editor.putString("activity3", "");
                    }
                    else if (Favroites.size()==0)
                    {
                        editor.putString("activity1", "");
                        editor.putString("activity2", "");
                        editor.putString("activity3", "");
                    }



                    editor.commit();
                }



                finish();


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        BackTxt.performClick();

    }

    //Fuction called when user click any of the activity listed on screen
    public void show(View view) {

        //check user alreay selsected three act.
        if (Favroites.size() == 3) {
            //yes //cheak user pressed alredy seleted act or other....
            //if it's it already selected deselect it, remove from Favroites arraylist, count -1 ,set it's boolen false and set next btn disable.
            //if it not seleted do nothing
            if (view == findViewById(R.id.park_txt)) {
                if (ParkBool) {
                    ParkBool = false;
                    ParkTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    ParkTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Park");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.movie_txt)) {
                if (MovieBool) {
                    MovieBool = false;
                    MovieTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    MovieTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Movie");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.swm_txt)) {
                if (SwinningBool) {
                    SwinningBool = false;
                    SwinningTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    SwinningTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Swimming");
                    Favroites.remove(indexid);
                    ;
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                // SwinningTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.game_txt)) {
                if (BoardGamesBool) {
                    BoardGamesBool = false;

                    BoardGamesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BoardGamesTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Board games");
                    Favroites.remove(indexid);
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //BoardGamesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.bank_txt)) {
                if (BankingBool) {
                    BankingBool = false;
                    BankingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BankingTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Banking");
                    Favroites.remove(indexid);
                }


                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                // BankingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.bike_txt)) {
                if (BikeRidesBool) {
                    BikeRidesBool = false;

                    BikeRidesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BikeRidesTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Bike Rides");
                    Favroites.remove(indexid);
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //BikeRidesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.ani_txt)) {
                if (AnimalsBool) {
                    AnimalsBool = false;
                    AnimalsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    AnimalsTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Animals");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //AnimalsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.paint_txt)) {
                if (PintingBool) {
                    PintingBool = false;

                    PintingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    PintingTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Painting");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //PintingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.sport_txt)) {
                if (SportsBool) {
                    SportsBool = false;
                    SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    SportsTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Sports");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //  Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.crawling_txt)) {
                if (CrawlingBool) {
                    CrawlingBool = false;
                    CrawlingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    CrawlingTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Crawling");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //  Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.blocks_txt)) {
                if (BlockBool) {
                    BlockBool = false;
                    BlocksTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BlocksTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Blocks");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //  Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.sandpit_txt)) {
                if (SandpitBool) {
                    SandpitBool = false;
                    SandpitTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    SandpitTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Sandpit");
                    Favroites.remove(indexid);
                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);
                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //  Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            }

        } else {

            if (view == findViewById(R.id.park_txt)) {
                if (ParkBool) {
                    ParkBool = false;

                    ParkTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    ParkTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    int indexid = Favroites.indexOf("Park");
                    Favroites.remove(indexid);
                } else {
                    ParkBool = true;

                    ParkTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    ParkTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Park");

                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }

            } else if (view == findViewById(R.id.movie_txt)) {
                if (MovieBool) {
                    MovieBool = false;
                    MovieTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    MovieTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    int indexid = Favroites.indexOf("Movie");
                    Favroites.remove(indexid);
                } else {
                    MovieBool = true;
                    MovieTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    MovieTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Movie");


                }
                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.swm_txt)) {
                if (SwinningBool) {
                    SwinningBool = false;

                    SwinningTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    SwinningTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Swimming");
                    Favroites.remove(indexid);

                } else {
                    SwinningBool = true;

                    SwinningTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    SwinningTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Swimming");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                // SwinningTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.game_txt)) {
                if (BoardGamesBool) {
                    BoardGamesBool = false;
                    BoardGamesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BoardGamesTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Board games");
                    Favroites.remove(indexid);
                } else {
                    BoardGamesBool = true;
                    BoardGamesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    BoardGamesTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Board games");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }

            } else if (view == findViewById(R.id.bank_txt)) {
                if (BankingBool) {
                    BankingBool = false;
                    BankingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BankingTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Banking");
                    Favroites.remove(indexid);

                } else {
                    BankingBool = true;
                    BankingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    BankingTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Banking");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                // BankingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.bike_txt)) {
                if (BikeRidesBool) {
                    BikeRidesBool = false;

                    BikeRidesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BikeRidesTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    int indexid = Favroites.indexOf("Bike Rides");
                    Favroites.remove(indexid);

                } else {
                    BikeRidesBool = true;

                    BikeRidesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    BikeRidesTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Bike Rides");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //BikeRidesTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.ani_txt)) {
                if (AnimalsBool) {
                    AnimalsBool = false;
                    AnimalsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    AnimalsTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Animals");
                    Favroites.remove(indexid);
                } else {
                    AnimalsBool = true;
                    AnimalsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    AnimalsTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Animals");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //AnimalsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                //Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.paint_txt)) {
                if (PintingBool) {
                    PintingBool = false;
                    PintingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    PintingTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    int indexid = Favroites.indexOf("Painting");
                    Favroites.remove(indexid);

                } else {
                    PintingBool = true;

                    PintingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    PintingTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Painting");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //PintingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.sport_txt)) {
                if (SportsBool) {
                    SportsBool = false;
                    SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    SportsTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    int indexid = Favroites.indexOf("Sports");
                    Favroites.remove(indexid);

                } else {
                    SportsBool = true;
                    SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    SportsTxt.setTypeface(Typeface.DEFAULT_BOLD);


                    Favroites.add("Sports");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.crawling_txt)) {
                if (CrawlingBool) {
                    CrawlingBool = false;

                    CrawlingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    CrawlingTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Crawling");
                    Favroites.remove(indexid);

                } else {
                    CrawlingBool = true;
                    CrawlingTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    CrawlingTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Crawling");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.blocks_txt)) {
                if (BlockBool) {
                    BlockBool = false;
                    BlocksTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    BlocksTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    int indexid = Favroites.indexOf("Blocks");
                    Favroites.remove(indexid);

                } else {
                    BlockBool = true;
                    BlocksTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    BlocksTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Blocks");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }
                //SportsTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                // Toast.makeText(getApplicationContext(), Favroites.toString(), Toast.LENGTH_LONG).show();
            } else if (view == findViewById(R.id.sandpit_txt)) {
                if (SandpitBool) {
                    SandpitBool = false;
                    SandpitTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_white_border));
                    SandpitTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                    int indexid = Favroites.indexOf("Sandpit");
                    Favroites.remove(indexid);

                } else {
                    SandpitBool = true;
                    SandpitTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_black_border));
                    SandpitTxt.setTypeface(Typeface.DEFAULT_BOLD);
                    Favroites.add("Sandpit");
                }

                if (Favroites.size() == 3) {
                    NextTxt.setTextColor(Color.parseColor("#000000"));
                    NextTxt.setEnabled(true);

                } else {
                    NextTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    NextTxt.setEnabled(false);
                }

            }
        }


    }

}
