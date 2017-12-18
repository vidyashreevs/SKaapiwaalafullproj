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




public class Reversemilk extends ActionBarActivity {
    String strReceived=null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText inputField;
    Button btnSend,btnRead;
    byte[] bytesToSend,a2;
    private Button bset, bback, bread;
    // private EditText etbrew;
    private String bctime;
    private TextView tv1,tv2;
    LinearLayout lcon;
    private EditText etmilkval,etteaval,etcoffeeval;
    private byte[] bytesToSend_cof,bytesToSend_tea;
    private byte[] a2_cof;
    private byte[] a2_tea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reversemilk);
        Btconnection.handler = handler;

        //initialising ID's
       // tv1 = (TextView) findViewById(R.id.textView1);
        //tv2 = (TextView) findViewById(R.id.textView4);
        //inputField = (EditText) findViewById(R.id.etrevmilk);
        etmilkval = (EditText) findViewById(R.id.etmilkval);
        etcoffeeval = (EditText) findViewById(R.id.etcoffeeval);
        etteaval = (EditText) findViewById(R.id.etteaval);

        bback = (Button) findViewById(R.id.buttonback);
        lcon = (LinearLayout) findViewById(R.id.lcon);
        btnRead = (Button) findViewById(R.id.buttonread);
        btnSend = (Button) findViewById(R.id.buttonset);


        //To send *VR# cmd while opening blacktea page and if bluetooth is connected then kaapiwaala tab will turn to green
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(Btconnection.btconnected=true&& Btconnection.bs.isConnected()) {
                        lcon.setBackgroundResource(R.drawable.kapiconect);
                    } else {
                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                    }
                    String bytesToSend1 = "*VR#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(Reversemilk.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }

         //SET button click event
        //Sending data to the machine
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (etmilkval.length() > 0&&etcoffeeval.length() > 0&&etteaval.length() > 0) {


                        String bytesToSend1 = "*MR,";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        bytesToSend = etmilkval.getText().toString().getBytes();
                        String aa = null;
                        a2 = new byte[0];
                        if (bytesToSend.length == 1) {
                            String value1 = new String(bytesToSend);
                            aa = value1;
                            a2 = aa.getBytes();
                        } else if (bytesToSend.length == 0) {
                            String value1 = new String(bytesToSend);
                            aa = "";
                            a2 = aa.getBytes();
                        }

                        String bytesToSend_coffee = "$CR,";
                        byte[] theByteArray_cof = bytesToSend_coffee.getBytes();
                        bytesToSend_cof = etcoffeeval.getText().toString().getBytes();
                        String aa_cof = null;
                        a2_cof = new byte[0];
                        if (bytesToSend_cof.length == 1) {
                            String value1 = new String(bytesToSend_cof);
                            aa_cof = value1;
                            a2_cof = aa_cof.getBytes();
                        } else if (bytesToSend_cof.length == 0) {
                            String value1 = new String(bytesToSend_cof);
                            aa_cof = "";
                            a2_cof = aa_cof.getBytes();
                        }

                        String bytesToSend_teaval = "$TR,";
                        byte[] theByteArray_tea = bytesToSend_teaval.getBytes();
                        bytesToSend_tea = etteaval.getText().toString().getBytes();
                        String aa_tea = null;
                        a2_tea = new byte[0];
                        if (bytesToSend_tea.length == 1) {
                            String value1 = new String(bytesToSend_tea);
                            aa_tea = value1;
                            a2_tea = aa_tea.getBytes();
                        } else if (bytesToSend_tea.length == 0) {
                            String value1 = new String(bytesToSend_tea);
                            aa_tea = "";
                            a2_tea = aa_tea.getBytes();
                        }


                        String bytesToSend2 = "#\n";
                        byte[] theByteArray2 = bytesToSend2.getBytes();


                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                        try {
                            output.write(theByteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.write(a2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.write(theByteArray_cof);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.write(a2_cof);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            output.write(theByteArray_tea);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            output.write(a2_tea);
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
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Reversemilk.this);
                        alertBuilder.setTitle("Invalid Data");
                        alertBuilder.setMessage("Please, Enter valid data");
                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertBuilder.create().show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(Reversemilk.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //READ button click event
        //To send *VR# cmd to the machine
        btnRead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String bytesToSend1 = "*VR#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();

                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(Reversemilk.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //BACK button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reversemilk.this, Operatorsettings.class);
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
                                String bytesToSend1 = "*CON#";
                                byte[] theByteArray = bytesToSend1.getBytes();
                                String data = new String(theByteArray);
                                Btconnection.sendbt(data);
                            } catch (Exception e) {
                                e.printStackTrace();
                              //  Toast.makeText(Reversemilk.this, "Connection Problem", Toast.LENGTH_SHORT).show();
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
                                       // Toast.makeText(Reversemilk.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                       // Toast.makeText(Reversemilk.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                        //Toast.makeText(Reversemilk.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }
                                    else  if(message.equals("*VR#")) {
                                        Toast.makeText(Reversemilk.this, "Data Received", Toast.LENGTH_SHORT).show();
                                    }



                                }
                            });
                            break;
                        }
                       /* else   if ((message.startsWith("*")) && (message.endsWith("#")) && message.length() == 6) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    inputField.setText(message.replaceAll("[^0-9.]", ""));

                                }

                            });*/
                        else if (message != null ) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String bhalf = message.substring(4, 5);
                                    etmilkval.setText(bhalf.replaceAll("[^0-9.]", ""));

                                    String bfull = message.substring(9, 10);
                                    etcoffeeval.setText(bfull.replaceAll("[^0-9.]", ""));

                                    String shalf = message.substring(14);
                                    etteaval.setText(shalf.replaceAll("[^0-9.]", ""));

                                    /*String z2 = message.substring(23);
                                    etmilkspeed.setText(z2.replaceAll("[^0-9.]", ""));*/


                                }
                            });
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(Reversemilk.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };
}















