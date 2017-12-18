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



public class Blackcofee extends Activity {


    EditText etsugarv,etcoffeev,etwaterspeed;
    byte[] bytesToSend, theByteArray, a2, a1, out, theByteArray3, bytesToSend2,bytesToSend3,theByteArray2;
    Button button;
    BufferedReader br;
    String incoming_data;
    String bytesToSend4;
    ByteArrayOutputStream output;
    Button bset, bback, bread;
    String strReceived = null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText coffee,tea,milk;
    LinearLayout lcon;
    String digits;
    private UUID myUUID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_coffee);
        Btconnection.handler=handler;

        //initialising ID's
        TextView bc= (TextView) findViewById(R.id.tvblackcoffee);
        TextView value= (TextView) findViewById(R.id.tvvalue);
        TextView tvsugarv= (TextView) findViewById(R.id.tvsugarv);
        TextView tvcoffeev= (TextView) findViewById(R.id.tvcoffeev);
        TextView btnwaterspeed= (TextView) findViewById(R.id.btnwaterspeed);
        etsugarv= (EditText) findViewById(R.id.etsugarval);
        etcoffeev= (EditText) findViewById(R.id.etcoffeeval);
        etwaterspeed= (EditText) findViewById(R.id.etwaterspeed);
        bset = (Button) findViewById(R.id.buttonset);
        bback = (Button) findViewById(R.id.buttonback);
        bread = (Button) findViewById(R.id.buttonread);
        lcon = (LinearLayout) findViewById(R.id.lcon);



        //To send *RA# cmd while opening blackcoffee page and if bluetooth is connected then kaapiwaala tab will turn to green
        try{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(Btconnection.btconnected=true && Btconnection.bs.isConnected()) {
                    lcon.setBackgroundResource(R.drawable.kapiconect);
                }
                else
                {
                    lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                }
                String bytesToSend1 = "*RA#\n";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(Blackcofee.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }



        //making edittext to take data in 0.0 format
        etsugarv.setFilters(new InputFilter[] {
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 2, afterDecimal = 1;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = etsugarv.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.0";
                        }
                         if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        }  else if (temp.length() > 5) {
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

        //making edittext to take data in 0.0 format
        etcoffeev.setFilters(new InputFilter[] {
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 2, afterDecimal = 1;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = etcoffeev.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.0";
                       }
                        else if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        }  else if (temp.length() > 5) {
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

        //making edittext to take data in 0.0 format
        etwaterspeed.setFilters(new InputFilter[] {
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 2, afterDecimal = 1;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = etwaterspeed.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.0";
                        }
                        else if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        }  else if (temp.length() > 5) {
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
        bset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                if ( etsugarv.length()!=0 && etsugarv.length()>=3 && etcoffeev.length()!=0 &&etwaterspeed.length()!=0  ) {

                    if (etsugarv.getText().toString().contains(".")) {


                        String bytesToSend1 = "*A,";
                        theByteArray = bytesToSend1.getBytes();

                        bytesToSend = etsugarv.getText().toString().getBytes();
                        zero();
                        bytesToSend4 = ",";
                        theByteArray3 = bytesToSend4.getBytes();
                        output = new ByteArrayOutputStream();
                        //*G,
                        try {
                            output.write(theByteArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //00
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


                        bytesToSend = etcoffeev.getText().toString().getBytes();
                        zero();


                        bytesToSend4 = ",";
                        theByteArray3 = bytesToSend4.getBytes();

                        output = new ByteArrayOutputStream();

                        //00
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


/////////////////////////////////////////////////////////////////////*B,00.0,00.0,00.0,00.0,000#


                        bytesToSend = etwaterspeed.getText().toString().getBytes();
                        zero();


                        bytesToSend4 = "#\n";
                        theByteArray3 = bytesToSend4.getBytes();
                        output = new ByteArrayOutputStream();

                        //00
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
                        String data2 = new String(out);
                        Btconnection.sendbt(data2);


/////////////////////////////////////////////////////////////////


                    }
                }
                else
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Blackcofee.this);
                    alertBuilder.setTitle("Invalid Data");
                    alertBuilder.setMessage("Please, Enter data in 00.0 format");
                    alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertBuilder.create().show();
                }

                } catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(Blackcofee.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }

            }


        });



        //READ button click event
        //To send *RA# cmd to the machine
        bread.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                String bytesToSend1 = "*RA#\n";
                byte[] theByteArray = bytesToSend1.getBytes();

                String data = new String(theByteArray);
                Btconnection.sendbt(data);
                } catch (Exception e) {
                    e.printStackTrace();
                  //  Toast.makeText(Blackcofee.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //BACK button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(Blackcofee.this, Operatorsettings.class);
                startActivity(i);
                finish();
            }
        });


        // send CON cmd when click on Kaapiwaala tab
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
                  //  Toast.makeText(Blackcofee.this, "Connection Problem", Toast.LENGTH_SHORT).show();
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
                                        //Toast.makeText(Blackcofee.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                       // Toast.makeText(Blackcofee.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                      //  Toast.makeText(Blackcofee.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }

                                   else if(message.equals("*O1#")) {
                                        Toast.makeText(Blackcofee.this, "Data Received", Toast.LENGTH_SHORT).show();

                                    }



                                }
                            });
                            break;
                        }
                        else   if (message != null ) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String bhalf = message.substring(3, 7);
                                    etsugarv.setText(bhalf.replaceAll("[^0-9.]", ""));

                                    String bfull = message.substring(8, 12);
                                    etcoffeev.setText(bfull.replaceAll("[^0-9.]", ""));

                                    String shalf = message.substring(13);
                                    etwaterspeed.setText(shalf.replaceAll("[^0-9.]", ""));


                                }
                            });
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                      //  Toast.makeText(Blackcofee.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };




    //Adding zero if input datalength less than 4
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

            a = "0" + value;
            a1 = a.getBytes();
        }
        else if (bytesToSend.length==2) {
            String value = new String(bytesToSend);

            a = "0" + value;
            a1 = a.getBytes();
        }
        else if (bytesToSend.length==3) {
            String value = new String(bytesToSend);

            a = "0" + value;
            a1 = a.getBytes();
        }
        else if (bytesToSend.length==4) {
            String value = new String(bytesToSend);

            a =  value;
            a1 = a.getBytes();
        }

    }



}















