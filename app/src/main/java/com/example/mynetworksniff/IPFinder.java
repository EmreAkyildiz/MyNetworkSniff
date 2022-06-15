package com.example.mynetworksniff;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class IPFinder implements Runnable {
    private String[] ips;
    private int low;
    private int high;
    private ArrayList<String> reachables= new ArrayList<>();
    String ipString ="192.168.1.45";



    public IPFinder(String[] ips, int low, int high) {
        this.ips = ips;
        this.low = low;
        this.high = high;
    }

    public IPFinder(String[] ips) {
        this(ips, 0, ips.length - 1);
    }

    @Override
    public void run() {
        String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
        for (int i = low; i <= high; i++) {
            boolean reachable=false;
            String testIp = prefix + String.valueOf(i);

            InetAddress address = null;
            try {
                address = InetAddress.getByName(testIp);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            try {
                Log.d("TAG","TESTİNG:"+testIp);
                reachable = address.isReachable(1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (reachable) {
                Log.i("TAG", "Host: " + "(" + String.valueOf(testIp) + ") is reachable!");
                reachables.add(testIp);
            }
        }

        }

        public int getReachableCount(){
        return reachables.size();
        }
    }
