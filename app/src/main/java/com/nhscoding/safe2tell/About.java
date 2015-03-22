package com.nhscoding.safe2tell;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link About.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link About#newInstance} factory method to
 * create an instance of this fragment.
 */
public class About extends Fragment {

    int position = 0;

    View rootView;
    TextView content;
    ImageView image;

    private OnFragmentInteractionListener mListener;

    public static About newInstance() {
        About fragment = new About();
        return fragment;
    }

    public About() {
        // Required empty public constructor
        position = 0;
    }

    public About(int pos) {
        position = pos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_about, container, false);
        content = (TextView) rootView.findViewById(R.id.aboutText);
        image = (ImageView) rootView.findViewById(R.id.aboutImage);
        switch (position) {
            case 0:
                content.setText("Hello World!");
                image.setImageDrawable(getResources().getDrawable(R.drawable.bus));
                break;

            case 1:
                content.setText("This is section 2");
                image.setImageDrawable(getResources().getDrawable(R.drawable.bus));
                break;

            default:
                content.setText("This is the default section.");
                image.setImageDrawable(getResources().getDrawable(R.drawable.bus));
                break;
        }
        return rootView;
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
