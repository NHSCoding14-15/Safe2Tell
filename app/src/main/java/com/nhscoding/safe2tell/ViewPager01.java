package com.nhscoding.safe2tell;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPager01 extends Fragment implements
        Text.OnFragmentInteractionListener{

    String text;
    ViewPager viewPager;
    PagerAdapter adapter;

    View rootView;

    private OnFragmentInteractionListener mListener;

    public static ViewPager01 newInstance() {
        ViewPager01 fragment = new ViewPager01();
        return fragment;
    }

    public ViewPager01() {
        // Required empty public constructor
    }

    public ViewPager01(String message){
        text = message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.subPager);
        adapter = new PagerAdapter(getFragmentManager(), text);
        viewPager.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        String text;

        public PagerAdapter(FragmentManager fm, String message) {
            super(fm);
            text = message;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment frag = new Text(text);
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
