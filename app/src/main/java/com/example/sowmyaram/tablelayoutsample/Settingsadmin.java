package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.UUID;


public class Settingsadmin extends Activity {
    String strReceived = null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText inputField;
    byte[] bytesToSend, a2, theByteArray;
    BluetoothAdapter mBluetoothAdapter;
    private UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";

    LinearLayout lcon;
    DataBaseHandler db = null;
    String incoming_data, bytesToSend1;
    BufferedReader br;
    Button bmaster, btoday, bteraseall, bcreteuser, bdownl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsadmin);

        bmaster = (Button) findViewById(R.id.buttonmaster);
        btoday = (Button) findViewById(R.id.buttontoday);
        bteraseall = (Button) findViewById(R.id.buttoneraseall);
        bcreteuser = (Button) findViewById(R.id.butonusers);
        bdownl = (Button) findViewById(R.id.buttondown);
        Button bback = (Button) findViewById(R.id.buttonback);

        try {
            bmaster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Settingsadmin.this, Mastercount2.class);
                    startActivity(i);
                    finish();

                }
            });

            btoday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i1 = new Intent(Settingsadmin.this, Totalcount2.class);
                    startActivity(i1);
                    finish();
                }
            });


            bcreteuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i3 = new Intent(Settingsadmin.this, Users.class);
                    startActivity(i3);
                    finish();
                }
            });


            bback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Settingsadmin.this, Logins.class);
                    startActivity(i);
                    finish();
                }
            });



            bdownl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                                    Intent i4 = new Intent(Settingsadmin.this, DownloadAdmin.class);
                                    startActivity(i4);
                                    finish();
                                }
                            });


            bteraseall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i4 = new Intent(Settingsadmin.this, Eraseall.class);
                    startActivity(i4);
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
         //   Toast.makeText(Settingsadmin.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Logins.class);
        startActivity(i);
        finish();
    }
}
