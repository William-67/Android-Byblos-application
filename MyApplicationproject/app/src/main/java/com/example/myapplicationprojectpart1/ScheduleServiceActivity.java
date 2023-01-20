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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ScheduleServiceActivity extends AppCompatActivity {
    private DatabaseReference serviceReference;
    private DatabaseReference scheduledServiceReference;
    private List<Service> serviceList;
    private List<ScheduledServices> scheduledServicesList;

    private ScheduledServices scheduledService;

    private Spinner serviceSpinner;
    private Spinner dayOfWeekSpinner;

    private List<String> serviceNames;

    private String name;
    private String phone;
    private String address;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private int startHour, startMin, endHour, endMin;
    private DayOfWeek dayOfWeek;
    private UserAccount account;
    private ServiceType eventType;
    private Button cancelBtn;
    private EditText phoneText;
    private EditText addressText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_service);

        //get information
        phoneText = (EditText) findViewById(R.id.phoneET);
        addressText = (EditText) findViewById(R.id.addressET);


        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        Intent intent = getIntent();
        account = (UserAccount) intent.getSerializableExtra("account");
        eventType = (ServiceType) intent.getSerializableExtra("eventType");
        if (eventType == ServiceType.CREATE_SERVICE) {
            cancelBtn.setEnabled(false);
        } else {
            scheduledService = (ScheduledServices) intent.getSerializableExtra("scheduledService");

        }

        serviceNames = new LinkedList<>();
        //initialize spinner
        serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
        dayOfWeekSpinner = (Spinner) findViewById(R.id.licenseTypeEditText);

        //initialize time picker
        startTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
        endTimePicker = (TimePicker) findViewById(R.id.endTimePicker);
        startTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                startHour = i;
                startMin = i1;
            }
        });
        endTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                endHour = i;
                endMin = i1;
            }
        });



        serviceList = new LinkedList<>();
        scheduledServicesList = new LinkedList<>();
        //initialize the database
        serviceReference = FirebaseDatabase.getInstance().getReference("listOfServices");
        scheduledServiceReference = FirebaseDatabase.getInstance().getReference("scheduledServices");
        serviceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get all information from database
                serviceList.clear();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Service service = child.getValue(Service.class);
                    serviceList.add(service);
                    serviceNames.add(service.getName());
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_selectable_list_item, serviceNames);
                serviceSpinner.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set up Spinner

        ArrayAdapter dayOfWeekAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item, DayOfWeek.values());
        dayOfWeekSpinner.setAdapter(dayOfWeekAdapter);
        dayOfWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dayOfWeek = DayOfWeek.values()[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name = serviceNames.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        scheduledServiceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduledServicesList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    scheduledServicesList.add(child.getValue(ScheduledServices.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(eventType==ServiceType.UPDATE_SERVICE){
            serviceReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    serviceNames.clear();
                    serviceList.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Service service = child.getValue(Service.class);
                        serviceList.add(service);
                        serviceNames.add(service.getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item, serviceNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                    serviceSpinner.setAdapter(adapter);
                    serviceSpinner.setSelection(serviceNames.indexOf(scheduledService.getName()));
                    phoneText.setText(scheduledService.getPhone());
                    addressText.setText(scheduledService.getAddress());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            dayOfWeekSpinner.setSelection(Arrays.asList(DayOfWeek.values()).indexOf(scheduledService.getDayOfWeek()));
            startTimePicker.setHour(scheduledService.getStartHour());
            startTimePicker.setMinute(scheduledService.getStartMin());
            endTimePicker.setHour(scheduledService.getEndHour());
            endTimePicker.setMinute(scheduledService.getEndMin());



            if(!scheduledService.getEmployeeName().equals(account.getUserName())){
                cancelBtn.setEnabled(false);
            }
        }


    }


    public void save(View view) {
        if (isValid()) {
            String key = "";
            if(eventType == ServiceType.CREATE_SERVICE){
                key = scheduledServiceReference.push().getKey();
            }else{
                key = scheduledService.getKey();
            }
            phone = phoneText.getText().toString();
            address = addressText.getText().toString();
            ScheduledServices scheduledServices = new ScheduledServices(name, key, phone, address, startHour, startMin, endHour, endMin, dayOfWeek, account.getUserName());
            scheduledServiceReference.child(key).setValue(scheduledServices);
            Intent intent = new Intent(this, EmployeeActivity.class);
            intent.putExtra("account",account);
            intent.putExtra("identity",Identity.employee);
            startActivity(intent);

        }

    }
    public void cancel(View view){
        scheduledServiceReference.child(scheduledService.getKey()).removeValue();
        finish();
    }

    private boolean isValid() {
        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        if (eventType == ServiceType.CREATE_SERVICE) {
            if (ScheduledServices.scheduledOnSameDate(scheduledServicesList, "", name, dayOfWeek)) {
                toast.setText("There is a conflict between the two services!");
                toast.show();
                return false;
            }
        } else {
            if (ScheduledServices.scheduledOnSameDate(scheduledServicesList, scheduledService.getKey(), name, dayOfWeek)) {
                toast.setText("There is a conflict between the two services!");
                toast.show();
                return false;

            }

            if (ScheduledServices.before(endHour, endMin, startHour, startMin)) {
                toast.setText("The end time can not be earlier than the start time!");
                toast.show();
                return false;
            }
            if(eventType==ServiceType.UPDATE_SERVICE){
                if (ScheduledServices.timeConflict(scheduledServicesList, scheduledService.getKey(),account.getUserName(), dayOfWeek, startHour, startMin, endHour, endMin)) {
                    toast.setText("There is a time conflict between the two services!");
                    toast.show();
                    return false;
                }
            }
//            else{
//                if (ScheduledServices.timeConflict(scheduledServicesList, scheduledService.getKey(),account.getUserName(), dayOfWeek, startHour, startMin, endHour, endMin)) {
//                    toast.setText("There is a time conflict between the two services!");
//                    toast.show();
//                    return false;
//                }
//            }

        }
            return true;


    }
}
