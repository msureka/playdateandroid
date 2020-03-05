package com.bba.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bba.playdate1.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by spielmohitp on 3/8/2017.
 */
public class EventMeetChatAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public Date comDate;
    String[] MessageArray, SenderFbIdArray, MessageDateArray, ChatTypeArray, ImgUrlArray, ImgHeightArray, ImgwidthArray, ProfilePic, Creatorprofilepic, Creatorfbid, Eventtitle, Eventlocation,
            Eventdate, Createdate, Eventdescription, Gender, Senderfname, Eventdateformat, Attendingstatus, senderprofilepic, sendergender;
    Context context;
    Activity act;
    public EventMeetChatAdapter(Activity act, Context context, String[] message, String[] senderfbid, String[] msgdate, String[] chattype, String[] imgurl, String[] imgh, String[] imgw, String[] profilepic, String[] Creatorprofilepic, String[] Creatorfbid, String[] Eventtitle, String[] Eventlocation, String[] Eventdate, String[] Createdate, String[] Eventdescription, String[] Gender, String[] Senderfname, String[] Eventdateformat, String[] Attendingstatus) {
        // TODO Auto-generated constructor stub

        this.MessageArray = message;
        this.SenderFbIdArray = senderfbid;
        this.MessageDateArray = msgdate;
        this.ChatTypeArray = chattype;
        this.ImgUrlArray = imgurl;
        this.ImgHeightArray = imgh;
        this.ImgwidthArray = imgw;
        this.ProfilePic = profilepic;
        this.Creatorprofilepic = Creatorprofilepic;
        this.Creatorfbid = Creatorfbid;
        this.Eventtitle = Eventtitle;
        this.Eventlocation = Eventlocation;
        this.Eventdate = Eventdate;
        this.Createdate = Createdate;
        this.Eventdescription = Eventdescription;
        this.Gender = Gender;
        this.Senderfname = Senderfname;
        this.Eventdateformat = Eventdateformat;
        this.Attendingstatus = Attendingstatus;

        String dtStart = MessageDateArray[0];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            comDate = format.parse(dtStart);
        } catch (Exception e) {

        }

        this.context = context;
        this.act = act;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public EventMeetChatAdapter(Activity act, Context context, String[] message, String[] senderfbid, String[] msgdate, String[] chattype, String[] imgurl, String[] imgh, String[] imgw, String[] profilepic, String[] senderprofilepic, String[] sendergender, String[] senderfname) {

        this.MessageArray = message;
        this.SenderFbIdArray = senderfbid;
        this.MessageDateArray = msgdate;
        this.ChatTypeArray = chattype;
        this.ImgUrlArray = imgurl;
        this.ImgHeightArray = imgh;
        this.ImgwidthArray = imgw;
        this.ProfilePic = profilepic;
        this.senderprofilepic = senderprofilepic;
        this.sendergender = sendergender;
        this.Senderfname = senderfname;
        this.context = context;
        this.act = act;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MessageArray.length;
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
        View rowView, rowView1;


        rowView = inflater.inflate(R.layout.eventitem, null);
        //  rowView1 = inflater.inflate(R.layout.eventitem, null);


        holder.PImageView1 = (ImageView) rowView.findViewById(R.id.meetprofileImgview);

        holder.titletxt = (TextView) rowView.findViewById(R.id.titletxt);
        holder.loctxt = (TextView) rowView.findViewById(R.id.loctxt);
        holder.timedatetxt = (TextView) rowView.findViewById(R.id.timedatetxt);
        holder.intxt = (TextView) rowView.findViewById(R.id.intxt);
        holder.canttxt = (TextView) rowView.findViewById(R.id.canttxt);
        holder.calendertxt = (TextView) rowView.findViewById(R.id.calendertxt);
        //   holder.loctxt = (TextView) rowView.findViewById(R.id.loctxt);
        Log.d("ChatType1", "ChatType1+");
        // holder.MsgImg = (ImageView) rowView.findViewById(R.id.image);


        holder.titletxt.setText("kjhjhhj");
        holder.loctxt.setText("kjhjhhj");
        holder.timedatetxt.setText("kjhjhhj");
        holder.timedatetxt.setText("kjhjhhj");


        //  String last =  games.get(games.size() -1);

        String dtStart = MessageDateArray[position];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //      try {
//                Date date = format.parse(dtStart);
//                DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
//                String output = outputFormatter.format(date); // Output : 01/20/2012
//                holder.DateTxt.setText(" " + output);
//                if (position == 0) {
//                    holder.DateTxt.setVisibility(View.VISIBLE);
//                } else {
//                    if (comDate.before(date)) {
//                        holder.DateTxt.setVisibility(View.VISIBLE);
//                        comDate = date;
//                    } else {
//                        holder.DateTxt.setVisibility(View.INVISIBLE);
//                    }
//
//                }
//            } catch (Exception e) {

        //   }


        holder.PImageView1.setVisibility(View.VISIBLE);


        Picasso.with(act)
                .load(ProfilePic[position])
                .placeholder(R.drawable.defaultboy)
                //.memoryPolicy(MemoryPolicy.NO_CACHE)// optional
                //.error(R.drawable.error)       // optional
                .resize(250, 200)               // optional
                .into(holder.PImageView1, new Callback() {
                    @Override
                    public void onSuccess() {
                        // pb.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError() {
                        // pb.setVisibility(View.INVISIBLE);
                    }
                });


        // Log.e("time ",timeString[position]);
        rowView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            }
        });

        return rowView;
    }

    public class Holder {
        ImageView PImageView1;
        TextView titletxt, loctxt, timedatetxt, intxt, canttxt, calendertxt;
    }
}