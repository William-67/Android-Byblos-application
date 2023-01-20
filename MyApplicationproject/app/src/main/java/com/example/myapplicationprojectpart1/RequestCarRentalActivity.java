package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class RequestCarRentalActivity extends AppCompatActivity {

    private UserAccount account;
    private ScheduledServices scheduledServices;
    private Spinner licenseSpinner;
    private Spinner carTypeSpinner;

    private Button submitBtn;


    private RequestedService requestedService;

    private TextView employeeNameEditText;
    private TextView durationEditText;
    private TextView statusText;
    private DatabaseReference carRentalReference;
    private List<CarRental> carRentalList;

    //instance variable for requestService
    private String name;
    private String dateOfBirth;
    private String address;
    private String email;
    private LicenseType licenseType;
    private CarType carType;
    private RequestStatus status;
    private String pickDate;
    private String returnDate;

    private EditText nameText;
    private EditText birthText;
    private EditText addressText;
    private EditText emailText;
    private EditText pickupText;
    private EditText returnText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_car_rental);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");
        scheduledServices = (ScheduledServices) intent.getSerializableExtra("scheduledService");
        status = (RequestStatus) intent.getSerializableExtra("serviceStatus");
        int workingTime = getWorkingTime(scheduledServices.getStartHour(), scheduledServices.getStartMin(),
                scheduledServices.getEndHour(), scheduledServices.getEndMin());

        employeeNameEditText = (TextView) findViewById(R.id.carEmployeeNameET);
        durationEditText = (TextView) findViewById(R.id.carDurationET);
        statusText = (TextView) findViewById(R.id.carStatusET);

        nameText = (EditText) findViewById(R.id.carCustomerNameEditText);
        birthText = (EditText) findViewById(R.id.carDateOfBirthEditText);
        addressText = (EditText) findViewById(R.id.carAddressEditText);
        emailText = (EditText) findViewById(R.id.carEmailEditText);
        pickupText = (EditText) findViewById(R.id.carPickDateEditText);
        returnText = (EditText) findViewById(R.id.carReturnDateEditText);


        employeeNameEditText.setText(scheduledServices.getEmployeeName());
        durationEditText.setText(Integer.toString(workingTime)+" minutes");

        carRentalList = new LinkedList<>();

        //initialize Reference
        carRentalReference = FirebaseDatabase.getInstance().getReference("carRentalService");
        carRentalReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                carRentalList.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    CarRental carRental = child.getValue(CarRental.class);
                    carRentalList.add(carRental);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //initialize Spinner
        licenseSpinner = (Spinner) findViewById(R.id.carLicenseTypeSpinner);
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

        carTypeSpinner = (Spinner) findViewById(R.id.carTypeSpinner);
        ArrayAdapter carTypeAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,CarType.values());
        carTypeSpinner.setAdapter(carTypeAdapter);
        carTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carType = CarType.values()[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (status == RequestStatus.NOT_SUBMIT){
            Button appBtn = (Button) findViewById(R.id.carApprove);
            Button rejBtn = (Button) findViewById(R.id.carReject);
            appBtn.setVisibility(View.INVISIBLE);
            rejBtn.setVisibility(View.INVISIBLE);
            statusText.setText("Not submitted");
        }else if (status ==RequestStatus.SUBMITTED){

            submitBtn = (Button) findViewById(R.id.carSubmitBtn);
            submitBtn.setEnabled(false);
            submitBtn.setVisibility(View.INVISIBLE);

            requestedService = (RequestedService) intent.getSerializableExtra("submittedService");

            status = requestedService.getStatus();
            statusText.setText(status.toString());

//
//            EditText nameText = (EditText) findViewById(R.id.carCustomerNameEditText);
//            EditText birthText = (EditText) findViewById(R.id.carDateOfBirthEditText);
//            EditText addressText = (EditText) findViewById(R.id.carAddressEditText);
//            EditText emailText = (EditText) findViewById(R.id.carEmailEditText);
//            EditText pickupText = (EditText) findViewById(R.id.carPickDateEditText);
//            EditText returnText = (EditText) findViewById(R.id.carReturnDateEditText);



            nameText.setText(requestedService.getCustomerName());
            birthText.setText(requestedService.getDateOfBirth());
            addressText.setText(requestedService.getAddress());
            emailText.setText(requestedService.getEmail());
            CarRental carRental = (CarRental) requestedService;
            pickupText.setText(carRental.getReturnDate());
            returnText.setText(carRental.getReturnDate());


            LicenseType[] license = LicenseType.values();
            LinkedList<LicenseType> licenseTypeLinkedList = new LinkedList<>(Arrays.asList(license));
            CarType[] carTypes = CarType.values();
            LinkedList<CarType> carTypeLinkedList = new LinkedList<>(Arrays.asList(carTypes));

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,licenseTypeLinkedList);
            adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
            licenseSpinner.setAdapter(adapter);
            licenseSpinner.setSelection(licenseTypeLinkedList.indexOf(carRental.getLicenseType()));

            ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,carTypeLinkedList);
            adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
            carTypeSpinner.setAdapter(adapter1);
            carTypeSpinner.setSelection(carTypeLinkedList.indexOf(carRental.getCarType()));

        }else{

            submitBtn = (Button) findViewById(R.id.carSubmitBtn);
            submitBtn.setEnabled(false);
            submitBtn.setVisibility(View.INVISIBLE);

            Button appBtn = (Button) findViewById(R.id.carApprove);
            Button rejBtn = (Button) findViewById(R.id.carReject);
            appBtn.setEnabled(false);
            rejBtn.setEnabled(false);

            requestedService = (RequestedService) intent.getSerializableExtra("submittedService");

            status = requestedService.getStatus();
            statusText.setText(status.toString());


//            EditText nameText = (EditText) findViewById(R.id.carCustomerNameEditText);
//            EditText birthText = (EditText) findViewById(R.id.carDateOfBirthEditText);
//            EditText addressText = (EditText) findViewById(R.id.carAddressEditText);
//            EditText emailText = (EditText) findViewById(R.id.carEmailEditText);
//            EditText pickupText = (EditText) findViewById(R.id.carPickDateEditText);
//            EditText returnText = (EditText) findViewById(R.id.carReturnDateEditText);


            nameText.setText(requestedService.getCustomerName());
            birthText.setText(requestedService.getDateOfBirth());
            addressText.setText(requestedService.getAddress());
            emailText.setText(requestedService.getEmail());
            CarRental carRental = (CarRental) requestedService;
            pickupText.setText(carRental.getReturnDate());
            returnText.setText(carRental.getReturnDate());


            LicenseType[] license = LicenseType.values();
            LinkedList<LicenseType> licenseTypeLinkedList = new LinkedList<>(Arrays.asList(license));
            CarType[] carTypes = CarType.values();
            LinkedList<CarType> carTypeLinkedList = new LinkedList<>(Arrays.asList(carTypes));

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,licenseTypeLinkedList);
            adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
            licenseSpinner.setAdapter(adapter);
            licenseSpinner.setSelection(licenseTypeLinkedList.indexOf(carRental.getLicenseType()));

            ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item,carTypeLinkedList);
            adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
            carTypeSpinner.setAdapter(adapter1);
            carTypeSpinner.setSelection(carTypeLinkedList.indexOf(carRental.getCarType()));
        }



    }

    public static int getWorkingTime(int startHour, int startMin, int engHour, int endMin){
        return (engHour-startHour)*60 + (endMin-startMin);
    }


    public void submit(View view){


//        EditText nameText = (EditText) findViewById(R.id.carCustomerNameEditText);
//        EditText birthText = (EditText) findViewById(R.id.carDateOfBirthEditText);
//        EditText addressText = (EditText) findViewById(R.id.carAddressEditText);
//        EditText emailText = (EditText) findViewById(R.id.carEmailEditText);
//        EditText pickupText = (EditText) findViewById(R.id.carPickDateEditText);
//        EditText returnText = (EditText) findViewById(R.id.carReturnDateEditText);

        if (isValid()){
            String key = carRentalReference.push().getKey();
            status = RequestStatus.SUBMITTED;

            name = nameText.getText().toString();
            dateOfBirth = birthText.getText().toString();
            address = addressText.getText().toString();
            email= emailText.getText().toString();
            pickDate= pickupText.getText().toString();
            returnDate= returnText.getText().toString();
            requestedService = new CarRental(scheduledServices.getName(), name, address,email,dateOfBirth,status,scheduledServices,licenseType,carType,
                    pickDate,returnDate,status,key);
            carRentalReference.child(key).setValue(requestedService);

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
        String key = ((CarRental)requestedService).getKey();

        name = nameText.getText().toString();
        dateOfBirth = birthText.getText().toString();
        address = addressText.getText().toString();
        email= emailText.getText().toString();
        pickDate= pickupText.getText().toString();
        returnDate= returnText.getText().toString();
        requestedService = new CarRental(scheduledServices.getName(), name, address,email,dateOfBirth,status,scheduledServices,licenseType,carType,
                pickDate,returnDate,status,key);
        carRentalReference.child(key).setValue(requestedService);

        Button appBtn = (Button) findViewById(R.id.carApprove);
        Button rejBtn = (Button) findViewById(R.id.carReject);
        appBtn.setEnabled(false);
        rejBtn.setEnabled(false);

        Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setText("Request approved!");
        toast.show();

    }
    public void reject(View view){
        status = RequestStatus.REJECTED;
        String key = ((CarRental)requestedService).getKey();

        name = nameText.getText().toString();
        dateOfBirth = birthText.getText().toString();
        address = addressText.getText().toString();
        email= emailText.getText().toString();
        pickDate= pickupText.getText().toString();
        returnDate= returnText.getText().toString();
        requestedService = new CarRental(scheduledServices.getName(), name, address,email,dateOfBirth,status,scheduledServices,licenseType,carType,
                pickDate,returnDate,status,key);
        carRentalReference.child(key).setValue(requestedService);

        Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        toast.setText("Request rejected!");
        toast.show();

        Button appBtn = (Button) findViewById(R.id.carApprove);
        Button rejBtn = (Button) findViewById(R.id.carReject);
        appBtn.setEnabled(false);
        rejBtn.setEnabled(false);
    }

    public boolean isValid(){
        return !nameText.getText().toString().equals("") & !birthText.getText().toString().equals("") &
                !addressText.getText().toString().equals("") & !emailText.getText().toString().equals("")&
                !pickupText.getText().toString().equals("") & ! returnText.getText().toString().equals("");
    }
}