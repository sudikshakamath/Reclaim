package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rehabcentres extends AppCompatActivity {

    String city1;
    ListView listview1;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rehabcentres);
//        city1 = getIntent().getStringExtra("city");
//        listview1 = (ListView) findViewById(R.id.listviewdata);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("rehab");
//
//        Query query = ref.orderByChild("location").equalTo(city1);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<String> centreNames = new ArrayList<>();
//                List<String> centreAddresses = new ArrayList<>();
//
//                for (DataSnapshot centreSnapshot : dataSnapshot.getChildren()) {
//                    String name = centreSnapshot.child("name").getValue(String.class);
//                    String address = centreSnapshot.child("address").getValue(String.class);
//
//                    centreNames.add(name);
//                    centreAddresses.add(address);
//                }
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(rehabcentres.this, android.R.layout.simple_list_item_2, android.R.id.text1, centreNames) {
//                    @Override
//                    public View getView(int position, View convertView, ViewGroup parent) {
//                        View view = super.getView(position, convertView, parent);
//
//                        TextView text2 = view.findViewById(android.R.id.text2);
//                        text2.setText(centreAddresses.get(position));
//
//                        return view;
//                    }
//                };
//
//                listview1.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle any errors that occur during the query
//            }
//        });
//
//    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rehabcentres);

    city1 = getIntent().getStringExtra("city");
    listview1 = (ListView) findViewById(R.id.listviewdata);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("rehab");

    Query query = ref.orderByChild("location").equalTo(city1);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            List<Map<String, String>> centreList = new ArrayList<>();

            for (DataSnapshot centreSnapshot : dataSnapshot.getChildren()) {
                String name = centreSnapshot.child("name").getValue(String.class);
                String address = centreSnapshot.child("address").getValue(String.class);

                Map<String, String> centreMap = new HashMap<>();
                centreMap.put("name", name);
                centreMap.put("address", address);

                centreList.add(centreMap);
            }

            SimpleAdapter adapter = new SimpleAdapter(rehabcentres.this, centreList, R.layout.list_item,
                    new String[]{"name", "address"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listview1.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Handle any errors that occur during the query
        }
    });
}
}