package com.nhscoding.safe2tell;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nhscoding.safe2tell.API.PostObject;
import com.nhscoding.safe2tell.API.PostParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by davidkopala on 2/6/15.
 */
public class LEARN extends Fragment {


    View rootview;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    int mID = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_learn, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.learnRecycler);
        setRecyclerView(recyclerView, mID);
        return rootview;
    }

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

    public LEARN(int i) {
        mID = i;
        Log.i("LEARN", "Learn mID = " + i);
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

    public static LEARN newInstance(int i) {
        LEARN fragment = new LEARN(i);
        return fragment;


    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    void setRecyclerView(RecyclerView view, int ID) {
        Log.i("Learn.setRecyclerView", "ID = " + ID);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view.setLayoutManager(layoutManager);

        PostParser postParser;
        CardAdapter adapter;

        postParser = new PostParser();
        postParser.execute();
        List Posts = null;
        InputStream is;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            is = postParser.get(5000, TimeUnit.MILLISECONDS);
            Posts = postParser.readJSONStream(is);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List temp = new ArrayList();

        if (Posts != null) {
            for (int i = 0; i < Posts.size(); i++) {
                PostObject post = (PostObject) Posts.get(i);
                if (post._ProblemID == ID) {
                    temp.add(post);
                }
            }
        }

        CustomCard[] dataset = new CustomCard[temp.size()];
        if (Posts != null) {
            for (int i = 0; i < temp.size(); i++) {
                PostObject entry = (PostObject) temp.get(temp.size() - (i + 1));
                int id = entry._ProblemID;
                if (id == ID) {
                    Log.i("Title", entry._Title);
                    Log.i("Text", entry._Text);

                    CustomCard card = new CustomCard(getActivity());
                    card.setTitle(entry._Title);
                    card.setText(entry._Text);

                    dataset[i] = card;
                }
            }
        }

        adapter = new CardAdapter(dataset, getActivity());
        view.setAdapter(adapter);
    }

//public class Stuff extends Activity {
//    Button submit_tip = (Button) findViewById(R.id.submit_tip);
//    submit_tip.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View v) {
//            Intent intent = new Intent(v.getContext(), SUBMIT_TIP.class);
//            startActivityForResult(intent,0);
//        }
}
