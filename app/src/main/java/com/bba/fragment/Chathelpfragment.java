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

import static com.bba.fragment.FriendsFragment.PlusImg;
import static com.bba.fragment.FriendsFragment.PlusImg1;
import static com.bba.fragment.FriendsFragment.addtxt;
import static com.bba.fragment.FriendsFragment.createtxt;
import static com.bba.fragment.FriendsFragment.joinbtn;
import static com.bba.fragment.FriendsFragment.jointxt;
import static com.bba.fragment.FriendsFragment.progressbar1;
import static com.bba.fragment.FriendsFragment.progressbar2;
import static com.bba.fragment.FriendsFragment.qbtn;
import static com.bba.fragment.FriendsFragment.titletxt;
import static com.bba.fragment.FriendsFragment.titletxt1;

/**
 * Created by spielmohitp on 2/22/2018.
 */

@SuppressLint("ValidFragment")
class Chathelpfragment extends Fragment {
    Button next;
    TextView txt,txt1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chathelplayout, container, false);

        next = (Button) view.findViewById(R.id.nextbtn1);
        txt = (TextView) view.findViewById(R.id.txt);
        txt1 = (TextView) view.findViewById(R.id.txt1);
        txt1.setTypeface(Typeface.DEFAULT_BOLD);
        addtxt.setVisibility(View.VISIBLE);
        createtxt.setVisibility(View.INVISIBLE);
        PlusImg1.setVisibility(View.INVISIBLE);
        PlusImg.setVisibility(View.VISIBLE);
        jointxt.setVisibility(View.INVISIBLE);
        joinbtn.setVisibility(View.INVISIBLE);
        progressbar1.setVisibility(View.INVISIBLE);
        progressbar2.setVisibility(View.VISIBLE);
        qbtn.setVisibility(View.VISIBLE);
        FriendsFragment.chatb.setTypeface(Typeface.DEFAULT_BOLD);
        FriendsFragment.meetb.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        titletxt1.setVisibility(View.INVISIBLE);
        titletxt.setVisibility(View.VISIBLE);
        titletxt.setTypeface(Typeface.DEFAULT_BOLD);

        titletxt1.setTypeface(Typeface.DEFAULT_BOLD);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetupshelpFRagment meet = new MeetupshelpFRagment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.home_fragment2, meet);
                fragmentTransaction.commit();
            }
        });

        return view;

    }
}

