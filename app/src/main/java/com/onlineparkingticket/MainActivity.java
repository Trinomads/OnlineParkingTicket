package com.onlineparkingticket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

@SuppressWarnings("All")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*TextView tvClick = (TextView) findViewById(R.id.tv_Main_Click);
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PleaActivity.class));
            }
        });

        TextView tvDigitalWallet = (TextView) findViewById(R.id.tv_Main_DigitalWallet);
        tvDigitalWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DigitalWalletActivity.class));
            }
        });

        TextView tvFragment = (TextView) findViewById(R.id.tv_Main_Fragment);
        tvFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentResolvedTicket());
            }
        });

        TextView tvPending = (TextView) findViewById(R.id.tv_Main_Pending);
        tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentPendingTicket());
            }
        });

        TextView tvNotification = (TextView) findViewById(R.id.tv_Main_Notification);
        tvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });

        TextView tvSettings = (TextView) findViewById(R.id.tv_Main_Settings);
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentSettings());
            }
        });*/
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_container, fragment).addToBackStack(null).commit();
    }
}
