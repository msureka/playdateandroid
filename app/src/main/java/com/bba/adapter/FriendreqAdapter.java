package com.bba.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bba.playdate1.FrirndReqActivity;
import com.bba.playdate1.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spielmohitp on 3/8/2017.
 */
public class FriendreqAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    String ProfileTypeString, MatchFbIdString, FnameString, GenderString, ProfilePicString, TypeString, MessageString, MatchDateString, MsgReadString, MsgDateString, UserdIdString, EmailIdString, EngLangString, ArbicLangString, FranchLangString, CityString, CountryString, DescriptionString, LikeToPlayString, ICanMeetString, Activity1String, Activity2String, Activity3String, Emoji1String, Emoji2String, Emoji3String, SuperHeroString, AgeString, ChatTypeString, ImgUrlString, TitleString, AdDescriptionString, SubTitleString, ColorRedString, ColorGreenString, ColorBlueString, ImageTitleString, ImgLogo1String, ImgLogo2String, ImgLogo3String;
    ArrayList<String> Array_req = new ArrayList<String>();
    Context context;
    Activity act;
    String str_url = "";

    public FriendreqAdapter(Activity act, Context context, ArrayList<String> array_request) {
        // TODO Auto-generated constructor stub

        this.Array_req = array_request;

        this.context = context;
        this.act = act;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Array_req.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // Toast.makeText(context, "In Adapter", Toast.LENGTH_SHORT).show();
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.requestlist, null);

        holder.ImageView = (ImageView) rowView.findViewById(R.id.profileImgview);
        holder.NameTxt = (TextView) rowView.findViewById(R.id.nametxt);

        try {
            JSONObject obj = new JSONObject(Array_req.get(position));
            FnameString = obj.getString("fname");


            str_url = (obj.getString("profilepic"));

            MatchFbIdString = obj.getString("matchedfbid");
            CityString = obj.getString("city");
            CountryString = obj.getString("country");

            AgeString = obj.getString("age");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.NameTxt.setText(FnameString);
        try {
            Picasso.with(act)
                    .load(str_url)
                    .placeholder(R.drawable.defaultboy)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)// optional
                    //.error(R.drawable.error)       // optional
                    .resize(250, 200)               // optional
                    .into(holder.ImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            // pb.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onError() {
                            // pb.setVisibility(View.INVISIBLE);
                        }
                    });
        } catch (Exception e) {
            Log.e("taglistadapter", "picaso error" + e.toString());
        }


        // / holder.NameTxt.setText(FnameString[position]);


        //    Log.e("time ",timeString[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle eventname_bundle = new Bundle();


                eventname_bundle.putString("matchedfbid", MatchFbIdString);
                eventname_bundle.putString("fname", FnameString);
                eventname_bundle.putString("profilepic", str_url);

                eventname_bundle.putString("city", CityString);
                eventname_bundle.putString("country", CountryString);
                eventname_bundle.putString("age", AgeString);
                Log.e("Buldle444", " " + eventname_bundle.toString());

                Intent in = new Intent(context, FrirndReqActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtras(eventname_bundle);
                context.startActivity(in);
                act.overridePendingTransition(R.anim.swipe_right_in, R.anim.swipe_left_out);


            }
        });

        return rowView;
    }

    public class Holder {
        ImageView ImageView;
        TextView NameTxt;
    }
}

