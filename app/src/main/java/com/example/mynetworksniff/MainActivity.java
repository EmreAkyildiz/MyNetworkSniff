package com.example.mynetworksniff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "nstask";

    public static final int ARRAY_SIZE = 255;
    public static final int FINDERS = 255;
    public static String[] array;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);

        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void init() throws InterruptedException{
        int sizeOfOne = ARRAY_SIZE/FINDERS;

        IPFinder[] finders = new IPFinder[FINDERS];
        Thread[] threads = new Thread[FINDERS];
        int reachableIPS =0;

        for(int i=0; i<FINDERS; i++) {
            int high = i < (FINDERS-1) ? (i+1) * sizeOfOne - 1 : ARRAY_SIZE-1;
            finders[i] = new IPFinder(array, i * sizeOfOne, high);
            threads[i] = new Thread(finders[i]);
            threads[i].start();
        }
        for(int i=0; i<FINDERS; i++) {
            threads[i].join();
            reachableIPS+= finders[i].getReachableCount();
        }
        Log.d("TOTAL",String.valueOf(reachableIPS));
        textView.setText(String.valueOf(reachableIPS));

}



}