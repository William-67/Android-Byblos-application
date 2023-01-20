package com.example.myapplicationprojectpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerActivity extends AppCompatActivity {

    private UserAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");

    }

    public void rateService(View view){
        Intent intent = new Intent(this,RateActicity.class);
        intent.putExtra("account",account);
        intent.putExtra("identity",account.getIdentity());
        startActivity(intent);

    }

    public void findOrRequest(View view){
        Intent intent = new Intent(this,ViewAllServicesActivies.class);
        intent.putExtra("account",account);
        intent.putExtra("identity",account.getIdentity());
        startActivity(intent);
    }

    public void viewAllRequestedService(View view){
        Intent intent = new Intent(this,SubmittedServiceActivity.class);
        intent.putExtra("account",account);
        intent.putExtra("identity",account.getIdentity());
        startActivity(intent);
    }

    public void viewAllRate(View view){
        Intent intent = new Intent(this,ViewAllRatingsActivity.class);
        intent.putExtra("account",account);
        intent.putExtra("identity",account.getIdentity());
        startActivity(intent);
    }
}