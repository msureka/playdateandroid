package com.bba.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bba.playdate1.MainActivity;
import com.bba.playdate1.R;

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    SharedPreferences prefs = null;
    LayoutInflater mLayoutInflater;
    int[] mResources = {
            R.drawable.tutorial1,
            R.drawable.tutorial2,
            R.drawable.tutorial3,
            R.drawable.tutorial4
    };

    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.tutorial_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);

        Button btn = (Button) itemView.findViewById(R.id.gotit_btn);
        btn.bringToFront();
        if (position == (mResources.length - 1)) {
            btn.setVisibility(View.VISIBLE);
            btn.setEnabled(true);

        } else {
            btn.setVisibility(View.GONE);
            btn.setEnabled(false);
        }
        container.addView(itemView);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your code that you want to execute on this button click
              /*  Intent i = new Intent(mContext,GotIT.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);*/
                Log.e("ghh", "hjghg");


                Intent i = new Intent(mContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(i);
                //overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_right_out);
            }

        });


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
