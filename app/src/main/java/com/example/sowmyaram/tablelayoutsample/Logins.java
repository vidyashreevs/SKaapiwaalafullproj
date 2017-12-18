package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;




public class Logins extends ActionBarActivity {

    DataBaseHandler db = null;
    Button btadmin, operator, user;
    String strReceived=null;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetoothAdapter;
    InputStream in = null;
    byte[] bytesToSend,a2;
    public static UUID myUUID;

    static  LinearLayout lcon;
    public static String incoming_data;
    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logins);
        Btconnection.handler=handler;

        //initialising ID's
        lcon = (LinearLayout) findViewById(R.id.lconnections);
        btadmin = (Button) findViewById(R.id.buttonAdmin);
        operator = (Button) findViewById(R.id.buttonoperator);
        user = (Button) findViewById(R.id.buttonuser);
        db = new DataBaseHandler(Logins.this);
        db.getWritableDatabase();

        //Go to admin login if click on admin
        btadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Logins.this, Adminlogin1.class);
                startActivity(i);
                finish();
            }
        });

        //Go to operator login if click on operator
        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Logins.this, Operatorlogin.class);
                startActivity(i1);
                finish();
            }
        });

        //Go to user login if click on user
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Logins.this, Userloginpage.class);
                startActivity(i2);
                finish();
            }
        });


        // send CON cmd if click on Kaapiwaala tab
        lcon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            if (Btconnection.btconnected = true && Btconnection.bs.isConnected()) {
                                                String bytesToSend1 = "*CON#";
                                                byte[] theByteArray = bytesToSend1.getBytes();
                                                String data = new String(theByteArray);
                                                Btconnection.sendbt(data);
                                                //Toast.makeText(Logins.this, "CON cmd sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                           // Toast.makeText(Logins.this, "Connection Problem", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


        //To send CON cmd while opening login page
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        if (Btconnection.btconnected = true && Btconnection.bs.isConnected()) {
                            lcon.setBackgroundResource(R.drawable.kapiconect);
                        } else {
                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                            //Toast.makeText(Logins.this, "Connection Problem..!! Device is off", Toast.LENGTH_SHORT).show();
                        }

                            String bytesToSend1 = "*CON#";
                            byte[] theByteArray = bytesToSend1.getBytes();
                            String data = new String(theByteArray);
                            Btconnection.sendbt(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(Logins.this, "Connection Problem..!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        //send DCN cmd if long click of kaapiwala tab
        lcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (Btconnection.btconnected = true && Btconnection.bs.isConnected()) {
                        String bytesToSend1 = "*DCN#\n";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                       // Toast.makeText(Logins.this, "DCN cmd sent", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(Logins.this, "Connection Problem", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {

           if (Btconnection.bs.isConnected()) {
               String bytesToSend1 = "*DCN#\n";
               byte[] theByteArray = bytesToSend1.getBytes();
               String data = new String(theByteArray);
               Btconnection.sendbt(data);
               moveTaskToBack(true);
               android.os.Process.killProcess(android.os.Process.myPid());
               System.exit(0);
           }else
           {
               moveTaskToBack(true);
               android.os.Process.killProcess(android.os.Process.myPid());
               System.exit(0);
           }


            /*if (Btconnection.bs.isConnected()) {
                try {
                    Btconnection.bs.close();

                } catch (IOException e) {

                    //e.printStackTrace();
                }*/


        } catch (Exception e) {

          //  Toast.makeText(Logins.this, "Connection Problem!! Device not connected", Toast.LENGTH_SHORT).show();
            // e.printStackTrace();
        }
    }



//To receive data from machine
    private Handler handler=new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    try {
                        final String message = (String) msg.obj;
                        if ((message.startsWith("*")) && (message.endsWith("#"))) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (message.equals("*CON#")) {
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                       // Toast.makeText(Logins.this, "Device Connected", Toast.LENGTH_SHORT).show();


                                    } else if (message.equals("*NOC#")) {
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                      //  Toast.makeText(Logins.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();

                                    } else if (message.equals("*DCN#")) {
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                       // Toast.makeText(Logins.this, "Device disconnected", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

}

















