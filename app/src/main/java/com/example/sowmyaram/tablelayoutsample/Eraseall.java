package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;



public class Eraseall extends Activity{


    Button e1, e2, e3, e4, e5, e6, e7, eall,e11,e12;
    LinearLayout lcon;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erase1);
        Btconnection.handler = handler;

        //initialising ID's
        Button d1 = (Button) findViewById(R.id.day1);
        Button d2 = (Button) findViewById(R.id.day2);
        Button d3 = (Button) findViewById(R.id.day3);
        Button d4 = (Button) findViewById(R.id.day4);
        Button d5 = (Button) findViewById(R.id.day5);
        Button d6 = (Button) findViewById(R.id.day6);
        Button d7 = (Button) findViewById(R.id.day7);
        Button dall = (Button) findViewById(R.id.all);
        e1 = (Button) findViewById(R.id.e1);
        e2 = (Button) findViewById(R.id.e2);
        e3 = (Button) findViewById(R.id.e3);
        e4 = (Button) findViewById(R.id.e4);
        e5 = (Button) findViewById(R.id.e5);
        e6 = (Button) findViewById(R.id.e6);
        e7 = (Button) findViewById(R.id.e7);
        eall = (Button) findViewById(R.id.eall);
        e11 = (Button) findViewById(R.id.e11);
        e12 = (Button) findViewById(R.id.e12);
        eall = (Button) findViewById(R.id.eall);
        lcon = (LinearLayout) findViewById(R.id.lcon);


        // if bluetooth is connected then kaapiwaala tab will turn to green
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Btconnection.btconnected=true &&Btconnection.bs.isConnected()) {
                            lcon.setBackgroundResource(R.drawable.kapiconect);
                        } else {
                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                        }

                    }

    catch (Exception e) {
        e.printStackTrace();
        //Toast.makeText(Eraseall.this, "Connection Problem", Toast.LENGTH_SHORT).show();
    }
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
                            }catch (Exception e) {
                                e.printStackTrace();
                               // Toast.makeText(Eraseall.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


        //Button click event for erase day1 data. It will show alert dialog for confirmation.
         e1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day1 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_LONG).show();
                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try {
                                        String bytesToSend1 = "*E1#\n";
                                        byte[] theByteArray = bytesToSend1.getBytes();
                                        String data = new String(theByteArray);
                                        Btconnection.sendbt(data);
                                        // Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                       // Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase day2 data. It will show alert dialog for confirmation.
            e2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day2 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_LONG).show();
                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*E2#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                       // Toast.makeText(Eraseall.this, "Connection Problem, can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase day3 data. It will show alert dialog for confirmation.
            e3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day3 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*E3#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        //Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase day4 data. It will show alert dialog for confirmation.
            e4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day4 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*E4#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                       // Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase day5 data. It will show alert dialog for confirmation.
            e5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day5 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*E5#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                       // Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase day6 data. It will show alert dialog for confirmation.
            e6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day6 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*E6#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        //Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase day7 data. It will show alert dialog for confirmation.
            e7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete day7 data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*E7#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        //Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase all data. It will show alert dialog for confirmation.
            eall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete all data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*EAD#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                       // Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });

        //Button click event for erase all master count data. It will show alert dialog for confirmation.
            e11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete all data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*MQ#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                       // Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.show();
                }
            });
        //Button click event for erase all days data. It will show alert dialog for confirmation.
            e12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Eraseall.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Are you sure to delete all data?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    try{
                                    String bytesToSend1 = "*EA#\n";
                                    byte[] theByteArray = bytesToSend1.getBytes();
                                    String data = new String(theByteArray);
                                    Btconnection.sendbt(data);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        //Toast.makeText(Eraseall.this, "Connection Problem,can't erase data", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(getApplicationContext(), Settingsadmin.class);
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
                                       // Toast.makeText(Eraseall.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                       // Toast.makeText(Eraseall.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                        //Toast.makeText(Eraseall.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }
                                    else  if(message.equals("*P1#")) {
                                        Toast.makeText(Eraseall.this, "Day1 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P2#")) {
                                        Toast.makeText(Eraseall.this, "Day2 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P3#")) {
                                        Toast.makeText(Eraseall.this, "Day3 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P4#")) {
                                        Toast.makeText(Eraseall.this, "Day4 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P5#")) {
                                        Toast.makeText(Eraseall.this, "Day5 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P6#")) {
                                        Toast.makeText(Eraseall.this, "Day6 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P7#")) {
                                        Toast.makeText(Eraseall.this, "Day7 data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*P#")) {
                                        Toast.makeText(Eraseall.this, "All data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*PM#")) {
                                        Toast.makeText(Eraseall.this, "All Master  data erased", Toast.LENGTH_SHORT).show();
                                    }
                                    else  if(message.equals("*PE#")) {
                                        Toast.makeText(Eraseall.this, " All days data erased", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                            break;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };
}







