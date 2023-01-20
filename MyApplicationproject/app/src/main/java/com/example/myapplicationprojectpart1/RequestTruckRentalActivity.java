package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RequestTruckRentalActivity extends AppCompatActivity {


    private UserAccount account;
    private ScheduledServices scheduledServices;
    private Spinner licenseSpinner;
    private LicenseType licenseType;
    private RequestedService requestedService;
    private DatabaseReference truckRentalReference;
    private List<TruckRental> truckRentalList;

    private TextView employeeNameEditText;
    private TextView durationEditText;
    private TextView statusText;

    private Button submitBtn;
    //instance variable for truckRental
    private String name;
    private String dateOfBirth;
    private String address;
    private String email;
    private String kilometers;
    private String area;
    private RequestStatus status;
    private String pickDate;
    private String returnDate;

    private EditText nameText;
    private EditText birthText;
    private EditText addressText ;
    private EditText emailText ;
    private EditText kilometerText ;
    private EditText locationText ;

    private EditText pickupText ;
    private EditText returnText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_truck_rental);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");
        scheduledServices = (ScheduledServices) intent.getSerializableExtra("scheduledService");
        status = (RequestStatus) intent.getSerializableExtra("serviceStatus");
        int workingTime = getWorkingTime(scheduledServices.getStartHour(), scheduledServices.getStartMin(),
                scheduledServices.getEndHour(), scheduledServices.getEndMin());

        employeeNameEditText = (TextView) findViewById(R.id.truckEmployeeNameET);
        durationEditText = (TextView) findViewById(R.id.truckDurationET);
        statusText = (TextView) findViewById(R.id.truckStatusET);

        employeeNameEditText.setText(scheduledServices.getEmployeeName());
        durationEditText.setText(Integer.toString(workingTime)+" minutes");

        //initialize edittext

        nameText = (EditText) findViewById(R.id.truckCustomerNameEditText);
        birthText = (EditText) findViewById(R.id.truckDateOfBirthEditText);
        addressText = (EditText) findViewById(R.id.truckAddressEditText);
        emailText = (EditText) findViewById(R.id.truckEmailEditText);
        kilometerText = (EditText) findViewById(R.id.truckNumberOfKmEditText);
        locationText = (EditText) findViewById(R.id.truckLocationEditText);

        pickupText = (EditText) findViewById(R.id.truckPickDateEditText);
        returnText = (EditText) findViewById(R.id.truckReturnDateEditText);

        truckRentalList = new LinkedList<>();
        //initialize database
        truckRentalReference = FirebaseDatabase.getInstance().getReference("truckRentalService");
        truckRentalReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                truckRentalList.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    TruckRental truckRental = child.getValue(TruckRental.class);
                    truckRentalList.add(truckRental);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        licenseSpinner = (Spinner) findViewById(R.id.truckLicenseTypeSpinner);
        ArrayAdapter licenseAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,LicenseType.values());
        licenseSpinner.setAdapter(licenseAdapter);
        licenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                licenseType = LicenseType.values()[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        if (status == RequestStatus.NOT_SUBMIT){
            Button appBtn = (Button) findViewById(R.id.truckApprove);
            Button rejBtn = (Button) findViewById(R.id.truckReject);
            appBtn.setVisibility(View.INVISIBLE);
            rejBtn.setVisibility(View.INVISIBLE);
            statusText.setText("Not submitted");
        }else if (status == RequestStatus.SUBMITTED){

            submitBtn = (Button) findViewById(R.id.truckSubmitBtn);
            submitBtn.setEnabled(false);
            submitBtn.setVisibility(View.INVISIBLE);

            requestedService = (RequestedService) intent.getSerializableExtra("submittedService");

            status = requestedService.getStatus();
            statusText.setText(status.toString());


//            EditText nameText = (EditText) findViewById(R.id.truckCustomerNameEditText);
//            EditText birthText = (EditText) findViewById(R.id.truckDateOfBirthEditText);
//            EditText addressText = (EditText) findViewById(R.id.truckAddressEditText);
//            EditText emailText = (EditText) findViewById(R.id.truckEmailEditText);
//            EditText kilometerText = (EditText) findViewById(R.id.truckNumberOfKmEditText);
//            EditText locationText = (EditText) findViewById(R.id.truckLocationEditText);
//            EditText pickupText = (EditText) findViewById(R.id.truckPickDateEditText);
//            EditText returnText = (EditText) findViewById(R.id.truckReturnDateEditText);



            nameText.setText(requestedService.getCustomerName());
            birthText.setText(requestedService.getDateOfBirth());
            addressText.setText(requestedService.getAddress());
            emailText.setText(requestedService.getEmail());
            TruckRental truckRental = (TruckRental) requestedService;
            kilometerText.setText(truckRental.getKilometers());
            locationText.setText(truckRental.getArea());
            pickupText.setText(truckRental.getPickDate());
            returnText.setText(truckRental.getReturnDate());

            LicenseType[] license = LicenseType.values();
            LinkedList<LicenseType> licenseTypeLinkedList = new LinkedList<>(Arrays.asList(license));

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,licenseTypeLinkedList);
            adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
            licenseSpinner.setAdapter(adapter);
            licenseSpinner.setSelection(licenseTypeLinkedList.indexOf(truckRental.getLicenseType()));

        }
        else {
            submitBtn = (Button) findViewById(R.id.truckSubmitBtn);
            submitBtn.setEnabled(false);
            submitBtn.setVisibility(View.INVISIBLE);

            Button appBtn = (Button) findViewById(R.id.truckApprove);
            Button rejBtn = (Button) findViewById(R.id.truckReject);
            appBtn.setEnabled(false);
            rejBtn.setEnabled(false);

            requestedService = (RequestedService) intent.getSerializableExtra("submittedService");

            status = requestedService.getStatus();
            statusText.setText(status.toString());


//            EditText nameText = (EditText) findViewById(R.id.truckCustomerNameEditText);
//            EditText birthText = (EditText) findViewById(R.id.truckDateOfBirthEditText);
//            EditText addressText = (EditText) findViewById(R.id.truckAddressEditText);
//            EditText emailText = (EditText) findViewById(R.id.truckEmailEditText);
//            EditText kilometerText = (EditText) findViewById(R.id.truckNumberOfKmEditText);
//            EditText locationText = (EditText) findViewById(R.id.truckLocationEditText);
//            EditText pickupText = (EditText) findViewById(R.id.truckPickDateEditText);
//            EditText returnText = (EditText) findViewById(R.id.truckReturnDateEditText);

            System.out.println(requestedService);


            nameText.setText(requestedService.getCustomerName());
            birthText.setText(requestedService.getDateOfBirth());
            addressText.setText(requestedService.getAddress());
            emailText.setText(requestedService.getEmail());
            TruckRental truckRental = (TruckRental) requestedService;
            kilometerText.setText(truckRental.getKilometers());
            locationText.setText(truckRental.getArea());
            pickupText.setText(truckRental.getPickDate());
            returnText.setText(truckRental.getReturnDate());


            LicenseType[] license = LicenseType.values();
            LinkedList<LicenseType> licenseTypeLinkedList = new LinkedList<>(Arrays.asList(license));

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,licenseTypeLinkedList);
            adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
            licenseSpinner.setAdapter(adapter);
            licenseSpinner.setSelection(licenseTypeLinkedList.indexOf(truckRental.getLicenseType()));

        }
    }

    public static int getWorkingTime(int startHour, int startMin, int engHour, int endMin){
        return (engHour-startHour)*60 + (endMin-startMin);
    }

    public void submit(View view){



        if (isValid()){
            String key = truckRentalReference.push().getKey();
            status = RequestStatus.SUBMITTED;


            name = nameText.getText().toString();
            dateOfBirth = birthText.getText().toString();
            address = addressText.getText().toString();
            email= emailText.getText().toString();
            kilometers= kilometerText.getText().toString();
            area = locationText.getText().toString();
            pickDate= pickupText.getText().toString();
            returnDate = returnText.getText().toString();
            requestedService = new TruckRental(scheduledServices.getName(), name, address,email,dateOfBirth,status,scheduledServices,kilometers,area,
                    status,licenseType,pickDate,returnDate,key);
            truckRentalReference.child(key).setValue(requestedService);

            Intent intent = new Intent(this, CustomerActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
            toast.setText("Please complete all information");
            toast.show();
        }


    }

    public void approve(View view){
        status = RequestStatus.APPROVED;
        String key = ((TruckRental)requestedService).getKey();

        name = nameText.getText().toString();
        dateOfBirth = birthText.getText().toString();
        address = addressText.getText().toString();
        email= emailText.getText().toString();
        kilometers= kilometerText.getText().toString();
        area = locationText.getText().toString();
        pickDate= pickupText.getText().toString();
        returnDate = returnText.getText().toString();
        requestedService = new TruckRental(scheduledServices.getName(), name, address,email,dateOfBirth,status,scheduledServices,kilometers,area,
                status,licenseType,pickDate,returnDate,key);
        truckRentalReference.child(key).setValue(requestedService);

        Button appBtn = (Button) findViewById(R.id.truckApprove);
        Button rejBtn = (Button) findViewById(R.id.truckReject);
        appBtn.setEnabled(false);
        rejBtn.setEnabled(false);

        Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setText("Request approved!");
        toast.show();

    }
    public void reject(View view){
        status = RequestStatus.REJECTED;
        String key = ((TruckRental)requestedService).getKey();

        name = nameText.getText().toString();
        dateOfBirth = birthText.getText().toString();
        address = addressText.getText().toString();
        email= emailText.getText().toString();
        kilometers= kilometerText.getText().toString();
        area = locationText.getText().toString();
        pickDate= pickupText.getText().toString();
        returnDate = returnText.getText().toString();
        requestedService = new TruckRental(scheduledServices.getName(), name, address,email,dateOfBirth,status,scheduledServices,kilometers,area,
                status,licenseType,pickDate,returnDate,key);
        truckRentalReference.child(key).setValue(requestedService);

        Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setText("Request rejected!");
        toast.show();

        Button appBtn = (Button) findViewById(R.id.truckApprove);
        Button rejBtn = (Button) findViewById(R.id.truckReject);
        appBtn.setEnabled(false);
        rejBtn.setEnabled(false);
    }

    public boolean isValid(){
        return !nameText.getText().toString().equals("") & !birthText.getText().toString().equals("") &
                !addressText.getText().toString().equals("") & !emailText.getText().toString().equals("")&
                !pickupText.getText().toString().equals("") & ! returnText.getText().toString().equals("")
                & !kilometerText.getText().toString().equals("") &!locationText.getText().toString().equals("") ;
    }
}