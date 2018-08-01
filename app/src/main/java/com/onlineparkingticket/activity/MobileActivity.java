package com.onlineparkingticket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextBold;

public class MobileActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditTextBold edtMobile;
    private LinearLayout linearPleaGuiltyNext;
    public static MobileActivity activity;
    Bundle b;
    private String activty;
    private String redirect ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        activity =this;
        init(activity);
        b = new Bundle();
        b = getIntent().getExtras();
        if ( b !=null)
        {
            activty = b.getString("activty");
            redirect = b.getString("redirect");
            setHeaderWithBack(activty,true,false);
            findViews();
        }
        else
        {
            setHeaderWithBack(getResources().getString(R.string.signup),true,false);
        }

        findViews();
        setAction();
    }

    private void setAction() {
        linearPleaGuiltyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(activity,OTPActivity.class);
                i.putExtra("activty",activty);
                i.putExtra("redirect",redirect);
                startActivity(i);


                    // getLogin();

            }
        });
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-07-31 15:03:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        toolbar = (Toolbar)findViewById( R.id.toolbar );
        edtMobile = (EditTextBold)findViewById( R.id.edt_mobile );
        linearPleaGuiltyNext = (LinearLayout)findViewById( R.id.linear_PleaGuilty_Next );
    }

}
