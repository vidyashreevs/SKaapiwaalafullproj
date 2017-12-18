package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;



public class Users extends Activity {
    Button buser,boperatr,badmin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);
        badmin= (Button) findViewById(R.id.buttonAdmin1);
        boperatr= (Button) findViewById(R.id.buttonoperator1);
        buser= (Button) findViewById(R.id.buttonuser1);
        Button bback = (Button) findViewById(R.id.buttonback);


        badmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Users.this,Adminlogin.class);
                startActivity(i1);
                finish();
            }
        });


        boperatr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Users.this,CreateOperator.class);
                startActivity(i3);
                finish();
            }
        });

        buser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(Users.this,CreateUser.class);
                startActivity(i4);
                finish();
            }
        });


        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Users.this, Settingsadmin.class);
                startActivity(i);
                finish();
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Settingsadmin.class);
        startActivity(i);
        finish();
    }
}
