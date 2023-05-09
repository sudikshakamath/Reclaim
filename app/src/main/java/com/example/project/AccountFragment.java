package com.example.project;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {

    TextView profileName,profileEmail, profileUsername,daysSober,daysLeft;
    Button relapse;
    int daysSoberValue;
    int daysLeftValue;
    public AccountFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        profileName = (TextView) view.findViewById(R.id.profileName);
        profileEmail = (TextView) view.findViewById(R.id.profileEmail);
        profileUsername = (TextView) view.findViewById(R.id.profileUsername);
        daysSober = (TextView) view.findViewById(R.id.dayssober);
        daysLeft = (TextView) view.findViewById(R.id.daysleft);
        relapse = (Button) view.findViewById(R.id.relapseButton);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference userRef = database.getReference("users").child(uid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);

                // Update the UI with the user's name and email
                profileName.setText(name);
                profileEmail.setText(email);
                profileUsername.setText(uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
        userRef.child("days_sober").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daysSoberValue = dataSnapshot.getValue(Integer.class);
                daysSober.setText(String.valueOf(daysSoberValue));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });


        userRef.child("days_left").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daysLeftValue = dataSnapshot.getValue(Integer.class);
                daysLeft.setText(String.valueOf(daysLeftValue));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
        // Create a handler to execute the loop every 30 seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the current value of days sober from the database and increment it by 1
                userRef.child("days_sober").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int daysSoberValue = dataSnapshot.getValue(Integer.class);

                        userRef.child("days_sober").setValue(daysSoberValue + 1);
                        daysSober.setText(String.valueOf(daysSoberValue + 1));

                        userRef.child("days_left").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int daysLeftValue = dataSnapshot.getValue(Integer.class);

                                // Subtract 1 from daysLeftValue
                                daysLeftValue = daysLeftValue - 1;

                                userRef.child("days_left").setValue(daysLeftValue);
                                daysLeft.setText(String.valueOf(daysLeftValue));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle errors
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors
                    }
                });

                // Repeat the loop after 30 seconds
                handler.postDelayed(this, 30000);
            }
        }, 30000);

        relapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.child("days_sober").setValue(0);
                userRef.child("days_left").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int initialDaysLeftValue = daysSoberValue + dataSnapshot.getValue(Integer.class);
                        userRef.child("days_left").setValue(initialDaysLeftValue);
                        daysLeft.setText(String.valueOf(initialDaysLeftValue));
                        daysSober.setText(String.valueOf(0));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors
                    }
                });
            }
        });

        return view;
    }
}