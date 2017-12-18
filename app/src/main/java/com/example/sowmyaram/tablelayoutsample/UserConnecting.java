package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sowmyaram on 4/24/2017.
 */

public class UserConnecting extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_connecting);

        Button bmasteruser = (Button) findViewById(R.id.buttonmasteruser);
        Button btoday = (Button) findViewById(R.id.buttontodayuser);

        Button bdown = (Button) findViewById(R.id.buttondownloaduser);
        Button bback = (Button) findViewById(R.id.buttonback);

        bmasteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserConnecting.this,MasterCountsetting.class);
                startActivity(i);
                finish();
            }
        });


        btoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(UserConnecting.this,Totalcountsetting.class);
                startActivity(i1);
                finish();
            }
        });


        bdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                                Intent i4 = new Intent(UserConnecting.this, DownloadUser.class);
                                startActivity(i4);
                                finish();

            }
        });

        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserConnecting.this, Logins.class);
                startActivity(i);
                finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Logins.class);
        startActivity(i);
        finish();
    }
}

