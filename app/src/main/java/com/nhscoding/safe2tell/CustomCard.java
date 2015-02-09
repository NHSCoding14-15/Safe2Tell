package com.nhscoding.safe2tell;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by davidkopala on 2/8/15.
 */
public class CustomCard extends View {

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.customCard,
                0, 0);

        try {
            mtext = a.getText(R.styleable.customCard_text, false);

        } finally {
            a.recycle();
        }
    }

    public CustomCard(Context context) {
        super(context);
    }

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

