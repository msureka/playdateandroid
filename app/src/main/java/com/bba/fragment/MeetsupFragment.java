package com.bba.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bba.playdate1.Chating_meetups_Activity;
import com.bba.playdate1.MainActivity;
import com.bba.playdate1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MeetsupFragment extends Fragment {

    //element declaration
    View v;
    ListView listview_meetups;
    ImageView PlusImg, PlusImg1;
    JSONArray Array_MeetupsData=null;
    JSONArray Array_AllMessagesData_Copy=null;
    Integer total_Row=0;
    String Str_title_match="",search_update="yes",str_search_txt="";

    public static SearchView searchbar;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        v = inflater.inflate(R.layout.fragment_meetsup, container, false);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UpdateInformation_list,
                new IntentFilter("updatefriends_list_meetups"));
        searchbar=(SearchView)v.findViewById(R.id.search_meetup);


        Array_MeetupsData=new JSONArray();
        Array_AllMessagesData_Copy=new JSONArray();

        Array_MeetupsData=FriendsFragment.Array_MeetupsData;
        Array_AllMessagesData_Copy=FriendsFragment.Array_MeetupsData;


        if (Array_MeetupsData !=null)
        {
            Str_title_match="My Meetups";
            total_Row=Array_MeetupsData.length()+1;
        }
        else
        {
            total_Row=0;
        }



        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.d("seach_query",query);
                // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                Array_MeetupsData = new JSONArray();
                if (Array_AllMessagesData_Copy != null)

                {
                    if (TextUtils.isEmpty(newText.toString())) {
                        search_update = "yes";
                        str_search_txt = "";
                        Array_MeetupsData = new JSONArray();
                        Array_AllMessagesData_Copy = new JSONArray();
                        Array_MeetupsData = FriendsFragment.Array_MeetupsData;
                        Array_AllMessagesData_Copy = FriendsFragment.Array_MeetupsData;

                    } else
                        {
                        search_update = "no";
                        for (int i = 0; i < Array_AllMessagesData_Copy.length(); i++) {

                            try {
                                String string = Array_AllMessagesData_Copy.getJSONObject(i).getString("eventtitle");
                                String stringcom = Array_AllMessagesData_Copy.getJSONObject(i).getString("message");
                                str_search_txt = newText;
                                if ((string.toLowerCase()).contains(newText.toLowerCase()) || (stringcom.toLowerCase()).contains(newText.toLowerCase())) {

                                    Log.e("constraint_str11", string);
                                    Log.e("constraint_str22", stringcom);
                                    Array_MeetupsData.put(Array_AllMessagesData_Copy.getJSONObject(i));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }


                    }

                    if (Array_MeetupsData !=null)
                    {
                        Str_title_match="My Meetups";
                        total_Row=Array_MeetupsData.length()+1;
                    }

                    listview_meetups.invalidate();
                    ((BaseAdapter) listview_meetups.getAdapter()).notifyDataSetChanged();

                }
                else
                {
                    total_Row=0;
                }
                return false;
            }
        });



        listview_meetups = (ListView) v.findViewById(R.id.list_meetups);
        CustomAdapter2 Customadpter_chat = new CustomAdapter2();
        listview_meetups.setAdapter(Customadpter_chat);


        return v;
    }

    private class CustomAdapter2 extends BaseAdapter {
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);


        @Override
        public int getCount()
        {
            return total_Row;
        }

        @Override
        public Object getItem(int i)
        {
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {









            if (i==0)
            {
                view = inflater.inflate(R.layout.section_one_newmatchs, null, false);
               // view =getActivity().getLayoutInflater().inflate(R.layout.section_one_newmatchs, null);
                final LinearLayout llGallery = (LinearLayout)view.findViewById(R.id.ImagesHsw);

                TextView borderline=(TextView)view.findViewById(R.id.list_topline);
                borderline.setVisibility(View.INVISIBLE);

                TextView title=(TextView)view.findViewById(R.id.title_chating);
                HorizontalScrollView Scroll_view=(HorizontalScrollView)view.findViewById(R.id.horizontal_scr);


                title.setText(Str_title_match);
                title.setTypeface(MainActivity.font_helvitica_bold);


                title.setVisibility(View.VISIBLE);



            }

            else {

                try {
                JSONObject obj_vallues = new JSONObject(String.valueOf(Array_MeetupsData.getString(i-1)));

                int drawable = 0;


                view = inflater.inflate(R.layout.meetfriendlist, null, false);

                //  view= ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.meetfriendlist, null);
                     RelativeLayout relative_tapp = (RelativeLayout) view.findViewById(R.id.relative_meetups);
                ImageView image_profile = (ImageView) view.findViewById(R.id.imageView2_meetup);
                ImageView image_profile_red = (ImageView) view.findViewById(R.id.redimg_meetups);
                TextView txt_meetup_date = (TextView) view.findViewById(R.id.textView_meetupdate);
                TextView txt_meetups_Title = (TextView) view.findViewById(R.id.textView_title_meetup);
                TextView txt_meetups_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);


                    relative_tapp.setTag(i-1);




                if (obj_vallues.getString("gender").equalsIgnoreCase("Boy")) {
                    drawable = R.drawable.defaultboy;
                } else {
                    drawable = R.drawable.defaultgirl;
                }

                Picasso.with(getActivity()).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                        .placeholder(drawable)
                        .into(image_profile);

                txt_meetup_date.setText(obj_vallues.getString("eventdateformat"));


                   String Str_name_txt=obj_vallues.getString("eventtitle");

                    //String str_message_txt=obj_vallues.getString("message");

                    String str_message_txt = "";//obj_vallues.getString("message");
                    if (obj_vallues.getString("chattype").equalsIgnoreCase("TEXT"))
                    {
                        str_message_txt = obj_vallues.getString("message");
                        txt_meetups_subtitle.setText(str_message_txt);


                    }
                    else
                    {
                        str_message_txt="Image";
                        txt_meetups_subtitle.setText("Image");

                    }

                    if (str_search_txt.length() !=0) {
                        if ((Str_name_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                            int startPos = (Str_name_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                            int endPos = startPos + str_search_txt.length();

                            Spannable spanText = Spannable.Factory.getInstance().newSpannable(Str_name_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                            spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            txt_meetups_Title.setText(spanText, TextView.BufferType.SPANNABLE);
                        }
                        else
                        {
                            txt_meetups_Title.setText(Str_name_txt);
                            txt_meetups_Title.setTextColor(getResources().getColor(R.color.black));
                        }

                        if((str_message_txt.toLowerCase()).contains(str_search_txt.toLowerCase()))
                        {

                            int startPos = (str_message_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                            int endPos = startPos + str_search_txt.length();

                            Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_message_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                            spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            txt_meetups_subtitle.setText(spanText,TextView.BufferType.SPANNABLE);
                        }
                        else
                        {
                            txt_meetups_subtitle.setText(str_message_txt);
                            txt_meetups_subtitle.setTextColor(getResources().getColor(R.color.black));
                        }

                    }
                    else
                    {
                        txt_meetups_Title.setText(Str_name_txt);
                        txt_meetups_subtitle.setText(str_message_txt);
                        txt_meetups_Title.setTextColor(getResources().getColor(R.color.black));
                        txt_meetups_subtitle.setTextColor(getResources().getColor(R.color.black));


                    }


                if (obj_vallues.getString("msgread").equalsIgnoreCase("no")) {
                    image_profile_red.setVisibility(View.VISIBLE);
                } else {
                    image_profile_red.setVisibility(View.INVISIBLE);
                }

                    relative_tapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1=new Intent(getActivity(),Chating_meetups_Activity.class);
                            Log.d("meetups tuch tag",v.getTag()+"");
                            try {
                                searchbar.clearFocus();
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow( searchbar.getWindowToken(), 0);

                                JSONObject new_obj=new JSONObject(String.valueOf(Array_MeetupsData.getJSONObject((Integer) v.getTag())));
                                Chating_meetups_Activity.Msg_Record_obj_meetupchat=new_obj;//Array_MessagesFriends.get((Integer) llGallery.getTag());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent1);
                        }
                    });

            } catch (JSONException e) {
                    e.printStackTrace();
                }





            }



            return view;
        }
    }
    public void onDestroy()
    {

        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(UpdateInformation_list);



    }
    private BroadcastReceiver UpdateInformation_list = new BroadcastReceiver()
    {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (search_update.equalsIgnoreCase("yes")) {
                Array_MeetupsData = new JSONArray();

                Array_MeetupsData = FriendsFragment.Array_MeetupsData;

                total_Row = 0;


                if (Array_MeetupsData != null)
                {
                    Str_title_match = "My Meetups";
                    total_Row = Array_MeetupsData.length() + 1;
                }
                else
                {
                    total_Row=0;
                }


                listview_meetups.invalidate();
                ((BaseAdapter) listview_meetups.getAdapter()).notifyDataSetChanged();


            }

        }
    };

}