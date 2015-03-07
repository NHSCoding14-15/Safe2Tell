package com.nhscoding.safe2tell;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by davidkopala on 2/6/15.
 */
public class LEARN extends Fragment {


    View rootview;

    private OnFragmentInteractionListener mListener;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_learn, container, false);
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

    public static LEARN newInstance() {
        LEARN fragment = new LEARN();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;


    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



//public class Stuff extends Activity {
//    Button submit_tip = (Button) findViewById(R.id.submit_tip);
//    submit_tip.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View v) {
//            Intent intent = new Intent(v.getContext(), SUBMIT_TIP.class);
//            startActivityForResult(intent,0);
//        }
}
