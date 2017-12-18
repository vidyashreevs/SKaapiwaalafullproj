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



public class BrewCups extends Activity {

    byte[] bytesToSend, theByteArray, a2, a1, out, theByteArray3, bytesToSend2,bytesToSend3,theByteArray2;
    Button button;
    BufferedReader br;
    String incoming_data;
    Button bset, bback, bread;
    String strReceived = null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText coffee,tea,milk;
    String digits;
    LinearLayout lcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brue_cups);
        Btconnection.handler=handler;

        //initialising ID's
        TextView cfe = (TextView) findViewById(R.id.textViewcoffee);
        TextView tvtea = (TextView) findViewById(R.id.textViewtea);
        TextView tvmilk = (TextView) findViewById(R.id.textViewmilk);
        coffee = (EditText) findViewById(R.id.editTextcoffee);
        tea = (EditText) findViewById(R.id.editTexttea);
        milk = (EditText) findViewById(R.id.editTextmilk);
        bread = (Button) findViewById(R.id.buttonread);
        bset = (Button) findViewById(R.id.buttonset);
        bback = (Button) findViewById(R.id.buttonback);
        lcon = (LinearLayout) findViewById(R.id.lcon);


        //To send *RBC# cmd while opening brewcups page
        try{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(Btconnection.btconnected=true&& Btconnection.bs.isConnected()) {
                    lcon.setBackgroundResource(R.drawable.kapiconect);
                }
                else
                {
                    lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                }
                String bytesToSend1 = "*RBC#\n";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(BrewCups.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }

         //SET button click event
        //Sending data to the machine
        bset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (coffee.length() > 0 && tea.length() > 0 && milk.length() > 0) {
                        String bytesToSend1 = "*C,";
                        theByteArray = bytesToSend1.getBytes();
                        bytesToSend = coffee.getText().toString().getBytes();
                        zero();
                        String bytesToSend4 = ",";
                        theByteArray3 = bytesToSend4.getBytes();
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        //*C,
                        try {
                            output.write(theByteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //000000
                        try {
                            output.write(a1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //,
                        try {
                            output.write(theByteArray3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        byte[] out = output.toByteArray();
                        String data = new String(out);
                        Btconnection.sendbt(data);


                        /////////////////////////////////////////////////

                        bytesToSend1 = "T,";
                        theByteArray = bytesToSend1.getBytes();
                        bytesToSend = tea.getText().toString().getBytes();
                        zero();


                        bytesToSend4 = ",";
                        theByteArray3 = bytesToSend4.getBytes();

                        output = new ByteArrayOutputStream();
                        //T,
                        try {
                            output.write(theByteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //000000
                        try {
                            output.write(a1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //,
                        try {
                            output.write(theByteArray3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        out = output.toByteArray();
                        String data1 = new String(out);
                        Btconnection.sendbt(data1);

/////////////////////////////////////////////////////////////////////

                        bytesToSend1 = "M,";
                        theByteArray = bytesToSend1.getBytes();
                        bytesToSend = milk.getText().toString().getBytes();
                        zero();
                        bytesToSend4 = "#\n";
                        theByteArray3 = bytesToSend4.getBytes();
                        output = new ByteArrayOutputStream();
                        //,
                        try {
                            output.write(theByteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //000000
                        try {
                            output.write(a1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //#
                        try {
                            output.write(theByteArray3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out = output.toByteArray();
                        String data2 = new String(out);
                        Btconnection.sendbt(data2);

/////////////////////////////////////////////////////////////////
                    } else {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BrewCups.this);
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
                 //   Toast.makeText(BrewCups.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //READ buttun click event
        //To send *RBC# cmd to the machine
        bread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                String bytesToSend1 = "*RBC#\n";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
                } catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(BrewCups.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
           //BACK buttun click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(BrewCups.this, Operatorsettings.class);
                startActivity(i);
                finish();
            }
        });



        // send CON cmd if click on Kaapiwaala tab
        lcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String bytesToSend1 = "*CON#";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
                } catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(BrewCups.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
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
                                        //Toast.makeText(BrewCups.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                       // Toast.makeText(BrewCups.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                       // Toast.makeText(BrewCups.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }
                                    else if(message.equals("*BC#")) {
                                        Toast.makeText(BrewCups.this, "Data Received", Toast.LENGTH_SHORT).show();
                                    }



                                }
                            });
                            break;
                        }
                        else if (message != null && message.length() == 19) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String bhalf = message.substring(3, 6);
                                    coffee.setText(bhalf.replaceAll("[^0-9.]", ""));

                                    String bfull = message.substring(9, 12);
                                    tea.setText(bfull.replaceAll("[^0-9.]", ""));

                                    String shalf = message.substring(15);
                                    milk.setText(shalf.replaceAll("[^0-9.]", ""));
                                }
                            });
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(BrewCups.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };


    //Adding zero if input datalength less than 3
    void zero() {

        String a = null;
        a1 = new byte[0];

        if (bytesToSend.length==0) {
            String value = new String(bytesToSend);

            a ="";
            a1 = a.getBytes();
        }

        else if (bytesToSend.length==1) {
            String value = new String(bytesToSend);

            a = "00" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length==2) {
            String value = new String(bytesToSend);

            a = "0" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length==3) {
            String value = new String(bytesToSend);

            a =  value;
            a1 = a.getBytes();
        }

    }



}










