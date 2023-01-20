package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.LinkedList;
import java.util.List;


public class AddServiceActivity extends AppCompatActivity {

    private EditText priceText;
    private EditText serviceNameText;

    private LinearLayout infoLayout;

    private List<Service> listOfServices = new LinkedList<>();

    private DatabaseReference serviceDatabase = FirebaseDatabase.getInstance().getReference("listOfServices");

    private String serName;
    private Service service;

    private ServiceType serviceType;

    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        priceText = (EditText) findViewById(R.id.servicePriceET);
        serviceNameText = (EditText) findViewById(R.id.serviceNameET);

        infoLayout = (LinearLayout) findViewById(R.id.CILayout);

        Intent intent =getIntent();
        serviceType = (ServiceType) intent.getSerializableExtra("ServiceType");
        if (serviceType == ServiceType.UPDATE_SERVICE){
            service = (Service) intent.getSerializableExtra("service");
            serviceNameText.setText(service.getName());
            priceText.setText(Double.toString(service.getPrice()));
            List<String> info = service.getDetail();
            if (info!=null){
                for (String inf:info){
                    EditText editText = new EditText(getApplicationContext());
                    editText.setText(inf);
                    infoLayout.addView(editText);
                }
            }

        }else if (serviceType == ServiceType.CREATE_SERVICE){
            Button delete = (Button) findViewById(R.id.deleteservice);
            delete.setEnabled(false);
        }




        serviceDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfServices.clear();
                for (DataSnapshot children: snapshot.getChildren()){
                    Service service = children.getValue(Service.class);
                    listOfServices.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addService(View view){
        serName = serviceNameText.getText().toString();
        if (isValid()){
            List<String> info = new LinkedList<>();
            for (int i = 0; i<infoLayout.getChildCount();i++){
                EditText editText = (EditText) infoLayout.getChildAt(i);
                String text = editText.getText().toString();
                if (!text.equals("")){
                    info.add(text);
                }
            }

            //random key
            String key = "";
            if (serviceType ==ServiceType.CREATE_SERVICE){
                key = serviceDatabase.push().getKey();
            }else if (serviceType==ServiceType.UPDATE_SERVICE){
                key = service.getKey();
            }

            Service service = new Service(serName,price,key,info);
            serviceDatabase.child(key).setValue(service);
            finish();

        }
    }

    public void addInfo(View view){
        EditText text = new EditText(getApplicationContext());
        infoLayout.addView(text);
    }

    public void deleteInfo(View view) {
        if (infoLayout.getChildCount()>0){
            infoLayout.removeViewAt(infoLayout.getChildCount()-1);
        }

    }
    public void deleteService(View view){
        serviceDatabase.child(service.getKey()).removeValue();
        finish();
    }


    private boolean isValid(){

        //check price
        String strPrice = priceText.getText().toString();
        try {
            price = Double.parseDouble(strPrice);
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Invalid price!",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }


        //check serveice name
        if (serName.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Empty service name is not accpeted!",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }


        //check duplicate
        if (serviceType ==ServiceType.CREATE_SERVICE && Service.isDuplicate(listOfServices,serName)){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Service already exists!",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }






        return true;
    }
}