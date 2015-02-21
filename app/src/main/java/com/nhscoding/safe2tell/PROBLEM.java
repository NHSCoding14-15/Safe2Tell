package com.nhscoding.safe2tell;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.nhscoding.safe2tell.API.PostObject;
import com.nhscoding.safe2tell.API.PostParser;
import com.nhscoding.safe2tell.API.ProblemParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PROBLEM.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PROBLEM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PROBLEM extends android.support.v4.app.Fragment {
    View rootview;

    private OnFragmentInteractionListener mListener;

    RecyclerView mRecyclerView;
    CardAdapter adapter;

    PostParser postParser;
    ProblemParser parser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_problem, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.problemRecycler);

        Log.d("Safe2Tell-STORIES", "Attempting To Start Problem Parser");
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

        CustomCard[] dataset = new CustomCard[Posts.size()];

        for (int i = 0; i < Posts.size(); i++) {
            PostObject entry = (PostObject) Posts.get(i);
            Log.i("Title", entry._Title);
            Log.i("Text", entry._Text);

            CustomCard card = new CustomCard(getActivity());
            card.setTitle(entry._Title);
            card.setText(entry._Text);

            dataset[i] = card;
        }

        adapter = new CardAdapter(dataset, getActivity());
        mRecyclerView.setAdapter(adapter);

        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);


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

    public static PROBLEM newInstance() {
        PROBLEM fragment = new PROBLEM();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
