package com.example.sowmyaram.tablelayoutsample;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
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




public class Cupdelay extends AppCompatActivity {

    String strReceived = null;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetoothAdapter;
    InputStream in = null;
    EditText inputField;
    Button btnSend, btnRead, bback;
    TextView tv1, tv2;
    byte[] bytesToSend, a2;
    private UUID myUUID;
    Logins myThreadConnectBTdevice;
    Logins myThreadConnected;
    String incoming_data;
    Logins write;
    Logins success;
    OutputStream connectedOutputStream;
    Logins connectedBluetoothSocket;
    InputStream connectedInputStream;
    LinearLayout lcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cup_delay);
        Btconnection.handler = handler;

        //initialising ID's
        inputField = (EditText) findViewById(R.id.etcupdelay);
        bback = (Button) findViewById(R.id.buttonback);
        btnRead = (Button) findViewById(R.id.buttonread);
        lcon = (LinearLayout) findViewById(R.id.lcon);

        //To send *CP# cmd while opening blacktea page and if bluetooth is connected then kaapiwaala tab will turn to green
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Btconnection.btconnected = true&& Btconnection.bs.isConnected()) {
                        lcon.setBackgroundResource(R.drawable.kapiconect);
                    } else {
                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                    }

                    String bytesToSend1 = "*CP#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);


                }

            });
        } catch (Exception e) {
        e.printStackTrace();
        //Toast.makeText(Cupdelay.this, "Connection Problem", Toast.LENGTH_SHORT).show();
    }


        //making edittext to take data in 0.0 format
        inputField.setFilters(new InputFilter[] {
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 1, afterDecimal = 1;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = inputField.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.0";
                        }
                        else if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        }  else if (temp.length() > 4) {
                            temp = null;
                            temp = (String) source;
                        }else {
                            temp = temp.substring(temp.indexOf(".") + 1);
                            if (temp.length() > afterDecimal) {
                                return "";
                            }
                        }

                        return super.filter(source, start, end, dest, dstart, dend);
                    }
                }
        });



        //SET button click event
        //Sending data to the machine
        btnSend = (Button) findViewById(R.id.buttonset);
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    if (inputField.length() > 0 &&inputField.length()==3) {
                        String bytesToSend1 = "*CP,";
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
                    } else {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Cupdelay.this);
                        alertBuilder.setTitle("Invalid Data");
                        alertBuilder.setMessage("Please, Enter data in 0.0 format");
                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertBuilder.create().show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(Cupdelay.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //READ button click event
        //To send *CP# cmd to the machine
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String bytesToSend1 = "*CP#\n";
                            byte[] theByteArray = bytesToSend1.getBytes();
                            String data = new String(theByteArray);
                            Btconnection.sendbt(data);
                        } catch (Exception e) {
                        e.printStackTrace();
                        //Toast.makeText(Cupdelay.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }
        });


        // send CON cmd if click on Kaapiwaala tab
        lcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String bytesToSend1 = "*CON#";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(Cupdelay.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //BACK button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cupdelay.this, Operatorsettings.class);
                startActivity(i);
                finish();
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //To receive data from machine
        private Handler handler=new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0: {
                        try {
                            final String message = (String) msg.obj;
                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if ((message.startsWith("*")) && (message.endsWith("#"))  && message.length() <= 5) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        // Toast.makeText(Logins.this,"incom1 "+message,Toast.LENGTH_LONG).show();

                                        if (message.equals("*CON#")) {
                                           // Toast.makeText(Cupdelay.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                            lcon.setBackgroundResource(R.drawable.kapiconect);
                                        } else if (message.equals("*NOC#")) {
                                          //  Toast.makeText(Cupdelay.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                        } else if (message.equals("*DCN#")) {
                                            //Toast.makeText(Cupdelay.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                        }
                                        else  if(message.equals("*CP#")) {
                                            Toast.makeText(Cupdelay.this, "Data Received", Toast.LENGTH_SHORT).show();
                                        }



                                    }
                                });
                                break;
                            }
                            else   if ((message.startsWith("*")) && (message.endsWith("#")) && message.length() ==8) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        inputField.setText(message.replaceAll("[^0-9.]", ""));

                                    }

                                });
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                           // Toast.makeText(Cupdelay.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        };
    }


