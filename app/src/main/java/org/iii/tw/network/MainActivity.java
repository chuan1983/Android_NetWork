package org.iii.tw.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager mgr;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgr = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  //
        NetworkInfo info = mgr.getActiveNetworkInfo();       //取得活動的網路
        if(info!=null && info.isConnected()){               //判斷是否連線
            try {
                Enumeration<NetworkInterface>ifs = NetworkInterface.getNetworkInterfaces();
                while (ifs.hasMoreElements()){
                    NetworkInterface ip = ifs.nextElement();
                    Enumeration<InetAddress>ips = ip.getInetAddresses();
                    while (ips.hasMoreElements()){
                        InetAddress ia = ips.nextElement();
                        Log.d("brad",ia.getHostAddress());
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }else{
            Log.d("brad","Not Connect");
        }
    }
    public void test1(View v){     //try不能為主執行序 一定要包在執行序裡
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    URL url = new URL("http://www.google.com");                //抓取google 的原始碼
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.connect();
//                    InputStream in = conn.getInputStream();
//                    int c;
//                    StringBuffer sb = new StringBuffer();
//                    while ((c = in.read()) != -1) {
//                        sb.append((char) c);
//                    }
//                    in.close();
//                    Log.d("brad", sb.toString());
//                } catch (Exception e) {
//                    Log.d("brad", e.toString());
//                }
//            }
//        }.start();
        MyTread mt1 = new MyTread();
        mt1.start();
    }

    private class MyTread extends Thread{
        @Override
        public void run() {
            super.run();
                try {
                    URL url = new URL("http://data.coa.gov.tw/Service/OpenData/EzgoTravelFoodStay.aspx");                //抓取google 的原始碼
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    data = reader.readLine();   //因為撈的資料只有1列  如果2列以上用while
                    reader.close();
                    Log.d("brad", data);
                } catch (Exception e) {
                    Log.d("brad", e.toString());
                }
            }
        }

}
