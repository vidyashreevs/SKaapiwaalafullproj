
package com.example.sowmyaram.tablelayoutsample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class Btconnection extends AppCompatActivity implements   Runnable{


    BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    static BluetoothDevice  bd=null;
    static BluetoothSocket bs=null;
    static boolean btconnected=false;
    static BufferedReader br=null;
    static Handler handler=null;
    static boolean in=false;
    static boolean ou=false;
    //static BufferedWriter bw=null;
    static OutputStream btoutstream=null;
    private static final String TAG = null;
    static  BluetoothDevice device;
    String address;
    static String mac;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    //To send data
    public static void sendbt(String data)
    {
        try {
            btoutstream.write(data.getBytes(),0,data.length());
            btoutstream.flush();
        } catch (IOException e) {


            btconnected=false;
            bd=null;
            bs=null;
            //e.printStackTrace();
            // return;
        }
    }

    public static boolean in()

    {
        return in;
    }
    public static boolean out()
    {
        return  ou;
    }


    @Override
    public void run() {
        Log.d("TAG", "CONNC");


       //Receive data

        try {
            bd = bluetoothAdapter.getRemoteDevice(Splash1.secondSubString);
        } catch (Exception e) {
            btconnected = false;
            bd = null;
            bs = null;

            // e.printStackTrace();
        }
        try {
            bs = bd.createRfcommSocketToServiceRecord(Splash1.myUUID);
            bs.connect();
            btconnected = true;
            while (true) {
                if (bs.isConnected()) {
                    br = new BufferedReader(new InputStreamReader(bs.getInputStream()));
                    in = true;
                    btoutstream = bs.getOutputStream();
                    ou = true;
                    String btdata = br.readLine();
                    handler.obtainMessage(0, 0, 0, btdata).sendToTarget();
                }
            }

        } catch (Exception e) {
            // System.out.println("Error " + e.getMessage());
            //return null;

            btconnected = false;
            bd = null;

        }



    }






}





