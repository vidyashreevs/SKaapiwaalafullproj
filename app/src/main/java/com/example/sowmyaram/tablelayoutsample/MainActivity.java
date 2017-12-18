package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.R.attr.name;
import static android.R.attr.password;

public class MainActivity extends AppCompatActivity {
    Button btnuserlogin;
    EditText etusername, etpaswd;
    DataBaseHandler db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_name);
        //creating id
        etusername = (EditText) findViewById(R.id.etusername);
        etpaswd = (EditText) findViewById(R.id.etpaswd);
        Button btnuserlogin = (Button) findViewById(R.id.buttonuserlogin);
//creating object for databasehelper and writing into db
        db = new DataBaseHandler(MainActivity.this);
        db.getWritableDatabase();

//checking for table row count
        int row = db.numberOfRows();
        if (row == 0)
        {

            btnuserlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s1 = etusername.getText().toString();
                    String s2 = etpaswd.getText().toString();
                  // String s3 = etpaswd.getText().toString();
                    if(s1.equals("admin")&&s2.equals("8051")) {
                     //  boolean isInserted = db.insertDataAdmin(s1, s2,"1234567890","A");
                      //  Log.d("TAG",String.valueOf(isInserted));
                       // Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, Logins.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "please type proper credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
             Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
        }



    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }




    }



