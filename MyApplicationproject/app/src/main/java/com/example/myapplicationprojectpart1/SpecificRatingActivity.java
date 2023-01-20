package com.example.myapplicationprojectpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SpecificRatingActivity extends AppCompatActivity {
    private Rate rate;
    private Intent intent;
    private TextView nameText;
    private TextView serviceText;
    private TextView rateText;
    private TextView feedbackText;
    private TextView customerText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_rating);
        serviceText= (TextView) findViewById(R.id.good);
        nameText = (TextView) findViewById(R.id.job);
        rateText = (TextView) findViewById(R.id.soFar);
        feedbackText= (TextView) findViewById(R.id.reviewTextView);
        customerText = (TextView) findViewById(R.id.rrrrrrrr);

        intent = getIntent();
        rate = (Rate) intent.getSerializableExtra("rate");

        serviceText.setText(rate.getScheduledServices().getName());
        nameText.setText(rate.getScheduledServices().getEmployeeName());
        rateText.setText(Float.toString(rate.getRate()));
        feedbackText.setText(rate.getFeedback());
        customerText.setText(rate.getCustomer().getUserName());

    }
}