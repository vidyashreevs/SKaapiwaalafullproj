package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static com.example.sowmyaram.tablelayoutsample.DataBaseHandler.db;

public class Adminlogin extends Activity {
    Button btnadminlogin;
    EditText editTextUserName, editTextPassword, editTextmobilenumber;
    DataBaseHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        //initialising ID's for the edittext and buttons
        editTextUserName = (EditText) findViewById(R.id.etusernamecretadmin);
        editTextPassword = (EditText) findViewById(R.id.etpaswdadmicreate);
        editTextmobilenumber = (EditText) findViewById(R.id.etmobilenocreteadmn);
        Button btnadminlogin = (Button) findViewById(R.id.buttonadminlogin);
        Button bback = (Button) findViewById(R.id.buttonback);

        editTextUserName.clearFocus();
        editTextPassword.clearFocus();
        editTextmobilenumber.clearFocus();

        //creating object to handler
        db = new DataBaseHandler(Adminlogin.this);
        db.getWritableDatabase();

        final String userName = editTextUserName.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String mobile = editTextmobilenumber.getText().toString();

        String nam = null;
        String phone = null;
        String storedpass = null;

        //getting data(mobile number) of Admin(A) from database
        Cursor rs = db.getData1("A");
        phone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
        nam = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
        storedpass = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));

        editTextUserName.setText(nam);

        editTextPassword.setText(storedpass);
        editTextmobilenumber.setText(phone);



//making hint disappear when click


       /* editTextUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextUserName.setHint("");
                else
                    editTextUserName.setHint("USERNAME");
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextPassword.setHint("");
                else
                    editTextPassword.setHint("PASSWORD");
            }
        });

        editTextmobilenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextmobilenumber.setHint("");
                else
                    editTextmobilenumber.setHint("MOBILE NUMBER");
            }
        });*/

        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextmobilenumber.setHint("");
                editTextUserName.setHint("");
                editTextPassword.setHint("");
                Intent i = new Intent(Adminlogin.this, Users.class);
                startActivity(i);
                finish();

            }
        });



        //button click event

        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //storing edittext values to string
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String mobile = editTextmobilenumber.getText().toString();

                String nam = null;
                String phone = null;
                String storedpass = null;

                //getting data(mobile number) of Admin(A) from database
                Cursor rs = db.getData1("A");
                phone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
                //checking et credentials
                if (!userName.equals("") && !password.equals("")){// && !phone.equals(mobile)  ) {

                    //updating data into database
                    boolean isInserted1 = db.updateData(userName, password, mobile, "A");
                    if (isInserted1) {
                        Log.d("TAG", String.valueOf(isInserted1));
                        Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Adminlogin.this, Users.class);
                        startActivity(i);
                        finish();
                    }
                }

                else {
                    //   Toast.makeText(getApplicationContext(), "Username and Password doesnot match", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Users.class);
        startActivity(i);
        finish();
    }


}

