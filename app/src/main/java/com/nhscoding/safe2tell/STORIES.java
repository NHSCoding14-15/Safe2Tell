package com.nhscoding.safe2tell;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhscoding.safe2tell.API.ProblemParser;

/**
 * Created by davidkopala on 2/6/15.
 */
public class STORIES extends Fragment {

    View rootview;

    private OnFragmentInteractionListener mListener;

    ProblemParser parser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_stories, container, false);
        Log.d("Safe2Tell-STORIES", "Attempting To Start Problem Parser");
        parser = new ProblemParser();
        parser.execute();
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

    public static STORIES newInstance() {
        STORIES fragment = new STORIES();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}