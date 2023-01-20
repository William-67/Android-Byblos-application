package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ServiceState;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViewAllServicesActivies extends AppCompatActivity {
    private DatabaseReference scheduledServiceReference;
    private List<ScheduledServices> scheduledServicesList;
    private ListView scheduledServicesListView;
    private SearchView searchView;
    private Identity identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_services_activies);

        Intent theIntent = getIntent();
        identity = (Identity) theIntent.getSerializableExtra("identity");

        scheduledServicesListView=(ListView) findViewById(R.id.scheduledServicesListView);
        searchView=(SearchView) findViewById(R.id.searchView);



        scheduledServiceReference = FirebaseDatabase.getInstance().getReference("scheduledServices");
        scheduledServicesList=new LinkedList<>();
        scheduledServiceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduledServicesList.clear();
                List<Map<String,String>> data = new LinkedList<>();
                for(DataSnapshot child: snapshot.getChildren()){
                    ScheduledServices scheduledServices = child.getValue(ScheduledServices.class);
                    scheduledServicesList.add(scheduledServices);
                    HashMap<String,String>map = new HashMap<>();
                    map.put("key",scheduledServices.getKey());
                    map.put("serviceName",scheduledServices.getName());
                    map.put("employeeName",scheduledServices.getEmployeeName());
                    map.put("day",scheduledServices.getDayOfWeek().toString());
                    data.add(map);
                }
                SimpleAdapter adaptor = new SimpleAdapter(getApplicationContext(),data,R.layout.scheduled_list_service_layout,new String[]{"key","serviceName","employeeName","day"},new int[]{R.id.keyTextView,R.id.serviceNameTextView,R.id.employeeNameTextView,R.id.dayTextView});
                scheduledServicesListView.setAdapter(adaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        scheduledServicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=(TextView) findViewById(R.id.keyTextView);
                ScheduledServices scheduledServices = scheduledServicesList.get(i);


                if (identity == Identity.employee){


                    Intent intent = new Intent(getApplicationContext(),ScheduleServiceActivity.class);
                    intent.putExtra("account",getIntent().getSerializableExtra("account"));
//                intent.putExtra("scheduledService",scheduledServicesList.get(ScheduledServices.getIndex(scheduledServicesList,textView.getText().toString())));
                    intent.putExtra("scheduledService",scheduledServices);
                    intent.putExtra("eventType",ServiceType.UPDATE_SERVICE);
                    startActivity(intent);
                }
                else if (identity == Identity.customer){
                    if ((scheduledServices.getName()).equals("Car rental")){
                        Intent intent = new Intent(getApplicationContext(), RequestCarRentalActivity.class);
                        intent.putExtra("account",getIntent().getSerializableExtra("account"));
//                intent.putExtra("scheduledService",scheduledServicesList.get(ScheduledServices.getIndex(scheduledServicesList,textView.getText().toString())));
                        intent.putExtra("scheduledService",scheduledServices);
                        intent.putExtra("serviceStatus", RequestStatus.NOT_SUBMIT);
//                    intent.putExtra("eventType",ServiceType.UPDATE_SERVICE);
                        startActivity(intent);
                    }
                    else if (scheduledServices.getName().equals("Truck rental")){

                        Intent intent = new Intent(getApplicationContext(), RequestTruckRentalActivity.class);
                        intent.putExtra("account",getIntent().getSerializableExtra("account"));
//                intent.putExtra("scheduledService",scheduledServicesList.get(ScheduledServices.getIndex(scheduledServicesList,textView.getText().toString())));
                        intent.putExtra("scheduledService",scheduledServices);
                        intent.putExtra("serviceStatus", RequestStatus.NOT_SUBMIT);
//                    intent.putExtra("eventType",ServiceType.UPDATE_SERVICE);
                        startActivity(intent);
                    }else if (scheduledServices.getName().equals("Moving assistance")){
                        Intent intent = new Intent(getApplicationContext(), RequestedMovingAssistanceActivity.class);
                        intent.putExtra("account",getIntent().getSerializableExtra("account"));
//                intent.putExtra("scheduledService",scheduledServicesList.get(ScheduledServices.getIndex(scheduledServicesList,textView.getText().toString())));
                        intent.putExtra("scheduledService",scheduledServices);
                        intent.putExtra("serviceStatus", RequestStatus.NOT_SUBMIT);
//                    intent.putExtra("eventType",ServiceType.UPDATE_SERVICE);
                        startActivity(intent);
                    }
                }

            }
        });
        scheduledServicesListView.setTextFilterEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s==null || s.equals("")){
                    scheduledServicesListView.clearTextFilter();
                }else{
                    scheduledServicesListView.setFilterText(s);
                }
                return false;
            }
        });
    }
}