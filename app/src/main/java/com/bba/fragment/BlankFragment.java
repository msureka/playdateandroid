package com.bba.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
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

import com.bba.playdate1.Chating_Activity;
import com.bba.playdate1.Chating_meetups_Activity;
import com.bba.playdate1.FrirndReqActivity;
import com.bba.playdate1.MainActivity;
import com.bba.playdate1.R;
import com.bba.playdate1.ViewMatchActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

/**
 * Created by spielmohitp on 2/24/2017.
 */
public class BlankFragment extends Fragment {

    private static final Location TODO = null;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    public static String matchswipe1;
    //element declaration
    View v;
    ListView listview_chat;
    ImageView PlusImg, PlusImg1;
    JSONArray Array_AllFriends;
    JSONArray Array_MatchFriends = null;
    JSONArray Array_RequestFriends = null;
    JSONArray Array_MessagesFriends = null;
    JSONArray Array_AllMessagesData = null;
    JSONArray Array_AllMessagesData_Copy = null;



    Integer total_Row = 0, matchrow = 0, reqrow = 0;
    Integer Update_scroll = -1;
    Integer Update_scroll1 = -1;
    View view_update, view_update1;
    public static SearchView searchbar;
    String Str_title_req = "", Str_title_match = "", search_update = "yes", str_search_txt = "";
    private BroadcastReceiver UpdateInformation_list = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {


            if (search_update.equalsIgnoreCase("yes")) {
                Array_AllMessagesData = new JSONArray();
                Array_MatchFriends = new JSONArray();
                Array_RequestFriends = new JSONArray();
                Array_MessagesFriends = new JSONArray();


                Array_AllMessagesData = FriendsFragment.Array_AllFriends;
                Array_MatchFriends = FriendsFragment.Array_MatchFriends;
                Array_RequestFriends = FriendsFragment.Array_RequestFriends;
                Array_MessagesFriends = FriendsFragment.Array_MessagesFriends;


                Array_AllMessagesData_Copy = new JSONArray();

                total_Row = 0;
                matchrow = 0;
                reqrow = 0;


                if (Array_MatchFriends != null) {
                    Str_title_match = "New Matches (" + Array_MatchFriends.length() + ")";
                    total_Row = total_Row + 1;
                    matchrow = Array_MatchFriends.length();

                }


                if (Array_RequestFriends != null) {
                    Str_title_req = "Friend Requests (" + Array_RequestFriends.length() + ")";

                    total_Row = total_Row + 1;
                    reqrow = Array_RequestFriends.length();
                }
                if (Array_MessagesFriends != null) {

                    total_Row = total_Row + Array_MessagesFriends.length() + 1;
                }




//
//               getActivity().runOnUiThread (new Thread(new Runnable() {
//                    public void run()
//                    {
                        listview_chat.invalidate();
                        ((BaseAdapter) listview_chat.getAdapter()).notifyDataSetChanged();

//                    }
//                }));


            }

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        v = inflater.inflate(R.layout.fragment_blank, container, false);

        searchbar = (SearchView) v.findViewById(R.id.search_chat);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UpdateInformation_list,
                new IntentFilter("updatefriends_list"));

        Array_AllMessagesData = new JSONArray();
        Array_MatchFriends = new JSONArray();
        Array_RequestFriends = new JSONArray();
        Array_MessagesFriends = new JSONArray();

                   Array_AllMessagesData = FriendsFragment.Array_AllFriends;
        Array_MatchFriends = FriendsFragment.Array_MatchFriends;
        Array_RequestFriends = FriendsFragment.Array_RequestFriends;
        Array_MessagesFriends = FriendsFragment.Array_MessagesFriends;


        if (Array_MatchFriends != null) {
            Str_title_match = "New Matches (" + Array_MatchFriends.length() + ")";
            total_Row = total_Row + 1;
            matchrow = Array_MatchFriends.length();

        }


