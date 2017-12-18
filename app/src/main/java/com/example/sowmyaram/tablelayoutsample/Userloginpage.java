/*
package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class Userloginpage extends Activity {
    Button btnlogin;
    EditText editTextUserNameop, editTextPasswordop;
    Button btnSignIn, btnSignUp,btnforuser;
    DataBaseHandler db = null;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login1);
//initialisng daatabase
        db = new DataBaseHandler(Userloginpage.this);
        db.getWritableDatabase();
        //   pullout();
        editTextUserNameop = (EditText) findViewById(R.id.etusername);
        editTextPasswordop = (EditText) findViewById(R.id.etpaswd);
        editTextUserNameop.clearFocus();
        editTextPasswordop.clearFocus();

        //  Typeface mytypeface=Typeface.createFromAsset(getAssets(),"timesnew.ttf");
        //   etpaswd.setTypeface(mytypeface);
        Button bback = (Button) findViewById(R.id.buttonback);
//seting hint to null when focus
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


        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Userloginpage.this, Logins.class);
                startActivity(i);
                finish();
            }
        });




        btnlogin = (Button) findViewById(R.id.buttonuserlogin);
        btnforuser = (Button) findViewById(R.id.btnforuser);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String storedpass1 = null;
                    String phone = null;
                    String logtype =null;
                    String uop1 = editTextUserNameop.getText().toString();
                    String oppass1= editTextPasswordop.getText().toString();
                    // String storedpass = db.get_pass();
//retriving the data of the user from databse
                    Cursor rs = db.getData(uop1);
                    if (rs != null) {
                        String nam = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
                        phone = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
                        storedpass1 = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));
                        logtype = rs.getString(rs.getColumnIndex(DataBaseHandler.LOGIN_TYPE));

                    }

                    Log.d("TAG", String.valueOf(storedpass1));




                    if (oppass1.equals(storedpass1)&& logtype.equals("U")) {
                        Intent i = new Intent(Userloginpage.this, UserConnecting.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "DATA inserted not true", Toast.LENGTH_SHORT).show();

                    }

//                Intent i = new Intent(Adminlogin1.this,Settingsadmin.class);
//                startActivity(i);
                }catch (Exception ee){
                    ee.printStackTrace();
                    Log.d("TAG", ee.toString());
                }

            }
        });


        btnforuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//initalising alert dialouge

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Userloginpage.this);
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

                                    String mobus = null;
                                    String namus = null;
                                    String storedpassus = null;
                                    String loginus = null;



                                    //   String f1 = forr.getText().toString();
                                    //  String p1 = editTextPassword.getText().toString();
                                    // String storedpass = db.get_pass();
                                    //  Cursor rs = db.getData(f1);
                                    Cursor rs = db.getData1("U");
                                    //  rs=db.getDatalogintype(u1);
                                    //   Log.d("TAG",String.valueOf(rs));
                                    if (rs.getCount()>0 ) {
                                        namus = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
                                        loginus = rs.getString(rs.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                                        mobus = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
                                        storedpassus = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));
                                        final String finalMob = mobus;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Toast.makeText(Userloginpage.this,"message deliverd ",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                    //  Log.d("TAG", String.valueOf(storedpass));


                                    if (loginus.equals("U") && mobus !=null) {



                                    //sending sms to mobile number
                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(mobus, null,"Username :  "+namus+"\n"+"Password  :  "+storedpassus , null,null);
                                            Toast.makeText(Userloginpage.this,"SMS sent ",Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {

                                            speech();
                                            Toast.makeText(getApplicationContext(),"Mobile number not registed please contact admin",Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }



                                        //  Intent i = new Intent(Forgotpass.this, Settingsadmin.class);
                                        //  startActivity(i);

                                    } else {
                                        //   Toast.makeText(getApplicationContext(), "mobile number not registerdplease contact admin", Toast.LENGTH_LONG).show();

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
        Intent i = new Intent(getApplicationContext(), Logins.class);
        startActivity(i);
        finish();
    }
//setting speech when mobile number not registed
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
//        File f = new File("/data/data/" + this.getPackageName() + "/databases/" + "login" + ".db"*/
/*"SecurityServer.db"*//*
);
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


*/
package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class Userloginpage extends Activity {
    Button btnlogin;
    EditText editTextUserNameop, editTextPasswordop;
    Button btnSignIn, btnSignUp, btnforuser;
    DataBaseHandler db = null;
    TextToSpeech t1;
    private SQLiteDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login1);
