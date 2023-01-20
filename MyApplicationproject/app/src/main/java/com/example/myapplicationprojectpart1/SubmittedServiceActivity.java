package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SubmittedServiceActivity extends AppCompatActivity {

    private UserAccount account;
    private Identity identity;
    private Button submitBtn;

    private RequestedService requestedService;
    private List<CarRental> carList;
    private List<TruckRental> truckList;
    private List<MovingAssistance> movingList;

    private ListView carRentalListView;
    private ListView truckRentalListView;
    private ListView movingAssistanceListView;

    private DatabaseReference carReference;
    private DatabaseReference truckReference;
    private DatabaseReference movingReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_service);

        carList = new LinkedList<>();
        truckList = new LinkedList<>();
        movingList = new LinkedList<>();

        Intent intent = getIntent();
        identity = (Identity) intent.getSerializableExtra("identity");
        account = (UserAccount) intent.getSerializableExtra("account");



        //initialize database
        carReference = FirebaseDatabase.getInstance().getReference("carRentalService");
        truckReference = FirebaseDatabase.getInstance().getReference("truckRentalService");
        movingReference = FirebaseDatabase.getInstance().getReference("movingAssistanceService");

        //car listview
        carReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carList.clear();
                List<Map<String,String>> data = new LinkedList<>();
                for (DataSnapshot child: snapshot.getChildren()){
                    CarRental requestedService = child.getValue(CarRental.class);
                    carList.add(requestedService);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("key",requestedService.getStatus().toString());
                    map.put("customer", requestedService.getCustomerName());
                    map.put("email",requestedService.getEmail());
                    data.add(map);
                }
                String [] from = new String []{"key","customer","email"} ;
                int [] to = new int[]{R.id.submitKeyTextView,R.id.customerNameTextView,R.id.emailTextView};
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.submitted_service_layout,from,to);
                carRentalListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // truck listView
        truckReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                truckList.clear();
                List<Map<String,String>> data = new LinkedList<>();
                for (DataSnapshot child: snapshot.getChildren()){
                    TruckRental requestedService = child.getValue(TruckRental.class);
                    truckList.add(requestedService);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("key",requestedService.getStatus().toString());
                    map.put("customer", requestedService.getCustomerName());
                    map.put("email",requestedService.getEmail());
                    data.add(map);
                }
                String [] from = new String []{"key","customer","email"} ;
                int [] to = new int[]{R.id.submitKeyTextView,R.id.customerNameTextView,R.id.emailTextView};
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.submitted_service_layout,from,to);
                truckRentalListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        movingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movingList.clear();
                List<Map<String,String>> data = new LinkedList<>();
                for (DataSnapshot child: snapshot.getChildren()){
                    MovingAssistance requestedService = child.getValue(MovingAssistance.class);
                    movingList.add(requestedService);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("key",requestedService.getStatus().toString());
                    map.put("customer", requestedService.getCustomerName());
                    map.put("email",requestedService.getEmail());
                    data.add(map);
                }
                String [] from = new String []{"key","customer","email"} ;
                int [] to = new int[]{R.id.submitKeyTextView,R.id.customerNameTextView,R.id.emailTextView};
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.submitted_service_layout,from,to);
                movingAssistanceListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Click on item
        carRentalListView = (ListView) findViewById(R.id.submittedCarRentalListView);
        truckRentalListView= (ListView) findViewById(R.id.submittedTruckRentalListView);
        movingAssistanceListView = (ListView) findViewById(R.id.submittedMovingAssistanceListView);

        carRentalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                requestedService = carList.get(i);
                if (identity == Identity.employee){
                    Intent intent1 = new Intent(getApplicationContext(),RequestCarRentalActivity.class);
                    intent1.putExtra("account",account);
                    intent1.putExtra("identity",identity);
                    intent1.putExtra("submittedService",requestedService);
                    intent1.putExtra("scheduledService",requestedService.getScheduledServices());
                    intent1.putExtra("serviceStatus", requestedService.getStatus());
                    startActivity(intent1);
                }
            }
        });

        truckRentalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                requestedService = truckList.get(i);
                if (identity == Identity.employee){
                    Intent intent1 = new Intent(getApplicationContext(),RequestTruckRentalActivity.class);
                    intent1.putExtra("account",account);
                    intent1.putExtra("identity",identity);
                    intent1.putExtra("submittedService",requestedService);
                    intent1.putExtra("scheduledService",requestedService.getScheduledServices());
                    intent1.putExtra("serviceStatus", requestedService.getStatus());
                    startActivity(intent1);
                }
            }
        });

        movingAssistanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                requestedService = movingList.get(i);
                if (identity == Identity.employee){
                    Intent intent1 = new Intent(getApplicationContext(),RequestedMovingAssistanceActivity.class);
                    intent1.putExtra("account",account);
                    intent1.putExtra("identity",identity);
                    intent1.putExtra("submittedService",requestedService);
                    intent1.putExtra("scheduledService",requestedService.getScheduledServices());
                    intent1.putExtra("serviceStatus", requestedService.getStatus());
                    startActivity(intent1);
                }
            }
        });


    }
}