/*
package com.example.sowmyaram.tablelayoutsample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

*/
/**
 * Created by vidya on 27-05-2017.
 *//*


public class abc {
}




public class Btconnection extends AppCompatActivity implements   Runnable{


    private static UUID myUUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //public static String macId = "00:14:01:25:16:43";


    BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    static BluetoothDevice bd=null;
    static BluetoothSocket bs=null;
    static boolean btconnected=false;
    BufferedReader br=null;
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

    */
/*public void hand(Handler hand)
{
    handler=hand;
}*//*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void sendbt(String data)
    {
        try {

            btoutstream.write(data.getBytes(),0,data.length());
            btoutstream.flush();
        } catch (IOException e) {

            btconnected=false;
            bd=null;
            bs=null;
            e.printStackTrace();
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

    */
/**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     *//*

    @Override
    public void run() {
        Log.d("TAG","CONNC");
      */
/*try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices.size() > 0) {
                for (BluetoothDevice device : devices) {
                    mac = device.getAddress();
                    //connect(mac);
                }
                bd = bluetoothAdapter.getRemoteDevice(mac);
            }

        }catch (Exception e) {
            bd=null;
            bs=null;
            btconnected=false;
            e.printStackTrace();

        }*//*










        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices.size() > 0) {
                for (BluetoothDevice device : devices) {
                    String name = device.getName();
                    if (name != null && name.contains("HC-05")) {

                        mac = device.getAddress();
                        //connect(mac);
                    }
                }
                bd = bluetoothAdapter.getRemoteDevice(mac);
            }

        }catch (Exception e) {
            bd=null;
            bs=null;
            btconnected=false;
            e.printStackTrace();

        }


















      */
/*  try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();


            List<String> s = new ArrayList<String>();
            for(BluetoothDevice bt : devices)
                s.add(bt.getName());
                Iterator<String> itr=s.iterator();

            int i=0;
            while(i<s.size())
            {
                mac=s.get(i);
                i++;
            }

           // if (devices.size() > 0) {
                //for (BluetoothDevice device : devices) {
                    //mac = device.getAddress();

               // }
                bd = bluetoothAdapter.getRemoteDevice(mac);


        }catch (Exception e) {
            bd=null;
            bs=null;
            btconnected=false;
            e.printStackTrace();

        }

*//*


















   */
/*  try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();

            if(devices.isEmpty())
            {
                Toast.makeText(this, "Please pair the device first", Toast.LENGTH_SHORT).show();
            }
            // if (devices.size() > 0) {

            else{
                for (BluetoothDevice iterator : devices) {

                    mac=iterator.getAddress();

                    // mac = device.getAddress();
                    boolean found = true;
                    break;
                    //  mac = device.getAddress();
                    //connect(mac);
                }
                bd = bluetoothAdapter.getRemoteDevice(mac);
            }

        }catch (Exception e) {
            bd=null;
            bs=null;
            btconnected=false;
            e.printStackTrace();

        }*//*




        try {
            // bs = bd.createInsecureRfcommSocketToServiceRecord(myUUID);
            bs = bd.createRfcommSocketToServiceRecord(myUUID);
            bluetoothAdapter.cancelDiscovery();
            bs.connect();
            btconnected = true;


            while (true)
            {
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

            btconnected=false;
            bd=null;
            bs=null;
            return;
        }
    }


}




*/




















        ///////////////////////////



























 /* try {
          bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
          Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();


        if (devices.size() > 0) {
        for (BluetoothDevice device : devices) {
        String name = device.getName();
        if (name != null && name.equals("RNBT-B441")) {

        mac = device.getAddress();
        break;
        //connect(mac);
        }
        }
        bd = bluetoothAdapter.getRemoteDevice(mac);

        }

        } catch (Exception e) {
        bd = null;
        bs = null;
        btconnected = false;
        e.printStackTrace();

        }


        try {
        // bs = bd.createInsecureRfcommSocketToServiceRecord(myUUID);
        bs = bd.createRfcommSocketToServiceRecord(myUUID);
        bluetoothAdapter.cancelDiscovery();
        // bs.connect();
        // btconnected = true;


        if (btconnected = true && bs.isConnected()) {
        if (bs.isConnected()) {
        br = new BufferedReader(new InputStreamReader(bs.getInputStream()));
        in = true;
        btoutstream = bs.getOutputStream();
        ou = true;
        String btdata = br.readLine();
        handler.obtainMessage(0, 0, 0, btdata).sendToTarget();
        }
        } else {
        try {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();


        if (devices.size() > 0) {
        for (BluetoothDevice device : devices) {
        String name = device.getName();
        if (name != null && name.equals("HC-05Test")) {

        mac = device.getAddress();
        //connect(mac);
        }
        }
        bd = bluetoothAdapter.getRemoteDevice(mac);

        abc();


        }

        } catch (Exception e) {
        bd = null;
        bs = null;
        btconnected = false;
        e.printStackTrace();

        }

        }


        } catch (Exception e) {
        // System.out.println("Error " + e.getMessage());
        //return null;

        btconnected = false;
        bd = null;
        bs = null;
        return;
        }
        }


        void abc() {
        try {
        // bs = bd.createInsecureRfcommSocketToServiceRecord(myUUID);
        bs = bd.createRfcommSocketToServiceRecord(myUUID);
        bluetoothAdapter.cancelDiscovery();
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
        bs = null;
        return;
        }
        }

*/