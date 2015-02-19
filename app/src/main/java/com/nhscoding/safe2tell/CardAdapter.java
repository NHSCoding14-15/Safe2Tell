package com.nhscoding.safe2tell;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by davidkopala on 2/16/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public static final String TAG = "CardAdapter";
    public static Context mContext;

    private CustomCard[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CustomCard card = new CustomCard(mContext);
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            card = (CustomCard) v.findViewById(R.id.recyclerCard);
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

        public View getView(){
            return view;
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
        viewHolder.getCard().setText(dataset[i].getText());
        viewHolder.getCard().setText(dataset[i].mTitleText);
        viewHolder.getCard().setTextSize(dataset[i].getTextSize());
        viewHolder.getCard().setTitleSize(dataset[i].getTitleSize());
        //viewHolder.getCard().setPadding(0, 15, 0, 10);
        return;
    }

    @Override
    public int getItemCount() {
        return dataset.length;
        //return 1;
    }
}
