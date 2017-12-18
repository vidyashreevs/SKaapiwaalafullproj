package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Operatorlogin extends Activity {
    Button btnoplogin;
    EditText editTextUserNameop, editTextPasswordop;
    Button btnSignIn, btfr;
    DataBaseHandler db = null;
    TextView forrr;
    TextToSpeech t1;
    private SQLiteDatabase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operatorlogin);
//initialising the databaase haandler
        db = new DataBaseHandler(Operatorlogin.this);
        db.getWritableDatabase();

        //   pullout();
        editTextUserNameop = (EditText) findViewById(R.id.etoprname);
        editTextPasswordop = (EditText) findViewById(R.id.etpaswdopr);
        forrr = (TextView) findViewById(R.id.textViewforgotpaswd);
        btnoplogin = (Button) findViewById(R.id.buttonoperlogin);
        Button bback = (Button) findViewById(R.id.buttonback);
        btfr = (Button) findViewById(R.id.btforgt);
        editTextUserNameop.clearFocus();
        editTextPasswordop.clearFocus();
//making hint to null when focussed
       editTextUserNameop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextUserNameop.setHint("");
                else
                    editTextUserNameop.setHint("USERNAME");
            }
        });

        editTextPasswordop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextPasswordop.setHint("");
                else
                    editTextPasswordop.setHint("PASSWORD");
            }
        });


//back button click event

        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Operatorlogin.this, Logins.class);
                startActivity(i);
                finish();
            }
        });


        btnoplogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            String storedpass = null;
                            String login = null;
                            String nam = null;
                            String mobile = null;
                            String u1 = editTextUserNameop.getText().toString();
                            String p1 = editTextPasswordop.getText().toString();

                           dataBase = db.getWritableDatabase();
                            Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                                    + DataBaseHandler.TABLE_NAME, null);
                            if (mCursor.moveToFirst()) {
                                do {
                                    if( mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE)).equals("O")) {
                                        login = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                                        nam = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.USERNAME));
                                        storedpass = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.PASSWORD));
                                    }else {

                                    }
                               } while (mCursor.moveToNext());
                                //do above till data exhausted
                            }
                                if (nam.equals(u1) && storedpass.equals(p1) && login.equals("O")) {
                                    //  if (num1.contains(u1) && password.contains(p1) && login.equals("A")) {
                                    Intent i = new Intent(Operatorlogin.this, Operatorsettings.class);
                                    startActivity(i);
                                    finish();

                                } else if (!nam.equals(u1)||!login.equals("O")) {
                                    Toast.makeText(getApplicationContext(), "Username and password doesnot match", Toast.LENGTH_SHORT).show();

                                } else if (!storedpass.equals(p1)) {
                                    Toast.makeText(getApplicationContext(), "Password doesnot match", Toast.LENGTH_SHORT).show();

                                } else if (!storedpass.equals(p1) || !nam.equals(u1)) {
                                    Toast.makeText(getApplicationContext(), "Password doesnot match", Toast.LENGTH_SHORT).show();

                                }

                        } catch (Exception ee) {
                            ee.printStackTrace();
                            Log.d("TAG", ee.toString());
                        }

                    }
                });

        btfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//making aalert dialogue apper
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Operatorlogin.this);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure to send sms ");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();


                                try {

                                    String mob1 = null;
                                    String nam1 = null;
                                    String storedpass1 = null;
                                    String login1 = null;


                                    Cursor rs = db.getData1("O");
                                    //  rs=db.getDatalogintype(u1);
                                    //   Log.d("TAG",String.valueOf(rs));
                                    if (rs !=null) {
                                        nam1 = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
                                        login1 = rs.getString(rs.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                                        mob1 = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
                                        storedpass1 = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));
                                        final String finalMob = mob1;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Toast.makeText(Operatorlogin.this, "message deliverd " , Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                    //  Log.d("TAG", String.valueOf(storedpass));


                                    if (login1.equals("O")&&mob1!= null) {

//sending sms to the registerd mobile number
                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(mob1, null, "Username :  " + nam1 + "\n" + "Password  :  " + storedpass1, null, null);
                                            Toast.makeText(getApplicationContext(), "SMS Sent!",
                                                    Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            speech();
                                            Toast.makeText(getApplicationContext(),"Mobile number not registed please contact admin",Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }


                                    } else {
                                        //   Toast.makeText(getApplicationContext(), "Mobile number not inserted please contact admin", Toast.LENGTH_LONG).show();

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
        Intent i = new Intent(Operatorlogin.this, Logins.class);
        startActivity(i);
        finish();
    }
    //if mobile number not registed voice speech
    public void speech(){

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    String toSpeak ="Mobile number not registed please contact admin";
                    // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }


//    public void pullout() {
//
//        //db.open();
//
//        File f = new File("/data/data/" + this.getPackageName() + "/databases/" + "login" + ".db"/*"SecurityServer.db"*/);
//        FileInputStream fin = null;
//        FileOutputStream fout = null;
//        try {
//            fin = new FileInputStream(f);
//            fout = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + "sowmya.db");
//            int i = 0;
//            while ((i = fin.read()) != -1) {
//                fout.write(i);
//            }
//
//            fout.flush();
//            Toast.makeText(this.getApplicationContext(), "dump created!!", Toast.LENGTH_LONG).show();
//            // db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this.getApplicationContext(), "exception occurs in creating dump!!", Toast.LENGTH_LONG)
//                    .show();
//        }
//
//    }

}


