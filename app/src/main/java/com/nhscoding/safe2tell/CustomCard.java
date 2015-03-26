package com.nhscoding.safe2tell;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidkopala on 2/13/15.
 */
public class CustomCard extends View {

    String mText = "Please Add Content";
    int mTextColor = 0;
    float mTextSize = 0.0f;
    float textPosX;
    float textPosY;
    Paint mTextPaint;

    String mTitleText = "Please Add A Title";
    float mTitleSize = 0.0f;
    float titlePosX;
    float titlePosY;
    Paint mTitlePaint;

    int logoType = -1;
    Drawable mLogoDrawable;
    String mLogoString;

    float paddingLeft;
    float paddingRight;
    float paddingTop;
    float paddingBottom;

    String[] textArray;

    int width = 0;
    int height = 0;

    Paint mLinePaint;
    Paint bgPaint;

    int triangleSize = 50;

    int bgColor;

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Leave Transparent
        //setBackgroundColor(Color.parseColor("#ffffff"));

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomCardView,
                0, 0
        );

        try {
            //Get Values From The XML File
            mText = a.getString(R.styleable.CustomCardView_txt);
            mTextColor = a.getInt(R.styleable.CustomCardView_txtColor, /*Default Value*/Color.parseColor("#000000"));
            mTextSize = a.getDimension(R.styleable.CustomCardView_txtSize, 0.0f);

            mTitleText = a.getString(R.styleable.CustomCardView_titleTxt);
            mTitleSize = a.getDimension(R.styleable.CustomCardView_titleSize, 0.0f);

            logoType = a.getInt(R.styleable.CustomCardView_logoType, -1);
            mLogoString = a.getString(R.styleable.CustomCardView_logoLocation);
            mLogoDrawable = a.getDrawable(R.styleable.CustomCardView_logoDrawable);

            bgColor = a.getColor(R.styleable.CustomCardView_bgColor, Color.parseColor("#DFE2E6"));
        } finally {
            a.recycle();
        }
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight() - triangleSize, bgPaint);
        Point[] points = new Point[4];
        //Upper Left
        points[0] = new Point();
        points[0].x = getWidth() - triangleSize - triangleSize;        //X
        points[0].y = getHeight() - triangleSize;       //Y
        //Upper Right
        points[1] = new Point();
        points[1].x = getWidth() - triangleSize;
        points[1].y = getHeight() - triangleSize;
        //Bottom Right
        points[2] = new Point();
        points[2].x = getWidth() - triangleSize;
        points[2].y = getHeight();
        //Return To Start; Close It
        points[3] = new Point();
        points[3].x = getWidth() - triangleSize - triangleSize;
        points[3].y = getHeight() - triangleSize;

        Path path = new Path();
        path.moveTo(points[0].x, points[0].y);
        path.lineTo(points[1].x, points[1].y);
        path.lineTo(points[2].x, points[2].y);
        path.lineTo(points[3].x, points[3].y);
        path.close();
        canvas.drawPath(path, bgPaint);

        //Draw Content Text
        for (int i = 0; i < textArray.length; i++) {
            String text = (String) textArray[i];
            float posY = i * (mTextSize + 5) + textPosY;
            if (text != null) {
                canvas.drawText((String) textArray[i], textPosX, posY, mTextPaint);
            }
        }
        //Draw Title Text
        canvas.drawText(mTitleText, titlePosX, titlePosY, mTitlePaint);
        //Underline Title
        canvas.drawLine(titlePosX, titlePosY + 10, getWidth() - 50, titlePosY + 10, mLinePaint);
    }

    public CustomCard(Context context) {
        super(context);
        init();
    }

    public void setTitle(String text) {
        mTitleText = text;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setText(String text) {
        mText = text;
        invalidate();
        requestLayout();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float size) {
        mTextSize = size;
    }

    public float getTitleSize() {
        return mTitleSize;
    }

    public void setTitleSize(float size) {
        mTitleSize = size;
    }

    public String getText() {
        return mText;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        //Refresh the View
        invalidate();
        requestLayout();
    }

    public void replace(CustomCard card) {
        mText = card.mText;
        mTextColor = card.mTextColor;
        mTextSize = card.mTextSize;
        textPosX = card.textPosX;
        textPosY = card.textPosY;
        mTextPaint = card.mTextPaint;

        mTitleText = card.mTitleText;
        mTitleSize = card.mTitleSize;
        titlePosX = card.titlePosX;
        titlePosY = card.titlePosY;
        mTitlePaint = card.mTitlePaint;

        logoType = card.logoType;
        mLogoDrawable = card.mLogoDrawable;
        mLogoString = card.mLogoString;

        paddingLeft = card.paddingLeft;
        paddingRight = card.paddingRight;
        paddingTop = card.getPaddingTop();
        paddingBottom = card.paddingBottom;

        textArray = card.textArray;

        width = card.width;
        height = card.height;

        mLinePaint = card.mLinePaint;

        //Update Properties
        init();

        invalidate();
        requestLayout();
    }

    public int getTextColor() {
        return mTextColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = 0;
        //Get Measured Width
        width = MeasureSpec.getSize(widthMeasureSpec);

        //Add A Margin of 60
        int adjWidth = width - 60;

        //Dynamically Calculating the Height Based on The Content Text
        String tempText = "";
        char[] text = mText.toCharArray();
        textArray = new String[(int) (mTextPaint.measureText(mText) / adjWidth) + 2];
        int position = -1;

        try {
            for (int i = 0; i < text.length; i++) {
                char aText = text[i];
                tempText += aText;
                int room = (int) (adjWidth - mTextPaint.measureText(tempText));
                int size = (int) mTextPaint.measureText("_______");
                if ((room < size) && (aText == ' ')) {
                    position++;
                    textArray[position] = tempText;
                    tempText = "";
                    height += mTextSize + 5;
                } else {
                    if ((i + 1) == text.length) {
                        position++;
                        textArray[position] = tempText;
                        tempText = "";
                        height += mTextSize + 5;
                    }
                }/*
                int testWidth = (int) mTextPaint.measureText(tempText + aText);
                if (testWidth > adjWidth) {
                    position++;
                    textArray.add(tempText);
                    tempText = "";
                    tempText += aText;
                    height += mTextSize + 5;
                } else {
                    tempText += aText;
                    if ((i + 1) == text.length) {
                        textArray.add(tempText);
                        height += mTextSize + 5;
                    }
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Add Padding To The Width
        width = width + (getPaddingLeft() + getPaddingRight());
        //Add Padding And Title To The Height
        height = (int) ((height) + (mTitleSize) + 40 + getPaddingBottom() + getPaddingTop()) + triangleSize;

        //Update The Height And Width
        //This is what directly sets the dimensions
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {

        //Get Padding
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();

        //Set Title Position
        titlePosX = paddingLeft + 30.0f;
        titlePosY = paddingTop + mTitleSize + 10.0f;

        //Set Text Position
        textPosX = paddingLeft + 30.0f;
        textPosY = titlePosY + mTextSize + 30.0f;

        //Set Text Properties
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        //Set Title Properties
        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setStyle(Paint.Style.FILL);
        mTitlePaint.setTextSize(mTitleSize);

        //Set Underline Properties
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(getResources().getColor(R.color.material_blue_grey_800));

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(bgColor);
    }
}
