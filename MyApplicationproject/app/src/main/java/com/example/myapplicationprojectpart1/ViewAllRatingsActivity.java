package com.example.myapplicationprojectpart1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ViewAllRatingsActivity extends AppCompatActivity {
    private DatabaseReference ratingsReference;
    private List<Rate> ratingsList;
    private ListView ratingsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_ratings);

        ratingsListView=(ListView) findViewById(R.id.ratingsListView);
        ratingsReference = FirebaseDatabase.getInstance().getReference("rating");
        ratingsList=new LinkedList<>();

        ratingsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ratingsList.clear();
                List<Map<String,String>> data = new LinkedList<>();
                for(DataSnapshot child: snapshot.getChildren()){
                    Rate rate = child.getValue(Rate.class);
                    ratingsList.add(rate);
                    HashMap<String,String>map = new HashMap<>();
                    map.put("service",rate.getScheduledServices().getName());
                    map.put("employeeName",rate.getScheduledServices().getEmployeeName());
                    map.put("rating",Float.toString(rate.getRate()));
                    map.put("customer",rate.getCustomer().getUserName());
                    data.add(map);
                }
                String[] from = new String[]{"service","employeeName","rating","customer"} ;
                int[] to = new int[]{R.id.what,R.id.the,R.id.freak,R.id.isThat};
                SimpleAdapter adaptor = new SimpleAdapter(getApplicationContext(),data,R.layout.adapt_rate_layout,from,to);
                ratingsListView.setAdapter(adaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ratingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Rate rate = ratingsList.get(i);
                Intent intent = new Intent(getApplicationContext(),SpecificRatingActivity.class);
                intent.putExtra("rate",rate);
                startActivity(intent);
            }
        });

    }
}