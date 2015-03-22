package com.nhscoding.safe2tell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nhscoding.safe2tell.API.PostParser;

/**
 * Created by davidkopala on 2/6/15.
 */
public class ABOUT_US extends Fragment implements
        About.OnFragmentInteractionListener {

    View rootview;

    VerticalViewPager viewPager;
    PagerAdapter adapter;

    private OnFragmentInteractionListener mListener;
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){

        final FloatingActionButton subTip = (FloatingActionButton) view.findViewById(R.id.subTip);
        subTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(),SUBMIT_TIP.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_about_us, container, false);
        viewPager = (VerticalViewPager) rootview.findViewById(R.id.aboutPager);
        adapter = new PagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static ABOUT_US newInstance() {
        ABOUT_US fragment = new ABOUT_US();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        FragmentManager manager;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            manager = fm;
        }

        @Override
        public Fragment getItem(int i) {
            return new About(i);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}