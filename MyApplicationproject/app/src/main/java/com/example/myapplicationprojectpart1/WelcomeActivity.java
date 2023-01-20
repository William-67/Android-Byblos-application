package com.example.myapplicationprojectpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    private TextView welcomeText;
    private Identity identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeText=(TextView) findViewById(R.id.welcomeText);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        Identity identity=(Identity) intent.getSerializableExtra("identity");
        UserAccount account = (UserAccount) intent.getSerializableExtra("account");
        StringBuffer buffer = new StringBuffer();
        buffer.append("Welcome "+userName+" ! You are log in as "+identity).
                append(System.lineSeparator()).append("Please wait for a few second");
        welcomeText.setText(buffer.toString());



        if (identity == Identity.administrator){
            Intent localIntent = new Intent(this,AdminActivity.class);
            localIntent.putExtra("userName",userName);
            localIntent.putExtra("identity",identity);
            localIntent.putExtra("account",account);
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    startActivity(localIntent);
                }
            };
            timer.schedule(tast,2000);
        }else if (identity == Identity.employee){
            Intent localIntent = new Intent(this,EmployeeActivity.class);
            localIntent.putExtra("userName",userName);
            localIntent.putExtra("identity",identity);
            localIntent.putExtra("account",account);
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    startActivity(localIntent);
                }
            };
            timer.schedule(tast,2000);
        }
        else if (identity == Identity.customer){
            Intent localIntent = new Intent(this,CustomerActivity.class);
            localIntent.putExtra("userName",userName);
            localIntent.putExtra("identity",identity);
            localIntent.putExtra("account",account);
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    startActivity(localIntent);
                }
            };
            timer.schedule(tast,2000);
        }
    }
}