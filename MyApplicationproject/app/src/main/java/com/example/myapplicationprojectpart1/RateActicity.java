package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class RateActicity extends AppCompatActivity {
    private RatingBar rb;
    private Spinner spinner;
    private List<ScheduledServices> ratingsList;
    private List<String> names;

    private DatabaseReference scheduledServiceDataBase;
    private DatabaseReference rateRef;
    private ScheduledServices service;
    private TextView feedbackTextView;
    private float rating;
    private String feedback;
    private String key;
    private UserAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_acticity);
        rb=(RatingBar) findViewById(R.id.ratingBar);
        rateRef=FirebaseDatabase.getInstance().getReference("rating");
        scheduledServiceDataBase = FirebaseDatabase.getInstance().getReference("scheduledServices");

        Intent theIntent = getIntent();
        account = (UserAccount) theIntent.getSerializableExtra("account");


        ratingsList=new LinkedList<>();
        names = new LinkedList<>();
        spinner = (Spinner) findViewById(R.id.scheduleRateSpinner);
        feedbackTextView = (TextView) findViewById(R.id.txt);



        scheduledServiceDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ratingsList.clear();
                names.clear();
                for (DataSnapshot children: snapshot.getChildren()){
                    ScheduledServices scheduledServices = children.getValue(ScheduledServices.class);
                    ratingsList.add(scheduledServices);
                    names.add(scheduledServices.getName()+" by "+scheduledServices.getEmployeeName()+" on "+scheduledServices.getDayOfWeek());
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_selectable_list_item, names);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 service = ratingsList.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void submit(View view){
        key = rateRef.push().getKey();
        rating = rb.getRating();
        if (feedbackTextView.getText().toString().equals("")){
            feedback = "There are no reviews for this user";
        }else{
            feedback = feedbackTextView.getText().toString();
        }
        Rate rate = new Rate(service,rating,feedback,key,account);
        rateRef.child(key).setValue(rate);

        Intent intent = new Intent(this,CustomerActivity.class);
        intent.putExtra("account",account);
        intent.putExtra("identity",account.getIdentity());
        startActivity(intent);
    }

}