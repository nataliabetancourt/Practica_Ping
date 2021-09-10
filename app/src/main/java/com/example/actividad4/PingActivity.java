package com.example.actividad4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingActivity extends AppCompatActivity {

    private TextView pingTxt;
    private Button backBtn;
    private String ip, ping;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);

        pingTxt = findViewById(R.id.pingTxt);
        backBtn = findViewById(R.id.backBtn);

        ping = "Waiting\n";

        //IP from main screen
        ip = getIntent().getExtras().getString("ip");

        //Button
        backBtn.setOnClickListener(
                (v)->{
                    finish();
                }
        );

        //Ping
        ping();

    }

    public void ping(){
        new Thread(
                ()->{
                    for (int i = 0; i < 5; i++) {
                        try {
                            InetAddress ipSearch = InetAddress.getByName(ip);
                            connected = ipSearch.isReachable(1000);

                            if (connected){
                                ping += "Recibido\n";
                            } else {
                                ping += "Perdido\n";
                            }

                            //UI thread to see the pings
                            runOnUiThread(
                                    ()->{
                                        pingTxt.setText(ping);
                                    }
                            );

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