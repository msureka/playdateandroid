package com.bba.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bba.playdate1.R;

import java.util.Date;

/**
 * Created by spielmohitp on 3/8/2017.
 */
public class FbInviteAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public Date comDate;
    String[] NameArray, PhoneArray, EmailArray;
    Context context;
    Activity act;

    public FbInviteAdapter(Activity act, Context context, String[] nameArray) {
        // TODO Auto-generated constructor stub

        this.NameArray = nameArray;

        this.context = context;
        this.act = act;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return NameArray.length;
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
        rowView = inflater.inflate(R.layout.fbinvite_item, null);


        holder.NameTxt = (TextView) rowView.findViewById(R.id.nametxt);

        holder.InviteBtn = (Button) rowView.findViewById(R.id.invitebtn);

        holder.NameTxt.setText(NameArray[position]);


        // Log.e("time ",timeString[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(context," Name "+PhoneArray[position],Toast.LENGTH_LONG).show();
            }
        });

        holder.InviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to Mail
                try {

                    if (EmailArray[position] != null && !EmailArray[position].trim().isEmpty()) {

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{EmailArray[position]});
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download Play:Date");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, \n\nPlay:Date is a grate app to find friends for your childern.i have been using it scince while, and it whould be great if you could download it! \n\nVisit http://www.play-date.ae to download it onn your mobile phone! \n\nThanks!");
                        shareIntent.setType("message/rfc822");
                        Intent new_intent = Intent.createChooser(shareIntent, "Share via");
                        new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(new_intent);


                    } else {

                        String x = "Play:Date is grate app to find friend for your children. I have been Using it scince a while and it would be great if you could download it ! \n \nVisit http://www.play-date.ae to download it on your mobile phone!";
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sendIntent.setData(Uri.parse("sms:" + PhoneArray[position]));
                        sendIntent.putExtra("sms_body", x);
                        act.startActivity(sendIntent);

                    }


                } catch (Exception e) {
                    Log.e("Error In Mail", e.toString());
                    String x = "Play:Date is grate app to find friend for your children. I have been Using it scince a while and it would be great if you could download it ! \n \nVisit http://www.play-date.ae to download it on your mobile phone!";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sendIntent.setData(Uri.parse("sms:" + PhoneArray[position]));
                    sendIntent.putExtra("sms_body", x);
                    act.startActivity(sendIntent);
                }


            }
        });


        return rowView;
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            /*Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();*/
        } catch (Exception ex) {
            /*Toast.makeText(context, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();*/
            ex.printStackTrace();
        }
    }

    public class Holder {
        TextView NameTxt, PhoneTxt, EmailTxt;
        Button InviteBtn;
    }
}

