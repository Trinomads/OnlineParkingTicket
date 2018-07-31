package com.onlineparkingticket.commonTextView;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextBold extends EditText {
    public EditTextBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.otf");
        setTypeface(tf);

    }

}
