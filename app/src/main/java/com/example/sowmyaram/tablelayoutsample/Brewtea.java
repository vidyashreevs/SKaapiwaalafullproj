package com.example.sowmyaram.tablelayoutsample;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;




public class Brewtea extends ActionBarActivity {
    String strReceived=null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText inputField;
    Button btnSend,btnRead;
    byte[] bytesToSend,a2,theByteArray;
    LinearLayout lcon;
    private Button bset, bback, bread;
    private String bytesToSend1;
    private TextView tv1,tv2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brew_tea);
        Btconnection.handler=handler;

        //initialising ID's
        tv1= (TextView) findViewById(R.id.textView1);
        tv2= (TextView) findViewById(R.id.textView4);
        inputField = (EditText) findViewById(R.id.etbrewtime);
        bback = (Button) findViewById(R.id.buttonback);
        btnRead = (Button)findViewById(R.id.buttonread);
        lcon = (LinearLayout) findViewById(R.id.lcon);
        btnSend = (Button)findViewById(R.id.buttonset);

        //To send CON cmd and *BRC# cmd while opening brewcoffee page
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(Btconnection.btconnected=true) {
                        if (Btconnection.btconnected = true && Btconnection.bs.isConnected()) {
                            lcon.setBackgroundResource(R.drawable.kapiconect);
                        } else {
                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                        }
                    }
                    else if( Btconnection.bs.isConnected()!=true) {

                        String bytesToSend1 = "*CON#";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                    }
                    String bytesToSend1 = "*BRT#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);

                }
            });

        }catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(BrewCoffe.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }



        //SET button click event
        //Sending data to the machine
        btnSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    if (inputField.length() > 0 && inputField.getText().toString().contains(".")) {

                        String currentstring = inputField.getText().toString();
                        String[] separated = currentstring.split("\\.");
                        String part1 = separated[0];
                        String part2 = separated[1];

                        int part2int = Integer.parseInt(part2);

                        if (part2int > 59) {
                            // Toast.makeText(Brewtea.this, "Invalid", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Brewtea.this);
                            alertBuilder.setTitle("Invalid Data");
                            alertBuilder.setMessage("Please, Enter data less than 59");
                            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            alertBuilder.create().show();

                        } else {
                            String bytesToSend1 = "*BT,";
                            byte[] theByteArray = bytesToSend1.getBytes();

                            bytesToSend = inputField.getText().toString().getBytes();
                            String bytesToSend2 = "#\n";
                            byte[] theByteArray2 = bytesToSend2.getBytes();
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            try {
                                output.write(theByteArray);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                output.write(bytesToSend);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                output.write(theByteArray2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            byte[] out = output.toByteArray();
                            String data = new String(out);
                            Btconnection.sendbt(data);
                        }
                    }else {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Brewtea.this);
                        alertBuilder.setTitle("Invalid Data");
                        alertBuilder.setMessage("Please, Enter data in 00.00 format");
                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertBuilder.create().show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(BrewCoffe.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //READ buttun click event
        //To send *RA# cmd to the machine
        btnRead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    String bytesToSend1 ="*BRT#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(BrewCoffe.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BACK buttun click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(Brewtea.this, Operatorsettings.class);
                startActivity(i);
                finish();
            }
        });


        // send CON cmd if click on Kaapiwaala tab
        lcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if( Btconnection.btconnected=false) {

                                String bytesToSend1 = "*CON#";
                                byte[] theByteArray = bytesToSend1.getBytes();
                                String data = new String(theByteArray);
                                Btconnection.sendbt(data);
                            }
                            String bytesToSend1 = "*CON#";
                            byte[] theByteArray = bytesToSend1.getBytes();
                            String data = new String(theByteArray);
                            Btconnection.sendbt(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Toast.makeText(BrewCoffe.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Operatorsettings.class);
        startActivity(i);
        finish();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //To receive data from machine
    private Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    try {
                        final String message = (String) msg.obj;

                        if ((message.startsWith("*")) && (message.endsWith("#"))  && message.length() == 5) {
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    if (message.equals("*CON#")) {
                                        // Toast.makeText(BrewCoffe.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                        //Toast.makeText(BrewCoffe.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                        //Toast.makeText(BrewCoffe.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }
                                    else  if(message.equals("*BRT#")) {
                                        Toast.makeText(Brewtea.this, "Data Received", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            break;
                        }
                        else   if ((message.startsWith("*")) && (message.endsWith("#")) && message.length() == 10) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    inputField.setText(message.replaceAll("[^0-9.]", ""));

                                }

                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Toast.makeText(BrewCoffe.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };
}


