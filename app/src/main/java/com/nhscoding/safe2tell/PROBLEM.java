package com.nhscoding.safe2tell;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nhscoding.safe2tell.API.PostObject;
import com.nhscoding.safe2tell.API.PostParser;
import com.nhscoding.safe2tell.API.ProblemObject;
import com.nhscoding.safe2tell.API.ProblemParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PROBLEM extends android.support.v4.app.Fragment {
    View rootview;

    private OnFragmentInteractionListener mListener;

    RecyclerView mRecyclerView;
    CardAdapter adapter;

    PostParser postParser;

    String name = "";
    String key1 = "arrayID";

    int ID = -1;
    ProblemParser problemParser;
    List Problems = new ArrayList();
    List Posts = new ArrayList();

    ViewPager viewPager;
    PagerAdapter pagerAdapter;


    public PROBLEM(int i) {
        Log.i("PROBLEM", "Problem ID = " + i);
        ID = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_problem, container, false);

        /*postParser = new PostParser();
        postParser.execute();
        InputStream postIn;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            postIn = postParser.get(5000, TimeUnit.MILLISECONDS);
            Posts = postParser.readJSONStream(postIn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List datalist = new ArrayList();

        if (ID > 0) {
            for (int i = 0; i < Posts.size(); i++) {
                PostObject post = (PostObject) Posts.get(i);
                if (post._ProblemID == ID) {
                    datalist.add(post);
                }
            }
        } else {
            datalist = Posts;
        }

        CustomCard[] dataset = new CustomCard[datalist.size()];

        for (int i = 0; i < datalist.size(); i++) {
            PostObject post = (PostObject) datalist.get(i);
            CustomCard card = new CustomCard(getActivity());
            card.setTitle(post._Title);
            card.setText(post._Text);
            dataset[i] = card;
        }

        /*adapter = new CardAdapter(dataset, getActivity());
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.learnRecycler);
        mRecyclerView.setAdapter(adapter);*/

        /*

        for (int i = 0; i < array.length; i++) {
            if (i == ID) {
                name = array[i];
                break;
            }
        }

        for (int i = 0; i < Problems.size(); i++) {
            ProblemObject entry = (ProblemObject) Problems.get(i);
            if (name.equals(entry.Name)) {
                ID = entry.ID;
                break;
            }
        }


        int count = 0;

        for (int i = 0; i < Posts.size(); i++) {
            PostObject entry = (PostObject) Posts.get(i);
            if (entry._ProblemID == ID) {
                count++;
            }
        }

        dataset = new CustomCard[count];

        int data = -1;
            for (int i = 0; i < Posts.size(); i++) {
                PostObject entry = (PostObject) Posts.get(i);
                if (entry._ProblemID == ID) {
                    CustomCard card = new CustomCard(getActivity());
                    card.mText = entry._Text;
                    card.mTitleText = entry._Title;
                    data++;
                    dataset[data] = card;
                }
            }

        //adapter = new CardAdapter(dataset, getActivity());
        //mRecyclerView.setAdapter(adapter);
        */

        viewPager = (ViewPager) rootview.findViewById(R.id.problemPager);
        pagerAdapter = new PagerAdapter(getFragmentManager(), ID);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //mRecyclerView.setLayoutManager(layoutManager);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int i = Integer.parseInt(getArguments().getString(key1));

            String[] array = getResources().getStringArray(R.array.problems);
            name = array[i];
            Log.i("Name", name);
        }
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

    public static PROBLEM newInstance(int i) {
        PROBLEM fragment = new PROBLEM(i);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        int _ID;

        public PagerAdapter(FragmentManager fm, int ID) {
            super(fm);
            _ID = ID;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            android.support.v4.app.Fragment frag = null;
            Log.i("PagerAdapter", "PagerAdapter _ID = " + _ID);
            switch (i) {
                case 0:
                    frag = new LEARN(_ID);
                    break;

                case 1:
                    frag = new QUIZ();
                    break;

                default:
                    frag = new LEARN(_ID);
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 1.0f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}
