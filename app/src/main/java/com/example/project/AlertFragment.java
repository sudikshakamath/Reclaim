package com.example.project;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AlertFragment extends Fragment {
    private ListView mMedicationsList;
    private Button mDismissButton;
    private Button mAddMedicationButton;
    private List<Medications> mMedications;
    private DatabaseReference mMedicationsRef;
    private ArrayAdapter<Medications> mAdapter;
    int reqcode =0;

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert, container, false);

        // Get references to the list view and button UI elements
        mMedicationsList = view.findViewById(R.id.medications_list);
        mAddMedicationButton = view.findViewById(R.id.add_button);

        // Initialize the list of medications
        mMedications = new ArrayList<>();

        // Create an ArrayAdapter to populate the list view with medication data
        ArrayAdapter<Medications> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, mMedications);
        mMedicationsList.setAdapter(adapter);

        // Get a reference to the medications node under the "medications" node
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mMedicationsRef = FirebaseDatabase.getInstance().getReference().child("medications").child(uid);

        //notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("medication_channel", "Medication Reminder", NotificationManager.IMPORTANCE_HIGH);
           // NotificationManager notificationManager = getContext().getSystemService()(NotificationManager.class);
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }


        // Set an on-click listener for the add medication button
        mAddMedicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a time picker dialog to allow the user to select a medication time
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Add the medication and its time to the list
                        String medicationName = "Medication " + (mMedications.size() + 1);
                        Calendar medicationTime = Calendar.getInstance();
                        medicationTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        medicationTime.set(Calendar.MINUTE, minute);
                        medicationTime.set(Calendar.SECOND, 0);
                        medicationTime.set(Calendar.MILLISECOND, 0);

                        // Store the medication and its time under the medications node->uid->medication name and under that medication time
                        Map<String, Object> medication = new HashMap<>();
                        medication.put("time", medicationTime.getTimeInMillis());
                        mMedicationsRef.child(medicationName).setValue(medication);
                        //alarm
                        // Set the alarm for the medication time
                        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
                        intent.putExtra("medication_name", medicationName);
//                        intent.putExtra("activityClass", AlertFragment.class);
//                        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0);
                        reqcode++;
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqcode, intent, 0);
//                        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), reqcode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, medicationTime.getTimeInMillis(), pendingIntent);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        mMedicationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMedications.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String medicationName = dataSnapshot.getKey();
                    long medicationTimeInMillis = dataSnapshot.child("time").getValue(Long.class);

                    // Create a new Medication object from the retrieved data
                    Calendar medicationTime = Calendar.getInstance();
                    medicationTime.setTimeInMillis(medicationTimeInMillis);
                    Medications medication = new Medications(medicationName, medicationTime);
                    mMedications.add(medication);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;}

}
