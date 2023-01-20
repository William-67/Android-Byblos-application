package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;


public class EmployeeActivity extends AppCompatActivity {

    private UserAccount account;
    private Identity identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");
        identity = (Identity) intent.getSerializableExtra("identity");


    }

    public void scheduleService(View view){
        Intent intent = new Intent(this,ScheduleServiceActivity.class);
        intent.putExtra("account", account);
        intent.putExtra("eventType",ServiceType.CREATE_SERVICE);
        startActivity(intent);
    }
    public void viewAllScheduledServices(View view){
        Intent intent = new Intent(this, ViewAllServicesActivies.class);
        intent.putExtra("account", account);
        intent.putExtra("identity",identity);

        startActivity(intent);
    }
    public void viewSubmittedRequest(View view){
        Intent intent = new Intent(this,SubmittedServiceActivity.class);
        intent.putExtra("account", account);
        intent.putExtra("identity",identity);
        startActivity(intent);
    }
    public void viewAllRate(View view){
        Intent intent = new Intent(this,ViewAllRatingsActivity.class);
        intent.putExtra("account",account);
        intent.putExtra("identity",account.getIdentity());
        startActivity(intent);
    }
}