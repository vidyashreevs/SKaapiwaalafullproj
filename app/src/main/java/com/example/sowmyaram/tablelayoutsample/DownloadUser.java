package com.example.sowmyaram.tablelayoutsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



public class DownloadUser extends ActionBarActivity {
    String strReceived = null;
    InputStream in = null;
    BluetoothAdapter bluetoothAdapter;
    EditText inputField;
    Button download,d2;
    byte[] bytesToSend, a2, theByteArray;
    LinearLayout lcon;
    private String bytesToSend1;
    TextView tv;
    String a;


    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_tab);
        Btconnection.handler = handler;

        //initialising ID's

        lcon = (LinearLayout) findViewById(R.id.lcon);
        download = (Button) findViewById(R.id.dwnld);
        tv = (TextView) findViewById(R.id.tv);
        inputField= (EditText) findViewById(R.id.url);

        inputField.setSelection(inputField.getText().length());

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if(!TextUtils.isEmpty(restoredText)){
            inputField.setText(restoredText);
            inputField.setSelection(inputField.getText().length());
        }



        //To send CON cmd and *BRC# cmd while opening brewcoffee page
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Btconnection.btconnected = true) {
                        if (Btconnection.btconnected = true && Btconnection.bs.isConnected()) {
                            lcon.setBackgroundResource(R.drawable.kapiconect);
                        } else {
                            lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                        }
                    } else if (Btconnection.bs.isConnected() != true) {

                        String bytesToSend1 = "*CON#";
                        byte[] theByteArray = bytesToSend1.getBytes();
                        String data = new String(theByteArray);
                        Btconnection.sendbt(data);
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(DownloadAdmin.this, "Connection Problem", Toast.LENGTH_SHORT).show();
        }


        download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String bytesToSend1 = "*RDA#\n";
                    byte[] theByteArray = bytesToSend1.getBytes();
                    String data = new String(theByteArray);
                    Btconnection.sendbt(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    //  Toast.makeText(DownloadAdmin.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                }


                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("File uploading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                progressBarStatus = 0;

                //  fileSize = 0;
                new Thread(new Runnable() {
                    public void run() {
                        progressBarbHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }
                }).start();





            }
        });


        inputField= (EditText) findViewById(R.id.url);
        inputField.setVisibility(View.INVISIBLE);
        Button btnurl= (Button) findViewById(R.id.btnurl);

        btnurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputField.isShown()) {
                    inputField.setVisibility(View.GONE);
                } else {
                    inputField.setVisibility(View.VISIBLE);
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
                            if (Btconnection.btconnected = false) {

                                String bytesToSend1 = "*CON#";
                                byte[] theByteArray = bytesToSend1.getBytes();
                                String data = new String(theByteArray);
                                Btconnection.sendbt(data);
                            }
                            String bytesToSend1 = "*CON#";
                            byte[] theByteArray = bytesToSend1.getBytes();
                            String data = new String(theByteArray);
                            Btconnection.sendbt(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Toast.makeText(DownloadAdmin.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }



    @Override
    public void onBackPressed() {

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("text", inputField.getText().toString());
        editor.commit();

        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), UserConnecting.class);
        startActivity(i);
        finish();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //To receive data from machine
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    try {
                        final String message = (String) msg.obj;

                        if ((message.startsWith("*")) && (message.endsWith("#")) && message.length() == 5) {
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    if (message.equals("*CON#")) {

                                        lcon.setBackgroundResource(R.drawable.kapiconect);
                                    } else if (message.equals("*NOC#")) {
                                        // Toast.makeText(DownloadAdmin.this, "Device Busy,not connected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    } else if (message.equals("*DCN#")) {
                                        // Toast.makeText(DownloadAdmin.this, "Device disconnected", Toast.LENGTH_SHORT).show();
                                        lcon.setBackgroundResource(R.drawable.kaapiwala_tab);
                                    }

                                }
                            });
                            break;
                        } else //if ((message.startsWith("*")) && (message.endsWith("#")))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    a=message;
                                    Thread t = new Thread() {

                                        public void run() {

                                            post(a);
                                        }
                                    };
                                    t.start();
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Toast.makeText(DownloadAdmin.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

        private void post(String a) {


            String str = DownloadUser.this.a;
            // Creating HTTP client
            HttpClient httpClient = new DefaultHttpClient();
            // Creating HTTP Post

            String url=inputField.getText().toString();
            HttpPost httpPost = new HttpPost(
                    url);


            // Building post parameters
            // key and value pair
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("rdata", str));
      /*  nameValuePair.add(new BasicNameValuePair("data",
                "Hi, trying Android HTTP post!"));*/

            // Url Encoding the POST parameters
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }

            // Making HTTP Request
            try {
                final HttpResponse response = httpClient.execute(httpPost);
                Thread.sleep(2000);
                String responseBody = null;

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseBody = EntityUtils.toString(entity);

                /*JSONObject j = new JSONObject(responseBody);
                responseBody = j.getString("data");*/

                    Log.d("TAG", "Http post Response: " + responseBody);
                }
                Log.d("TAG", responseBody);

                final String finalResponseBody = responseBody;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv.setText(finalResponseBody);
                        progressBar.dismiss();
                    }
                });
            } catch (ClientProtocolException e) {
                // writing exception to log
                e.printStackTrace();
            } catch (IOException e) {
                // writing exception to log
                e.printStackTrace();

            } /*catch (JSONException e) {
            e.printStackTrace();
        }*/ catch (InterruptedException e) {


            }
        }


    };
}
