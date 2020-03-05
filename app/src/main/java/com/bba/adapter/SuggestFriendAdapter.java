package com.bba.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bba.playdate1.R;
import com.bba.playdate1.SuggestFreindActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spielmohitp on 3/8/2017.
 */
public class SuggestFriendAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    String[] ProfileTypeString, MatchFbIdString, FnameString, GenderString, ProfilePicString, TypeString, MessageString, MatchDateString, MsgReadString, MsgDateString, UserdIdString, EmailIdString, EngLangString, ArbicLangString, FranchLangString, CityString, CountryString, DescriptionString, LikeToPlayString, ICanMeetString, Activity1String, Activity2String, Activity3String, Emoji1String, Emoji2String, Emoji3String, SuperHeroString, AgeString, ChatTypeString, ImgUrlString, TitleString, AdDescriptionString, SubTitleString, ColorRedString, ColorGreenString, ColorBlueString, ImageTitleString, ImgLogo1String, ImgLogo2String, ImgLogo3String;
    Context context;
    Activity act;
    ArrayList<String> Array_request = new ArrayList<String>();
    public SuggestFriendAdapter(Activity act, Context context, String[] profiletype, String[] matchfbid, String[] fname, String[] gender, String[] profilepic, String[] type, String[] message, String[] matchdate, String[] msgread, String[] msgdate, String[] userid, String[] emailid, String[] englang, String[] arbiclang, String[] franchlang, String[] city, String[] country, String[] desc, String[] liketoplay, String[] icanmeet, String[] act1, String[] act2, String[] act3, String[] emoji1, String[] emoji2, String[] emoji3, String[] superhero, String[] age, String[] chattype, String[] imgurl, String[] title, String[] addesc, String[] subtitle, String[] colorred, String[] colorgreen, String[] colorblue, String[] imagetitle, String[] imglogo1, String[] imglogo2, String[] imglogo3) {
        // TODO Auto-generated constructor stub

        this.ProfileTypeString = profiletype;
        this.MatchFbIdString = matchfbid;
        this.FnameString = fname;
        this.GenderString = gender;
        this.ProfilePicString = profilepic;
        this.TypeString = type;
        this.MessageString = message;
        this.MatchDateString = matchdate;
        this.MsgReadString = msgread;
        this.MsgDateString = msgdate;
        this.UserdIdString = userid;
        this.EmailIdString = emailid;
        this.EngLangString = englang;
        this.ArbicLangString = arbiclang;
        this.FranchLangString = franchlang;
        this.CityString = city;
        this.CountryString = country;
        this.DescriptionString = desc;
        this.LikeToPlayString = liketoplay;
        this.ICanMeetString = icanmeet;
        this.Activity1String = act1;
        this.Activity2String = act2;
        this.Activity3String = act3;
        this.Emoji1String = emoji1;
        this.Emoji2String = emoji2;
        this.Emoji3String = emoji3;
        this.SuperHeroString = superhero;
        this.AgeString = age;
        this.ChatTypeString = chattype;
        this.ImgUrlString = imgurl;
        this.TitleString = title;
        this.AdDescriptionString = addesc;
        this.SubTitleString = subtitle;
        this.ColorRedString = colorred;
        this.ColorGreenString = colorgreen;
        this.ColorBlueString = colorblue;
        this.ImageTitleString = imagetitle;
        this.ImgLogo1String = imglogo1;
        this.ImgLogo2String = imglogo2;
        this.ImgLogo3String = imglogo3;

        this.context = context;
        this.act = act;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public SuggestFriendAdapter(SuggestFreindActivity suggestFreindActivity, Context applicationContext, ArrayList<String> array_request) {
this.Array_request=array_request;
        this.context = context;
        this.act = act;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Array_request.size();
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
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.suggestfriendlist, null);

        holder.ImageView = (ImageView) rowView.findViewById(R.id.profileImgview);
        holder.NameTxt = (TextView) rowView.findViewById(R.id.nametxt);
        holder.sugfrndtick = (ImageView) rowView.findViewById(R.id.sugfrndtick);
        holder.rl = (RelativeLayout) rowView.findViewById(R.id.rl);


Log.d("jjjjtest","jjjjkkkk");
        try {
            JSONObject obj = new JSONObject(Array_request.get(position));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sugfrndtick.setVisibility(View.VISIBLE);
            }
        });


        try {
            Picasso.with(act)
                    .load(ProfilePicString[position])
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


        holder.NameTxt.setText(FnameString[position]);


        // Log.e("time ",timeString[position]);
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Bundle eventname_bundle = new Bundle();
//
//                eventname_bundle.putString("profiletype", ProfileTypeString[position]);
//                eventname_bundle.putString("matchedfbid", MatchFbIdString[position]);
//                eventname_bundle.putString("fname", FnameString[position]);
//                eventname_bundle.putString("gender", GenderString[position]);
//                eventname_bundle.putString("profilepic", ProfilePicString[position]);
//                eventname_bundle.putString("type", TypeString[position]);
//                eventname_bundle.putString("message", MessageString[position]);
//                eventname_bundle.putString("matchdate", MatchDateString[position]);
//                eventname_bundle.putString("msgread", MsgReadString[position]);
//                eventname_bundle.putString("msgdate", MsgDateString[position]);
//                eventname_bundle.putString("userid", UserdIdString[position]);
//                eventname_bundle.putString("emailid", EmailIdString[position]);
//                eventname_bundle.putString("englang", EngLangString[position]);
//                eventname_bundle.putString("arabiclang", ArbicLangString[position]);
//                eventname_bundle.putString("frenchlang", FranchLangString[position]);
//                eventname_bundle.putString("city", CityString[position]);
//                eventname_bundle.putString("country", CountryString[position]);
//                eventname_bundle.putString("description", DescriptionString[position]);
//                eventname_bundle.putString("liketoplay", LikeToPlayString[position]);
//                eventname_bundle.putString("icanmeet", ICanMeetString[position]);
//                eventname_bundle.putString("activity1", Activity1String[position]);
//                eventname_bundle.putString("activity2", Activity2String[position]);
//                eventname_bundle.putString("activity3", Activity3String[position]);
//                eventname_bundle.putString("emoji1", Emoji1String[position]);
//                eventname_bundle.putString("emoji2", Emoji2String[position]);
//                eventname_bundle.putString("emoji3", Emoji3String[position]);
//                eventname_bundle.putString("superhero", SuperHeroString[position]);
//                eventname_bundle.putString("age", AgeString[position]);
//                eventname_bundle.putString("chattype", ChatTypeString[position]);
//                eventname_bundle.putString("imageurl", ImgUrlString[position]);
//                eventname_bundle.putString("title", TitleString[position]);
//                eventname_bundle.putString("addescription", AdDescriptionString[position]);
//                eventname_bundle.putString("subtitle", SubTitleString[position]);
//                eventname_bundle.putString("colourred", ColorRedString[position]);
//                eventname_bundle.putString("colourgreen", ColorGreenString[position]);
//                eventname_bundle.putString("colourblue", ColorBlueString[position]);
//                eventname_bundle.putString("imagetitle", ImageTitleString[position]);
//                eventname_bundle.putString("imagelogo1", ImgLogo1String[position]);
//                eventname_bundle.putString("imagelogo2", ImgLogo2String[position]);
//                eventname_bundle.putString("imagelogo3", ImgLogo3String[position]);
//
//                // Log.e("Buldle"," "+eventname_bundle.toString());
//
//                Intent in = new Intent(context, ChatActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                in.putExtras(eventname_bundle);
//                context.startActivity(in);
//                act.overridePendingTransition(R.anim.swipe_right_in, R.anim.swipe_left_out);
//
//            }
//        });

        return rowView;
    }

    public class Holder {
        ImageView ImageView, sugfrndtick;
        TextView NameTxt;
        RelativeLayout rl;
    }
}

