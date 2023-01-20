package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RequestedMovingAssistanceActivity extends AppCompatActivity {


    private UserAccount account;
    private ScheduledServices scheduledServices;
    private RequestedService requestedService;

    private TextView employeeNameEditText;
    private TextView durationEditText;
    private TextView statusText;

    private DatabaseReference movingAssistanceReference;
    private List<MovingAssistance> movingAssistanceList;
    private Button submitBtn;

    //instance variable for Moving
    private String name;
    private String dateOfBirth;
    private String address;
    private String email;
    private String startLocation;
    private String endLocation;
    private int numOfBoxes;
    private int numOfMovers;
    private RequestStatus status;

    private EditText nameText;
    private EditText birthText;
    private EditText addressText;
    private EditText emailText;
    private EditText startLocationText;
    private EditText endLocationText;
    private EditText boxesText;
    private EditText moversText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_moving_assistance);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");
        scheduledServices = (ScheduledServices) intent.getSerializableExtra("scheduledService");
        status = (RequestStatus) intent.getSerializableExtra("serviceStatus");
        int workingTime = getWorkingTime(scheduledServices.getStartHour(), scheduledServices.getStartMin(),
                scheduledServices.getEndHour(), scheduledServices.getEndMin());

        nameText = (EditText) findViewById(R.id.movingCustomerNameEditText);
        birthText = (EditText) findViewById(R.id.movingDateOfBirthEditText);
        addressText = (EditText) findViewById(R.id.movingAddressEditText);
        emailText = (EditText) findViewById(R.id.movingEmailEditText);
        startLocationText = (EditText) findViewById(R.id.startLocationEditText);
        endLocationText = (EditText) findViewById(R.id.endLocationEditText);
        boxesText = (EditText) findViewById(R.id.numberOfBoxesEditText);
        moversText = (EditText) findViewById(R.id.numberOfMoversEditText);


        employeeNameEditText = (TextView) findViewById(R.id.movingEmployeeName);
        durationEditText = (TextView) findViewById(R.id.movingDuration);
        employeeNameEditText.setText(scheduledServices.getEmployeeName());
        statusText = (TextView) findViewById(R.id.movingStatusET);
        durationEditText.setText(Integer.toString(workingTime)+" minutes");

        movingAssistanceList = new LinkedList<>();
        //initialize database
        movingAssistanceReference = FirebaseDatabase.getInstance().getReference("movingAssistanceService");
        movingAssistanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movingAssistanceList.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    MovingAssistance movingAssistance = child.getValue(MovingAssistance.class);
                    movingAssistanceList.add(movingAssistance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (status == RequestStatus.NOT_SUBMIT){
            Button appBtn = (Button) findViewById(R.id.movingApprove);
            Button rejBtn = (Button) findViewById(R.id.movingReject);
            appBtn.setVisibility(View.INVISIBLE);
            rejBtn.setVisibility(View.INVISIBLE);
            statusText.setText("Not submitted");
        }else if (status == RequestStatus.SUBMITTED){

            submitBtn = (Button) findViewById(R.id.movingSubmitBtn);
            submitBtn.setEnabled(false);
            submitBtn.setVisibility(View.INVISIBLE);

            requestedService = (RequestedService) intent.getSerializableExtra("submittedService");

            status = requestedService.getStatus();
            statusText.setText(status.toString());

//            EditText nameText = (EditText) findViewById(R.id.movingCustomerNameEditText);
//            EditText birthText = (EditText) findViewById(R.id.movingDateOfBirthEditText);
//            EditText addressText = (EditText) findViewById(R.id.movingAddressEditText);
//            EditText emailText = (EditText) findViewById(R.id.movingEmailEditText);
//            EditText startLocationText = (EditText) findViewById(R.id.startLocationEditText);
//            EditText endLocationText = (EditText) findViewById(R.id.endLocationEditText);
//            EditText boxesText = (EditText) findViewById(R.id.numberOfBoxesEditText);
//            EditText moversText = (EditText) findViewById(R.id.numberOfMoversEditText);


            nameText.setText(requestedService.getCustomerName());
            birthText.setText(requestedService.getDateOfBirth());
            addressText.setText(requestedService.getAddress());
            emailText.setText(requestedService.getEmail());
            MovingAssistance movingAssistance = (MovingAssistance) requestedService;
            startLocationText.setText(movingAssistance.getStartLocation());
            endLocationText.setText(movingAssistance.getEndLocation());
            boxesText.setText(Integer.toString(movingAssistance.getNumOfBoxes()));
            moversText.setText(Integer.toString(movingAssistance.getNumOfMovers()));
        }
        else{
            submitBtn = (Button) findViewById(R.id.movingSubmitBtn);
            submitBtn.setEnabled(false);
            submitBtn.setVisibility(View.INVISIBLE);

            Button appBtn = (Button) findViewById(R.id.movingApprove);
            Button rejBtn = (Button) findViewById(R.id.movingReject);
            appBtn.setEnabled(false);
            rejBtn.setEnabled(false);

            requestedService = (RequestedService) intent.getSerializableExtra("submittedService");

            status = requestedService.getStatus();
            statusText.setText(status.toString());

//            EditText nameText = (EditText) findViewById(R.id.movingCustomerNameEditText);
//            EditText birthText = (EditText) findViewById(R.id.movingDateOfBirthEditText);
//            EditText addressText = (EditText) findViewById(R.id.movingAddressEditText);
//            EditText emailText = (EditText) findViewById(R.id.movingEmailEditText);
//            EditText startLocationText = (EditText) findViewById(R.id.startLocationEditText);
//            EditText endLocationText = (EditText) findViewById(R.id.endLocationEditText);
//            EditText boxesText = (EditText) findViewById(R.id.numberOfBoxesEditText);
//            EditText moversText = (EditText) findViewById(R.id.numberOfMoversEditText);


            nameText.setText(requestedService.getCustomerName());
            birthText.setText(requestedService.getDateOfBirth());
            addressText.setText(requestedService.getAddress());
            emailText.setText(requestedService.getEmail());
            MovingAssistance movingAssistance = (MovingAssistance) requestedService;
            startLocationText.setText(movingAssistance.getStartLocation());
            endLocationText.setText(movingAssistance.getEndLocation());
            boxesText.setText(Integer.toString(movingAssistance.getNumOfBoxes()));
            moversText.setText(Integer.toString(movingAssistance.getNumOfMovers()));
        }
    }

    public static int getWorkingTime(int startHour, int startMin, int engHour, int endMin){
        return (engHour-startHour)*60 + (endMin-startMin);
    }

    public void submit(View view){


//        EditText nameText = (EditText) findViewById(R.id.movingCustomerNameEditText);
//        EditText birthText = (EditText) findViewById(R.id.movingDateOfBirthEditText);
//        EditText addressText = (EditText) findViewById(R.id.movingAddressEditText);
//        EditText emailText = (EditText) findViewById(R.id.movingEmailEditText);
//        EditText startLocationText = (EditText) findViewById(R.id.startLocationEditText);
//        EditText endLocationText = (EditText) findViewById(R.id.endLocationEditText);
//        EditText boxesText = (EditText) findViewById(R.id.numberOfBoxesEditText);
//        EditText moversText = (EditText) findViewById(R.id.numberOfMoversEditText);
        if (isValid()){
            String key = movingAssistanceReference.push().getKey();
            status = RequestStatus.SUBMITTED;
            try {
                name = nameText.getText().toString();
                dateOfBirth = birthText.getText().toString();
                address = addressText.getText().toString();
                email= emailText.getText().toString();
                startLocation= startLocationText.getText().toString();
                endLocation = endLocationText.getText().toString();

                numOfBoxes = Integer.parseInt(boxesText.getText().toString());
                numOfMovers= Integer.parseInt(moversText.getText().toString());
                requestedService = new MovingAssistance(scheduledServices.getName(),name, address,email,dateOfBirth,status,scheduledServices,startLocation,endLocation,numOfBoxes,numOfMovers,status,key);
                movingAssistanceReference.child(key).setValue(requestedService);

                Intent intent = new Intent(this, CustomerActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }catch(Exception e){
                Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
                toast.setText("Please input valid number");
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
            toast.setText("Please complete all information");
            toast.show();
        }


    }

    public void approve(View view){
        status = RequestStatus.APPROVED;
        String key = ((MovingAssistance)requestedService).getKey();

        name = nameText.getText().toString();
        dateOfBirth = birthText.getText().toString();
        address = addressText.getText().toString();
        email= emailText.getText().toString();
        startLocation= startLocationText.getText().toString();
        endLocation = endLocationText.getText().toString();

        numOfBoxes = Integer.parseInt(boxesText.getText().toString());
        numOfMovers= Integer.parseInt(moversText.getText().toString());
        requestedService = new MovingAssistance(scheduledServices.getName(),name, address,email,dateOfBirth,status,scheduledServices,startLocation,endLocation,numOfBoxes,numOfMovers,status,key);
        movingAssistanceReference.child(key).setValue(requestedService);

        Button appBtn = (Button) findViewById(R.id.movingApprove);
        Button rejBtn = (Button) findViewById(R.id.movingReject);
        appBtn.setEnabled(false);
        rejBtn.setEnabled(false);

        Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setText("Request approved!");
        toast.show();

    }
    public void reject(View view){
        status = RequestStatus.REJECTED;
        String key = ((MovingAssistance)requestedService).getKey();

        name = nameText.getText().toString();
        dateOfBirth = birthText.getText().toString();
        address = addressText.getText().toString();
        email= emailText.getText().toString();
        startLocation= startLocationText.getText().toString();
        endLocation = endLocationText.getText().toString();

        numOfBoxes = Integer.parseInt(boxesText.getText().toString());
        numOfMovers= Integer.parseInt(moversText.getText().toString());
        requestedService = new MovingAssistance(scheduledServices.getName(),name, address,email,dateOfBirth,status,scheduledServices,startLocation,endLocation,numOfBoxes,numOfMovers,status,key);
        movingAssistanceReference.child(key).setValue(requestedService);

        Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setText("Request rejected!");
        toast.show();

        Button appBtn = (Button) findViewById(R.id.movingApprove);
        Button rejBtn = (Button) findViewById(R.id.movingReject);
        appBtn.setEnabled(false);
        rejBtn.setEnabled(false);
    }

    public boolean isValid(){
        return !nameText.getText().toString().equals("") & !birthText.getText().toString().equals("") &
                !addressText.getText().toString().equals("") & !emailText.getText().toString().equals("")&
                !startLocationText.getText().toString().equals("") & !endLocationText.getText().toString().equals("")
                &!boxesText.getText().toString().equals("") &!moversText.getText().toString().equals("");
    }

}