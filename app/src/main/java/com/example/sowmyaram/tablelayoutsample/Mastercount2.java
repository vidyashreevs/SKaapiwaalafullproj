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



public class Mastercount2 extends Activity {


    byte[] bytesToSend, theByteArray, a2, a1, out, theByteArray3, bytesToSend3;

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
    private UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";
    LinearLayout lcon;
    TextView totalhalf,totalfull;
    int sumhalf,sumfull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_count_table);
        Btconnection.handler = handler;


        //initialising ID's
        Button opt = (Button) findViewById(R.id.btnoption);
        Button half = (Button) findViewById(R.id.btnhalf);
        Button full = (Button) findViewById(R.id.btnfull);
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

        //To send *MR# cmd while opening blacktea page and if bluetooth is connected then kaapiwaala tab will turn to green
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(Btconnection.btconnected=true&& Btconnection.bs.isConnected()) {
                        lcon.setBackgroundResource(R.drawable.kapiconect);
                    } else {
                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                    }
                    String bytesToSend1 = "*MR#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
          //  Toast.makeText(Mastercount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }


        //SET button click event
        //Sending data to the machine
            bset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (hbc.length() > 0 && fbc.length() > 0) {
                            String bytesToSend1 = "*MA,";
                            theByteArray = bytesToSend1.getBytes();
                            bytesToSend = hbc.getText().toString().getBytes();
                            zero();
                            String bytesToSend4 = ",";
                            byte[] theByteArray3 = bytesToSend4.getBytes();
                            bytesToSend3 = fbc.getText().toString().getBytes();
                            zero2();
                            String bytesToSend2 = "#\n";
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

                            byte[] out = output.toByteArray();
                            String data = new String(out);
                            Btconnection.sendbt(data);

                        } else {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Mastercount2.this);
                            alertBuilder.setTitle("Invalid Data");
                            alertBuilder.setMessage("Please, Enter data");
                            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            alertBuilder.create().show();
                        }

                    }catch(Exception e)
                    {
                    e.printStackTrace();
                    //Toast.makeText(Mastercount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
            });

        //READ button click event
        //To send *MR# cmd to the machine
            bread.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String bytesToSend1 = "*MR#\n";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                    }catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(Mastercount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }


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
                    } catch (Exception e) {
                        e.printStackTrace();
                      //  Toast.makeText(Mastercount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        //BACK button click event
            bback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Mastercount2.this, Settingsadmin.class);
                    startActivity(i);
                    finish();
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
                                           // Toast.makeText(Mastercount2.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                            lcon.setBackgroundResource(R.drawable.kapiconect);
                                        } else if (message.equals("*NOC#")) {
                                            //Toast.makeText(Mastercount2.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                        } else if (message.equals("*DCN#")) {
                                           // Toast.makeText(Mastercount2.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                        }
                                        //if received data is *M1# then strong coffee data will send to machine
                                        else if (message.equals("*M1#")) {
                                            String bytesToSend1 = "*MB,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = hsc.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = fsc.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
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
                                        }


                                        //////////////////////////////////////////////////////////////////////////////////

                                        //if received data is *M2# then  coffee data will send to machine
                                        else if (message.equals("*M2#")) {

                                            String bytesToSend1 = "*MC,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = hcof.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = fcof.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
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
                                            byte[] out = output.toByteArray();
                                            String data1 = new String(out);
                                            Btconnection.sendbt(data1);
                                        }


                                        /////////////////////////////////////////////////////////////////////////////////////////

                                        //if received data is *M3# then black tea data will send to machine
                                        else if (message.equals("*M3#")) {

                                            String bytesToSend1 = "*MD,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = hbt.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = fbt.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
                                            byte[] theByteArray2 = bytesToSend2.getBytes();
                                            ByteArrayOutputStream output = new ByteArrayOutputStream();
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
                                            String data2 = new String(out);
                                            Btconnection.sendbt(data2);
                                        }


                                        /////////////////////////////////////////////////////////////////////

                                        //if received data is *M4# then strong tea data will send to machine
                                        else if (message.equals("*M4#")) {

                                            String bytesToSend1 = "*ME,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = hst.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            byte[] theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = fst.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
                                            byte[] theByteArray2 = bytesToSend2.getBytes();
                                            ByteArrayOutputStream output = new ByteArrayOutputStream();
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
                                            String data3 = new String(out);
                                            Btconnection.sendbt(data3);
                                        }
                                        //////////////////////////////////////////////////////////////////////

                                        //if received data is *M5# then tea data will send to machine
                                        else if (message.equals("*M5#")) {
                                            String bytesToSend1 = "*MF,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = htea.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            byte[] theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = ftea.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
                                            byte[] theByteArray2 = bytesToSend2.getBytes();
                                            ByteArrayOutputStream output = new ByteArrayOutputStream();
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
                                            String data4 = new String(out);
                                            Btconnection.sendbt(data4);
                                        }

                                        /////////////////////////////////////////////////////////////////////
                                        //if received data is *M6# then milk data will send to machine
                                        else if (message.equals("*M6#")) {

                                            String bytesToSend1 = "*MG,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = hmilk.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = fmilk.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
                                            byte[] theByteArray2 = bytesToSend2.getBytes();
                                            ByteArrayOutputStream output = new ByteArrayOutputStream();
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
                                            String data5 = new String(out);
                                            Btconnection.sendbt(data5);
                                        }
                                        ////////////////////////////////////////////////////////////////////////////////

                                        //if received data is *M7# then hotwater data will send to machine
                                        else if (message.equals("*M7#")) {

                                            String bytesToSend1 = "*MH,";
                                            theByteArray = bytesToSend1.getBytes();
                                            bytesToSend = hhw.getText().toString().getBytes();
                                            zero();
                                            String bytesToSend4 = ",";
                                            theByteArray3 = bytesToSend4.getBytes();
                                            bytesToSend3 = fhw.getText().toString().getBytes();
                                            zero2();
                                            String bytesToSend2 = "#\n";
                                            byte[] theByteArray2 = bytesToSend2.getBytes();
                                            ByteArrayOutputStream output = new ByteArrayOutputStream();
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
                                            String data6 = new String(out);
                                            Btconnection.sendbt(data6);
                                        }
                                        else if (message.equals("*M8#")) {

                                            Toast.makeText(Mastercount2.this,"Data received",Toast.LENGTH_SHORT).show();

                                        }




                                    }

                                });

                            }


                            else if (message != null && message.length() == 129) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String bhalf = message.substring(3, 9);
                                        hbc.setText(bhalf.replaceAll("[^0-9.]", ""));

                                        String bfull = message.substring(10, 16);
                                        fbc.setText(bfull.replaceAll("[^0-9.]", ""));

                                        String shalf = message.substring(19, 25);
                                        hsc.setText(shalf.replaceAll("[^0-9.]", ""));

                                        String sfull = message.substring(26, 32);
                                        fsc.setText(sfull.replaceAll("[^0-9.]", ""));

                                        String chalf = message.substring(35, 41);
                                        hcof.setText(chalf.replaceAll("[^0-9.]", ""));

                                        String cfull = message.substring(42, 48);
                                        fcof.setText(cfull.replaceAll("[^0-9.]", ""));


                                        String bthalf = message.substring(51, 57);
                                        hbt.setText(bthalf.replaceAll("[^0-9.]", ""));

                                        String btfull = message.substring(58, 64);
                                        fbt.setText(btfull.replaceAll("[^0-9.]", ""));

                                        String sthalf = message.substring(67, 73);
                                        hst.setText(sthalf.replaceAll("[^0-9.]", ""));

                                        String stfull = message.substring(74, 80);
                                        fst.setText(stfull.replaceAll("[^0-9.]", ""));

                                        String thalf = message.substring(83, 89);
                                        htea.setText(thalf.replaceAll("[^0-9.]", ""));

                                        String tfull = message.substring(90, 96);
                                        ftea.setText(tfull.replaceAll("[^0-9.]", ""));


                                        String mhalf = message.substring(99, 105);
                                        hmilk.setText(mhalf.replaceAll("[^0-9.]", ""));

                                        String mfull = message.substring(106, 112);
                                        fmilk.setText(mfull.replaceAll("[^0-9.]", ""));

                                        String hwhalf = message.substring(115, 121);
                                        hhw.setText(hwhalf.replaceAll("[^0-9.]", ""));

                                        String hwfull = message.substring(122);
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
                        //Toast.makeText(Mastercount2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }

    };


    //Adding zero if input datalength less than 4
    void zero() {

        String a = null;
        a1 = new byte[0];
        if (bytesToSend.length == 0) {
            String value = new String(bytesToSend);

            a = "";
            a1 = a.getBytes();
        } else if (bytesToSend.length == 1) {
            String value = new String(bytesToSend);

            a = "00000" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length == 2) {
            String value = new String(bytesToSend);

            a = "0000" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length == 3) {
            String value = new String(bytesToSend);

            a = "000" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length == 4) {
            String value = new String(bytesToSend);

            a = "00" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length == 5) {
            String value = new String(bytesToSend);

            a = "0" + value;
            a1 = a.getBytes();
        } else if (bytesToSend.length == 6) {
            String value = new String(bytesToSend);

            a = value;
            a1 = a.getBytes();

        }
    }


    void zero2() {

        //adding zero
        String aa = null;
        a2 = new byte[0];
        if (bytesToSend3.length < 2) {
            String value1 = new String(bytesToSend3);

            aa = "00000" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length < 3) {
            String value1 = new String(bytesToSend3);

            aa = "0000" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length < 4) {
            String value1 = new String(bytesToSend3);

            aa = "000" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length < 5) {
            String value1 = new String(bytesToSend3);

            aa = "00" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length < 6) {
            String value1 = new String(bytesToSend3);

            aa = "0" + value1;
            a2 = aa.getBytes();
        } else if (bytesToSend3.length == 6) {
            String value1 = new String(bytesToSend3);

            aa = value1;
            a2 = aa.getBytes();
        }

    }
}









