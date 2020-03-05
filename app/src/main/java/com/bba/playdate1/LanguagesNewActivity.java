package com.bba.playdate1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class LanguagesNewActivity extends AppCompatActivity {
    ListView listView1;
    String[] Languages;
    String[] str_languges;
    TextView SaveTxt;
    public SharedPreferences pref;
    SharedPreferences.Editor editor;

    ArrayList<String> Array_Languge_three=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages_new);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        str_languges = new String[3];
        Languages = getResources().getStringArray(R.array.languages);
        SaveTxt = (TextView) findViewById(R.id.save_txt);
        SaveTxt.setEnabled(false);
        SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));

        String Str_Lang=null;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if (extras.getString("speak_row").equalsIgnoreCase("yes"))
            {
                Str_Lang=SettingActivity.Language1;

            }
            else {
                Str_Lang = pref.getString("language", "");

            }
        }
        else
        {


     Str_Lang=pref.getString("language","");



        }


        if ( Str_Lang.length()!=0)
        {
            SaveTxt.setEnabled(true);
            SaveTxt.setTextColor(Color.parseColor("#000000"));
            try {
                JSONArray array=new JSONArray(Str_Lang);
                for (int k=0;k<array.length();k++)
                {
                    Array_Languge_three.add((String)array.get(k));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





        listView1=(ListView)findViewById(R.id.listviewMap);

        CustomAdapter1 adapters=new CustomAdapter1();
        listView1.setAdapter(adapters);



        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                // String s = listView1.getItemAtPosition(i).toString();


                ImageView imageViews = (ImageView) view.findViewById(R.id.imageViewMapL5);
               // imageViews.setBackgroundColor(Color.RED);
               // imageViews.setImageResource(android.R.color.transparent);


                if (Array_Languge_three.size()<3)
                    {
                        if (Array_Languge_three.contains(Languages[i]))
                        {
                            imageViews.setImageResource(0);
                            int indexid = Array_Languge_three.indexOf(Languages[i]);
                            Array_Languge_three.remove(indexid);

                        } else
                            {

                                imageViews.setImageResource(R.drawable.checkmark);
                                Array_Languge_three.add(Languages[i]);
                        }
                    }
                    else
                    {
                        if (Array_Languge_three.contains(Languages[i]))
                        {
                            imageViews.setImageResource(0);
                            int indexid = Array_Languge_three.indexOf(Languages[i]);
                            Array_Languge_three.remove(indexid);

                        }
                    }
                    if (Array_Languge_three.size() !=0)
                    {
                        SaveTxt.setEnabled(true);
                    SaveTxt.setTextColor(Color.parseColor("#000000"));
                    }
                    else
                    {
                        SaveTxt.setEnabled(false);
                        SaveTxt.setTextColor(Color.parseColor("#b4b4b4"));
                    }


            }
        });

        SaveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONArray Array_lang=new JSONArray(Array_Languge_three);


                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {

                    if ( extras.getString("speak_row").equalsIgnoreCase("yes"))
                    {
                        SettingActivity.Language1=Array_lang.toString();
                        Intent intent1 = new Intent("registerinfo_setting");
                        // intent1.putExtra("message", mapstr);
                        LocalBroadcastManager.getInstance(LanguagesNewActivity.this).sendBroadcast(intent1);

                    }
                    else
                    {

                        editor.putString("language",Array_lang.toString());
                        editor.commit();


                        Intent intent1 = new Intent("registerinfo");
                        // intent1.putExtra("message", mapstr);
                        LocalBroadcastManager.getInstance(LanguagesNewActivity.this).sendBroadcast(intent1);
                    }
                }
                else
                {

                    editor.putString("language",Array_lang.toString());
                    editor.commit();


                    Intent intent1 = new Intent("registerinfo");
                    // intent1.putExtra("message", mapstr);
                    LocalBroadcastManager.getInstance(LanguagesNewActivity.this).sendBroadcast(intent1);
                }

                finish();






            }
        });

    }

    class CustomAdapter1 extends BaseAdapter
    {

        @Override
        public int getCount() {
            return Languages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public int getItemViewType(int position) {
//            if (getItem(position) instanceof Person) {
//                return TYPE_PERSON;
//            }
//
//            return TYPE_DIVIDER;
            return 1;
        }
        public class ViewHolder {


        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


//            if (view ==null) {
                view = getLayoutInflater().inflate(R.layout.layout_maplocationlist, null);
                final RelativeLayout itemRowSelect = (RelativeLayout) view.findViewById(R.id.itemMapLR);
                TextView textViewName = (TextView) view.findViewById(R.id.textMapL24);
                textViewName.setText(Languages[i]);
                ImageView imageViews = (ImageView) view.findViewById(R.id.imageViewMapL5);
                imageViews.setTag(i);
                view.setTag(i);
            if (Array_Languge_three.contains(Languages[i]))
            {

                imageViews.setImageResource(R.drawable.checkmark);

            } else
            {

                imageViews.setImageResource(0);

            }


            return view;
        }
    }

}
