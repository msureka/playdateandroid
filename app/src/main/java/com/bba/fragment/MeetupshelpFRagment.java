package com.bba.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bba.playdate1.R;

import static com.bba.fragment.FriendsFragment.titletxt;
import static com.bba.fragment.FriendsFragment.titletxt1;

/**
 * Created by spielmohitp on 2/22/2018.
 */

@SuppressLint("ValidFragment")
class MeetupshelpFRagment extends Fragment {
    Button gotit;
    TextView createtxt1,txt,txt1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.meethelplayout, container, false);

        gotit = (Button) view.findViewById(R.id.gotbtn);
        createtxt1 = (TextView) view.findViewById(R.id.createtxt);

         txt=(TextView)view.findViewById(R.id.txt);
         txt1=(TextView)view.findViewById(R.id.txt1);



        MeetsupFragment meet = new MeetsupFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment1, meet);
        fragmentTransaction.commit();
        FriendsFragment.qbtn.setVisibility(View.GONE);
        FriendsFragment.joinbtn.setVisibility(View.VISIBLE);
        FriendsFragment.PlusImg1.setVisibility(View.VISIBLE);
        FriendsFragment.PlusImg.setVisibility(View.INVISIBLE);
        FriendsFragment.addtxt.setVisibility(View.INVISIBLE);
        FriendsFragment.createtxt.setVisibility(View.VISIBLE);
        FriendsFragment.jointxt.setVisibility(View.VISIBLE);
        FriendsFragment.progressbar1.setVisibility(View.VISIBLE);
        FriendsFragment.progressbar2.setVisibility(View.INVISIBLE);
        titletxt.setVisibility(View.INVISIBLE);
        titletxt1.setVisibility(View.VISIBLE);
        titletxt1.setTypeface(Typeface.DEFAULT_BOLD);
        titletxt.setTypeface(Typeface.DEFAULT_BOLD);
        txt1.setTypeface(Typeface.DEFAULT_BOLD);

        createtxt1.setVisibility(View.VISIBLE);
        FriendsFragment.meetb.setTypeface(Typeface.DEFAULT_BOLD);
        FriendsFragment.chatb.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.setVisibility(View.GONE);
            }
        });

        return view;

    }
}

