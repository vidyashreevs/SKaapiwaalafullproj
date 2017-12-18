package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class Splash1 extends Activity {

    String deviceBTName,deviceBTAdd;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetoothAdapter;
    ListView listViewPairedDevice;
    ArrayList<String> pairedDeviceArrayList;
    ArrayAdapter<String> pairedDeviceAdapter;
    static UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";
   // ThreadConnectBTdevice myThreadConnectBTdevice;
  static String deviceToConnect;
    DataBaseHandler db = null;
    BluetoothAdapter BA;
   static String secondSubString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash1);
        db = new DataBaseHandler(Splash1.this);
        db.getWritableDatabase();

        listViewPairedDevice = (ListView) findViewById(R.id.pairedlist);


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, "FEATURE_BLUETOOTH NOT support", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //using the well-known SPP UUID
        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this hardware platform", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        try {
            //Turn ON BlueTooth if it is OFF
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Go to setup()  after 4500ms

                        setup();
                    }
                }, 4500);
            } else if (bluetoothAdapter.isEnabled()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Go to setup()  after 1500ms

                        setup();
                    }
                }, 1500);

            }
        }catch (Exception e) {
            e.printStackTrace();


        }
    }


    //Show paired devices list in alert dialog
    private void setup() {

        try {

            final ListView devicesList = new ListView(this);
            devicesList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                pairedDeviceArrayList = new ArrayList<>();
                for (BluetoothDevice device : pairedDevices)
                {
                    deviceBTName = device.getName();
                    deviceBTAdd = device.getAddress();
                    pairedDeviceArrayList.add(deviceBTName + "\n" + deviceBTAdd);

                }

                pairedDeviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pairedDeviceArrayList);
                devicesList.setAdapter(pairedDeviceAdapter);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                dialogBuilder.setTitle("Paired BT Devices");
                dialogBuilder.setIcon(R.drawable.b1);
                dialogBuilder.setView(devicesList);
                final AlertDialog alertDialogObject = dialogBuilder.create();



                //To take selected device mac id
                devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String deviceToConnect = pairedDeviceArrayList.get(position);
                        pairedDeviceAdapter = null;
                        alertDialogObject.dismiss();

                        String[] split = deviceToConnect.split("\n");
                        String firstSubString = split[0];
                        secondSubString = split[1];


                        //start thread
                        Btconnection btthread = new Btconnection();
                        final Thread t = new Thread(btthread);
                        t.start();
                        Intent i = new Intent(Splash1.this, Splashscreen.class);
                        startActivity(i);

                    }
                });

                WindowManager.LayoutParams wmlp = alertDialogObject.getWindow().getAttributes();

                wmlp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_VERTICAL;
              //  wmlp.x = 5;   //x position
                //wmlp.y = 5;   //y position
                alertDialogObject.show();


                alertDialogObject.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        pairedDeviceAdapter = null;
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    public void showMessage(String message) {

    }
}























   /* @Override
    protected void onDestroy() {
        super.onDestroy();

        if(myThreadConnectBTdevice!=null){
            myThreadConnectBTdevice.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                setup();
            }else{
                Toast.makeText(this,
                        "BlueTooth NOT enabled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //Called in ThreadConnectBTdevice once connect successed
    //to start ThreadConnected
    private void startThreadConnected(BluetoothSocket socket){

        // myThreadConnected = new ThreadConnected(socket);
        //  myThreadConnected.start();
    }

    *//*
    ThreadConnectBTdevice:
    Background Thread to handle BlueTooth connecting
    *//*
    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        private ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
                //  textStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // textStatus.setText("something wrong bluetoothSocket.connect(): \n" + eMessage);
                    }
                });

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            if(success){
                //connect successful
                final String msgconnected = "connect successful:\n"
                        + "BluetoothSocket: " + bluetoothSocket + "\n"
                        + "BluetoothDevice: " + bluetoothDevice;

                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        // textStatus.setText(msgconnected);

                        //listViewPairedDevice.setVisibility(View.GONE);
                        // inputPane.setVisibility(View.VISIBLE);
                    }});

                startThreadConnected(bluetoothSocket);
            }else{
                //fail
            }
        }

        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }



}
*/