/*
package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

*/
/**
 * Created by sowmyaram on 4/20/2017.
 *//*


public class Userlogin extends Activity {
    Button btnmaster,btntable,btnsettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);

        //  Typeface mytypeface=Typeface.createFromAsset(getAssets(),"timesnew.ttf");
        //Typeface mytypeface1=Typeface.createFromAsset(getAssets(),"times.ttf");
        //btnmaster.setTypeface(mytypeface);
        // btntable.setTypeface(mytypeface1);
        //  btnsettings.setTypeface(mytypeface);

        Button btnmaster = (Button) findViewById(R.id.buttonmaster);
      //  Button btntable = (Button) findViewById(R.id.buttontotal);
      //  Button btnsettings = (Button) findViewById(R.id.buttonsetting);
        btnmaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Userlogin.this,MasterCountsetting.class);
                startActivity(i);
            }
        });


        btntable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Userlogin.this,Totalcountsetting.class);
                startActivity(i1);
            }
        });


        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Userlogin.this,Settingsmenuuser.class);
                startActivity(i2);
            }
        });

    }


}



*/
