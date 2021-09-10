package com.example.actividad4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText ipA, ipB, ipC, ipD;
    private Button pingBtn, hostBtn;
    private TextView myIPTxt;
    private String ip;
    private int partA, partB, partC, partD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipA = findViewById(R.id.ipA);
        ipB = findViewById(R.id.ipB);
        ipC = findViewById(R.id.ipC);
        ipD = findViewById(R.id.ipD);
        pingBtn = findViewById(R.id.pingBtn);
        hostBtn = findViewById(R.id.hostBtn);
        myIPTxt = findViewById(R.id.myIPTxt);

        //Set IP
        createIP();

        //Buttons
        pingBtn.setOnClickListener(
                (v)->{
                    //Turning text edit into int
                    partA = Integer.parseInt(ipA.getText().toString());
                    partB = Integer.parseInt(ipB.getText().toString());
                    partC = Integer.parseInt(ipC.getText().toString());
                    partD = Integer.parseInt(ipD.getText().toString());

                    //If all the entries are numbers between 0 and 255, create the IP and switch screens
                    if (validate(partA) && validate(partB) && validate(partC) && validate(partD)){
                        ip = partA + "." + partB + "." + partC + "." + partD;
                        Intent ping = new Intent(this, PingActivity.class);
                        ping.putExtra("ip", ip);
                        startActivity(ping);
                        ipA.getText().clear();
                        ipB.getText().clear();
                        ipC.getText().clear();
                        ipD.getText().clear();
                    } else {
                        ipA.getText().clear();
                        ipB.getText().clear();
                        ipC.getText().clear();
                        ipD.getText().clear();
                        Toast.makeText(this, "Entrada no válida", Toast.LENGTH_SHORT);
                    }
                }
        );

        hostBtn.setOnClickListener(
                (v)->{
                    //Switching to host screen
                    Intent host = new Intent(this, HostActivity.class);
                    startActivity(host);
                    ipA.getText().clear();
                    ipB.getText().clear();
                    ipC.getText().clear();
                    ipD.getText().clear();
                }
        );

    }

    public boolean validate(int entry){
        if (0 > entry || entry > 250) {
            return false;
        }
        return true;
    }

    public void createIP(){
        new Thread(
                ()->{
                    try {
                        //Get computer IP
                        InetAddress myIP = InetAddress.getLocalHost();

                        runOnUiThread(
                                ()->{
                                    myIPTxt.setText(myIP.getHostAddress());
                                }
                        );
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

}