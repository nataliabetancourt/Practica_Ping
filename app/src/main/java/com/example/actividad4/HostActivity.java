package com.example.actividad4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostActivity extends AppCompatActivity {

    private TextView hostTxt;
    private Button backBtn;
    private String hostConnected;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        hostTxt = findViewById(R.id.hostTxt);
        backBtn = findViewById(R.id.backBtn2);

        //Button
        backBtn.setOnClickListener(
                (v)->{
                    finish();
                }
        );

        searchHost();
    }

    public void searchHost(){
        //On thread to make sure you can see the design while searching for hosts
        new Thread(
                ()->{
                    //My computer IP is 192.168.39.171 -> search for all hosts in this red

                    for (int i = 1; i < 255; i++) {
                        try {
                            //Go from 1-254 to search for hosts on the red
                            InetAddress allIP = InetAddress.getByName("192.168.39."+i);

                            //Check if any of the IP's are connected
                            isConnected = allIP.isReachable(3000);

                            if (isConnected) {
                                hostConnected = allIP.getHostAddress();

                                //Write down the IPs that are connected
                                runOnUiThread(
                                        ()->{
                                            hostTxt.setText(hostConnected);
                                        }
                                );
                            }

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

    }
}