//initialisng daatabase
        db = new DataBaseHandler(Userloginpage.this);
        db.getWritableDatabase();
        //   pullout();
        editTextUserNameop = (EditText) findViewById(R.id.etusername);
        editTextPasswordop = (EditText) findViewById(R.id.etpaswd);
        editTextUserNameop.clearFocus();
        editTextPasswordop.clearFocus();

        //  Typeface mytypeface=Typeface.createFromAsset(getAssets(),"timesnew.ttf");
        //   etpaswd.setTypeface(mytypeface);
        Button bback = (Button) findViewById(R.id.buttonback);
//seting hint to null when focus
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


        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Userloginpage.this, Logins.class);
                startActivity(i);
                finish();
            }
        });


        btnlogin = (Button) findViewById(R.id.buttonuserlogin);
        btnforuser = (Button) findViewById(R.id.btnforuser);
        btnlogin.setOnClickListener(new View.OnClickListener() {
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
                            if( mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE)).equals("U")) {
                            login = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                            nam = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.USERNAME));
                            storedpass = mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.PASSWORD));

                          /*  num1.add(mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.USERNAME)));
                            password.add(mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.PASSWORD)));
                            logintype.add(mCursor.getString(mCursor.getColumnIndex(DataBaseHandler.LOGIN_TYPE)));
*/
                            }else {

                            }
                        } while (mCursor.moveToNext());
                        //do above till data exhausted
                    }
                    if (nam.equals(u1) && storedpass.equals(p1) && login.equals("U")) {
                        //  if (num1.contains(u1) && password.contains(p1) && login.equals("A")) {
                        Intent i = new Intent(Userloginpage.this, UserConnecting.class);
                        startActivity(i);
                        finish();

                    } else if (!nam.equals(u1) || !login.equals("U")) {
                        Toast.makeText(getApplicationContext(), "Username and password doesnot match", Toast.LENGTH_SHORT).show();

                    } else if (!storedpass.equals(p1)) {
                        Toast.makeText(getApplicationContext(), "Password doesnot match", Toast.LENGTH_SHORT).show();

                    } else if (!storedpass.equals(p1) || !nam.equals(u1)) {
                        Toast.makeText(getApplicationContext(), "Password doesnot match", Toast.LENGTH_SHORT).show();

                    }
//                Intent i = new Intent(Adminlogin1.this,Settingsadmin.class);
//                startActivity(i);
                } catch (Exception ee) {
                    ee.printStackTrace();
                    Log.d("TAG", ee.toString());
                }

            }
        });


        btnforuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//initalising alert dialouge

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Userloginpage.this);
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

                                    String mobus = null;
                                    String namus = null;
                                    String storedpassus = null;
                                    String loginus = null;

                                    Cursor rs = db.getData1("U");
                                    //  rs=db.getDatalogintype(u1);
                                    //   Log.d("TAG",String.valueOf(rs));
                                    if (rs.getCount() > 0) {
                                        namus = rs.getString(rs.getColumnIndex(DataBaseHandler.USERNAME));
                                        loginus = rs.getString(rs.getColumnIndex(DataBaseHandler.LOGIN_TYPE));
                                        mobus = rs.getString(rs.getColumnIndex(DataBaseHandler.MOBILE_NUMBER));
                                        storedpassus = rs.getString(rs.getColumnIndex(DataBaseHandler.PASSWORD));
                                        final String finalMob = mobus;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Toast.makeText(Userloginpage.this,"message deliverd ",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                    //  Log.d("TAG", String.valueOf(storedpass));


                                    if (loginus.equals("U") && mobus != null) {


                                        //sending sms to mobile number
                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(mobus, null, "Username :  " + namus + "\n" + "Password  :  " + storedpassus, null, null);
                                            Toast.makeText(Userloginpage.this, "SMS sent ", Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {

                                            speech();
                                            Toast.makeText(getApplicationContext(), "Mobile number not registed please contact admin", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }


                                        //  Intent i = new Intent(Forgotpass.this, Settingsadmin.class);
                                        //  startActivity(i);

                                    } else {
                                        //   Toast.makeText(getApplicationContext(), "mobile number not registerdplease contact admin", Toast.LENGTH_LONG).show();

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
        Intent i = new Intent(getApplicationContext(), Logins.class);
        startActivity(i);
        finish();
    }

    //setting speech when mobile number not registed
    public void speech() {

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    String toSpeak = "Mobile number not registed please contact admin";
                    // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
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

    public void pullout() {
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


}