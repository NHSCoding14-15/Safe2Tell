package com.nhscoding.safe2tell;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by davidkopala on 3/22/15.
 */
public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ev.setLocation(ev.getY(), ev.getX());
        return super.onTouchEvent(ev);
    }

    private void init() {
        setPageTransformer(true, new PageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class PageTransformer implements ViewPager.PageTransformer {

        float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) {
                view.setAlpha(0.0f);
            } else if (position <= 0) {
                view.setAlpha(1.0f);
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                //set Y position to swipe in from top
                float yPosition = position * pageHeight;
                view.setTranslationY(yPosition);
            } else if (position <= 1) {
                view.setAlpha(1 - position);

                //Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                float yPosition = position * pageHeight;
                view.setTranslationY(yPosition);

                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0.0f);
            }
        }
    }
}
