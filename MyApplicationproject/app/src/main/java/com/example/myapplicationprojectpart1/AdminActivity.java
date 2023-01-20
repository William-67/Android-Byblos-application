package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


public class AdminActivity extends AppCompatActivity {
    private DatabaseReference accountsReference;
    private DatabaseReference servicesReference;
    private List<UserAccount> accounts;
    private ListView accountListView;
    private ListView servicesListView;
    private List<Service> services;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        accounts = new LinkedList<>();
        services = new LinkedList<>();


        accountListView = (ListView)findViewById(R.id.accountListview);
        servicesListView = (ListView) findViewById(R.id.serviceListview);

        //for going to account detail page
        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserAccount account = accounts.get(i);
                Intent intent = new Intent(getApplicationContext(),AccountDeleteActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                Intent intent = new Intent(getApplicationContext(),AddServiceActivity.class);
                intent.putExtra("service",service);
                intent.putExtra("ServiceType",ServiceType.UPDATE_SERVICE);
                startActivity(intent);
            }
        });

        accountsReference = FirebaseDatabase.getInstance().getReference("accounts");
        servicesReference = FirebaseDatabase.getInstance().getReference("listOfServices");
        accountsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                List<Map<String,String>> map = new LinkedList<>();

                for (DataSnapshot children: snapshot.getChildren()){
                    UserAccount account = children.getValue(UserAccount.class);
                    accounts.add(account);

                    // get data from database, and used by adapter
                    HashMap<String,String> accountInfo = new HashMap<>();
                    accountInfo.put("User:",account.getUserName());
                    accountInfo.put("Identity:",account.getIdentity().toString());
                    map.add(accountInfo);
                }
                //using an adapter
                String[] from = new String[]{"User:","Identity:"};
                int[] to = new int[]{R.id.userNameText,R.id.identityTextview};
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), map,
                        R.layout.accountlistview,from,to);
                accountListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        servicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                List<Map<String,String>> map = new LinkedList<>();
                for (DataSnapshot children:snapshot.getChildren()){
                    Service service = children.getValue(Service.class);
                    services.add(service);
                    HashMap<String,String> serviceInfo = new HashMap<>();
                    serviceInfo.put("Service name:",service.getName());
                    serviceInfo.put("Price:",Double.toString(service.getPrice()));
                    map.add(serviceInfo);
                }

                String[] from = new String[]{"Service name:","Price:"};
                int[] to = new int[]{R.id.customerNameTextView,R.id.priceTextview};
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),map,
                        R.layout.service_listview,from,to);
                servicesListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addService(View view){
        Intent intent = new Intent(getApplicationContext(),AddServiceActivity.class);
        intent.putExtra("ServiceType",ServiceType.CREATE_SERVICE);
        startActivity(intent);
    }
}