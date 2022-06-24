package com.bhoomikabihar.surveyapp.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class HindiTextView extends AppCompatTextView {
    public HindiTextView(Context context) {
        super(context);
    }

    public HindiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HindiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initTypeFaces(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "noto_sans_regular.ttf");

    }
}
