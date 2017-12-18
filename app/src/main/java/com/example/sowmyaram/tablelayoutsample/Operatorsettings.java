package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class Operatorsettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operatorseting);

        boolean intstatus=Btconnection.in();
        boolean oustats=Btconnection.out();

        Button  brewcoff1 = (Button) findViewById(R.id.buttonbrewope);
        Button  cupdelay1 = (Button) findViewById(R.id.buttoncupdelayope);
        Button  temp1 = (Button) findViewById(R.id.buttontempope);
        Button  date1 = (Button) findViewById(R.id.buttondateope);
        Button  brewcups1 = (Button) findViewById(R.id.buttonbrewcupsope);
        Button blackcoffe1 = (Button) findViewById(R.id.buttonblackcoffope);
        Button   stroncofe1 = (Button) findViewById(R.id.buttonstrngcoffeope);
        Button  coffee1 = (Button) findViewById(R.id.buttoncoffeope);
        Button  blacktea1 = (Button) findViewById(R.id.buttonblkteaope);
        Button  strongtea1 = (Button) findViewById(R.id.buttonstrngteaope);
        Button   tea1 = (Button) findViewById(R.id.buttonteaope);
        Button  milk1 = (Button) findViewById(R.id.buttonmilkope);
        Button  hotwater1 = (Button) findViewById(R.id.buttonhotwaterope);
        Button  reversemilk = (Button) findViewById(R.id.buttonreversemilkope);
        Button bback = (Button) findViewById(R.id.buttonback);
        Button  brewtea = (Button) findViewById(R.id.buttonbrewope1);


        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Operatorsettings.this, Logins.class);
                startActivity(i);
                finish();
            }
        });


        brewcoff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Operatorsettings.this,BrewCoffe.class);
                startActivity(i3);
                finish();
            }
        });


        cupdelay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(Operatorsettings.this,Cupdelay.class);
                startActivity(i4);
                finish();
            }
        });



        temp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5 = new Intent(Operatorsettings.this,Temperature.class);
                startActivity(i5);
                finish();
            }
        });

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6 = new Intent(Operatorsettings.this,Datepage.class);
                startActivity(i6);
                finish();
            }
        });

        brewcups1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i7 = new Intent(Operatorsettings.this,BrewCups.class);
                startActivity(i7);
                finish();
            }
        });

        blackcoffe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i8 = new Intent(Operatorsettings.this,Blackcofee.class);
                startActivity(i8);
                finish();
            }
        });

        stroncofe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i9 = new Intent(Operatorsettings.this,Strongcofee.class);
                startActivity(i9);
                finish();
            }
        });


        coffee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i10 = new Intent(Operatorsettings.this,Coffee.class);
                startActivity(i10);
                finish();
            }
        });



        blacktea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i11 = new Intent(Operatorsettings.this,Blacktea.class);
                startActivity(i11);
                finish();
            }
        });


        strongtea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i12 = new Intent(Operatorsettings.this,StrongTea.class);
                startActivity(i12);
                finish();
            }
        });

        tea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i13 = new Intent(Operatorsettings.this,Tea.class);
                startActivity(i13);
                finish();
            }
        });



        milk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i14 = new Intent(Operatorsettings.this,Milk.class);
                startActivity(i14);
                finish();
            }
        });


        hotwater1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i15 = new Intent(Operatorsettings.this,Hotwater.class);
                startActivity(i15);
                finish();
            }
        });

        reversemilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i16 = new Intent(Operatorsettings.this,Reversemilk.class);
                startActivity(i16);
                finish();
            }
        });

        brewtea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i16 = new Intent(Operatorsettings.this,Brewtea.class);
                startActivity(i16);
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