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



public class Totalcount2 extends Activity {


    byte[] bytesToSend, theByteArray, a2, a1, out, theByteArray3, bytesToSend2,bytesToSend3,theByteArray2;
   LinearLayout lcon;
    int status = 0;
    Button button;
    ProgressDialog progressdialog;
    BufferedReader br;
    String incoming_data;
    Button bset, bback, bread;
    String strReceived = null;
    private static final int REQUEST_ENABLE_BT = 1;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText hbc, hsc, hcof, hbt, hst, htea, hmilk, hhw;
    EditText fbc, fsc, fcof, fbt, fst, ftea, fmilk, fhw;
    String digits;
    TextView totalhalf,totalfull;
    int sumhalf,sumfull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_count_settings);
        Btconnection.handler = handler;

        //initialising ID's
        Button opt = (Button) findViewById(R.id.btnoption);
        Button half = (Button) findViewById(R.id.btnhalf);
        final Button full = (Button) findViewById(R.id.btnfull);
        Button bc = (Button) findViewById(R.id.btnbc);
        Button sc = (Button) findViewById(R.id.btnsc);
        Button cof = (Button) findViewById(R.id.btncoffee);
        Button bt = (Button) findViewById(R.id.btnblacktea);
        Button st = (Button) findViewById(R.id.btnstrongtea);
        Button tea = (Button) findViewById(R.id.btntea);
        Button milk = (Button) findViewById(R.id.btnmilk);
        Button hw = (Button) findViewById(R.id.btnhotwater);


        hbc = (EditText) findViewById(R.id.etblackcofeehalf);
        hsc = (EditText) findViewById(R.id.etstrongcofeehalf);
        hcof = (EditText) findViewById(R.id.etcofeehalf);
        hbt = (EditText) findViewById(R.id.etblackteahalf);
        hst = (EditText) findViewById(R.id.estrongteahalf);
        htea = (EditText) findViewById(R.id.etteahalf);
        hmilk = (EditText) findViewById(R.id.etmilkhalf);
        hhw = (EditText) findViewById(R.id.ethotwaterhalf);
        fbc = (EditText) findViewById(R.id.etblackcofeefull);
        fsc = (EditText) findViewById(R.id.etstrongcofeefull);
        fcof = (EditText) findViewById(R.id.etcofeefull);
        fbt = (EditText) findViewById(R.id.etblackteafull);
        fst = (EditText) findViewById(R.id.etstrongteafull);
        ftea = (EditText) findViewById(R.id.etteafull);
        fmilk = (EditText) findViewById(R.id.etmilkfull);
        fhw = (EditText) findViewById(R.id.ethotwaterfull);
        bread = (Button) findViewById(R.id.buttonread);
        bset = (Button) findViewById(R.id.buttonset);
        bback = (Button) findViewById(R.id.buttonback);
        lcon = (LinearLayout) findViewById(R.id.lcon);

        totalhalf= (TextView) findViewById(R.id.totalhalf);
        totalfull= (TextView) findViewById(R.id.totalfull);

        //To send *DR# cmd while opening blacktea page and if bluetooth is connected then kaapiwaala tab will turn to green
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(Btconnection.btconnected=true&& Btconnection.bs.isConnected()) {
                        lcon.setBackgroundResource(R.drawable.kapiconect);
                    } else {
                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                    }
                    String bytesToSend1 = "*DR#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(Totalcount2.this, "Connection Problem, Pair bluetooth device", Toast.LENGTH_SHORT).show();
        }

         //SET button click event
        //Sending data to the machine
            bset.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        if (hbc.length() > 0 && fbc.length() > 0) {

                            String bytesToSend1 = "*A,";
                            theByteArray = bytesToSend1.getBytes();
                            bytesToSend = hbc.getText().toString().getBytes();
                            zero();


                            String bytesToSend4 = ",";
                            theByteArray3 = bytesToSend4.getBytes();
                            bytesToSend3 = fbc.getText().toString().getBytes();
                            zero2();

                            String bytesToSend2 = "$";
                            theByteArray2 = bytesToSend2.getBytes();
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            //*A,
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
                            //000000
                            try {
                                output.write(a2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //#
                            try {
                                output.write(theByteArray2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] out = output.toByteArray();
                            String data = new String(out);
                            Btconnection.sendbt(data);


                            /////////////////////////////////////////////////

                            bytesToSend1 = "B,";
                            theByteArray = bytesToSend1.getBytes();
                            bytesToSend = hsc.getText().toString().getBytes();
                            zero();


                            bytesToSend4 = ",";
                            theByteArray3 = bytesToSend4.getBytes();
                            bytesToSend3 = fsc.getText().toString().getBytes();
                            zero2();

                            bytesToSend2 = "$";
                            theByteArray2 = bytesToSend2.getBytes();
                            output = new ByteArrayOutputStream();
                            //*A,
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
                            //000000
                            try {
                                output.write(a2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //#
                            try {
                                output.write(theByteArray2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out = output.toByteArray();
                            String data1 = new String(out);
                            Btconnection.sendbt(data1);

/////////////////////////////////////////////////////////////////////

                            bytesToSend1 = "C,";
                            theByteArray = bytesToSend1.getBytes();
                            bytesToSend = hcof.getText().toString().getBytes();
                            zero();


                            bytesToSend4 = ",";
                            theByteArray3 = bytesToSend4.getBytes();
                            bytesToSend3 = fcof.getText().toString().getBytes();
                            zero2();

                            bytesToSend2 = "$";
                            theByteArray2 = bytesToSend2.getBytes();
                            output = new ByteArrayOutputStream();
                            //*A,
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
                            //000000
                            try {
                                output.write(a2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //#
                            try {
                                output.write(theByteArray2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out = output.toByteArray();
                            String data2 = new String(out);
                            Btconnection.sendbt(data2);

/////////////////////////////////////////////////////////////////


                            bytesToSend1 = "D,";
                            theByteArray = bytesToSend1.getBytes();
                            bytesToSend = hbt.getText().toString().getBytes();
                            zero();


                            bytesToSend4 = ",";
                            theByteArray3 = bytesToSend4.getBytes();
                            bytesToSend3 = fbt.getText().toString().getBytes();
                            zero2();

                            bytesToSend2 = "#\n";
                            theByteArray2 = bytesToSend2.getBytes();
                            output = new ByteArrayOutputStream();
                            //*A,
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
                            //000000
                            try {
                                output.write(a2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //#
                            try {
                                output.write(theByteArray2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out = output.toByteArray();
                            String data3 = new String(out);
                            Btconnection.sendbt(data3);
                        } else {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Totalcount2.this);
                            alertBuilder.setTitle("Invalid Data");
                            alertBuilder.setMessage("Please, Enter data");
                            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            alertBuilder.create().show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(Totalcount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        //READ button click event
        //To send *DR# cmd to the machine
            bread.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String bytesToSend1 = "*DR#\n";
                        byte[] theByteArray = bytesToSend1.getBytes();

                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                }catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(Totalcount2.this, "Connection Problem, Pair bluetooth device", Toast.LENGTH_SHORT).show();
                }

                }
            });
        //BACK button click event
            bback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Totalcount2.this, Settingsadmin.class);
                    startActivity(i);
                    finish();
                }
            });
        // send CON cmd if click on Kaapiwaala tab
            lcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String bytesToSend1 = "*CON#";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                }catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(Totalcount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
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
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    try {
                        // for (int i = 1; i <= 7; i++) {

                        final String message = (String) msg.obj;
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        if ((message.startsWith("*")) && (message.endsWith("#")) && message.length() <= 5) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // Toast.makeText(Logins.this,"incom1 "+message,Toast.LENGTH_LONG).show();

                                    if (message.equals("*CON#")) {
                                       // Toast.makeText(Totalcount2.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                        //Toast.makeText(Totalcount2.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                       // Toast.makeText(Totalcount2.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }  if (message.equals("*DW1#")) {
                                        String bytesToSend1 = "*E,";
                                        theByteArray = bytesToSend1.getBytes();
                                        bytesToSend = hst.getText().toString().getBytes();
                                        zero();
                                        String bytesToSend4 = ",";
                                        theByteArray3 = bytesToSend4.getBytes();
                                        bytesToSend3 = fst.getText().toString().getBytes();
                                        zero2();
                                        String bytesToSend2 = "$";
                                        byte[] theByteArray2 = bytesToSend2.getBytes();
                                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                                        //*A,
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
                                        //000000
                                        try {
                                            output.write(a2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //#
                                        try {
                                            output.write(theByteArray2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        out = output.toByteArray();
                                        String data = new String(out);
                                        Btconnection.sendbt(data);


//////////////////////////////////////////////////////////////////////////////

                                        bytesToSend1 = "F,";
                                        theByteArray = bytesToSend1.getBytes();
                                        bytesToSend = htea.getText().toString().getBytes();
                                        zero();
                                        bytesToSend4 = ",";
                                        theByteArray3 = bytesToSend4.getBytes();
                                        bytesToSend3 = ftea.getText().toString().getBytes();
                                        zero2();
                                        bytesToSend2 = "$";
                                        theByteArray2 = bytesToSend2.getBytes();
                                        output = new ByteArrayOutputStream();

                                        //*A,
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
                                        //000000
                                        try {
                                            output.write(a2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //#
                                        try {
                                            output.write(theByteArray2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        byte[] out = output.toByteArray();
                                        String data1 = new String(out);
                                        Btconnection.sendbt(data1);



////////////////////////////////////////////////////////////////////////////////

                                        bytesToSend1 = "G,";
                                        theByteArray = bytesToSend1.getBytes();
                                        bytesToSend = hmilk.getText().toString().getBytes();
                                        zero();
                                        bytesToSend4 = ",";
                                        theByteArray3 = bytesToSend4.getBytes();
                                        bytesToSend3 = fmilk.getText().toString().getBytes();
                                        zero2();
                                        bytesToSend2 = "$";
                                        theByteArray2 = bytesToSend2.getBytes();
                                        output = new ByteArrayOutputStream();

                                        //*A,
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
                                        //000000
                                        try {
                                            output.write(a2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //#
                                        try {
                                            output.write(theByteArray2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        out = output.toByteArray();
                                        String data3 = new String(out);
                                        Btconnection.sendbt(data3);


//////////////////////////////////////////////////////////////////////
                                        bytesToSend1 = "H,";
                                        theByteArray = bytesToSend1.getBytes();
                                        bytesToSend = hhw.getText().toString().getBytes();
                                        zero();
                                        bytesToSend4 = ",";
                                        theByteArray3 = bytesToSend4.getBytes();
                                        bytesToSend3 = fhw.getText().toString().getBytes();
                                        zero2();
                                        bytesToSend2 = "#\n";
                                        theByteArray2 = bytesToSend2.getBytes();
                                        output = new ByteArrayOutputStream();

                                        //*A,
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
                                        //000000
                                        try {
                                            output.write(a2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //#
                                        try {
                                            output.write(theByteArray2);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        out = output.toByteArray();
                                        String data4 = new String(out);
                                        Btconnection.sendbt(data4);

                                    }
                                    else if (message.equals("*DW2#"))

                                    {

                                        Toast.makeText(Totalcount2.this,"Data received",Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });

                        }


                        // final String incoming_data = br.readLine();
                        else if (message != null && message.length() == 97) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String bhalf = message.substring(3, 7);
                                    hbc.setText(bhalf.replaceAll("[^0-9.]", ""));

                                    String bfull = message.substring(8, 12);
                                    fbc.setText(bfull.replaceAll("[^0-9.]", ""));

                                    String shalf = message.substring(15, 19);
                                    hsc.setText(shalf.replaceAll("[^0-9.]", ""));

                                    String sfull = message.substring(20, 24);
                                    fsc.setText(sfull.replaceAll("[^0-9.]", ""));

                                    String chalf = message.substring(27, 31);
                                    hcof.setText(chalf.replaceAll("[^0-9.]", ""));

                                    String cfull = message.substring(32, 36);
                                    fcof.setText(cfull.replaceAll("[^0-9.]", ""));


                                    String bthalf = message.substring(39, 43);
                                    hbt.setText(bthalf.replaceAll("[^0-9.]", ""));

                                    String btfull = message.substring(44, 48);
                                    fbt.setText(btfull.replaceAll("[^0-9.]", ""));

                                    String sthalf = message.substring(51, 55);
                                    hst.setText(sthalf.replaceAll("[^0-9.]", ""));

                                    String stfull = message.substring(56, 60);
                                    fst.setText(stfull.replaceAll("[^0-9.]", ""));

                                    String thalf = message.substring(63, 67);
                                    htea.setText(thalf.replaceAll("[^0-9.]", ""));

                                    String tfull = message.substring(68, 72);
                                    ftea.setText(tfull.replaceAll("[^0-9.]", ""));


                                    String mhalf = message.substring(75, 79);
                                    hmilk.setText(mhalf.replaceAll("[^0-9.]", ""));

                                    String mfull = message.substring(80, 84);
                                    fmilk.setText(mfull.replaceAll("[^0-9.]", ""));

                                    String hwhalf = message.substring(87, 91);
                                    hhw.setText(hwhalf.replaceAll("[^0-9.]", ""));

                                    String hwfull = message.substring(92);
                                    fhw.setText(hwfull.replaceAll("[^0-9.]", ""));


                                    int bchc = Integer.parseInt(hbc.getText().toString());
                                    int bcfc = Integer.parseInt(fbc.getText().toString());
                                    int schc = Integer.parseInt(hsc.getText().toString());
                                    int scfc = Integer.parseInt(fsc.getText().toString());
                                    int chc = Integer.parseInt(hcof.getText().toString());
                                    int cfc = Integer.parseInt(fcof.getText().toString());
                                    int bthc = Integer.parseInt(hbt.getText().toString());
                                    int btfc = Integer.parseInt(fbt.getText().toString());
                                    int sthc = Integer.parseInt(hst.getText().toString());
                                    int stfc = Integer.parseInt(fst.getText().toString());
                                    int thc = Integer.parseInt(htea.getText().toString());
                                    int tfc = Integer.parseInt(ftea.getText().toString());
                                    int mhc = Integer.parseInt(hmilk.getText().toString());
                                    int mfc = Integer.parseInt(fmilk.getText().toString());


                                    sumhalf=bchc+schc+chc+bthc+sthc+thc+mhc;
                                    sumfull=bcfc+scfc+cfc+btfc+stfc+tfc+mfc;
                                    totalhalf.setText(String.valueOf(sumhalf));
                                    totalfull.setText(String.valueOf(sumfull));


                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(Totalcount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
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

            a = "000" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length==2) {
            String value = new String(bytesToSend);

            a = "00" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length==3) {
            String value = new String(bytesToSend);

            a = "0" + value;
            a1 = a.getBytes();
        }
         else if (bytesToSend.length == 4) {
            String value = new String(bytesToSend);

            a = value;
            a1 = a.getBytes();

        }
    }
    void zero2() {
        //adding zero
        String aa = null;
        a2 = new byte[0];

        if(bytesToSend3.length==0) {
            String value1 = new String(bytesToSend3);

            aa = "";
            a2 = aa.getBytes();
        }
       else if (bytesToSend3.length==1) {
            String value1 = new String(bytesToSend3);

            aa = "000" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length==2) {
            String value1 = new String(bytesToSend3);

            aa = "00" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length==3) {
            String value1 = new String(bytesToSend3);

            aa = "0" + value1;
            a2 = aa.getBytes();
        }  else if (bytesToSend3.length == 4) {
            String value1 = new String(bytesToSend3);

            aa = value1;
            a2 = aa.getBytes();
        }

    }
}
