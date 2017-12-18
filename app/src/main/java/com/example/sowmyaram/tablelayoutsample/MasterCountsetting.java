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



public class MasterCountsetting extends Activity {

    TextView totalhalf,totalfull;
    int sumhalf,sumfull;
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
    LinearLayout lcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_count_settings);
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
        bback = (Button) findViewById(R.id.buttonback);
        lcon = (LinearLayout) findViewById(R.id.lcon);

        totalhalf= (TextView) findViewById(R.id.totalhalf);
        totalfull= (TextView) findViewById(R.id.totalfull);

        //To send *MR# cmd while opening blacktea page and if bluetooth is connected then kaapiwaala tab will turn to green
        try{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //totalfull.setText(sum);
                if(Btconnection.btconnected=true&& Btconnection.bs.isConnected()) {
                    lcon.setBackgroundResource(R.drawable.kapiconect);
                }
                else
                {
                    lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                }
                String bytesToSend1 = "*MR#\n";
                byte[] theByteArray = bytesToSend1.getBytes();
                String data = new String(theByteArray);
                Btconnection.sendbt(data);
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(MasterCountsetting.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }

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
                   // Toast.makeText(MasterCountsetting.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BACK button click event
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(MasterCountsetting.this, UserConnecting.class);
                startActivity(i);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), UserConnecting.class);
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
                                       // Toast.makeText(MasterCountsetting.this, "Device Connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                        //Toast.makeText(MasterCountsetting.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                        //Toast.makeText(MasterCountsetting.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }

                                }
                            });
                        }
                        // final String incoming_data = br.readLine();
                        else if (message != null && message.length() == 129) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    String bhalf = message.substring(3, 9);
                                    hbc.setText(bhalf.replaceAll("[^0-9.]", ""));
                                    int bchc = Integer.parseInt(hbc.getText().toString());

                                    String bfull = message.substring(10, 16);
                                    fbc.setText(bfull.replaceAll("[^0-9.]", ""));
                                    int bcfc = Integer.parseInt(fbc.getText().toString());

                                    String shalf = message.substring(19, 25);
                                    hsc.setText(shalf.replaceAll("[^0-9.]", ""));
                                    int schc = Integer.parseInt(hsc.getText().toString());

                                    String sfull = message.substring(26, 32);
                                    fsc.setText(sfull.replaceAll("[^0-9.]", ""));
                                    int scfc = Integer.parseInt(fsc.getText().toString());

                                    String chalf = message.substring(35, 41);
                                    hcof.setText(chalf.replaceAll("[^0-9.]", ""));
                                    int chc = Integer.parseInt(hcof.getText().toString());

                                    String cfull = message.substring(42, 48);
                                    fcof.setText(cfull.replaceAll("[^0-9.]", ""));
                                    int cfc = Integer.parseInt(fcof.getText().toString());


                                    String bthalf = message.substring(51, 57);
                                    hbt.setText(bthalf.replaceAll("[^0-9.]", ""));
                                    int bthc = Integer.parseInt(hbt.getText().toString());

                                    String btfull = message.substring(58, 64);
                                    fbt.setText(btfull.replaceAll("[^0-9.]", ""));
                                    int btfc = Integer.parseInt(fbt.getText().toString());

                                    String sthalf = message.substring(67, 73);
                                    hst.setText(sthalf.replaceAll("[^0-9.]", ""));
                                    int sthc = Integer.parseInt(hst.getText().toString());

                                    String stfull = message.substring(74, 80);
                                    fst.setText(stfull.replaceAll("[^0-9.]", ""));
                                    int stfc = Integer.parseInt(fst.getText().toString());

                                    String thalf = message.substring(83, 89);
                                    htea.setText(thalf.replaceAll("[^0-9.]", ""));
                                    int thc = Integer.parseInt(htea.getText().toString());

                                    String tfull = message.substring(90, 96);
                                    ftea.setText(tfull.replaceAll("[^0-9.]", ""));
                                    int tfc = Integer.parseInt(ftea.getText().toString());


                                    String mhalf = message.substring(99, 105);
                                    hmilk.setText(mhalf.replaceAll("[^0-9.]", ""));
                                    int mhc = Integer.parseInt(hmilk.getText().toString());

                                    String mfull = message.substring(106, 112);
                                    fmilk.setText(mfull.replaceAll("[^0-9.]", ""));
                                    int mfc = Integer.parseInt(fmilk.getText().toString());

                                    String hwhalf = message.substring(115, 121);
                                    hhw.setText(hwhalf.replaceAll("[^0-9.]", ""));


                                    String hwfull = message.substring(122);
                                    fhw.setText(hwfull.replaceAll("[^0-9.]", ""));

                                     sumhalf=bchc+schc+chc+bthc+sthc+thc+mhc;
                                    sumfull=bcfc+scfc+cfc+btfc+stfc+tfc+mfc;

                                    totalhalf.setText(String.valueOf(sumhalf));
                                    totalfull.setText(String.valueOf(sumfull));

                                }
                            });
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                        //Toast.makeText(MasterCountsetting.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

};




    /*public void CreateProgressDialog() {

        progressdialog = new ProgressDialog(MasterCountsetting.this);

        progressdialog.setIndeterminate(false);
        progressdialog.setMessage("Processing Please wait!.......");

        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressdialog.setCancelable(true);

        progressdialog.setMax(100);

        progressdialog.show();

    }

    public void ShowProgressDialog() {


        new Thread(new Runnable() {
            @Override
            public void run() {


                //  if(status <10){

                while (status < 100) {


                    status += 1;

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressdialog.setProgress(status);


                            if (status == 100) {

                                progressdialog.dismiss();
                            }
                        }
                    });
                }
            }
        }).start();



    }*/


}





