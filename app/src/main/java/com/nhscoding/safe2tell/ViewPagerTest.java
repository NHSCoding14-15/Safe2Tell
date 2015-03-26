package com.nhscoding.safe2tell;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPagerTest.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPagerTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerTest extends android.support.v4.app.Fragment
    implements LEARN.OnFragmentInteractionListener
{


    private OnFragmentInteractionListener mListener;

    private ViewPager mPager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ViewPagerAdapter(getFragmentManager());

        mPager = (ViewPager) getView().findViewById(R.id.viewPager);
        mPager.setAdapter(adapter);
    }

    private ViewPagerAdapter adapter;

    public static final int NUM_ITEMS = 2;

    public static ViewPagerTest newInstance() {
        ViewPagerTest fragment = new ViewPagerTest();
        return fragment;
    }

    public ViewPagerTest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager_test, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int num) {
            android.support.v4.app.Fragment objFragment;
            if (num == 0) {
                objFragment = new LEARN(-1);
            } else {
                objFragment = new QUIZ();
            }
            return objFragment;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

}
