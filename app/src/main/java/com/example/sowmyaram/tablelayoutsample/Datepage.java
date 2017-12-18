package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



public class Datepage extends Activity implements View.OnClickListener {


    byte[] bytesToSend, theByteArray, a2, a1, out, theByteArray3, bytesToSend2, bytesToSend3, theByteArray2;
    Button button;
    BufferedReader br;
    String incoming_data;
    Button bset, bback, bread;
    String strReceived = null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText coffee, tea, milk;
    String digits;
    private UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";

    private EditText etdate, ettime, etday;
    public Date date;
    String time;
    String date1, day;
    TextView tv1, tv2, tv3, tv;
    LinearLayout lcon;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time);

        Btconnection.handler=handler;

        //initialising ID's
        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textViewdate);
        tv2 = (TextView) findViewById(R.id.textViewtime);
        tv3 = (TextView) findViewById(R.id.textViewday);
        etdate = (EditText) findViewById(R.id.editTextdate);
        ettime = (EditText) findViewById(R.id.editTexttime);
        etday = (EditText) findViewById(R.id.editTextday);
        bread = (Button) findViewById(R.id.buttonread);
        bset = (Button) findViewById(R.id.buttonset);
        bback = (Button) findViewById(R.id.buttonback);
        etdate.setOnClickListener(this);
        ettime.setOnClickListener(this);
        etday.setOnClickListener(this);
        lcon = (LinearLayout) findViewById(R.id.lcon);


        //To send *RT# cmd while opening blacktea page and if bluetooth is connected then kaapiwaala tab will turn to green
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

                String bytesToSend1 = "*RT#\n";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
            }
        }); } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(Datepage.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }

        //SET button click event
        //Sending date and time and day of week to the machine
        bset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    if (etdate.length() > 0 && ettime.length() > 0 && etday.length() > 0) {
                        String bytesToSend1 = "*";
                        theByteArray = bytesToSend1.getBytes();
                        bytesToSend = etdate.getText().toString().getBytes();

                        String bytesToSend4 = "@";
                        theByteArray3 = bytesToSend4.getBytes();
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        //*
                        try {
                            output.write(theByteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //DD:MM:YYYY
                        try {
                            output.write(bytesToSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //@
                        try {
                            output.write(theByteArray3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] out = output.toByteArray();
                        String data = new String(out);
                        Btconnection.sendbt(data);

                        /////////////////////////////////////////////////

                        bytesToSend = ettime.getText().toString().getBytes();
                        bytesToSend4 = "$";
                        theByteArray3 = bytesToSend4.getBytes();
                        output = new ByteArrayOutputStream();
                        //hh:mm
                        try {
                            output.write(bytesToSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //$
                        try {
                            output.write(theByteArray3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        out = output.toByteArray();
                        String data1 = new String(out);
                        Btconnection.sendbt(data1);
/////////////////////////////////////////////////////////////////////

                        bytesToSend = etday.getText().toString().getBytes();
                        bytesToSend4 = "#\n";
                        theByteArray3 = bytesToSend4.getBytes();
                        output = new ByteArrayOutputStream();
                        //d
                        try {
                            output.write(bytesToSend);
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
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Datepage.this);
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
                   // Toast.makeText(Datepage.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //READ button click event
        //To send *RT# cmd to the machine
        bread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                String bytesToSend1 = "*RT#\n";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(Datepage.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
                }
        });

        //BACK button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(Datepage.this, Operatorsettings.class);
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
                    //Toast.makeText(Datepage.this, "", Toast.LENGTH_SHORT).show();
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


    //creating date pickerdialog
    @Override
    public void onClick(View v) {
        try{
        if (v == etdate) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etdate.setText((dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth)) + ":" + (monthOfYear < 9 ? ("0" + (monthOfYear+1)) : (monthOfYear+1)) + ":" + year);
                            a = (etdate.getText().toString());
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

            //creating time pickerdialog
        if (v == ettime) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mHour = hourOfDay;
                            mMinute = minute;
                           /* if (mHour < 12 && mHour >= 0) {
                                ettime.setText((mHour < 10 ? ("0" + mHour) : (mHour)) + " : " + (mMinute < 10 ? ("0" + (mMinute)) : (mMinute)));
                            } else {
                                mHour -= 12;
                                if (mHour == 0) {
                                    mHour = 12;
                                }*/
                                ettime.setText((mHour < 10 ? ("0" + mHour):(mHour)) + ":" + (mMinute < 10 ? ("0" + (mMinute)):(mMinute)));
                            }

                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

        //To display day of week
        if (v == etday) {
            String weekDay = "";
            Calendar c = Calendar.getInstance();
            String pattern = "dd:MM:yyyy";
            try {
                date = new SimpleDateFormat(pattern).parse(a);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            if (Calendar.MONDAY == dayOfWeek) {
                weekDay = "2";
            } else if (Calendar.TUESDAY == dayOfWeek) {
                weekDay = "3";
            } else if (Calendar.WEDNESDAY == dayOfWeek) {
                weekDay = "4";
            } else if (Calendar.THURSDAY == dayOfWeek) {
                weekDay = "5";
            } else if (Calendar.FRIDAY == dayOfWeek) {
                weekDay = "6";
            } else if (Calendar.SATURDAY == dayOfWeek) {
                weekDay = "7";
            } else if (Calendar.SUNDAY == dayOfWeek) {
                weekDay = "1";
            }

            etday.setText(weekDay);

        }

    }
        catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(Datepage.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }}

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
                                       // Toast.makeText(Datepage.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                        //Toast.makeText(Datepage.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                        //Toast.makeText(Datepage.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }
                                   else if (message.equals("*DT#")) {
                                        Toast.makeText(Datepage.this, "Data Received", Toast.LENGTH_SHORT).show();
                                    }



                                }
                            });
                            break;
                        }
                        else  if (message != null  && message.length() == 20) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String bhalf = message.substring(1, 11);
                                    etdate.setText(bhalf.replaceAll("[^0-9.:]", ""));

                                    String bfull = message.substring(12, 17);
                                    ettime.setText(bfull.replaceAll("[^0-9.:]", ""));

                                    String shalf = message.substring(18);
                                    etday.setText(shalf.replaceAll("[^0-9.:]", ""));
                                }
                            });
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(Datepage.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };
}























































