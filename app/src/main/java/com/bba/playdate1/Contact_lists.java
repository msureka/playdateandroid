package com.bba.playdate1;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bba.fragment.FriendsFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Contact_lists extends AppCompatActivity {

    private static ArrayList<Contact_Model> arrayList;
    ArrayList<String> Arraylist_Names=new ArrayList<>();
    ArrayList<String>Arraylist_Phone=new ArrayList<>();
    ArrayList<String>Arraylist_Emails=new ArrayList<>();

    JSONArray Array_AllContacts = null;
    JSONArray Array_AllContacts1 = null;
    JSONArray Array_InviteContacts = null;
    JSONArray Array_AddContacts = null;

    JSONArray Array_InviteContacts1 = null;
    JSONArray Array_AddContacts1 = null;
    private static final int PERMISSION_REQUEST_CONTACT=2;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;
    android.support.v7.app.AlertDialog alertDialog_Box;
    TextView button_back,txt_result_title;
    ProgressBar progressbar_contact;
    SearchView searchbar;
    ProgressDialog progressDialog;
    String str_emailAdd="",str_phonenumber="",str_search_txt="";
    public  static  String str_invite_type=null;

   // ListView listview_contacts;
    String str_names,str_phones,str_emails,Str_fbid2="";


    private ExpandableListView listView;
    private ExpandableListAdapter1 listAdapter;
    private List<String> listDataHeaderData;
    private HashMap<String, JSONArray> listHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_lists);

        pref =getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();









        button_back=(TextView)findViewById(R.id.back_contact);
        txt_result_title=(TextView)findViewById(R.id.titles_txt_contacts);
        progressbar_contact=(ProgressBar) findViewById(R.id.progress_contact);
        searchbar=(SearchView)findViewById(R.id.search_Contacts);
       // listview_contacts=(ListView) findViewById(R.id.listview_contacts);

        str_emails="";
        str_names="";
        str_phones="";
        progressbar_contact.setVisibility(View.VISIBLE);
        listView = (ExpandableListView) findViewById(R.id.lvExp);
        listView.setVisibility(View.INVISIBLE);

        Array_AllContacts = new JSONArray();
        Array_AllContacts1 = new JSONArray();
        Array_AddContacts = new JSONArray();
        Array_InviteContacts = new JSONArray();
        Array_AddContacts1 = new JSONArray();
        Array_InviteContacts1 = new JSONArray();

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.d("seach_query",query);
                // do something on text submit
                return false;
            }


            public boolean onQueryTextChange(String newText) {




                if (Array_AllContacts != null)

                {




                    if (TextUtils.isEmpty(newText.toString())) {

                        str_search_txt = "";
                        Array_AddContacts = new JSONArray();
                        Array_InviteContacts = new JSONArray();


                        Array_AddContacts = Array_AddContacts1;
                        Array_InviteContacts =Array_InviteContacts1;

                    } else
                    {
                        Array_AddContacts = new JSONArray();
                        Array_InviteContacts = new JSONArray();

                        for (int i = 0; i < Array_AllContacts.length(); i++) {

                            try {
                                String string = Array_AllContacts.getJSONObject(i).getString("name");
                                String stringcom = Array_AllContacts.getJSONObject(i).getString("friendmobileno");
                                String str_emails=Array_AllContacts.getJSONObject(i).getString("friendemail");



                                String str_status=Array_AllContacts.getJSONObject(i).getString("status");
                                str_search_txt = newText;
                                if ((string.toLowerCase()).contains(newText.toLowerCase()) || (stringcom.toLowerCase()).contains(newText.toLowerCase())) {
                                    if (str_status.equalsIgnoreCase("INVITE")) {
                                        Array_InviteContacts.put(Array_AllContacts.getJSONObject(i));


                                    } else if (str_status.equalsIgnoreCase("ADD"))
                                    {

                                        Array_AddContacts.put(Array_AllContacts.getJSONObject(i));

                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }







                    }

                    listDataHeaderData = new ArrayList<>();
                    listHashMap = new HashMap<>();



                     listDataHeaderData.add("INVITE");
                        listDataHeaderData.add("ADD");


                        listHashMap.put(listDataHeaderData.get(0), Array_InviteContacts);
                        listHashMap.put(listDataHeaderData.get(1), Array_AddContacts);


                    if (Array_InviteContacts.length() !=0 || Array_AddContacts.length() !=0)
                    {
                        // listView = (ExpandableListView) findViewById(R.id.lvExp);
                        listView.setVisibility(View.VISIBLE);
                        listAdapter = new ExpandableListAdapter1(getApplicationContext(), listDataHeaderData, listHashMap);
                        listView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();


                            listView.expandGroup(0);
                            listView.expandGroup(1);


                    }
                    else
                    {
                        listView.setVisibility(View.INVISIBLE);
                    }


                }

                return false;
            }
        });


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        if (ContextCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Contact_lists.this,android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(Contact_lists.this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Contact_lists.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(Contact_lists.this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }else{
                new LoadContacts().execute();
            }
        }
        else{
            new LoadContacts().execute();
        }




        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }


    private class LoadContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            arrayList = readContacts();// Get contacts array list from this
            // method
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);



            if (Arraylist_Names !=null) {
                Log.d("Array_names", Arraylist_Names + "");
                Log.d("Array_Contacts", Arraylist_Phone + "");
                Log.d("Array_emails", Arraylist_Emails + "");

                Communication_uploadcontacts myTask1 = new Communication_uploadcontacts();

                myTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




            }
            else
            {

            }


        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // Show Dialog
            progressbar_contact.setVisibility(View.VISIBLE);
            txt_result_title.setVisibility(View.VISIBLE);
        }

    }

    // Method that return all contact details in array format
    private ArrayList<Contact_Model> readContacts() {
        ArrayList<Contact_Model> contactList = new ArrayList<Contact_Model>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI; // Contact URI
        Cursor contactsCursor = getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC "); // Return
        // all
        // contacts
        // name
        // containing
        // in
        // URI
        // in
        // ascending
        // order
        // Move cursor at starting
        if (contactsCursor.moveToFirst()) {
            do {
                long contctId = contactsCursor.getLong(contactsCursor
                        .getColumnIndex("_ID")); // Get contact ID
                Uri dataUri = ContactsContract.Data.CONTENT_URI; // URI to get
                // data of
                // contacts
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);// Retrun data cusror represntative to
                // contact ID

                // Strings to get all details
                String displayName = "";
                String nickName = "";
                String homePhone = "";
                String mobilePhone = "";
                String workPhone = "";
                String photoPath = "" +  R.mipmap.ic_launcher; // Photo path
                byte[] photoByte = null;// Byte to get photo since it will come
                // in BLOB
                String homeEmail = "";
                String workEmail = "";
                String companyName = "";
                String title = "";

                // This strings stores all contact numbers, email and other
                // details like nick name, company etc.
                String contactNumbers = "";
                String contactEmailAddresses = "";
                String contactOtherDetails = "";

                // Now start the cusrsor
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// get

                    do {

                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {

                            // In this get All contact numbers like home,
                            // mobile, work, etc and add them to numbers string
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Home Phone : " + homePhone
                                            + "\n";

                                    homePhone=homePhone.replace(" ","");
                                    homePhone=homePhone.replace("+","");

                                    Arraylist_Names.add(displayName);
                                    Arraylist_Phone.add(homePhone);
                                    Arraylist_Emails.add("");
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    workPhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Work Phone : " + workPhone
                                            + "\n";

                                    workPhone=workPhone.replace(" ","");
                                    workPhone=workPhone.replace("+","");
                                    Arraylist_Names.add(displayName);
                                    Arraylist_Phone.add(workPhone);
                                    Arraylist_Emails.add("");
                                    break;

                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers += "Mobile Phone : "
                                            + mobilePhone + "\n";

                                    mobilePhone=mobilePhone.replace(" ","");
                                    mobilePhone=mobilePhone.replace("+","");

                                    Arraylist_Names.add(displayName);
                                    Arraylist_Phone.add(mobilePhone);
                                    Arraylist_Emails.add("");
                                    break;

                            }
                        }
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {

                            // In this get all Emails like home, work etc and
                            // add them to email string
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactEmailAddresses += "Home Email : "
                                            + homeEmail + "\n";


                                    homeEmail=homeEmail.replace(" ","");


                                    Arraylist_Names.add(displayName);
                                    Arraylist_Phone.add("");
                                    Arraylist_Emails.add(homeEmail);
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactEmailAddresses += "Work Email : "
                                            + workEmail + "\n";

                                    workEmail=workEmail.replace(" ","");
                                    Arraylist_Names.add(displayName);
                                    Arraylist_Phone.add("");
                                    Arraylist_Emails.add(workEmail);

                                    break;

                            }
                        }



                    } while (dataCursor.moveToNext()); // Now move to next

                }

            } while (contactsCursor.moveToNext());
        }

        return contactList;
    }




    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    public class  Communication_uploadcontacts extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {



                URL url = new URL(UrlClass.invite_contacts);
                JSONObject postDataParams = new JSONObject();




                postDataParams.put("fbid",pref.getString("userid",""));

                str_names=Arraylist_Names.toString();
                str_names=str_names.replace("[","");
                str_names=str_names.replace("]","");
                str_names=str_names.replace("{","");
                str_names=str_names.replace("}","");


                str_phones=Arraylist_Phone.toString();
                str_phones=str_phones.replace("[","");
                str_phones=str_phones.replace("]","");
                str_phones=str_phones.replace("{","");
                str_phones=str_phones.replace("}","");


                str_emails=Arraylist_Emails.toString();
                str_emails=str_emails.replace("[","");
                str_emails=str_emails.replace("]","");
                str_emails=str_emails.replace("{","");
                str_emails=str_emails.replace("}","");











                postDataParams.put("name",str_names);
                postDataParams.put("mobileno",str_phones);
                postDataParams.put("email",str_emails);



                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {

                Log.d("Exception_php", e.getMessage()+"");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {


            if (result !=null) {

                if (result.equalsIgnoreCase("done"))
                {
                    Communication_showcontacts myTask1 = new Communication_showcontacts();

                    myTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }

            }
            else
            {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Contact_lists.this);
                builder1.setTitle("Oops");
                builder1.setMessage("Unknown error reported from server. Please contact support@play-date.ae.");
                builder1.setCancelable(false);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                // finish();
                            }
                        });
                alertDialog_Box = builder1.create();
                alertDialog_Box.show();
            }


        }
    }
    public class  Communication_showcontacts extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {



                URL url = new URL(UrlClass.show_contacts);
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("fbid",pref.getString("userid",""));



                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {

                Log.d("Exception_php", e.getMessage()+"");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            progressbar_contact.setVisibility(View.INVISIBLE);
            txt_result_title.setVisibility(View.INVISIBLE);

            if (result !=null)
            {
                Array_AllContacts = new JSONArray();
                Array_AddContacts = new JSONArray();
                Array_InviteContacts = new JSONArray();

                Array_AddContacts1 = new JSONArray();
                Array_InviteContacts1 = new JSONArray();

                try {
                    Array_AllContacts = new JSONArray(result);

                    for (int i = 0; i < Array_AllContacts.length(); i++) {
                        JSONObject Jsonobject = Array_AllContacts.getJSONObject(i);
                        if (Jsonobject.getString("status").equalsIgnoreCase("INVITE")) {
                            Array_InviteContacts.put(Jsonobject);
                            Array_InviteContacts1.put(Jsonobject);

                        } else if (Jsonobject.getString("status").equalsIgnoreCase("ADD")) {
                            Array_AddContacts.put(Jsonobject);
                            Array_AddContacts1.put(Jsonobject);
                        }
                    }



                    listDataHeaderData = new ArrayList<>();
                    listHashMap = new HashMap<>();



    listDataHeaderData.add("INVITE");
    listDataHeaderData.add("ADD");


    listHashMap.put(listDataHeaderData.get(0), Array_InviteContacts);
    listHashMap.put(listDataHeaderData.get(1), Array_AddContacts);


                    if (Array_InviteContacts !=null || Array_AddContacts !=null)
                    {
                        listView = (ExpandableListView) findViewById(R.id.lvExp);
                        listView.setVisibility(View.VISIBLE);
                        listAdapter = new ExpandableListAdapter1(getApplicationContext(), listDataHeaderData, listHashMap);
                        listView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();

                            listView.expandGroup(0);
                            listView.expandGroup(1);




                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else
            {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Contact_lists.this);
                builder1.setTitle("Oops");
                builder1.setMessage("Error given to server.");
                builder1.setCancelable(false);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                // finish();
                            }
                        });
                alertDialog_Box = builder1.create();
                alertDialog_Box.show();
            }


        }
    }

    public class ExpandableListAdapter1 extends BaseExpandableListAdapter {

        private Context context;
        private List<String> listDataHeader;
        private HashMap<String, JSONArray> listHashMap;


        public ExpandableListAdapter1(Context context, List<String> listDataHeader, HashMap<String, JSONArray> listHashMap) {
            this.context = context;
            this.listDataHeader = listDataHeader;
            this.listHashMap = listHashMap;


        }

        @Override
        public int getGroupCount() {


            return listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return listHashMap.get(listDataHeader.get(i)).length();
        }

        @Override
        public Object getGroup(int i) {

            return listDataHeader.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {

            Object obj_val=0;
            try {
                obj_val=listHashMap.get(listDataHeader.get(i)).get(i1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return obj_val;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

            String HeaderTitle = (String) getGroup(i);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_group, null);

            }
            TextView lblListHeder = (TextView) view.findViewById(R.id.lblListHeader);

            lblListHeder.setTypeface(null, Typeface.BOLD);
            lblListHeder.setText(HeaderTitle);
            Log.d("heder==", HeaderTitle + "");
            return view;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {



           LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);




            if (i == 0)
            {
                view = inflater.inflate(R.layout.contact_list_invites, null);

                TextView txt_name=(TextView)view.findViewById(R.id.text_contact_names);
                TextView txt_number_Email=(TextView)view.findViewById(R.id.text_number_email);
                final TextView txt_invite=(TextView)view.findViewById(R.id.text_invte_contacts);
                txt_invite.setTag(i1);

                txt_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            JSONObject obj_invite = new JSONObject(String.valueOf(Array_InviteContacts.get((Integer) txt_invite.getTag())));

                             str_phonenumber=obj_invite.getString("friendmobileno");
                             str_emailAdd=obj_invite.getString("friendemail");

                            str_emailAdd=str_emailAdd.replace(" ","");
                            str_phonenumber=str_phonenumber.replace(" ","");


                            invitesUsers();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                });

//[{"friendemail":" ","friendmobileno":" 88888 88888","frienduserid":"","name":" Sachin","imageurl":"","status":"INVITE","gender":null}
//[{"friendemail":" sachinmokashi1989@yahoo.com","friendmobileno":" ","frienduserid":"10155141087782139","name":"tariq","imageurl":"http:\/\/play-date.ae\/app\/profileimages\/10155141087782139.jpg","status":"ADD","gender":"Boy"},


           if (Array_InviteContacts !=null)
                {


                        JSONObject Array_obj = null;
                        try {
                            Array_obj = new JSONObject(String.valueOf(Array_InviteContacts.get(i1)));
                            String str_names2=Array_obj.getString("name");
                            String str_number=Array_obj.getString("friendmobileno");
                            String str_emails=Array_obj.getString("friendemail");

                            str_emails=str_emails.replace(" ","");
                            str_number=str_number.replace(" ","");

                            if (str_search_txt.length() !=0)
                            {
                                if ((str_names2.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                                    int startPos = (str_names2.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                                    int endPos = startPos + str_search_txt.length();

                                    Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_names2); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                                    spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                    txt_name.setText(spanText, TextView.BufferType.SPANNABLE);
                                } else
                                    {
                                    txt_name.setText(str_names2);
                                    txt_name.setTextColor(getResources().getColor(R.color.black));
                                }


                                if (!(Array_obj.getString("friendemail")).equalsIgnoreCase(""))
                                {

                                    if ((str_emails.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                                        int startPos = (str_emails.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                                        int endPos = startPos + str_search_txt.length();

                                        Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_emails); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                                        spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                        txt_number_Email.setText(spanText, TextView.BufferType.SPANNABLE);
                                    } else {
                                        txt_number_Email.setText(str_emails);
                                        txt_number_Email.setTextColor(getResources().getColor(R.color.darkgray));
                                    }
                                }

                                if (!(Array_obj.getString("friendmobileno")).equalsIgnoreCase(""))
                                {


                                    if ((str_number.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                                        int startPos = (str_number.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                                        int endPos = startPos + str_search_txt.length();

                                        Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_number); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                                        spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                        txt_number_Email.setText(spanText, TextView.BufferType.SPANNABLE);
                                    } else {
                                        txt_number_Email.setText(str_number);
                                        txt_number_Email.setTextColor(getResources().getColor(R.color.darkgray));
                                    }
                                }

                            }
                            else
                            {
                                txt_name.setText(str_names2);
                                txt_name.setTextColor(getResources().getColor(R.color.black));
                                txt_number_Email.setText(str_number);
                                txt_number_Email.setTextColor(getResources().getColor(R.color.darkgray));

                            }


                            } catch (JSONException e) {
                            e.printStackTrace();
                        }






                }


            }
            else
            {
                view = inflater.inflate(R.layout.facebook_add_friends, null);

                ImageView img_profileimg=(ImageView)view.findViewById(R.id.fb_profileimage);
                TextView txt_name=(TextView)view.findViewById(R.id.fb_name);
                final TextView txt_add=(TextView)view.findViewById(R.id.fb_add);

                txt_add.setTag(i1);

                txt_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try
                        {

                            JSONObject obj_invite = new JSONObject(String.valueOf(Array_AddContacts.get((Integer) txt_add.getTag())));
                            Str_fbid2=obj_invite.getString("frienduserid");


                            addUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                });

                if (Array_AddContacts !=null)
                {


                        JSONObject Array_obj = null;
                        try {
                            Array_obj = new JSONObject(String.valueOf(Array_AddContacts.get(i1)));

                            String str_names2=(Array_obj.getString("name"));
                            int img_gefult_boy_girl=0;

                            if (str_search_txt.length() !=0) {
                                if ((str_names2.toLowerCase()).contains(str_search_txt.toLowerCase())) {

                                    int startPos = (str_names2.toLowerCase()).indexOf(str_search_txt.toLowerCase());
                                    int endPos = startPos + str_search_txt.length();

                                    Spannable spanText = Spannable.Factory.getInstance().newSpannable(str_names2); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                                    spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                    txt_name.setText(spanText, TextView.BufferType.SPANNABLE);
                                } else {
                                    txt_name.setText(str_names2);
                                    txt_name.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                            else
                            {
                                txt_name.setText(str_names2);
                                txt_name.setTextColor(getResources().getColor(R.color.black));
                            }

                            if (Array_obj.getString("gender").equalsIgnoreCase("Boy")) {

                                img_gefult_boy_girl = R.drawable.defaultboy;

                            } else
                            {

                                img_gefult_boy_girl = R.drawable.defaultgirl;

                            }


                            Picasso.with(Contact_lists.this).load(Array_obj.getString("imageurl")).fit().centerCrop()
                                    .placeholder(img_gefult_boy_girl)
                                    .into(img_profileimg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }








                }


            }
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }


        public void onBackPressed() {

        }
    }

    private void addUser()
    {

        progressDialog = new ProgressDialog(Contact_lists.this);
        progressDialog.setMessage("Sending..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Communication_Friends_add myTask = new Communication_Friends_add();

                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB)
                        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        myTask.execute();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void invitesUsers()
    {




        if (!str_phonenumber.equalsIgnoreCase(""))
        {
            String str_texttoshare="";
            if (str_invite_type.equalsIgnoreCase("meetup"))
        {
            str_texttoshare="You have been invited to a Play:Date meet-up!\n\nEnter the event code:\n"+setting_meetups_user1.str_meetupsetting+" to join the meet-up.\n\nDownload Play:Date on your iPhone from http://www.play-date.ae and find new friends for your children!";
        }
        else
        {
            str_texttoshare = "Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\n Visit http://www.play-date.ae to start using the app today!";
        }


            Intent sendIntent = new Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.setData(Uri.parse("sms:" + str_phonenumber));
            sendIntent.putExtra("sms_body", str_texttoshare);
            startActivity(sendIntent);

        }


        if (!str_emailAdd.equalsIgnoreCase(""))
        {
            String str_texttoshare="";
            if (str_invite_type.equalsIgnoreCase("meetup"))
            {
                str_texttoshare="You have been invited to a Play:Date meet-up!\n\nEnter the event code:\n"+setting_meetups_user1.str_meetupsetting+" to join the meet-up.\n\nDownload Play:Date on your iPhone from http://www.play-date.ae and find new friends for your children!";
            }
            else
            {
                str_texttoshare="Hey, \n\n Play:Date is a great app to start your child's social network. Join events and get access to exclusive deals for the whole family! \n\nI have been using it since a while, and it would be great if you could download it! \n\n Visit http://www.play-date.ae to download it on your mobile phone! \n\n Thanks!" ;
            }
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{str_emailAdd});
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download Play:Date");
            shareIntent.putExtra(Intent.EXTRA_TEXT, str_texttoshare);
            shareIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(shareIntent, "Pick an Email provider"));
        }

    }

    public class Communication_Friends_add extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {

        }

        //        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL(UrlClass.invite_addfriend);
                JSONObject postDataParams = new JSONObject();


                postDataParams.put("fbid1", pref.getString("userid", ""));
                postDataParams.put("fbid2",Str_fbid2);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {

                Log.d("Exception_php", e.getMessage() + "");
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {


            if (result != null)
            {

                progressDialog.dismiss();

                if (result.equalsIgnoreCase("done"))
                {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Communication_showcontacts myTask1 = new Communication_showcontacts();
                                myTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
                else  if (result.equalsIgnoreCase("inserterror"))
                {
                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Contact_lists.this);
                    builder1.setTitle("Oops");
                    builder1.setMessage("The server encountered an error and your Play:Date could not be created. Please try again.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();
                }
                else  if (result.equalsIgnoreCase("userrejected"))
                {

                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Contact_lists.this);
                    builder1.setTitle("Rejected");
                    builder1.setMessage("The user has left swiped your profile and you cannot send a request to this user.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();

                }
                else  if (result.equalsIgnoreCase("alreadyadded"))
                {


                    android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Contact_lists.this);
                    builder1.setTitle("Request already sent");
                    builder1.setMessage("You have already requested the user to become friends with you. You will be notified once the request is accepted.");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    // finish();
                                }
                            });
                    alertDialog_Box = builder1.create();
                    alertDialog_Box.show();

                }





            }



        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new LoadContacts().execute();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    //  ToastMaster.showMessage(getActivity(),"No permission for contacts");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }
    }

