package com.onlineparkingticket.commonTextView;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextBlack extends EditText {
    public EditTextBlack(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextBlack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextBlack(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Black.otf");
        setTypeface(tf);

    }

}
