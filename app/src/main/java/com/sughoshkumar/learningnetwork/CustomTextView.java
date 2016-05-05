package com.sughoshkumar.learningnetwork;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CustomTextView extends AppCompatTextView {

    Context mContext;
    public CustomTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        Typeface fontFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/sans-serif-light.ttf");
        setTypeface(fontFace);
    }
}
