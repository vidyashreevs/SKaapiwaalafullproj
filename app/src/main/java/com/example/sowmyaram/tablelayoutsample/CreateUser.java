package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sowmyaram on 4/24/2017.
 */

public class CreateUser extends Activity {
    Button btnuserlogin;
    EditText etuser,etuserpass,etusermobil;
    DataBaseHandler db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
//initialisng the ID
        etuser=(EditText)findViewById(R.id.etusername);
        etuserpass=(EditText)findViewById(R.id.etpassuser);
        etusermobil=(EditText)findViewById(R.id.etusermobil);
        Button bback = (Button) findViewById(R.id.buttonback);
        db = new DataBaseHandler(CreateUser.this);
        db.getWritableDatabase();
        // db.usertpe("Admin");
//clearing focus of the edit text
        etuser.clearFocus();
        etuserpass.clearFocus();
        etusermobil.clearFocus();



        final String userNameuser=etuser.getText().toString();
        final String passworduser=etuserpass.getText().toString();
        final String mobileuser=etusermobil.getText().toString();

        String opnam=null;
        String usphone=null;
        String opstoredpass=null;

        //retriving the user data from database
        Cursor rs = db.getData1("U");
        usphone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
        opnam = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
        opstoredpass = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));

        etuser.setText(opnam);
        etuserpass.setText(opstoredpass);
        etusermobil.setText(usphone);




//making hint to null when focussed
       /* etuser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    etuser.setHint("");
                else
                    etuser.setHint("USERNAME");
            }
        });

        etuserpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    etuserpass.setHint("");
                else
                    etuserpass.setHint("PASSWORD");
            }
        });

        etusermobil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    etusermobil.setHint("");
                else
                    etusermobil.setHint("MOBILE NUMBER");
            }
        });

*/

//back button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateUser.this, Users.class);
                startActivity(i);
                finish();
            }
        });






        Button btnadminlogin = (Button) findViewById(R.id.btnsaveuser);
        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//storing the values of eddittexxt
                String userNameuser=etuser.getText().toString();
                String passworduser=etuserpass.getText().toString();
                String mobileuser=etusermobil.getText().toString();

                String opnam=null;
                String usphone=null;
                String opstoredpass=null;

                //retriving the user data from database
                Cursor rs = db.getData1("U");
                usphone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));

                if (!userNameuser.equals("") && !passworduser.equals("") ) {

                    boolean isInserted1 = db.updateData(userNameuser, passworduser, mobileuser, "U");
                    Log.d("TAG", String.valueOf(isInserted1));
                    Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(CreateUser.this,  Users.class);
                    startActivity(i);
                    finish();

                } else {
                    //  Toast.makeText(getApplicationContext(), "Username and Password doesnot match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //on click of default back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Users.class);
        startActivity(i);
        finish();
    }

}

