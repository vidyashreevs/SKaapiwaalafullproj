package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import static com.example.sowmyaram.tablelayoutsample.DataBaseHandler.TABLE_NAME;
import static java.lang.System.exit;

public class Adminlogin1 extends Activity {
    Button btnadminlogin, btnforgot;
    EditText editTextUserName, editTextPassword;
    TextView tfor;
    Button btnSignIn, btnSignUp;
    DataBaseHandler db = null;
    TextToSpeech t1;
    private ArrayList<String> num1 = new ArrayList<String>();
    private ArrayList<String> password = new ArrayList<String>();
    private ArrayList<String> logintype = new ArrayList<String>();
    private ArrayList<String> status = new ArrayList<String>();
    private ArrayList<String> data = new ArrayList<String>();
    private SQLiteDatabase dataBase;
    private boolean dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin1);

//initialising database adapter
        db = new DataBaseHandler(Adminlogin1.this);
        db.getWritableDatabase();


        //   pullout();
        tfor = (TextView) findViewById(R.id.textViewforgotpaswd);
        editTextUserName = (EditText) findViewById(R.id.etusernameadmin1);
        editTextPassword = (EditText) findViewById(R.id.etpaswdadmin1);
        btnadminlogin = (Button) findViewById(R.id.buttonadminloginadmin);
        Button bback = (Button) findViewById(R.id.buttonback);
        btnforgot = (Button) findViewById(R.id.btnforgot);

        editTextUserName.clearFocus();
        editTextPassword.clearFocus();


//making the hint disaper when edittext focussed
        editTextUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

//defaulf back button presed
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Adminlogin1.this, Logins.class);
                startActivity(i);
                finish();
            }
        });


        btnadminlogin.setOnClickListener(new View.OnClickListener() {


            public String s;

            @Override
            public void onClick(View v) {
                String storedpass = null;
                String login = null;
                String nam = null;
                String mobile = null;
                String u1 = editTextUserName.getText().toString();
                String p1 = editTextPassword.getText().toString();

                dataBase = db.getWritableDatabase();
                Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                        + DataBaseHandler.TABLE_NAME, null);

                try {

                    if (mCursor.moveToFirst()) {
                        do {
                            if( mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE)).equals("A")) {
                                login = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                                nam = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.USERNAME));
                                storedpass = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.PASSWORD));

                            }
                            else {

                            }
                        } while (mCursor.moveToNext());
                        //do above till data exhausted
                    }
                    if(login.equals("A")) {
                        if (nam.equals(u1) && storedpass.equals(p1) && login.equals("A")) {
                            //  if (num1.contains(u1) && password.contains(p1) && login.equals("A")) {
                            Intent i1 = new Intent(Adminlogin1.this, Settingsadmin.class);
                            startActivity(i1);
                            finish();

                        } else if (!nam.equals(u1) || !login.equals("A")) {
                            Toast.makeText(getApplicationContext(), "Username and password doesnot match", Toast.LENGTH_SHORT).show();

                        } else if (!storedpass.equals(p1)) {
                            Toast.makeText(getApplicationContext(), "Password doesnot match", Toast.LENGTH_LONG).show();

                        } else if (!storedpass.equals(p1) || !nam.equals(u1)) {
                            Toast.makeText(getApplicationContext(), "Password doesnot match", Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Username and password doesnot match", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception ee) {
                    ee.printStackTrace();
                    Log.d("TAG", ee.toString());
                }


            }
        });

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Intent i2 = new Intent(Adminlogin1.this, Forgotpass.class);
                // startActivity(i2);
                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            t1.setLanguage(Locale.UK);
                        }
                    }
                });
//
                //initialising the alert dialouge
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Adminlogin1.this);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure to send sms ");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                try {

                                    String mobop = null;
                                    String namop = null;
                                    String storedpassop = null;
                                    String loginop = null;

                                    //cursor retriving the admin data
                                    Cursor rs = db.getData1("A");
                                    if (rs.getCount() > 0) {
                                        namop = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
                                        loginop = rs.getString(rs.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                                        mobop = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
                                        storedpassop = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));
                                        final String finalMob = mobop;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                            }
                                        });
                                    }


                                    //sending the sms to the registerd mobile number

                                    if (loginop.equals("A") && mobop != null) {

                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(mobop, null, "Username :  " + namop + "\n" + "Password  :  " + storedpassop, null, null);
                                            Toast.makeText(getApplicationContext(), "SMS Sent!",
                                                    Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {

                                            speech();

                                            Toast.makeText(getApplicationContext(), "Mobile number not registed please contact admin", Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }


                                    } else {


                                    }

                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                    Log.d("TAG", ee.toString());
                                }
                            }
                        });
                builder.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Adminlogin1.this, Logins.class);
        startActivity(i);
        finish();
    }


    //when admin not register mobile number voice speech
    public void speech() {

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    String toSpeak = "Mobile number not registed please contact admin";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

 /*  public void pullout() {

        //db.open();

        File f = new File("/data/data/" + this.getPackageName() + "/databases/" + "sample" + ".db");
     /*   FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(f);
            fout = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + "sowmya.db");
            int i = 0;
            while ((i = fin.read()) != -1) {
                fout.write(i);
            }

           fout.flush();
            Toast.makeText(this.getApplicationContext(), "dump created!!", Toast.LENGTH_LONG).show();
             db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getApplicationContext(), "exception occurs in creating dump!!", Toast.LENGTH_LONG)
                    .show();}

    }*/


}








