package com.onlineparkingticket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextBold;
import com.onlineparkingticket.commonTextView.TextViewRegular;

public class ConfirmPasswordActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditTextBold edtPassword;
    private EditTextBold edtCpassword;
    private LinearLayout linearPleaGuiltyNext;
    public static ConfirmPasswordActivity activity;
    Bundle b;
    private String activty;
    private String redirect ="0";
    private TextViewRegular txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);
        activity =this;
        init(activity);
        b = new Bundle();
        b = getIntent().getExtras();
        if ( b !=null)
        {
            activty = b.getString("activty");
            redirect = b.getString("redirect");
            setHeaderWithBack(activty,true,false);

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
               /* if (isValidField()) {

                    startActivity(new Intent(activity, RegistrationActivity.class));
                    finish();


                    // getLogin();

                }*/


               if (redirect.equalsIgnoreCase("1"))
               {
                   startActivity(new Intent(activity, LoginActivity.class));
                   finish();
               }
               else {
                   startActivity(new Intent(activity, FinalRegistrationActivity.class));
                   finish();

               }


                // getLogin();


            }
        });

    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-07-31 15:10:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        toolbar = (Toolbar)findViewById( R.id.toolbar );
        txt_password = (TextViewRegular)findViewById( R.id.txt_password );
        edtPassword = (EditTextBold)findViewById( R.id.edt_password );
        edtCpassword = (EditTextBold)findViewById( R.id.edt_cpassword );
        linearPleaGuiltyNext = (LinearLayout)findViewById( R.id.linear_PleaGuilty_Next );

        if (redirect.equalsIgnoreCase("1"))
        {
            txt_password.setText(getResources().getString(R.string.newpassword));
        }
    }

}
