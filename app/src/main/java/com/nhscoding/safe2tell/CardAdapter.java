package com.nhscoding.safe2tell;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by davidkopala on 2/16/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public static final String TAG = "CardAdapter";
    public static Context mContext;

    private CustomCard[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CustomCard card = new CustomCard(mContext);

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked");
                }
            });
        }

        public CustomCard getCard() {
            return card;
        }

        public void replaceCard(CustomCard customCard) {
            card = customCard;
        }
    }

    public CardAdapter(CustomCard[] dataSet, Context context) {
        dataset = dataSet;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_card, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.d(TAG, "Element " + i + " set");
        viewHolder.replaceCard(dataset[i]);
        return;
    }

    @Override
    public int getItemCount() {
        return dataset.length;
        //return 1;
    }
}
