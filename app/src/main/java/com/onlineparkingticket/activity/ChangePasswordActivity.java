package com.onlineparkingticket.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.CommonUtils;

@SuppressWarnings("All")
public class ChangePasswordActivity extends BaseActivity {

    public static Context mContext;
    private EditText edOld, edNew, edConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mContext = ChangePasswordActivity.this;
        init(ChangePasswordActivity.this);
        setHeaderWithBack(getString(R.string.change_password), true, true);

        txt_right_header.setImageResource(R.drawable.done);
        txt_right_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidField()){
                    Toast.makeText(ChangePasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    ChangePasswordActivity.this.finish();
                }
            }
        });


        init();
    }

    private void init() {
        edOld = (EditText) findViewById(R.id.ed_ChangePassword_Old);
        edNew = (EditText) findViewById(R.id.ed_ChangePassword_New);
        edConfirm = (EditText) findViewById(R.id.ed_ChangePassword_Confirm);
    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edOld.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_password));
            return false;
        } else if (!CommonUtils.isTextAvailable(edNew.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_confirm_password));
            return false;
        } else if (!edNew.getText().toString().equalsIgnoreCase(edConfirm.getText().toString())) {
            CommonUtils.commonToast(this, getString(R.string.validate_password));
            return false;
        } else
            return true;
    }
}
