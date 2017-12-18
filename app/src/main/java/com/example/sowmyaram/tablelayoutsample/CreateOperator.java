package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sowmyaram on 4/24/2017.
 */

public class CreateOperator extends Activity {
    Button btnadminlogin;
    EditText etopername,etopepass,etopemobil;
    DataBaseHandler db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operator_create);
//initialising  ID
        etopername=(EditText)findViewById(R.id.etusernameoper);
        etopepass=(EditText)findViewById(R.id.etcreateope);
        etopemobil=(EditText)findViewById(R.id.etmobilenooper);
        btnadminlogin = (Button) findViewById(R.id.btnopersave);
        Button bback = (Button) findViewById(R.id.buttonback);
//clearing the cursor focus
        etopername.clearFocus();
        etopepass.clearFocus();
        etopemobil.clearFocus();
//initialising the database handler
        db = new DataBaseHandler(CreateOperator.this);
        db.getWritableDatabase();




        final String userNameopr=etopername.getText().toString();
        final String passwordopr=etopepass.getText().toString();
        final String mobileopr=etopemobil.getText().toString();

        String opnam=null;
        String opphone=null;
        String opstoredpass=null;

        //initialising cursor and getting operator data from database
        Cursor rs = db.getData1("O");
        opphone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
        opnam = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
        opstoredpass = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));

        etopername.setText(opnam);
        etopepass.setText(opstoredpass);
        etopemobil.setText(opphone);


//making hint null when focussed
       /* etopername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    etopername.setHint("");
                else
                    etopername.setHint("USERNAME");
            }
        });

        etopepass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    etopepass.setHint("");
                else
                    etopepass.setHint("PASSWORD");
            }
        });
        etopemobil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    etopemobil.setHint("");
                else
                    etopemobil.setHint("MOBILE NUMBER");
            }
        });*/

//back button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateOperator.this, Users.class);
                startActivity(i);
                finish();
            }
        });





        //button click event
        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userNameopr=etopername.getText().toString();
                String passwordopr=etopepass.getText().toString();
                String mobileopr=etopemobil.getText().toString();

                String opnam=null;
                String opphone=null;
                String opstoredpass=null;

                //initialising cursor and getting operator data from database
                Cursor rs = db.getData1("O");
                opphone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));

                if (!userNameopr.equals("") && !passwordopr.equals("")){// && !opphone.equals(mobileopr) ) {

                    boolean isInserted1 = db.updateData(userNameopr, passwordopr, mobileopr, "O");
                    if(isInserted1) {
                        Log.d("TAG", String.valueOf(isInserted1));
                        Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(CreateOperator.this, Users.class);
                        startActivity(i1);
                        finish();
                    }
                } else {
                    //  Toast.makeText(getApplicationContext(), "Username and Password doesnot match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //default back button click
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Users.class);
        startActivity(i);
        finish();
    }

}