        if (Array_RequestFriends != null) {
            Str_title_req = "Friend Requests (" + Array_RequestFriends.length() + ")";

            total_Row = total_Row + 1;
            reqrow = Array_RequestFriends.length();
        }
        if (Array_MessagesFriends != null) {


            total_Row = total_Row + Array_MessagesFriends.length() + 1;
        }





        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.d("seach_query", query);
                // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                Array_AllMessagesData_Copy = new JSONArray();
                if (Array_AllMessagesData != null)

                {
                    if (TextUtils.isEmpty(newText.toString())) {
                        search_update = "yes";
                        str_search_txt = "";
                        Array_AllMessagesData = new JSONArray();
                        Array_MatchFriends = new JSONArray();
                        Array_RequestFriends = new JSONArray();
                        Array_MessagesFriends = new JSONArray();

                        Array_AllMessagesData = FriendsFragment.Array_AllFriends;
                        Array_MatchFriends = FriendsFragment.Array_MatchFriends;
                        Array_RequestFriends = FriendsFragment.Array_RequestFriends;
                        Array_MessagesFriends = FriendsFragment.Array_MessagesFriends;
                    } else {
                        search_update = "no";
                        for (int i = 0; i < Array_AllMessagesData.length(); i++) {

                            try {
                                String string = Array_AllMessagesData.getJSONObject(i).getString("fname");
                                String stringcom = Array_AllMessagesData.getJSONObject(i).getString("message");
                                str_search_txt = newText;
                                if ((string.toLowerCase()).contains(newText.toLowerCase()) || (stringcom.toLowerCase()).contains(newText.toLowerCase())) {

                                    Log.e("constraint_str11", string);
                                    Log.e("constraint_str22", stringcom);
                                    Array_AllMessagesData_Copy.put(Array_AllMessagesData.getJSONObject(i));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.e("length_arr", String.valueOf(Array_AllMessagesData_Copy.length()));


                        }


                        Array_MatchFriends = new JSONArray();
                        Array_RequestFriends = new JSONArray();
                        Array_MessagesFriends = new JSONArray();

                        for (int i = 0; i < Array_AllMessagesData_Copy.length(); i++) {
                            JSONObject Jsonobject = null;
                            try {
                                Jsonobject = Array_AllMessagesData_Copy.getJSONObject(i);

                                if (Jsonobject.getString("type").equalsIgnoreCase("match")) {
                                    Array_MatchFriends.put(Jsonobject);

                                } else if (Jsonobject.getString("type").equalsIgnoreCase("request")) {
                                    Array_RequestFriends.put(Jsonobject);
                                } else {


                                    if (Array_MessagesFriends.length() == 0) {
                                        Array_MessagesFriends.put(Jsonobject);
                                    } else {

                                        for (int J = Array_MessagesFriends.length() - 1; J < Array_MessagesFriends.length(); J++) {
                                            JSONObject Jsonobject1 = Array_MessagesFriends.getJSONObject(J);
                                            String fbMatch = Jsonobject.getString("matchedfbid");
                                            String fbMatch2 = Jsonobject1.getString("matchedfbid");

                                            if (!fbMatch2.equalsIgnoreCase(fbMatch)) {

                                                Array_MessagesFriends.put(Jsonobject);
                                                break;
                                            }

                                        }
                                    }


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }

                    total_Row = 0;
                    matchrow = 0;
                    reqrow = 0;
                    Update_scroll = -1;
                    Update_scroll1 = -1;

                    if (Array_MatchFriends.length() != 0) {
                        Str_title_match = "New Matches (" + Array_MatchFriends.length() + ")";
                        total_Row = total_Row + 1;
                        matchrow = Array_MatchFriends.length();


                    } else {
                        total_Row = total_Row + 1;
                    }


                    if (Array_RequestFriends.length() != 0) {
                        Str_title_req = "Friend Requests (" + Array_RequestFriends.length() + ")";

                        total_Row = total_Row + 1;
                        reqrow = Array_RequestFriends.length();
                    } else {
                        total_Row = total_Row + 1;
                    }

                    if (Array_MessagesFriends.length() != 0) {


                        total_Row = total_Row + Array_MessagesFriends.length() + 1;
                    }
//
//                    listview_chat.invalidate();
//                    ((BaseAdapter) listview_chat.getAdapter()).notifyDataSetChanged();

                 //   listview_chat = (ListView) v.findViewById(R.id.list_new);
                    CustomAdapter2 Customadpter_chat = new CustomAdapter2();
                    listview_chat.setAdapter(Customadpter_chat);

                } else {
                    total_Row = 0;
                }
                return false;
            }
        });


        listview_chat = (ListView) v.findViewById(R.id.list_new);
        CustomAdapter2 Customadpter_chat = new CustomAdapter2();
        listview_chat.setAdapter(Customadpter_chat);


        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void onDestroy() {

        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(UpdateInformation_list);


    }

    private class CustomAdapter2 extends BaseAdapter {
        private LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return total_Row;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup)

        {
            //  view = getActivity().getLayoutInflater().inflate(R.layout.section_one_newmatchs, null);


            view = inflater.inflate(R.layout.section_one_newmatchs, null, false);
            if (i == 0) {
                if (Update_scroll != matchrow) {
                    if (Update_scroll == -1) {
                        Update_scroll = 0;
                    }
                    view_update = view;
                    Update_scroll = matchrow;
                    final LinearLayout llGallery = (LinearLayout) view.findViewById(R.id.ImagesHsw);

                    TextView borderline = (TextView) view.findViewById(R.id.list_topline);
                    borderline.setVisibility(View.INVISIBLE);

                    TextView title = (TextView) view.findViewById(R.id.title_chating);
                    HorizontalScrollView Scroll_view = (HorizontalScrollView) view.findViewById(R.id.horizontal_scr);


                    title.setText(Str_title_match);
                    title.setTypeface(MainActivity.font_helvitica_bold);


                    title.setVisibility(View.VISIBLE);


                    if (matchrow == 0) {

                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
                        params.height = 0;
                        params.topMargin = 0;

                        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) Scroll_view.getLayoutParams();
                        params1.height = 0;
                        params1.topMargin = 0;
                        Scroll_view.setLayoutParams(params1);


                    }

                    for (int x = 0; x < matchrow; x++) {

                        final View vi = inflater.inflate(R.layout.images_list, null, false);
                        //   final View vi = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.images_list, null);
                        ImageView image_profile = (ImageView) vi.findViewById(R.id.imageView_img);
                        TextView name_txt = (TextView) vi.findViewById(R.id.editText2_txt);
                        name_txt.setTypeface(MainActivity.font_helvitica_light);

                        vi.setTag(x);


                        try {
                            JSONObject obj_vallues = new JSONObject(Array_MatchFriends.getString(x));
                            String str_message_txt = obj_vallues.getString("fname");
                            if ((str_message_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                                int startPos = (str_message_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                                int endPos = startPos + str_search_txt.length();

                                Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_message_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                                spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                name_txt.setText(spanText, TextView.BufferType.SPANNABLE);
                            } else {
                                name_txt.setText(str_message_txt);
                                name_txt.setTextColor(getResources().getColor(R.color.black));
                            }

                            int int_defaultimg=0;
                            if (obj_vallues.getString("gender").equalsIgnoreCase("Boy"))
                            {
                                int_defaultimg = R.drawable.defaultboy;

                            } else {
                                int_defaultimg = R.drawable.defaultgirl;

                            }

                            Picasso.with(getActivity()).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                                    .placeholder(R.drawable.defaultgirl)
                                    .into(image_profile);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        vi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = new Intent(getActivity(), Chating_Activity.class);
                                try {

                                    searchbar.clearFocus();
                                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow( searchbar.getWindowToken(), 0);
                                    JSONObject new_obj = new JSONObject(String.valueOf(Array_MatchFriends.getJSONObject((Integer) vi.getTag())));
                                    Chating_Activity.Msg_Record_obj = new_obj;
                                    Chating_Activity.chatflag="message";
                                    //Array_MessagesFriends.get((Integer) llGallery.getTag());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent1);
                            }
                        });

                        llGallery.addView(vi);
                    }
                }

                if (Update_scroll != matchrow) {

                } else {
                    view = view_update;
                }

            } else if (i == 1) {
                if (Update_scroll1 != reqrow) {
                    if (Update_scroll1 == -1) {
                        Update_scroll1 = 0;
                    }
                    view_update1 = view;
                    Update_scroll1 = reqrow;
                    LinearLayout llGallery = (LinearLayout) view.findViewById(R.id.ImagesHsw);

                    TextView borderline = (TextView) view.findViewById(R.id.list_topline);
                    borderline.setVisibility(View.VISIBLE);

                    TextView title = (TextView) view.findViewById(R.id.title_chating);
                    HorizontalScrollView Scroll_view = (HorizontalScrollView) view.findViewById(R.id.horizontal_scr);
                    title.setText(Str_title_req);
                    title.setTypeface(MainActivity.font_helvitica_bold);
                    title.setVisibility(View.VISIBLE);
                    if (reqrow == 0) {

                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
                        params.height = 0;
                        params.topMargin = 0;

                        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) Scroll_view.getLayoutParams();
                        params1.height = 0;
                        params1.topMargin = 0;
                        Scroll_view.setLayoutParams(params1);
                        title.setLayoutParams(params);
                        borderline.setVisibility(View.INVISIBLE);

                    }


                    for (int x = 0; x < reqrow; x++) {
                        final View vi = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.images_list, null);

                        ImageView image_profile = (ImageView) vi.findViewById(R.id.imageView_img);
                        TextView name_txt = (TextView) vi.findViewById(R.id.editText2_txt);
                        name_txt.setTypeface(MainActivity.font_helvitica_light);
                        vi.setTag(x);


                        try {
                           JSONObject obj_vallues = new JSONObject(Array_RequestFriends.getString(x));
                           // JSONObject obj_vallues = new JSONObject(Array_RequestFriends.getString(0));

                            String str_message_txt = obj_vallues.getString("fname");
                            if ((str_message_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                                int startPos = (str_message_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                                int endPos = startPos + str_search_txt.length();

                                Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_message_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                                spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                name_txt.setText(spanText, TextView.BufferType.SPANNABLE);
                            } else {
                                name_txt.setText(str_message_txt);
                                name_txt.setTextColor(getResources().getColor(R.color.black));
                            }

                            int int_defaultimg=0;

                            if (obj_vallues.getString("gender").equalsIgnoreCase("Boy"))
                            {
                                int_defaultimg = R.drawable.defaultboy;

                            } else {
                                int_defaultimg = R.drawable.defaultgirl;

                            }


                            Picasso.with(getActivity()).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                                    .placeholder(int_defaultimg)
                                    .into(image_profile);

                            vi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent1 = new Intent(getActivity(), FrirndReqActivity.class);
                                    try {

                                        searchbar.clearFocus();
                                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow( searchbar.getWindowToken(), 0);
                                        JSONObject new_obj = new JSONObject(String.valueOf(Array_RequestFriends.getJSONObject((Integer) vi.getTag())));
                                        FrirndReqActivity.req_json_obj = new_obj;//Array_MessagesFriends.get((Integer) llGallery.getTag());


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent1);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        llGallery.addView(vi);
                    }
                }

                if (Update_scroll1 != reqrow) {

                } else {
                    view = view_update1;
                }

                // view=null;
            } else if (i == 2) {
                LinearLayout llGallery = (LinearLayout) view.findViewById(R.id.ImagesHsw);
                TextView borderline = (TextView) view.findViewById(R.id.list_topline);
                borderline.setVisibility(View.VISIBLE);


                TextView title = (TextView) view.findViewById(R.id.title_chating);
                title.setText("Messages ");
                title.setVisibility(View.VISIBLE);

                if (reqrow == 0 && matchrow == 0) {
                    borderline.setVisibility(View.INVISIBLE);
                }


            } else {


                final LinearLayout llGallery = (LinearLayout) view.findViewById(R.id.ImagesHsw1);
                if (i == 3) {
                    TextView borderline = (TextView) view.findViewById(R.id.list_topline);
                    borderline.setVisibility(View.INVISIBLE);
                } else {
                    TextView borderline = (TextView) view.findViewById(R.id.list_topline);
                    borderline.setVisibility(View.VISIBLE);

                }
                TextView title = (TextView) view.findViewById(R.id.title_chating);
                title.setTypeface(MainActivity.font_helvitica_bold);
                title.setVisibility(View.INVISIBLE);

                View vi = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.section_listmessagesview, null);
                ImageView image_profile = (ImageView) vi.findViewById(R.id.imageView2);
                ImageView image_profile_red = (ImageView) vi.findViewById(R.id.redimageView2);
                TextView name_txt = (TextView) vi.findViewById(R.id.textView9);
                TextView message_txt = (TextView) vi.findViewById(R.id.textView10);

                llGallery.setTag(i - 3);


                try {
                    JSONObject obj_vallues = new JSONObject(Array_MessagesFriends.getString(i-3));
                    //JSONObject obj_vallues = new JSONObject(Array_MessagesFriends.getString(0));
                    String Str_name_txt = obj_vallues.getString("fname");

                    String str_message_txt = "";//obj_vallues.getString("message");
if (obj_vallues.getString("chattype").equalsIgnoreCase("TEXT"))
{
    str_message_txt = obj_vallues.getString("message");
    message_txt.setText(str_message_txt);


}
else
{
    str_message_txt="Image";
    message_txt.setText("Image");

}
                    name_txt.setTypeface(MainActivity.font_helvitica_bold);
                    if (str_search_txt.length() != 0) {
                        if ((Str_name_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                            int startPos = (Str_name_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                            int endPos = startPos + str_search_txt.length();

                            Spannable spanText = Spannable.Factory.getInstance().newSpannable(Str_name_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                            spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            name_txt.setText(spanText, TextView.BufferType.SPANNABLE);
                        } else {
                            name_txt.setText(Str_name_txt);
                            name_txt.setTextColor(getResources().getColor(R.color.black));
                        }

                        if ((str_message_txt.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                            int startPos = (str_message_txt.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                            int endPos = startPos + str_search_txt.length();

                            Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_message_txt); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                            spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            message_txt.setText(spanText, TextView.BufferType.SPANNABLE);
                        } else {
                            message_txt.setText(str_message_txt);
                            message_txt.setTextColor(getResources().getColor(R.color.black));
                        }

                    } else {
                        message_txt.setText(str_message_txt);
                        name_txt.setText(Str_name_txt);

                    }


                    if (obj_vallues.getString("profiletype").equalsIgnoreCase("PROFILE")) {

                        Picasso.with(getActivity()).load(obj_vallues.getString("profilepic")).fit().centerCrop()
                                .placeholder(R.drawable.defaultgirl)
                                .into(image_profile);
                    }
                    else

                    {
                        Picasso.with(getActivity()).load(obj_vallues.getString("imagetitle")).fit().centerCrop()
                                .placeholder(R.drawable.defaultgirl)
                                .into(image_profile);

                    }


                    if (obj_vallues.getString("msgread").equalsIgnoreCase("no")) {
                        image_profile_red.setVisibility(View.VISIBLE);
                    } else {
                        image_profile_red.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                llGallery.addView(vi);

                llGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getActivity(), Chating_Activity.class);
                        try {

                            searchbar.clearFocus();
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow( searchbar.getWindowToken(), 0);
                            JSONObject new_obj = new JSONObject(String.valueOf(Array_MessagesFriends.getJSONObject((Integer) llGallery.getTag())));
                            Chating_Activity.Msg_Record_obj = new_obj;//Array_MessagesFriends.get((Integer) llGallery.getTag());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent1);
                    }
                });
            }


            return view;


        }
    }


}


