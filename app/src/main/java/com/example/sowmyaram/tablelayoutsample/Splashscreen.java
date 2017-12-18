package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class Splashscreen extends Activity {

    DataBaseHandler db = null;
    BluetoothAdapter BA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        db = new DataBaseHandler(Splashscreen.this);
        db.getWritableDatabase();

       /* db.insertDataAdmin("admin","8051","","A");
        db.insertDataAdmin("admin","8051","","O");
        db.insertDataAdmin("admin","8051","","U");
*/
        Toast.makeText(Splashscreen.this, "Connecting...", Toast.LENGTH_LONG).show();

        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(3500);

                    int row = db.numberOfRows();

                    if(row==0){
                        boolean isInserted12 = db.insertDataAdmin("admin", "8051","","A");
                        boolean isInserted22 =db.insertDataAdmin("admin","8051","","O");
                        boolean isInserted2 = db.insertDataAdmin( "admin","8051","","U");

                        if ((isInserted2 ==true)&& isInserted12 ==true && isInserted22 ==true)
                        {
                            Intent i = new Intent(Splashscreen.this, Logins.class);
                            startActivity(i);
                        }
                    }else{
                        Intent i1 = new Intent(Splashscreen.this,Logins.class);
                        startActivity(i1);
                    }

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();

    }


}











